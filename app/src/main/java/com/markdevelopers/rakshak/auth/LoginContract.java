package com.markdevelopers.rakshak.auth;


import com.markdevelopers.rakshak.common.BaseContract;
import com.markdevelopers.rakshak.data.remote.models.UserResponse;

/**
 * Created by Archish on 1/13/2017.
 */

public interface LoginContract {
    interface LoginView extends BaseContract.BaseView {
        void onLogin(UserResponse userResponse);

        void onLogout(UserResponse userResponse);

    }

    interface LoginPresenter {
        void login(String fcm_token, String mobileno, String password);

        void logout(String accessToken);
    }
}
