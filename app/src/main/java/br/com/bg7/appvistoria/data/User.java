package br.com.bg7.appvistoria.data;

import android.support.annotation.NonNull;

import com.j256.ormlite.table.DatabaseTable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

/**
 * Created by: elison
 * Date: 2017-07-12
 *
 * Represents a user in the app
 */
@DatabaseTable(tableName = "users")
public class User {

    private static final Logger LOG = LoggerFactory.getLogger(User.class);

    private static final String SEPARATOR = "###!!!###";
    public static final int ID_INDEX = 0;
    public static final int USERNAME_INDEX = 1;
    public static final int PASSWORD_HASH_INDEX = 2;
    public static final int TOKEN_INDEX = 3;

    private Long id;

    private String username;

    private String passwordHash;

    private String token;

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
        User user = new User(this.username, this.token, this.passwordHash);

        user.id = this.id;

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
            String username = parts[USERNAME_INDEX];
            String passwordHash = parts[PASSWORD_HASH_INDEX];
            String token = parts[TOKEN_INDEX];

            User user = new User(username, token, passwordHash);

            user.id = Long.parseLong(parts[ID_INDEX]);

            return user;
        }
        catch (NumberFormatException|IndexOutOfBoundsException exception) {
            LOG.warn("Não foi possível recuperar usuário", exception);
            LOG.warn("Usuário serializado: {}", serializedUser);

            return null;
        }
    }
}
