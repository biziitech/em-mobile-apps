package com.bz.em.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bz.em.R;
import com.bz.em.model.InspectionMachineResult;
import com.bz.em.model.SamityMemberMachineDetailData;
import com.bz.em.model.TatInfo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Bellal Hossain
 **/
public class RvSamityMachineAdapter extends RecyclerView.Adapter<RvSamityMachineAdapter.ViewHolder> {

    public static List<SamityMemberMachineDetailData> samityMemberMachineDetailDataList;
    private Context context;


    public RvSamityMachineAdapter(Context context, List<SamityMemberMachineDetailData> samityMemberMachineDetailDataList) {
        this.context = context;
        this.samityMemberMachineDetailDataList = samityMemberMachineDetailDataList;
    }


    @NonNull
    @Override
    public RvSamityMachineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tat_machine_list, parent, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        return new RvSamityMachineAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvQ.setText(samityMemberMachineDetailDataList.get(position).getNoOfMachine() + "");

        holder.tvNameOfTat.setText(samityMemberMachineDetailDataList.get(position).getMachineTypeName());

        try {

            if (position % 2 == 1) {
                //holder.cardTaskLst.setBackgroundColor(Color.parseColor("#99bbff"));
            } else {
                //holder.cardTaskLst.setBackgroundColor(Color.parseColor("#b3ccff"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return samityMemberMachineDetailDataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private EditText editText;
        private TextView tvQ;
        private TextView tvNameOfTat;

        private CardView cardTaskLst;

        ViewHolder(View itemView) {
            super(itemView);

            editText = (EditText) itemView.findViewById(R.id.etActualQuantity);
            tvQ = (TextView) itemView.findViewById(R.id.tvQuantity);
            tvNameOfTat = (TextView) itemView.findViewById(R.id.tvNameOfTat);

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    samityMemberMachineDetailDataList.get(getAdapterPosition()).setNoOfMachine(Integer.valueOf(editText.getText().toString()));

                    samityMemberMachineDetailDataList.get(getAdapterPosition()).setMemberId(samityMemberMachineDetailDataList.get(getAdapterPosition()).getMemberId());

                    samityMemberMachineDetailDataList.get(getAdapterPosition()).setMachineTypeName(samityMemberMachineDetailDataList.get(getAdapterPosition()).getMachineTypeName());

                    samityMemberMachineDetailDataList.get(getAdapterPosition()).setMachineTypeId(samityMemberMachineDetailDataList.get(getAdapterPosition()).getMachineTypeId());

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }
}