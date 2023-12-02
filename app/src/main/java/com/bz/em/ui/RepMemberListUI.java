package com.bz.em.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.bz.em.adapter.RvRepaySomityMemberListAdapter;
import com.bz.em.adapter.RvSomityMemberListAdapter;
import com.bz.em.connection.ApiConnection;
import com.bz.em.model.SomityMemberDetail;
import com.bz.em.service.ApiService;
import com.bz.em.utils.EmConstants;
import com.bz.em.utils.EmNetworkStateCheck;
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

import java.util.List;

public class RepMemberListUI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rep_member_list_ui);
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
       // Long user_id = EmSharedPreferenceManager.getLongVal("USER_ID", this);

        if (EmNetworkStateCheck.checkNetConnection(RepMemberListUI.this)) {
            getSomityMember(somityId);
        } else {
            EmUtils.vibrate(RepMemberListUI.this, 400);
            Toast.makeText(RepMemberListUI.this, EmConstants.NETWORK_ERROR_MSG, Toast.LENGTH_LONG).show();
        }
    }

    private List<SomityMemberDetail> memberList;

    private void getSomityMember(Long samityId) {

        try {
            EmUtils.showProgress(this);
            ApiService service = ApiConnection.getRetrofit().create(ApiService.class);
            Call<List<SomityMemberDetail>> call = service.getRepaymentSamityMemberList(samityId);

            call.enqueue(new Callback<List<SomityMemberDetail>>() {
                @Override
                public void onResponse(Call<List<SomityMemberDetail>> call, Response<List<SomityMemberDetail>> response) {
                    EmUtils.dissmisProgress();
                    memberList = response.body();

                    LinearLayoutManager layoutManager = new LinearLayoutManager(RepMemberListUI.this);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcv_repay_somityMemberList);
                    recyclerView.setHasFixedSize(true);
                    TextView textView = (TextView) findViewById(R.id.empty_rcView_repay_somityMemberList);

                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setLayoutManager(layoutManager);

                    if (memberList == null || memberList.size() == 0) {
                        recyclerView.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                    } else {
                        textView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        RvRepaySomityMemberListAdapter adapterRecycler = new RvRepaySomityMemberListAdapter(getApplicationContext(), memberList);
                        recyclerView.setAdapter(adapterRecycler);
                    }
                    recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {

                            if (EmNetworkStateCheck.checkNetConnection(RepMemberListUI.this)) {

                                Intent intent = new Intent(RepMemberListUI.this, RepayLoanCollectionUI.class);
                                intent.putExtra("MEMBER_NAME", memberList.get(position).getApplicantName());
                                intent.putExtra("SOMITY_NAME", memberList.get(position).getSamityName());
                                intent.putExtra("SOMITY_ID", memberList.get(position).getSamityId());

                                intent.putExtra("BASIC_CENTER_ID", memberList.get(position).getBasicCenterId());
                                intent.putExtra("SOMITY_MEMBER_ID", memberList.get(position).getId());
                                intent.putExtra("SOMITY_MEMBER_ID", memberList.get(position).getSamityMemberId());

                                intent.putExtra("LOAN_ACC_ID", memberList.get(position).getLoanAccountId());
                                startActivity(intent);


                            } else {
                                EmUtils.vibrate(RepMemberListUI.this, 400);
                                Toast.makeText(RepMemberListUI.this, EmConstants.NETWORK_ERROR_MSG, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));
                }

                @Override
                public void onFailure(Call<List<SomityMemberDetail>> call, Throwable t) {
                    EmUtils.dissmisProgress();
                    Log.d("erro_log", t.fillInStackTrace().toString());
                    Toast.makeText(RepMemberListUI.this, EmConstants.SERVER_ERROR_MASSAGE, Toast.LENGTH_LONG).show();
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
