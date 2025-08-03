package kakeibo_app_java.kakeibo_app_java.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    
    @GetMapping("/hello")
    public String sayHello(){
        return "Hello World";
    }
}
