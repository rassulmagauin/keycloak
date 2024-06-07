package kz.bitlab.middle02.keycloak.service;

import kz.bitlab.middle02.keycloak.client.KeycloakClient;
import kz.bitlab.middle02.keycloak.dto.CreateUserDTO;
import kz.bitlab.middle02.keycloak.dto.UserDTO;
import kz.bitlab.middle02.keycloak.dto.UserLoginDTO;
import kz.bitlab.middle02.keycloak.dto.UserTokenDTO;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final KeycloakClient keycloakClient;

    public UserDTO createUser(CreateUserDTO createUserDTO) {
        UserRepresentation userRepresentation = keycloakClient.createUser(createUserDTO);
        UserDTO userDto = new UserDTO();
        userDto.setUsername(userRepresentation.getUsername());
        userDto.setEmail(userRepresentation.getEmail());
        userDto.setFirstName(userRepresentation.getFirstName());
        userDto.setLastName(userRepresentation.getLastName());
        return userDto;
    }

    public UserTokenDTO login(UserLoginDTO userLoginDTO) {
        return keycloakClient.signIn(userLoginDTO);
    }
}
