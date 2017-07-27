package br.com.bg7.appvistoria.login;

import br.com.bg7.appvistoria.BasePresenter;
import br.com.bg7.appvistoria.BaseView;

/**
 * Created by: luciolucio
 * Date: 2017-07-14
 *
 * {@link SuppressWarnings pois so as sub-classes sao implementadas, de proposito}
 */

@SuppressWarnings("unused")
public interface LoginContract {

    interface View extends BaseView<Presenter> {

        boolean isConnected();
        void showMainScreen();
        void showUsernameEmptyError();
        void showPasswordEmptyError();
        void showCannotLoginError();
        void showBadCredentialsError();
        void showCriticalError();
    }

    interface Presenter extends BasePresenter {

        void login(String username, String password);

    }
}
