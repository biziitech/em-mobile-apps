package com.bz.em.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.bz.em.adapter.RvRepayLoanCollectionAdapter;
import com.bz.em.connection.ApiConnection;
import com.bz.em.model.DueCollection;
import com.bz.em.model.DueLoanCollectionData;
import com.bz.em.model.LoanCollectionInfo;
import com.bz.em.model.ResponseCollectionData;
import com.bz.em.service.ApiService;
import com.bz.em.utils.EmConstants;
import com.bz.em.utils.EmNetworkStateCheck;
import com.bz.em.utils.EmUtils;

import androidx.appcompat.app.AlertDialog;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bz.em.R;

import java.util.ArrayList;
import java.util.Arrays;

public class RepayLoanCollectionUI extends AppCompatActivity {

    private String applicantName;
    private String somityName;
    private long somityId;

    private long basicCenterId;
    private long somityMemberId;

    private TextView tvName;
    private TextView tvInstallmentAmt;
    private TextView etCollectionAmt;
    private Button btnSubmitLoanCollection;
    public static long loanAccId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repay_loan_collection_ui);
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

        tvName = findViewById(R.id.tvApplicantName);

        Intent intent = getIntent();
        //applicantName = intent.getExtras().getString("MEMBER_NAME");
        //somityName = intent.getExtras().getString("SOMITY_NAME");
        //somityId = intent.getExtras().getLong("SOMITY_ID");

        //basicCenterId = intent.getExtras().getLong("BASIC_CENTER_ID");
        //somityMemberId = intent.getExtras().getLong("SOMITY_MEMBER_ID");

        loanAccId = intent.getExtras().getLong("LOAN_ACC_ID");


        if (EmNetworkStateCheck.checkNetConnection(RepayLoanCollectionUI.this)) {
            // getLoanAccountId();
            getDuePayments(loanAccId);
        } else {
            EmUtils.vibrate(RepayLoanCollectionUI.this, 400);
            Toast.makeText(RepayLoanCollectionUI.this, EmConstants.NETWORK_ERROR_MSG, Toast.LENGTH_LONG).show();
        }
    }

    private void getDuePayments(long loanAccountId) {

        try {
            EmUtils.showProgress(this);
            ApiService service = ApiConnection.getRetrofit().create(ApiService.class);
            Call<DueLoanCollectionData> call = service.getDueCollectionList(loanAccountId);

            call.enqueue(new Callback<DueLoanCollectionData>() {
                @Override
                public void onResponse(Call<DueLoanCollectionData> call, Response<DueLoanCollectionData> response) {
                    EmUtils.dissmisProgress();

                    if (response.isSuccessful()) {
                        DueLoanCollectionData data = response.body();
                        ArrayList<DueCollection> datas = new ArrayList<>(Arrays.asList(data.getData()));

                        LinearLayoutManager layoutManager = new LinearLayoutManager(RepayLoanCollectionUI.this);
                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcView_Collection_List);
                        recyclerView.setHasFixedSize(true);
                        TextView textView = (TextView) findViewById(R.id.empty_rcView_Collection_List);

                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setLayoutManager(layoutManager);

                        if (datas == null || datas.size() == 0) {
                            recyclerView.setVisibility(View.GONE);
                            textView.setVisibility(View.VISIBLE);
                        } else {
                            textView.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            RvRepayLoanCollectionAdapter adapterRecycler = new RvRepayLoanCollectionAdapter(getApplicationContext(), datas);
                            recyclerView.setAdapter(adapterRecycler);
                        }
                    }
                }

                @Override
                public void onFailure(Call<DueLoanCollectionData> call, Throwable t) {
                    EmUtils.dissmisProgress();
                    Log.d("erro_log", t.fillInStackTrace().toString());
                    Toast.makeText(RepayLoanCollectionUI.this, EmConstants.SERVER_ERROR_MASSAGE, Toast.LENGTH_LONG).show();

                }
            });

        } catch (Exception e) {
            EmUtils.dissmisProgress();
            e.printStackTrace();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
