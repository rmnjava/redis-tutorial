package com.charlie.weibo.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dhy on 17-3-22.
 *
 */
@RestController
public class HelloController {
    @RequestMapping("/")
    public String index() {
        return "Greeting from Spring boot\n";
    }
}
