package me.xuling.train.unittest.mapper;

import me.xuling.train.unittest.model.User;

/**
 * @since 2019-03-29
 */
public interface UserMapper {

    User selectById(String id);

}
