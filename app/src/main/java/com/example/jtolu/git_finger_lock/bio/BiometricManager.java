package com.example.jtolu.git_finger_lock.bio;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;


public class BiometricManager extends BiometricManagerV23
{


    public BiometricManager(final BiometricBuilder biometricBuilder) {
        this.context = biometricBuilder.context;

    }


    public void authenticate(@NonNull final BiometricCallback biometricCallback) {

        if(!BiometricUtils.isSdkVersionSupported()) {
            biometricCallback.onSdkVersionNotSupported();
        }

        if(!BiometricUtils.isPermissionGranted(context)) {
            biometricCallback.onBiometricAuthenticationPermissionNotGranted();
        }

        if(!BiometricUtils.isHardwareSupported(context)) {
            biometricCallback.onBiometricAuthenticationNotSupported();
        }

        if(!BiometricUtils.isFingerprintAvailable(context)) {
            biometricCallback.onBiometricAuthenticationNotAvailable();
        }

        displayBiometricDialog(biometricCallback);
    }



    public void displayBiometricDialog(BiometricCallback biometricCallback) {
        if(BiometricUtils.isBiometricPromptEnabled()) {
            displayBiometricPrompt(biometricCallback);
        } else {
            displayBiometricPromptV23(biometricCallback);
        }
    }



    @SuppressLint("MissingPermission")
    @TargetApi(Build.VERSION_CODES.P)
    private void displayBiometricPrompt(final BiometricCallback biometricCallback) {
        new BiometricPrompt.Builder(context)
                .setNegativeButton(negativeButtonText, context.getMainExecutor(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        biometricCallback.onAuthenticationCancelled();
                    }
                })
                .build()
                .authenticate(new CancellationSignal(), context.getMainExecutor(),
                        new BiometricCallbackV28(biometricCallback));
    }


    public static class BiometricBuilder {


        private Context context;
        public BiometricBuilder(Context context) {
            this.context = context;
        }


        public BiometricManager build() {
            return new BiometricManager(this);
        }
    }
}
