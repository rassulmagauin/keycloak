package kz.bitlab.middle02.keycloak.client;
import jakarta.ws.rs.core.Response;
import kz.bitlab.middle02.keycloak.dto.CreateUserDTO;
import kz.bitlab.middle02.keycloak.dto.UserLoginDTO;
import kz.bitlab.middle02.keycloak.dto.UserTokenDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakClient {

    private final Keycloak keycloak;
    private final RestTemplate restTemplate;

    @Value("${keycloak.url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    public UserRepresentation createUser(CreateUserDTO createUserDTO) {
        UserRepresentation user = getUserRepresentation(createUserDTO);

        Response response = keycloak.realm(realm).users().create(user);

        if (response.getStatus() != HttpStatus.CREATED.value()) {
            log.error("Failed to create user in keycloak: {}", response.getStatus());
            throw new RuntimeException("Failed to create user in keycloak, status: " + response.getStatus());
        }

        List<UserRepresentation> userList = keycloak.realm(realm).users().search(createUserDTO.getUsername());
        return userList.get(0);

    }

    private static UserRepresentation getUserRepresentation(CreateUserDTO createUserDTO) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(createUserDTO.getUsername());
        user.setEmail(createUserDTO.getEmail());
        user.setFirstName(createUserDTO.getFirstName());
        user.setLastName(createUserDTO.getLastName());
        user.setEnabled(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(createUserDTO.getPassword());
        credential.setTemporary(false);
        user.setCredentials(List.of(credential));
        return user;
    }

    public UserTokenDTO signIn(UserLoginDTO userLoginDTO) {
        String endpointUrl = serverUrl + "/realms/" + realm + "/protocol/openid-connect/token";
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("username", userLoginDTO.getUsername());
        formData.add("password", userLoginDTO.getPassword());
        formData.add("grant_type", "password");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        ResponseEntity<Map> response = restTemplate.postForEntity(endpointUrl,new HttpEntity<>(formData, headers), Map.class);

        Map<String, Object> responseBody = response.getBody();
        if (!response.getStatusCode().is2xxSuccessful() || responseBody == null) {
            log.error("Failed to sign in user: {}", response.getStatusCode());
            throw new RuntimeException("Failed to sign in user, status: " + response.getStatusCode());
        }

        UserTokenDTO userTokenDTO = new UserTokenDTO();
        userTokenDTO.setAccessToken((String) responseBody.get("access_token"));
        userTokenDTO.setRefreshToken((String) responseBody.get("refresh_token"));
        return userTokenDTO;








    }
}
