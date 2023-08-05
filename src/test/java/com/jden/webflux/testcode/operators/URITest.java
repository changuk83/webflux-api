package com.jden.webflux.testcode.operators;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
public class URITest {
    @Test
    public void callURITest() throws InterruptedException {
        URI baseUri = UriComponentsBuilder.newInstance().scheme("http")
                .host("localhost")
                .port(8080)
                .path("/mono/test1")
                .build()
                .encode()
                .toUri();

        WebClient.create()
                .get()
                .uri(baseUri)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(d -> log.info("{}", d));

        Thread.sleep(5000L);
    }
}
