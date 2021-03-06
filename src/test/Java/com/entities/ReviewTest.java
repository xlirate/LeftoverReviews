package com.entities;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ReviewTest {

    @Autowired
    private RepoManager manager;//this MUST be wired created somewhere for the entity classes to work.

    @Test
    public void overridingOldReviewsWithNewOnes(){
        User josh = new User("Josh").save();
        Product timms = josh.createProduct("Tim's","timhortons.ca", "coffee");
        Review r = josh.writeReview(timms, "hot", 1d);

        assertTrue(r.getDescription().equals("hot"));

        josh.writeReview(timms, "cold", 0d);
        r = r.getRepo().findById(r.getId()).get();

        assertTrue(r.getDescription().equals("cold"));
    }


    @Test
    public void equalsNotFalseNegative() {
        User bob = new User("Bob").save();
        Product carleton = bob.createProduct("Carleton","carleton.ca", "A school");
        Review original = bob.writeReview(carleton, "ok",3.5d/5d);
        Review copy = original.getRepo().findById(original.getId()).orElse(null);

        assertTrue(original.equals(copy));
    }
}