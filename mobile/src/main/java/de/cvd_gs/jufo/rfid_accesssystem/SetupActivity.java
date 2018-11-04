package de.cvd_gs.jufo.rfid_accesssystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import de.cvd_gs.jufo.rfid_accesssystem.ui.setup.AutoSetup0;
import de.cvd_gs.jufo.rfid_accesssystem.ui.setup.AutoSetup1;
import de.cvd_gs.jufo.rfid_accesssystem.ui.setup.Setup0;
import de.cvd_gs.jufo.rfid_accesssystem.ui.setup.Setup1;
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
                boolean autoConfiguration = mViewModel.getAutoconfig();
                if (autoConfiguration)
                {
                    switch (step)
                    {
                        case 0:
                            fragmentManager.beginTransaction().replace(fragmentContainer, new AutoSetup0());
                            break;
                        case 1:
                            fragmentManager.beginTransaction().replace(fragmentContainer, new AutoSetup1());
                            buttonForward.setText(R.string.finishSetup);
                            buttonBack.setEnabled(false);
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
                }
                else
                {
                    switch (step)
                    {
                        case 0:
                            fragmentManager.beginTransaction().replace(fragmentContainer, new Setup1());
                            break;
                        case 1:
                            fragmentManager.beginTransaction().replace(fragmentContainer, new Setup2());
                            break;
                        case 2:
                            fragmentManager.beginTransaction().replace(fragmentContainer, new Setup3());
                            break;
                        case 3:
                            fragmentManager.beginTransaction().replace(fragmentContainer, new Setup4());
                            break;
                        case 4:
                            fragmentManager.beginTransaction().replace(fragmentContainer, new Setup5());
                            break;
                        case 5:
                            fragmentManager.beginTransaction().replace(fragmentContainer, new Setup6());
                            break;
                    }
                }
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int step = mViewModel.getCurrentStep();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
