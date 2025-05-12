package ru.testeurecaclient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test1")
@RequiredArgsConstructor
public class TestController2 {

    @GetMapping("/test")
    public String example() {
        return "Hello, controller2!";
    }
}