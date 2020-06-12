package com.example.financial_savings.modules.icons;

import android.content.Context;

public class IconsDrawableModule {

    public static int getResourcesDrawble(Context context, String name) {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }
}
