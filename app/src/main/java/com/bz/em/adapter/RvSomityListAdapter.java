package com.bz.em.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bz.em.R;
import com.bz.em.connection.ApiConnection;
import com.bz.em.model.AssignedTask;
import com.bz.em.model.Somity;
import com.bz.em.service.ApiService;
import com.bz.em.ui.RepMemberListUI;
import com.bz.em.ui.SomityListUI;
import com.bz.em.ui.SomityMemberListUI;
import com.bz.em.utils.EmConstants;
import com.bz.em.utils.EmNetworkStateCheck;
import com.bz.em.utils.EmUtils;

import java.util.List;

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
public class RvSomityListAdapter extends RecyclerView.Adapter<RvSomityListAdapter.ViewHolder> {

    private List<Somity> somityList;
    private Context context;
    private int listItemPosition;
    private String navType;


    public RvSomityListAdapter(Context context, List<Somity> somityList, String navType) {
        this.context = context;
        this.somityList = somityList;
        this.navType = navType;
    }


    @NonNull
    @Override
    public RvSomityListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_somity_list, parent, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new RvSomityListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.txtBasicCenterName.setText(somityList.get(position).getBasicCenterName());
        holder.txtSomityName.setText(somityList.get(position).getSamityName());
        holder.txtAddress.setText(somityList.get(position).getSamityId() + "");
        holder.memQuantity.setText(somityList.get(position).getCount() + "");


        holder.linearLayoutSamityList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent;
                if (navType.equals("REPAYMENTS")) {

                    intent = new Intent(context, RepMemberListUI.class);
                    intent.putExtra("somity_id", somityList.get(position).getSamityId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else if (navType.equals("LOAN_INSPECTION")) {

                    intent = new Intent(context, SomityMemberListUI.class);
                    intent.putExtra("NAV_TYPE", navType);
                    intent.putExtra("somity_id", somityList.get(position).getSamityId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    intent = new Intent(context, SomityMemberListUI.class);
                    intent.putExtra("NAV_TYPE", navType);
                    intent.putExtra("somity_id", somityList.get(position).getSamityId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }


            }
        });


        if (navType.equals("SOMITY_INSPECTION")) {

            holder.btnFinalSubmit.setVisibility(View.VISIBLE);

            holder.btnFinalSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();

                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                    builder.setMessage("সমিতি পরিদর্শন চূড়ান্ত করবেন কি?")
                            .setCancelable(false)
                            .setPositiveButton("হ্যা", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    postSamityFinalizeSubmit(somityList.get(position).getSamityId(), holder);

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
        }


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
        return somityList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtBasicCenterName;
        private TextView txtSomityName;
        private TextView txtAddress;
        private TextView memQuantity;
        private Button btnFinalSubmit;
        private LinearLayout linearLayoutSamityList;

        private CardView cardTaskLst;

        ViewHolder(View itemView) {
            super(itemView);

            txtBasicCenterName = itemView.findViewById(R.id.basicCenterName);
            txtSomityName = itemView.findViewById(R.id.somityName);
            txtAddress = itemView.findViewById(R.id.somityAddress);
            memQuantity = itemView.findViewById(R.id.tvMemQuantity);
            btnFinalSubmit = itemView.findViewById(R.id.btnFinalSubmit);
            linearLayoutSamityList = itemView.findViewById(R.id.idLinearSamityList);
        }

        @Override
        public void onClick(View v) {

        }
    }

    private void postSamityFinalizeSubmit(long samityId, final RvSomityListAdapter.ViewHolder holder) {


        try {
            //  EmUtils.showProgress(context);
            ApiService service = ApiConnection.getRetrofit().create(ApiService.class);
            Call<Object> call = service.postSamityFinalizeFlag(samityId);

            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    EmUtils.dissmisProgress();

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            Toast.makeText(context.getApplicationContext(), "সমিতি পরিদর্শন সফলভাবে চূড়ান্তকরণ করা হয়েছে।", Toast.LENGTH_LONG).show();
                            somityList.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());

                        } else {
                            Toast.makeText(context.getApplicationContext(), "সার্ভার এররের কারণে, সমিতি পরিদর্শন সফলভাবে চূড়ান্তকরণ করা যায়নি।", Toast.LENGTH_LONG).show();

                        }

                    } else {
                        Log.d("error", response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    //  EmUtils.dissmisProgress();
                    Log.d("erro_log", t.fillInStackTrace().toString());
                    Toast.makeText(context.getApplicationContext(), EmConstants.SERVER_ERROR_MASSAGE, Toast.LENGTH_LONG).show();

                }
            });

        } catch (Exception e) {
            //  EmUtils.dissmisProgress();
            e.printStackTrace();
        }
    }
}