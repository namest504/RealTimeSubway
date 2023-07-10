package com.lim1t.realtimesubway.controller;

import com.lim1t.realtimesubway.dto.SubwayInfoDto.StationInfo;
import com.lim1t.realtimesubway.dto.SubwayInfoDto.SubwayInfoResponse;
import com.lim1t.realtimesubway.service.SubwayServiceImpl;
import lombok.RequiredArgsConstructor;
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
     * 실시간 지하철 역 기준 상행선&하행선 상태 데이터
     *
     * @param subwayNumber
     * @return Flux<StationInfo>
     */
    @GetMapping("/station")
    public Flux<StationInfo> parsingStationInfo(@RequestParam int subwayNumber) {
        return subwayServiceImpl.getTrainInfoByStation(subwayNumber);
    }

    /**
     * API 정보 요청
     *
     * @param startIdx
     * @param endIdx
     * @param subwayNumber
     * @return Mono<SubwayInfoResponse>
     * @example 1호선 정보 요청 : http://localhost:8080/rawjson?startIdx=0&endIdx=99&subwayNumber=1
     */
    @GetMapping("/rawjson")
    public Mono<SubwayInfoResponse> requestInfo(
            @RequestParam int startIdx,
            @RequestParam int endIdx,
            @RequestParam int subwayNumber
    ) {
        return subwayServiceImpl.findAllInfo(startIdx, endIdx, subwayNumber);
    }
}
