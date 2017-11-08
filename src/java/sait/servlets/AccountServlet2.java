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
public class AccountServlet2 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sess = request.getSession();
        User user = (User) sess.getAttribute("user");

        if (user == null || user.getRole().getRoleID() == 1) {//is admin
            response.sendRedirect("/login");
            return;
        }

        String msg = (String) sess.getAttribute("msg");
        if (msg != null) {
            request.setAttribute("err", msg);
            sess.removeAttribute("msg");
        }
        getServletContext().getRequestDispatcher("/WEB-INF/account.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession sess = request.getSession();
        User user = (User) sess.getAttribute("user");
        String newPassword = request.getParameter("newPassword");

        if (action.equals("newPassword")) {

            if (newPassword.equals("")) {
                sess.setAttribute("msg", "Password value must be provided for change");
                response.sendRedirect("/account");
                return;
            }

            UserServices us = new UserServices(getServletContext().getRealPath("/WEB-INF/"));

            try {
                us.updatePassword(user, newPassword);
            } catch (Exception ex) {
                Logger.getLogger(AccountServlet2.class.getName()).log(Level.SEVERE, null, ex);
            }

            sess.setAttribute("msg", "Password updated!");
            response.sendRedirect("/account");
        }
    }

}
