package br.com.bg7.appvistoria.data.source.local.fake;

import java.io.IOException;

import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.source.local.AuthRepository;

/**
 * Created by: elison
 * Date: 2017-08-07
 */

public class FakeAuthRepository implements AuthRepository {

    private User user;
    private String token;

    @Override
    public User currentUser() {
        return user;
    }

    @Override
    public String currentToken() {
        return token;
    }

    @Override
    public void save(User user, String token) throws IOException {
        this.user = user;
        this.token = token;

        if (token.equals("123PIM 456PIM")) {
            throw new IOException("123PIM 567PIM 91011PIM");
        }
    }

    @Override
    public void clear() {
        this.user = null;
        this.token = null;
    }
}
