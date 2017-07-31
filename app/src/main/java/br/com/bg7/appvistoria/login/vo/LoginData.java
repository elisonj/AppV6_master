package br.com.bg7.appvistoria.login.vo;

import br.com.bg7.appvistoria.data.User;

/**
 * Created by: luciolucio
 * Date: 2017-07-31
 */

public class LoginData {
    private String username;
    private String password;
    private User user;
    private boolean passwordMatches;

    public LoginData(String username, String password, User user, boolean passwordMatches) {
        this.username = username;
        this.password = password;
        this.user = user;
        this.passwordMatches = passwordMatches;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public User getUser() {
        return user;
    }

    public boolean passwordMatches() {
        return passwordMatches;
    }
}
