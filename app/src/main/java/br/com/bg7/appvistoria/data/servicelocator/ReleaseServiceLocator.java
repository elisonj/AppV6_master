package br.com.bg7.appvistoria.data.servicelocator;

import br.com.bg7.appvistoria.BuildConfig;
import br.com.bg7.appvistoria.data.source.local.AuthRepository;
import br.com.bg7.appvistoria.data.source.local.ConfigRepository;
import br.com.bg7.appvistoria.data.source.local.LanguageRepository;
import br.com.bg7.appvistoria.data.source.local.UserRepository;
import br.com.bg7.appvistoria.data.source.local.WorkOrderRepository;
import br.com.bg7.appvistoria.data.source.local.android.ResourcesLanguageRepository;
import br.com.bg7.appvistoria.data.source.local.android.SharedPreferencesAuthRepository;
import br.com.bg7.appvistoria.data.source.local.ormlite.OrmLiteConfigRepository;
import br.com.bg7.appvistoria.data.source.local.ormlite.OrmLiteUserRepository;
import br.com.bg7.appvistoria.data.source.local.ormlite.OrmLiteWorkOrderRepository;
import br.com.bg7.appvistoria.data.source.remote.TokenService;
import br.com.bg7.appvistoria.data.source.remote.UserService;
import br.com.bg7.appvistoria.data.source.remote.retrofit.RetrofitTokenService;
import br.com.bg7.appvistoria.data.source.remote.retrofit.RetrofitUserService;

/**
 * Created by: luciolucio
 * Date: 2017-09-03
 */

class ReleaseServiceLocator extends ServiceLocator {
    @Override
    AuthRepository getAuthRepository() {
        return new SharedPreferencesAuthRepository(context);
    }

    @Override
    ConfigRepository getConfigRepository() {
        return new OrmLiteConfigRepository(baseActivity.getConfigDao());
    }

    @Override
    LanguageRepository getLanguageRepository() {
        return new ResourcesLanguageRepository(context);
    }

    @Override
    UserRepository getUserRepository() {
        return new OrmLiteUserRepository(baseActivity.getUserDao());
    }

    @Override
    WorkOrderRepository getWorkOrderRepository() {
        return new OrmLiteWorkOrderRepository(baseActivity.getWorkOrderDao());
    }

    @Override
    TokenService getTokenService() {
        return new RetrofitTokenService(BuildConfig.BASE_URL, BuildConfig.GRANT_TYPE, BuildConfig.CLIENT_ID);
    }

    @Override
    UserService getUserService() {
        return new RetrofitUserService(BuildConfig.BASE_URL);
    }
}
