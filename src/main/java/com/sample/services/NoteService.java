package com.sample.services;

import com.sample.entities.Note;
import com.sample.entities.repos.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для обработки
 */

public interface NoteService {

    Note getNoteById(Integer id);
    boolean saveNote(Note note);
    void setNoteStatus(Integer id, boolean status);
    void deleteNote(Integer id);
    void updateNote(Integer id, String text, boolean status);
    List<Note> findAllOrderByAsc(String username);
    List<Note> findAllOrderByDesc(String username);
}


