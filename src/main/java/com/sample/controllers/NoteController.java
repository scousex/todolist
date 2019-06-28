package com.sample.controllers;

import com.sample.entities.Note;
import com.sample.services.NoteService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.MapKey;
import java.util.ArrayList;
import java.util.List;

@Controller
public class NoteController {

    private String sortDateMethod = "ASC";

    private NoteService noteService;

    @Autowired
    public void setNoteService(NoteService service){
        this.noteService = service;
    }

    @GetMapping(path="/notes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> list(Model model){
        List<Note> notes = filterAndSort();
        model.addAttribute("notes",notes);
        model.addAttribute("sort",sortDateMethod);
        List<JSONObject> entities = new ArrayList<JSONObject>();
        for (Note n : notes) {
            JSONObject entity = new JSONObject();
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
                notebook = noteService.findAllOrderByAsc();
                break;
            /*case "DESC":
                notebook = noteService.findAllOrderByDesc(username);
                break;*/
        }
        return notebook;
    }

  /*  @GetMapping("/new")
    public String newNote() {
        return "operations/new";
    } //возвращаем view с созданием

    @PostMapping("/save")
    public String updateNote(@RequestParam String text, @RequestParam String username) {
        noteService.saveNote(new Note(username,text));
        return "redirect:/"; //возвращаем обновлённое view
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Note note = noteService.getNoteById(id);
        model.addAttribute("note", note);
        return "operations/edit"; //возвращаем view с редактированием
    }

    @PostMapping("/update")
    public String saveNote(@RequestParam Integer id, @RequestParam String text,
                           @RequestParam(value = "status", required = false) boolean status) {
        noteService.updateNote(id, text, status);
        return "redirect:/"; //обновляем view
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        noteService.deleteNote(id);
        return "redirect:/"; //обновляем view
    }*/

}
