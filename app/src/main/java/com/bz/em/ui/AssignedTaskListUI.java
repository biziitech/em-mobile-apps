package com.bz.em.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.bz.em.adapter.RvAssignedTaskListAdapter;
import com.bz.em.model.AssignedTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bz.em.R;
import com.bz.em.utils.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class AssignedTaskListUI extends AppCompatActivity {

    private RecyclerView rvAssignedTask;
    private List<AssignedTask> assignedTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigned_task_list_ui);

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

       // rvAssignedTask = findViewById(R.id.rcv_assignedTaskList);
        assignedTaskList = new ArrayList<>();
        AssignedTask assignedTask = new AssignedTask("05456500", "Rehana", "Yakub Ai","Mirpur, Dhaka","017748884","12-12-2019");
        AssignedTask assignedTask2 = new AssignedTask("05456501", "Habib ", "Abul Kasem","Jatrabari, Dhaka","017748884","12-10-2019");
        AssignedTask assignedTask3 = new AssignedTask("44054565", "Sumon Khan", "Mir Ai","Dohar, Dhaka","017748884","12-11-2019");
        AssignedTask assignedTask4 = new AssignedTask("11054565", "Rehana", "Yakub Ai","Mirpur, Dhaka","018748884","12-12-2019");
        AssignedTask assignedTask5 = new AssignedTask("55054565", "Sajahan", "abdul Karim","Dhanmondi, Dhaka","013748884","12-12-2019");
        AssignedTask assignedTask6 = new AssignedTask("0554565", "Sumonana", "Yakub Ai","Malibag, Dhaka","014748884","17-12-2019");
        assignedTaskList.add(assignedTask);
        assignedTaskList.add(assignedTask2);
        assignedTaskList.add(assignedTask3);
        assignedTaskList.add(assignedTask4);
        assignedTaskList.add(assignedTask5);
        assignedTaskList.add(assignedTask6);

        getTaskList();
    }

    private void getTaskList() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(AssignedTaskListUI.this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcv_assignedTaskList);
        recyclerView.setHasFixedSize(true);
        TextView textView = (TextView) findViewById(R.id.empty_rcView_assignedTask);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);

        if (assignedTaskList == null || assignedTaskList.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            RvAssignedTaskListAdapter adapterRecycler = new RvAssignedTaskListAdapter(getApplicationContext(), assignedTaskList);
            recyclerView.setAdapter(adapterRecycler);
        }
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(AssignedTaskListUI.this, MemberDetailUI.class);
                intent.putExtra("NAME",assignedTaskList.get(position).getMemberName());
                intent.putExtra("FATHER_NAME",assignedTaskList.get(position).getFatherName());
                intent.putExtra("NID",assignedTaskList.get(position).getNidNo());
                intent.putExtra("ADDRESS",assignedTaskList.get(position).getAddress());
                intent.putExtra("MOBILE_NO",assignedTaskList.get(position).getMobileNo());
                intent.putExtra("INSPECTION_DATE",assignedTaskList.get(position).getInspectionDate());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
