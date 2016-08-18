package net.smartcosmos.cluster.userdetails;

import java.util.ArrayList;

import org.junit.*;
import org.springframework.test.web.servlet.MvcResult;

import net.smartcosmos.cluster.userdetails.domain.AuthenticateDetails;
import net.smartcosmos.cluster.userdetails.domain.AuthenticateRequest;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserDetailsResourceTest extends AbstractTestResource {

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
