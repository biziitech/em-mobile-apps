package com.bz.em.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bz.em.R;
import com.bz.em.model.TatInfo;

import java.util.List;

/**
 * Created by Bellal Hossain
 **/

public class RvSubmittedImgDisplayAdapter extends RecyclerView.Adapter<RvSubmittedImgDisplayAdapter.ViewHolder> {

    private List<String> submittedImgList;
    private Context context;


    public RvSubmittedImgDisplayAdapter(Context context, List<String> submittedImgList) {
        this.context = context;
        this.submittedImgList = submittedImgList;
    }


    @NonNull
    @Override
    public RvSubmittedImgDisplayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_submitted_image, parent, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        return new RvSubmittedImgDisplayAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        //holder.tvQ.setText(tatInfoArayList.get(position).getNoOfMachine() + "");


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
        return submittedImgList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private EditText editText;
        private TextView tvQ;
        private TextView tvNameOfTat;
        private ImageView ivDispImg;

        private CardView cardTaskLst;

        ViewHolder(View itemView) {
            super(itemView);
          //  ivDispImg = itemView.findViewById(R.id.id_img_display);

        }

        @Override
        public void onClick(View v) {

        }
    }
}