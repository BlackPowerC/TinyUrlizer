package com.blackpowerc.tinyurlizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

/**
 * API REST pour raccourcir les urls.
 *
 * @author jordy jordy.fatigba@theopentrade.com
 */
@Transactional
@RestController
public class ShrinkedURLResource
{
    private ShrinkedURLService shrinkedURLService ;

    @Autowired
    public ShrinkedURLResource(ShrinkedURLService shrinkedURLService) {
        this.shrinkedURLService = shrinkedURLService;
    }

    @PostMapping(path = "/api/shrink",
            consumes = {
                MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                MediaType.APPLICATION_JSON_VALUE
            },
            produces = {
                MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                MediaType.APPLICATION_JSON_VALUE
            }
    )
    public ResponseEntity<ShrinkedURLResponse> shrinkUrl(@RequestBody(required = true) ShrinkedURLForm form)
    {
        ShrinkedURL shrinkedURL = this.shrinkedURLService.shrinkUrl(form.sourceUrl()) ;

        String shrinkedUrlValue = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/{id}")
                .build(Map.of("id", shrinkedURL.getPartialId()))
                .toString() ;

        return new ResponseEntity<>(new ShrinkedURLResponse(shrinkedUrlValue, shrinkedURL.getPartialId()), HttpStatus.CREATED) ;
    }

    @GetMapping(path = "/{partialId}")
    public ResponseEntity<String> redirectShrinkedUrl(@PathVariable String partialId)
    {
        Optional<ShrinkedURL> shrinkedURL = this.shrinkedURLService.findByPartialId(partialId) ;

        // 404 not found
        if(shrinkedURL.isEmpty()) {
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND) ;
        }

        String shrinkedUrlValue = shrinkedURL.get().getSourceUrl() ;

        HttpHeaders headers = new HttpHeaders() ;
        headers.setLocation(URI.create(shrinkedUrlValue)) ;

        // redirection
        return new ResponseEntity<>(headers, HttpStatus.FOUND) ;
    }
}
