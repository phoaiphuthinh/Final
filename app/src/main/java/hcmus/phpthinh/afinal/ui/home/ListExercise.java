package hcmus.phpthinh.afinal.ui.home;

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
    private ArrayList<Exercise> []_exercise;
    private ExerciseAdapter _exerciseAdapter;
    private ListView _lvExercise;
    public static int day;
    String id = "1uwvxTT5V5M";

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

        _exerciseAdapter = new ExerciseAdapter(this, R.layout.item_exercise, _exercise[day]);

        _lvExercise.setAdapter(_exerciseAdapter);
    }

    private void loadData() {
        _exercise = new ArrayList<>().toArray(new ArrayList[10]);

        for (int i = 0; i < 5; ++i) {
            _exercise[i] = new ArrayList<>();
        }

        String name[] = {"Bench Press", "Incline Bench Press", "Low Cable Fly", "Arnold Press", "Lateral Shoulder Raise",
                "Dead Lift", "Pull Up", "Seat Row",
                "Squad", "Leg Press", "Leg Extension", "Leg Curl",
                "Hammer Curl", "Dip", "Chin Up", "Diamond Push Up",
                "Hanging leg raise", "Hanging knee raise", "Russian twist"};
        String url[] = {"gRVjAtPip0Y", "SrqOu55lrYU", "cwO60wM7zkw", "3ml7BH7mNwQ", "WJm9zA2NY8E",
                "ytGaGIn3SjE", "eGo4IYlbE5g", "GZbfZ033f74",
                "MVMNk0HiTMg", "GvRgijoJ2xY", "YyvSfVjQeL0", "ELOCsoDSmrg",
                "7jqi2qWAUJk", "wjUmnZH528Y", "brhRXlOhsAM", "J0DnG1_S92I",
                "Pr1ieGZ5atk", "Q5QaSJEzmf8", "TfTUk2AjV7g"};
        int rep[] = {4, 4, 5, 5, 5,
                6, 5, 6,
                5, 5, 5, 5,
                4, 4, 4, 4,
                5, 5, 5};
        int set[] = {6, 6, 12, 12, 12,
                6, 12, 10,
                6, 8, 10, 10,
                10, 10, 10, 10,
                10, 10, 10};
        Double power[] = {80.0, 80.0, 60.0, 60.0, 60.0,
                90.0, 80.0, 60.0,
                90.0, 70.0, 60.0, 60.0,
                70.0, 70.0, 80.0, 80.0,
                80.0, 80.0, 80.0};

        for (int i = 0; i < 5; ++i) {
            Exercise tmp = new Exercise(name[i], url[i], rep[i], set[i], power[i]);
            _exercise[0].add(tmp);
        }

        for (int i = 5; i < 8; ++i) {
            Exercise tmp = new Exercise(name[i], url[i], rep[i], set[i], power[i]);
            _exercise[1].add(tmp);
        }

        for (int i = 8; i < 12; ++i) {
            Exercise tmp = new Exercise(name[i], url[i], rep[i], set[i], power[i]);
            _exercise[2].add(tmp);
        }

        for (int i = 12; i < 16; ++i) {
            Exercise tmp = new Exercise(name[i], url[i], rep[i], set[i], power[i]);
            _exercise[3].add(tmp);
        }

        for (int i = 16; i < 19; ++i) {
            Exercise tmp = new Exercise(name[i], url[i], rep[i], set[i], power[i]);
            _exercise[4].add(tmp);
        }
    }

    public static void set(int _day){
        day = _day;
    }
}
