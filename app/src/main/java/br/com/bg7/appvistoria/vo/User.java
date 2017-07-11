package br.com.bg7.appvistoria.vo;

import com.orm.SugarRecord;

/**
 * Created by elison on 11/07/17.
 */

public class User extends SugarRecord<User> {

    private String fullName;
    private String userName;
    private String email;
    private String password;
    private Token token;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
