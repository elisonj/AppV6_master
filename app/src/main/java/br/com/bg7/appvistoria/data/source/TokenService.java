package br.com.bg7.appvistoria.data.source;


import br.com.bg7.appvistoria.data.source.remote.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.dto.Token;
import br.com.bg7.appvistoria.data.source.remote.dto.UserResponse;

/**
 * Created by: elison
 * Date: 2017-07-11
 */

public interface TokenService {
    void getToken(String username, String password, HttpCallback<Token> callback);
}
