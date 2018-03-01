package cn.droidlover.xdroidmvp.demo.appname.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.demo.R;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * Created by wang on 2018/3/1.
 */

public class WYYActivity extends XActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;



    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
        getSupportActionBar().setTitle("WYYY");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initToolbar();
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_wyy;
    }
    @Override
    public Object newP() {
        return null;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
