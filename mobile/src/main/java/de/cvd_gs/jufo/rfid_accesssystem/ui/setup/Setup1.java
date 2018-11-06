package de.cvd_gs.jufo.rfid_accesssystem.ui.setup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import de.cvd_gs.jufo.rfid_accesssystem.R;

public class Setup1 extends Fragment {
    private ManualSetupViewModel mViewModel;

    public static Setup1 newInstance() {
        return new Setup1();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
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
                    }
                    else {
                        mViewModel.setServer_address(editURL.getText().toString());
                    }
                }
            }
        });
        return setup1;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(ManualSetupViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
