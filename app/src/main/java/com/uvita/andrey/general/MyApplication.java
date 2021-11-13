package com.uvita.andrey.general;


import android.app.Application;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.uvita.andrey.BuildConfig;
import com.uvita.andrey.modules.repository.local.AppDB;

import static com.uvita.andrey.general.LogUtil.LOGE;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize DB INSTANCE
        AppDB.createAppDatabase(this);
        Fresco.initialize(this);
        checkAndInitStetho();

        initData();

    }

    private void initData() {

    }

    private void checkAndInitStetho() {
        //Stetho integration // TODO check if this can be remove or Flipper still needs it?
        if (BuildConfig.DEBUG) {
            try {
                Stetho.initialize(
                        Stetho.newInitializerBuilder(MyApplication.this)
                                .enableDumpapp(Stetho.defaultDumperPluginsProvider(MyApplication.this))
                                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(MyApplication.this))

                                .build());
            } catch (Exception e) {
                LOGE("ERROR", "Error on checkAndInitStetho()", e);
            }
        }
    }

}
