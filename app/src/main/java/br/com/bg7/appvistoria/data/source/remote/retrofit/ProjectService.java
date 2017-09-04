package br.com.bg7.appvistoria.data.source.remote.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by: elison
 * Date: 2017-09-04
 */
interface ProjectService {
    @GET("auction-lotting/commercial-project/")
    Call<br.com.bg7.appvistoria.data.source.remote.dto.Project> findByIdOrDescription(@Header("Authorization") String token, @Query("q") String search);
}
