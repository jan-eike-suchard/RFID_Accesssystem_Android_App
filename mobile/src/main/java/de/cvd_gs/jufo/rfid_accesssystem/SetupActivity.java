package de.cvd_gs.jufo.rfid_accesssystem;

import androidx.lifecycle.ViewModelProviders;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.ArrayList;

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
        mViewModel = ViewModelProviders.of(this).get(SetupViewModel.class);
        final ArrayList<Fragment> fragmentsManual = new ArrayList<>();
        fragmentsManual.add(new Setup1());
        final ArrayList<Fragment> fragmentsAuto = new ArrayList<>();
        fragmentsAuto.add(new AutoSetup0());
        fragmentsAuto.add(new AutoSetup1());
        setContentView(R.layout.setup_activity);
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
                if (step+1 > 0) {
                    buttonBack.setEnabled(true);
                    buttonBack.setVisibility(View.VISIBLE);
                } else {
                    buttonBack.setEnabled(false);
                    buttonBack.setVisibility(View.INVISIBLE);

                }
                Boolean autoSetup = mViewModel.getAutoconfig();
                if (autoSetup) {
                    fragmentManager.beginTransaction().replace(fragmentContainer, fragmentsAuto.get(step)).commit();
                    progressBar.setProgress(step+1);
                    progressBar.setMax(3);
                    mViewModel.setCurrentStep(step+1);
                }
                else {
                    fragmentManager.beginTransaction().replace(fragmentContainer, fragmentsManual.get(step)).commit();
                    progressBar.setProgress(step+1);
                    mViewModel.setCurrentStep(step+1);
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
