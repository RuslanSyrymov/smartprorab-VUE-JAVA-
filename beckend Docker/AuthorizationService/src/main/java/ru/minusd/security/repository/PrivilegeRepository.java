package ru.minusd.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.minusd.security.domain.dto.PrivilegesDTO;
import ru.minusd.security.domain.model.Personal;
import ru.minusd.security.domain.model.Privilege;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    @Query(value = "SELECT privilege FROM personal_privilege WHERE personal_id = :value", nativeQuery = true)
    List<PrivilegesDTO> findByPrivilege(@Param("value") Long value);
}

