package com.codesoft.edu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.codesoft.edu.model.User;
import com.codesoft.edu.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private List<User> users;

    public UserServiceImpl() {
        users = new ArrayList<>();
    }

    @Override
    public User addUser(User user) {
        if (user.getEmail() == null) {
            throw new IllegalArgumentException("User email cannot be null");
        }

        for (User currentUser : users) {
            if (user.getEmail().equals(currentUser.getEmail())) {
                return null;
            }
        }
        users.add(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(user.getEmail())) {
                users.set(i, user);
                return user;
            }
        }
        return null;
    }

    @Override
    public void deleteUser(User user) {
        users.removeIf(u -> u.getEmail().equals(user.getEmail()));
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users);
    }

}
