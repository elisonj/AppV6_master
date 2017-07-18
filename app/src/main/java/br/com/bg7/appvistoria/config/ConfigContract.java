package br.com.bg7.appvistoria.config;

import br.com.bg7.appvistoria.BasePresenter;
import br.com.bg7.appvistoria.BaseView;
import br.com.bg7.appvistoria.login.LoginContract;

/**
 * Created by: luciolucio
 * Date: 2017-07-17
 */

public interface ConfigContract {
    interface View extends BaseView<ConfigContract.Presenter> {
    }

    interface Presenter extends BasePresenter {
    }
}
