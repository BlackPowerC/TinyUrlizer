package com.blackpowerc.tinyurlizer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ShrinkedURLServiceTest
{
    private static String partialId ;

    @Autowired
    private ShrinkedURLService service ;

    @Order(1)
    @ParameterizedTest
    @Tag("integration-test")
    @ValueSource(strings = {"https://theopentrade.com", "https://www.globalknowledge.com/fr-fr/ressources/livres-blancs/2021-it-skills-and-salary-report"})
    public void testShrinkUrl(String url) {
        partialId = service.shrinkUrl(url).getPartialId() ;
    }

    @Test
    @Order(2)
    @Tag("integration-test")
    public void testFindByPartialId()
    {
        Optional<ShrinkedURL> optionalUrl = this.service.findByPartialId(partialId) ;
        Assertions.assertTrue(optionalUrl.isPresent()) ;
    }
}