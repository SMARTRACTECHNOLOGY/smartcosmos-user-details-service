package net.smartcosmos.cluster.userdetails.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.internal.matchers.GreaterThan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import net.smartcosmos.cluster.userdetails.DevelopmentUserDetailsService;
import net.smartcosmos.cluster.userdetails.domain.AuthenticateDetails;
import net.smartcosmos.cluster.userdetails.domain.AuthenticateUserRequest;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static net.smartcosmos.cluster.userdetails.domain.MessageResponse.CODE_ERROR;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = { DevelopmentUserDetailsService.class })
@ActiveProfiles("test")
@ComponentScan
public class UserDetailsResourceTest {

    // region setup

    @Autowired
    protected WebApplicationContext webApplicationContext;
    protected MockMvc mockMvc;
    protected HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays
            .stream(converters)
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .get();

        Assert.assertNotNull("the JSON message converter must not be null",
                             this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .apply(springSecurity())
            .build();
    }

    protected String json(Object o) throws IOException {

        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON,
                                                       mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    // endregion

    @Test
    public void thatAuthenticateSucceeds() throws Exception {

        final String tenantUrn = "urn:tenant:uuid:DAF0D088-75A5-4C65-B331-24F26A30A331";
        final String userUrn = "urn:user:uuid:6E3718FA-3DDD-4079-89C4-D401FAC78CA1";
        final String usernameUnderTest = "jules";
        final String passwordUnderTest = "hotpassword";
        final String grantType = "credentials";
        final String requestScope = "read";

        AuthenticateDetails details = AuthenticateDetails.builder()
            .grantType(grantType)
            .scope(requestScope)
            .username(usernameUnderTest)
            .build();

        AuthenticateUserRequest request = AuthenticateUserRequest.builder()
            .authorities(new ArrayList<>())
            .authenticated(false)
            .principal(usernameUnderTest)
            .credentials(passwordUnderTest)
            .name(usernameUnderTest)
            .details(details)
            .build();

        MvcResult mvcResult = this.mockMvc.perform(
            post("/authenticate")
                .content(this.json(request))
                .contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.tenantUrn", is(tenantUrn)))
            .andExpect(jsonPath("$.userUrn", is(userUrn)))
            .andExpect(jsonPath("$.username", is(usernameUnderTest)))
            .andExpect(jsonPath("$.passwordHash", not(isEmptyOrNullString())))
            .andExpect(jsonPath("$.authorities", hasSize(new GreaterThan<>(0))))
            .andReturn();
    }

    @Test
    public void thatMissingUsernameReturnsBadRequest() throws Exception {

        final String usernameUnderTest = null;
        final String passwordUnderTest = "hotpassword";
        final String grantType = "credentials";
        final String requestScope = "read";

        AuthenticateDetails details = AuthenticateDetails.builder()
            .grantType(grantType)
            .scope(requestScope)
            .username(usernameUnderTest)
            .build();

        AuthenticateUserRequest request = AuthenticateUserRequest.builder()
            .authorities(new ArrayList<>())
            .authenticated(false)
            .principal(usernameUnderTest)
            .credentials(passwordUnderTest)
            .name(usernameUnderTest)
            .details(details)
            .build();

        MvcResult mvcResult = this.mockMvc.perform(
            post("/authenticate")
                .content(this.json(request))
                .contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.code", is(CODE_ERROR)))
            .andExpect(jsonPath("$.message").isString())
            .andReturn();
    }

    @Test
    public void thatEmptyUsernameReturnsBadRequest() throws Exception {

        final String usernameUnderTest = "";
        final String passwordUnderTest = "hotpassword";
        final String grantType = "credentials";
        final String requestScope = "read";

        AuthenticateDetails details = AuthenticateDetails.builder()
            .grantType(grantType)
            .scope(requestScope)
            .username(usernameUnderTest)
            .build();

        AuthenticateUserRequest request = AuthenticateUserRequest.builder()
            .authorities(new ArrayList<>())
            .authenticated(false)
            .principal(usernameUnderTest)
            .credentials(passwordUnderTest)
            .name(usernameUnderTest)
            .details(details)
            .build();

        MvcResult mvcResult = this.mockMvc.perform(
            post("/authenticate")
                .content(this.json(request))
                .contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.code", is(CODE_ERROR)))
            .andExpect(jsonPath("$.message").isString())
            .andReturn();
    }

    @Test
    public void thatMissingPasswordReturnsBadRequest() throws Exception {

        final String usernameUnderTest = "jules";
        final String passwordUnderTest = null;
        final String grantType = "credentials";
        final String requestScope = "read";

        AuthenticateDetails details = AuthenticateDetails.builder()
            .grantType(grantType)
            .scope(requestScope)
            .username(usernameUnderTest)
            .build();

        AuthenticateUserRequest request = AuthenticateUserRequest.builder()
            .authorities(new ArrayList<>())
            .authenticated(false)
            .principal(usernameUnderTest)
            .credentials(passwordUnderTest)
            .name(usernameUnderTest)
            .details(details)
            .build();

        MvcResult mvcResult = this.mockMvc.perform(
            post("/authenticate")
                .content(this.json(request))
                .contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.code", is(CODE_ERROR)))
            .andExpect(jsonPath("$.message").isString())
            .andReturn();
    }

    @Test
    public void thatEmptyPasswordReturnsBadRequest() throws Exception {

        final String usernameUnderTest = "jules";
        final String passwordUnderTest = "";
        final String grantType = "credentials";
        final String requestScope = "read";

        AuthenticateDetails details = AuthenticateDetails.builder()
            .grantType(grantType)
            .scope(requestScope)
            .username(usernameUnderTest)
            .build();

        AuthenticateUserRequest request = AuthenticateUserRequest.builder()
            .authorities(new ArrayList<>())
            .authenticated(false)
            .principal(usernameUnderTest)
            .credentials(passwordUnderTest)
            .name(usernameUnderTest)
            .details(details)
            .build();

        MvcResult mvcResult = this.mockMvc.perform(
            post("/authenticate")
                .content(this.json(request))
                .contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.code", is(CODE_ERROR)))
            .andExpect(jsonPath("$.message").isString())
            .andReturn();
    }
}
