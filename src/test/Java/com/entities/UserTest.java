package com.entities;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private RepoManager manager;//this MUST be wired created somewhere for the entity classes to work.

    @Test
    public void createProduct() {
        User alice = new User("Alice").save();
        Product apple = alice.createProduct("apple.com", "Makes phones");

        assertTrue(apple.getUrl().equals("apple.com"));
        assertTrue(apple.getDescription().equals("Makes phones"));
        assertTrue(alice.getCreatedProducts().contains(apple));
        assertTrue(apple.getCreator().equals(alice));
    }

    @Test
    public void writeReview() {
        User tom = new User("Tom").save();
        Product google = tom.createProduct("google.com", "a dns provider");
        Review r = tom.writeReview(google, "ehh..", 1d/2d);

        assertTrue(r.getProduct().equals(google));
        assertTrue(r.getScore() == 1d/2d);
        assertTrue(r.getDescription().equals("ehh.."));
        assertTrue(r.getWriter().equals(tom));
        assertTrue(tom.getWritenReviews().contains(r));
        assertTrue(r.getProduct().equals(google));
        assertTrue(google.getReviews().contains(r));
    }

    @Test
    public void follow() {
        User alice = new User("Alice").save();
        User bob = new User("Bob").save();
        User eve = new User("Eve").save();

        alice.follow(bob);

        assertTrue(alice.getFollowedUsers().contains(bob));
        assertFalse(bob.getFollowedUsers().contains(alice));
        assertFalse(eve.getFollowedUsers().contains(alice));
        assertFalse(eve.getFollowedUsers().contains(bob));
        assertFalse(bob.getFollowedUsers().contains(eve));
        assertFalse(alice.getFollowedUsers().contains(eve));
    }

    @Test
    public void jaccardDistance() {
        User john = new User("John").save();
        User alice = new User("Alice").save();
        User bob = new User("Bob").save();
        User eve = new User("Eve").save();

        Product a = john.createProduct("a.com", "aaa");
        Product b = john.createProduct("b.com", "bbb");
        Product c = john.createProduct("c.com", "ccc");
        Product d = john.createProduct("d.com", "ddd");

        alice.writeReview(b, "a", 1d/5d);
        alice.writeReview(c, "a", 1d/5d);
        alice.writeReview(d, "a", 1d/5d);

        bob.writeReview(a, "a", 2d/5d);
        bob.writeReview(c, "a", 2d/5d);
        bob.writeReview(d, "a", 2d/5d);

        eve.writeReview(a, "a", 4d/5d);
        eve.writeReview(b, "a", 4d/5d);
        eve.writeReview(d, "a", 4d/5d);

        //instead of specific values, we are testing for relationships
        assertTrue(alice.jaccardDistance(bob) < alice.jaccardDistance(eve));
        assertTrue(bob.jaccardDistance(alice) < bob.jaccardDistance(eve));
        assertTrue(eve.jaccardDistance(alice) > eve.jaccardDistance(bob));

        assertTrue(Math.abs(alice.jaccardDistance(bob) - bob.jaccardDistance(alice))<0.01);//jaccard distance should be reflexive

    }

    @Test
    public void baconDistance() {
        List<User> users = new ArrayList<>();
        String[] names = {"Adam","Boy","Charles","David","Edward","Frank","George","Henry"};
        for(String name : names){
            users.add(new User(name).save());
        }

        for(int i = users.size()-1; i>0; i--){
            users.get(i-1).follow(users.get(i));
        }

        assertTrue(users.get(0).baconDistance(users.get(0)) == 0);
        assertTrue(users.get(0).baconDistance(users.get(1)) == 1);
        assertTrue(users.get(0).baconDistance(users.get(2)) == 2);
        assertTrue(users.get(0).baconDistance(users.get(3)) == 3);
        assertTrue(users.get(0).baconDistance(users.get(4)) == 4);
        assertTrue(users.get(0).baconDistance(users.get(5)) == 5);
        assertTrue(users.get(0).baconDistance(users.get(6)) == 6);
        assertTrue(users.get(0).baconDistance(users.get(7)) == null);//null because 7 is greater than 6, id bacon distance caps out at 6 to limit computation time
        assertTrue(users.get(3).baconDistance(users.get(0)) == null);//there is no way to navigate from 3 to 0, so their distance is undefined
    }

    @Test
    public void equalsNotFalsePositive() {
        User tim = new User("Tim").save();
        User arthur = new User("Arthur").save();

        assertFalse(tim.equals(arthur));
    }

    @Test
    public void equalsNotFalseNegative() {
        User original = new User("Bill").save();
        User copy = RepoManager.getUserRepository().findById(original.getId()).orElse(null);

        assertTrue(original.equals(copy));
    }
}