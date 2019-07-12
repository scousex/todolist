package com.sample.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sample.entities.Note;
import com.sample.entities.User;
import com.sample.payloads.ApiResponse;
import com.sample.services.CurrentUser;
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
import java.security.Principal;
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

    @CrossOrigin("/*")
    @GetMapping(value="/todos", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Note>> list(@RequestHeader("Authorization") String token){

        logger.info("request header is: \n" + token);

        String username = securityService.getUserByToken(token.substring(7,token.length()));

        Gson gsonBuilder = new GsonBuilder().create();
        List<Note> notes = filterAndSort(username);

       // if(notes.size()==0) return new ResponseEntity<Object>("User hasn't notes",HttpStatus.NOT_EXTENDED);

        logger.info("Получено "+notes.size() + " записей из базы");

      //  String usersNotes = gsonBuilder.toJson(notes);

        return new ResponseEntity<List<Note>>(notes,HttpStatus.OK);
    }

    private List<Note> filterAndSort(String username) {
        List<Note> notebook = null;
        switch (sortDateMethod) {
            case "ASC":
                notebook = noteService.findAllOrderByAsc(username);
                break;
            case "DESC":
                notebook = noteService.findAllOrderByDesc(username);
                break;
        }
        System.out.println("Получено " + notebook.size());

        return notebook;
    }

    @CrossOrigin("/addNote")
    @PostMapping(value = "/addNote", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateNote(@RequestHeader("Authorization") String token, @RequestBody ObjectNode obj) {

        logger.info("request header is: \n" + token);

        String username = securityService.getUserByToken(token.substring(7,token.length()));

        noteService.saveNote(new Note(username,obj.get("text").asText()));

        return new ResponseEntity<Object>(new ApiResponse(true,"Note added successfully"),HttpStatus.OK);


     //   return new ResponseEntity("User is unauthorized",HttpStatus.UNAUTHORIZED);

    }

    @CrossOrigin("/status")
    @PutMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> edit(@RequestHeader("Authorization") String token, @RequestBody ObjectNode obj) {

        logger.info("request header is: \n" + token);

        String username = securityService.getUserByToken(token.substring(7,token.length()));


        Integer id = obj.get("id").asInt();
        boolean status = obj.get("status").asBoolean();

        if(noteService.getNoteById(id).getUsername() == username) {
            ///TODO: Обработать ошибки при сохранении
            noteService.setNoteStatus(id, status);
            return new ResponseEntity<Object>(new ApiResponse(true,"Note status updated"),HttpStatus.OK);
        }
        return new ResponseEntity<Object>(
                new ApiResponse(false,"The note doesn't belong to you"),HttpStatus.OK); //возвращаем view с редактированием
    }

    @PutMapping(value="/edit", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveNote(@RequestHeader("Authorization") String token, @RequestBody ObjectNode note) {

        logger.info("request header is: \n" + token);

        String username = securityService.getUserByToken(token.substring(7,token.length()));

        Integer id = note.get("id").asInt();
        String text = note.get("text").asText();
        boolean status = note.get("status").asBoolean();

        if(noteService.getNoteById(id).getUsername() == username) {
            ///TODO: Обработать ошибки обновления
            noteService.updateNote(id, text, status);
            return new ResponseEntity<Object>(new ApiResponse(true,"Note edited"),HttpStatus.OK);
        }
        return new ResponseEntity<Object>(
                new ApiResponse(false,"The note doesn't belong to you"),HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(@RequestHeader("Authorization") String token, @RequestBody ObjectNode note) {

        logger.info("request header is: \n" + token);

        String username = securityService.getUserByToken(token.substring(7,token.length()));
        ///TODO: Обработать ошибки удаления
        Integer id = note.get("id").asInt();
        if(noteService.getNoteById(id).getUsername() == username) {

            noteService.deleteNote(id);
            return new ResponseEntity<Object>(new ApiResponse(true,"Note deleted"),HttpStatus.OK);

        }

        return new ResponseEntity<Object>(
                new ApiResponse(false,"The note doesn't belong to you"),HttpStatus.OK);

    }

}
