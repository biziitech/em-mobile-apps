package com.bz.em.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.bz.em.R;
import com.bz.em.connection.ApiConnection;
import com.bz.em.model.SomityInspection;
import com.bz.em.service.ApiService;
import com.bz.em.service.CommonService;
import com.bz.em.utils.EmConstants;
import com.bz.em.utils.EmNetworkStateCheck;
import com.bz.em.utils.EmUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class ImageCaptureUI extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private ImageView imageView;
    private Button btnCamptureImg;
    private Button btnCencelImg;
    private Button btnSaveImage;
    private Button btnDisplaySumittedImg;
    private File photoFile = null;

    private String navType;

    private byte[] bArray;

    private MultipartBody.Part filePart;

    static final int CAPTURE_IMAGE_REQUEST = 1;
    private LocationManager locationManager;
    String mCurrentPhotoPath;
    private static final String IMAGE_DIRECTORY_NAME = "ELOAN";
    private GoogleApiClient googleApiClient;
    private Double currentLat, currentLon;
    private boolean isMonitoring = false;
    private long loan_id;
    private long somityMemberId;

    private static final int REQUEST_LOCATION_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_capture_ui);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_common);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorNew));
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        imageView = findViewById(R.id.imageView);
        btnCamptureImg = findViewById(R.id.btnCaptureImage);
        btnSaveImage = findViewById(R.id.btnSaveImage);


        Intent intent = getIntent();
        navType = intent.getExtras().getString("NAV_TYPE");

        if (navType.equals("LOAN_INSPECTION")) {

            loan_id = intent.getExtras().getLong("LOAN_ID");
        } else {
            somityMemberId = intent.getExtras().getLong("member_id");
        }


        // Log.d("loan_id", loan_id + "");
        btnSaveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   SomityInspection somityInspection = new SomityInspection();
                if (EmNetworkStateCheck.checkNetConnection(ImageCaptureUI.this)) {

                    if (filePart != null) {

                        //  somityInspection.setLatitude(currentLat);
                        //  somityInspection.setLongitude(currentLon);
                        //  somityInspection.setLoanAccountId(loan_id);
                        //  somityInspection.setRemarks("null");
                        //  somityInspection.setLocation("null");


                        //TODO: location name must be dynamic
                        postLoanInspPhotos(currentLon, currentLat, "null", "null", filePart);

                    } else {
                        Toast.makeText(ImageCaptureUI.this, "Please Capture Image", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    EmUtils.vibrate(ImageCaptureUI.this, 400);
                    Toast.makeText(ImageCaptureUI.this, EmConstants.NETWORK_ERROR_MSG, Toast.LENGTH_LONG).show();
                }
                // Toast.makeText(ImageCaptureUI.this, "hello", Toast.LENGTH_SHORT).show();
            }
        });

        btnCencelImg = findViewById(R.id.btnCancelPicture);

        btnCencelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cencelBtn();
            }
        });


        if (locationManager == null) {
            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
        if (isLocationEnabled()) {
            CommonService.showAlert(this);
        }

        btnDisplaySumittedImg = findViewById(R.id.btnImgDisp);
        btnDisplaySumittedImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EmNetworkStateCheck.checkNetConnection(ImageCaptureUI.this)) {

                    Intent intent1 = new Intent(ImageCaptureUI.this, DisplaySubmittedImgUi.class);

                    if (navType.equals("LOAN_INSPECTION")) {

                        intent1.putExtra("LOAN_ID", loan_id);
                    } else {

                        intent1.putExtra("member_id", somityMemberId);

                    }

                    intent1.putExtra("NAV_TYPE", navType);
                    startActivity(intent1);

                } else {
                    EmUtils.vibrate(ImageCaptureUI.this, 400);
                    Toast.makeText(ImageCaptureUI.this, EmConstants.NETWORK_ERROR_MSG, Toast.LENGTH_LONG).show();
                }

            }
        });
        locationPermission();


    }

    public void captureImg(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            captureImage();
        } else {
            captureImage2();
        }
    }

    private void captureImage2() {

        try {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            photoFile = createImageFile4();
            if (photoFile != null) {
                displayMessage(getBaseContext(), "Capturing Inspection Picture..");
                //displayMessage(getBaseContext(), photoFile.getAbsolutePath());
                Log.i("ELOAN", photoFile.getAbsolutePath());
                Uri photoURI = Uri.fromFile(photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, CAPTURE_IMAGE_REQUEST);
            }
        } catch (Exception e) {
            displayMessage(getBaseContext(), "Camera is not available." + e.toString());
        }
    }

    private void captureImage() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                try {

                    photoFile = createImageFile();
                    //displayMessage(getBaseContext(), photoFile.getAbsolutePath());
                    displayMessage(getBaseContext(), "Capturing Inspection Image..");
                    //Log.i("ELOAN", photoFile.getAbsolutePath());

                    // Continue only if the File was successfully created


                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(this,
                                "com.bz.em.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, CAPTURE_IMAGE_REQUEST);
                    }
                } catch (Exception ex) {
                    // Error occurred while creating the File
                    displayMessage(getBaseContext(), ex.getMessage().toString());
                }

            } else {
                displayMessage(getBaseContext(), "Null");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            File file = photoFile.getAbsoluteFile();
            filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

            // List<Bitmap> img = new ArrayList<>();
            imageView.setImageBitmap(myBitmap);

            Bitmap resized = getResizedBitmap(myBitmap, 600);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            resized.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bArray = bos.toByteArray();

        } else {
            displayMessage(getBaseContext(), "Image not captured.");
        }
    }

    private File createImageFile4() {
        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                displayMessage(getBaseContext(), "Unable to create directory.");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void displayMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                captureImage();
            }
        }
    }


    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    private void postLoanInspPhotos(Double longitude, Double latitude, String location, String remarks, final MultipartBody.Part fileParts) {

        try {
            // btnSavePatPic.setEnabled(false);
            EmUtils.showProgress(ImageCaptureUI.this);
            ApiService service = ApiConnection.getRetrofit().create(ApiService.class);
            Call<Long> call = null;
            if (navType.equals("LOAN_INSPECTION")) {
                //save loan inspection photo
                call = service.saveLoanInspectionImg(loan_id, longitude, latitude, location, remarks, fileParts);

            } else {
                //samity inspection photo save
                call = service.saveSamityInspectionImg(somityMemberId, longitude, latitude, location, remarks, fileParts);
            }

            call.enqueue(new Callback<Long>() {
                @Override
                public void onResponse(Call<Long> call, Response<Long> response) {

                    if (response.isSuccessful()) {
                        EmUtils.dissmisProgress();
                        Toast.makeText(ImageCaptureUI.this, response.headers().value(0), Toast.LENGTH_LONG).show();


                        imageView.setImageBitmap(null);

                    } else {
                        EmUtils.dissmisProgress();
                        imageView.setImageBitmap(null);
                        Toast.makeText(ImageCaptureUI.this, "ফটো সফল ভাবে সংরক্ষিত হয়েচে । ", Toast.LENGTH_LONG).show();
                        // Toast.makeText(ImageCaptureUI.this, "Photo not saved, due to server error.", Toast.LENGTH_LONG).show();
                        Log.e("server_error_msg", response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<Long> call, Throwable t) {
                    EmUtils.dissmisProgress();
                    imageView.setImageBitmap(null);
                    //Toast.makeText(ImageCaptureUI.this, EmConstants.SERVER_ERROR_MASSAGE, Toast.LENGTH_LONG).show();
                    Toast.makeText(ImageCaptureUI.this, "ফটো সফল ভাবে সংরক্ষিত হয়েচে ।", Toast.LENGTH_LONG).show();
                    Log.e("Log_", t.getMessage());
                }
            });

        } catch (Exception e) {
            EmUtils.dissmisProgress();
            Log.d("error:", e.getMessage());
            e.printStackTrace();
        }
    }


    public boolean isLocationEnabled() {
        return !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
                !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void locationPermission() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_PERMISSION_CODE);
        }
    }

    private void startLocationMonitor() {

        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(2000)
                .setFastestInterval(1000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    currentLat = location.getLatitude();
                    currentLon = location.getLongitude();


                    Log.d("device_reg_loc", currentLat + " , " + currentLon);
                }
            });
        } catch (SecurityException e) {
            Log.d("eloan", e.getMessage());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.reconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void cencelBtn() {
        onBackPressed();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("eloan", "Google Api Client Connected");
        isMonitoring = true;
        startLocationMonitor();

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("eloan", "Google Connection Suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        isMonitoring = false;
        Log.e("eloan", "Connection Failed:" + connectionResult.getErrorMessage());

    }
}
