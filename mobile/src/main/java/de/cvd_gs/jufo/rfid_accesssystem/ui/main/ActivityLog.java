package de.cvd_gs.jufo.rfid_accesssystem.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.cvd_gs.jufo.rfid_accesssystem.R;

public class ActivityLog extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View activityLog = inflater.inflate(R.layout.main_activity_log, container);
        SwipeRefreshLayout layout = activityLog.findViewById(R.id.activityLogLayout);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //TODO: Download activity list
            }
        });
        return activityLog;
    }
}
