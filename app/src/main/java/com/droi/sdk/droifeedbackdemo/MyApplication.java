package com.droi.sdk.droifeedbackdemo;

import android.app.Application;

import com.droi.sdk.core.Core;
import com.droi.sdk.feedback.DroiFeedback;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Core.initialize(this);
        DroiFeedback.initialize(this);
    }
}
