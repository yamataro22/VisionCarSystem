package com.example.adamw.visoncarsystem;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.ImageView;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;



public class CameraScreenActivity extends Activity implements CameraBridgeViewBase.CvCameraViewListener {

    public static final String TYPE = "THRESH";
    public static final String filterType = "CANNY";
    public static final String THRESHPARAM = "140";
    private int threshVal = 140;



    public static final String IFBLUR = "messageBlur";
    public static final String IFTHRESH = "messageThresh";
    public static final String IFGRAY = "messageGray";
    public static final String IFCANNY = "messageCanny";

    private boolean[] filters = new boolean[4];


    static {
        OpenCVLoader.initDebug();
    }
    public static final int  MY_PERMISSIONS_REQUEST_CAMERA =1;
    private CameraBridgeViewBase mOpenCvCameraView;
    private  Mat mRgba;

    private static final String  TAG = "CameraScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "CameraScreenActivity created");
        Log.i("filtry", "uruchmamiam onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_camera_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Intent intent = getIntent();
            filters[0] = intent.getBooleanExtra(IFGRAY, false);
            filters[1] = intent.getBooleanExtra(IFBLUR, false);
            filters[2] = intent.getBooleanExtra(IFTHRESH, false);
            filters[3] = intent.getBooleanExtra(IFCANNY, false);
            Log.i("filtry", "----------------");
            Log.i("filtry", filters[0] + "");
            Log.i("filtry", filters[1] + "");
            Log.i("filtry", filters[2] + "");
            Log.i("filtry", filters[3] + "");
            Log.i("filtry", "----------------");
            Log.i("filtry", "skończyłem odczytywać filtry");
        }
        //filterType = intent.getStringExtra(TYPE);
        //threshVal = intent.getIntExtra(THRESHPARAM, 140);

        // Here, thisActivity is the current activity
        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permissions not granted");
            Log.i("filtry", "nie przyznano praw");
            // Permission is not granted
            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                Log.i(TAG, "Sending permission request");
                Log.i("filtry", "wysyłam do wyboru praw");
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }


        mOpenCvCameraView = findViewById(R.id.HelloOpenCvView);
        mOpenCvCameraView.enableView();
        mOpenCvCameraView.enableFpsMeter();
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
        Log.i("filtry", "wychodzę z onCreate");


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.i("filtry", "wchodzę do requestpermissionresult");
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    //mCamera = getCameraInstance();
                    //mPreview = new CameraPreview(this, mCamera);
                    //FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
                    //preview.addView(mPreview);
                    Log.i(TAG, "włączam vidok opencv");


                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    public void putMatInImageView(Mat m, ImageView view)
    {
        Imgproc.putText(m, "hi there ;)", new Point(30,80), Core.FONT_HERSHEY_SCRIPT_SIMPLEX, 2.2, new Scalar(200,200,0),2);
        Bitmap bm = Bitmap.createBitmap(m.cols(), m.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(m, bm);
        view.setImageBitmap(bm);
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(Mat inputFrame) {
        Mat dst = new Mat();
        Filtr f = new Filtr();

        if(filters[0]) f.filtr(Filtr.filters.GRAY,inputFrame);
        if(filters[1]) f.filtr(Filtr.filters.MBLUR,inputFrame);
        if(filters[2]) f.filtr(Filtr.filters.THRESHBINARY,inputFrame);
        if(filters[3])
        {
            f.canny(inputFrame, dst);
            return dst;
        }
        return inputFrame;
    }

    @Override
    public void onPause()
    {
        Log.i("filtry", "jestem w OnPause");
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }
    public void onDestroy() {
        super.onDestroy();
        Log.i("filtry", "jestem w onDestroy");
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        return inputFrame.rgba();
    }

}
