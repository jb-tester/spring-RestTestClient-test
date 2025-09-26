package com.mytests.spring.springresttestclienttest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class FooRoutesConfiguration {

    @Bean
    public RouterFunction<ServerResponse> routes() {
        return
                RouterFunctions.route(RequestPredicates.GET("/test4"), request -> ServerResponse.ok().body("route test4"))
                        .andRoute(RequestPredicates.GET("/test5"), request -> ServerResponse.ok().body("route test5"));
    }

}
