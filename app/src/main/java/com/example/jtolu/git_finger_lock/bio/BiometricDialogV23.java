package com.example.jtolu.git_finger_lock.bio;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;


public class BiometricDialogV23 extends Dialog {

    private Context context;

    public BiometricDialogV23(@NonNull Context context) {
        super(context);
        this.context = context.getApplicationContext();
    }

}
