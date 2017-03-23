package com.charlie.weibo.controller;

import com.charlie.weibo.entity.User;
import com.charlie.weibo.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by dhy on 17-3-22.
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public long register(@RequestBody(required = false) User user) throws IOException {
        return userService.register(user);
    }

    private UserService userService;

    private final static Logger logger = Logger.getLogger(UserController.class);
}
