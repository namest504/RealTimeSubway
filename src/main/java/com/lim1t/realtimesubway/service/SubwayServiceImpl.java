package com.lim1t.realtimesubway.service;

import com.lim1t.realtimesubway.dto.SubwayInfoDto.SubwayInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubwayServiceImpl implements SubwayService {

    @Value("${SUBWAY_POSITION_API_KEY}")
    private String SUBWAY_POSITION_API_KEY;

    private final WebClient webClient;

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