package com.markdevelopers.rakshak.auth;

import android.util.Log;

import com.markdevelopers.rakshak.data.remote.models.UserResponse;
import com.markdevelopers.rakshak.data.repository.UserRepository;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Archish on 1/13/2017.
 */

public class LoginPresenter implements LoginContract.LoginPresenter {

    LoginContract.LoginView view;
    UserRepository userRepository;

    public LoginPresenter(UserRepository userRepository, LoginContract.LoginView view) {
        this.view = view;
        this.userRepository = userRepository;
    }


    @Override
    public void login(String fcm_token, String mobileno, String password) {
        userRepository
                .setLogin(fcm_token, mobileno, password)
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
                    public void onNext(UserResponse userResponse) {
                        if (view != null)
                            view.onLogin(userResponse);

                    }
                });
    }

    @Override
    public void logout(String accessToken) {
        userRepository.logout(accessToken)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<UserResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if(view!=null)
                            view.onNetworkException(e);
                    }

                    @Override
                    public void onNext(UserResponse userResponse) {
                        if(view!=null)
                            view.onLogout(userResponse);
                    }
                });

    }
}
