package com.markdevelopers.rakshak.data.remote;


import com.markdevelopers.rakshak.data.remote.models.CityWrapper;
import com.markdevelopers.rakshak.data.remote.models.UserResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Archish on 1/10/2017.
 */

public interface UserRestService {

    @FormUrlEncoded
    @POST("auth/register")
    Observable<UserResponse> setUser(@Field("fcm_token") String fcm_token,
                                     @Field("fname") String fname,
                                     @Field("lname") String lname,
                                     @Field("emailid") String emailid,
                                     @Field("phoneno") String phoneno,
                                     @Field("password") String password,
                                     @Field("role") int role
    );

    @FormUrlEncoded
    @POST("auth/register")
    Observable<UserResponse> setWorker(@Field("fcm_token") String fcm_token,
                                       @Field("fname") String fname,
                                       @Field("lname") String lname,
                                       @Field("emailid") String emailid,
                                       @Field("phoneno") String phoneno,
                                       @Field("password") String password,
                                       @Field("role") int role,
                                       @Field("state") String state,
                                       @Field("city") String city

    );

    @FormUrlEncoded
    @POST("cities")
    Observable<CityWrapper> getCities(@Field("state") String state);

    @GET("maintotd_json.php")
    Observable<UserResponse> getUser();

    @FormUrlEncoded
    @POST("auth/otp/verify")
    Observable<UserResponse> sendOTP(@Field("accessToken") String access_token, @Field("otp") String otp);

    @FormUrlEncoded
    @POST("auth/otp/resend")
    Observable<UserResponse> resendOTP(@Field("accessToken") String access_token);

    @FormUrlEncoded
    @POST("auth/register/phase3")
    Observable<UserResponse> setPatientCategory(@Field("accessToken") String accessToken,
                                                @Field("gender") String gender,
                                                @Field("dob") String dob,
                                                @Field("bloodgroup") String bloodgroup);

    @FormUrlEncoded
    @POST("auth/register/phase3")
    Observable<UserResponse> setHospitalCategory(@Field("accessToken") String accessToken,
                                                 @Field("haddress") String haddress,
                                                 @Field("hcity") String hcity,
                                                 @Field("hpincode") String hpincode,
                                                 @Field("hstate") String hstate);

    @FormUrlEncoded
    @POST("auth/register/phase3")
    Observable<UserResponse> setDoctorCategory(@Field("accessToken") String accessToken,
                                               @Field("gender") String gender,
                                               @Field("dob") String dob,
                                               @Field("specialist") String specialist,
                                               @Field("qualification") String qualification);


    @FormUrlEncoded
    @POST("auth/login")
    Observable<UserResponse> setLogin(@Field("fcm_token") String fcm_token, @Field("phoneno") String phoneno, @Field("password") String password);

    @FormUrlEncoded
    @POST("auth/logout")
    Observable<UserResponse> logout(@Field("accessToken") String accessToken);


    //TODO Login "auth/login" succes token true false
}
