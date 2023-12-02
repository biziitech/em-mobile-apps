package com.bz.em.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.bz.em.R;
import com.bz.em.utils.EmSharedPreferenceManager;

public class LauncherUI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher_ui);

        int loginflag = EmSharedPreferenceManager.getIntVal("LOGIN_FLAG", this);

        if (loginflag == 1) {
            Intent intent = new Intent(this, DashboardUI.class);
            this.finish();
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, LoginUI.class);
            this.finish();
            startActivity(intent);
        }
    }
}
