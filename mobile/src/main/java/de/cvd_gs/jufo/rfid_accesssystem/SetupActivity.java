package de.cvd_gs.jufo.rfid_accesssystem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import de.cvd_gs.jufo.rfid_accesssystem.ui.setup.AutoSetup0;
import de.cvd_gs.jufo.rfid_accesssystem.ui.setup.AutoSetup1;
import de.cvd_gs.jufo.rfid_accesssystem.ui.setup.Setup0;
import de.cvd_gs.jufo.rfid_accesssystem.ui.setup.Setup1;
import de.cvd_gs.jufo.rfid_accesssystem.ui.setup.Setup2;
import de.cvd_gs.jufo.rfid_accesssystem.ui.setup.Setup3;
import de.cvd_gs.jufo.rfid_accesssystem.ui.setup.Setup4;
import de.cvd_gs.jufo.rfid_accesssystem.ui.setup.Setup5;
import de.cvd_gs.jufo.rfid_accesssystem.ui.setup.Setup6;
import de.cvd_gs.jufo.rfid_accesssystem.ui.setup.SetupViewModel;

public class SetupActivity extends AppCompatActivity {
    public Button buttonForward;
    public Button buttonBack;
    private SetupViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_activity);
        mViewModel = ViewModelProviders.of(this).get(SetupViewModel.class);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Setup0.newInstance())
                    .commitNow();
        }
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final int fragmentContainer = R.id.container;
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        buttonForward = findViewById(R.id.buttonForward);
        buttonBack = findViewById(R.id.buttonBack);
        buttonForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int step = mViewModel.getCurrentStep();
                Log.d("CURRENT STEP", String.valueOf(step));
                boolean autoConfiguration = mViewModel.getAutoconfig();
                Log.d("Automatic config status", String.valueOf(autoConfiguration));
                if (autoConfiguration)
                {
                    switch (step)
                    {
                        case 0:
                            fragmentManager.beginTransaction().replace(fragmentContainer, new AutoSetup0()).commit();
                            buttonBack.setEnabled(true);
                            break;
                        case 1:
                            fragmentManager.beginTransaction().replace(fragmentContainer, new AutoSetup1()).commit();
                            buttonForward.setText(R.string.finishSetup);
                            buttonBack.setEnabled(true);
                            break;
                        case 2:
                            Toast.makeText(getApplicationContext(), R.string.errorSteps, Toast.LENGTH_LONG).show();
                            Intent restartApp = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                            restartApp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            restartApp.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(restartApp);
                            finish();
                            break;
                    }
                    step++;
                    mViewModel.setCurrentStep(step);
                }
                else
                {
                    switch (step)
                    {
                        case 0:
                            fragmentManager.beginTransaction().replace(fragmentContainer, new Setup1()).commit();
                            buttonBack.setEnabled(true);
                            break;
                        case 1:
                            fragmentManager.beginTransaction().replace(fragmentContainer, new Setup2()).commit();
                            buttonBack.setEnabled(true);
                            break;
                        case 2:
                            fragmentManager.beginTransaction().replace(fragmentContainer, new Setup3()).commit();
                            buttonBack.setEnabled(true);
                            break;
                        case 3:
                            fragmentManager.beginTransaction().replace(fragmentContainer, new Setup4()).commit();
                            buttonBack.setEnabled(true);
                            break;
                        case 4:
                            fragmentManager.beginTransaction().replace(fragmentContainer, new Setup5()).commit();
                            buttonBack.setEnabled(true);
                            break;
                        case 5:
                            fragmentManager.beginTransaction().replace(fragmentContainer, new Setup6()).commit();
                            buttonForward.setText(R.string.finishSetup);
                            buttonBack.setEnabled(true);
                            break;
                        case 6:
                            Intent restartApp = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                            restartApp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            restartApp.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(restartApp);
                            finish();

                    }
                    step++;
                    mViewModel.setCurrentStep(step);
                }
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int step = mViewModel.getCurrentStep();
                Log.d("CURRENT STEP", String.valueOf(step));
                boolean autoConfiguration = mViewModel.getAutoconfig();
                Log.d("Automatic config status", String.valueOf(autoConfiguration));
                if (autoConfiguration)
                {
                    switch (step)
                    {
                        case 0:
                            break;
                        case 1:
                            fragmentManager.beginTransaction().replace(fragmentContainer, new Setup0()).commit();
                            break;
                        case 2:
                            fragmentManager.beginTransaction().replace(fragmentContainer, new AutoSetup0()).commit();
                            break;
                    }
                    step--;
                    mViewModel.setCurrentStep(step);
                }
                else
                {
                    switch (step)
                    {
                        case 1:
                            fragmentManager.beginTransaction().replace(fragmentContainer, new Setup0()).commit();
                            buttonBack.setEnabled(false);
                            break;
                        case 2:
                            fragmentManager.beginTransaction().replace(fragmentContainer, new Setup1()).commit();
                            buttonBack.setEnabled(true);
                            break;
                        case 3:
                            fragmentManager.beginTransaction().replace(fragmentContainer, new Setup2()).commit();
                            buttonBack.setEnabled(true);
                            break;
                        case 4:
                            fragmentManager.beginTransaction().replace(fragmentContainer, new Setup3()).commit();
                            buttonBack.setEnabled(true);
                            break;
                        case 5:
                            fragmentManager.beginTransaction().replace(fragmentContainer, new Setup4()).commit();
                            buttonBack.setEnabled(true);
                            break;
                        case 6:
                            fragmentManager.beginTransaction().replace(fragmentContainer, new Setup5()).commit();
                            buttonForward.setText(R.string.finishSetup);
                            buttonBack.setEnabled(true);
                            break;

                    }
                    step--;
                    mViewModel.setCurrentStep(step);
                }
            }
        });
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] {Manifest.permission.CAMERA}, 1);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case 1:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Snackbar.make(this.findViewById(R.id.setupLayout), R.string.cameraPermissionGranted, Snackbar.LENGTH_LONG).show();

                }
                else
                {
                    Snackbar.make(this.findViewById(R.id.setupLayout), R.string.cameraPermission, Snackbar.LENGTH_LONG).show();
                    Log.w("PERMISSION ERROR", "camera_denied");
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new Setup1()).commit();
                    mViewModel.setAutoconfig(false);
                    mViewModel.setCurrentStep(1);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
