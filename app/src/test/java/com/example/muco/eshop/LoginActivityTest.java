package com.example.muco.eshop;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Button;

import com.example.muco.eshop.api.ServiceRepository;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import java.lang.reflect.Field;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class LoginActivityTest {

    private static final String JSON_ROOT_PATH = "/json/";
    private String jsonFullPath;
    private final LoginActivity loginActivity = Robolectric.setupActivity(LoginActivity.class);

    @Before
    public void setUp() throws Exception {
        jsonFullPath = getClass().getResource(JSON_ROOT_PATH).toURI().getPath();
        Field loginService = ServiceRepository.class.getDeclaredField("loginService");
        loginService.setAccessible(true);
        loginService.set(null, null);
    }

    @Test
    public void should_navigate_to_main_activity_when_username_and_password_right() throws Exception {
        OkHttpClient okHttpClient = getMockHttpClient("login_success.json", 200);
        ServiceRepository.okHttpClient = okHttpClient;


        loginActivity.username.setText("muco");
        loginActivity.password.setText("right_password");
        ((Button) loginActivity.findViewById(R.id.login)).performClick();

        ShadowApplication instance = ShadowApplication.getInstance();
        Intent nextActivity = instance.getNextStartedActivity();
        String className = nextActivity.getComponent().getClassName();
        assertThat(className, is(MainActivity.class.getName()));
    }

    @Test
    public void should_stay_on_login_activity_when_login_fail() throws Exception {
        OkHttpClient okHttpClient = getMockHttpClient("login_fail.json", 400);
        ServiceRepository.okHttpClient = okHttpClient;

        loginActivity.username.setText("muco");
        loginActivity.password.setText("wrong_password");
        ((Button) loginActivity.findViewById(R.id.login)).performClick();

        ShadowApplication application = ShadowApplication.getInstance();
        assertThat("Next activity should not started", application.getNextStartedActivity(), Is.is(nullValue()));
        assertThat("Show error for Email field ", loginActivity.username.getError(), Is.is(notNullValue()));
        assertThat("Show error for Password field ", loginActivity.password.getError(), Is.is(notNullValue()));
    }

    @NonNull
    private OkHttpClient getMockHttpClient(String responseFile, int responseCode) {
        return new OkHttpClient.Builder()
                .addInterceptor(new MockInterceptor(jsonFullPath, getResponseBuilder(responseCode), responseFile))
                .build();
    }

    private Response.Builder getResponseBuilder(int responseCode) {
        return new Response.Builder()
                .code(responseCode)
                .protocol(Protocol.HTTP_1_0)
                .addHeader("content-type", "application/json");
    }
}
