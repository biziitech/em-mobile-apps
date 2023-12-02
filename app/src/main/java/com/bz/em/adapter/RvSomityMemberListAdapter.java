package com.bz.em.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.bz.em.model.LoanInspectionData;
import com.bz.em.model.SamityMemberData;
import com.bz.em.model.Somity;
import com.bz.em.model.SomityMemberDetail;
import com.bz.em.service.ApiService;
import com.bz.em.ui.ImageCaptureUI;
import com.bz.em.ui.SamityInspectionMachineDataUI;
import com.bz.em.ui.SomityMemberDetailsUI;
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
public class RvSomityMemberListAdapter extends RecyclerView.Adapter<RvSomityMemberListAdapter.ViewHolder> {

    private List<SomityMemberDetail> somityMemberDetailList;
    private List<SamityMemberData> samityInspectionSamityMemberDataList;
    private Context context;
    private int listItemPosition;
    private String navType;


    public RvSomityMemberListAdapter(Context context, List<SomityMemberDetail> somityMemberDetailList, String navType) {
        this.context = context;
        this.somityMemberDetailList = somityMemberDetailList;
        this.navType = navType;
    }

    public RvSomityMemberListAdapter(Context context, List<SamityMemberData> samityInspectionSamityMemberDataList, String navType, String extra) {
        this.context = context;
        this.samityInspectionSamityMemberDataList = samityInspectionSamityMemberDataList;
        this.navType = navType;
    }

    @NonNull
    @Override
    public RvSomityMemberListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_somity_member_list, parent, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        return new RvSomityMemberListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        if (navType.equals("LOAN_INSPECTION")) {

            holder.txtMemberName.setText(somityMemberDetailList.get(position).getApplicantName());
            holder.txtMobileNo.setText(somityMemberDetailList.get(position).getMobileNo());
            holder.txtFatherOrHusband.setText(somityMemberDetailList.get(position).getFatherHusbandName());

            final long loanId = somityMemberDetailList.get(position).getId();
            final String nid = somityMemberDetailList.get(position).getNid();
            final String dob = somityMemberDetailList.get(position).getDob();
            final String applicantName = somityMemberDetailList.get(position).getApplicantName();
            final String applicantNameEn = somityMemberDetailList.get(position).getApplicantNameEn();
            final String fatherHousband = somityMemberDetailList.get(position).getFatherHusbandName();
            final String permanentAddr = somityMemberDetailList.get(position).getPermanentAddress();
            final String presentAddr = somityMemberDetailList.get(position).getPresentAddress();
            final String mobileNo = somityMemberDetailList.get(position).getMobileNo();
            final String age = somityMemberDetailList.get(position).getAge();
            final String village = somityMemberDetailList.get(position).getVillage();
            final String postOffice = somityMemberDetailList.get(position).getPoName();
            final String district = somityMemberDetailList.get(position).getDistrictName();
            final String upzillaName = somityMemberDetailList.get(position).getUpzillaName();
            final String basicCenterName = somityMemberDetailList.get(position).getBasicCenterName();

            final String somityName = somityMemberDetailList.get(position).getSamityName();
            final Long somityId = somityMemberDetailList.get(position).getSamityId();


            holder.btnInspectionEntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, SomityMemberDetailsUI.class);

                    intent.putExtra("loanId", loanId);
                    intent.putExtra("nid", nid);
                    intent.putExtra("dob", dob);
                    intent.putExtra("applicantName", applicantName);
                    intent.putExtra("applicantNameEn", applicantNameEn);
                    intent.putExtra("fatherHousband", fatherHousband);
                    intent.putExtra("permanentAddr", permanentAddr);
                    intent.putExtra("presentAddr", presentAddr);
                    intent.putExtra("mobileNo", mobileNo);
                    intent.putExtra("age", age);
                    intent.putExtra("district", district);
                    intent.putExtra("village", village);
                    intent.putExtra("postOffice", postOffice);
                    intent.putExtra("upzillaName", upzillaName);
                    intent.putExtra("basicCenterName", basicCenterName);

                    intent.putExtra("somityName", somityName);
                    intent.putExtra("somityId", somityId);

                    intent.putExtra("NAV_TYPE", navType);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

