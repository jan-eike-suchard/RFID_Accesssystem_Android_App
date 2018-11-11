package de.cvd_gs.jufo.rfid_accesssystem.ui.setup;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.cvd_gs.jufo.rfid_accesssystem.R;

public class Setup4 extends Fragment {
    private SetupViewModel mViewModel;
    public static Setup4 newInstance() {
        return new Setup4();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View setup4 = inflater.inflate(R.layout.setup_fragment4, container, false);
        //TODO: Implement control logic for port check
        return setup4;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(SetupViewModel.class);
    }
}
