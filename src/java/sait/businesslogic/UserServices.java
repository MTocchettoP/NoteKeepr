/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sait.businesslogic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sait.dataaccess.NotesDBException;
import sait.dataaccess.UserDB;
import sait.domainmodel.Company;
import sait.domainmodel.Role;
import sait.domainmodel.User;

/**
 *
 * @author awarsyle
 */
public class UserServices {

    private UserDB userDB;

    public UserServices() {
        userDB = new UserDB();
    }

    public User getUser(String username) throws Exception {

        return userDB.getUser(username);
    }

    public ArrayList<User> getUsers() throws Exception {
        return new ArrayList<User>(userDB.getAllUsers());
    }

    public User loging(String username, String password) throws Exception {

        User user = userDB.getUser(username);
        if (user != null) {
            if (!password.equals(user.getPassword()) || !user.getActive() || !user.getRegistered()) {
                user = null;
            }
        }
        return user;
    }

    public boolean updatePassword(User user, String newPassword) throws Exception {
        user.setPassword(newPassword);
        return (userDB.update(user) == 1);
    }

    public boolean updateUser(User user) throws Exception {
        return (userDB.update(user) == 1);
    }

    public boolean addUser(String username, String password, String email, String firstName, String lastName, String company, String path) throws IOException, Exception {
        User user = new User(username, password, email, true,false, firstName, lastName);
        Role defRole = userDB.getRole(2);
        Company comp = userDB.getCompany(company);
        user.setRole(defRole);
        user.setCompany(comp);

        boolean isAdded = true;

        ArrayList<User> users = new ArrayList<User>(userDB.getAllUsers());
        if (users.indexOf(user) != -1) {
            isAdded = false;
        } else {
            AwaitingService as = new AwaitingService();
            as.startRegistration(email, user, path);
        }
        return isAdded;
    }

    public boolean removeUser(User user, String admin) throws Exception {
        boolean isRemoved = true;

        if (user.getUsername().equals(admin)) {
            isRemoved = false;
        } else {
            isRemoved = (userDB.removeUser(user) == 1);
        }

        return isRemoved;
    }

    public boolean logicalRemove(User user) throws Exception {
        boolean isRemoved = true;

        if (user.getRole().getRoleID() == 1) {
            isRemoved = false;
        } else {
            user.setActive(false);
            isRemoved = (userDB.update(user) == 1);
        }
        return isRemoved;
    }

    public boolean sendRetrivalMail(String email, String path) throws NotesDBException {
        boolean userExist = false;
        User toRetrive = getUserByEmail(email);
        if (toRetrive == null) {
            return false;
        }

        PasswordChangeService ps = new PasswordChangeService();
        ps.startPasswordRetrieval(email, toRetrive, path);

        return true;
    }

    public User retrivePassword(String uuid) throws NotesDBException {

        PasswordChangeService pcs = new PasswordChangeService();
        return pcs.completePasswordRetrieval(uuid);

    }
    
    public User completeRegistration(String uuid) throws NotesDBException, Exception{
        AwaitingService as = new AwaitingService();
        User user = as.completeRegistration(uuid);
        if(user != null)
            userDB.addUser(user);
        return user;
    }

    public User getUserByEmail(String email) {

        User toRetrive = null;
        try {
            List<User> users = userDB.getAllUsers();
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    toRetrive = user;
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
        }

        return toRetrive;
    }
}
