package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Register our parse model
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("4WMf80GMHPWsZocGofaYI7e6wWzSF5txvIJPsF7m")
                .clientKey("fa73nEittDVIJFFmwOIrf7l3ZessfTrovkJvlDGn")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
