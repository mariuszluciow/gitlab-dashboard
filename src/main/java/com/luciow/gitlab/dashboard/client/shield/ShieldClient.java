package com.luciow.gitlab.dashboard.client.shield;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "shield", url = "https://img.shields.io")
public interface ShieldClient {

    @RequestMapping(value = "/badge/{left}-{right}-{color}.svg?style=flat-square", method = RequestMethod.GET)
    String getBadge(@PathVariable("left") String left, @PathVariable("right") String right, @PathVariable("color") String color, @RequestHeader("User-Agent") String userAgent);

}
