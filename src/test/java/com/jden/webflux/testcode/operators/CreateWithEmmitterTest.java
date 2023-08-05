package com.jden.webflux.testcode.operators;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class CreateWithEmmitterTest {
    static int start = 1;
    static int end = 4;

    @Test
    public void emmiterTest() throws InterruptedException {
        Flux.create((FluxSink<Integer> sink) -> {
            sink.onRequest(n -> {
                log.info("# requested: " + n);
                try {
                    Thread.sleep(500L);
                    for(int i = start ; i <= end ; i++) {
                        sink.next(i); // 4개씩 emit
                    }
                    start += 4;
                    end += 4;
                } catch (Exception e) {

                }
            });

            sink.onDispose(() -> {
                log.info("# clean up");
            });
        },
            FluxSink.OverflowStrategy.DROP) // 넘치는 것은 drop
            .subscribeOn(Schedulers.boundedElastic())
            .publishOn(Schedulers.parallel(), 2)
            .subscribe(data -> log.info("# onNext : {}", data));

        Thread.sleep(3000L);
    }
}
