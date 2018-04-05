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
    private UsersController usersCon;
    @Autowired
    private ReviewSystemController rsCon;

    @Test
    public void accountControllerContextLoads() throws Exception {
        assertThat(accountCon).isNotNull();
    }

    @Test
    public void productControllerContextLoads() throws Exception {
        assertThat(productCon).isNotNull();
    }

    @Test
    public void userControllerContextLoads() throws Exception {
        assertThat(usersCon).isNotNull();
    }

    @Test
    public void reviewSystemControllerContextLoads() throws Exception {
        assertThat(rsCon).isNotNull();
    }
}