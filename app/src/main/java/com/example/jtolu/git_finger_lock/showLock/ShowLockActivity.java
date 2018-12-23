package com.example.jtolu.git_finger_lock.showLock;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jtolu.git_finger_lock.Finger.WalletApplication;
import com.example.jtolu.git_finger_lock.MainActivity;
import com.example.jtolu.git_finger_lock.R;
import com.nightonke.blurlockview.BlurLockView;
import com.nightonke.blurlockview.Password;

public class ShowLockActivity extends AppCompatActivity implements ShowLockActivityInterface.View
{
//    public static final String IS_LOGIN = "is_login";
//    public static final String TITLE = "TITLE";
    private BlurLockView blurLockView;
    private ImageView imageView1;

    ShowLockActivityInterface.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        presenter = new ShowLockActivityPresenter(getApplicationContext(), this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        blurLockView = new BlurLockView(this);

        if (blurLockView.isRegister())
        {
            presenter.login_Presenter();
        }
        else
        {
            presenter.register_Presenter();
        }
    }

    @Override
    public void setPresenter_View(ShowLockActivityInterface.Presenter presenter)
    {
        this.presenter = presenter;
    }

    @Override
    public void register_View()
    {
        FrameLayout layout = new FrameLayout(this);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.blure_background_image);
//        imageView.setBackgroundColor(getResources().getColor(R.color.mainColor));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);


        presenter.initBlurLockViewRegister_Presenter(blurLockView, imageView);

        blurLockView.setOnPasswordInputListener(new BlurLockView.OnPasswordInputListener()
        {
            @Override
            public void correct(String inputPassword)
            {
                presenter.passwordListenerCorrect_Presenter();
            }

            @Override
            public void incorrect(String inputPassword)
            {
                presenter.passwordListenerInCorrect_Presenter();
            }

            @Override
            public void input(String inputPassword)
            {

            }
        });

        presenter.blurLockViewAddView_Presenter(layout, imageView);

    }

    @Override
    public void passwordListenerCorrect_View()
    {
        WalletApplication.isLogin = true;
        Toast.makeText(getApplicationContext(), R.string.show_lock_pin_code_created_successfully, Toast.LENGTH_SHORT).show();
        if (!TextUtils.isEmpty(getFromWhere()))
        {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            Bundle from_where= new Bundle();
            from_where.putString("from_where" ,  "show_lock");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtras(from_where);
            //intent.putExtra("from_where" ,  "show_lock");
            startActivity(intent);
        }
        else
        {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        finish();
    }

    @Override
    public void passwordListenerInCorrect_View()
    {
        WalletApplication.isLogin = false;
        Toast.makeText(getApplicationContext(), R.string.show_lock_try_again, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void blurLockViewAddView_View(FrameLayout layout, ImageView imageView)
    {
        layout.addView(imageView);
        layout.addView(blurLockView);
        setContentView(layout);
    }

    @Override
    public void login_View()
    {
        setContentView(R.layout.activity_lock);

        imageView1 = findViewById(R.id.image_1);
        blurLockView = findViewById(R.id.blurlockview);

        presenter.initBlurLockViewLogin_Presenter(blurLockView, imageView1);

        blurLockView.setOnLeftButtonClickListener(new BlurLockView.OnLeftButtonClickListener()
        {
            @Override
            public void onClick()
            {

            }
        });
        blurLockView.setOnPasswordInputListener(new BlurLockView.OnPasswordInputListener()
        {
            @Override
            public void correct(String inputPassword)
            {
                presenter.clickListenerOnPassword_Presenter();
            }

            @Override
            public void incorrect(String inputPassword)
            {

                WalletApplication.isLogin = false;
            }

            @Override
            public void input(String inputPassword)
            {

            }
        });

        imageView1.setOnClickListener(new View.OnClickListener()
                                      {
                                          @Override
                                          public void onClick(View v)
                                          {
                                              presenter.onClickImageView_Presenter();
                                          }
                                      }
        );
    }

    @Override
    public BlurLockView initBlurLockViewRegister_View(BlurLockView blurLockView, ImageView imageView)
    {
        blurLockView.setBlurredView(imageView);

        blurLockView.setType(Password.NUMBER, false);
        blurLockView.setTitle(getString(R.string.show_lock_pin_code_title));
        blurLockView.setTypeface(presenter.getTypeface_Presenter());
        blurLockView.show(
                getIntent().getIntExtra("SHOW_DURATION", 1000),
                presenter.getShowType_Presenter(getIntent().getIntExtra("SHOW_DIRECTION", 0)),
                presenter.getEaseType_Presenter(getIntent().getIntExtra("SHOW_EASE_TYPE", 30)));
        return blurLockView;
    }

    @Override
    public void onClickImageView_View()
    {
        blurLockView.show(ShowLockActivity.this.getIntent().getIntExtra("SHOW_DURATION", 1000),
                presenter.getShowType_Presenter(ShowLockActivity.this.getIntent().getIntExtra("SHOW_DIRECTION", 0)),
                presenter.getEaseType_Presenter(ShowLockActivity.this.getIntent().getIntExtra("SHOW_EASE_TYPE", 30)));
    }

    @Override
    public void clickListenerOnPassword_View()
    {
        WalletApplication.isLogin = true;
        if (getFromWhere().equals("main"))
        {
           /* Intent intent = new Intent(getApplicationContext(), ConfigActivity.class);
            //intent.putExtra("from_where" ,  "show_lock");
            Bundle from_where= new Bundle();
            from_where.putString("from_where" ,  "show_lock");
            intent.putExtras(from_where);
            startActivity(intent);*/
        }
        else
        {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        finish();
    }

    public BlurLockView initBlurLockViewLogin_View(BlurLockView blurLockView, ImageView imageView)
    {
        // Set the view that need to be blurred
        blurLockView.setBlurredView(imageView);

        // Set the password
        //        blurLockView.setCorrectPassword(LockPereferencesHelper.getPassword());

        blurLockView.setTitle(getString(R.string.show_lock_enter_pin_code));
        //            blurLockView.setLeftButton(getIntent().getStringExtra("LEFT_BUTTON"));
        //            blurLockView.setRightButton(getIntent().getStringExtra("RIGHT_BUTTON"));
        blurLockView.setTypeface(presenter.getTypeface_Presenter());
        blurLockView.setType(presenter.getPasswordType_Presenter(new Intent(this, ShowLockActivityPresenter.class)), false);
        return blurLockView;
    }

    private String getFromWhere()
    {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            return bundle.getString("from_where");
        }
        return "";
    }

    @Override
    protected void onResume() {
        super.onResume();
        // در صورتیکه در حینِ ست کردنِ رمز، اپ از background به foreground آورده شود
     /*   if (!blurLockView.isRegister())
            WalletApplication.mWalletConfig.setShowLock(true).saveConfig();*/

    }

    @Override
    protected void onStop() {
        super.onStop();
        // در صورتیکه در حینِ ست کردنِ رمز، اپ به background برود
      /*  if (!blurLockView.isRegister())
            WalletApplication.mWalletConfig.setShowLock(false).saveConfig();*/
    }
}
