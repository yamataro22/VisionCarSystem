package com.example.adamw.visoncarsystem;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainScreen extends AppCompatActivity implements filterChoserFragment.filterChoserListener{

    private visionParams parameters = new visionParams();

    @Override
    public void itemClicked(int id)
    {
        switch(id)
        {
            case 0:
                parameters.toggleGray();
                break;
            case 1:
                parameters.toggleBlur();
                break;
            case 2:
                parameters.toggleThresh();
                break;
            case 3:
                parameters.toggleCanny();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        if(savedInstanceState != null)
        {
            parameters.gray = savedInstanceState.getBoolean("m1");
            parameters.blur = savedInstanceState.getBoolean("m2");
            parameters.thresh = savedInstanceState.getBoolean("m3");
            parameters.canny = savedInstanceState.getBoolean("m4");
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickStartCapturing(View view)
    {
        Log.i("filtry", "tworzę teraz nową intencję");
        Intent intent = new Intent(this, CameraScreenActivity.class);
        //intent.putExtra(CameraScreenActivity.TYPE, "CANNY");
        //intent.putExtra(CameraScreenActivity.THRESHPARAM, 140);
        intent.putExtra(CameraScreenActivity.IFGRAY, parameters.gray);
        intent.putExtra(CameraScreenActivity.IFBLUR, parameters.blur);
        intent.putExtra(CameraScreenActivity.IFTHRESH, parameters.thresh);
        intent.putExtra(CameraScreenActivity.IFCANNY, parameters.canny);
        startActivity(intent);
    }
    public class visionParams
    {
        private boolean gray = false;
        private boolean blur = false;
        private boolean thresh = false;
        private boolean canny = false;

        public void toggleCanny()
        {
            canny = !canny;
            if(canny)
            {
                gray = true;
                thresh = true;
                canny = true;
            }
            else
                canny = false;
        }
        public void toggleBlur()
        {
            blur = !blur;
        }
        public void toggleGray()
        {
            gray = !gray;
        }
        public void toggleThresh()
        {
            thresh = !thresh;
            if(thresh)
                gray = true;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("m1", parameters.gray);
        savedInstanceState.putBoolean("m2", parameters.blur);
        savedInstanceState.putBoolean("m3", parameters.thresh);
        savedInstanceState.putBoolean("m4", parameters.canny);
    }
}
