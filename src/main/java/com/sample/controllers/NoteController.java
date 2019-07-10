package com.sample.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sample.entities.Note;
import com.sample.services.NoteService;
import com.sample.services.SecurityService;

import com.google.gson.JsonObject;
import io.netty.handler.codec.json.JsonObjectDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.MapKey;
import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class NoteController {

    public static final Logger logger = Logger.getLogger(NoteController.class.getName());

    private String sortDateMethod = "ASC";

    @Autowired
    private NoteService noteService;

    @Autowired
    private SecurityService securityService;

    //@CrossOrigin("/todos")
    @GetMapping(value="/todos", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Note>> list(){
        Gson gsonBuilder = new GsonBuilder().create();
        List<Note> notes = filterAndSort();

       // if(notes.size()==0) return new ResponseEntity<Object>("User hasn't notes",HttpStatus.NOT_EXTENDED);

        logger.info("Получено "+notes.size() + " записей из базы");

      //  String usersNotes = gsonBuilder.toJson(notes);

        return new ResponseEntity<List<Note>>(notes,HttpStatus.OK);
    }

    private List<Note> filterAndSort() {
        List<Note> notebook = null;
        switch (sortDateMethod) {
            case "ASC":
                notebook = noteService.findAllOrderByAsc(securityService.findUserInUsername());
                break;
            case "DESC":
                notebook = noteService.findAllOrderByDesc(securityService.findUserInUsername());
                break;
        }
        System.out.println("Получено " + notebook.size());

        return notebook;
    }

    @PostMapping(path = "/addNote", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateNote(@RequestBody String text) {
        String username = securityService.findUserInUsername();
        if(noteService.saveNote(new Note(username,text)))
        {
            return new ResponseEntity<Object>("Note added",HttpStatus.OK);
        }

        return new ResponseEntity<>("User is unauthorized",HttpStatus.UNAUTHORIZED);

    }

    @PutMapping(path = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> edit(@RequestParam("id") Integer id, @RequestParam("status") boolean status) {


        ///TODO: Добавить проверку наличия
        Note note = noteService.getNoteById(id);

        ///TODO: Обработать ошибки при смене статуса
        note.setStatus(status);

        ///TODO: Обработать ошибки при сохранении
        noteService.saveNote(note);
        return new ResponseEntity<Object>("Note status updated",HttpStatus.OK); //возвращаем view с редактированием
    }

    @PutMapping(path="/edit", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveNote(@RequestParam Integer id, @RequestParam String text,
                           @RequestParam(value = "status", required = false) boolean status) {

        ///TODO: Обработать ошибки обновления
        noteService.updateNote(id, text, status);
        return new ResponseEntity<Object>("Note edited",HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(@RequestParam String id) {

        ///TODO: Обработать ошибки удаления
        noteService.deleteNote(Integer.parseInt(id));
        return new ResponseEntity<Object>("Note deleted",HttpStatus.OK);
    }

}
