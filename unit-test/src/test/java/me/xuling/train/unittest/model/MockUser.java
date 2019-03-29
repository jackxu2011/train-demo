package me.xuling.train.unittest.model;

/**
 * @since 2019-03-29
 */
public class MockUser {

    public static User getUser() {
        return User.builder()
            .username("username")
            .password("password")
            .build();
    }

}
