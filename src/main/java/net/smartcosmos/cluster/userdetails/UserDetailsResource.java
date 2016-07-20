package net.smartcosmos.cluster.userdetails;

import java.io.IOException;
import java.util.Arrays;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.smartcosmos.cluster.userdetails.domain.AuthenticateRequest;
import net.smartcosmos.cluster.userdetails.domain.UserDto;

@RestController
@Slf4j
public class UserDetailsResource {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ObjectMapper objectMapper;

    @RequestMapping(value = "authenticate", method = RequestMethod.POST)
    public UserDto authenticate(@RequestBody @Valid AuthenticateRequest authentication)
                    throws UsernameNotFoundException, IOException {

        log.info("Requested information on username {} with {}", authentication.getName(),
                authentication);

        String passwordHash = null;
        if (StringUtils.isNotBlank(authentication.getCredentials())) {
            String credentials = authentication.getCredentials();
            if (!"password".equals(credentials)) {
                log.error("Password incorrect.");
                throw new BadCredentialsException("Invalid password");
            }
            passwordHash = passwordEncoder.encode(credentials);
        }

        final String accountUrn = "urn:account:uuid:53f452c2-5a01-44fd-9956-3ecff7c32b30";
        final String userUrn = "urn:user:uuid:53f452c2-5a01-44fd-9956-3ecff7c32b30";

        return UserDto.builder().tenantUrn(accountUrn).userUrn(userUrn)
                .username(authentication.getName()).passwordHash(passwordHash)
                .authorities(Arrays.asList("https://authorities.smartcosmos.net/things/read")).build();
    }
}
