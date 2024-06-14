package in.esmartsolution.shree.proadmin.api;


import java.util.Map;

import in.esmartsolution.shree.proadmin.model.GeoResponse;
import in.esmartsolution.shree.proadmin.model.UserData;
import in.esmartsolution.shree.proadmin.model.VisitData;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;


public interface AppService {
    @FormUrlEncoded
    @POST("/smartpro/lifecaresangli/user/loginadmin.php")
    Call<UserData> login(@FieldMap Map<String, String> params);

    @GET("/smartpro/lifecaresangli/user/allvisits.php")
    Call<VisitData> allvisits();

    @GET("/maps/api/geocode/json")
    Call<GeoResponse> getAddressFromLatLng(@QueryMap Map<String, String> options);

}
