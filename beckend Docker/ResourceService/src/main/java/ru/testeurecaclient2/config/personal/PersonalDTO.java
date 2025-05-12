package ru.testeurecaclient2.config.personal;


import lombok.*;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PersonalDTO  {

    private Long id;

    private String personal_name;

    private String personal_email;

    public List<String> privileges;
}