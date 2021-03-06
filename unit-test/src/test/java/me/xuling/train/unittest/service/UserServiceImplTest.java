package me.xuling.train.unittest.service;

import me.xuling.train.unittest.mapper.UserMapper;
import me.xuling.train.unittest.model.MockUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @since 2019-03-29
 */
class UserServiceImplTest {

    UserMapper userMapper = mock(UserMapper.class);

    @BeforeEach
    void setUp() {
        when(userMapper.selectById("1")).thenReturn(MockUser.getUser());
    }

    @Test
    void findUserById() {
        UserService userService = new UserServiceImpl(userMapper);
        assertThat(userService.findUserById("1")).isEqualTo(MockUser.getUser());
        assertThat(userService.findUserById("99")).isNull();
        verify(userMapper, times(2)).selectById(anyString());
    }
}