package com.bz.em.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bz.em.R;
import com.bz.em.model.InstallmentInfo;
import com.bz.em.model.InstalmentDtl;
import com.bz.em.model.Somity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Bellal Hossain
 **/
public class RvLoanInstallmentListAdapter extends RecyclerView.Adapter<RvLoanInstallmentListAdapter.ViewHolder> {

    private List<InstalmentDtl> installmentInfos;
    private Context context;

    public RvLoanInstallmentListAdapter(Context context, List<InstalmentDtl> installmentInfos) {
        this.context = context;
        this.installmentInfos = installmentInfos;
    }


    @NonNull
    @Override
    public RvLoanInstallmentListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_installment_list, parent, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        return new RvLoanInstallmentListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtInstallmentAmt.setText(installmentInfos.get(position).getInstallmentAmount());
        holder.txtPaymentDate.setText(installmentInfos.get(position).getPaymentDate());
        holder.textStatus.setText(installmentInfos.get(position).getStatus());
        holder.textdueDate.setText(installmentInfos.get(position).getDueDate());

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
        return installmentInfos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtInstallmentAmt;
        private TextView txtPaymentDate;
        private TextView textStatus;
        private TextView textdueDate;

        private CardView cardTaskLst;

        ViewHolder(View itemView) {
            super(itemView);

            txtInstallmentAmt = itemView.findViewById(R.id.installment_amount);
            txtPaymentDate = itemView.findViewById(R.id.payment_date);
            textStatus = itemView.findViewById(R.id.txt_payment_status);
            textdueDate = itemView.findViewById(R.id.id_dueDate);
        }

        @Override
        public void onClick(View v) {

        }
    }
}