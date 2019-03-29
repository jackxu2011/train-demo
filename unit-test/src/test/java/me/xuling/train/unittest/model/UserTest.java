package me.xuling.train.unittest.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @since 2019-03-29
 */
class UserTest {

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void serializableTest() {

        String expiredJson = "{\"username\":\"username\", \"password\": \"password\"}";

        User user = User.builder()
                .username("username")
                .password("password")
                .build();
        try {
            String jsonString = mapper.writeValueAsString(user);
            assertThatJson(jsonString).isEqualTo(expiredJson);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
            fail("User serializable fail");
        }

        try {
            User deUser = mapper.readValue(expiredJson, User.class);
            assertThat(deUser).isEqualTo(user);
        } catch (IOException e) {
            e.printStackTrace();
            fail("User Deserializable fail");
        }

    }
}