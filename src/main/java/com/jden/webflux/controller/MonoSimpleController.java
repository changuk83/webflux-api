package com.jden.webflux.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class MonoSimpleController {
    @GetMapping("/mono/test1")
    public Mono<String> hello() {
        log.info("pos1");
        Mono m = Mono.just("Hello WebFlux").log();
        m.subscribe(d -> log.info("{}", d));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        log.info("pos2");
        return m;
    }
}
