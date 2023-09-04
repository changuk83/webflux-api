package com.jden.webflux.testcode.operators;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
public class RetryTest {
    @Test
    public void retryTest() throws InterruptedException {
        final int[] count = {1};

        Flux
                .range(1, 3)
                .delayElements(Duration.ofSeconds(1))
                .map(num -> {
                    try {
                        if(num == 3 && count[0] == 1) {
                            count[0]++;
                            Thread.sleep(1000);
                        }
                    } catch (Exception e) {

                    }
                    return num;
                })
                .timeout(Duration.ofMillis(1500L)) // 1.5초간 emit되는 데이터가 upstream에서 없으면 timeout Exception
                .retry(1)
                .subscribe(data -> log.info("#onNext : {}", data),
                        (error -> log.error("#onError : ", error)),
                        () -> log.info("# onComplte"));
        Thread.sleep(7000);
    }
}
