package com.bz.em.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.bz.em.adapter.RvSomityListAdapter;
import com.bz.em.connection.ApiConnection;
import com.bz.em.model.Somity;
import com.bz.em.service.ApiService;
import com.bz.em.utils.EmConstants;
import com.bz.em.utils.EmNetworkStateCheck;
import com.bz.em.utils.EmSharedPreferenceManager;
import com.bz.em.utils.EmUtils;
import com.bz.em.utils.RecyclerTouchListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bz.em.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SomityListUI extends AppCompatActivity {

   // private RecyclerView rvsSomity;
    private String navType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_somity_list_ui);

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

        navType = intent.getExtras().getString("NAV_TYPE");


        if (EmNetworkStateCheck.checkNetConnection(SomityListUI.this)) {
            gtSomityList();
        } else {
            EmUtils.vibrate(SomityListUI.this, 400);
            Toast.makeText(SomityListUI.this, EmConstants.NETWORK_ERROR_MSG, Toast.LENGTH_LONG).show();
        }
    }


    private void gtSomityList() {

        long officeID = EmSharedPreferenceManager.getLongVal("OFFICE_ID", this);
        long user_id = EmSharedPreferenceManager.getLongVal("USER_ID", this);

        try {
            EmUtils.showProgress(this);
            ApiService service = ApiConnection.getRetrofit().create(ApiService.class);
            Call<List<Somity>> call = null;


            if (navType.equals("REPAYMENTS")) {
                call = service.getRepaymentSomityList();
            } else if (navType.equals("LOAN_INSPECTION")) {
                call = service.getSomityList(user_id);
            } else {
                call = service.getSamityInspectionSomityList(user_id);
            }

            call.enqueue(new Callback<List<Somity>>() {
                @Override
                public void onResponse(Call<List<Somity>> call, Response<List<Somity>> response) {
                    EmUtils.dissmisProgress();
                    List<Somity> tatInfoList = null;

                    final List<Somity> somityList = response.body();
                    if (response.isSuccessful()) {

                        LinearLayoutManager layoutManager = new LinearLayoutManager(SomityListUI.this);
                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcv_somityList);
                        recyclerView.setHasFixedSize(true);
                        TextView textView = (TextView) findViewById(R.id.empty_rcView_somityList);

                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setLayoutManager(layoutManager);

                        if (somityList == null || somityList.size() == 0) {
                            recyclerView.setVisibility(View.GONE);
                            textView.setVisibility(View.VISIBLE);
                        } else {
                            textView.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            RvSomityListAdapter adapterRecycler = new RvSomityListAdapter(getApplicationContext(), somityList,navType);
                            recyclerView.setAdapter(adapterRecycler);
                        }
                        /*recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
                            @Override
                            public void onClick(View view, int position) {

                                if (EmNetworkStateCheck.checkNetConnection(SomityListUI.this)) {
                                    Intent intent;
                                    if (navType.equals("REPAYMENTS")) {

                                        intent = new Intent(SomityListUI.this, RepMemberListUI.class);
                                        intent.putExtra("somity_id", somityList.get(position).getSamityId());
                                        startActivity(intent);
                                    } else if (navType.equals("LOAN_INSPECTION")) {

                                        intent = new Intent(SomityListUI.this, SomityMemberListUI.class);
                                        intent.putExtra("NAV_TYPE", "LOAN_INSPECTION");
                                        intent.putExtra("somity_id", somityList.get(position).getSamityId());
                                        startActivity(intent);
                                    } else {
                                        intent = new Intent(SomityListUI.this, SomityMemberListUI.class);
                                        intent.putExtra("NAV_TYPE", "SOMITY_INSPECTION");
                                        intent.putExtra("somity_id", somityList.get(position).getSamityId());
                                        startActivity(intent);
                                    }

                                } else {
                                    EmUtils.vibrate(SomityListUI.this, 400);
                                    Toast.makeText(SomityListUI.this, EmConstants.NETWORK_ERROR_MSG, Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onLongClick(View view, int position) {

                            }
                        }));*/
                    }
                }

                @Override
                public void onFailure(Call<List<Somity>> call, Throwable t) {
                    EmUtils.dissmisProgress();
                    Log.d("erro_log", t.fillInStackTrace().toString());
                    //  Toast.makeText(SomityMemberDetailsUI.this, EmConstants.SERVER_ERROR_MASSAGE, Toast.LENGTH_LONG).show();
                    Log.e("erro_log", t.getMessage());
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
