package de.cvd_gs.jufo.rfid_accesssystem.ui.setup;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.nfc.NfcAdapter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import de.cvd_gs.jufo.rfid_accesssystem.R;

public class AutoSetup1 extends Fragment {
    public String scanResult;
    private SetupViewModel mViewModel;
    TextView serverAddress;
    TextView serverPort;
    TextView connectionType;
    TextView username;
    TextView nfcLogin;
    TextView nfcDoorOpener;
    public Handler mHandler;

    public static AutoSetup1 newInstance() {
        return new AutoSetup1();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(SetupViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View autosetup1 = inflater.inflate(R.layout.autosetup_fragment1, container, false);
        serverAddress = autosetup1.findViewById(R.id.textViewServerAddress);
        serverPort = autosetup1.findViewById(R.id.textViewServerPort);
        connectionType = autosetup1.findViewById(R.id.textViewConnectionType);
        username = autosetup1.findViewById(R.id.textViewUsername);
        nfcLogin = autosetup1.findViewById(R.id.textViewNFCLogin);
        nfcDoorOpener = autosetup1.findViewById(R.id.textViewNFCOpen);
        Button buttonContinue = getActivity().findViewById(R.id.buttonForward);
        buttonContinue.setText(getText(R.string.finishSetup));
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = mViewModel.getAutoconfig_url();
        Log.d("RECIVED URL", url);
        final StringRequest authenticate = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("RESPONSE", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String server_address = jsonObject.getString("address");
                    String server_port = jsonObject.getString("port");
                    String connection_type = jsonObject.getString("connection");
                    String user_name = jsonObject.getString("username");
                    Boolean nfc_Login = jsonObject.getBoolean("nfc_login");
                    Boolean nfcOpen = jsonObject.getBoolean("nfc_open");
                    NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(getContext());
                    Boolean hceSupport = getActivity().getPackageManager().hasSystemFeature("android.hardware.nfc.hce");
                    serverAddress.setText(server_address);
                    serverPort.setText(server_port);
                    if (connection_type.equals("secure"))
                    {
                        connectionType.setText(getString(R.string.connectionSecure));
                        connectionType.setTextColor(Color.GREEN);
                        connectionType.setTypeface(connectionType.getTypeface(), Typeface.BOLD);
                    }
                    else
                    {
                        connectionType.setText(getString(R.string.connectionInsecure));
                        connectionType.setTextColor(Color.RED);
                        connectionType.setTypeface(connectionType.getTypeface(), Typeface.BOLD);
                    }
                    username.setText(user_name);
                    if (nfc_Login &&  nfcAdapter != null)
                    {
                        nfcLogin.setText(getString(R.string.nfcLogin_enabled));
                        nfcLogin.setTextColor(Color.GREEN);
                        nfcLogin.setTypeface(nfcLogin.getTypeface(), Typeface.BOLD);
                    }
                    else
                    {
                        nfcLogin.setText(getString(R.string.notSupported));
                        nfcLogin.setTextColor(Color.RED);
                        nfcLogin.setTypeface(nfcLogin.getTypeface(), Typeface.BOLD_ITALIC);
                    }
                    if (!nfc_Login)
                    {
                        nfcLogin.setText(getString(R.string.nfcLogin_disabled));
                        nfcLogin.setTextColor(Color.RED);
                        nfcLogin.setTypeface(nfcLogin.getTypeface(), Typeface.BOLD);
                    }
                    if(nfcOpen && hceSupport && nfcAdapter != null)
                    {
                        nfcDoorOpener.setText(getString(R.string.nfcOpen_enabled));
                        nfcDoorOpener.setTextColor(Color.GREEN);
                        nfcDoorOpener.setTypeface(nfcDoorOpener.getTypeface(), Typeface.BOLD);
                    }
                    else
                    {
                        nfcLogin.setText(getString(R.string.notSupported));
                        nfcLogin.setTextColor(Color.RED);
                        nfcLogin.setTypeface(nfcLogin.getTypeface(), Typeface.BOLD_ITALIC);
                    }
                    if (!nfcOpen)
                    {
                        nfcDoorOpener.setText(getString(R.string.nfcOpen_disabled));
                        nfcDoorOpener.setTextColor(Color.RED);
                        nfcDoorOpener.setTypeface(nfcDoorOpener.getTypeface(), Typeface.BOLD);
                    }
                    ProgressBar progressFetchData = autosetup1.findViewById(R.id.progressBar2);
                    ImageView setupCompleted = autosetup1.findViewById(R.id.imageView3);
                    progressFetchData.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.fade_out));
                    setupCompleted.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
                } catch (JSONException e) {
                    e.printStackTrace();
                    ProgressBar progressFetchData = autosetup1.findViewById(R.id.progressBar2);
                    ImageView setupCompleted = autosetup1.findViewById(R.id.imageView3);
                    progressFetchData.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.fade_out));
                    setupCompleted.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
                    Drawable errorImage = autosetup1.getResources().getDrawable(R.drawable.ic_clear_black_24dp);
                    setupCompleted.setImageDrawable(errorImage);
                    TextView qr_read = autosetup1.findViewById(R.id.textView3);
                    qr_read.setText(e.toString());

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", error.toString());
                ProgressBar progressFetchData = autosetup1.findViewById(R.id.progressBar2);
                ImageView setupCompleted = autosetup1.findViewById(R.id.imageView3);
                progressFetchData.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.fade_out));
                setupCompleted.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
                Drawable errorImage = autosetup1.getResources().getDrawable(R.drawable.ic_clear_black_24dp);
                setupCompleted.setImageDrawable(errorImage);
                TextView qr_read = autosetup1.findViewById(R.id.textView3);
                qr_read.setText(error.toString());
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };
        queue.add(authenticate);

        return autosetup1;
    }

    @Override
    public void onActivityCreated (@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }
}

