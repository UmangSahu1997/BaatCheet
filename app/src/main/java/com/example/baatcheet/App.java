package com.example.baatcheet;

import android.app.Application;
import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("AEiVV540nmVQtOHQHf4aoYxepSsL4iSvAt9s6Nis")
                // if defined
                .clientKey("ERXxZKP8PVGrlHzo5jtppOv2qJDtExDonBZdZ7zu")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}