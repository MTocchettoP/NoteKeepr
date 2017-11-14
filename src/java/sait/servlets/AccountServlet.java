/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sait.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
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
public class AccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sess = request.getSession();

        String username = (String) sess.getAttribute("username");
        UserServices us = new UserServices();
        User user = null;
        try {
            user = us.getUser(username);
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        String edit = (String) sess.getAttribute("edit");
        if (edit != null) {
            request.setAttribute("edit", edit);
            sess.removeAttribute("edit");
        }

        String msg = (String) sess.getAttribute("msg");
        if (msg != null) {
            request.setAttribute("msg", msg);
            sess.removeAttribute("msg");
        }

        request.setAttribute("user", user);
        getServletContext().getRequestDispatcher("/WEB-INF/account/viewedit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sess = request.getSession();
        String action = request.getParameter("action");

        if (action != null && action.equals("edit")) {
            sess.setAttribute("edit", "true");
            response.sendRedirect("/account/edit");
            return;
        }

        if (action != null && action.equals("save")) {
            editUser(request, response);
        }

        if (action != null && action.equals("delete")) {
            logicalDelete(request, response);
        }
    }

    private void logicalDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession sess = request.getSession();
        UserServices us = new UserServices();

        String username = request.getParameter("username");
        User user = null;
        try {
            user = us.getUser(username);
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (us.logicalRemove(user)) {

                response.sendRedirect("/login?action=logout");
            } else {
                sess.setAttribute("msg", "Admins can't delete their selfs silly");
                response.sendRedirect("/account");
                return;
            }
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

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("username", username);
            request.setAttribute("password", password);
            request.setAttribute("email", email);
            request.setAttribute("firstname", firstname);
            request.setAttribute("lastname", lastname);
            sess.setAttribute("msg", "Please enter all values");
            sess.setAttribute("edit", "edit");
            response.sendRedirect("/account/edit");
            return;
        }

        try {
            User user = us.getUser(username);
            user.setEmail(email);
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setPassword(password);
            if (us.updateUser(user)) {
                sess.setAttribute("msg", "User updated");
            } else {
                sess.setAttribute("msg", "An error has occurred");
            }
            response.sendRedirect("/account");
            return;
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
