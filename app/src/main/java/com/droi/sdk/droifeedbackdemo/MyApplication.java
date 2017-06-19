package com.droi.sdk.droifeedbackdemo;

import android.app.Application;

import com.droi.sdk.core.Core;
import com.droi.sdk.feedback.DroiFeedback;
import com.droi.sdk.feedback.GlideEngine;
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
        DroiFeedback.initialize(this, "_Alo_HOvf32ohDFdYVKz05DGhCYTpqPzKq78elkEx1iDjmOtPe_anOHQGA2TS4m9");
        DroiFeedback.setImageEngine(new GlideEngine());
    }
}
