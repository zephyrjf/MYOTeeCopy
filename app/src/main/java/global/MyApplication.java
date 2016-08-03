package global;

import android.app.Application;
import android.content.Context;

import helper.DataHelper;

/**
 * Created by Zephyr on 2016/7/24.
 */
public class MyApplication extends Application {
    private static Context instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = getApplicationContext();
        DataHelper.getInstance().initIconData();
    }

    public static Context getAppContext() {
        return instance;
    }
}
