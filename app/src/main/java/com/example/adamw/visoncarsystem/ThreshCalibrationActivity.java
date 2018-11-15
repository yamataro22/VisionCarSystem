package com.example.adamw.visoncarsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import org.opencv.core.Mat;
import org.w3c.dom.Text;

public class ThreshCalibrationActivity extends CameraCalibrationActivity {

    int threshValue;
    public static final String THRESH_1 = "m1";
    public static final String THRESH_2 = "m2";
    Filtr f = new Filtr();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        threshValue = intent.getIntExtra(THRESH_1, 120);
        final SeekBar sb = findViewById(R.id.seekBar);
        sb.setProgress(threshValue);

        TextView textView = findViewById(R.id.seekBarValue);
        textView.setText(threshValue+"");

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                threshValue = sb.getProgress();
                TextView textView = findViewById(R.id.seekBarValue);
                textView.setText(threshValue+"");
                Log.i("wiadomosc", "xD");
            }
        });
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(Mat inputFrame) {

        f.setThreshBinaryParam(threshValue);
        f.filtr(Filtr.filters.GRAY, inputFrame);
        f.filtr(Filtr.filters.THRESHBINARY,inputFrame);
        return inputFrame;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            SeekBar sb = findViewById(R.id.seekBar);
            int newVal = sb.getProgress();
            Intent intent = new Intent();
            intent.putExtra(THRESH_2, newVal);
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return false;
    }
}
