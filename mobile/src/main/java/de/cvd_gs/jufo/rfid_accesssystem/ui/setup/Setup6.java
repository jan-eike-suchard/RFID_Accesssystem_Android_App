package de.cvd_gs.jufo.rfid_accesssystem.ui.setup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import de.cvd_gs.jufo.rfid_accesssystem.R;

public class Setup6 extends Fragment {
    private SetupViewModel mViewModel;
    public static Setup6 newInstance() {
        return new Setup6();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View setup6 = inflater.inflate(R.layout.setup_fragment6, container, false);
        //TODO: Implement control logic for port check
        return setup6;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(SetupViewModel.class);
    }
}
