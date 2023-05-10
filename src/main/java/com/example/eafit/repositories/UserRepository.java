package com.example.eafit.repositories;

import com.example.eafit.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.example.eafit.model.exceptions.BusinessException;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final String FILE_PATH = "C:\\Users\\bryan\\Downloads\\registro-usuarios-api\\registro-usuarios-api\\src\\main\\resources\\user_database.txt";
    //ACCEDER A UN ARCHIVO TXT PARA PERSISTIR LA INFORMACIÓN DE LOS USUARIOS, UN USUARIO POR LINEA
    //Y CADA PROPIEDAD SEPARADA POR COMA

    public List<User> getAllUsersFromDatabase(){
        try {
            List<User> users = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            String line;
            while((line = reader.readLine()) != null){
                String[] fields = line.split(",");
                User user = new User(fields[1], fields[2], fields[3]);
                user.setId(fields[0]);
                user.setActive(Boolean.valueOf(fields[4]));
                users.add(user);
            }
            return users;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public User saveUser(User user){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true));
            String userPropiertiesString = user.getId()+","+user.getUserName()+","+user.getPassword()
                    +","+user.getEmail()+","+user.isActive();
            writer.write(userPropiertiesString);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
            }
        return user;
    }

    public void updateUser(User user) throws BusinessException {
        try {
            List<String> users = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields[0].equals(user.getId())) {
                    fields[1] = user.getUserName();
                    fields[2] = user.getPassword();
                    fields[3] = user.getEmail();
                    fields[4] = String.valueOf(user.isActive());
                    line = String.join(",", fields);
                }
                users.add(line);
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
            for (String newLine : users) {
                writer.write(newLine);
                writer.newLine();
            }
            writer.close();
        } catch (FileNotFoundException e) {
            throw new BusinessException("Database not found");
        } catch (IOException e) {
            throw new BusinessException("Error updating user");
        }
    }
}
