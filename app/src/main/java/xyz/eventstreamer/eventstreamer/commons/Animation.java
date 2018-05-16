package xyz.eventstreamer.eventstreamer.commons;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Animation {

    @IntDef({NONE, DOWN, UP, LEFT, RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationType {}

    public static final int NONE = 0;
    public static final int DOWN = 1;
    public static final int UP = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

}
