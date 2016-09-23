package net.smartcosmos.cluster.userdetails.resource;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.smartcosmos.cluster.userdetails.domain.AuthenticateUserRequest;
import net.smartcosmos.cluster.userdetails.service.AuthenticateUserService;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@Slf4j
public class UserDetailsResource {

    private final AuthenticateUserService service;

    @Autowired
    public UserDetailsResource(AuthenticateUserService authenticateUserService) {

        service = authenticateUserService;
    }

    @RequestMapping(value = "authenticate",
                    method = RequestMethod.POST,
                    produces = APPLICATION_JSON_UTF8_VALUE,
                    consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> authenticate(@RequestBody @Valid AuthenticateUserRequest requestBody) {

        return service.authenticateUser(requestBody);
    }
}
