package br.com.bg7.appvistoria.auth.callback;

import br.com.bg7.appvistoria.auth.vo.LoginData;
import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.source.local.UserRepository;
import br.com.bg7.appvistoria.data.source.remote.dto.Token;
import br.com.bg7.appvistoria.data.source.remote.dto.UserResponse;
import br.com.bg7.appvistoria.data.source.remote.http.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;

/**
 * Created by: elison
 * Date: 2017-07-31
 */

class UserServiceCallback extends ServiceCallbackBase implements HttpCallback<UserResponse> {
    private Token token;

    UserServiceCallback(LoginData loginData, Token token, UserRepository userRepository, AuthCallback callback) {
        super(loginData, userRepository, callback);

        this.token = token;
    }

    @Override
    public void onResponse(HttpResponse<UserResponse> httpResponse) {
        if(httpResponse.isSuccessful() && httpResponse.body() != null) {
            processSuccess(httpResponse.body());
            return;
        }

        processNonSuccess(httpResponse.code());
    }

    @Override
    public void onFailure(Throwable t) {
        if(loginData.getLocalUser() != null) {
            saveLocalUserAndEnter();
            return;
        }

        callback.onCannotLogin();
    }

    /**
     * SuppressWarnings enquanto nao salvamos o nome do usuario
     *
     * TODO: Quando salvarmos o nome do usuario, tirar o SuppressWarnings
     *
     * @param userResponse Resposta do servico, com dados do usuario
     */
    @SuppressWarnings("UnusedParameters")
    private void processSuccess(UserResponse userResponse) {
        String passwordHash = hashpw(loginData.getPassword());

        User user = new User(loginData.getUsername(), token.getAccessToken(), passwordHash);
        User existingUser = userRepository.findByUsername(loginData.getUsername());

        if (existingUser != null) {
            user = existingUser
                    .withPasswordHash(passwordHash)
                    .withToken(token.getAccessToken());
        }

        saveUserAndEnter(user);
    }

    private void saveLocalUserAndEnter() {
        User user = loginData.getLocalUser().withToken(token.getAccessToken());
        user = user.withPasswordHash(hashpw(loginData.getPassword()));

        saveUserAndEnter(user);
    }

    private void saveUserAndEnter(User user) {
        userRepository.save(user);
        callback.onSuccess();
    }
}
