package com.jden.webflux.testcode.operators;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

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

    /**
     * 구구단 중 3단 출력
     */
    @Test
    public void multiTest() {
        final int dan = 3;
        Flux.generate(
                () -> Tuples.of(dan, 1),
                (state, sink) -> {
                    sink.next(String.format("%d * %d = %d", state.getT1(), state.getT2(), (state.getT1() * state.getT2())));

                    if(state.getT2() == 9) sink.complete();
                    return Tuples.of(dan, state.getT2() + 1);
                }
        ).subscribe(d -> log.info("{}", d));
    }
}
