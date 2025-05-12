package ru.minusd.security.repository;

import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.minusd.security.domain.dto.PersonalDTO;
import ru.minusd.security.domain.model.Personal;
import ru.minusd.security.domain.model.User;
import java.util.Optional;

@Repository
public interface PersonalRepository extends JpaRepository<Personal, Long> {
    Optional <Personal> findById(Long id);
}
