package com.blackpowerc.tinyurlizer.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Configuration
public class WebConfiguration implements WebMvcConfigurer
{
    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        registry.addMapping("/api/*")
                .allowedMethods("POST")
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowCredentials(false)
                .maxAge(3600) ;
    }
}
