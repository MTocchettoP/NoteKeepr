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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sait.businesslogic.AwaitingService;
import sait.businesslogic.CompanyService;
import sait.businesslogic.UserServices;
import sait.businesslogic.WebMailService;
import sait.dataaccess.NotesDBException;
import sait.domainmodel.Company;
import sait.domainmodel.User;

/**
 *
 * @author 733196
 */
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sess = request.getSession();

        String uuid = request.getParameter("complete");
        if (uuid != null) {
            UserServices us = new UserServices();
            try {
                //very secure, much wow, such code (look at doge meme if you missed the reference)
                sess.invalidate();
                sess = request.getSession();
                
                User user = us.completeRegistration(uuid);
                if (user != null) {
                    String path = getServletContext().getRealPath("/WEB-INF");
                    path = path + "/emailtemplates/welcome.html";
                    WebMailService.sendMail(user.getEmail(), "Welcome", path, null);
                    sess.setAttribute("msg", "err.registrationComplete");
                } else {
                    sess.setAttribute("msg", "err.unknown");
                }                
                response.sendRedirect("/login");
                return;
            } catch (NotesDBException ex) {
                Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        CompanyService cs = new CompanyService();
        try {
            List<Company> companies = cs.getAll();
            request.setAttribute("companies", companies);
        } catch (Exception ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/account/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null && action.equals("register")) {
            addUser(request, response);
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
            request.setAttribute("msg", "err.missingvalue");
            getServletContext().getRequestDispatcher("/WEB-INF/account/register.jsp").forward(request, response);
            return;
        }

        try {
            String path = getServletContext().getRealPath("/WEB-INF");
            if (us.addUser(username, password, email, firstname, lastname, company, path)) {
                sess.setAttribute("msg", "err.confEmailSent");
                response.sendRedirect("/login");
            } else {
                request.setAttribute("msg", "err.UserAlreadyExist");
                request.setAttribute("username", username);
                request.setAttribute("password", password);
                request.setAttribute("email", email);
                request.setAttribute("firstname", firstname);
                request.setAttribute("lastname", lastname);
                request.setAttribute("company", company);
                getServletContext().getRequestDispatcher("/WEB-INF/account/register.jsp").forward(request, response);
                return;
            }

            return;
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
