/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sait.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import sait.businesslogic.CompanyService;
import sait.businesslogic.UserServices;
import sait.domainmodel.Company;
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
        sess.setAttribute("admin", "true");//used to enable the Note link on the header
        sess.setAttribute("sysadmin", "true");//used to enable the company link on the header
        UserServices us = new UserServices();
        
        String msg = (String) sess.getAttribute("msg");
        if (msg != null) {
            request.setAttribute("msg", msg);
            sess.removeAttribute("msg");
        }

        String action = request.getParameter("action");
        if (action != null && action.equals("back")) {
            request.setAttribute("msg", "err.actionCanceled");
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
            CompanyService cs = new CompanyService();
            try {
                List<Company> companies = cs.getAll();
                request.setAttribute("companies", companies);
            } catch (Exception ex) {
                Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            CompanyService cs = new CompanyService();
            try {
                List<Company> companies = cs.getAll();
                request.setAttribute("companies", companies);
            } catch (Exception ex) {
                Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        String company = request.getParameter("company");
        if (username == null || username.isEmpty() || password == null || password.isEmpty()
                || email == null || email.isEmpty() || firstname == null || firstname.isEmpty()
                || lastname == null || lastname.isEmpty()
                || company == null || company.isEmpty()) {
            request.setAttribute("username", username);
            request.setAttribute("password", password);
            request.setAttribute("email", email);
            request.setAttribute("firstname", firstname);
            request.setAttribute("lastname", lastname);
            request.setAttribute("company", company);
            sess.setAttribute("msg", "err.missingvalue");
            getServletContext().getRequestDispatcher("/WEB-INF/admin/addedit.jsp").forward(request, response);
            return;
        }

        try {
            String path = getServletContext().getRealPath("/WEB-INF");
            if (us.addUser(username, password, email, firstname, lastname, company,path)) {
                sess.setAttribute("msg", "err.confEmailSent");
            } else {
                sess.setAttribute("msg", "err.UserAlreadyExist");
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
                sess.setAttribute("msg", "err.userDeleted");
            } else {
                sess.setAttribute("msg", "err.acc.adminnono");
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
        String company = request.getParameter("company");
        boolean isActive = false;
        if (active != null) {
            isActive = true;
        }

        if (username == null || username.isEmpty() || password == null || password.isEmpty()
                || email == null || email.isEmpty()
                || firstname == null || firstname.isEmpty()
                || lastname == null || lastname.isEmpty()
                || company == null || company.isEmpty()) {
            request.setAttribute("username", username);
            request.setAttribute("password", password);
            request.setAttribute("email", email);
            request.setAttribute("firstname", firstname);
            request.setAttribute("lastname", lastname);
            request.setAttribute("company", company);
            sess.setAttribute("msg", "err.missingvalue");
            getServletContext().getRequestDispatcher("/WEB-INF/admin/addedit.jsp").forward(request, response);
            return;
        }

        try {
            User user = us.getUser(username);
            Company comp = new CompanyService().get(company);
            user.setEmail(email);
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setPassword(password);
            user.setActive(isActive);
            user.setCompany(comp);
            if (us.updateUser(user)) {
                sess.setAttribute("msg", "err.userUpdated");
            } else {
                sess.setAttribute("msg", "err.unknown");
            }

            response.sendRedirect("/users");
            return;
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
