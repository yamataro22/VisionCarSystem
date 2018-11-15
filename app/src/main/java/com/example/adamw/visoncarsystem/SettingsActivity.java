package com.example.adamw.visoncarsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void onClickCalibrate(View v)
    {
        Intent intent = new Intent(this, CameraCalibrationActivity.class);
        int requestCode = 1; // Or some number you choose
        startActivityForResult(intent, requestCode);
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        // Collect data from the intent and use it
        String value = data.getStringExtra("someValue");
        Log.i("otrzymano",value);
    }
}
