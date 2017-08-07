package br.com.bg7.appvistoria.data.source.local.fake;

import br.com.bg7.appvistoria.data.source.local.AuthRepository;

/**
 * Created by: luciolucio
 * Date: 2017-08-07
 */

public class FakeAuthRepository implements AuthRepository {

    private String username;

    @Override
    public String currentUsername() {
        return username;
    }

    @Override
    public void save(String username) {
        this.username = username;
    }

    @Override
    public void clear() {
        this.username = null;
    }
}
