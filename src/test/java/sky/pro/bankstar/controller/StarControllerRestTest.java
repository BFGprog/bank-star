package sky.pro.bankstar.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sky.pro.bankstar.service.StarService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StarControllerRestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private StarService starService;

    private String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/star";
    }

    @AfterEach
    public void tearDown() {
        reset(starService);
    }

    @Test
    public void testGetRandomTransactionAmount() {
        UUID userId = UUID.randomUUID();
        int expectedAmount = 100;
        when(starService.getRandomTransactionAmount(userId)).thenReturn(expectedAmount);

        assertResponse(baseUrl + "/transaction?user=" + userId, expectedAmount);
    }

    @Test
    public void testGetUsersInvest500() {
        List<String> expectedUsers = Arrays.asList("user1", "user2");
        when(starService.getUsersInvest500()).thenReturn(expectedUsers);

        assertResponse(baseUrl + "/500", expectedUsers);
    }

    @Test
    public void testGetUsersTopSaving() {
        List<String> expectedUsers = Arrays.asList("user3", "user4");
        when(starService.getUsersTopSaving()).thenReturn(expectedUsers);

        assertResponse(baseUrl + "/saving", expectedUsers);
    }

    @Test
    public void testGetUsersCredit() {
        List<String> expectedUsers = Arrays.asList("user5", "user6");
        when(starService.getUsersCredit()).thenReturn(expectedUsers);

        assertResponse(baseUrl + "/credit", expectedUsers);
    }

    @Test
    public void testGetRandomTransactionAmount_EmptyOptional() {
        UUID userId = UUID.randomUUID();
        when(starService.getRandomTransactionAmount(userId)).thenReturn(0);

        assertResponse(baseUrl + "/transaction?user=" + userId, 0);
    }

    private <T> void assertResponse(String url, T expectedResponse) {
        ResponseEntity<T> response = restTemplate.getForEntity(url, (Class<T>) expectedResponse.getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    private void assertErrorResponse(String url, HttpStatus expectedStatus, String expectedMessage) {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertEquals(expectedStatus, response.getStatusCode());
        assertEquals(expectedMessage, response.getBody());
    }
}