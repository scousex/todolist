package com.sample.entities;
import javax.persistence.*;

@Entity
@Table(name = "user", schema = "test", catalog = "")
public class User {

    //user id (primary key) генерируется spring'ом

   // @GeneratedValue
    //private Integer userID;

    @Id
    @Column(name = "username", nullable = false, unique = true)
    //user nickname
    private String username;

    @Column(name = "password", nullable = false)
    //пароль пользователя
    private String password;

    public User(){}

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    //getters and setters

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

   // public int getUserID() {
   //     return userID;
    //}
}
