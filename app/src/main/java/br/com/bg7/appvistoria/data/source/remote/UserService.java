package br.com.bg7.appvistoria.data.source.remote;

import br.com.bg7.appvistoria.data.source.remote.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.dto.UserResponse;

/**
 * Created by: luciolucio
 * Date: 2017-07-19
 */

public interface UserService {
    void getUser(String token, String userId, HttpCallback<UserResponse> callback);
}
