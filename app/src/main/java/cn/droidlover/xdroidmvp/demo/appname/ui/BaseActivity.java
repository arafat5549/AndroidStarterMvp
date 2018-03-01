package cn.droidlover.xdroidmvp.demo.appname.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by wang on 2018/3/1.
 */

public class BaseActivity  extends AppCompatActivity{
    private boolean mTrackStarted;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // ((MyApplication) getApplication()).registerActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

       // ((MyApplication) getApplication()).unregisterActivity(this);
    }

    @Override
    public void onStart() {
        super.onStart();

//        if (Settings.getEnableAnalytics()) {
//            EasyTracker.getInstance(this).activityStart(this);
//            mTrackStarted = true;
//        }
    }

    @Override
    public void onStop() {
        super.onStop();

//        if (mTrackStarted) {
//            EasyTracker.getInstance(this).activityStop(this);
//            mTrackStarted = false;
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if(Settings.getEnabledSecurity()){
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
//                    WindowManager.LayoutParams.FLAG_SECURE);
//        }else{
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
//        }
    }
}
