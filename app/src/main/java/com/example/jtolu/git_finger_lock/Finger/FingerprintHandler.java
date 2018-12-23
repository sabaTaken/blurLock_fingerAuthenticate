package com.example.jtolu.git_finger_lock.Finger;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.jtolu.git_finger_lock.MainActivity;
import com.example.jtolu.git_finger_lock.R;

/**
 * Created by whit3hawks on 11/16/16.
 */
@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback
{

    private Context context;
    private String from_where;

    // Constructor
    public FingerprintHandler(Context mContext, String from_where)
    {
        context = mContext;
        this.from_where = from_where;
    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject)
    {
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString)
    {
        WalletApplication.isLogin = false;
        this.update("خطا در شناسایی هویت");
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString)
    {
        WalletApplication.isLogin = false;
        this.update("مجددا تلاش نمایید");
    }

    @Override
    public void onAuthenticationFailed()
    {
        WalletApplication.isLogin = false;
        this.update("خطا در شناسایی هویت");
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result)
    {
        //        ((Activity) context).finish();
        //        Intent intent = new Intent(context, MainActivity.class);
        //        context.startActivity(intent);
        //        //Toast.makeText((Activity) context, "اثر انگشت فعال هست", Toast.LENGTH_SHORT).show();
        //        ((Activity) context).finish();
        WalletApplication.isLogin = true;

        if (!TextUtils.isEmpty(from_where) && from_where.equals("main"))
        {
            Intent intent = new Intent(context, FingerActivity.class);
            intent.putExtra("from_where", "fingerprint");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            ((Activity) context).startActivity(intent);
            ((Activity) context).finish();
        }
        else
        {
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            ((Activity) context).startActivity(intent);
            ((Activity) context).finish();
        }

    }

    private void update(String e)
    {
        TextView textView = (TextView) ((Activity) context).findViewById(R.id.tv_text_finger);
        textView.setText(e);
    }

}
