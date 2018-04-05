package com.entities;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.File;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void accountDisplayShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/account",
                String.class)).contains("account");
    }

    @Test
    public void loginDisplayShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/account/login",
                String.class)).contains("account");
    }

    @Test
    public void registerDisplayShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/account/register",
                String.class)).contains("account");
    }

    @Test
    public void homeDisplayShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/home",
                String.class)).contains("Product Review System");
    }

    @Test
    public void defaultDisplayShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
                String.class)).contains("Product Review System");
    }

    @Test
    public void productListDisplayShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/products",
                String.class)).contains("productList");
    }

    /*@Test
    public void productDisplayShouldReturnDefaultMessage() throws Exception {
        User tom = new User("Tom").save();
        Product google = tom.createProduct("google.com", "A search tool").save();

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/products/"+google.getId(),
                String.class)).contains("The product ID is: " + google.getId());
    }*/

    @Test
    public void userDisplayDisplayShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/user",
                String.class)).contains("user");
    }

    @Test
    public void allUserDisplayDisplayShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/allusers",
                String.class)).contains("allusers");
    }
}