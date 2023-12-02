package com.bz.em.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.bz.em.R;
import com.bz.em.connection.ApiConnection;
import com.bz.em.model.UserDtl;
import com.bz.em.service.ApiService;
import com.bz.em.utils.EmConstants;
import com.bz.em.utils.EmSharedPreferenceManager;
import com.bz.em.utils.EmUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginUI extends AppCompatActivity {

    private EditText etUserName;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ui);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorNew));
        }

        etUserName = findViewById(R.id.login_userName);
        etPassword = findViewById(R.id.login_password);
        // etUserName.setText("admin");
        // etPassword.setText("123");
    }

    public void usrLogin(View view) {
        String userName = etUserName.getText().toString();
        String userPass = etPassword.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "অনুগ্রহ করে লগইন আইডি দিন। ", Toast.LENGTH_SHORT).show();
          //  Toast.makeText(this, "Please input username and password", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(userPass)) {
            Toast.makeText(this, "অনুগ্রহ করে পাসওয়ার্ড দিন।", Toast.LENGTH_SHORT).show();
          //  Toast.makeText(this, "Please input password", Toast.LENGTH_SHORT).show();
        } else {

            getUserLoginInfo(userName, userPass);
           /* if ((userName.equals("admin") && userPass.equals("123")) || (userName.equals("user") && userPass.equals("123"))) {
                EmSharedPreferenceManager.saveInt("LOGIN_FLAG", 1, LoginUI.this);
                EmSharedPreferenceManager.saveString("USER_NAME", userName, LoginUI.this);

                Intent intent = new Intent(this, DashboardUI.class);
               // intent.putExtra("USER_NAME", userName);
                startActivity(intent);
                killActivity();

            } else {

                Toast.makeText(this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();

            }*/
        }
    }

    private void killActivity() {
        LoginUI.this.finish();
    }

    private void getUserLoginInfo(final String username, String password) {


        try {
            EmUtils.showProgress(this);
            ApiService service = ApiConnection.getRetrofit().create(ApiService.class);
            Call<UserDtl> call = service.userLogin(username, password);

            call.enqueue(new Callback<UserDtl>() {
                @Override
                public void onResponse(Call<UserDtl> call, Response<UserDtl> response) {
                    EmUtils.dissmisProgress();

                    try {
                        UserDtl userDtl = response.body();

                        boolean success = userDtl.isSuccess();

                        List<UserDtl> offices = userDtl.getOffices();

                        String officeName;
                        long officeId;
                        String officeShortCode;

                        for (UserDtl userD : offices) {
                            officeName = userD.getOfficeName();
                            officeId = userD.getOfficeId();
                            officeShortCode = userD.getShortCode();
                            EmSharedPreferenceManager.saveLong("OFFICE_ID", officeId, LoginUI.this);
                            EmSharedPreferenceManager.saveString("OFFICE_NAME", officeName, LoginUI.this);
                        }

                        long id = userDtl.getId();
                        String userType = userDtl.getUserType();
                        String usernames = userDtl.getUsername();
                        String error = userDtl.getError();

                        if (success) {

                            EmSharedPreferenceManager.saveInt("LOGIN_FLAG", 1, LoginUI.this);
                            EmSharedPreferenceManager.saveString("USER_NAME", usernames, LoginUI.this);
                            EmSharedPreferenceManager.saveString("USER_TYPE", userType, LoginUI.this);
                            EmSharedPreferenceManager.saveLong("USER_ID", id, LoginUI.this);

                            Intent intent = new Intent(LoginUI.this, DashboardUI.class);
                            startActivity(intent);
                            killActivity();
                        } else {
                            Toast.makeText(LoginUI.this, response.body().getError(), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(LoginUI.this, "আপনার লগইন আইডি অথবা পাসওয়ার্ড সঠিক নয়। ", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<UserDtl> call, Throwable t) {
                    EmUtils.dissmisProgress();
                    Log.d("erro_log", t.fillInStackTrace().toString());
                      Toast.makeText(LoginUI.this, EmConstants.SERVER_ERROR_MASSAGE, Toast.LENGTH_LONG).show();
                    Log.e("erro_log", t.getMessage());
                }
            });

        } catch (Exception e) {
            EmUtils.dissmisProgress();
            e.printStackTrace();
        }
    }

}
