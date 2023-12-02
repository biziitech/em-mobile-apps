package com.bz.em.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.bz.em.adapter.RvLoanInfoAdapter;
import com.bz.em.adapter.RvLoanInstallmentListAdapter;
import com.bz.em.connection.ApiConnection;
import com.bz.em.model.InstallmentInfo;
import com.bz.em.model.InstalmentDtl;
import com.bz.em.model.LoanInfo;
import com.bz.em.model.LoanInfoDtl;
import com.bz.em.service.ApiService;
import com.bz.em.utils.EmConstants;
import com.bz.em.utils.EmSharedPreferenceManager;
import com.bz.em.utils.EmUtils;
import com.bz.em.utils.RecyclerTouchListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bz.em.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InstallmentInfoUI extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installment_info_ui);

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
        Long loan_Id = intent.getExtras().getLong("loan_Id");

        getInstallmentInfo(loan_Id);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getInstallmentInfo(Long loanId) {

       // Long userId = EmSharedPreferenceManager.getLongVal("USER_ID", InstallmentInfoUI.this);
      //  Log.d("userId", userId + "");

        try {
            EmUtils.showProgress(this);
            ApiService service = ApiConnection.getRetrofit().create(ApiService.class);
            Call<InstallmentInfo> call = service.getInstallmentInfo(loanId);

            call.enqueue(new Callback<InstallmentInfo>() {
                @Override
                public void onResponse(Call<InstallmentInfo> call, Response<InstallmentInfo> response) {
                    EmUtils.dissmisProgress();

                    InstallmentInfo data = response.body();

                    ArrayList<InstalmentDtl> dataList = new ArrayList<>(Arrays.asList(data.getDataList()));

                    LinearLayoutManager layoutManager = new LinearLayoutManager(InstallmentInfoUI.this);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcv_loan_installmentInfo_List);
                    recyclerView.setHasFixedSize(true);
                    TextView textView = (TextView) findViewById(R.id.empty_rcView_loan_installment_info_List);

                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setLayoutManager(layoutManager);

                    if (dataList == null || dataList.size() == 0) {
                        recyclerView.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                    } else {
                        textView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        RvLoanInstallmentListAdapter rvLoanInstallmentListAdapter = new RvLoanInstallmentListAdapter(getApplicationContext(), dataList);
                        recyclerView.setAdapter(rvLoanInstallmentListAdapter);
                    }

                }

                @Override
                public void onFailure(Call<InstallmentInfo> call, Throwable t) {
                    EmUtils.dissmisProgress();
                    Log.d("erro_log", t.fillInStackTrace().toString());
                    //Toast.makeText(SomityMemberListUI.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    Toast.makeText(InstallmentInfoUI.this, EmConstants.SERVER_ERROR_MASSAGE, Toast.LENGTH_LONG).show();

                }
            });

        } catch (Exception e) {
            EmUtils.dissmisProgress();
            e.printStackTrace();
        }
    }
}
