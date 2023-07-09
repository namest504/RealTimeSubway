package com.lim1t.realtimesubway.controller;

import com.lim1t.realtimesubway.dto.SubwayInfoDto.SubwayInfoResponse;
import com.lim1t.realtimesubway.service.SubwayServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class SubwayController {

    private final SubwayServiceImpl subwayServiceImpl;

    /**
     * API 정보 요청
     * @example 1호선 정보 요청 : http://localhost:8080/rawjson?startIdx=0&endIdx=99&subwayNumber=1
     * @param startIdx
     * @param endIdx
     * @param subwayNumber
     * @return Mono<SubwayInfoResponse>
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
