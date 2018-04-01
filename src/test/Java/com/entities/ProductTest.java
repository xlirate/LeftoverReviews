package com.entities;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProductTest {


    @Autowired
    private RepoManager manager;//this MUST be wired created somewhere for the entity classes to work.

    @Test
    public void equalsNotFalsePositive() {
        User tom = new User("Tom").save();
        Product google = tom.createProduct("google.com", "A search tool");
        Product carleton = tom.createProduct("carleton.ca", "A school");

        assertFalse(google.equals(carleton));
    }

    @Test
    public void equalsNotFalseNegative() {
        User jill = new User("Jill").save();
        Product original = jill.createProduct("youtube.com", "video site");
        Product copy = original.getRepo().findById(original.getId()).orElse(null);

        assertTrue(original.equals(copy));
    }
}