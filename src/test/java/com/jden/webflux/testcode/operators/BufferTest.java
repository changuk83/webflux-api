package com.jden.webflux.testcode.operators;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

@Slf4j
public class BufferTest {
    @Test
    public void bufferTest() {
        Flux.range(1, 100)
                .buffer(10)
                .subscribe(buffer -> log.info("{}", buffer));
    }
}
