package hcmus.phpthinh.afinal.ui.home.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;

import hcmus.phpthinh.afinal.R;

public class ExerciseAdapter extends ArrayAdapter<Exercise> {
    private String API = "AIzaSyDlwERSi1D9HSZzq_npptkzc9OjkiFt9iU";

    private Context _context;
    private int _layoutID;
    private List<Exercise> _items;

    public ExerciseAdapter(@NonNull Context context, int resource, @NonNull List<Exercise> objects) {
        super(context, resource, objects);
        _context = context;
        _layoutID = resource;
        _items = objects;
    }

    @Override
    public int getCount() {
        return _items.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            //convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_exercise, parent, false);
            LayoutInflater layoutInflater = LayoutInflater.from(_context);
            convertView = layoutInflater.inflate(_layoutID, null, false);
        }

        YouTubePlayerView mYouTube = convertView.findViewById(R.id.youtube_player);
        YouTubePlayer.OnInitializedListener mOnInit;

        TextView name = convertView.findViewById(R.id.nameExercise);
        TextView set = convertView.findViewById(R.id.set);
        TextView rep = convertView.findViewById(R.id.rep);
        TextView power = convertView.findViewById(R.id.power);

        final Exercise exercise = _items.get(position);

        name.setText(exercise.get_name());
        set.setText(String.valueOf(exercise.get_set()));
        rep.setText(String.valueOf(exercise.get_rep()));
        power.setText(exercise.get_power().toString() + "%");

        mYouTube.initialize(API, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("1uwvxTT5V5M");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

        return convertView;
    }
}

