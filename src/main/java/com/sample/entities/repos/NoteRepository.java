package com.sample.entities.repos;

import com.sample.entities.Note;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface NoteRepository extends JpaRepository<Note,Integer> {
    List<Note> findAllByUserIdBOrderByNoteIdAsc(@Param("username") String username);
    List<Note> findAllByUserIdBOrderByNoteIdDesc(@Param("username") String username);
    Note findByNoteId(@Param("noteId") long id);
    void deleteByNoteId(@Param("noteId") long noteId);
    void setNoteStatus(@Param("noteId") long noteId, @Param("status") boolean status);
    void saveNote(@Param("username") String username,  @Param("text") String text);
}
