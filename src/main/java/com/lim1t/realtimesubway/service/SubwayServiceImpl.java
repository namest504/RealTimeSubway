package com.lim1t.realtimesubway.service;

import com.lim1t.realtimesubway.dto.SubwayInfoDto;
import com.lim1t.realtimesubway.dto.SubwayInfoDto.StationInfo;
import com.lim1t.realtimesubway.dto.SubwayInfoDto.SubwayInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubwayServiceImpl implements SubwayService {

    @Value("${SUBWAY_POSITION_API_KEY}")
    private String SUBWAY_POSITION_API_KEY;

    private final WebClient webClient;

    @Override
    public Flux<StationInfo> getTrainInfoByStation(int subwayNumber) {
        log.info("getTrainInfoByStation 실행");
        return findAllInfo(0, 300, subwayNumber)
                .flatMapMany(subwayInfoResponse -> {
                    List<SubwayInfoDto.RealtimePosition> realtimePositionList = subwayInfoResponse.getRealtimePositionList();
                    List<StationInfo> stationInfoList = new ArrayList<>();

                    for (SubwayInfoDto.RealtimePosition position : realtimePositionList) {
                        stationInfoList.add(new StationInfo(
                                position.getStatnId(),
                                position.getStatnNm(),
                                position.getTrainNo(),
                                position.getUpdnLine(),
                                position.getTrainSttus(),
                                position.getStatnTid(),
                                position.getStatnTnm(),
                                position.getDirectAt(),
                                position.getLstcarAt()));
                    }
                /*
                todo: 중복 제거 및 정렬 필요한지 실제 데이터 비교후 확인 필요
                List<StationInfo> collect = stationInfoList.stream().distinct().collect(Collectors.toList());
                Collections.sort(collect, (StationInfo o1, StationInfo o2) -> o1.getStationId() - o2.getStationId());
                */
                    return Flux.fromIterable(stationInfoList);
                });
    }

    @Override
    public Mono<SubwayInfoResponse> findAllInfo(int startIdx, int endIdx, int subwayNumber) {
        log.info("findAllInfo 실행");
        return webClient
                .get()
                .uri("http://swopenapi.seoul.go.kr/api/subway/" + SUBWAY_POSITION_API_KEY + "/json/realtimePosition/" + startIdx + "/" + endIdx + "/" + subwayNumber + "호선")
                .retrieve()
                .bodyToMono(SubwayInfoResponse.class);
    }
}