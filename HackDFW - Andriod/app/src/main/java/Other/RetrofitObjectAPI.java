package Other;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by KrishnChinya on 3/26/17.
 */

public interface RetrofitObjectAPI {

        @GET("source/{id1}/destination/{id2}")
        Call<MapsResultJson> getFromTo(@Path("id1") String from,@Path("id2") String to );

        @GET("lat/{id1}/long/{id2}")
        Call<YelpResultJson> getYelp(@Path("id1") String from,@Path("id2") String to );

}


