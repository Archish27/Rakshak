package com.markdevelopers.rakshak.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.markdevelopers.rakshak.MainActivity;
import com.markdevelopers.rakshak.R;
import com.markdevelopers.rakshak.common.BaseActivity;
import com.markdevelopers.rakshak.common.RakshakApp;
import com.markdevelopers.rakshak.data.local.SharedPreferenceManager;
import com.markdevelopers.rakshak.data.remote.models.CityWrapper;
import com.markdevelopers.rakshak.data.remote.models.UserResponse;
import com.markdevelopers.rakshak.data.repository.UserRepository;
import com.markdevelopers.rakshak.ui.widgets.BaseButton;
import com.markdevelopers.rakshak.ui.widgets.BaseEditText;
import com.markdevelopers.rakshak.ui.widgets.BaseRadioButton;

import java.util.ArrayList;


/**
 * Created by Archish on 1/13/2017.
 */

public class SignUpActivity extends BaseActivity implements SignUpContract.SignUpView {
    BaseEditText etFName, etLName, etEmail, etPhoneNo, etPassword;
    RadioGroup rgCategory;
    BaseRadioButton rbCategory;
    BaseButton bSignUp;
    AppCompatSpinner sState, sCity;
    int category;
    SignUpPresenter presenter;
    Animation animFadein, animFadeOut;
    LinearLayout workerLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (new SharedPreferenceManager(getApplicationContext()).getMainPage() != 0) {
            Intent i = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        initViews();
        UserRepository userRepository = ((RakshakApp) getApplication()).getComponent().userRepository();
        presenter = new SignUpPresenter(userRepository, this);

        sState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                presenter.getCities(sState.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        rgCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = rgCategory.getCheckedRadioButtonId();
                rbCategory = (BaseRadioButton) findViewById(selectedId);

                switch (rbCategory.getText().toString()) {
                    case "User":
                        workerLayout.startAnimation(animFadeOut);
                        category = 4;
                        break;
                    case "Worker":
                        workerLayout.startAnimation(animFadein);
                        category = 3;
                        break;

                }

            }
        });
        bSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (category == 4) {
                    boolean status = validate();
                    if (status)
                        presenter.signUpUser(new SharedPreferenceManager(getApplicationContext()).getDeviceToken(), etFName.getText().toString(), etLName.getText().toString(), etEmail.getText().toString(), etPhoneNo.getText().toString(), etPassword.getText().toString(), category);
                    Log.d("Data", new SharedPreferenceManager(getApplicationContext()).getDeviceToken() + etFName.getText().toString() + etLName.getText().toString() + etEmail.getText().toString() + etPhoneNo.getText().toString() + etPassword.getText().toString() + category + sState.getSelectedItem().toString() + sCity.getSelectedItem().toString());
                } else if (category == 3) {
                    boolean status = validateWorker();
                    if (status)
                        presenter.signUpWorker(new SharedPreferenceManager(getApplicationContext()).getDeviceToken(), etFName.getText().toString(), etLName.getText().toString(), etEmail.getText().toString(), etPhoneNo.getText().toString(), etPassword.getText().toString(), category, sState.getSelectedItem().toString(), sCity.getSelectedItem().toString());
                    Log.d("Data", new SharedPreferenceManager(getApplicationContext()).getDeviceToken() + etFName.getText().toString() + etLName.getText().toString() + etEmail.getText().toString() + etPhoneNo.getText().toString() + etPassword.getText().toString() + category + sState.getSelectedItem().toString() + sCity.getSelectedItem().toString());
                }

            }
        });
        animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);
        // set animation listener
        animFadein.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (animation == animFadein) {
                    workerLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animFadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (animation == animFadeOut) {
                    workerLayout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void initViews() {
        etFName = (BaseEditText) findViewById(R.id.etFName);
        etLName = (BaseEditText) findViewById(R.id.etLName);
        etEmail = (BaseEditText) findViewById(R.id.etEmail);
        etPhoneNo = (BaseEditText) findViewById(R.id.etPhoneNo);
        etPassword = (BaseEditText) findViewById(R.id.etPassword);
        rgCategory = (RadioGroup) findViewById(R.id.rgCategory);
        bSignUp = (BaseButton) findViewById(R.id.bSignUp);
        workerLayout = (LinearLayout) findViewById(R.id.workerLayout);
        sState = (AppCompatSpinner) findViewById(R.id.sState);
        sCity = (AppCompatSpinner) findViewById(R.id.sCity);
    }

    private boolean validate() {
        if (etFName.getText().toString().isEmpty()) {
            etFName.setError("First name cannot be empty");
            etFName.setFocusable(true);
            return false;
        } else if (etLName.getText().toString().isEmpty()) {
            etFName.setError("First name cannot be empty");
            etFName.setFocusable(true);
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
            etEmail.setError("Invalid Email id");
            etEmail.setFocusable(true);

            return false;
        } else if (etPhoneNo.getText().toString().isEmpty() || etPhoneNo
                .getText().length() < 10) {
            etPhoneNo.setError("Mobile number should be of 10 digits");
            etPhoneNo.setFocusable(true);

            return false;
        } else if (etPassword.getText().toString().isEmpty() || etPassword.getText().length() < 6) {
            etPassword.setError("Password length should me more than 6 characters");
            etPassword.setFocusable(true);
            return false;
        }

        return true;
    }

    private boolean validateWorker() {
        if (etFName.getText().toString().isEmpty()) {
            etFName.setError("First name cannot be empty");
            etFName.setFocusable(true);
            return false;
        } else if (etLName.getText().toString().isEmpty()) {
            etFName.setError("First name cannot be empty");
            etFName.setFocusable(true);
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
            etEmail.setError("Invalid Email id");
            etEmail.setFocusable(true);

            return false;
        } else if (etPhoneNo.getText().toString().isEmpty() || etPhoneNo
                .getText().length() < 10) {
            etPhoneNo.setError("Mobile number should be of 10 digits");
            etPhoneNo.setFocusable(true);

            return false;
        } else if (etPassword.getText().toString().isEmpty() || etPassword.getText().length() < 6) {
            etPassword.setError("Password length should me more than 6 characters");
            etPassword.setFocusable(true);
            return false;
        } else if (sState.getSelectedItem().equals("Select State")) {
            Toast.makeText(SignUpActivity.this, "Please select state", Toast.LENGTH_SHORT).show();
            return false;
        } else if (sCity.getSelectedItem() == null) {
            Toast.makeText(SignUpActivity.this, "Please select city", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onSignUp(UserResponse userResponse) {

        boolean status = userResponse.getSuccess();
        String accessToken = userResponse.getAccessToken();

        if (status) {
            Toast.makeText(SignUpActivity.this, "Successful", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(i);
            finish();
            new SharedPreferenceManager(getApplicationContext()).saveMainPage(1);
            new SharedPreferenceManager(getApplicationContext()).saveAccessToken(accessToken);
            new SharedPreferenceManager(getApplicationContext()).saveCategory(category);
        } else {
            Toast.makeText(SignUpActivity.this, "Failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCites(CityWrapper cityWrapper) {
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (int i = 0; i < cityWrapper.data.size(); i++) {
            stringArrayList.add(cityWrapper.data.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stringArrayList);
        sCity.setAdapter(adapter);
    }

    @Override
    public void onNetworkException(Throwable e) {
        super.onNetworkException(e);
        Log.d("ServerException", String.valueOf(e));
    }

}
