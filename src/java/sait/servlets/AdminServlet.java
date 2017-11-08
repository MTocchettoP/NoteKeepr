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
        User user = (User) sess.getAttribute("user");

        if (user == null || !user.getIsAdmin()) {
            response.sendRedirect("/login");
            return;
        }

        String msg = (String) sess.getAttribute("msg");
        if (msg != null) {
            request.setAttribute("err", msg);
            sess.removeAttribute("msg");
        }
        
        String msg2 = (String) sess.getAttribute("msg2");
        if (msg2 != null) {
            request.setAttribute("errDel", msg2);
            sess.removeAttribute("msg2");
        }
        UserServices us = new UserServices(getServletContext().getRealPath("/WEB-INF/"));

        try {
            sess.setAttribute("userList", us.getUsers());
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession sess = request.getSession();

        if (action != null && action.equals("addUser")) {
            addUser(request, response);
        }

        if (action != null && action.equals("delete")) {
            deleteUser(request, response);
        }
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession sess = request.getSession();
        UserServices us = new UserServices(getServletContext().getRealPath("/WEB-INF/"));

        String username = request.getParameter("newUsername");
        String password = request.getParameter("password");

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("newUsername", username);
            request.setAttribute("password", password);
            sess.setAttribute("msg", "Please enter both values");
            response.sendRedirect("/admin");
            return;
        }

        try {
            if (us.addUser(username, password)) {
                sess.setAttribute("msg", "New user added!");
                response.sendRedirect("/admin");
            } else {
                sess.setAttribute("msg", "Username already in use, please chose another one");
                response.sendRedirect("/admin");
            }
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession sess = request.getSession();
        UserServices us = new UserServices(getServletContext().getRealPath("/WEB-INF/"));
        
        String username = request.getParameter("toDel");
        if (username == null) {
            sess.setAttribute("msg2", "Please select a user to delete");
            response.sendRedirect("/admin");
            return;
        }
        User user = null;
        try {
            user = us.getUser(username);
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        User admin = (User) sess.getAttribute("user");
         try {
            if (us.removeUser(user, admin.getUsername())) {
                sess.setAttribute("msg2", "User deleted");
                response.sendRedirect("/admin");
            } else {
                sess.setAttribute("msg2", "You can't delete yourself silly");
                response.sendRedirect("/admin");
            }
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
