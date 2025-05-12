package ru.testeurecaclient2.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.testeurecaclient2.model.Test;
import ru.testeurecaclient2.repository.TestRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestRepository testRepository;

    public Optional<Test> findById(Long id) {
        return testRepository.findById(id);
    }
}
