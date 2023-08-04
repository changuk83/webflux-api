package com.jden.webflux.testcode.operators;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;

@Slf4j
public class GenerateTest {
    @Test
    public void generateTest() {
        Flux.generate(
                () -> 0,
                (state, sink) -> {
                    sink.next(state);
                    if(state == 10) sink.complete();
                    return ++state;
                })
                .subscribe(data -> log.info("{}", data));
        Assert.isTrue(1 == 1, "test");
    }
}
