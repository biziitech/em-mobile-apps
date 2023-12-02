package com.bz.em.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bz.em.R;
import com.bz.em.model.AssignedTask;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Bellal Hossain
 **/
public class RvAssignedTaskListAdapter extends RecyclerView.Adapter<RvAssignedTaskListAdapter.ViewHolder> {

    private List<AssignedTask> taskList;
    private Context context;
    private int listItemPosition;


    public RvAssignedTaskListAdapter(Context context, List<AssignedTask> taskList) {
        this.context = context;
        this.taskList = taskList;
    }


    @NonNull
    @Override
    public RvAssignedTaskListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_assigned_task_list, parent, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        return new RvAssignedTaskListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.txtName.setText(taskList.get(position).getMemberName());
            holder.txtMobileNo.setText(taskList.get(position).getMobileNo());
            holder.txtAddress.setText(taskList.get(position).getAddress());

        try {

            if (position % 2 == 1) {
                holder.cardTaskLst.setBackgroundColor(Color.parseColor("#99bbff"));
            } else {
                holder.cardTaskLst.setBackgroundColor(Color.parseColor("#b3ccff"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtName;
        private TextView txtMobileNo;
        private TextView txtAddress;
        private CardView cardTaskLst;

        ViewHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.memberName);
            txtMobileNo = itemView.findViewById(R.id.tvMobileNo);
            txtAddress = itemView.findViewById(R.id.tvAddress);
        }

        @Override
        public void onClick(View v) {

        }
    }
}