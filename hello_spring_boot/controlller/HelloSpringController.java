package com.Quang.hello_spring_boot.controlller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloSpringController {
    @GetMapping("/hello")
    String sayhello(){
        return "hello spring boot 3 , again";
    }

}
