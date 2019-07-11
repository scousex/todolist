package com.sample.payloads;

import com.sample.entities.Note;

import java.util.List;

public class TodosResponse {
    private List<Note> noteList;
    private String success;

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Note> getNoteList() {
        return noteList;
    }

    public String getSuccess() {
        return success;
    }

    public String toString(){

        return null;
    }
}
