package com.sample.services;

import com.sample.entities.Note;
import com.sample.entities.repos.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService{
    private NoteRepository noteRepository;

    @Autowired
    public void setNoteRepository(NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }

    //@Override
    //public Note getNoteById(Integer id){
       // return noteRepository.findByNoteId(id);
    //}

   /* @Override
    public void setNoteStatus(Integer id, boolean status) {
        noteRepository.setNoteStatus(id,status);
    }

    @Override
    public void saveNote(Note note){
        noteRepository.saveNote(note.getUsername(), note.getText());
    }

    @Override
    public void deleteNote(Integer id){
        noteRepository.deleteByNoteId(id);
    }

    @Override
    public void updateNote(Integer id, String text, boolean status) {

    }

    @Override
    public List<Note> findAllOrderByAsc(String username) {
        return noteRepository.findAllByUserIdBOrderByNoteIdAsc(username);
    }
    @Override
    public List<Note> findAllOrderByDesc(String username) {
        return noteRepository.findAllByUserIdBOrderByNoteIdDesc(username);
    }*/
   @Override
   public List<Note> findAllOrderByAsc() {
       return noteRepository.findAllByOrderByNoteIdAsc();
   }
}
