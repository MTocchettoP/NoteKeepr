/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sait.servlets;

import sait.businesslogic.PasswordChangeService;
import sait.businesslogic.UserServices;
import sait.dataaccess.NotesDBException;
import sait.domainmodel.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sait.domainmodel.PasswordChangeRequest;

/**
 *
 * @author 733196
 */
public class ForgotPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String msg = (String) session.getAttribute("msg");
        if (msg != null) {
            request.setAttribute("msg", msg);
            session.removeAttribute("msg");
        }

        String uuid = request.getParameter("ret");
        if (uuid != null) {

            try {
                UserServices us = new UserServices();
                User user = us.retrivePassword(uuid);
                if (user != null) {
                    session.setAttribute("uuid", uuid);
                    getServletContext().getRequestDispatcher("/WEB-INF/retrieval/resetpassword.jsp").forward(request, response);
                } else {
                    request.setAttribute("msg", "err.badLink");
                }
            } catch (NotesDBException ex) {
                Logger.getLogger(ForgotPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        String sessUUID = (String) session.getAttribute("uuid");
        if (sessUUID != null) {
            getServletContext().getRequestDispatcher("/WEB-INF/retrieval/resetpassword.jsp").forward(request, response);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/retrieval/forgotpassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action != null && action.equals("send")) {
            send(request, response);
        }

        if (action != null && action.equals("reset")) {
            reset(request, response);
        }

    }

    private void reset(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String pass = request.getParameter("password");
        String pass2 = request.getParameter("password2");
        if (pass == null || pass.isEmpty() || pass2 == null || pass2.isEmpty() || !(pass.equals(pass2))) {
            session.setAttribute("msg", "err.passwordDontMatch");
            response.sendRedirect("/forgot");
            return;
        }

        String sessUUID = (String) session.getAttribute("uuid");
        PasswordChangeService pcs = new PasswordChangeService();
        try {
            PasswordChangeRequest pcr = pcs.get(sessUUID);
            if (pcr != null) {
                User user = pcr.getUserID();
                user.setPassword(pass);
                pcs.delete(pcr);
                session.setAttribute("msg", "err.passwordReset");
                response.sendRedirect("/login");
            }else{
                session.setAttribute("msg", "err.wrongaccOwner");
                response.sendRedirect("/login");
            }

        } catch (NotesDBException ex) {
            Logger.getLogger(ForgotPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void send(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        if (email == null || email.isEmpty()) {
            session.setAttribute("msg", "err.missingEmail");
            response.sendRedirect("/forgot");
            return;
        }

        String username = (String) session.getAttribute("username");
        UserServices us = new UserServices();
        try {
            String path = getServletContext().getRealPath("/WEB-INF");
            if(us.sendRetrivalMail(email, path))
                session.setAttribute("msg", "err.forgotPassEmailSent");
            else
                session.setAttribute("msg", "err.UserNotFound");
            response.sendRedirect("/forgot");
        } catch (Exception ex) {
            Logger.getLogger(ForgotPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
