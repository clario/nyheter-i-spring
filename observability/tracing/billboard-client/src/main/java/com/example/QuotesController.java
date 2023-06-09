package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;

@RestController
public class QuotesController {

    private final QuoteService quoteService;

    public QuotesController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }




    @GetMapping("/random")
    Quote random() {
        return  this.quoteService.randomQuote()
                .retryWhen(Retry.backoff(3, Duration.ofMillis(500))
                        .filter(this::is5xxServerError))
                .block();
    }


    @GetMapping("/all")
    List<Quote> all() {
        return this.quoteService.getAllQuotes();
    }

    @GetMapping("/quote/{id}")
    Quote getQuote(@PathVariable Integer id){
        return this.quoteService.getQuoteById(id);
    }


    public boolean is5xxServerError(Throwable throwable){
        return throwable instanceof WebClientResponseException &&
                ((WebClientResponseException)throwable).getStatusCode().is5xxServerError();
    }
}
