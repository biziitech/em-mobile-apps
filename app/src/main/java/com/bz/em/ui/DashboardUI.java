package com.bz.em.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.bz.em.R;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;

import com.bz.em.utils.EmConstants;
import com.bz.em.utils.EmNetworkStateCheck;
import com.bz.em.utils.EmSharedPreferenceManager;
import com.bz.em.utils.EmUtils;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardUI extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorNew));
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        TextView emptyTv = findViewById(R.id.tv_id_emptyText);
        CardView cvLoanInspectionList = findViewById(R.id.cvLoanInspectionList);
        CardView cvProfile = findViewById(R.id.cvUserProfile);
        CardView cvLoanInfo = findViewById(R.id.cvLoanInfo);
        CardView cvInstallmentInfo = findViewById(R.id.cvInstallmentInfo);
        CardView cvSomitylmentInfo = findViewById(R.id.cvSomityInspectionList);
        CardView cvRepayments = findViewById(R.id.cvRepayment);

        Intent intent = getIntent();

        //String userName = intent.getExtras().getString("USER_NAME");
        //String userName="admin";

        String userName = EmSharedPreferenceManager.getString("USER_NAME", this);
        String userType = EmSharedPreferenceManager.getString("USER_TYPE", this);

        if (userType.equals("INSPECTOR") || userType.equals("LO")) {   // LO type user added by ashraf: 23/11/23

            cvLoanInspectionList.setVisibility(View.VISIBLE);
            cvSomitylmentInfo.setVisibility(View.VISIBLE);
            cvRepayments.setVisibility(View.VISIBLE);
            cvProfile.setVisibility(View.GONE);
            cvLoanInfo.setVisibility(View.GONE);
            cvInstallmentInfo.setVisibility(View.GONE);

        } else if (userType.equals("WEAVER")) {

            cvLoanInspectionList.setVisibility(View.GONE);
            cvSomitylmentInfo.setVisibility(View.GONE);
            cvRepayments.setVisibility(View.GONE);
            cvProfile.setVisibility(View.VISIBLE);
            cvLoanInfo.setVisibility(View.VISIBLE);

          //  cvInstallmentInfo.setVisibility(View.VISIBLE);
        } else {

            emptyTv.setVisibility(View.VISIBLE);
            emptyTv.setText(userType + " dashboard is empty !");

        }

        View headerView = navigationView.getHeaderView(0);
        ImageView userProfilePic = headerView.findViewById(R.id.idNavProfilePic);
        TextView userFullName = headerView.findViewById(R.id.idNavUserName);
        TextView userDesignation = headerView.findViewById(R.id.idNavDesignation);
        userDesignation.setText(userName + "@eloan.com");


        /*CardView cvAssignedTask = findViewById(R.id.cardViewAssignedTask);
        cvAssignedTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(DashboardUI.this, AssignedTaskListUI.class);
                //startActivity(intent);
            }
        });*/


        //somity inspection
        cvLoanInspectionList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EmNetworkStateCheck.checkNetConnection(DashboardUI.this)) {
                    Intent intent = new Intent(DashboardUI.this, SomityListUI.class);
                    intent.putExtra("NAV_TYPE", "LOAN_INSPECTION");
                    startActivity(intent);
                } else {
                    EmUtils.vibrate(DashboardUI.this, 400);
                    Toast.makeText(DashboardUI.this, EmConstants.NETWORK_ERROR_MSG, Toast.LENGTH_LONG).show();
                }
            }
        });
        //somity inspection list
        cvSomitylmentInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EmNetworkStateCheck.checkNetConnection(DashboardUI.this)) {
                    Intent intent = new Intent(DashboardUI.this, SomityListUI.class);
                    intent.putExtra("NAV_TYPE", "SOMITY_INSPECTION");
                    startActivity(intent);
                } else {
                    EmUtils.vibrate(DashboardUI.this, 400);
                    Toast.makeText(DashboardUI.this, EmConstants.NETWORK_ERROR_MSG, Toast.LENGTH_LONG).show();
                }

            }
        });

        //somity inspection list
        cvRepayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EmNetworkStateCheck.checkNetConnection(DashboardUI.this)) {
                    Intent intent = new Intent(DashboardUI.this, SomityListUI.class);
                    intent.putExtra("NAV_TYPE", "REPAYMENTS");
                    startActivity(intent);
                } else {
                    EmUtils.vibrate(DashboardUI.this, 400);
                    Toast.makeText(DashboardUI.this, EmConstants.NETWORK_ERROR_MSG, Toast.LENGTH_LONG).show();
                }

            }
        });


        cvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardUI.this, UserProfileUI.class);
                startActivity(intent);
            }
        });
        cvLoanInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardUI.this, LoanInfoUI.class);
                startActivity(intent);
            }
        });
        cvInstallmentInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardUI.this, InstallmentInfoUI.class);
                startActivity(intent);
            }
        });
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
        } else if (id == R.id.nav_notification) {
            Intent mainIntent = new Intent(this, NotificationUI.class);
            startActivity(mainIntent);
        } /*else if (id == R.id.nav_user_profile) {
            Intent mainIntent = new Intent(this, UserProfileUI.class);
            startActivity(mainIntent);
        }*/ else if (id == R.id.nav_change_password) {
            Intent mainIntent = new Intent(this, PasswordChangeUI.class);
            startActivity(mainIntent);
        } else if (id == R.id.nav_log_out) {
            EmSharedPreferenceManager.saveInt("LOGIN_FLAG", 0, DashboardUI.this);
            EmSharedPreferenceManager.saveString("USER_NAME", null, DashboardUI.this);

            Intent mainIntent = new Intent(this, LoginUI.class);
            startActivity(mainIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DashboardUI.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
