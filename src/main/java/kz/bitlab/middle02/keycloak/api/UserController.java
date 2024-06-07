package kz.bitlab.middle02.keycloak.api;

import kz.bitlab.middle02.keycloak.dto.CreateUserDTO;
import kz.bitlab.middle02.keycloak.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;


    @PostMapping(value="/create")
    public ResponseEntity<?> createUser(@RequestBody CreateUserDTO createUserDTO) {
        return new ResponseEntity<>(userService.createUser(createUserDTO), HttpStatus.CREATED);
    }
}
