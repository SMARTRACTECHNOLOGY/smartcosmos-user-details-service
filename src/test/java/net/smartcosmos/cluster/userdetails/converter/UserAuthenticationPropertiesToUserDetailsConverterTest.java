package net.smartcosmos.cluster.userdetails.converter;

import java.util.Arrays;
import java.util.Collection;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import net.smartcosmos.cluster.userdetails.config.UserAuthenticationProperties;
import net.smartcosmos.userdetails.domain.UserDetails;

import static java.util.Collections.emptySet;
import static org.junit.Assert.*;
import static org.mockito.Mockito.reset;

import static net.smartcosmos.cluster.userdetails.config.UserAuthenticationProperties.DEFAULT_AUTHORITIES;
import static net.smartcosmos.cluster.userdetails.config.UserAuthenticationProperties.DEFAULT_NAME;
import static net.smartcosmos.cluster.userdetails.config.UserAuthenticationProperties.DEFAULT_PASSWORD;
import static net.smartcosmos.cluster.userdetails.config.UserAuthenticationProperties.DEFAULT_TENANT_URN;
import static net.smartcosmos.cluster.userdetails.config.UserAuthenticationProperties.DEFAULT_USER_URN;

@RunWith(MockitoJUnitRunner.class)
public class UserAuthenticationPropertiesToUserDetailsConverterTest {

    @Spy
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @InjectMocks
    UserAuthenticationPropertiesToUserDetailsConverter converter;

    @After
    public void tearDown() {

        reset(passwordEncoder);
    }

    @Test
    public void thatMockingWorks() {

        assertNotNull(passwordEncoder);
        assertNotNull(converter);
    }

    @Test
    public void thatConvertSucceedsForDefaultProperties() {

        final String expectedUsername = DEFAULT_NAME;
        final String expectedPassword = DEFAULT_PASSWORD;
        final String expectedUserUrn = DEFAULT_USER_URN;
        final String expectedTenantUrn = DEFAULT_TENANT_URN;
        final Collection<String> expectedAuthorities = DEFAULT_AUTHORITIES;

        final UserAuthenticationProperties source = new UserAuthenticationProperties();

        UserDetails userDetails = converter.convert(source);

        assertNotNull(userDetails);
        assertEquals(expectedUsername, userDetails.getUsername());
        assertTrue(passwordEncoder.matches(expectedPassword, userDetails.getPasswordHash()));
        assertEquals(expectedUserUrn, userDetails.getUserUrn());
        assertEquals(expectedTenantUrn, userDetails.getTenantUrn());
        assertTrue(userDetails.getAuthorities()
                       .containsAll(expectedAuthorities));
    }

    @Test
    public void thatConvertSucceedsForCustomProperties() {

        final String expectedUsername = "someUser";
        final String expectedPassword = "somePassword";
        final String expectedUserUrn = "someUserUrn";
        final String expectedTenantUrn = "someTenantUrn";
        final Collection<String> expectedAuthorities = Arrays.asList("someAuthority", "someOtherAuthority");

        UserAuthenticationProperties source = new UserAuthenticationProperties();
        source.setName(expectedUsername);
        source.setPassword(expectedPassword);
        source.setUserUrn(expectedUserUrn);
        source.setTenantUrn(expectedTenantUrn);
        source.setAuthorities(expectedAuthorities);

        UserDetails userDetails = converter.convert(source);

        assertNotNull(userDetails);
        assertEquals(expectedUsername, userDetails.getUsername());
        assertTrue(passwordEncoder.matches(expectedPassword, userDetails.getPasswordHash()));
        assertEquals(expectedUserUrn, userDetails.getUserUrn());
        assertEquals(expectedTenantUrn, userDetails.getTenantUrn());
        assertTrue(userDetails.getAuthorities()
                       .containsAll(expectedAuthorities));
    }

    @Test
    public void thatConvertReturnsNoDuplicateAuthorities() {

        final Collection<String> inputAuthorities = Arrays.asList("someAuthority", "someAuthority", "someOtherAuthority");
        final Collection<String> expectedAuthorities = Arrays.asList("someAuthority", "someOtherAuthority");

        UserAuthenticationProperties source = new UserAuthenticationProperties();
        source.setAuthorities(inputAuthorities);

        UserDetails userDetails = converter.convert(source);

        assertNotNull(userDetails);
        assertTrue(userDetails.getAuthorities()
                       .containsAll(expectedAuthorities));
    }

    @Test
    public void thatConvertReturnsEmptySetForNullAuthorities() throws Exception {

        final Collection<String> inputAuthorities = null;
        final Collection<String> expectedAuthorities = emptySet();

        UserAuthenticationProperties source = new UserAuthenticationProperties();
        source.setAuthorities(inputAuthorities);

        UserDetails userDetails = converter.convert(source);

        assertNotNull(userDetails);
        assertEquals(expectedAuthorities, userDetails.getAuthorities());
    }
}
