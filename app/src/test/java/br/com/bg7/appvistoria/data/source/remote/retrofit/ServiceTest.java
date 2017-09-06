package br.com.bg7.appvistoria.data.source.remote.retrofit;

import junit.framework.Assert;

import org.junit.Before;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import br.com.bg7.appvistoria.UserLoggedInTest;
import br.com.bg7.appvistoria.data.source.remote.http.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

/**
 * Created by: luciolucio
 * Date: 2017-09-06
 */

class ServiceTest extends UserLoggedInTest {

    private static final int TIMEOUT = 2000;

    private CountDownLatch lock;

    MockWebServer mockWebServer;
    String url;

    @Before
    public void setUp() throws IOException {
        super.setUp();

        mockWebServer = new MockWebServer();
        url = mockWebServer.url("").toString();

        lock = new CountDownLatch(1);
    }

    void setUpResponse(String body) {
        mockWebServer.enqueue(
                new MockResponse()
                        .setBody(body)
        );
    }

    void waitForServerToRespond() throws InterruptedException {
        Assert.assertTrue(lock.await(TIMEOUT, TimeUnit.MILLISECONDS));
    }

    abstract class VerifyCallback<T> implements HttpCallback<T> {

        @Override
        public void onResponse(HttpResponse<T> httpResponse) {
            Assert.assertNotNull(httpResponse.body());

            verify(httpResponse.body());

            lock.countDown();
        }

        @Override
        public void onFailure(Throwable t) {
            Assert.fail();
        }

        protected abstract void verify(T value);
    }

    class EmptyCallback<T> implements HttpCallback<T> {

        @Override
        public void onResponse(HttpResponse<T> httpResponse) {
            Assert.assertTrue(true);
        }

        @Override
        public void onFailure(Throwable t) {
            Assert.assertTrue(true);
        }
    }
}
