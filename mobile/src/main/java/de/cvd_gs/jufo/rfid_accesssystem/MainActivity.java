package de.cvd_gs.jufo.rfid_accesssystem;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import de.cvd_gs.jufo.rfid_accesssystem.ui.main.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private int fragmentContainer = R.id.mainContainer;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_home:
                    fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).replace(fragmentContainer, new HomeFragment()).commit();
                    return true;
                case R.id.menu_account:
                    //
                    return true;
                case R.id.menu_activityLog:
                    //
                    return true;
                case R.id.menu_nfcOpen:
                    //
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fragmentManager = getSupportFragmentManager();
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        String firstName = preferences.getString("firstName", "-");
        String lastName = preferences.getString("lastName", "-");
        String email = preferences.getString("userMail", "mock@mockserver.mock");
    }

}
