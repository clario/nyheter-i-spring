package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuoteController2 {

  private final QuoteRepository quoteRepository;

  public QuoteController2(QuoteRepository quoteRepository) {
    this.quoteRepository = quoteRepository;
  }

  @GetMapping("/random-quote")
  public Quote randomQuote() {
    System.out.println("returning a random quote");
    System.out.println("returning a random quote");
    System.out.println("returning a random quote");
    System.out.println("returning a random quote");
    System.out.println("returning a random quote");
    System.out.println("returning a random quote");
    System.out.println("returning a random quote");
    System.out.println("returning a random quote");
    System.out.println("returning a random quote");

    return quoteRepository.findRandomQuote();
  }
}
