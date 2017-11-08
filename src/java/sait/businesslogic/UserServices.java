/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sait.businesslogic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import sait.dataaccess.UserDB;
import sait.domainmodel.User;

/**
 *
 * @author awarsyle
 */
public class UserServices {

    private UserDB userDB;

    public UserServices(String path) {
        userDB = new UserDB(path);
    }

    public User getUser(String username) throws Exception {

        return userDB.getUser(username);
    }
    
    public ArrayList<User> getUsers() throws Exception{
        return (ArrayList<User>) userDB.getAllUsers();
    }
    public User loging(String username, String password) throws Exception {

        User user = userDB.getUser(username);
        if (user != null) {
            if (!password.equals(user.getPassword())) {
                user = null;
            }
        }
        return user;
    }

    public void updatePassword(User user, String newPassword) throws Exception {
        user.setPassword(newPassword);
        userDB.update(user);
    }

    public boolean addUser(String username, String password) throws IOException, Exception {
        User user = new User(username, password, false);

        boolean isAdded = true;

        ArrayList<User> users = (ArrayList<User>) userDB.getAllUsers();
        if (users.indexOf(user) != -1) {
            isAdded = false;
        } else {
            userDB.addUser(user);
        }
        
        return isAdded;
    }
    
    public boolean removeUser(User user, String admin) throws Exception{
        boolean isRemoved = true;
        
        if(user.getUsername().equals(admin))
            isRemoved = false;
        else
            userDB.removeUser(user);
        
        return isRemoved;
    }
}
