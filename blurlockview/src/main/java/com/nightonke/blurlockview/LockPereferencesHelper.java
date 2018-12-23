package com.nightonke.blurlockview;

import android.content.Context;
import android.content.SharedPreferences;

public class LockPereferencesHelper
{
    public static final String IS_REGISTER = "isRegister";
    public static final String LOCK = "lock";
    public static final String PASSWORD = "password";
    private static Context mContext;

    public LockPereferencesHelper()
    {

    }

    public static boolean isRegister(Context context)
    {
        return getSharedPreferences(context).getBoolean(IS_REGISTER, false);
    }

    public static void setIsRegister(Context context,boolean isRegister)
    {
        getSharedPreferences(context).edit().putBoolean(IS_REGISTER, isRegister).apply();
    }

    public static String getPassword(Context context)
    {
        return getSharedPreferences(context).getString(PASSWORD, "");
    }

    public static void setPassword(Context context,String pass)
    {
        getSharedPreferences(context).edit().putString(PASSWORD, pass).apply();
    }

    public static SharedPreferences getSharedPreferences(Context context)
    {
        return context.getSharedPreferences(LOCK, Context.MODE_PRIVATE);
    }

    public static void init(Context context)
    {
        mContext = context;
    }

    public static void clear(Context context)
    {
        setIsRegister(context,false);
        setPassword(context,"");
    }
}
