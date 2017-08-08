package br.com.bg7.appvistoria.data.source.local.fake;

import br.com.bg7.appvistoria.data.source.local.AuthRepository;

/**
 * Created by: luciolucio
 * Date: 2017-08-07
 */

public class FakeAuthRepository implements AuthRepository {

    private String username;
    private String token;

    @Override
    public String currentUsername() {
        return username;
    }

    @Override
    public String currentToken() {
        return token;
    }

    @Override
    public void save(String username, String token) {
        this.username = username;
        this.token = token;
    }

    @Override
    public void clear() {
        this.username = null;
        this.token = null;
    }
}
