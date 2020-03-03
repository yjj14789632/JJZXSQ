package com.bn.Util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;

import static android.content.Context.MODE_WORLD_WRITEABLE;
import static android.os.ParcelFileDescriptor.MODE_WORLD_READABLE;

/**
 * Created by Administrator on 2017/4/3.
 */

public class UserInfoUtil
{
    public static SharedPreferences sp = ContextUtil.getInstance().getSharedPreferences("UID", Context.MODE_MULTI_PROCESS |MODE_WORLD_WRITEABLE);
    //存入数据
    static SharedPreferences.Editor editor = sp.edit();

    public static void WriteUid(String uid)
    {

        editor.putString("uid", uid);
        editor.commit();
    }

    public static String GetUid()
    {
        return sp.getString("uid",null);
    }

    public static void WriteUname(String uname)
    {
        editor.putString("uname", uname);
        editor.commit();
    }

    public static String GetUname()
    {
        return sp.getString("uname",null);
    }

    public static void WriteUhead(String uhead)
    {
        editor.putString("uhead", uhead);
        editor.commit();
    }

    public static String GetUhead()
    {
        return sp.getString("uhead",null);
    }

    public static void WriteStep(int stepSum)
    {
        Calendar c= Calendar.getInstance();
        String stepStr=c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE);
        if(stepStr.compareTo(sp.getString("STEP_DATE",stepStr))==0)
        {
            editor.putString("STEP_DATE",stepStr);
            editor.putString("StepSum", Integer.parseInt(sp.getString("StepSum","0"))+stepSum+"");
        }
        else
        {
            editor.putString("STEP_DATE",stepStr);
            editor.putString("StepSum",stepSum+"");
        }
        editor.commit();
    }
    public static String GetStep()
    {
        SharedPreferences sharedPreferences =
                ContextUtil.getInstance().getSharedPreferences("UID", Context.MODE_MULTI_PROCESS |
                        MODE_WORLD_READABLE);
        return sharedPreferences.getString("StepSum","0");
    }
}

