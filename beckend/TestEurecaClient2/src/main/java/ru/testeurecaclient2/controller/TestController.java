package ru.testeurecaclient2.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.testeurecaclient2.model.Test;
import ru.testeurecaclient2.service.TestService;

import java.util.Optional;

@RestController
@RequestMapping("/test2")
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;


    @GetMapping("/{id}")
    public Optional<Test> test(@PathVariable Long id) {
        return testService.findById(id);

    }

    @GetMapping("/reg")
    @Operation(summary = "Доступен только авторизованным пользователям")
    public String example() {
        return "Hello, REg!";
    }
}
