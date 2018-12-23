package com.example.jtolu.git_finger_lock.showLock;

import android.content.Intent;
import android.graphics.Typeface;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.nightonke.blurlockview.BlurLockView;
import com.nightonke.blurlockview.Directions.HideType;
import com.nightonke.blurlockview.Directions.ShowType;
import com.nightonke.blurlockview.Eases.EaseType;
import com.nightonke.blurlockview.Password;

public interface ShowLockActivityInterface {
    interface View
    {
        void setPresenter_View(Presenter presenter);
        void register_View();
        void login_View();
        BlurLockView initBlurLockViewRegister_View(BlurLockView blurLockView, ImageView imageView);
        BlurLockView initBlurLockViewLogin_View(BlurLockView blurLockView, ImageView imageView);
        void clickListenerOnPassword_View();
        void onClickImageView_View();
        void passwordListenerCorrect_View();
        void passwordListenerInCorrect_View();
        void blurLockViewAddView_View(FrameLayout layout, ImageView imageView);
    }

    interface Presenter
    {
        void register_Presenter();
        void login_Presenter();
        //        Activity getAppActivity_Presenter();
        Password getPasswordType_Presenter(Intent intent);
        Typeface getTypeface_Presenter();
        ShowType getShowType_Presenter(int p);
        HideType getHideType_Presenter(int p);
        EaseType getEaseType_Presenter(int p);
        BlurLockView initBlurLockViewRegister_Presenter(BlurLockView blurLockView, ImageView imageView);
        BlurLockView initBlurLockViewLogin_Presenter(BlurLockView blurLockView, ImageView imageView);
        void clickListenerOnPassword_Presenter();
        void onClickImageView_Presenter();
        void passwordListenerCorrect_Presenter();
        void passwordListenerInCorrect_Presenter();
        void blurLockViewAddView_Presenter(FrameLayout layout, ImageView imageView);
    }
}
