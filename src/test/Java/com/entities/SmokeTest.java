package com.entities;

import static org.assertj.core.api.Assertions.assertThat;

import com.controller.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmokeTest {

    @Autowired
    private AccountController accountCon;
    @Autowired
    private ProductController productCon;
    @Autowired
    private ReviewRestController reviewRestCon;
    @Autowired
    private UsersController usersCon;
    @Autowired
    private ViewController viewCon;

    @Test
    public void accountControllerContextLoads() throws Exception {
        assertThat(accountCon).isNotNull();
    }

    @Test
    public void productControllerContextLoads() throws Exception {
        assertThat(productCon).isNotNull();
    }

    @Test
    public void reviewRestControllerContextLoads() throws Exception {
        assertThat(reviewRestCon).isNotNull();
    }

    @Test
    public void usersControllerContextLoads() throws Exception {
        assertThat(usersCon).isNotNull();
    }

    @Test
    public void viewControllerContextLoads() throws Exception {
        assertThat(viewCon).isNotNull();
    }
}