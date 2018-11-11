package de.cvd_gs.jufo.rfid_accesssystem.ui.setup;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import de.cvd_gs.jufo.rfid_accesssystem.R;

public class Setup2 extends Fragment {
    private SetupViewModel mViewModel;
    public static Setup2 newInstance() {
        return new Setup2();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View setup2 = inflater.inflate(R.layout.setup_fragment2, container, false);
        EditText editPort = setup2.findViewById(R.id.editTextPort);
        editPort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String port = s.toString();
                mViewModel.setPort(port);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return setup2;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(SetupViewModel.class);
    }
}
