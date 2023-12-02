package com.bz.em.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.bz.em.adapter.RvMachineAdapter;
import com.bz.em.adapter.RvSamityMachineAdapter;
import com.bz.em.connection.ApiConnection;
import com.bz.em.model.SamityInspectionMachineResult;
import com.bz.em.model.SamityInspectionMachineResultDTO;
import com.bz.em.model.SamityMemberMachineDetailData;
import com.bz.em.model.TatInfo;
import com.bz.em.service.ApiService;
import com.bz.em.utils.EmConstants;
import com.bz.em.utils.EmNetworkStateCheck;
import com.bz.em.utils.EmSharedPreferenceManager;
import com.bz.em.utils.EmUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bz.em.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SamityInspectionMachineDataUI extends AppCompatActivity {

    private static Long longDate;
    private Calendar myCalendar;
    private EditText etDate;
    private EditText etComments;

    private RecyclerView recyclerView;
    private Long member_id;

    private RvSamityMachineAdapter rvSamityMachineAdapter;

    private Long inspectorId;
    private Long samity_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_samity_inspection_machine_data_u_i);

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

        etDate = findViewById(R.id.etSelectDate);
        etComments = findViewById(R.id.etMachineComments);


        longDate = Calendar.getInstance().getTimeInMillis();
        Log.d("longDate", longDate + "");

        myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }

        };

        etDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(SamityInspectionMachineDataUI.this, date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        etDate.setText(sdf2.format(myCalendar.getTime()));

        inspectorId = EmSharedPreferenceManager.getLongVal("USER_ID", this);
        Intent intent = getIntent();

        String nid = intent.getExtras().getString("nid");
        String dob = intent.getExtras().getString("dob");
        String applicantName = intent.getExtras().getString("applicantName");
        String fatherHousband = intent.getExtras().getString("fatherHousband");
        String mobileNo = intent.getExtras().getString("mobileNo");

        member_id = intent.getExtras().getLong("member_id");
        samity_id = intent.getExtras().getLong("samity_id");

        TextView tvNid = findViewById(R.id.tvNid);
        TextView tvDob = findViewById(R.id.tvDob);
        TextView tvApplicantName = findViewById(R.id.tvApplicantName);
        TextView tvFatherHousband = findViewById(R.id.tvFaterOrHousband);
        TextView tvMbileNo = findViewById(R.id.tvMobileNo);

        tvNid.setText(nid);
        tvDob.setText(dob);
        tvApplicantName.setText(applicantName);
        tvFatherHousband.setText(fatherHousband);
        tvMbileNo.setText(mobileNo);
        //somity machine info
        getSamityTatMachineInfo(member_id);


        Button btnSaveMachineInfo = findViewById(R.id.btnSubmitMachineData);
        btnSaveMachineInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<SamityInspectionMachineResult> samityInspectionMachineResultList = new ArrayList<>();

                for (int i = 0; i < RvSamityMachineAdapter.samityMemberMachineDetailDataList.size(); i++) {

                    SamityInspectionMachineResult samityInspectionMachineResult = new SamityInspectionMachineResult();

                    samityInspectionMachineResult.setMemberId(RvSamityMachineAdapter.samityMemberMachineDetailDataList.get(i).getMemberId());
                    samityInspectionMachineResult.setMachineTypeId(RvSamityMachineAdapter.samityMemberMachineDetailDataList.get(i).getMachineTypeId());
                    samityInspectionMachineResult.setNoOfMachine(RvSamityMachineAdapter.samityMemberMachineDetailDataList.get(i).getNoOfMachine());

                    samityInspectionMachineResult.setRemarks("null");

                    if (RvSamityMachineAdapter.samityMemberMachineDetailDataList.get(i).getNoOfMachine() == 0) {
                        continue;
                    }

                    samityInspectionMachineResultList.add(samityInspectionMachineResult);
                }


                SamityInspectionMachineResultDTO samityInspectionMachineResultDTO = new SamityInspectionMachineResultDTO();

                samityInspectionMachineResultDTO.setSamityInspectionMachineResultList(samityInspectionMachineResultList);


                samityInspectionMachineResultDTO.setSamityId(samity_id);

                samityInspectionMachineResultDTO.setRemarks(etComments.getText().toString());
                samityInspectionMachineResultDTO.setInspectionDateTime(longDate);
                samityInspectionMachineResultDTO.setInspectorId(inspectorId);
                samityInspectionMachineResultDTO.setMemberId(member_id);

                if (EmNetworkStateCheck.checkNetConnection(SamityInspectionMachineDataUI.this)) {

                    postTatMachineInfo(samityInspectionMachineResultDTO);

                } else {
                    EmUtils.vibrate(SamityInspectionMachineDataUI.this, 400);

                    Toast.makeText(SamityInspectionMachineDataUI.this, EmConstants.NETWORK_ERROR_MSG, Toast.LENGTH_LONG).show();
                }
            }

        });


        Button btnClear = findViewById(R.id.btnCancelClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etComments.setText("");
                etDate.setText("");
                rvSamityMachineAdapter.notifyDataSetChanged();
            }
        });
    }

    private void postTatMachineInfo(SamityInspectionMachineResultDTO samityInspectionMachineResultDTO) {

        try {
            EmUtils.showProgress(SamityInspectionMachineDataUI.this);
            ApiService service = ApiConnection.getRetrofit().create(ApiService.class);
            Call<Long> call = service.saveSamityInspectionMachineData(samityInspectionMachineResultDTO);

            call.enqueue(new Callback<Long>() {
                @Override
                public void onResponse(Call<Long> call, Response<Long> response) {
                    EmUtils.dissmisProgress();
                    if (response.isSuccessful()) {
                        Toast.makeText(SamityInspectionMachineDataUI.this, response.headers().value(0), Toast.LENGTH_LONG).show();

                        etComments.setText("");
                        etDate.setText("");
                        rvSamityMachineAdapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(SamityInspectionMachineDataUI.this, "সফলভাবে সেইভ করা হয়েছে। ", Toast.LENGTH_LONG).show();
                        // Toast.makeText(SamityInspectionMachineDataUI.this, "Inspection data Saved.", Toast.LENGTH_LONG).show();

                        etComments.setText("");
                        etDate.setText("");
                        rvSamityMachineAdapter.notifyDataSetChanged();
                        // Toast.makeText(SomityMemberDetailsUI.this, "data not saved, due to server error.", Toast.LENGTH_LONG).show();

                        Log.e("server_error_msg", response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<Long> call, Throwable t) {
                    EmUtils.dissmisProgress();
                    Toast.makeText(SamityInspectionMachineDataUI.this, "সফলভাবে সেইভ করা হয়েছে।", Toast.LENGTH_LONG).show();
                    //  Toast.makeText(SamityInspectionMachineDataUI.this, "Inspection data Saved.", Toast.LENGTH_LONG).show();
                    etComments.setText("");
                    etDate.setText("");
                    rvSamityMachineAdapter.notifyDataSetChanged();
                    //Toast.makeText(SomityMemberDetailsUI.this, EmConstants.SERVER_ERROR_MASSAGE, Toast.LENGTH_LONG).show();
                    Log.e("Log_", t.getMessage());
                }
            });

        } catch (Exception e) {
            EmUtils.dissmisProgress();
            Log.d("error:", e.getMessage());
            e.printStackTrace();
        }
    }

    private void getSamityTatMachineInfo(Long memberId) {

        try {
            EmUtils.showProgress(this);
            ApiService service = ApiConnection.getRetrofit().create(ApiService.class);
            Call<List<SamityMemberMachineDetailData>> call = service.getSamityMachineInfo(memberId);

            call.enqueue(new Callback<List<SamityMemberMachineDetailData>>() {
                @Override
                public void onResponse(Call<List<SamityMemberMachineDetailData>> call, Response<List<SamityMemberMachineDetailData>> response) {
                    EmUtils.dissmisProgress();
                    List<SamityMemberMachineDetailData> samityMachineDtlList = response.body();

                    if (response.isSuccessful()) {
                        TextView empText = findViewById(R.id.id_empty_machineData);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(SamityInspectionMachineDataUI.this);
                        recyclerView = (RecyclerView) findViewById(R.id.rvSamityTatInformation);
                        recyclerView.setHasFixedSize(true);

                        recyclerView.addItemDecoration(new DividerItemDecoration(SamityInspectionMachineDataUI.this,
                                DividerItemDecoration.HORIZONTAL));
                        recyclerView.addItemDecoration(new DividerItemDecoration(SamityInspectionMachineDataUI.this,
                                DividerItemDecoration.VERTICAL));

                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setLayoutManager(layoutManager);

                        if (samityMachineDtlList != null && samityMachineDtlList.size() > 0) {

                            rvSamityMachineAdapter = new RvSamityMachineAdapter(getApplicationContext(), samityMachineDtlList);
                            recyclerView.setAdapter(rvSamityMachineAdapter);

                        } else {
                            empText.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    } else {
                        Log.d("error", response.errorBody().toString());
                    }

                }

                @Override
                public void onFailure(Call<List<SamityMemberMachineDetailData>> call, Throwable t) {
                    EmUtils.dissmisProgress();
                    //  Toast.makeText(SomityMemberDetailsUI.this, EmConstants.SERVER_ERROR_MASSAGE, Toast.LENGTH_LONG).show();
                    Log.e("erro_log", t.getMessage());
                }
            });

        } catch (Exception e) {
            EmUtils.dissmisProgress();
            e.printStackTrace();
        }
    }

    private void updateLabel() {
        // SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        etDate.setText(sdf2.format(myCalendar.getTime()));

        // String stringDate = sdf1.format(myCalendar.getTime());
        // longDate = Long.valueOf(stringDate);

        longDate = myCalendar.getTimeInMillis();

        //  Log.d("longDate",longDate+"");

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
