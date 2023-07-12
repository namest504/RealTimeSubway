package com.lim1t.realtimesubway.controller;

import com.lim1t.realtimesubway.dto.StationInfoDto.StationInfoResponse;
import com.lim1t.realtimesubway.dto.SubwayInfoDto.StationInfo;
import com.lim1t.realtimesubway.dto.SubwayInfoDto.SubwayInfoResponse;
import com.lim1t.realtimesubway.service.SubwayServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class SubwayController {

    private final SubwayServiceImpl subwayServiceImpl;

    /**
     * 실시간 역 기준 지하철 도착 정보 요청
     * @param subwayName
     * @return
     */
    @GetMapping("/rawjson/arrival")
    public Mono<ResponseEntity<StationInfoResponse>> getArrivalInfo(@RequestParam String subwayName) {
        Mono<StationInfoResponse> stationArrivalInfo = subwayServiceImpl.findStationArrivalInfo(subwayName);
//        return Mono.just(ResponseEntity.ok().body(stationArrivalInfo));
        return stationArrivalInfo.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    /**
     * 실시간 지하철 기준 상행선&하행선 상태 데이터
     *
     * @param subwayNumber
     * @return Flux<StationInfo>
     */
    @GetMapping("/station")
    public Mono<ResponseEntity<StationInfo>> parsingStationInfo(@RequestParam int subwayNumber) {

//        Flux<StationInfo> trainInfoByStation = subwayServiceImpl.getTrainInfoByStation(subwayNumber);
//        return Mono.just(ResponseEntity.ok().body(trainInfoByStation));
        Flux<StationInfo> trainInfoByStation = subwayServiceImpl.getTrainInfoByStation(subwayNumber);
        return trainInfoByStation
                .collectList()
                .filter(list -> !list.isEmpty())
                .map(list -> ResponseEntity.ok().body(list.get(0)))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * API 정보 요청
     *
     * @param startIdx
     * @param endIdx
     * @param subwayNumber
     * @return Mono<SubwayInfoResponse>
     * @example 1호선 정보 요청 : http://localhost:8080/rawjson/station?startIdx=0&endIdx=99&subwayNumber=1
     */
    @GetMapping("/rawjson/station")
    public Mono<ResponseEntity<SubwayInfoResponse>> requestInfo(
            @RequestParam int startIdx,
            @RequestParam int endIdx,
            @RequestParam int subwayNumber
    ) {
        Mono<SubwayInfoResponse> allInfo = subwayServiceImpl.findAllInfo(startIdx, endIdx, subwayNumber);
        return allInfo.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
