package me.xuling.train.unittest.service;

import me.xuling.train.unittest.model.User;

/**
 * @since 2019-03-29
 */
public interface UserService {

    User findUserById(String id);
}
