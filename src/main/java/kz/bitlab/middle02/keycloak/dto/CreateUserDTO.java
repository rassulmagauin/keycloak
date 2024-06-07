package kz.bitlab.middle02.keycloak.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
