package com.sample.services;

import com.sample.entities.Note;
import com.sample.entities.repos.NoteRepository;
import com.sample.entities.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService{

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Note getNoteById(Integer id){
       return noteRepository.findByNoteId(id);
    }

   // @Override
    //public void setNoteStatus(Integer id, boolean status) {
     //   noteRepository.setNoteStatus(id,status);
    //}

    @Override
    public boolean saveNote(Note note){
        if(userRepository.findByUsername(note.getUsername())!=null){
            noteRepository.save(note);
            return true;
        }

        return false;

    }

    @Override
    public void deleteNote(Integer id){
        noteRepository.removeNoteByNoteId(id);
    }

    @Override
    public void updateNote(Integer id, String text, boolean status) {
        Note note = noteRepository.findByNoteId(id);

        note.setText(text);
        note.setStatus(status);

        noteRepository.save(note);
    }

    @Override
    public List<Note> findAllOrderByAsc(String username) {
        return noteRepository.findAllByUsernameOrderByNoteIdAsc(username);
    }
    @Override
    public List<Note> findAllOrderByDesc(String username) {
        return noteRepository.findAllByUsernameOrderByNoteIdDesc(username);
    }

}
