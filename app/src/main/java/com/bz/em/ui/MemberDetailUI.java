package com.bz.em.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bz.em.R;

public class MemberDetailUI extends AppCompatActivity {

    private TextView tvName, tvFatherName, tvAddress, tvMobileNo, tvInspectionDate, tvNid;
    private Button btnVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail_ui);
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

        tvName = findViewById(R.id.txtView_mName);
        tvFatherName = findViewById(R.id.txtView_mFatherName);
        tvAddress = findViewById(R.id.txtView_mAddress);
        tvMobileNo = findViewById(R.id.txtView_mMobileNo);
        tvInspectionDate = findViewById(R.id.txtView_mInspectionDate);
        tvNid = findViewById(R.id.txtView_mNid);

        String name = null;
        String fatherName = null;
        String nid = null;
        String address = null;
        String mobileNo = null;
        String inspectionDate = null;

        Intent intent = getIntent();

        if (intent != null) {
            name = intent.getExtras().getString("NAME");
            fatherName = intent.getExtras().getString("FATHER_NAME");
            nid = intent.getExtras().getString("NID");
            address = intent.getExtras().getString("ADDRESS");
            mobileNo = intent.getExtras().getString("MOBILE_NO");
            inspectionDate = intent.getExtras().getString("INSPECTION_DATE");
        }

        tvName.setText(name);
        tvFatherName.setText(fatherName);
        tvAddress.setText(address);
        tvMobileNo.setText(mobileNo);
        tvInspectionDate.setText(inspectionDate);
        tvNid.setText(nid);


        btnVerify = findViewById(R.id.btnVerify);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemberDetailUI.this, FieldVisitInputUI.class);
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
