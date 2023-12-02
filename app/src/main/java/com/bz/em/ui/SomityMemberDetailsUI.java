package com.bz.em.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.bz.em.adapter.RvMachineAdapter;
import com.bz.em.connection.ApiConnection;
import com.bz.em.model.InspectionMachineResult;
import com.bz.em.model.InspectionMachineResultDTO;
import com.bz.em.model.TatInfo;
import com.bz.em.service.ApiService;
import com.bz.em.utils.EmConstants;
import com.bz.em.utils.EmNetworkStateCheck;
import com.bz.em.utils.EmSharedPreferenceManager;
import com.bz.em.utils.EmUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bz.em.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SomityMemberDetailsUI extends AppCompatActivity {

    private Long loanId = null;
    private Long machineTypeId = null;
    private EditText etMComments;
    private static Long longDate;
    private EditText etDate;
    private Calendar myCalendar;
    private long inspectorId;
    private String navType;

    private RvMachineAdapter rvMachineAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_somity_member_details_ui);
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

        etDate = (EditText) findViewById(R.id.etSelectDate);
        longDate = Calendar.getInstance().getTimeInMillis();

        etMComments = findViewById(R.id.etMachineComments);

        inspectorId = EmSharedPreferenceManager.getLongVal("USER_ID", this);
        myCalendar = Calendar.getInstance();

        etDate = (EditText) findViewById(R.id.etSelectDate);
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
                new DatePickerDialog(SomityMemberDetailsUI.this, date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        etDate.setText(sdf2.format(myCalendar.getTime()));


        TextView tvNid = findViewById(R.id.tvNid);
        TextView tvDob = findViewById(R.id.tvDob);
        TextView tvApplicantName = findViewById(R.id.tvApplicantName);
        TextView tvApplicantNameEn = findViewById(R.id.tvApplicantNameEn);
        TextView tvFatherHousband = findViewById(R.id.tvFaterOrHousband);
        TextView tvPermanentAddr = findViewById(R.id.tvPermanantAddr);
        TextView tvPresentAddr = findViewById(R.id.tvPresentAddr);
        TextView tvMbileNo = findViewById(R.id.tvMobileNo);
        TextView tvAge = findViewById(R.id.tvAge);
        TextView tvVillage = findViewById(R.id.tvVillage);
        TextView tvPostOffice = findViewById(R.id.tvPostOffice);
        TextView tvDistrict = findViewById(R.id.tvDistrict);
        TextView tvUpzillaName = findViewById(R.id.tvUpozilla);
        TextView tvBasicCenterName = findViewById(R.id.tvBasicCenterName);

        TextView tvTatiSomityName = findViewById(R.id.tvTatiSomityName);
        TextView tvSomityRegNo = findViewById(R.id.tvSomityRegNo);


        Intent intent = getIntent();
        if (intent != null) {
            navType = intent.getExtras().getString("NAV_TYPE");

            //  Log.d("navType",navType);
            String nid = intent.getExtras().getString("nid");
            loanId = intent.getExtras().getLong("loanId");
            String dob = intent.getExtras().getString("dob");
            String applicantName = intent.getExtras().getString("applicantName");
            String applicantNameEn = intent.getExtras().getString("applicantNameEn");
            String fatherHousband = intent.getExtras().getString("fatherHousband");
            String permanentAddr = intent.getExtras().getString("permanentAddr");
            String presentAddr = intent.getExtras().getString("presentAddr");
            String mobileNo = intent.getExtras().getString("mobileNo");
            String age = intent.getExtras().getString("age");
            String village = intent.getExtras().getString("village");
            String postOffice = intent.getExtras().getString("postOffice");
            String district = intent.getExtras().getString("district");
            String upzillaName = intent.getExtras().getString("upzillaName");
            String basicCenterName = intent.getExtras().getString("basicCenterName");

            String somityName = intent.getExtras().getString("somityName");
            Long somityId = intent.getExtras().getLong("somityId");

            tvNid.setText(nid);
            tvDob.setText(dob);
            tvApplicantName.setText(applicantName);
            tvApplicantNameEn.setText(applicantNameEn);
            tvFatherHousband.setText(fatherHousband);
            tvPermanentAddr.setText(permanentAddr);
            tvPresentAddr.setText(presentAddr);
            tvMbileNo.setText(mobileNo);
            tvAge.setText(age);
            tvVillage.setText(village);
            tvPostOffice.setText(postOffice);
            tvDistrict.setText(district);
            tvUpzillaName.setText(upzillaName);
            tvBasicCenterName.setText(basicCenterName);
            tvTatiSomityName.setText(somityName);
            tvSomityRegNo.setText(somityId.toString());
        }

        getTatMachineInfo(loanId);

        Button btnSaveMachineData = findViewById(R.id.btnSubmitProfile);

        Button btnClear = findViewById(R.id.btnCancelProfile);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // etMComments.setText("");
               // etDate.setText("");
               // rvMachineAdapter.notifyDataSetChanged();

                onBackPressed();
            }
        });


        btnSaveMachineData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<InspectionMachineResult> inspectionMachineResultList = new ArrayList<>();

                for (int i = 0; i < RvMachineAdapter.tatInfoArayList.size(); i++) {

                    InspectionMachineResult inspectionMachineResult = new InspectionMachineResult();

                    inspectionMachineResult.setLoanApplicationId(RvMachineAdapter.tatInfoArayList.get(i).getLoanAccountId());
                    inspectionMachineResult.setMachineTypeId(RvMachineAdapter.tatInfoArayList.get(i).getWeaverMachineTypeId());
                    inspectionMachineResult.setNoOfMachine(RvMachineAdapter.tatInfoArayList.get(i).getNoOfMachine());

                    inspectionMachineResult.setRemarks("null");

                    if (RvMachineAdapter.tatInfoArayList.get(i).getNoOfMachine() == 0) {
                        continue;
                    }

                    inspectionMachineResultList.add(inspectionMachineResult);
                }
                InspectionMachineResultDTO inspectionMachineResultDTO = new InspectionMachineResultDTO();

                inspectionMachineResultDTO.setInspectionMachineResultList(inspectionMachineResultList);

                inspectionMachineResultDTO.setRemarks(etMComments.getText().toString());

                inspectionMachineResultDTO.setInspectorId(inspectorId);

                inspectionMachineResultDTO.setLoanApplicationId(loanId);

                inspectionMachineResultDTO.setInspectionDateTime(longDate);


                if (EmNetworkStateCheck.checkNetConnection(SomityMemberDetailsUI.this)) {

                    postTatMachineInfo(inspectionMachineResultDTO);

                } else {
                    EmUtils.vibrate(SomityMemberDetailsUI.this, 400);

                    Toast.makeText(SomityMemberDetailsUI.this, EmConstants.NETWORK_ERROR_MSG, Toast.LENGTH_LONG).show();
                }
            }

        });

    }


    private void postTatMachineInfo(InspectionMachineResultDTO inspectionMachineResultDTO) {

        // Long userId = H2SharedPreferenceManager.getLongVal("USER_ID", this);


        try {
            EmUtils.showProgress(SomityMemberDetailsUI.this);
            ApiService service = ApiConnection.getRetrofit().create(ApiService.class);
            Call<List<Long>> call = service.saveMachineInspectionResult(inspectionMachineResultDTO);

            call.enqueue(new Callback<List<Long>>() {
                @Override
                public void onResponse(Call<List<Long>> call, Response<List<Long>> response) {

                    if (response.isSuccessful()) {
                        EmUtils.dissmisProgress();
                        Toast.makeText(SomityMemberDetailsUI.this, response.headers().value(0), Toast.LENGTH_LONG).show();

                        etMComments.setText("");
                        etDate.setText("");
                        rvMachineAdapter.notifyDataSetChanged();
                    } else {
                        EmUtils.dissmisProgress();
                        Toast.makeText(SomityMemberDetailsUI.this, "সফলভাবে সেইভ করা হয়েছে। ", Toast.LENGTH_LONG).show();
                        etMComments.setText("");
                        etDate.setText("");
                        rvMachineAdapter.notifyDataSetChanged();
                        // Toast.makeText(SomityMemberDetailsUI.this, "data not saved, due to server error.", Toast.LENGTH_LONG).show();

                        Log.e("server_error_msg", response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<List<Long>> call, Throwable t) {
                    EmUtils.dissmisProgress();
                    Toast.makeText(SomityMemberDetailsUI.this, "সফলভাবে সেইভ করা হয়েছে। ", Toast.LENGTH_LONG).show();
                    //  Toast.makeText(SomityMemberDetailsUI.this, "Inspection data Saved.", Toast.LENGTH_LONG).show();
                    etMComments.setText("");
                    etDate.setText("");
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

    private ListView listView;

    private RecyclerView recyclerView;

    private void getTatMachineInfo(Long loanApplicationId) {

        try {
            EmUtils.showProgress(this);
            ApiService service = ApiConnection.getRetrofit().create(ApiService.class);
            Call<List<TatInfo>> call = service.getTatMachineInfo(loanApplicationId);

            call.enqueue(new Callback<List<TatInfo>>() {
                @Override
                public void onResponse(Call<List<TatInfo>> call, Response<List<TatInfo>> response) {
                    EmUtils.dissmisProgress();
                    List<TatInfo> tatInfoList = response.body();

                    if (response.isSuccessful()) {
                        TextView empText = findViewById(R.id.id_empty_machineData);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(SomityMemberDetailsUI.this);
                        recyclerView = (RecyclerView) findViewById(R.id.rvTatInformation);
                        recyclerView.setHasFixedSize(true);

                        recyclerView.addItemDecoration(new DividerItemDecoration(SomityMemberDetailsUI.this,
                                DividerItemDecoration.HORIZONTAL));
                        recyclerView.addItemDecoration(new DividerItemDecoration(SomityMemberDetailsUI.this,
                                DividerItemDecoration.VERTICAL));
                        TextView textView = (TextView) findViewById(R.id.empty_rcView_somityList);

                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setLayoutManager(layoutManager);

                        if (tatInfoList != null && tatInfoList.size() > 0) {

                            rvMachineAdapter = new RvMachineAdapter(getApplicationContext(), tatInfoList);
                            recyclerView.setAdapter(rvMachineAdapter);

                        } else {
                            empText.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    }
                  /*  recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {

                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));*/

                }

                @Override
                public void onFailure(Call<List<TatInfo>> call, Throwable t) {
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
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        etDate.setText(sdf2.format(myCalendar.getTime()));
        //  String stringDate = sdf1.format(myCalendar.getTimeInMillis());
        // longDate = Long.valueOf(stringDate);
        longDate = myCalendar.getTimeInMillis();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
