package com.jden.webflux.testcode.operators;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;


@Slf4j
public class PublishTest {

    /**
     * hot sequence 로 동작하는 걸 확인해 볼 수 있는 예제
     */
    @SneakyThrows
    @Test
    public void publishTest() {
        ConnectableFlux<Integer> flux =
                Flux.range(1, 5)
                        .delayElements(Duration.ofMillis(300L))
                        .publish();

        Thread.sleep(500L);
        flux.subscribe(data -> log.info("# subscriber1 : {}", data));

        Thread.sleep(200L);
        flux.subscribe(data -> log.info("# subscribe2 : {}", data));

        flux.connect(); // 이 때 부터 emit 됨.

        Thread.sleep(1000L);
        flux.subscribe(data -> log.info("# subscriber3 : {}", data));

        Thread.sleep(2000L);
    }

    @SneakyThrows
    @Test
    public void autoConnectTest() {
        Flux<String> publisher =
                Flux.just("Concert part1", "Concert part2", "Concert part3")
                        . delayElements(Duration.ofMillis(300L))
                        .publish()
                        .autoConnect(2); // 파라미터로 지정한 숫자만큼 구독이 발생하면 emit 시작.

        Thread.sleep(500L);
        publisher.subscribe(data -> log.info("# audience 1 is waiting {}", data));

        Thread.sleep(500L);
        publisher.subscribe(data -> log.info("# audience 2 is waiting {}", data));

        Thread.sleep(500L);
        publisher.subscribe(data -> log.info("# audience 3 is waiting {}", data));

        Thread.sleep(1000L);
    }
}
