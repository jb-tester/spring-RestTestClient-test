package com.mytests.spring.springresttestclienttest;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/")
public class FooController {

    @RequestMapping(path = "/test1")
    public Map<String, Object> requestMethodTests(HttpServletRequest request, @RequestParam String reqparam) {

        return Map.of(
                "method", request.getMethod(),
                "uri", request.getRequestURI(),
                "reqparam", reqparam
        );
    }
    @GetMapping(path = { "/test2/{id}-{name}"})
    public Map<String, String> withPathVarsTests(@PathVariable String id, @PathVariable String name) {

        return Map.of(
                "id", id,
                "name", name
        );
    }

    @PostMapping("/test3")
    public String postTest3(@RequestBody String body) {
        return body + " was passed";
    }
}
