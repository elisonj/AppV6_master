package br.com.bg7.appvistoria.data.source.remote.retrofit;

import br.com.bg7.appvistoria.data.source.remote.dto.UserResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by: luciolucio
 * Date: 2017-07-19
 */

public interface UserService {
    @GET("account/v2/user/{id}")
    Call<UserResponse> getUser(@Header("Authorization") String token, @Path("id") String userId);
}
