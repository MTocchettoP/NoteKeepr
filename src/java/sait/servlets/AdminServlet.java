/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sait.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sait.businesslogic.UserServices;
import sait.domainmodel.User;

/**
 *
 * @author 733196
 */
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sess = request.getSession();
        sess.setAttribute("uri", "/users");
        sess.setAttribute("admin", "true");
        UserServices us = new UserServices();

        String msg = (String) sess.getAttribute("msg");
        if (msg != null) {
            request.setAttribute("msg", msg);
            sess.removeAttribute("msg");
        }

        String action = request.getParameter("action");
        if (action != null && action.equals("back")) {
            request.setAttribute("msg", "Action canceled");
        }

        try {
            request.setAttribute("userList", us.getUsers());
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/admin/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action != null && action.equals("add")) {
            getServletContext().getRequestDispatcher("/WEB-INF/admin/addedit.jsp").forward(request, response);
            return;
        }

        if (action != null && action.equals("addUser")) {
            addUser(request, response);
        }

        if (action != null && action.equals("delete")) {
            deleteUser(request, response);
        }

        if (action != null && action.equals("edit")) {
            String username = request.getParameter("selectedUser");
            UserServices us = new UserServices();
            User user = null;
            try {
                user = us.getUser(username);
            } catch (Exception ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute("selectedUser", user);
            request.setAttribute("readonly", "readonly");
            getServletContext().getRequestDispatcher("/WEB-INF/admin/addedit.jsp").forward(request, response);
            return;
        }

        if (action != null && action.equals("editUser")) {
            editUser(request, response);
        }
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession sess = request.getSession();
        UserServices us = new UserServices();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");

        if (username == null || username.isEmpty() || password == null || password.isEmpty()
                || email == null || email.isEmpty() || firstname == null || firstname.isEmpty()
                || lastname == null || lastname.isEmpty()) {
            request.setAttribute("username", username);
            request.setAttribute("password", password);
            request.setAttribute("email", email);
            request.setAttribute("firstname", firstname);
            request.setAttribute("lastname", lastname);
            sess.setAttribute("msg", "Please enter all values");
            getServletContext().getRequestDispatcher("/WEB-INF/admin/addedit.jsp").forward(request, response);
            return;
        }

        try {
            if (us.addUser(username, password, email, firstname, lastname)) {
                sess.setAttribute("msg", "New user added!");
            } else {
                sess.setAttribute("msg", "Username already in use, please chose another one");
            }
            response.sendRedirect("/users");
            return;
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession sess = request.getSession();
        UserServices us = new UserServices();

        String username = request.getParameter("selectedUser");
        User user = null;
        try {
            user = us.getUser(username);
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        String adminName = (String) sess.getAttribute("username");
        User admin = null;
        try {
            admin = us.getUser(adminName);
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            if (us.removeUser(user, admin.getUsername())) {
                sess.setAttribute("msg", "User deleted");
            } else {
                sess.setAttribute("msg", "You can't delete yourself silly");
            }
            response.sendRedirect("/users");
            return;
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void editUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession sess = request.getSession();
        UserServices us = new UserServices();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String active = request.getParameter("active");
        boolean isActive = false;
        if (active != null) {
            isActive = true;
        }

        if (username == null || username.isEmpty() || password == null || password.isEmpty()
                || email == null || email.isEmpty()
                || firstname == null || firstname.isEmpty()
                || lastname == null || lastname.isEmpty()) {
            request.setAttribute("username", username);
            request.setAttribute("password", password);
            request.setAttribute("email", email);
            request.setAttribute("firstname", firstname);
            request.setAttribute("lastname", lastname);
            sess.setAttribute("msg", "Please enter all values");
            getServletContext().getRequestDispatcher("/WEB-INF/admin/addedit.jsp").forward(request, response);
            return;
        }

        try {
            User user = us.getUser(username);
            user.setEmail(email);
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setPassword(password);
            user.setActive(isActive);
            if (us.updateUser(user)) {
                sess.setAttribute("msg", "User updated");
            } else {
                sess.setAttribute("msg", "An error has occurred");
            }

            response.sendRedirect("/users");
            return;
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
