package com.example.adamw.visoncarsystem;


import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;


public class filterChoserFragment extends Fragment implements View.OnClickListener{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View infView = inflater.inflate(R.layout.fragment_filter_choser, container, false);

        int states[][] = new int[][]{
                new int[]{-android.R.attr.state_enabled},
                new int[]{android.R.attr.state_enabled, -android.R.attr.state_checked},
                new int[]{android.R.attr.state_enabled, android.R.attr.state_checked}
        };

        int colors[] = {Color.GRAY, Color.RED, Color.GREEN};

        CheckBox checkBox = infView.findViewById(R.id.grayCheckBox);
        checkBox.setOnClickListener(this);
        checkBox.setButtonTintList(new ColorStateList(states, colors));
        checkBox = infView.findViewById(R.id.blurCheckBox);
        checkBox.setOnClickListener(this);
        checkBox.setButtonTintList(new ColorStateList(states, colors));
        checkBox = infView.findViewById(R.id.threshCheckBox);
        checkBox.setOnClickListener(this);
        checkBox.setButtonTintList(new ColorStateList(states, colors));
        checkBox = infView.findViewById(R.id.cannyCheckBox);
        checkBox.setOnClickListener(this);
        checkBox.setButtonTintList(new ColorStateList(states, colors));

        return infView;
    }


    @Override
    public void onStart()
    {
        updateCheckBoxes();
        super.onStart();
    }
    private filterChoserListener listener;

    interface filterChoserListener
    {
        void itemClicked(int id);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.listener = (filterChoserListener)context;
    }

    @Override
    public void onClick(View v)
    {
            int id = v.getId();
            updateCheckBoxes();
            switch(id)
            {
                case R.id.grayCheckBox:
                    listener.itemClicked(0);
                    break;
                case R.id.blurCheckBox:
                    listener.itemClicked(1);
                    break;
                case R.id.threshCheckBox:
                    listener.itemClicked(2);
                    break;
                case R.id.cannyCheckBox:
                    listener.itemClicked(3);
                    break;
            }


    }



    private void updateCheckBoxes()
    {
        View v = getView();

        CheckBox threshCb = v.findViewById(R.id.threshCheckBox);
        CheckBox grayCb = v.findViewById(R.id.grayCheckBox);
        CheckBox cannyCb = v.findViewById(R.id.cannyCheckBox);

        if(threshCb.isChecked())
        {
            grayCb.setChecked(true);
            grayCb.setEnabled(false);
        }
        else
        {
            grayCb.setEnabled(true);
        }

        if(cannyCb.isChecked())
        {
                    threshCb.setChecked(true);
                    threshCb.setEnabled(false);
                    grayCb.setChecked(true);
                    grayCb.setEnabled(false);
        }
        else
        {
            threshCb.setEnabled(true);
            if(!threshCb.isChecked())
            {
                grayCb.setEnabled(true);
            }
        }
        }
    }


