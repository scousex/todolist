package com.sample.services;

import com.sample.entities.Note;
import com.sample.entities.repos.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface NoteService {

    /*Note getNoteById(Integer id);
    void saveNote(Note note);
    void setNoteStatus(Integer id, boolean status);
    void deleteNote(Integer id);
    void updateNote(Integer id, String text, boolean status);*/
    List<Note> findAllOrderByAsc();
    //List<Note> findAllOrderByDesc(String username);
}


