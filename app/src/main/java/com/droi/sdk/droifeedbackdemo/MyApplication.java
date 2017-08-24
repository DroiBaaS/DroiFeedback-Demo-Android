package com.droi.sdk.droifeedbackdemo;

import android.app.Application;

import com.droi.sdk.core.Core;
import com.droi.sdk.feedback.DroiFeedback;
import com.squareup.leakcanary.LeakCanary;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        Core.initialize(this);
        DroiFeedback.initialize(this,"GcurV5MnVBK3wsgHsu6MdQrqXJ5w6uP3P-SRStogfGhhmNgin2f_1m4MIv8CjAnE");
    }
}
