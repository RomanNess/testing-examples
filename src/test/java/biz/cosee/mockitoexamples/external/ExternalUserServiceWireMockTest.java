package biz.cosee.mockitoexamples.external;


import biz.cosee.mockitoexamples.MockitoExamplesApplication;
import biz.cosee.mockitoexamples.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

public class ExternalUserServiceWireMockTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort());

    private ExternalUserService externalUserService;

    @Before
    public void initializeRetrofitClient() {
        String baseUrl = "http://localhost:" + wireMockRule.port();
        ObjectMapper objectMapper = new MockitoExamplesApplication().globalObjectMapper();
        externalUserService = new ExternalUserService(baseUrl, objectMapper);
        externalUserService.intializeRetrofitClient();
    }

    @Test
    public void wireMockStubbing() {
        stubFor(get(urlEqualTo("/users"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[]")
                )
        );

        List<User> users = externalUserService.getUsers();
        assertThat(users).isNotNull().isEmpty();
    }

    @Test
    public void wireMockWithBodyFromFile() {
        stubFor(get(urlEqualTo("/users"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        // file has to be located at classpath:__files/wiremock/users.json
                        .withBodyFile("wiremock/users.json")
                )
        );

        List<User> users = externalUserService.getUsers();
        assertThat(users).isNotNull().hasSize(2);
    }
}