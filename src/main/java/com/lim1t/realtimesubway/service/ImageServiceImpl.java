package com.lim1t.realtimesubway.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    @Override
    public Flux<DataBuffer> loadImage(String imageType, String imageName) {
        log.info("{}", imageName);
        ClassPathResource imageResource = new ClassPathResource("static/images/" + imageType + "/" + imageName);

        if (!imageResource.exists()) {
            log.warn("파일을 찾을 수 없음");
            return Flux.empty();
        }

        return DataBufferUtils.read(
                imageResource,
                new DefaultDataBufferFactory(),
                4096);
    }
}
