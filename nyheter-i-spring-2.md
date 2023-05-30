

# Nyheter i Spring Boot 3


## Krav
 * Må være på Java 17 (Kotlin 1.7+)
 * Om man skal oppdatere fra Spring Boot 2 fins det gode migrasjonsguider.
   *  (Om man er på en eldre 2.X versjon, start med å oppgrader til 2.7.3)
 * Java EE til Jakarta EE9
    * ```import javax.servlet.http.HttpServletRequest; ```
    * Må endre til:
    * ``` import jakarta.servlet.http.HttpServletRequest; ```


## Tracing
 * Tracing i Spring Cloud Sleuth 
 * OpenTelemetry
 * Demo med klient og server + db 
 * Server: [message-service](observability%2Ftracing%2Fmessage-service)
 * Klient: [billboard-client](observability%2Ftracing%2Fbillboard-client)
 * Bruker Zipkin som trace klient

Diagram:
```mermaid
graph LR
      A[Client] --> B{billboard-client}
      B[BillboardClient] --> C[MessageService] --> D[OpenZipkin]
```

Klient: http://localhost:8080/
Zipkin: http://localhost:9411/


## Graphql
* Ligner Spring sin REST-kontroller [quotes-graphql](graphql%2Fquotes-graphql)

http://localhost:8080/graphiql?path=/graphql

```
{
    allAuthors {
        field
        name
        wikipediaUrl
   }
}
```

## Native images
* Scale to Zero
* GraalVM, bygge native images for raskare oppstart og mindre minnebruk
* Spring Native fungere dårlig på arm64...
* 

## Declarative clients
* Bruke interface for å lage REST-klienter
* Om man er vant til å bruke Feign, så er dette ein god erstatning, så slepper man en ekstra avhengighet

## Bonus

### Bygge container
* Eksempel på korleis man typisk gjer det: [fatjar-dockerfile](containerize%2Ffatjar-dockerfile)
* Bedre: [layers-dockerfile](containerize%2Flayers-dockerfile)
* "Dive" av container image
* PAKETO buildpacks