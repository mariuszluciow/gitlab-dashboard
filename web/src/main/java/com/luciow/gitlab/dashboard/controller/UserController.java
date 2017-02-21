package com.luciow.gitlab.dashboard.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {

    @RequestMapping(method = RequestMethod.GET, value = "/api/user")
    public Principal getPrincipal(Principal principal) {
        return principal == null ? () -> null : principal;
    }
}
