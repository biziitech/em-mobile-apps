package com.bz.em.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bz.em.R;
import com.bz.em.model.SomityMemberDetail;
import com.bz.em.ui.ImageCaptureUI;
import com.bz.em.ui.SomityMemberDetailsUI;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Bellal Hossain
 **/
public class RvRepaySomityMemberListAdapter extends RecyclerView.Adapter<RvRepaySomityMemberListAdapter.ViewHolder> {

    private List<SomityMemberDetail> somityMemberDetailList;
    private Context context;
    private int listItemPosition;


    public RvRepaySomityMemberListAdapter(Context context, List<SomityMemberDetail> somityMemberDetailList) {
        this.context = context;
        this.somityMemberDetailList = somityMemberDetailList;
    }

    @NonNull
    @Override
    public RvRepaySomityMemberListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_repay_somity_member_list, parent, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        return new RvRepaySomityMemberListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtMemberName.setText(somityMemberDetailList.get(position).getApplicantName());
        holder.txtMobileNo.setText(somityMemberDetailList.get(position).getMobileNo());
        holder.txtFatherOrHusband.setText(somityMemberDetailList.get(position).getFatherHusbandName());

    }

    @Override
    public int getItemCount() {
        return somityMemberDetailList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtMemberName;
        private TextView txtMobileNo;
        private TextView txtFatherOrHusband;

        private CardView cardTaskLst;

        ViewHolder(View itemView) {
            super(itemView);

            txtMemberName = itemView.findViewById(R.id.memberName);
            txtMobileNo = itemView.findViewById(R.id.tvMobileNo);
            txtFatherOrHusband = itemView.findViewById(R.id.fatherOrHusband);
        }

        @Override
        public void onClick(View v) {

        }
    }
}