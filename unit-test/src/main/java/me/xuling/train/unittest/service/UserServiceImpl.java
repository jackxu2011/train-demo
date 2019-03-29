package me.xuling.train.unittest.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.xuling.train.unittest.mapper.UserMapper;
import me.xuling.train.unittest.model.User;
import org.springframework.stereotype.Service;

/**
 * @since 2019-03-29
 */
@Service
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    @Override
    public User findUserById(String id) {
        return userMapper.selectById(id);
    }
}
