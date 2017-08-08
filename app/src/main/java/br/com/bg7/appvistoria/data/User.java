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

    public String serialize() throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
        objectStream.writeObject(this);
        objectStream.flush();

        return byteStream.toString();
    }

    public static User deserialize(String serializedUser) {
        try {
            byte b[] = serializedUser.getBytes();
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);
            return (User) si.readObject();
        } catch (IOException|ClassNotFoundException exception) {
            LOG.warn("Não foi possível recuperar usuário", exception);
            LOG.warn("Usuário serializado", exception);
            LOG.warn(serializedUser);

            return null;
        }
    }
}
