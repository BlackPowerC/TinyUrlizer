package com.blackpowerc.tinyurlizer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author jordy jordy.fatigba@theopentrade.com
 */
@Controller
public class ShrinkedURLPageController
{
    @GetMapping(path = "/shrinker/index.html")
    public String index() {
        return "app" ;
    }
}
