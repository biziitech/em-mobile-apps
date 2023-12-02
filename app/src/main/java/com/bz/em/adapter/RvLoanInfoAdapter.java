package com.bz.em.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bz.em.R;
import com.bz.em.model.LoanInfo;
import com.bz.em.model.LoanInfoDtl;
import com.bz.em.model.Somity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Bellal Hossain
 **/
public class RvLoanInfoAdapter extends RecyclerView.Adapter<RvLoanInfoAdapter.ViewHolder> {

    private List<LoanInfoDtl> loanInfoList;
    private Context context;


    public RvLoanInfoAdapter(Context context, List<LoanInfoDtl> loanInfoList) {
        this.context = context;
        this.loanInfoList = loanInfoList;
    }


    @NonNull
    @Override
    public RvLoanInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_loan_info_list, parent, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        return new RvLoanInfoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtLoanId.setText(String.valueOf(loanInfoList.get(position).getLoanId()));
        holder.txTweaberId.setText(String.valueOf(loanInfoList.get(position).getWeaverId()));
        holder.txMachineType.setText(String.valueOf(loanInfoList.get(position).getMachineTypeForLoan()));
        holder.txNachineName.setText(String.valueOf(loanInfoList.get(position).getMachineTypeName()));
        holder.txLoanAmt.setText(String.valueOf(loanInfoList.get(position).getLoanAmount()));
        holder.txInstallNo.setText(String.valueOf(loanInfoList.get(position).getNoOfInstallment()));
        holder.txProjectName.setText(String.valueOf(loanInfoList.get(position).getProjectName()));

        holder.tvApplicationStatus.setText(loanInfoList.get(position).getApplicationStatus());
        holder.txChargeRate.setText(String.valueOf(loanInfoList.get(position).getChargeRate()));

        /*try {

            if (position % 2 == 1) {
                holder.cardTaskLst.setBackgroundColor(Color.parseColor("#99bbff"));
            } else {
                holder.cardTaskLst.setBackgroundColor(Color.parseColor("#b3ccff"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public int getItemCount() {
        return loanInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtLoanId, txTweaberId,
                txMachineType, txNachineName,
                txLoanAmt, txInstallNo,
                txProjectName, txChargeRate,tvApplicationStatus;

        private CardView cardTaskLst;

        ViewHolder(View itemView) {
            super(itemView);

            txtLoanId = itemView.findViewById(R.id.tvLoan_id);
            txTweaberId = itemView.findViewById(R.id.tv_waberId);
            txMachineType = itemView.findViewById(R.id.tvMachineType);
            txNachineName = itemView.findViewById(R.id.tvMachineName);
            txLoanAmt = itemView.findViewById(R.id.tvLoanAmt);
            txInstallNo = itemView.findViewById(R.id.tvInstallNo);
            txProjectName = itemView.findViewById(R.id.tvProjectName);
            tvApplicationStatus = itemView.findViewById(R.id.tvApplicationStatus);
            txChargeRate = itemView.findViewById(R.id.tvChargeRate);

        }

        @Override
        public void onClick(View v) {

        }
    }
}