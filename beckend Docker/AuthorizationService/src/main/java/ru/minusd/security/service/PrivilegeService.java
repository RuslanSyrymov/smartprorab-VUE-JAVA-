package ru.minusd.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.minusd.security.domain.dto.PrivilegesDTO;
import ru.minusd.security.domain.model.Privilege;
import ru.minusd.security.repository.PrivilegeRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PrivilegeService {

    private  final PrivilegeRepository privilegeRepository;

    public List<PrivilegesDTO> findByPrivilege(Long personal_id){
        return privilegeRepository.findByPrivilege(personal_id);
    }
}
