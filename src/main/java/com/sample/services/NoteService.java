package com.sample.services;

import com.sample.entities.Note;
import com.sample.entities.repos.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для выполнения операций по заметкам.
 * Интерфейс.
 */

public interface NoteService {

    Note getNoteById(Integer id);
    void saveNote(Note note);
    void setNoteStatus(Integer id, boolean status);
    void deleteNote(Integer id);
    void updateNote(Integer id, String text, boolean status);
    List<Note> findAllOrderByAsc(String username);
    List<Note> findAllOrderByDesc(String username);
}


