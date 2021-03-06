package com.example.muco.eshop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.muco.eshop.api.LoginService;
import com.example.muco.eshop.api.ServiceRepository;
import com.example.muco.eshop.api.UserDto;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends Activity {
    public static final String TAG = "Login";

    private Observer<UserDto> observer = new Observer<UserDto>() {

        @Override
        public void onCompleted() {
            System.out.println("complete");
        }

        @Override
        public void onError(Throwable e) {
            setErrorMessageAndRequestFocus(username, getString(R.string.error_incorrect_login));
            setErrorMessageAndRequestFocus(password, getString(R.string.error_incorrect_login));
        }

        @Override
        public void onNext(UserDto userDto) {
            Log.e(TAG, "next");
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

    @Bind(R.id.username)
    EditText username;
    @Bind(R.id.password)
    EditText password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.login)
    public void login() {
        resetErrors();
        LoginService loginService = ServiceRepository.loginService();

        loginService
                .login(username.getText().toString(), password.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        System.out.println("login clicked");

    }

    private void resetErrors() {
        username.setError(null);
        password.setError(null);
    }

    private void setErrorMessageAndRequestFocus(TextView textView, String errorMessage) {
        textView.setError(Html.fromHtml("<font color='red'>" + errorMessage + "</font>"));
        textView.requestFocus();
    }
}
