package com.example.adamw.visoncarsystem;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThresholdParametersFragment extends Fragment {


    public ThresholdParametersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_threshhold_parameters, container, false);
    }


    public void onClickButtonTry(View view)
    {
        View v = getView();

        Intent intent = new Intent(getContext(), CameraScreenActivity.class);
        intent.putExtra(CameraScreenActivity.TYPE, "THRESH");
        EditText editText = (EditText)v.findViewById(R.id.threshValueTextId);
        String value = editText.getText().toString();
        int finalValue = Integer.parseInt(value);
        intent.putExtra(CameraScreenActivity.THRESHPARAM, finalValue);
        startActivity(intent);
    }
}
