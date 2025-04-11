package ru.minusd.security.domain.dto;

import jakarta.persistence.Column;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrivilegesDTO {
    private String privilege_name;
}
