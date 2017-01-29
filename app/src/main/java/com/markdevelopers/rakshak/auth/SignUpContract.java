package com.markdevelopers.rakshak.auth;

import com.markdevelopers.rakshak.common.BaseContract;
import com.markdevelopers.rakshak.data.remote.models.CityWrapper;
import com.markdevelopers.rakshak.data.remote.models.UserResponse;

/**
 * Created by Archish on 1/13/2017.
 */

public interface SignUpContract {
    interface SignUpView extends BaseContract.BaseView {
        void onSignUp(UserResponse userResponse);

        void onCites(CityWrapper cityWrapper);
    }

    interface SignUpPresenter {
        void signUpUser(String regToken, String fname, String lname, String emailid, String mobileno, String password, int role);

        void signUpWorker(String regToken, String fname, String lname, String emailid, String mobileno, String password, int role, String state, String city);

        void getCities(String state);

        void getUser();
    }
}
