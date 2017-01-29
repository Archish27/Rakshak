package com.markdevelopers.rakshak.common;

import android.app.Application;

import com.markdevelopers.rakshak.di.AppComponent;
import com.markdevelopers.rakshak.di.AppModule;
import com.markdevelopers.rakshak.di.DaggerAppComponent;

/**
 * Created by Archish on 1/10/2017.
 */

public class RakshakApp extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        initComponent();
    }

    private void initComponent() {
        component = DaggerAppComponent.builder().appModule(new AppModule()).build();
    }

    public AppComponent getComponent() {
        return component;
    }
}
