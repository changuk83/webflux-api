package com.jden.webflux.testcode.operators;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

@Slf4j
public class FlatmapTest {
    @Test
    public void flatMapTest() {
        Flux.just("Good", "Bad")
                .flatMap(feeling ->
                    Flux.just("Morning", "Afternoon", "Evening")
                            .map(time -> feeling + " " + time))
                .subscribe(log::info);
    }

    @Test
    public void flatMapTest2() {
        Flux.just("Good", "Bad")
                .map(feeling ->
                        Flux.just("Morning", "Afternoon", "Evening")
                                .map(time -> feeling + " " + time))
                .subscribe(d -> {
                    log.info("{}", d);
                });
    }
}
