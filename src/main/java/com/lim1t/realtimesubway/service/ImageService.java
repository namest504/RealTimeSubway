package com.lim1t.realtimesubway.service;

import org.springframework.core.io.buffer.DataBuffer;
import reactor.core.publisher.Flux;

public interface ImageService {

    /**
     * static 경로의 이미지 불러오기
     */
    Flux<DataBuffer> loadImage(String imageType, String imageName);
}
