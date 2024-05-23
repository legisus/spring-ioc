package com.codesoft.edu.service;

import java.util.List;

import com.codesoft.edu.model.User;

public interface UserService {
    
    User addUser(User user);

    User updateUser(User user);

    void deleteUser(User user);

    List<User> getAll();

}
