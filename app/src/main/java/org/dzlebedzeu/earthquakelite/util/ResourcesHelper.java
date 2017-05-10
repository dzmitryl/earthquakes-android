package org.dzlebedzeu.earthquakelite.util;

import android.content.Context;
import android.os.Build;

public class ResourcesHelper {

    /**
     * Method returns a color int calling appropriate resources method depending on OS version
     * @param context Context
     * @param colorId Resources ID of the color
     * @return Color int value
     */
    public static int getColorForColorId(Context context, int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getResources().getColor(colorId, context.getTheme());
        } else {
            return context.getResources().getColor(colorId);
        }
    }
}
