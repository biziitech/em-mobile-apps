package com.bz.em.ui;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.bz.em.connection.ApiConnection;
import com.bz.em.model.UserDtl;
import com.bz.em.model.UserProfile;
import com.bz.em.service.ApiService;
import com.bz.em.utils.EmConstants;
import com.bz.em.utils.EmSharedPreferenceManager;
import com.bz.em.utils.EmUtils;

import java.util.List;

public class UserProfileUI extends AppCompatActivity {

    private TextView tvNid, tvDob,
            tvFullName, tvFatherName,
            tvMotherName, tvSomityName,
            tvUserType, tvMemberNo, tvMobileNo, basicCenterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_ui);

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

        tvNid = findViewById(R.id.tvNid);
        //tvDob = findViewById(R.id.tvDob);
        tvFullName = findViewById(R.id.tvFullName);
        //tvFatherName = findViewById(R.id.tvFatherName);
       // tvMotherName = findViewById(R.id.tvMotherName);
        tvSomityName = findViewById(R.id.tvSomityName);
        tvUserType = findViewById(R.id.tvUserType);
        tvMemberNo = findViewById(R.id.tvMemberNo);
        tvMobileNo = findViewById(R.id.tvMobileNo);
        basicCenterId = findViewById(R.id.tvBasicCenterId);

        getUserProfile();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getUserProfile() {

        Long userId = EmSharedPreferenceManager.getLongVal("USER_ID", UserProfileUI.this);
        Log.d("userId", userId + "");

        try {
            EmUtils.showProgress(this);
            ApiService service = ApiConnection.getRetrofit().create(ApiService.class);
            Call<UserProfile> call = service.getUserDtl(userId);

            call.enqueue(new Callback<UserProfile>() {
                @Override
                public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                    EmUtils.dissmisProgress();
                    UserProfile userProfile = response.body();


                    if (userProfile.getNid()!=null){

                        tvNid.setText(userProfile.getNid());
                    }else{
                        tvNid.setText("--");

                    }
                    //tvDob.setText();
                    tvFullName.setText(userProfile.getFullName());
                    //tvFatherName.setText();
                    //tvMotherName.setText();
                    //tvSomityName.setText();
                    //tvSomtyRegNo.setText();
                    tvUserType.setText(userProfile.getUserType());
                    tvMemberNo.setText(userProfile.getId() + "");
                    tvMobileNo.setText(userProfile.getContactNo());
                    basicCenterId.setText(userProfile.getBasicCenterId() + "");


                }

                @Override
                public void onFailure(Call<UserProfile> call, Throwable t) {
                    EmUtils.dissmisProgress();
                    Log.d("erro_log", t.fillInStackTrace().toString());
                    //Toast.makeText(SomityMemberListUI.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    Toast.makeText(UserProfileUI.this, EmConstants.SERVER_ERROR_MASSAGE, Toast.LENGTH_LONG).show();

                }
            });

        } catch (Exception e) {
            EmUtils.dissmisProgress();
            e.printStackTrace();
        }
    }
}
