package com.markdevelopers.rakshak.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.markdevelopers.rakshak.MainActivity;
import com.markdevelopers.rakshak.R;
import com.markdevelopers.rakshak.common.BaseActivity;
import com.markdevelopers.rakshak.common.RakshakApp;
import com.markdevelopers.rakshak.data.local.SharedPreferenceManager;
import com.markdevelopers.rakshak.data.remote.models.UserResponse;
import com.markdevelopers.rakshak.data.repository.UserRepository;
import com.markdevelopers.rakshak.ui.widgets.BaseButton;
import com.markdevelopers.rakshak.ui.widgets.BaseEditText;
import com.markdevelopers.rakshak.ui.widgets.BaseTextView;

/**
 * Created by Archish on 1/20/2017.
 */

public class LoginActivity extends BaseActivity implements LoginContract.LoginView {

    BaseTextView tvForgotPassword, tvCreateAccount;
    BaseEditText etMobileNo, etPassword;
    BaseButton bLogin;
    LoginPresenter loginPresenter;
    ProgressDialog _dialog;

    @Override
    public void onNetworkException(Throwable e) {
        super.onNetworkException(e);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (new SharedPreferenceManager(getApplicationContext()).getMainPage() != 0) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        setContentView(R.layout.activity_login);
        initViews();
        UserRepository userRepository = ((RakshakApp) getApplication()).getComponent().userRepository();
        loginPresenter = new LoginPresenter(userRepository, this);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean status = validate();
                if (status) {
                    showLoading();
                    loginPresenter.login(new SharedPreferenceManager(getApplicationContext()).getDeviceToken(), etMobileNo.getText().toString(), etPassword.getText().toString());

                }
            }
        });
        tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(i);
                finish();
            }
        });
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initViews() {
        etMobileNo = (BaseEditText) findViewById(R.id.etMobileNo);
        etPassword = (BaseEditText) findViewById(R.id.etPassword);
        tvForgotPassword = (BaseTextView) findViewById(R.id.tvForgotPassword);
        tvCreateAccount = (BaseTextView) findViewById(R.id.tvCreate);
        bLogin = (BaseButton) findViewById(R.id.bLogin);
    }

    @Override
    public void onLogin(UserResponse userResponse) {
        if (userResponse.getSuccess()) {
            _dialog.dismiss();
            new SharedPreferenceManager(getApplicationContext()).saveMainPage(1);
            new SharedPreferenceManager(getApplicationContext()).saveAccessToken(userResponse.getAccessToken());
            new SharedPreferenceManager(getApplicationContext()).saveCategory(userResponse.getCategory());
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "Invalid login", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onLogout(UserResponse userResponse) {

    }

    private boolean validate() {
        if (etMobileNo.getText().toString().isEmpty()) {
            etMobileNo.setError("Mobile number cannot be empty");
            etMobileNo.setFocusable(true);
            return false;
        } else if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError("Password cannot be empty");
            etPassword.setFocusable(true);
            return false;
        }
        return true;
    }

    private void showLoading() {
        _dialog = new ProgressDialog(LoginActivity.this);
        _dialog.setTitle("Loading");
        _dialog.setMessage(getString(R.string.please_wait)); // set your messages if not inflated from XML
        _dialog.setCancelable(false);

    }

}
