package br.com.bg7.appvistoria.data;

import android.support.annotation.NonNull;

import com.orm.SugarRecord;

/**
 * Created by: elison
 * Date: 2017-07-12
 *
 * Represents a user in the app
 *
 * Warnings of "FieldCanBeLocal" are ignored if the field is not used because
 * the field needs to be present for Sugar to create it in the database
 */
public class User extends SugarRecord<User> {

    private String username;

    private String passwordHash;

    private String token;

    /**
     * Default constructor used by Sugar
     */
    @SuppressWarnings("unused")
    public User() {}

    public User(String username, String token, String passwordHash) {
        this.username = username;
        this.token = token;
        this.passwordHash = passwordHash;
    }

    public User withToken(String token) {
        User user = cloneUser();
        user.token = token;

        return user;
    }

    public User withPasswordHash(String passwordHash) {
        User user = cloneUser();
        user.passwordHash = passwordHash;

        return user;
    }

    @NonNull
    private User cloneUser() {
        User user = new User();

        user.id = this.id;
        user.username = this.username;
        user.passwordHash = this.passwordHash;
        user.token = this.token;
        return user;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getUsername() {
        return username;
    }
}
