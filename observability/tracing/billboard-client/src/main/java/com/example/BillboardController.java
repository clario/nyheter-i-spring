package com.example;

import io.micrometer.tracing.Baggage;
import io.micrometer.tracing.Tracer;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpRequest;
import java.util.Enumeration;

@RestController
public class BillboardController {

  private final Logger logger = LoggerFactory.getLogger(BillboardController.class);
  private final RestTemplate restTemplate;

  private final Tracer tracer;

  public BillboardController(RestTemplateBuilder builder, Tracer tracer) {

    // TLDR: Always use RestTemplateBuilder
    //
    // If ou create the rest template using new RestTemplate() instead of using
    // a RestTemplateBuilder the created template will not be configured with
    // the correct instrumentation setting you will not see any trace ids
    // propagated to the backend message-service
    this.restTemplate = builder.build();
    // this.restTemplate.setRequestFactory( new HttpComponentsClientHttpRequestFactory());
    this.tracer = tracer;
  }



  @GetMapping("/message")
  // Get header from request
  public String get(HttpServletRequest request) {
    Enumeration<String> headerNames = request.getHeaderNames();
    logger.info(headerNames.toString());
    logger.info("Calling message-service");
    String billboardId = "123";
    //
    // The BaggageInScope needs to be closed so that spans after this code block
    // don't have the baggage.
    //
    Baggage baggage = this.tracer.createBaggage("billboardId");
    baggage.set(billboardId);
    Quote quote = restTemplate.getForObject("http://localhost:8081/", Quote.class);
    logger.info("message-service returned {}", quote);
    return quote.getQuote() + " -- " + quote.getAuthor();


  }
}
