package de.cvd_gs.jufo.rfid_accesssystem.ui.setup;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.EditText;

import de.cvd_gs.jufo.rfid_accesssystem.R;

public class Setup1 extends Fragment {
    private SetupViewModel mViewModel;

    public static Setup1 newInstance() {
        return new Setup1();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View setup1 = inflater.inflate(R.layout.setup_fragment1, container, false);
        final EditText editURL = setup1.findViewById(R.id.editTextServerURL);
        editURL.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (setup1.hasFocus())
                {
                    if (!URLUtil.isValidUrl(editURL.getText().toString()))
                    {
                        editURL.setError(getString(R.string.urlInvalid));
                        mViewModel.setForward(false);
                    }
                    else {
                        mViewModel.setForward(true);
                    }
                }
            }
        });
        return setup1;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(SetupViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
