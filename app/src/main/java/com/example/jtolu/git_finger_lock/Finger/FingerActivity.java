package com.example.jtolu.git_finger_lock.Finger;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jtolu.git_finger_lock.R;
import com.example.jtolu.git_finger_lock.bio.BiometricCallback;
import com.example.jtolu.git_finger_lock.bio.BiometricManager;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class FingerActivity extends AppCompatActivity implements BiometricCallback
{

    private Cipher cipher;
    private KeyStore keyStore;
    private static final String KEY_NAME = "androidHive";
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger);
        textView = findViewById(R.id.tv_text_finger);

        checkFingerDevice();


    }

    private void checkFingerDevice()
    {
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        FingerprintManager fingerprintManager = null;

        // Initializing both Android Keyguard Manager and Fingerprint Manager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

            // Check whether the device has a Fingerprint sensor.
            if (fingerprintManager.isHardwareDetected())
            {

                // Checks whether fingerprint permission is set on manifest
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED)
                {
                    textView.setText("مجوز شناسایی هویت با اثر انگشت غیر فعال است");
                }
                else
                {
                    // Check whether at least one fingerprint is registered
                    if (!fingerprintManager.hasEnrolledFingerprints())
                    {
                        textView.setText("لطفا یک اثر انگشت در بخش تنظیمات دستگاه ثبت کنید");
                    }

                    // Checks whether lock screen security is enabled or not
                    else if (!keyguardManager.isKeyguardSecure())
                    {
                        textView.setText("قفل بخش تنظیمات اثر انگشت فعال است");
                    }
                    else
                    {
                        textView.setText("لطفا اثر انگشت وارد کنید");
                        BiometricManager.BiometricBuilder builder = new BiometricManager.BiometricBuilder(FingerActivity.this);
                        builder.build();
                        BiometricManager manager = new BiometricManager(builder);
                        manager.authenticate(FingerActivity.this);
                    }

                }

            }
            else
            {
                textView.setText("دستگاه شما ، اثر انگشت را پشتیبانی نمی کند");
            }

        }
        else
        {
            textView.setText("ورژن اندروید دستگاه شما پایین می باشد");
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    protected void generateKey()
    {
        try
        {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        KeyGenerator keyGenerator;
        try
        {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        }
        catch (NoSuchAlgorithmException | NoSuchProviderException e)
        {
            throw new RuntimeException("Failed to get KeyGenerator instance", e);
        }

        try
        {
            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        }
        catch (NoSuchAlgorithmException |
                InvalidAlgorithmParameterException
                | CertificateException | IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean cipherInit()
    {
        try
        {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException e)
        {
            throw new RuntimeException("Failed to get Cipher", e);
        }
        try
        {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        }
        catch (KeyPermanentlyInvalidatedException e)
        {
            return false;
        }
        catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e)
        {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    @Override
    public void onSdkVersionNotSupported()
    {
        Toast.makeText(getApplicationContext(), "version fail", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationNotSupported()
    {
        Toast.makeText(getApplicationContext(), "version fail", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationNotAvailable()
    {
        Toast.makeText(getApplicationContext(), "no bio metric", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationPermissionNotGranted()
    {
        Toast.makeText(getApplicationContext(), "bio metric permission", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationInternalError(String error)
    {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationFailed()
    {
        Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationCancelled()
    {
        Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationSuccessful()
    {
        Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
        onBackPressed();
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString)
    {
        Toast.makeText(getApplicationContext(), helpString, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString)
    {
        Toast.makeText(getApplicationContext(), errString, Toast.LENGTH_LONG).show();
    }

}
