package ru.minusd.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.minusd.security.domain.dto.PersonalDTO;
import ru.minusd.security.domain.dto.PrivilegesDTO;
import ru.minusd.security.domain.model.Personal;
import ru.minusd.security.domain.model.Privilege;
import ru.minusd.security.domain.model.User;
import ru.minusd.security.repository.PersonalRepository;
import ru.minusd.security.repository.PrivilegeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class PersonalService {
     private final UserService userService;
     private final PersonalRepository personalRepository;
     private final PrivilegeService privilegeService;

    public PersonalDTO personalDTOfind(String username) {
        User user = userService.getByUsername(username);
        System.out.println(user.toString());
        Optional<Personal> personal = personalRepository.findById(user.getPersonal_id());
        final Personal person = personal.get();
        System.out.println(person.getId());
        List<PrivilegesDTO> privileges = privilegeService.findByPrivilege(person.getId());
        List <String> privilegesList = new ArrayList();
        for(PrivilegesDTO pr : privileges){
            privilegesList.add(pr.getPrivilege_name());
        }
        System.out.println(privileges);
        var personalDTO = PersonalDTO.builder()
                .id(person.getId())
                .personal_name(person.getName())
                .personal_email(person.getPersonal_email())
                .privileges(privilegesList)
                .build();

        return personalDTO;
    }
}
