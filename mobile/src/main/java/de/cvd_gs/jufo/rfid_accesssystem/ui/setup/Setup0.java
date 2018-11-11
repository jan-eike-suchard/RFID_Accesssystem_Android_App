package de.cvd_gs.jufo.rfid_accesssystem.ui.setup;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RadioGroup;

import de.cvd_gs.jufo.rfid_accesssystem.R;

public class Setup0 extends Fragment {

    public boolean automaticSetup;
    private SetupViewModel mViewModel;
    private ImageView imageTransmitting;
    private ImageView imageLock;
    private ImageView imagePhone;
    private ImageView logoCvD;
    private AlphaAnimation animationTransmittingIn = new AlphaAnimation(0.0f, 1.0f);
    private AlphaAnimation animationTransmittingOut = new AlphaAnimation(1.0f, 0.0f);
    private AlphaAnimation animationLockIn = new AlphaAnimation(0.0f, 1.0f);
    private AlphaAnimation animationLockOut = new AlphaAnimation(1.0f, 0.0f);
    private AlphaAnimation animationPhoneOut = new AlphaAnimation(1.0f, 0.0f);
    private AlphaAnimation animationLogoIn = new AlphaAnimation(0.0f, 1.0f);
    private boolean animationsShowed = false;

    public static Setup0 newInstance() {
        return new Setup0();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View setup0 = inflater.inflate(R.layout.setup_fragment0, container, false);

        RadioGroup setupType = setup0.findViewById(R.id.radioGroup);
        imageTransmitting = setup0.findViewById(R.id.imageView5);
        imageLock = setup0.findViewById(R.id.imageView6);
        logoCvD = setup0.findViewById(R.id.imageView7);
        imagePhone = setup0.findViewById(R.id.imageView4);
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
        mViewModel = ViewModelProviders.of(getActivity()).get(SetupViewModel.class);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!animationsShowed)
        {
            animationLockIn.setDuration(500);
            animationLockIn.setFillAfter(true);
            animationLockOut.setDuration(450);
            animationTransmittingIn.setDuration(500);
            animationTransmittingIn.setFillAfter(true);
            animationTransmittingOut.setDuration(450);
            animationPhoneOut.setDuration(450);
            animationLogoIn.setDuration(500);
            animationLogoIn.setFillAfter(true);

            animationTransmittingIn.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    imageLock.startAnimation(animationLockIn);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            animationLockIn.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    imageLock.startAnimation(animationLockOut);
                    imageTransmitting.startAnimation(animationTransmittingOut);
                    imagePhone.startAnimation(animationPhoneOut);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            animationPhoneOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    imagePhone.setVisibility(View.INVISIBLE);
                    logoCvD.startAnimation(animationLogoIn);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            imageTransmitting.startAnimation(animationTransmittingIn);
            animationsShowed = true;
        }
        else
        {
            animationsShowed = true;
        }
    }
}
