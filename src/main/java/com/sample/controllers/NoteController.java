package com.sample.controllers;

import com.sample.entities.Note;
import com.sample.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.MapKey;
import java.util.List;

@Controller
public class NoteController {

    private String sortDateMethod = "ASC";

    private NoteService noteService;

    @Autowired
    public void setNoteService(NoteService service){
        this.noteService = service;
    }

    @GetMapping("/{username}")
    public String list(Model model, @PathVariable String username){
        List<Note> notes = filterAndSort(username);
        model.addAttribute("notes",notes);
        model.addAttribute("sort",sortDateMethod);
        return "index"; //возвращаем главную страницу
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
        return notebook;
    }

    @GetMapping("/new")
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
    }

}
