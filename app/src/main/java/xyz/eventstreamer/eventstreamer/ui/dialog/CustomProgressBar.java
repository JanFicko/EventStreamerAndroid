package xyz.eventstreamer.eventstreamer.ui.dialog;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import xyz.eventstreamer.eventstreamer.R;

public class CustomProgressBar extends ProgressBar {
    public CustomProgressBar(Context context) {
        super(context);
        init(context);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable wrapDrawable = DrawableCompat.wrap(this.getIndeterminateDrawable());
            DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(context, R.color.blue_light));
            this.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
        } else {
            this.getIndeterminateDrawable().setColorFilter(
                    ContextCompat.getColor(context, R.color.blue_light), PorterDuff.Mode.SRC_IN);
        }
    }

}
