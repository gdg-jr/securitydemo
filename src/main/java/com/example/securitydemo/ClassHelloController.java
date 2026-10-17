package com.example.securitydemo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClassHelloController {

    @GetMapping("/hello")
    public String Hello(){
        return  "Hello there!";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String userEndPoint(){
        return  "Hello user!";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminEndPoint(){
        return  "Hello admin!";
    }
}
