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
import sait.domainmodel.User;

/**
 *
 * @author 733196
 */
@WebServlet(name = "LogingServlet", urlPatterns = {"/LogingServlet"})
public class AccountServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession sess = request.getSession();
        
        String action = request.getParameter("action");
        if (action != null && action.equals("logout")) {
            sess.removeAttribute("user");
            sess.setAttribute("msg", "Sucessfuly logout");            
            response.sendRedirect("/login");
            return;
        }
        
        User user = (User) sess.getAttribute("user");
        if (user != null) {
            if (user.getRole().getRoleID() == 1) {//is admin
                response.sendRedirect("/admin");
            } else {
                response.sendRedirect("/notes");
            }
            return;
        }
        
        String msg = (String) sess.getAttribute("msg");
        if (msg != null) {
            request.setAttribute("errorMessage", msg);
            sess.removeAttribute("msg");
        }
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        
        if (userName == null || userName.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("userName", userName);
            request.setAttribute("password", password);
            request.setAttribute("errorMessage", "Please enter both value.");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        UserServices us = new UserServices(getServletContext().getRealPath("/WEB-INF/"));
        User user = null;
        try {
            user = us.loging(userName, password);
        } catch (Exception ex) {
            Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (user == null) {
            request.setAttribute("errorMessage", "Username or Password is invalid!");
            request.setAttribute("userName", userName);
            request.setAttribute("password", password);
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        
        HttpSession sess = request.getSession();
        sess.setAttribute("user", user);
        if (user.getRole().getRoleID() == 1) {//is admin
            response.sendRedirect("/admin");
        } else {
            response.sendRedirect("/notes");
        }
    }
    
}
