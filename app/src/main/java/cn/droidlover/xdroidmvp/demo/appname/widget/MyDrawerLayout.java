package cn.droidlover.xdroidmvp.demo.appname.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.hippo.drawerlayout.DrawerLayout;
import com.hippo.yorozuya.AnimationUtils;


import java.util.ArrayList;
import java.util.List;

import cn.droidlover.xdroidmvp.demo.R;

/**
 * Created by wang on 2018/3/1.
 */

@CoordinatorLayout.DefaultBehavior(MyDrawerLayout.Behavior.class)
public class MyDrawerLayout extends DrawerLayout {
    private List<View> mAboveSnackViewList;

    public MyDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public MyDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void addAboveSnackView(View view) {
        if (null == mAboveSnackViewList) {
            mAboveSnackViewList = new ArrayList<>();
        }
        mAboveSnackViewList.add(view);
    }

    public void removeAboveSnackView(View view) {
        if (null == mAboveSnackViewList) {
            return;
        }
        mAboveSnackViewList.remove(view);
    }

    public int getAboveSnackViewCount() {
        return null == mAboveSnackViewList ? 0 : mAboveSnackViewList.size();
    }

    @Nullable
    public View getAboveSnackViewAt(int index) {
        if (null == mAboveSnackViewList || index < 0 || index >= mAboveSnackViewList.size()) {
            return null;
        } else {
            return mAboveSnackViewList.get(index);
        }
    }

    public static class Behavior extends CoordinatorLayout.Behavior<MyDrawerLayout> {
        // We only support the FAB <> Snackbar shift movement on Honeycomb and above. This is
        // because we can use view translation properties which greatly simplifies the code.
        private static final boolean SNACKBAR_BEHAVIOR_ENABLED = Build.VERSION.SDK_INT >= 11;

        @Override
        public boolean layoutDependsOn(CoordinatorLayout parent,
                                       MyDrawerLayout child, View dependency) {
            // We're dependent on all SnackbarLayouts (if enabled)
            return SNACKBAR_BEHAVIOR_ENABLED && dependency instanceof Snackbar.SnackbarLayout;
        }

        @Override
        public boolean onDependentViewChanged(CoordinatorLayout parent, MyDrawerLayout child,
                                              View dependency) {
            if (dependency instanceof Snackbar.SnackbarLayout) {
                for (int i = 0, n = child.getAboveSnackViewCount(); i < n; i++) {
                    View view = child.getAboveSnackViewAt(i);
                    updateChildTranslationForSnackbar(parent, child, view);
                }
            }
            return false;
        }

        private void updateChildTranslationForSnackbar(CoordinatorLayout parent,
                                                       MyDrawerLayout view, final View child) {
            final float targetTransY = getChildTranslationYForSnackbar(parent, view);
            float childTranslationY = 0.0f;
            Object obj = child.getTag(R.id.fab_translation_y);
            if (obj instanceof Float) {
                childTranslationY = (Float) obj;
            }
            if (childTranslationY == targetTransY) {
                // We're already at (or currently animating to) the target value, return...
                return;
            }

            ValueAnimator fabTranslationYAnimator = null;
            obj = child.getTag(R.id.fab_translation_y_animator);
            if (obj instanceof ValueAnimator) {
                fabTranslationYAnimator = (ValueAnimator) obj;
            }
            // Make sure that any current animation is cancelled
            if (fabTranslationYAnimator != null && fabTranslationYAnimator.isRunning()) {
                fabTranslationYAnimator.cancel();
            }

            final float currentTransY = ViewCompat.getTranslationY(child);

            if (child.isShown()
                    && Math.abs(currentTransY - targetTransY) > (child.getHeight() * 0.667f)) {
                // If the FAB will be travelling by more than 2/3 of it's height, let's animate
                // it instead
                if (fabTranslationYAnimator == null) {
                    fabTranslationYAnimator = ValueAnimator.ofFloat();
                    fabTranslationYAnimator.setInterpolator(AnimationUtils.FAST_SLOW_INTERPOLATOR);
                    fabTranslationYAnimator.addUpdateListener(
                            new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    ViewCompat.setTranslationY(child, (Float) animation.getAnimatedValue());
                                }
                            });
                    child.setTag(R.id.fab_translation_y_animator, fabTranslationYAnimator);
                }
                fabTranslationYAnimator.setFloatValues(currentTransY, targetTransY);
                fabTranslationYAnimator.start();
            } else {
                // Now update the translation Y
                ViewCompat.setTranslationY(child, targetTransY);
            }

            child.setTag(R.id.fab_translation_y, targetTransY);
        }

        private float getChildTranslationYForSnackbar(CoordinatorLayout parent,
                                                      MyDrawerLayout child) {
            float minOffset = 0;
            final List<View> dependencies = parent.getDependencies(child);
            for (int i = 0, z = dependencies.size(); i < z; i++) {
                final View view = dependencies.get(i);
                if (view instanceof Snackbar.SnackbarLayout && parent.doViewsOverlap(child, view)) {
                    minOffset = Math.min(minOffset,
                            ViewCompat.getTranslationY(view) - view.getHeight());
                }
            }

            return minOffset;
        }

    }
}
