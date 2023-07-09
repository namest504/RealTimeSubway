package com.lim1t.realtimesubway.service;

import com.lim1t.realtimesubway.dto.SubwayInfoDto.StationInfo;
import com.lim1t.realtimesubway.dto.SubwayInfoDto.SubwayInfoResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SubwayService {

    /**
     * 전체 구역의 운행중인 지하철 역 기준 전체 상태 조회
     */
    Flux<StationInfo> parsingIdWithName(int subwayNumber);

    /**
     * 특정 구역의 운행중인 지하철 목록 전체 조회
     */
    Mono<SubwayInfoResponse> findAllInfo(int startIdx, int endIdx, int subwayNumber);
}
