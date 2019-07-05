package com.sample.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sample.entities.Note;
import com.sample.services.NoteService;
import com.sample.services.SecurityService;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.MapKey;
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


    @GetMapping(path="todos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> list(){
        Gson gsonBuilder = new GsonBuilder().create();
        List<Note> notes = filterAndSort();

        logger.info("Получено "+notes.size() + " записей из базы");


        String usersNotes = gsonBuilder.toJson(notes);

        return new ResponseEntity<String>(usersNotes, HttpStatus.OK);
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

    @PostMapping("add")
    public ResponseEntity<Object> updateNote(@RequestParam String text, @RequestParam String username) {
        if(noteService.saveNote(new Note(username,text)))
        {
            return new ResponseEntity<Object>(HttpStatus.OK);
        }

        return new ResponseEntity<>("username",HttpStatus.NOT_EXTENDED);

    }

    @PutMapping("status")
    public ResponseEntity<Object> edit(@RequestParam Integer id, @RequestParam boolean status, Model model) {
        Note note = noteService.getNoteById(id);
        note.setStatus(status);
        return new ResponseEntity<Object>(HttpStatus.OK); //возвращаем view с редактированием
    }

    @PutMapping("edit")
    public ResponseEntity<Object> saveNote(@RequestParam Integer id, @RequestParam String text,
                           @RequestParam(value = "status", required = false) boolean status) {
        noteService.updateNote(id, text, status);
        return new ResponseEntity<Object>(HttpStatus.OK); //обновляем view
    }

    @DeleteMapping("delete")
    public ResponseEntity<Object> delete(@RequestParam Integer id) {
        noteService.deleteNote(id);
        return new ResponseEntity<Object>(HttpStatus.OK); //обновляем view
    }

}
