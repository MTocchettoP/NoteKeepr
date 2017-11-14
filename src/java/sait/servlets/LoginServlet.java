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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
@WebServlet(name = "LogingServlet", urlPatterns = {"/LogingServlet"})
public class LoginServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession sess = request.getSession();
        
        
        
        String action = request.getParameter("action");
        if (action != null && action.equals("logout")) {
            sess.invalidate();
            sess = request.getSession();
            sess.setAttribute("msg", "Sucessfuly logout");           
            response.sendRedirect("/login");
            return;
        }
        
        String username =  (String) sess.getAttribute("username");
        UserServices us = new UserServices();
        User user = null;
        try {
            if(username != null)
                user = us.getUser(username);
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (user != null) {
            if (user.getRole().getRoleID() == 1) {//is admin
                response.sendRedirect("/users");
            } else {
                response.sendRedirect("/notes");
            }
            return;
        }
        
        String msg = (String) sess.getAttribute("msg");
        if (msg != null) {
            request.setAttribute("msg", msg);
            sess.removeAttribute("msg");
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("userName", username);
            request.setAttribute("password", password);
            request.setAttribute("msg", "Please enter both value.");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        UserServices us = new UserServices();
        User user = null;
        try {
            user = us.loging(username, password);
        } catch (Exception ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (user == null) {
            request.setAttribute("msg", "Username or Password is invalid!");
            request.setAttribute("userName", username);
            request.setAttribute("password", password);
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        
        HttpSession sess = request.getSession();
        sess.setAttribute("username", username);
        if (user.getRole().getRoleID() == 1) {//is admin
            response.sendRedirect("/users");
        } else {
            response.sendRedirect("/notes");
        }
    }
    
}
