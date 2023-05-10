package com.example.eafit.controllers;

import com.example.eafit.model.User;
import com.example.eafit.model.exceptions.BusinessException;
import com.example.eafit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
    @PostMapping
    public void crearUsuario(@RequestBody User user){
        try{
            userService.createUser(user);
        } catch (BusinessException e) {
            //RESPONDER EL CÓDIGO 400 + EL MENSAJE DE LA EXCEPTCIÓN
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("email/{email}")
    public User getUserByEmail(@PathVariable String email) throws BusinessException {
        User userFound = null;
        try {
            userFound = userService.getByEmail(email);
        } catch (BusinessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return userFound;
    }

    @GetMapping("name/{name}")
    public List<User> getUserByName(@PathVariable String name) throws BusinessException {
        List usersFound = null;
        try {
            usersFound = userService.getByName(name);
        } catch (BusinessException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return usersFound;
    }

    @GetMapping("/allusers")
    public List<User> getAllUsers(){
        List<User> userList = userService.getAllUsers();
        return userList;
    }

    @DeleteMapping("/deleteuser/{email}")
    public void deleteUser(@PathVariable String email) {
        try {
            userService.deleteUser(email);
        } catch (BusinessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/updateuser/{email}")
    public void updateUser(@PathVariable String email,@RequestBody User userToUpdate) throws BusinessException{
        try {
            userService.updateUser(email, userToUpdate);
        } catch (BusinessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
