package com.bz.em.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bz.em.R;

public class RepayDashBoardUI extends AppCompatActivity {

    private String applicantName;
    private String somityName;
    private long somityId;

    private long basicCenterId;
    private long somityMemberId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repay_dash_board_ui);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_common);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorNew));
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        Intent intent = getIntent();

        applicantName = intent.getExtras().getString("MEMBER_NAME");
        somityName = intent.getExtras().getString("SOMITY_NAME");
        somityId = intent.getExtras().getLong("SOMITY_ID");

        basicCenterId = intent.getExtras().getLong("BASIC_CENTER_ID");
        somityMemberId = intent.getExtras().getLong("SOMITY_MEMBER_ID");

        CardView cvLoanCollections = findViewById(R.id.cvLoanCollections);
        cvLoanCollections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RepayDashBoardUI.this, RepayLoanCollectionUI.class);
                intent.putExtra("MEMBER_NAME", applicantName);
                intent.putExtra("SOMITY_NAME", somityName);
                intent.putExtra("SOMITY_ID", somityId);

                intent.putExtra("BASIC_CENTER_ID", basicCenterId);
                intent.putExtra("SOMITY_MEMBER_ID", somityMemberId);

                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
