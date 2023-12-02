package com.bz.em.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bz.em.R;
import com.bz.em.model.InspectionMachineResult;
import com.bz.em.model.TatInfo;

import java.util.List;

/**
 * Created by Bellal Hossain
 **/
public class RvMachineAdapter extends RecyclerView.Adapter<RvMachineAdapter.ViewHolder> {

    public static List<TatInfo> tatInfoArayList;
    private Context context;


    public RvMachineAdapter(Context context, List<TatInfo> tatInfoArayList) {
        this.context = context;
        this.tatInfoArayList = tatInfoArayList;
    }


    @NonNull
    @Override
    public RvMachineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tat_machine_list, parent, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        return new RvMachineAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvQ.setText(tatInfoArayList.get(position).getNoOfMachine() + "");

        holder.tvNameOfTat.setText(tatInfoArayList.get(position).getMachineTypeName());

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
        return tatInfoArayList.size();
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

                    tatInfoArayList.get(getAdapterPosition()).setNoOfMachine(Integer.valueOf(editText.getText().toString()));

                    tatInfoArayList.get(getAdapterPosition()).setLoanAccountId(tatInfoArayList.get(getAdapterPosition()).getLoanAccountId());

                    tatInfoArayList.get(getAdapterPosition()).setMachineTypeName(tatInfoArayList.get(getAdapterPosition()).getMachineTypeName());

                    tatInfoArayList.get(getAdapterPosition()).setWeaverMachineTypeId(tatInfoArayList.get(getAdapterPosition()).getWeaverMachineTypeId());

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }
}