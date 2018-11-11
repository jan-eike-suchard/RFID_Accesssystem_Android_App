package de.cvd_gs.jufo.rfid_accesssystem;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends Activity {

    /*
    Benötigte Variablen
     */
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private boolean cardRead = false;
    private String lastName;
    private String firstName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        Boolean firstrun = preferences.getBoolean("firstrun", true);
        if (firstrun)
        {
            Intent intent = new Intent(this, SetupActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_login);
        ImageView nfcStatus = findViewById(R.id.imageView);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
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
            if (uid != null) {
                uid = uid.toUpperCase();
            }
            Log.wtf("NFC ID", uid);
            nfcLogin(uid);
        }
        else
        {
            Log.wtf("TAG REVICED", "NOPE");
        }
    }

    private void nfcLogin(final String uid) {
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        String server_address = preferences.getString("server", null);
        final String api_key = preferences.getString("api_key", null);
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
                        Intent nextActivity = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(nextActivity);
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
                Map<String, String> params = new HashMap<>();
                params.put("mode", "nfc");
                params.put("uid", uid);
                if (api_key != null) {
                    params.put("api", api_key);
                }
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
        for (byte aSrc : src) {
            buffer[0] = Character.forDigit((aSrc >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(aSrc & 0x0F, 16);
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

