package de.cvd_gs.jufo.rfid_accesssystem.ui.setup;

import androidx.lifecycle.ViewModelProviders;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;

import de.cvd_gs.jufo.rfid_accesssystem.R;

public class Setup0 extends Fragment {

    public boolean automaticSetup;
    private SetupViewModel mViewModel;

    public static Setup0 newInstance() {
        return new Setup0();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View setup0 = inflater.inflate(R.layout.setup_fragment0, container, false);
        ImageView imageWireless = setup0.findViewById(R.id.animationViewWireless);
        imageWireless.setBackgroundResource(R.drawable.wifi_animation);
        AnimationDrawable animationWireless;
        animationWireless = (AnimationDrawable) imageWireless.getBackground();
        animationWireless.start();
        RadioGroup setupType = setup0.findViewById(R.id.radioGroup);
        setupType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonAuto)
                {
                   mViewModel.setAutoconfig(true);
                }
                else if (checkedId == R.id.radioButtonManual)
                {
                    mViewModel.setAutoconfig(false);
                }
            }
        });
        return setup0;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SetupViewModel.class);

    }

}
