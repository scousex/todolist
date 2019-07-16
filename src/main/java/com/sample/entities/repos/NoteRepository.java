package com.sample.entities.repos;

import com.sample.entities.Note;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Репозиторий для сущности Note.
 * Интерфейс взаимодействия с БД.
 */

public interface NoteRepository extends JpaRepository<Note,Integer> {

    //Обновить статус заметки
    @Modifying
    @Transactional
    @Query("UPDATE Note SET status=:status WHERE note_id=:id")
    void updateStatusById(@Param("id")Integer id, @Param("status")boolean status);

    //Редактировать, обновить заметку
    @Modifying
    @Transactional
    @Query("UPDATE Note SET status=:status, text=:text WHERE note_id=:id")
    void updateById(@Param("id")Integer id, @Param("status")boolean status, @Param("text") String text);

    //Искать заметки пользователя по его username и сортировать их по id по возрастанию
    List<Note> findAllByUsernameOrderByNoteIdAsc(@Param("username") String username);

    //Искать заметки пользователя по его username и сортировать их по id по убыванию
    List<Note> findAllByUsernameOrderByNoteIdDesc(@Param("username") String username);

    //Найти конкретную заметку по id
    Note findByNoteId(@Param("noteId") Integer id);

    //Удалить заметку по id
    void deleteById(@Param("noteId") Integer id);

}
