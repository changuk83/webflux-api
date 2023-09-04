package com.jden.webflux.testcode.operators;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

@Slf4j
public class OnErrorContinueTest {

    // error 가 나도 계속해서 emit 될 수 있게 함.
    @Test
    public void onErrorContinueTest() {
        Flux.just(1,2,4,0,6,12)
                .map(num -> 12 / num)
                .onErrorContinue((error, num) ->
                        log.error("error : {}, num : {}", error.getMessage(), num))
                .subscribe(data -> log.info("# onNext : {}", data),
                error -> log.error("# onError : ", error));
    }
}
