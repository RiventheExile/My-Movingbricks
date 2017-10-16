package com.my_movingbricks.utlis;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.my_movingbricks.R;

/**
 * Created by Nevermore on 16/7/3.
 */
public class Drawables {
    public static Drawable sPlaceholderDrawable;
    public static Drawable sErrorDrawable;

    private Drawables() {
    }

    public static void init(final Resources resources) {
        if (sPlaceholderDrawable == null) {
            sPlaceholderDrawable = resources.getDrawable(R.mipmap.img_default_meizi);
        }
        if (sErrorDrawable == null) {
            sErrorDrawable = resources.getDrawable(R.mipmap.ic_avatar_default);
        }
    }
}
