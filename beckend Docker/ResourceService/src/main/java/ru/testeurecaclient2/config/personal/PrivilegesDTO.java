package ru.testeurecaclient2.config.personal;

import lombok.*;

import java.util.List;
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrivilegesDTO {

    public List<String> privileges;
}
