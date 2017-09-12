package br.com.bg7.appvistoria.data.servicelocator;

import android.content.Context;

import java.util.HashMap;
import java.util.ServiceConfigurationError;

import br.com.bg7.appvistoria.BaseActivity;
import br.com.bg7.appvistoria.BuildConfig;
import br.com.bg7.appvistoria.data.source.local.AuthRepository;
import br.com.bg7.appvistoria.data.source.local.ConfigRepository;
import br.com.bg7.appvistoria.data.source.local.InspectionRepository;
import br.com.bg7.appvistoria.data.source.local.LanguageRepository;
import br.com.bg7.appvistoria.data.source.local.UserRepository;
import br.com.bg7.appvistoria.data.source.local.WorkOrderRepository;
import br.com.bg7.appvistoria.data.source.remote.InspectionService;
import br.com.bg7.appvistoria.data.source.remote.PictureService;
import br.com.bg7.appvistoria.data.source.remote.ProductService;
import br.com.bg7.appvistoria.data.source.remote.ProjectService;
import br.com.bg7.appvistoria.data.source.remote.TokenService;
import br.com.bg7.appvistoria.data.source.remote.UserService;

/**
 * Created by: luciolucio
 * Date: 2017-09-03
 */

public abstract class ServiceLocator {
    private static HashMap<String, Class> map;

    private static String currentType = BuildConfig.BUILD_TYPE;

    BaseActivity baseActivity;
    Context context;

    static {
        map = new HashMap<>();

        map.put("debug", AlwaysLoggedInServiceLocator.class);
        map.put("debugWithLogin", ReleaseServiceLocator.class);
        map.put("release", ReleaseServiceLocator.class);
    }

    public static void configure(String type) {
        currentType = type;
    }

    public static ServiceLocator create(BaseActivity baseActivity, Context context) {
        ServiceLocator locator;
        Class clazz = map.get(currentType);

        try {
            if (clazz == null) {
                throw new ServiceConfigurationError(String.format("Service Locator config '%s' is not available", currentType));
            }

            locator = (ServiceLocator) clazz.newInstance();
        } catch (InstantiationException|IllegalAccessException e) {
            // Can happen if class does not have an accessible default constructor
            throw new RuntimeException("Cannot instantiate Service Locator", e);
        }

        locator.baseActivity = baseActivity;
        locator.context = context;

        return locator;
    }

    public abstract AuthRepository getAuthRepository();
    public abstract ConfigRepository getConfigRepository();
    public abstract LanguageRepository getLanguageRepository();
    public abstract UserRepository getUserRepository();
    public abstract WorkOrderRepository getWorkOrderRepository();
    public abstract InspectionRepository getInspectionRepository();

    public abstract TokenService getTokenService();
    public abstract UserService getUserService();
    public abstract ProjectService getProjectService();
    public abstract ProductService getProductService();
    public abstract PictureService getPictureService();
    public abstract InspectionService getInspectionService();

}
