package com.muddzdev.regret;

import android.app.Application;

import io.paperdb.Paper;

class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Paper.init(this);
    }
}
