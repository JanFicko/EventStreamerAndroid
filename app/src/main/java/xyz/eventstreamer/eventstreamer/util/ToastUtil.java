package xyz.eventstreamer.eventstreamer.util;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import xyz.eventstreamer.eventstreamer.R;

public final class ToastUtil {

    /**
     * Toast the long text
     *
     * @param context
     * @param text
     */
    public static void toastLong(Context context, String text) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.view_toast_custom, null);

        TextView textView = layout.findViewById(R.id.tv_toast_text);
        textView.setText(text);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 375);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    /**
     * Toast the long text
     *
     * @param context
     * @param resId
     */
    public static void toastLong(Context context, int resId) {
        Resources res = context.getResources();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.view_toast_custom, null);

        TextView textView = layout.findViewById(R.id.tv_toast_text);
        textView.setText(res.getString(resId));

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 375);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    /**
     * Toast the short text
     *
     * @param context
     * @param resId
     */
    public static void toastShort(Context context, int resId) {
        Resources res = context.getResources();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.view_toast_custom, null);

        TextView textView = (TextView) layout.findViewById(R.id.tv_toast_text);
        textView.setText(res.getString(resId));

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 375);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    /**
     * Toast the short text
     *
     * @param context
     * @param text
     */
    public static void toastShort(Context context, String text) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.view_toast_custom, null);

        TextView textView = layout.findViewById(R.id.tv_toast_text);
        textView.setText(text);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 375);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public static void snackbarIndefinite(View view, @StringRes int text, @StringRes int action, View.OnClickListener callback){
        Snackbar errorSnackbar = Snackbar.make(view, text, Snackbar.LENGTH_INDEFINITE);
        errorSnackbar.setAction(action, callback);
        errorSnackbar.show();
    }

    public static void snackbarLong(View view, @StringRes int text, @StringRes int action, View.OnClickListener callback){
        Snackbar errorSnackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        errorSnackbar.setAction(action, callback);
        errorSnackbar.show();
    }

    public static void snackbarShort(View view, @StringRes int text, @StringRes int action, View.OnClickListener callback){
        Snackbar errorSnackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        errorSnackbar.setAction(action, callback);
        errorSnackbar.show();
    }
}
