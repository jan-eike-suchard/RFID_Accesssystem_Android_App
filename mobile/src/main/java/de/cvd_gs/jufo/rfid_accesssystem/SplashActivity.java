package de.cvd_gs.jufo.rfid_accesssystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImageView animatedLogo = findViewById(R.id.imageView2);
        animatedLogo.setBackgroundResource(R.drawable.wifi_animation_one);
        AnimationDrawable logoAnimation = (AnimationDrawable) animatedLogo.getBackground();
        logoAnimation.start();
        animatedLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getPreferences(MODE_PRIVATE);
                Boolean firstrun = preferences.getBoolean("firstrun", true);
                if (firstrun)
                {
                    Intent intent = new Intent(SplashActivity.this, SetupActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1500);

    }
}
