package com.example.eafit.services;

import com.example.eafit.model.User;
import com.example.eafit.model.exceptions.BusinessException;
import com.example.eafit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImp implements UserService{

    private static UserRepository userRepository; //hay que instanciar el repositorio para poder buscar datos en él
    List<User> databaseUsers;

    @Autowired
    public UserServiceImp(UserRepository userRepository){
        this.userRepository = userRepository;
        databaseUsers = userRepository.getAllUsersFromDatabase();
    }

    @Override
    public void createUser(User user) throws BusinessException{
        //QUE NO EXISTA EL USUARIO CON EL MISMO ID O CON EL MISMO EMAIL
        //longitud y carácteres del especiales de la contraseña
        if(user.getUserName() == null || user.getUserName().isBlank()){
            throw new BusinessException("Username is not valid");
        }
        if(user.getEmail() == null || user.getEmail().isBlank()){
            throw new BusinessException("Email is not valid");
        }
        if(user.getPassword() == null || user.getPassword().isBlank()){
            throw new BusinessException("Password is not valid");
        }

        validateEmail(user);
        userRepository.saveUser(user);
    }

    private void validateEmail(User user) throws BusinessException {
        for(User databaseUser: databaseUsers) {
            if(databaseUser.getEmail().equalsIgnoreCase((user.getEmail()))){
                throw new BusinessException("Email already exists");
            }
        }
    }

    @Override
    public User getByEmail(String email) throws BusinessException{
        User userFound = null;
        for(User databaseUser: databaseUsers){
            if(databaseUser.getEmail().equalsIgnoreCase(email)){
                userFound = databaseUser;
            }
        }
        if(userFound == null){
            throw new BusinessException("Email not found");
        }
        return userFound;
    }

    @Override
    public List<User> getByName(String name) throws BusinessException {
        List<User> listUsersByName = new ArrayList<>();
        User userFound = null;
        for(User databaseUser: databaseUsers){
            if(databaseUser.getUserName().equalsIgnoreCase(name)){
                userFound = databaseUser;
                listUsersByName.add(userFound);
            }
        }
        if(userFound == null){
            throw new BusinessException("Name not found");
        }
        return listUsersByName;
    }

    @Override
    public void deleteUser(String email) throws BusinessException {
        User userToDelete = getByEmail(email);
        if (userToDelete == null) {
            throw new BusinessException("User not found");
        }
        userToDelete.setActive(false);
        userRepository.updateUser(userToDelete);
    }
    @Override
    public void updateUser(String email, User user) throws BusinessException {
        User oldUser = getByEmail(email);
        if (oldUser == null) {
            throw new BusinessException("User not found");
        }
        oldUser.setUserName(user.getUserName());
        oldUser.setEmail(user.getEmail());
        oldUser.setPassword(user.getPassword());
        oldUser.setActive(user.isActive());
        userRepository.updateUser(oldUser);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsersFromDatabase();
    }
}
