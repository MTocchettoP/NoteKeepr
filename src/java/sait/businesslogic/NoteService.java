package sait.businesslogic;

import java.util.ArrayList;
import java.util.Collection;
import sait.dataaccess.NoteDB;
import sait.domainmodel.Note;
import java.util.Date;
import java.util.List;
import sait.domainmodel.User;

public class NoteService {

    private NoteDB noteDB;

    public NoteService() {
        noteDB = new NoteDB();
    }

    public Note get(int noteid) throws Exception {
        return noteDB.getNote(noteid);
    }

    public List<Note> getAll() throws Exception {
        return new ArrayList<Note>(noteDB.getAll());
    }

    public boolean update(int noteid, String contents, String title) throws Exception {
        Note note = noteDB.getNote(noteid);
        note.setContents(contents);
        note.setTitle(title);
        return (noteDB.update(note)==1);
    }

    public boolean delete(int noteid) throws Exception {
        Note deletedNote = noteDB.getNote(noteid);
        return (noteDB.delete(deletedNote)==1);
    }

    public boolean insert(String title, String contents, User user) throws Exception {
        Date date = new Date();
        Note note = new Note(0, date, title, contents);
        note.setOwner(user);
        return (noteDB.insert(note)==1);
    }
    
    public boolean hasNoteOwnership(User user, int noteID){
        boolean hasOwnership = false;
            Collection<Note> notes = user.getNoteCollection();
            for(Note note : notes){
                if(note.getNoteID() == noteID)
                    hasOwnership = true;
            }
        
        return hasOwnership;
    }
}
