package net.smartcosmos.cluster.userdetails.service;

import java.util.Arrays;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import net.smartcosmos.cluster.userdetails.config.UserAuthenticationProperties;
import net.smartcosmos.cluster.userdetails.domain.UserDetails;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceDevelopmentTest {

    @Mock
    ConversionService conversionService;

    @Spy
    UserAuthenticationProperties userAuthenticationProperties;

    @Mock
    UserDetails mockUserDetails;

    @Spy
    Validator validator = Validation.buildDefaultValidatorFactory()
        .getValidator();

    @InjectMocks
    UserDetailsServiceDevelopment userDetailsService;

    @After
    public void tearDown() {

        reset(conversionService, validator);
    }

    @Test
    public void thatMockingWorks() {

        assertNotNull(conversionService);
        assertNotNull(userAuthenticationProperties);
        assertNotNull(userDetailsService);
    }

    // region getUserDetails()

    @Test
    public void thatGetUserDetailsSucceeds() throws Exception {

        final String expectedUsername = "user";
        final String expectedPassword = "password";

        when(conversionService.convert(eq(userAuthenticationProperties), eq(UserDetails.class))).thenReturn(mockUserDetails);

        UserDetails userDetails = userDetailsService.getUserDetails(expectedUsername, expectedPassword);

        assertNotNull(userDetails);
        assertEquals(mockUserDetails, userDetails);
    }

    @Test(expected = BadCredentialsException.class)
    public void thatGetUserDetailsInvalidPasswordFails() throws Exception {

        final String expectedUsername = "user";
        final String expectedPassword = "invalidPassword";

        userDetailsService.getUserDetails(expectedUsername, expectedPassword);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void thatUnknownUserFails() throws Exception {

        final String expectedUsername = "invalidUser";
        final String expectedPassword = "invalidPassword";

        userDetailsService.getUserDetails(expectedUsername, expectedPassword);
    }

    @Test(expected = IllegalArgumentException.class)
    public void thatNullPasswordFailsImmediately() throws Exception {

        userDetailsService.getUserDetails("user", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void thatBlankPasswordFailsImmediately() throws Exception {

        userDetailsService.getUserDetails("user", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void thatNullUserFailsImmediately() throws Exception {

        userDetailsService.getUserDetails(null, "password");
    }

    @Test(expected = IllegalArgumentException.class)
    public void thatBlankUserFailsImmediately() throws Exception {

        userDetailsService.getUserDetails("", "password");
    }

    // endregion

    // region isValid()

    @Test
    public void thatValidationSucceeds() {

        final UserDetails user = UserDetails.builder()
            .userUrn("someUserUrn")
            .tenantUrn("someTenantUrn")
            .username("someUsername")
            .authorities(Arrays.asList("authority1", "authority2"))
            .build();

        assertTrue(userDetailsService.isValid(user));
    }

    @Test
    public void thatValidationMissingUserUrnFails() {

        final UserDetails user = UserDetails.builder()
            .tenantUrn("someTenantUrn")
            .username("someUsername")
            .authorities(Arrays.asList("authority1", "authority2"))
            .build();

        assertFalse(userDetailsService.isValid(user));
    }

    @Test
    public void thatValidationMissingTenantUrnFails() {

        final UserDetails user = UserDetails.builder()
            .userUrn("someUserUrn")
            .username("someUsername")
            .authorities(Arrays.asList("authority1", "authority2"))
            .build();

        assertFalse(userDetailsService.isValid(user));
    }

    @Test
    public void thatValidationMissingUsernameFails() {

        final UserDetails user = UserDetails.builder()
            .userUrn("someUserUrn")
            .tenantUrn("someTenantUrn")
            .authorities(Arrays.asList("authority1", "authority2"))
            .build();

        assertFalse(userDetailsService.isValid(user));
    }

    @Test
    public void thatValidationMissingAuthoritiesSucceeds() {

        final UserDetails user = UserDetails.builder()
            .userUrn("someUserUrn")
            .tenantUrn("someTenantUrn")
            .username("someUsername")
            .build();

        assertTrue(userDetailsService.isValid(user));
    }

    // endregion
}
