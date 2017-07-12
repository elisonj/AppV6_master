package br.com.bg7.appvistoria.vo;

import com.orm.SugarRecord;

import br.com.bg7.appvistoria.service.dto.UserResponse;

/**
 * Created by: elison
 * Date: 2017-07-12
 */
public class User extends SugarRecord<User> {

    private String fullName;
    private String userName;
    private String email;
    private String password;
    private Token token;

    public User(UserResponse user, br.com.bg7.appvistoria.service.dto.Token tk) {
        fullName = user.getUserAccounts().get(0).getBasicInfo().getFullName();
        userName = user.getUserAccounts().get(0).getCredentials().getLogin();
        email = user.getUserAccounts().get(0).getBasicInfo().getEmail().getAddress();

        token = new Token(tk);

    }
}
