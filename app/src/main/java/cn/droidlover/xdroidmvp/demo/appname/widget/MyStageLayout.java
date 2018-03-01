package cn.droidlover.xdroidmvp.demo.appname.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.hippo.drawerlayout.DrawerLayoutChild;

import cn.droidlover.xdroidmvp.demo.commonui.scene.StageLayout;

/**
 * Created by wang on 2018/3/1.
 */

public class MyStageLayout extends StageLayout implements DrawerLayoutChild {
    private int mFitPaddingTop;
    public MyStageLayout(Context context) {
        super(context);
    }
    public MyStageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MyStageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setFitPadding(int top, int bottom) {
        mFitPaddingTop = top;
    }

    @Override
    public int getLayoutPaddingTop() {
        return mFitPaddingTop;
    }

    @Override
    public int getLayoutPaddingBottom() {
        return 0;
    }
}
