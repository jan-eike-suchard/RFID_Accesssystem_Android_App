package de.cvd_gs.jufo.rfid_accesssystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
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
}
