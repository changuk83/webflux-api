package com.jden.webflux.testcode.operators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.Iterable;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.Arrays;

@Slf4j
public class GroupByTest {
    @AllArgsConstructor
    @Data
    public static class BookData {
        private String name;
        private String authorName;
    }
    @Test
    public void groupByTest() {
        Iterable<BookData> iterable = () -> {
            return Arrays.asList(
                    new BookData("book1", "1"),
                    new BookData("book2", "1"),
                    new BookData("book3", "1"),
                    new BookData("book4", "2"),
                    new BookData("book5", "2")
            ).iterator();
        };

        Flux.fromIterable(iterable)
                .groupBy(book ->
                        book.getAuthorName(),
                        book -> book.getName() + "(" + book.getAuthorName() + ")")
                .flatMap(groupedFlux -> groupedFlux.collectList())
                .subscribe(bookByAuthor -> {
                    log.info("# book by author : {}", bookByAuthor);
                });
    }
}