            holder.btnImageCapture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, ImageCaptureUI.class);

                    intent.putExtra("LOAN_ID", loanId);
                    intent.putExtra("NAV_TYPE", navType);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

            holder.btnFinalSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();

                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                    builder.setMessage("ঋণ পরিদর্শন চূড়ান্ত করবেন কি?")
                            .setCancelable(false)
                            .setPositiveButton("হ্যা", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    postFinalizeSubmit(somityMemberDetailList.get(position).getId(), holder);

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
                    holder.cardTaskLst.setBackgroundColor(Color.parseColor("#99bbff"));
                } else {
                    holder.cardTaskLst.setBackgroundColor(Color.parseColor("#b3ccff"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            holder.btnFinalSubmit.setVisibility(View.GONE);
            //samity inspection
            holder.txtMemberName.setText(samityInspectionSamityMemberDataList.get(position).getMemberName());
            holder.txtMobileNo.setText(samityInspectionSamityMemberDataList.get(position).getContactNo());
            holder.txtFatherOrHusband.setText(samityInspectionSamityMemberDataList.get(position).getFatherHusbandName());

            final Long memberId = samityInspectionSamityMemberDataList.get(position).getId();
            final Long samityId = samityInspectionSamityMemberDataList.get(position).getSamityId();


            final String dob = samityInspectionSamityMemberDataList.get(position).getDateOfBirth();
            final String nid = samityInspectionSamityMemberDataList.get(position).getNid();
            final String name = samityInspectionSamityMemberDataList.get(position).getMemberName();
            final String mobileNo = samityInspectionSamityMemberDataList.get(position).getContactNo();
            final String fatherHusband = samityInspectionSamityMemberDataList.get(position).getFatherHusbandName();

            holder.btnInspectionEntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, SamityInspectionMachineDataUI.class);
                    //  intent.putExtra("LOAN_ID", loanId);
                    intent.putExtra("NAV_TYPE", navType);
                    intent.putExtra("nid", nid);
                    intent.putExtra("member_id", memberId);
                    intent.putExtra("samity_id", samityId);
                    intent.putExtra("dob", dob);
                    intent.putExtra("applicantName", name);
                    intent.putExtra("mobileNo", mobileNo);
                    intent.putExtra("fatherHousband", fatherHusband);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

            holder.btnImageCapture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, ImageCaptureUI.class);

                    intent.putExtra("member_id", memberId);
                    //   intent.putExtra("samity_id", samityId);

                    intent.putExtra("NAV_TYPE", navType);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

            /*holder.btnFinalSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();

                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                    builder.setMessage("সমিতি পরিদর্শন চূড়ান্ত করবেন কি?")
                            .setCancelable(false)
                            .setPositiveButton("হ্যা", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    postSamityFinalizeSubmit(samityInspectionSamityMemberDataList.get(position).getSamityId(), holder);

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
            });*/
        }

    }

    @Override
    public int getItemCount() {

        if (navType.equals("LOAN_INSPECTION")) {
            return somityMemberDetailList.size();
        } else {
            return samityInspectionSamityMemberDataList.size();
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtMemberName;
        private TextView txtMobileNo;
        private TextView txtFatherOrHusband;
        private Button btnInspectionEntry;
        private Button btnImageCapture;
        private Button btnFinalSubmit;

        private CardView cardTaskLst;

        ViewHolder(View itemView) {
            super(itemView);

            txtMemberName = itemView.findViewById(R.id.memberName);
            txtMobileNo = itemView.findViewById(R.id.tvMobileNo);
            txtFatherOrHusband = itemView.findViewById(R.id.fatherOrHusband);
            btnInspectionEntry = itemView.findViewById(R.id.btnInspectionEntry);
            btnImageCapture = itemView.findViewById(R.id.btnCaptureImage);
            btnFinalSubmit = itemView.findViewById(R.id.btnFinalSubmit);
        }

        @Override
        public void onClick(View v) {

        }
    }

    private void postFinalizeSubmit(long loanApplicationId, final RvSomityMemberListAdapter.ViewHolder holder) {


        try {
            //  EmUtils.showProgress(context);
            ApiService service = ApiConnection.getRetrofit().create(ApiService.class);
            Call<Object> call = service.postFinalizeFlag(loanApplicationId);

            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    EmUtils.dissmisProgress();

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            Toast.makeText(context.getApplicationContext(), "ঋণ পরিদর্শন সফলভাবে চূড়ান্তকরণ করা হয়েছে।", Toast.LENGTH_LONG).show();
                            somityMemberDetailList.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());

                        } else {
                            Toast.makeText(context.getApplicationContext(), "সার্ভার এররের কারণে, ঋণ পরিদর্শন সফলভাবে চূড়ান্তকরণ করা যায়নি।", Toast.LENGTH_LONG).show();
                        }
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

  /*  private void postSamityFinalizeSubmit(long samityId, final RvSomityMemberListAdapter.ViewHolder holder) {


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
                            samityInspectionSamityMemberDataList.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());

                        } else {
                            Toast.makeText(context.getApplicationContext(), "সার্ভার এররের কারণে, সমিতি পরিদর্শন সফলভাবে চূড়ান্তকরণ করা যায়নি।", Toast.LENGTH_LONG).show();

                        }

                    }else {
                        Log.d("error",response.errorBody().toString());
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
    }*/
}