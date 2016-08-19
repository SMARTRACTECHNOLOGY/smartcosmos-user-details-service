package net.smartcosmos.cluster.userdetails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
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

import net.smartcosmos.cluster.userdetails.domain.AuthenticateDetails;
import net.smartcosmos.cluster.userdetails.domain.AuthenticateRequest;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = { UserDetailsService.class })
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
        MockitoAnnotations.initMocks(getClass());

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

        final String accountUrn = "urn:account:uuid:53f452c2-5a01-44fd-9956-3ecff7c32b30";
        final String userUrn = "urn:user:uuid:53f452c2-5a01-44fd-9956-3ecff7c32b30";
        final String username = "user";

        AuthenticateDetails details = AuthenticateDetails.builder()
            .grantType("password")
            .scope("read")
            .username("user")
            .build();

        AuthenticateRequest request = AuthenticateRequest.builder()
            .authorities(new ArrayList<>())
            .authenticated(false)
            .principal("user")
            .credentials("password")
            .name("user")
            .details(details)
            .build();

        MvcResult mvcResult = this.mockMvc.perform(
            post("/authenticate")
                .content(this.json(request))
                .contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.tenantUrn", is(accountUrn)))
            .andExpect(jsonPath("$.userUrn", is(userUrn)))
            .andExpect(jsonPath("$.username", is(username)))
            .andExpect(jsonPath("$.passwordHash", not(isEmptyOrNullString())))
            .andExpect(jsonPath("$.authorities", hasSize(11)))
            .andReturn();
    }
}
