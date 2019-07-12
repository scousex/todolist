package com.sample.entities.repos;

import com.sample.entities.Note;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface NoteRepository extends JpaRepository<Note,Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Note SET status=:status WHERE note_id=:id")
    void updateStatusById(@Param("id")Integer id, @Param("status")boolean status);
    //List<Note> findAllByOrderByNoteIdAsc();
    List<Note> findAllByUsernameOrderByNoteIdAsc(@Param("username") String username);
    List<Note> findAllByUsernameOrderByNoteIdDesc(@Param("username") String username);
    Note findByNoteId(@Param("noteId") Integer id);
    void deleteById(@Param("noteId") Integer id);
   // void setNoteStatus(@Param("noteId") long noteId, @Param("status") boolean status);
    //void save(@Param("username") String username,  @Param("text") String text);
}
