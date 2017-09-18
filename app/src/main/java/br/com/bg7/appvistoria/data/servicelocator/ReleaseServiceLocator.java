package br.com.bg7.appvistoria.data.servicelocator;

import br.com.bg7.appvistoria.BuildConfig;
import br.com.bg7.appvistoria.data.source.local.AuthRepository;
import br.com.bg7.appvistoria.data.source.local.ConfigRepository;
import br.com.bg7.appvistoria.data.source.local.InspectionRepository;
import br.com.bg7.appvistoria.data.source.local.LanguageRepository;
import br.com.bg7.appvistoria.data.source.local.UserRepository;
import br.com.bg7.appvistoria.data.source.local.WorkOrderRepository;
import br.com.bg7.appvistoria.data.source.local.android.ResourcesLanguageRepository;
import br.com.bg7.appvistoria.data.source.local.android.SharedPreferencesAuthRepository;
import br.com.bg7.appvistoria.data.source.local.ormlite.OrmLiteConfigRepository;
import br.com.bg7.appvistoria.data.source.local.ormlite.OrmLiteUserRepository;
import br.com.bg7.appvistoria.data.source.local.stub.StubInspectionRepository;
import br.com.bg7.appvistoria.data.source.remote.InspectionService;
import br.com.bg7.appvistoria.data.source.remote.PictureService;
import br.com.bg7.appvistoria.data.source.local.ormlite.OrmLiteWorkOrderRepository;
import br.com.bg7.appvistoria.data.source.remote.ProductService;
import br.com.bg7.appvistoria.data.source.remote.ProjectService;
import br.com.bg7.appvistoria.data.source.remote.TokenService;
import br.com.bg7.appvistoria.data.source.remote.UserService;
import br.com.bg7.appvistoria.data.source.remote.retrofit.RetrofitPictureService;
import br.com.bg7.appvistoria.data.source.remote.retrofit.RetrofitTokenService;
import br.com.bg7.appvistoria.data.source.remote.retrofit.RetrofitUserService;
import br.com.bg7.appvistoria.data.source.remote.stub.StubProductService;
import br.com.bg7.appvistoria.data.source.remote.stub.StubProjectService;
import br.com.bg7.appvistoria.data.source.remote.stub.StubRetrofitInspectionService;

/**
 * Created by: luciolucio
 * Date: 2017-09-03
 */

class ReleaseServiceLocator extends ServiceLocator {

    private static final int BUFFER = 1024;

    @Override
    public AuthRepository getAuthRepository() {
        return new SharedPreferencesAuthRepository(context);
    }

    @Override
    public ConfigRepository getConfigRepository() {
        return new OrmLiteConfigRepository(baseActivity.getConfigDao());
    }

    @Override
    public LanguageRepository getLanguageRepository() {
        return new ResourcesLanguageRepository(context);
    }

    @Override
    public UserRepository getUserRepository() {
        return new OrmLiteUserRepository(baseActivity.getUserDao());
    }

    @Override
    public WorkOrderRepository getWorkOrderRepository() {
        return new OrmLiteWorkOrderRepository(baseActivity.getWorkOrderDao());
    }

    @Override
    public InspectionRepository getInspectionRepository() {
        return new StubInspectionRepository();
    }

    @Override
    public TokenService getTokenService() {
        return new RetrofitTokenService(BuildConfig.BASE_URL, BuildConfig.GRANT_TYPE, BuildConfig.CLIENT_ID);
    }

    @Override
    public UserService getUserService() {
        return new RetrofitUserService(BuildConfig.BASE_URL);
    }

    @Override
    public ProjectService getProjectService() {
        return new StubProjectService();
    }

    @Override
    public ProductService getProductService() {
        return new StubProductService();
    }

    @Override
    public PictureService getPictureService() {
        return new RetrofitPictureService(BuildConfig.BASE_URL, null, BUFFER);
    }

    @Override
    public InspectionService getInspectionService() {
        return new StubRetrofitInspectionService();
    }
}
