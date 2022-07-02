package com.blackpowerc.tinyurlizer;

import lombok.extern.java.Log;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

@Log
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShrinkedURLResourceTest
{
    private final TestRestTemplate restClient ;

    private static final String sourceUrl = "https://theopentrade.com" ;
    private static ShrinkedURLResponse urlResponse ;

    @Autowired
    public ShrinkedURLResourceTest(TestRestTemplate restClient) {
        this.restClient = restClient;
    }

    @Test
    @Order(1)
    @Tag("integration-test")
    public void testShrinkUrl()
    {
        var response = restClient
                .postForEntity(
                        "/api/shrink",
                        new ShrinkedURLForm(sourceUrl),
                        ShrinkedURLResponse.class
                ) ;

        urlResponse = response.getBody() ;

        String shrinkedUrlValue = urlResponse.shrinkedUrl() ;

        log.info("ShrinkUrl: response body: " + shrinkedUrlValue);

        assertSame(response.getStatusCode(), HttpStatus.CREATED) ;
        assertFalse(shrinkedUrlValue.isBlank()) ;
        assertFalse(shrinkedUrlValue.isEmpty()) ;
    }

    @Test
    @Order(2)
    @Tag("integration-test")
    public void testRedirectShrinkedUrl()
    {
        var response = restClient
                .getForEntity("/" + urlResponse.partialId(), String.class) ;

        assertSame(response.getStatusCode(), HttpStatus.FOUND) ;
        assertEquals(response.getHeaders().getLocation().toString(), sourceUrl);
    }

    @Test
    @Tag("integration-test")
    public void testRedirectNotFoundShrinkedUrl()
    {
        var response = restClient
                .getForEntity("/" + UUID.randomUUID(), String.class) ;

        assertSame(response.getStatusCode(), HttpStatus.NOT_FOUND) ;
    }
}