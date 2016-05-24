package com.example.muco.eshop;

import android.content.Intent;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class WelcomeActivityTest {

    @Ignore
    public void should_navigate_to_main_activity() throws Exception {
        Robolectric.setupActivity(WelcomeActivity.class);
        ShadowApplication instance = ShadowApplication.getInstance();
        Intent nextStartedActivity = instance.getNextStartedActivity();
        assertNotNull(nextStartedActivity);
        String className = nextStartedActivity.getComponent().getClassName();
        assertThat(className, is(LoginActivity.class.getName()));
    }
}