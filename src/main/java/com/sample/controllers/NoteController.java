package com.sample.controllers;

import com.sample.entities.Note;
import com.sample.services.NoteService;
import com.sample.services.SecurityService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.MapKey;
import java.util.ArrayList;
import java.util.List;

@RestController
public class NoteController {

    private String sortDateMethod = "ASC";

    @Autowired
    private NoteService noteService;

    @Autowired
    private SecurityService securityService;


    @GetMapping(path="todos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> list(){
        List<Note> notes = filterAndSort();
        List<JSONObject> entities = new ArrayList<JSONObject>();
        for (Note n : notes) {
            JSONObject entity = new JSONObject();
            entity.put("noteid",n.getNoteId());
            entity.put("text",n.getText());
            entity.put("status", n.getStatus());
            entities.add(entity);
        }
        return new ResponseEntity<Object>(entities, HttpStatus.OK);
    }



    private List<Note> filterAndSort() {
        List<Note> notebook = null;
        switch (sortDateMethod) {
            case "ASC":
                notebook = securityService.findTodolistInUsername();
                break;
            case "DESC":
                notebook = securityService.findTodolistInUsername();
                break;
        }
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
