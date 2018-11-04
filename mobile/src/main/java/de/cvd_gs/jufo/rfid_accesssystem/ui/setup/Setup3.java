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

public class Setup3 extends Fragment {
    private SetupViewModel mViewModel;
    public static Setup3 newInstance() {
        return new Setup3();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View setup3 = inflater.inflate(R.layout.setup_fragment3, container, false);
        //TODO: Implement control logic for port check
        return setup3;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(SetupViewModel.class);
    }
}
