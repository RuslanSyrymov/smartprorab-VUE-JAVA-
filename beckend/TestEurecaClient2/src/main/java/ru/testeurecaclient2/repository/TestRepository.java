package ru.testeurecaclient2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.testeurecaclient2.model.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

}
