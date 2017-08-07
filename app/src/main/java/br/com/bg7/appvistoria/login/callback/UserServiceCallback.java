package br.com.bg7.appvistoria.login.callback;

import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.source.local.UserRepository;
import br.com.bg7.appvistoria.data.source.remote.http.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;
import br.com.bg7.appvistoria.data.source.remote.dto.Token;
import br.com.bg7.appvistoria.data.source.remote.dto.UserResponse;
import br.com.bg7.appvistoria.login.LoginContract;
import br.com.bg7.appvistoria.login.vo.LoginData;

/**
 * Created by: luciolucio
 * Date: 2017-07-31
 */

class UserServiceCallback extends LoginCallback implements HttpCallback<UserResponse> {
    private Token token;

    UserServiceCallback(LoginData loginData, Token token, UserRepository userRepository, LoginContract.View loginView) {
        super(loginData, userRepository, loginView);

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

        loginView.showCannotLoginError();
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
        User existingUser = userRepository.findByUsername(loginData.getUsername());

        if (existingUser != null) {
            userRepository.delete(existingUser);
        }

        User user = new User(loginData.getUsername(), token.getAccessToken(), hashpw(loginData.getPassword()));
        saveUserAndEnter(user);
    }

    private void saveLocalUserAndEnter() {
        User user = loginData.getLocalUser().withToken(token.getAccessToken());
        user = user.withPasswordHash(hashpw(loginData.getPassword()));

        saveUserAndEnter(user);
    }

    private void saveUserAndEnter(User user) {
        userRepository.save(user);
        loginView.showMainScreen();
    }
}
