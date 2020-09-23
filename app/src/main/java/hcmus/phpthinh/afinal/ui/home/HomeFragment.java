package hcmus.phpthinh.afinal.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import hcmus.phpthinh.afinal.R;

public class HomeFragment extends Fragment implements View.OnClickListener {
    TextView _day01, _day02, _day03, _day04, _day05;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);

        initComponents();

        return root;
    }

    private void initComponents() {
        _day01 = root.findViewById(R.id.chest_day);
        _day02 = root.findViewById(R.id.back_day);
        _day03 = root.findViewById(R.id.leg_day);
        _day04 = root.findViewById(R.id.arm_day);
        _day05 = root.findViewById(R.id.abs_day);

        _day01.setOnClickListener(this);
        _day02.setOnClickListener(this);
        _day03.setOnClickListener(this);
        _day04.setOnClickListener(this);
        _day05.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chest_day:
                ListExercise.set(0);
                break;
            case R.id.back_day:
                ListExercise.set(1);
                break;
            case R.id.leg_day:
                ListExercise.set(2);
                break;
            case R.id.arm_day:
                ListExercise.set(3);
                break;
            case R.id.abs_day:
                ListExercise.set(4);
                break;
            default:
                break;
        }
        Intent myIntent = new Intent(getContext(), ListExercise.class);
        startActivity(myIntent);
    }
}