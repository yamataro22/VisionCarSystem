package com.example.adamw.visoncarsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class SettingsActivity extends Activity {

    private int threshValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void onClickCalibrate(View v)
    {
        EditText editText = findViewById(R.id.threshEditText);
        threshValue = Integer.parseInt(editText.getText().toString());
        Intent intent = new Intent(this, ThreshCalibrationActivity.class);
        intent.putExtra(ThreshCalibrationActivity.THRESH_1, threshValue);
        int requestCode = 1; // Or dowolny
        startActivityForResult(intent, requestCode);
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        // Collect data from the intent and use it
        threshValue = data.getIntExtra(ThreshCalibrationActivity.THRESH_2, 100);
        EditText editText = findViewById(R.id.threshEditText);
        editText.setText(threshValue+"");
    }
}
