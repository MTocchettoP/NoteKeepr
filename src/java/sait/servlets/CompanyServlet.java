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
import sait.businesslogic.CompanyService;
import sait.domainmodel.Company;

/**
 *
 * @author 733196
 */
public class CompanyServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        CompanyService cs = new CompanyService();
        HttpSession sess = request.getSession();
        
        try {
            List<Company> companies = cs.getAll();
            request.setAttribute("companies", companies);
        } catch (Exception ex) {
            Logger.getLogger(CompanyServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        String msg = (String) sess.getAttribute("msg");
        if (msg != null) {
            request.setAttribute("msg", msg);
            sess.removeAttribute("msg");
        }

        String action = request.getParameter("action");
        if (action != null && action.equals("back")) {
            request.setAttribute("msg", "err.actionCanceled");
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/admin/companies.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if (action != null && action.equals("add")) {
            getServletContext().getRequestDispatcher("/WEB-INF/admin/addeditcompany.jsp").forward(request, response);
            return;
        }
        
        if (action != null && action.equals("addCompany")) {
            addCompany(request, response);
        }
        
        if (action != null && action.equals("edit")) {
            
            String companyID = request.getParameter("selectedCompany");
            CompanyService cs = new CompanyService();
            Company company = null;
            try {
                company = cs.get(companyID);
            } catch (Exception ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute("company", company);
            getServletContext().getRequestDispatcher("/WEB-INF/admin/addeditcompany.jsp").forward(request, response);
            return;
        }
        
        if (action != null && action.equals("editCompany")) {
            editCompany(request, response);
        }
        
    }
    
    private void addCompany(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sess = request.getSession();
        CompanyService cs = new CompanyService();
        
        String name = request.getParameter("company");
        if (name == null || name.isEmpty()) {
            request.setAttribute("company", name);
            sess.setAttribute("msg", "err.missingvalue");
            getServletContext().getRequestDispatcher("/WEB-INF/admin/addeditCompany.jsp").forward(request, response);
            return;
        }
        
        try {
            if (cs.add(name)) {
                sess.setAttribute("msg", "err.newCompany");
            } else {
                sess.setAttribute("msg", "err.companyExist");
            }
            response.sendRedirect("/companies");
            return;
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void editCompany(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {
        HttpSession sess = request.getSession();
        CompanyService cs = new CompanyService();
        
        String name = request.getParameter("company");
        String id = request.getParameter("companyID");
        if (name == null || name.isEmpty()) {
            request.setAttribute("company", name);
            sess.setAttribute("msg", "err.missingvalue");
            getServletContext().getRequestDispatcher("/WEB-INF/admin/addeditCompany.jsp").forward(request, response);
            return;
        }
        try {
            Company company = cs.get(id);
            company.setCompanyName(name);
            if (cs.update(company)) {
                sess.setAttribute("msg", "err.companyupdate");
            } else {
                sess.setAttribute("msg", "err.unknown");
            }
            
            response.sendRedirect("/companies");
            return;
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
