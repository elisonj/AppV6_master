package br.com.bg7.appvistoria.data.source.remote.retrofit;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import br.com.bg7.appvistoria.data.source.remote.dto.Token;
import okhttp3.mockwebserver.RecordedRequest;

import static br.com.bg7.appvistoria.data.source.remote.retrofit.Resources.GET_TOKEN_RESPONSE_JSON;

/**
 * Created by: elison
 * Date: 2017-09-06
 */

public class RetrofitTokenServiceTest extends ServiceTest {

    private RetrofitTokenService tokenService;

    @Before
    public void setUp() throws IOException {
        super.setUp();

        tokenService = new RetrofitTokenService(url, "grant", "clientId");
    }

    @Test
    public void shouldCallServiceWithTheCorrectParameters() throws InterruptedException {
        tokenService.getToken("user", "pwd", new EmptyCallback<Token>());

        RecordedRequest request = mockWebServer.takeRequest();

        Assert.assertEquals("/account/oauth/token", request.getPath());
        Assert.assertEquals("POST", request.getMethod());
        Assert.assertEquals("application/x-www-form-urlencoded", request.getHeader("Content-Type"));
        Assert.assertEquals("grant_type=grant&client_id=clientId&username=user&password=pwd", request.getBody().readUtf8());
    }

    @Test
    public void shouldConvertJsonToToken() throws IOException, InterruptedException {
        setUpResponse(GET_TOKEN_RESPONSE_JSON);

        tokenService.getToken("user", "pwd", new VerifySuccessCallback<Token>() {
            @Override
            protected void verify(Token value) {
                Assert.assertEquals("12b9646778ffdasdasdfc746f", value.getAccessToken());
                Assert.assertEquals("1235", value.getUserId());
            }
        });

        waitForServerToRespond();
    }
}
