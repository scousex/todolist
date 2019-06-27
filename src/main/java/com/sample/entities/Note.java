package com.sample.entities;

import javax.persistence.*;

@Entity
@Table(name = "note", schema = "test", catalog = "")
public class Note {

    //note id (primary key) генерируется spring'ом
    @Id
    @GeneratedValue
    private Integer noteId;

    @Column(name = "username")
    private String username;

    @Column(name = "text")
    //note text
    private String text;

    @Column(name = "status")
    //note status
    private boolean status;

    public Note(){}

    public Note(String username, String text){
        this.username = username;
        this.text = text;
        this.status = false;
    }

    public String getUsername() {
        return username;
    }

    public String getText() {
        return text;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getStatus(){
        return status;
    }


}
