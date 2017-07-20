package br.com.bg7.appvistoria.data.source.remote.retrofit;

import br.com.bg7.appvistoria.data.source.remote.dto.Token;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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
