package br.com.bg7.appvistoria.auth.callback;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;

/**
 * Created by: luciolucio
 * Date: 2017-08-08
 */

public class CheckCannotLoginCallback implements AuthCallback {
    @Override
    public void onCannotLogin() {
        throw new AssertionFailedError();
    }

    @Override
    public void onBadCredentials() {
        Assert.fail();
    }

    @Override
    public void onSuccess() {
        Assert.fail();
    }
}
