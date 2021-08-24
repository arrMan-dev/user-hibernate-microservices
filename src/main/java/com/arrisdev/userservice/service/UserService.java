package com.arrisdev.userservice.service;

import com.arrisdev.userservice.model.User;

import java.util.List;

public interface UserService {
    User save(User user);

    User findByUsername(String username);

    List<User> findByUsers(List<Long> idList);
}
