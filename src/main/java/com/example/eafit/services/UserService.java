package com.example.eafit.services;

import com.example.eafit.model.User;
import com.example.eafit.model.exceptions.BusinessException;

import java.util.List;

public interface UserService {

    void createUser(User user) throws BusinessException;
    User getByEmail(String email) throws BusinessException;
    void deleteUser(String email) throws BusinessException;
    void updateUser(String email, User user) throws BusinessException;
    List<User> getByName(String name) throws BusinessException;
    List<User> getAllUsers();
}
