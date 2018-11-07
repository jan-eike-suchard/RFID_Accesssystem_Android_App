package de.cvd_gs.jufo.rfid_accesssystem.ui.setup;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

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
        editURL.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean validURL = Patterns.WEB_URL.matcher(s).matches();
                if (validURL)
                {
                    mViewModel.setServer_address(s.toString());
                    TextView textStatusURL = setup1.findViewById(R.id.textView5);
                    textStatusURL.setText(R.string.urlValid);
                    textStatusURL.setTextColor(Color.GREEN);
                    editURL.setTextColor(Color.GREEN);
                }
                else
                {
                    TextView textStatusURL = setup1.findViewById(R.id.textView5);
                    textStatusURL.setText(R.string.urlInvalid);
                    textStatusURL.setTextColor(Color.RED);
                    editURL.setTextColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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
