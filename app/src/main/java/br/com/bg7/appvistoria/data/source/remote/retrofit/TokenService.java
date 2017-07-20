package br.com.bg7.appvistoria.data.source.remote.retrofit;

import br.com.bg7.appvistoria.data.source.remote.dto.Token;
import br.com.bg7.appvistoria.data.source.remote.dto.UserResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by: elison
 * Date: 2017-07-11
 */

public interface TokenService {
    @POST("account/oauth/token")
    @FormUrlEncoded
    Call<Token> getToken(@Field("grant_type") String grant_type,
                         @Field("client_id") String client_id,
                         @Field("username") String username,
                         @Field("password") String password);
}
