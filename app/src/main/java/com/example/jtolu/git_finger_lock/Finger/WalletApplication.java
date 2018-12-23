package com.example.jtolu.git_finger_lock.Finger;

import android.content.res.Configuration;

import com.example.jtolu.git_finger_lock.bio.G;

import java.util.Locale;

//import android.support.multidex.MultiDexApplication;

public class WalletApplication
{
    public static final String CONFIG = "config";
    public static boolean isLogin;
    public static boolean isFirstOpenForGettingUserBalance;
    public static boolean isComingFromAnotherApplication = false;
    private static WalletApplication mInstance;
//    public static WalletConfig mWalletConfig = new WalletConfig();
    private Locale locale = null;
    private G g;


    public void onCreate()
    {
        //        super.onCreate();
        mInstance = this;
        isFirstOpenForGettingUserBalance = true;
        readConfig();
        setLocale();
    }


    public void onConfigurationChanged(Configuration newConfig)
    {
        //        super.onConfigurationChanged(newConfig);
        if (locale != null)
        {
            newConfig.locale = locale;
            Locale.setDefault(locale);
            g.getMyContext().getResources().updateConfiguration(newConfig, g.getMyContext().getResources().getDisplayMetrics());
        }
    }

    private void setLocale()
    {
        Configuration config = g.getMyContext().getResources().getConfiguration();
        locale = new Locale(Locale.ENGLISH.getLanguage());
        Locale.setDefault(locale);
        config.locale = locale;
        g.getMyContext().getResources().updateConfiguration(config, g.getMyContext().getResources().getDisplayMetrics());
    }

    private void readConfig()
    {
       /* Gson gson = new Gson();
        String json = WalletSession.getSharedPreferences().getString(CONFIG, "");
        WalletConfig config = gson.fromJson(json, WalletConfig.class);
        if (config != null)
            mWalletConfig = config;
        else
        {
            saveConfig();
        }*/
    }

    public void saveConfig()
    {
        /*SharedPreferences.Editor prefsEditor = WalletSession.getSharedPreferences().edit();
        Gson gson = new Gson();
        String json = gson.toJson(mWalletConfig);
        prefsEditor.putString(CONFIG, json);
        prefsEditor.apply();*/
    }


    public void onTerminate()
    {
        saveConfig();
        //        super.onTerminate();
    }

   /* public WalletConfig getWalletConfig()
    {
        return mWalletConfig;
    }*/

    public static WalletApplication getInstance()
    {
        return mInstance;
    }
}
