package com.example.adamw.visoncarsystem;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import org.opencv.core.Mat;

public class CameraCalibrationActivity extends  CameraBasicActivity implements View.OnTouchListener {

    onStateChangedListener listener;

    boolean isLandscaped = false;


    public interface onStateChangedListener
    {
        void stateChanged();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_calibration);
        mOpenCvCameraView = findViewById(R.id.calibrationOpencvView);
        mOpenCvCameraView.enableView();
        mOpenCvCameraView.enableFpsMeter();
        mOpenCvCameraView.setMaxFrameSize(2400, 2000);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
        mOpenCvCameraView.setOnTouchListener(this);
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(Mat inputFrame) {
        return inputFrame;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(mOpenCvCameraView.getVisibility() == SurfaceView.VISIBLE)
            mOpenCvCameraView.setVisibility(SurfaceView.INVISIBLE);
        else
            mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        return false;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            intent.putExtra("someValue", "data");
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
