package com.sample.payloads;

public class Login{

    private String username;
    private String password;
        /*Login(String username, String password){
            this.password = password;
            this.username = username;
        }*/

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}