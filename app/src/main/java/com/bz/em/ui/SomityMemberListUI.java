package com.bz.em.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.bz.em.adapter.RvSomityMemberListAdapter;
import com.bz.em.connection.ApiConnection;
import com.bz.em.model.LoanInspectionData;
import com.bz.em.model.SamityMemberData;
import com.bz.em.model.Somity;
import com.bz.em.model.SomityMemberDetail;
import com.bz.em.service.ApiService;
import com.bz.em.utils.EmConstants;
import com.bz.em.utils.EmNetworkStateCheck;
import com.bz.em.utils.EmSharedPreferenceManager;
import com.bz.em.utils.EmUtils;
import com.bz.em.utils.RecyclerTouchListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bz.em.R;

import java.util.ArrayList;
import java.util.List;

public class SomityMemberListUI extends AppCompatActivity {


    private String navType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_somity_member_list_ui);
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
        Long somityId = intent.getExtras().getLong("somity_id");
        Long user_id = EmSharedPreferenceManager.getLongVal("USER_ID", this);
        navType = intent.getExtras().getString("NAV_TYPE");


        if (EmNetworkStateCheck.checkNetConnection(SomityMemberListUI.this)) {
            if (navType.equals("LOAN_INSPECTION")) {
                getSomityMember(somityId, user_id);
            }else {
                getSamityInspectionSomityMember(somityId, user_id);
            }

        } else {
            EmUtils.vibrate(SomityMemberListUI.this, 400);
            Toast.makeText(SomityMemberListUI.this, EmConstants.NETWORK_ERROR_MSG, Toast.LENGTH_LONG).show();
        }
    }


    private void getSomityMember(Long somityId, Long inspectorId) {

        try {
            EmUtils.showProgress(this);
            ApiService service = ApiConnection.getRetrofit().create(ApiService.class);
            Call<List<SomityMemberDetail>> call = null;

            if (navType.equals("LOAN_INSPECTION")) {

                call = service.getSomityMemberList(somityId, inspectorId);

            }

            call.enqueue(new Callback<List<SomityMemberDetail>>() {
                @Override
                public void onResponse(Call<List<SomityMemberDetail>> call, Response<List<SomityMemberDetail>> response) {
                    EmUtils.dissmisProgress();
                    List<SomityMemberDetail> memberList = response.body();

                    LinearLayoutManager layoutManager = new LinearLayoutManager(SomityMemberListUI.this);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcv_somityMemberList);
                    recyclerView.setHasFixedSize(true);
                    TextView textView = (TextView) findViewById(R.id.empty_rcView_somityMemberList);

                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setLayoutManager(layoutManager);

                    if (memberList == null || memberList.size() == 0) {
                        recyclerView.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                    } else {
                        textView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        RvSomityMemberListAdapter adapterRecycler = new RvSomityMemberListAdapter(getApplicationContext(), memberList, navType);
                        recyclerView.setAdapter(adapterRecycler);
                    }
                }

                @Override
                public void onFailure(Call<List<SomityMemberDetail>> call, Throwable t) {
                    EmUtils.dissmisProgress();
                    Log.d("erro_log", t.fillInStackTrace().toString());
                    Toast.makeText(SomityMemberListUI.this, EmConstants.SERVER_ERROR_MASSAGE, Toast.LENGTH_LONG).show();
                }
            });

        } catch (Exception e) {
            EmUtils.dissmisProgress();
            e.printStackTrace();
        }
    }


    private void getSamityInspectionSomityMember(Long somityId, Long inspectorId) {

        try {
            EmUtils.showProgress(this);
            ApiService service = ApiConnection.getRetrofit().create(ApiService.class);
            Call<List<SamityMemberData>> call = service.getSamityInspectionSamityMemberList(somityId, inspectorId);

            call.enqueue(new Callback<List<SamityMemberData>>() {
                @Override
                public void onResponse(Call<List<SamityMemberData>> call, Response<List<SamityMemberData>> response) {
                    EmUtils.dissmisProgress();
                    List<SamityMemberData> memberList = response.body();

                    LinearLayoutManager layoutManager = new LinearLayoutManager(SomityMemberListUI.this);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcv_somityMemberList);
                    recyclerView.setHasFixedSize(true);
                    TextView textView = (TextView) findViewById(R.id.empty_rcView_somityMemberList);

                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setLayoutManager(layoutManager);

                    if (memberList == null || memberList.size() == 0) {
                        recyclerView.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                    } else {
                        textView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        RvSomityMemberListAdapter adapterRecycler = new RvSomityMemberListAdapter(getApplicationContext(), memberList,navType,"eex");
                        recyclerView.setAdapter(adapterRecycler);
                    }
                }

                @Override
                public void onFailure(Call<List<SamityMemberData>> call, Throwable t) {
                    EmUtils.dissmisProgress();
                    Log.d("erro_log", t.fillInStackTrace().toString());
                    Toast.makeText(SomityMemberListUI.this, EmConstants.SERVER_ERROR_MASSAGE, Toast.LENGTH_LONG).show();
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
