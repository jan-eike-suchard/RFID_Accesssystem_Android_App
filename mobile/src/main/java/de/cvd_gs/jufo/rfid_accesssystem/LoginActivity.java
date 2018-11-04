package de.cvd_gs.jufo.rfid_accesssystem;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /*
    Benötigte Variablen
     */
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter[] intentFilters;
    private String[][] techLists;
    private IntentFilter[] intentFiltersArray;
    private boolean cardRead = false;
    private String firstName;
    private String lastName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_login);
        ImageView nfcStatus = findViewById(R.id.imageView);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        //nfcAdapter.enableReaderMode(this, null,NfcAdapter.FLAG_READER_NO_PLATFORM_SOUNDS, null);
        if (nfcAdapter == null)
        {
            //TODO: Disable NFC Symbol
            nfcStatus.setVisibility(View.GONE);

        }
        if (!nfcAdapter.isEnabled())
        {
            //TODO: Set NFC Symbol yellow
            nfcStatus.setImageResource(R.drawable.ic_nfc_disabled);
        }
        else
        {
            //TODO: Set NFC Symbol green
            nfcStatus.setImageResource(R.drawable.ic_nfc_waiting);
            Intent nfcIntent = new Intent(this, getClass());
            pendingIntent = PendingIntent.getActivity(this, 0, nfcIntent,0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        enableNfcForegroundDispatch();

    }

    @Override
    protected void onPause() {
        super.onPause();
        disableNfcForegrundDispatch();
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        Log.wtf("INTENT RECIVED", intent.toString());
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        processNfcTag(tag);
    }

    private void processNfcTag(Tag tag) {
        ImageView nfcStatus = findViewById(R.id.imageView);
        nfcStatus.setImageResource(R.drawable.ic_nfc_enabled);
        TextView textNFC = findViewById(R.id.textViewUseNFC);
        textNFC.setText(R.string.nfc_read);
        if (tag!=null)
        {
            cardRead = true;
            Log.wtf("TAG REVICED", tag.toString());
            String uid;
            uid = bytesToHexString(tag.getId());
            uid = uid.toUpperCase();
            Log.wtf("NFC ID", uid);
            //nfcLogin(uid);
        }
        else
        {
            Log.wtf("TAG REVICED", "NOPE");
        }
    }

    private void nfcLogin(final String uid) {
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        String server_address = preferences.getString("server", null);
        final String api_key = preferences.getString("api_key", "none");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://" + server_address + "/api/authenticate.php";
        StringRequest authenticate = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("RESPONSE", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean authenticated = jsonObject.getBoolean("authenticated");
                    if (authenticated)
                    {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", error.toString());
            }

        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mode", "nfc");
                params.put("uid", uid);
                params.put("api", api_key);
                return params;
            }
        };
        queue.add(authenticate);
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0)
        {
            return null;
        }
        char[] buffer = new char[2];
        for (int i = 0; i<src.length;i++)
        {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            System.out.println(buffer);
            stringBuilder.append(buffer);
        }
        return stringBuilder.toString();
    }

    private void enableNfcForegroundDispatch()
    {
        if (nfcAdapter != null && nfcAdapter.isEnabled() && !cardRead)
        {
            ImageView nfcStatus = findViewById(R.id.imageView);
            nfcStatus.setImageResource(R.drawable.ic_nfc_waiting);
            TextView textNFC = findViewById(R.id.textViewUseNFC);
            textNFC.setText(R.string.login_text_useNFC);
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
            Log.wtf("NFC STATUS", "DISPATCH ENABLED");
        }
        else if (cardRead)
        {
            ImageView nfcStatus = findViewById(R.id.imageView);
            nfcStatus.setImageResource(R.drawable.ic_nfc_enabled);
            TextView textNFC = findViewById(R.id.textViewUseNFC);
            textNFC.setText(R.string.nfc_read);
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        }
        else
        {
            disableNfcForegrundDispatch();
            ImageView nfcStatus = findViewById(R.id.imageView);
            nfcStatus.setImageResource(R.drawable.ic_nfc_disabled);
            TextView textNFC = findViewById(R.id.textViewUseNFC);
            textNFC.setText(R.string.nfc_unavailable);
        }
    }

    private void disableNfcForegrundDispatch() {
        nfcAdapter.disableForegroundDispatch(this);
        Log.wtf("NFC STATUS", "DISPATCH DISABLED");
    }

    public String getFirstName()
    {
     return firstName;
    }

    public String getLastName()
    {
     return lastName;
    }
}

