package kz.bitlab.middle02.keycloak.api;

import kz.bitlab.middle02.keycloak.dto.UserLoginDTO;
import kz.bitlab.middle02.keycloak.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/login")
public class LoginController {

    private final UserService userService;

    @PostMapping(value="/token")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO) {
        return new ResponseEntity<>(userService.login(userLoginDTO), HttpStatus.OK);
    }



}
