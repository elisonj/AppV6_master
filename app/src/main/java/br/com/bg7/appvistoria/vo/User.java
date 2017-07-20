package br.com.bg7.appvistoria.vo;

import com.orm.SugarRecord;

import org.mindrot.jbcrypt.BCrypt;

import br.com.bg7.appvistoria.data.source.remote.dto.UserResponse;

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

    @SuppressWarnings("FieldCanBeLocal")
    private String fullName;

    @SuppressWarnings("FieldCanBeLocal")
    private String userName;

    @SuppressWarnings("FieldCanBeLocal")
    private String email;

    private String password;

    @SuppressWarnings("FieldCanBeLocal")
    private Token token;

    /**
     * Default constructor used by Sugar
     */
    @SuppressWarnings("unused")
    public User() {}

    public User(UserResponse user, br.com.bg7.appvistoria.data.source.remote.dto.Token tokenFromService, String password) {
        fullName = user.getUserAccounts().get(0).getBasicInfo().getFullName();
        userName = user.getUserAccounts().get(0).getCredentials().getLogin();
        email = user.getUserAccounts().get(0).getBasicInfo().getEmail().getAddress();
        this.password  = BCrypt.hashpw(password, BCrypt.gensalt());
        token = new Token(tokenFromService);
    }

    public String getPassword() {
        return password;
    }

}
