package com.bz.em.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bz.em.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    /*TextView txtPercentage ;
            Button btnUpload ;
    LinearLayout layout ;
    ProgressBar progressBar ;
            ImageView imgPreview ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
      TextView txtPercentage = (TextView) findViewById(R.id.txtPercentage);
     Button btnUpload = (Button) findViewById(R.id.btnUpload);
     LinearLayout layout = (LinearLayout) findViewById(R.id.ly1);
      ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
     ImageView imgPreview = (ImageView) findViewById(R.id.imgPreview);

        addImageView(layout);

        // Changing action bar background color
        getActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor(getResources().getString(
                        R.color.action_bar))));

        // Receiving the data from previous activity
        Intent i = getIntent();

        // image or video path that is captured in previous activity
        filePath = i.getStringExtra("filePath");

        LinearLayout ly_myLayout = (LinearLayout)findViewById(R.id.myLinearLayout);
        ArrayList<String> yourFilePaths = new ArrayList<>();//Create ArrayList

        //first you have to check if you've saved filepaths
        SharedPreferences prefs = getSharedPreferences("SavedFilePaths",   Context.MODE_PRIVATE);
        String myJSONArrayString = prefs.getString("SavedFilePathsJSONArray", "");
        if(!myJSONArrayString.isEmpty()){
            try {
                JSONArray jsonArray = new JSONArray(myJSONArrayString);
                for (int i=0;i<jsonArray.length();i++){
                    yourFilePaths.add(jsonArray.get(i).toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //add the last photo you've taken
        yourFilePaths.add(filePath);

        //Save the new ArrayList in SharedPreferences:
        JSONArray mJSONArray = new JSONArray(yourFilePaths);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("SavedFilePathsJSONArray",mJSONArray.toString()).commit(); // add commit

        //Show images by filePaths
        File imgFile;
        for (int i = 0; i < yourFilePaths.size(); ++i) {
            imgFile = new  File(yourFilePaths.get(i));
            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                ImageView myImage = new ImageView(this);

                myImage.setImageBitmap(myBitmap);// In this step you've to create dynamic imageViews to see more than one picture

                ly_myLayout.addView(myImage);//Then add your dynamic imageviews to your layout

            }
        }

        btnUpload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // uploading the file to server
                new UploadFileToServer().execute();
            }
        });

    }

    *//**
     * Displaying captured image/video on the screen
     * *//*
    private void previewMedia(boolean isImage) {
        // Checking whether captured media is image or video
        if (isImage) {
            imgPreview.setVisibility(View.VISIBLE);
            vidPreview.setVisibility(View.GONE);
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // down sizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

            imgPreview.setImageBitmap(bitmap);

        }
    }

    private void addImageView(LinearLayout layout) {
        i = 0;
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.ic_launcher);
        imageView.setId(i);
        layout.addView(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(UploadActivity.this,
                        MainActivity.class);
                startActivity(intent);
            }
        });
    }*/

}