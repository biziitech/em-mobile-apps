package com.bz.em.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.bz.em.adapter.RvSubmittedImgDisplayAdapter;
import com.bz.em.connection.ApiConnection;
import com.bz.em.service.ApiService;
import com.bz.em.utils.EmUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplaySubmittedImgUi extends AppCompatActivity {

    private String navType;
    private  long loan_id ;
    private  long samityMemberId ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_submitted_img_ui);

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

        final Intent intent = getIntent();

        navType = intent.getExtras().getString("NAV_TYPE");
        if (navType.equals("LOAN_INSPECTION")) {

            loan_id = intent.getExtras().getLong("LOAN_ID");
            getSubmittedPhoto(loan_id);

        }else {
            samityMemberId = intent.getExtras().getLong("member_id");
            getSubmittedPhoto(samityMemberId);
        }
    }

    private void getSubmittedPhoto(long loanApplicationId) {


        try {

            EmUtils.showProgress(this);
            ApiService service = ApiConnection.getRetrofit().create(ApiService.class);
            Call<List<String>> call=null;

            if (navType.equals("LOAN_INSPECTION")) {

                call= service.getSubmittedPhotos(loanApplicationId);
            }else {

                call= service.getSamitySubmittedPhotos(loanApplicationId);
            }

            call.enqueue(new Callback<List<String>>() {
                @Override
                public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                    EmUtils.dissmisProgress();
                    List<String> photos = null;

                    photos = response.body();

                    //Log.d("photos", response.body().toString());

                    // Toast.makeText(DisplaySubmittedImgUi.this, response.body().toString(), Toast.LENGTH_SHORT).show();

                    if (response.isSuccessful()) {

                        LinearLayoutManager layoutManager = new LinearLayoutManager(DisplaySubmittedImgUi.this);
                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcv_display_img);
                        recyclerView.setHasFixedSize(true);
                        TextView textView = (TextView) findViewById(R.id.empty_rcView_image);

                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setLayoutManager(layoutManager);

                        if (photos == null || photos.size() == 0) {
                            recyclerView.setVisibility(View.GONE);
                            textView.setVisibility(View.VISIBLE);
                        } else {
                            textView.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            RvSubmittedImgDisplayAdapter rvSubmittedImgDisplayAdapter = new RvSubmittedImgDisplayAdapter(getApplicationContext(), photos);
                            recyclerView.setAdapter(rvSubmittedImgDisplayAdapter);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<String>> call, Throwable t) {
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
