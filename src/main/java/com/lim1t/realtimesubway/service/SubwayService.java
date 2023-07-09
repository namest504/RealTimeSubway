package com.lim1t.realtimesubway.service;

import com.lim1t.realtimesubway.dto.SubwayInfoDto.SubwayInfoResponse;
import reactor.core.publisher.Mono;

public interface SubwayService {

    Mono<SubwayInfoResponse> findAllInfo(int startIdx, int endIdx, int subwayNumber);
}
