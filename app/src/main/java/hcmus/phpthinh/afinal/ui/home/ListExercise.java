package hcmus.phpthinh.afinal.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.google.android.youtube.player.YouTubeBaseActivity;

import java.util.ArrayList;

import hcmus.phpthinh.afinal.R;
import hcmus.phpthinh.afinal.ui.home.ui.Exercise;
import hcmus.phpthinh.afinal.ui.home.ui.ExerciseAdapter;

public class ListExercise extends YouTubeBaseActivity {
    private ArrayList<Exercise> _exercise;
    private ExerciseAdapter _exerciseAdapter;
    private ListView _lvExercise;
    public static int day;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_exercise);

        Log.e("@@@", "ListExercise");

        loadData();
        initComponent();
    }

    private void initComponent() {
        _lvExercise = findViewById(R.id.listExercise);

        _exerciseAdapter = new ExerciseAdapter(this, R.layout.item_exercise, _exercise);

        _lvExercise.setAdapter(_exerciseAdapter);
    }

    private void loadData() {
        String name[] = {"Bench Press", "Incline Bench Press", "Low Cable Fly", "Arnold Press", "Lateral Shoulder Raise"};
        String url[] = {"gRVjAtPip0Y", "SrqOu55lrYU", "cwO60wM7zkw", "3ml7BH7mNwQ", "WJm9zA2NY8E"};
        int rep[] = {4, 4, 5, 5, 5};
        int set[] = {6, 6, 12, 12, 12};
        Double power[] = {80.0, 80.0, 60.0, 60.0, 60.0};

        _exercise = new ArrayList<>();
        for (int i = 0; i < name.length; ++i) {
            Exercise tmp = new Exercise(name[i], url[i], rep[i], set[i], power[i]);
            _exercise.add(tmp);
        }
    }

    public static void set(int _day){
        day = _day;
    }
}
