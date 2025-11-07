package ninja.oxente.cara_suja.presentation.controllers;

import ninja.oxente.cara_suja.config.BaseTestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PresentationBaseIntegrationTest extends BaseTestConfig {

    @LocalServerPort
    protected int port;

    @Autowired
    protected TestRestTemplate restTemplate;

}
