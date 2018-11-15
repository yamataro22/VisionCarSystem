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



public class CameraScreenActivity extends CameraBasicActivity {

    public static final String TYPE = "THRESH";
    public static final String THRESHPARAM = "140";

    public static final String IFBLUR = "messageBlur";
    public static final String IFTHRESH = "messageThresh";
    public static final String IFGRAY = "messageGray";
    public static final String IFCANNY = "messageCanny";

    private boolean[] filters = new boolean[4];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Intent intent = getIntent();
            filters[0] = intent.getBooleanExtra(IFGRAY, false);
            filters[1] = intent.getBooleanExtra(IFBLUR, false);
            filters[2] = intent.getBooleanExtra(IFTHRESH, false);
            filters[3] = intent.getBooleanExtra(IFCANNY, false);
        }

        mOpenCvCameraView = findViewById(R.id.HelloOpenCvView);
        mOpenCvCameraView.enableView();
        mOpenCvCameraView.enableFpsMeter();
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
        Log.i("filtry", "wychodzę z onCreate");


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


}
