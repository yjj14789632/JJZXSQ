package com.bn.Util;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorManager;


import java.lang.reflect.Field;

public class ContextUtil extends Application
{
    private static ContextUtil instance;

    public static ContextUtil getInstance()
    {
        return instance;
    }

    private static final String TAG = "MoblieApplication";

    //字体地址，一般放置在assets/fonts目录

    String fontPath = "fonts/PingFang_Light.ttf";
    public static Typeface typeFace;

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
        setTypeface();

    }

    public void setTypeface()
    {
        typeFace = Typeface.createFromAsset(getAssets(), fontPath);
        try
        {
            Field field_2 = Typeface.class.getDeclaredField("MONOSPACE");
            field_2.setAccessible(true);
            field_2.set(null, typeFace);

        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

}
