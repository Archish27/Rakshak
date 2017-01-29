package com.markdevelopers.rakshak.data.repository;

import com.markdevelopers.rakshak.data.remote.models.CityWrapper;
import com.markdevelopers.rakshak.data.remote.models.UserResponse;

import rx.Observable;

/**
 * Created by Archish on 1/14/2017.
 */

public interface UserRepository {
    Observable<UserResponse> setUser(String fcm_token,
                                     String fname,
                                     String lname,
                                     String emailid,
                                     String phoneno,
                                     String password,
                                     int role);

    Observable<UserResponse> setWorker(String fcm_token,
                                     String fname,
                                     String lname,
                                     String emailid,
                                     String phoneno,
                                     String password,
                                     int role, String state, String city);

    Observable<UserResponse> getUser();

    Observable<CityWrapper> getCities(String state);

    Observable<UserResponse> sendOTP(String access_token, String otp);

    Observable<UserResponse> resendOTP(String access_token);


    Observable<UserResponse> setPatientCategory(String accessToken, String gender,
                                                String dob,
                                                String bloodgroup);

    Observable<UserResponse> setHospitalCategory(String accessToken, String haddress,
                                                 String hcity,
                                                 String hpincode,
                                                 String hstate);

    Observable<UserResponse> setDoctorCategory(String accessToken,
                                               String gender,
                                               String dob,
                                               String specialist,
                                               String qualification);


    Observable<UserResponse> setLogin(String fcm_token, String phoneno, String password);
    Observable<UserResponse> logout(String accessToken);


}
