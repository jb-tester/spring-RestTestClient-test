package com.mytests.spring.springresttestclienttest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/")
public class OtherController {

    @GetMapping("/test0")
    public String test0() {
        return "test0";
    }
}
