package net.smartcosmos.cluster.userdetails;

import java.io.IOException;
import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.smartcosmos.cluster.userdetails.domain.UserDto;

@RestController
@Slf4j
public class UserDetailsResource {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ObjectMapper objectMapper;

    @RequestMapping(value = "{username}", method = RequestMethod.POST)
    public UserDto authenticate(@PathVariable("username") String username,
            @RequestBody byte[] requestBody)
                    throws UsernameNotFoundException, IOException {

        final ObjectNode authentication = objectMapper.readValue(requestBody,
                ObjectNode.class);

        log.info("Requested information on username {} with {}", username,
                authentication);

        String passwordHash = null;
        if (authentication.has("credentials")) {
            String credentials = authentication.get("credentials").asText();
            if (!"password".equals(credentials)) {
                log.error("Password incorrect.");
                throw new BadCredentialsException("Invalid password");
            }
            passwordHash = passwordEncoder.encode(credentials);
        }

        final String accountUrn = "urn:account:uuid:53f452c2-5a01-44fd-9956-3ecff7c32b30";
        final String userUrn = "urn:user:uuid:53f452c2-5a01-44fd-9956-3ecff7c32b30";

        return UserDto.builder().tenantUrn(accountUrn).userUrn(userUrn)
                .username(username).passwordHash(passwordHash)
                .authorities(Arrays.asList("https://authorities.smartcosmos.net/things/read")).build();
    }

    /**
     * The GET method only returns the details on the user, removing the password hash
     * component. This is not cached in the authorization server, and is merely a fallback
     * method for the user details service.
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     * @throws IOException
     */
    @RequestMapping(value = "{username}")
    public UserDto authenticate(@PathVariable("username") String username)
            throws UsernameNotFoundException, IOException {

        log.info("Requested information for details only on username {}", username);

        final String accountUrn = "urn:account:uuid:53f452c2-5a01-44fd-9956-3ecff7c32b30";
        final String userUrn = "urn:user:uuid:53f452c2-5a01-44fd-9956-3ecff7c32b30";

        return UserDto.builder().tenantUrn(accountUrn).userUrn(userUrn)
                .username(username).authorities(Arrays.asList("https://authorities.smartcosmos.net/things/read")).build();

    }
}
