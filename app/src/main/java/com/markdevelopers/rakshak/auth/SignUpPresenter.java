package com.markdevelopers.rakshak.auth;

import android.util.Log;

import com.markdevelopers.rakshak.data.remote.models.CityWrapper;
import com.markdevelopers.rakshak.data.remote.models.UserResponse;
import com.markdevelopers.rakshak.data.repository.UserRepository;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Archish on 1/13/2017.
 */

public class SignUpPresenter implements SignUpContract.SignUpPresenter {

    SignUpContract.SignUpView view;
    UserRepository userRepository;

    public SignUpPresenter(UserRepository userRepository, SignUpContract.SignUpView view) {
        this.view = view;
        this.userRepository = userRepository;
    }

    @Override
    public void signUpUser(String fcm_token, String fname, String lname, String emailid, String mobileno, String password, int role) {
        userRepository
                .setUser(fcm_token, fname, lname, emailid, mobileno, password, role)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<UserResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.d("UserMessageComplete", "Complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view != null)
                            view.onNetworkException(e);
                    }

                    @Override
                    public void onNext(UserResponse user) {
                        if (view != null)
                            view.onSignUp(user);
                    }
                });


    }

    @Override
    public void signUpWorker(String fcm_token, String fname, String lname, String emailid, String mobileno, String password, int role, String state, String city) {
        userRepository
                .setWorker(fcm_token, fname, lname, emailid, mobileno, password, role, state, city)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<UserResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.d("UserMessageComplete", "Complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view != null)
                            view.onNetworkException(e);
                    }

                    @Override
                    public void onNext(UserResponse user) {
                        if (view != null)
                            view.onSignUp(user);
                    }
                });


    }


    @Override
    public void getCities(String state) {
        userRepository.getCities(state)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CityWrapper>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view != null)
                            view.onNetworkException(e);
                    }

                    @Override
                    public void onNext(CityWrapper cityWrapper) {
                        if (view != null) {
                            view.onCites(cityWrapper);
                        }
                    }
                });

    }

    @Override
    public void getUser() {
        userRepository.getUser()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<UserResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.d("UserMessageComplete", "Complete");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("ErrorMessageUser", String.valueOf(e));

                    }

                    @Override
                    public void onNext(UserResponse userResponse) {
                        Log.d("UserMessageHere", "Success");

                    }
                });
    }
}
