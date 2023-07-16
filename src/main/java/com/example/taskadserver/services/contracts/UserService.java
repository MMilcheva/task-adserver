package com.example.taskadserver.services.contracts;

import com.example.taskadserver.models.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User getUserById(Long id);

    User getUserByUsername(String username);

}
