package br.com.bg7.appvistoria.data;

import android.support.annotation.NonNull;

import com.orm.SugarRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Locale;

/**
 * Created by: elison
 * Date: 2017-07-12
 *
 * Represents a user in the app
 *
 * Warnings of "FieldCanBeLocal" are ignored if the field is not used because
 * the field needs to be present for Sugar to create it in the database
 */
public class User extends SugarRecord<User> implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(User.class);

    private static final String SEPARATOR = "###!!!###";
    public static final int ID_INDEX = 0;
    public static final int USERNAME_INDEX = 1;
    public static final int PASSWORD_HASH_INDEX = 2;
    public static final int TOKEN_INDEX = 3;

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

    public User withUsername(String username) {
        User user = cloneUser();
        user.username = username;

        return user;
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

    public String getToken() {
        return token;
    }

    public String serialize() {
        return String.format(Locale.US, "%d%s%s%s%s%s%s",
                this.id, SEPARATOR,
                this.username, SEPARATOR,
                this.passwordHash, SEPARATOR,
                this.token);
    }

    public static User deserialize(String serializedUser) {
        String[] parts = serializedUser.split(SEPARATOR);

        try {
            User user = new User();
            user.id = Long.parseLong(parts[ID_INDEX]);
            user.username = parts[USERNAME_INDEX];
            user.passwordHash = parts[PASSWORD_HASH_INDEX];
            user.token = parts[TOKEN_INDEX];

            return user;
        }
        catch (NumberFormatException exception) {
            LOG.warn("Não foi possível recuperar usuário", exception);
            LOG.warn("Usuário serializado: {}", serializedUser);

            return null;
        }
    }
}
