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
        try {
            ClassPathResource imageResource = new ClassPathResource("static/images/" + imageType + "/" + imageName);
            return DataBufferUtils.read(
                    imageResource,
                    new DefaultDataBufferFactory(),
                    4096);
        } catch (Exception e) {
            return Flux.empty();
        }
    }
}
