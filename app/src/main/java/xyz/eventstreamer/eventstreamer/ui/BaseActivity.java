package xyz.eventstreamer.eventstreamer.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import xyz.eventstreamer.eventstreamer.R;
import xyz.eventstreamer.eventstreamer.commons.Animation;

public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = setLayoutResId();
        if (layoutId != 0) {
            setContentView(layoutId);
        }
        TAG = this.getClass().getSimpleName();
    }

    @LayoutRes
    protected abstract int setLayoutResId();

    public void moveToNextFragment(@NonNull Fragment fragment, @Animation.AnimationType int animationType) {
        moveToNextFragment(getSupportFragmentManager(), fragment, R.id.content_container, animationType);
    }

    protected void moveToNextFragment(@NonNull FragmentManager fragmentManager,
                                      @NonNull Fragment fragment, int frameId, @Animation.AnimationType int animationType) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (animationType) {
            case Animation.DOWN:
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_down, R.anim.slide_out_down);
                break;
            case Animation.UP:
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                break;
            case Animation.LEFT:
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case Animation.RIGHT:
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right);
                break;
            case Animation.NONE:
            default:
                /* No animation. */
        }
        fragmentTransaction.replace(frameId, fragment, fragment.getClass().getSimpleName());
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();
    }

}
