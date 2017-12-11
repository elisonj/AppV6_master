package br.com.bg7.appvistoria.auth.vo;

import br.com.bg7.appvistoria.data.User;

/**
 * Created by: elison
 * Date: 2017-07-31
 */

public class LoginData {
    private String username;
    private String password;
    private User localUser;
    private boolean passwordMatches;

    public LoginData(String username, String password, User localUser, boolean passwordMatches) {
        this.username = username;
        this.password = password;
        this.localUser = localUser;
        this.passwordMatches = passwordMatches;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public User getLocalUser() {
        return localUser;
    }

    public boolean passwordMatches() {
        return passwordMatches;
    }
}
