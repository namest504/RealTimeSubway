package com.lim1t.realtimesubway.controller;

import com.lim1t.realtimesubway.service.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageServiceImpl imageService;

    /**
     * 서비스 관련 이미지 불러오기
     *
     * @param imageType
     * @param imageName
     * @return
     * @example http://localhost:8080/images/icon/1%ED%98%B8%EC%84%A0.svg
     * @example http://localhost:8080/images/line/1%ED%98%B8%EC%84%A0.png
     */
    @GetMapping("/images/{imageType}/{imageName}")
    public Mono<ResponseEntity<DataBuffer>> getImage(
            @PathVariable String imageType,
            @PathVariable String imageName) {

        MediaType mediaType;

        if (imageName.toLowerCase().endsWith(".png")) {
            mediaType = MediaType.IMAGE_PNG;
        } else if (imageName.toLowerCase().endsWith(".jpeg") || imageName.toLowerCase().endsWith(".jpg")) {
            mediaType = MediaType.IMAGE_JPEG;
        } else if (imageName.toLowerCase().endsWith(".svg")) {
            mediaType = MediaType.valueOf("image/svg+xml");
        } else {
            mediaType = MediaType.IMAGE_PNG;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);

        Mono<DataBuffer> dataBufferMono = imageService.loadImage(imageType, imageName).reduce(DataBuffer::write);
        return dataBufferMono.map(dataBuffer -> ResponseEntity.ok().headers(headers).body(dataBuffer))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
