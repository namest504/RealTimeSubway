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
    public Flux<StationInfo> parsingIdWithName(int subwayNumber) {
        return findAllInfo(0, 300, subwayNumber)
                .map(subwayInfoResponse -> {
                    List<SubwayInfoDto.RealtimePosition> realtimePositionList = subwayInfoResponse.getRealtimePositionList();
                    List<StationInfo> stationInfoList = new ArrayList<>();

                    for (SubwayInfoDto.RealtimePosition position : realtimePositionList) {
                        int stationId = Integer.parseInt(position.getStatnId());
                        String stationName = position.getStatnNm();
                        String stationLine = position.getUpdnLine();
                        String stationStatus = position.getTrainSttus();
                        stationInfoList.add(new StationInfo(stationId, stationName, stationLine, stationStatus));
                    }
                    /*
                    todo: 중복 제거 및 정렬 필요한지 실제 데이터 비교후 확인 필요
                    List<StationInfo> collect = stationInfoList.stream().distinct().collect(Collectors.toList());
                    Collections.sort(collect, (StationInfo o1, StationInfo o2) -> o1.getStationId() - o2.getStationId());
                    */
                    return stationInfoList;
                })
                .flatMapMany(Flux::fromIterable);
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