/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sait.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sait.businesslogic.NoteService;
import sait.businesslogic.UserServices;
import sait.domainmodel.Note;
import sait.domainmodel.User;

/**
 *
 * @author 733196
 */
public class NotesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sess = request.getSession();
        sess.setAttribute("uri", "/notes");
        String username = (String) sess.getAttribute("username");
        UserServices us = new UserServices();
        User user = null;
        try {
            user = us.getUser(username);
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<Note> notes = new ArrayList(user.getNoteCollection());
        request.setAttribute("notes", notes);

        String msg = (String) sess.getAttribute("msg");
        if (msg != null) {
            request.setAttribute("msg", msg);
            sess.removeAttribute("msg");
        }

        getServletContext().getRequestDispatcher("/WEB-INF/notes/notes.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sess = request.getSession();
        String action = request.getParameter("action");
        String msg = (String) sess.getAttribute("msg");
        if (msg != null) {
            request.setAttribute("msg", msg);
            sess.removeAttribute("msg");
        }
        //Delete button from notes
        if (action != null && action.equals("delete")) {
            deleteNote(request, response);
        }
        //add button from notes
        if (action != null && action.equals("add")) {
            addNote(request, response);
        }
        //edit button after you view a note
        if (action != null && action.equals("edit")) {
            editNote(request, response);
        }
        // view button in notes
        if (action != null && action.equals("view")) {
            viewNote(request, response);
        }
        //Save button after you edit a note
        if (action != null && action.equals("save")) {
            saveNote(request, response);
        }

    }

    private void deleteNote(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        checkOwnership(request,response);
        HttpSession sess = request.getSession();
        NoteService ns = new NoteService();
        int noteID = Integer.parseInt(request.getParameter("selectedNoteID"));

        try {
            if (ns.delete(noteID)) {
                sess.setAttribute("msg", "Note deleted");
                response.sendRedirect("/notes");
                return;
            } else {
                sess.setAttribute("msg", "Error trying to delete note");
                response.sendRedirect("/notes");
                return;
            }
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addNote(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sess = request.getSession();
        String username = (String) sess.getAttribute("username");
        UserServices us = new UserServices();
        User user = null;
        try {
            user = us.getUser(username);
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        NoteService ns = new NoteService();
        String title = request.getParameter("title");
        String contents = request.getParameter("contents");

        if (title == null || title.isEmpty() || contents == null || contents.isEmpty()) {
            request.setAttribute("title", title);
            request.setAttribute("contents", contents);
            sess.setAttribute("msg", "Please enter all values");
            getServletContext().getRequestDispatcher("/WEB-INF/notes.jsp").forward(request, response);
            return;
        }

        try {
            if (ns.insert(title, contents, user)) {
                sess.setAttribute("msg", "New note added!");
                response.sendRedirect("/notes");
                return;
            } else {
                sess.setAttribute("msg", "Error adding note");
                response.sendRedirect("/notes");
                return;
            }
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void editNote(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        checkOwnership(request,response);
        NoteService ns = new NoteService();
        int noteID = Integer.parseInt(request.getParameter("selectedNoteID"));
        Note note = null;
        try {
            note = ns.get(noteID);
        } catch (Exception ex) {
            Logger.getLogger(NotesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("note", note);
        request.setAttribute("edit", "true");
        getServletContext().getRequestDispatcher("/WEB-INF/notes/viewedit.jsp").forward(request, response);
    }

    private void viewNote(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        checkOwnership(request,response);
        NoteService ns = new NoteService();
        int noteID = Integer.parseInt(request.getParameter("selectedNoteID"));
        
        Note note = null;
        try {
            note = ns.get(noteID);
        } catch (Exception ex) {
            Logger.getLogger(NotesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("note", note);
        getServletContext().getRequestDispatcher("/WEB-INF/notes/viewedit.jsp").forward(request, response);
    }

    private void saveNote(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession sess = request.getSession();
        int noteID = Integer.parseInt(request.getParameter("selectedNoteID"));
        String contents = request.getParameter("contents");
        String title = request.getParameter("title");
        NoteService ns = new NoteService();
     
        checkOwnership(request,response);

        
        if (contents == null || contents.isEmpty() || title == null || title.isEmpty()) {
            Note note = null;
            try {
                note = ns.get(noteID);
            } catch (Exception ex) {
                Logger.getLogger(NotesServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute("note", note);
            sess.setAttribute("msg", "Please enter all values");
            getServletContext().getRequestDispatcher("/WEB-INF/notes/viewedit.jsp").forward(request, response);
            return;
        }

        try {
            if (ns.update(noteID, contents, title)) {
                sess.setAttribute("msg", "Note updated");
                response.setStatus(307);//cool hacks are cool, allows redirect with post
                response.addHeader("Location", "/notes?action=view");
                return;
            } else {
                sess.setAttribute("msg", "An error has occurred");
                response.setStatus(307);
                response.addHeader("Location", "/notes?action=view");
                return;
            }
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void checkOwnership(HttpServletRequest request, HttpServletResponse response) throws IOException{
        HttpSession sess = request.getSession();
        int noteID = Integer.parseInt(request.getParameter("selectedNoteID"));
        NoteService ns = new NoteService();
     
        String username = (String) sess.getAttribute("username");
        UserServices us = new UserServices();
        User user = null;
        try {
            user = us.getUser(username);
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean hasOwnership = ns.hasNoteOwnership(user, noteID);
        if (!hasOwnership) {
            sess.setAttribute("msg", "You do not own this note, stop spoofing my app");
            response.sendRedirect("/notes");
            return;
        }
    }
}
