package com.jden.webflux.testcode.operators;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Slf4j
public class BackpressureTest2 {
    @SneakyThrows
    @Test
    public void test1() {
        // subscribe 되는 수 보다 emit 되는게 점점 더 많아 지는 상황을 테스트.
        Flux.interval(Duration.ofMillis(1L)) // 0.001 초 마다 emit
                .onBackpressureError() // ERROR 전략으로 지정
                .doOnNext(data -> log.info("# doOnNext : {}", data))
                .publishOn(Schedulers.parallel())
                .subscribe(data -> {
                    try {
                        Thread.sleep(5L); // 0.005 초 대기
                    } catch (Exception e) {

                    }
                    log.info("# onNext : {}", data);
                },
                        error -> log.error("# onError"));

        Thread.sleep(2000L);
    }


    @SneakyThrows
    @Test
    public void test2() {
        // subscribe 되는 수 보다 emit 되는게 점점 더 많아 지는 상황을 테스트.
        Flux.interval(Duration.ofMillis(1L)) // 0.001 초 마다 emit
                .onBackpressureDrop(dropped -> log.info("# dropped : {}", dropped)) // Drop 전략으로 지정
                .doOnNext(data -> log.info("# doOnNext : {}", data))
                .publishOn(Schedulers.parallel())
                .subscribe(data -> {
                            try {
                                Thread.sleep(5L); // 0.005 초 대기
                            } catch (Exception e) {

                            }
                            log.info("# onNext : {}", data);
                        },
                        error -> log.error("# onError"));

        Thread.sleep(2000L);
    }
}
