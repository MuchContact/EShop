package com.example.muco.eshop.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface LoginService {
    @FormUrlEncoded
    @POST("authentication")
    Observable<UserDto> login(@Field("user_name") String username, @Field("password") String password);
}
