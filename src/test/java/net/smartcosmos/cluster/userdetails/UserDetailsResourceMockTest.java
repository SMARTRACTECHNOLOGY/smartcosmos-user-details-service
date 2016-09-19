package net.smartcosmos.cluster.userdetails;

import java.util.Collections;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import net.smartcosmos.cluster.userdetails.domain.AuthenticateRequest;
import net.smartcosmos.cluster.userdetails.domain.UserAuthenticationProperties;
import net.smartcosmos.cluster.userdetails.domain.UserDto;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsResourceMockTest {

    @Spy
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Spy
    UserAuthenticationProperties properties = new UserAuthenticationProperties();

    @InjectMocks
    UserDetailsResource resource;

    @Test
    public void thatMockingWorks() {

        assertNotNull(resource);
        assertNotNull(passwordEncoder);
        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder);
    }

    @Test
    public void thatAuthenticateDefaultConfigurationReturnsUserDto() throws Exception {

        final String expectedUsername = "user";
        final String expectedPassword = "password";

        final AuthenticateRequest request = AuthenticateRequest.builder()
            .name(expectedUsername)
            .principal(expectedUsername)
            .credentials(expectedPassword)
            .build();

        UserDto user = resource.authenticate(request);

        assertNotNull(user);
    }

    @Test
    public void thatAuthenticateDefaultConfigurationReturnsDefaultValues() throws Exception {

        final String expectedUsername = "user";
        final String expectedPassword = "password";
        final String expectedTenantUrn = "urn:tenant:uuid:513161AA-24B0-410F-BE1D-D6CD04B6C9BB";
        final String expectedUserUrn = "urn:user:uuid:71266402-55BC-4267-B56E-407BE9778C3B";

        final AuthenticateRequest request = AuthenticateRequest.builder()
            .name(expectedUsername)
            .principal(expectedUsername)
            .credentials(expectedPassword)
            .build();

        UserDto user = resource.authenticate(request);

        assertEquals(expectedUsername, user.getUsername());
        assertEquals(expectedUserUrn, user.getUserUrn());
        assertEquals(expectedTenantUrn, user.getTenantUrn());
        assertEquals(Collections.emptyList(), user.getAuthorities());
    }

    @Test(expected = BadCredentialsException.class)
    public void thatAuthenticateBadCredentialsThrowsException() throws Exception {

        final String expectedUsername = "user";
        final String expectedPassword = "incorrect";

        final AuthenticateRequest request = AuthenticateRequest.builder()
            .name(expectedUsername)
            .principal(expectedUsername)
            .credentials(expectedPassword)
            .build();

        resource.authenticate(request);
    }

    @Test
    public void thatAuthenticateExplicitNullAuthoritiesSucceeds() throws Exception {

        properties = new UserAuthenticationProperties();
        properties.setAuthorities(null);

        resource = new UserDetailsResource(passwordEncoder, properties);

        final String expectedUsername = "user";
        final String expectedPassword = "password";

        final AuthenticateRequest request = AuthenticateRequest.builder()
            .name(expectedUsername)
            .principal(expectedUsername)
            .credentials(expectedPassword)
            .build();

        UserDto user = resource.authenticate(request);
        
        assertEquals(Collections.emptyList(), user.getAuthorities());
    }
}
