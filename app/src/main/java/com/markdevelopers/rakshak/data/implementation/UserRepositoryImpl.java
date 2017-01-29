package com.markdevelopers.rakshak.data.implementation;


import com.markdevelopers.rakshak.data.remote.UserRestService;
import com.markdevelopers.rakshak.data.remote.models.CityWrapper;
import com.markdevelopers.rakshak.data.remote.models.HomeWrapper;
import com.markdevelopers.rakshak.data.remote.models.UserResponse;
import com.markdevelopers.rakshak.data.repository.UserRepository;

import rx.Observable;

/**
 * Created by Archish on 1/10/2017.
 */

public class UserRepositoryImpl implements UserRepository {

    private UserRestService userRestService;

    public UserRepositoryImpl(UserRestService userRestService) {
        this.userRestService = userRestService;
    }


    @Override
    public Observable<UserResponse> setUser(String fcm_token, String fname, String lname, String emailid, String phoneno, String password, int role) {
        return userRestService.setUser(fcm_token, fname, lname, emailid, phoneno, password, role);
    }

    @Override
    public Observable<UserResponse> setWorker(String fcm_token, String fname, String lname, String emailid, String phoneno, String password, int role, String state, String city) {
        return userRestService
                .setWorker(fcm_token, fname, lname, emailid, phoneno, password, role, state, city);
    }

    @Override
    public Observable<UserResponse> getUser() {
        return userRestService.getUser();
    }

    @Override
    public Observable<CityWrapper> getCities(String state) {
        return userRestService.getCities(state);
    }

    @Override
    public Observable<UserResponse> sendOTP(String access_token, String otp) {
        return userRestService.sendOTP(access_token, otp);
    }

    @Override
    public Observable<UserResponse> resendOTP(String access_token) {
        return userRestService.resendOTP(access_token);
    }

    @Override
    public Observable<UserResponse> setPatientCategory(String accessToken, String gender, String dob, String bloodgroup) {
        return userRestService.setPatientCategory(accessToken, gender, dob, bloodgroup);
    }


    @Override
    public Observable<UserResponse> setHospitalCategory(String accessToken, String haddress, String hcity, String hpincode, String hstate) {
        return userRestService.setHospitalCategory(accessToken, haddress, hcity, hpincode, hstate);
    }

    @Override
    public Observable<HomeWrapper> getHomeData(String accessToken) {
        return userRestService.getHomeData(accessToken);
    }


    @Override
    public Observable<UserResponse> setLogin(String fcm_token, String phoneno, String password) {
        return userRestService.setLogin(fcm_token, phoneno, password);
    }

    @Override
    public Observable<UserResponse> logout(String accessToken) {
        return userRestService.logout(accessToken);
    }


}
