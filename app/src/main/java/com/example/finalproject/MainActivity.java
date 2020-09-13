package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText _height, _weight;
    TextView _bmi;
    Button _btnGenerate;
    ImageView _imgChart;
    int _gender = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        _btnGenerate.setOnClickListener(this);

    }

    private void init() {
        _height = findViewById(R.id.height);
        _weight = findViewById(R.id.weight);
        _bmi = findViewById(R.id.bmi);
        _imgChart = findViewById(R.id.imgChart);

        _btnGenerate = findViewById(R.id.btnGenerate);
    }

    @Override
    public void onClick(View view) {
        Double height = Double.valueOf(_height.getText().toString());
        Double weight = Double.valueOf(_weight.getText().toString());

        _bmi.setText(String.valueOf(weight/height/height*10000));
    }

    public void onRadioButtonClicked(View view) {
        boolean choosed = ((RadioButton) view).isChecked();
        if (!choosed) _gender = -1;
        switch (view.getId()) {
            case R.id.genFemale:
                _gender = 0;
                _imgChart.setImageResource(R.drawable.bmi_chart_female);
                break;
            default:
                _gender = 1;
                _imgChart.setImageResource(R.drawable.bmi_chart_male);
                break;
        }
    }
}