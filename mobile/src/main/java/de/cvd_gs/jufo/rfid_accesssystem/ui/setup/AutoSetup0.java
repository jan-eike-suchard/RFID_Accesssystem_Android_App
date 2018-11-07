package de.cvd_gs.jufo.rfid_accesssystem.ui.setup;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import de.cvd_gs.jufo.rfid_accesssystem.R;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class AutoSetup0 extends Fragment implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private SetupViewModel mViewModel;
    private boolean camera_activated;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mScannerView = new ZXingScannerView(getActivity());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[] {Manifest.permission.CAMERA}, 1);
        }
        if (camera_activated)
        {
            return mScannerView;
        }
        else
        {
            Snackbar.make(getActivity().findViewById(R.id.setupLayout), R.string.cameraPermission, Snackbar.LENGTH_LONG).show();
            getFragmentManager().beginTransaction().replace(R.id.container, new Setup1()).commit();
            mViewModel.setAutoconfig(false);
            mViewModel.setCurrentStep(1);
        }
        return mScannerView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length >  0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            camera_activated = true;
        }
        else
        {
            camera_activated = false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
        mScannerView.setAutoFocus(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(SetupViewModel.class);
    }

    @Override
    public void handleResult(Result rawResult) {
        if (rawResult.getBarcodeFormat() == BarcodeFormat.QR_CODE)
        {
            Log.d("QR CODE RAW", rawResult.toString());

            if ((URLUtil.isHttpUrl(rawResult.getText()) && URLUtil.isValidUrl(rawResult.getText())) || (URLUtil.isHttpsUrl(rawResult.getText()) && URLUtil.isValidUrl(rawResult.getText())))
            {
                mViewModel.setAutoconfig_url(rawResult.getText());
                Button buttonForward = getActivity().findViewById(R.id.buttonForward);
                buttonForward.performClick();

            }
            else
            {
                Toast.makeText(AutoSetup0.this.getContext(), getText(R.string.urlInvalid), Toast.LENGTH_LONG).show();
                mScannerView.resumeCameraPreview(AutoSetup0.this);
            }
        }
        else
        {
            Toast.makeText(AutoSetup0.this.getContext(), getText(R.string.wrongCode), Toast.LENGTH_LONG).show();
            mScannerView.resumeCameraPreview(AutoSetup0.this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void onActivityCreated (@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

    }

}