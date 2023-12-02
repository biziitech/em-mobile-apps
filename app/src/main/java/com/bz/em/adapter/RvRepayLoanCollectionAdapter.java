package com.bz.em.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bz.em.R;
import com.bz.em.connection.ApiConnection;
import com.bz.em.model.DueCollection;
import com.bz.em.service.ApiService;
import com.bz.em.ui.RepayLoanCollectionUI;
import com.bz.em.utils.EmConstants;
import com.bz.em.utils.EmUtils;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bellal Hossain
 **/
public class RvRepayLoanCollectionAdapter extends RecyclerView.Adapter<RvRepayLoanCollectionAdapter.ViewHolder> {

    private ArrayList<DueCollection> collectionList;
    private Context context;
    private int listItemPosition;


    public RvRepayLoanCollectionAdapter(Context context, ArrayList<DueCollection> collectionList) {
        this.context = context;
        this.collectionList = collectionList;
    }


    @NonNull
    @Override
    public RvRepayLoanCollectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_loan_collection_list, parent, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new RvRepayLoanCollectionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.txtInstallmentNo.setText(collectionList.get(position).getInstallmentNo() + "");
        holder.txtDueDate.setText(collectionList.get(position).getDueDate());
        holder.txtInstallmentAmount.setText(collectionList.get(position).getInstallmentAmount() + "");

        holder.btnPostCollectionData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppCompatActivity activity = (AppCompatActivity) v.getContext();

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                builder.setMessage("ঋণের কিস্তি চুড়ান্তভাবে সংগ্রহ করবেন কি?")
                        .setCancelable(false)
                        .setPositiveButton("হ্যা", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                postSomityMember(RepayLoanCollectionUI.loanAccId, collectionList.get(position).getInstallmentAmount(), holder);

                                //collectionList.remove(holder.getAdapterPosition());
                                //notifyItemRemoved(holder.getAdapterPosition());

                            }
                        })
                        .setNegativeButton("না", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        try {

            if (position % 2 == 1) {
                holder.cardLoanCollectionLst.setBackgroundColor(Color.parseColor("#f6f6ee"));
            } else {
                holder.cardLoanCollectionLst.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return collectionList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtLoanId;
        private TextView txtName;
        private TextView txtInstallmentNo;
        private TextView txtDueDate;
        private TextView txtInstallmentAmount;
        private Button btnPostCollectionData;


        private CardView cardLoanCollectionLst;

        ViewHolder(View itemView) {

            super(itemView);

            txtInstallmentNo = itemView.findViewById(R.id.idInstallmentNo);
            txtDueDate = itemView.findViewById(R.id.id_dueDate);
            txtInstallmentAmount = itemView.findViewById(R.id.idInstallmentAmount);
            btnPostCollectionData = itemView.findViewById(R.id.btnPostCollection);
            cardLoanCollectionLst = itemView.findViewById(R.id.cardViewLoanCollectionList);

        }

        @Override
        public void onClick(View v) {

        }
    }

    private void postSomityMember(long loanAccountId, double collectionAmount, final ViewHolder holder) {


        try {
          //  EmUtils.showProgress(context);
            ApiService service = ApiConnection.getRetrofit().create(ApiService.class);
            Call<Object> call = service.postLoanRepayment(loanAccountId, collectionAmount);

            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {

               //     EmUtils.dissmisProgress();

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            Toast.makeText(context.getApplicationContext(), "ঋণের কিস্তি সফলভাবে সংগ্রহ করা হয়েছে।", Toast.LENGTH_LONG).show();

                            collectionList.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());

                        } else {
                            Toast.makeText(context.getApplicationContext(), "সার্ভার এররের কারণে, ঋণের কিস্তি সফলভাবে সংগ্রহ করা যায়নি।", Toast.LENGTH_LONG).show();

                        }

                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                 //   EmUtils.dissmisProgress();
                    Log.d("erro_log", t.fillInStackTrace().toString());
                    Toast.makeText(context.getApplicationContext(), EmConstants.SERVER_ERROR_MASSAGE, Toast.LENGTH_LONG).show();

                }
            });

        } catch (Exception e) {
         //   EmUtils.dissmisProgress();
            e.printStackTrace();
        }
    }
}