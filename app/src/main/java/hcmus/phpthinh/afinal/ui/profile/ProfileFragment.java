package hcmus.phpthinh.afinal.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import hcmus.phpthinh.afinal.R;

public class ProfileFragment extends Fragment {

    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        sharedPreferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView textView = root.findViewById(R.id.textviewName);
        String name = sharedPreferences.getString(getResources().getString(R.string.keyName), null);
        textView.setText(name);

        textView = (TextView)root.findViewById(R.id.textviewGender);
        boolean gen = sharedPreferences.getBoolean(getResources().getString(R.string.keyGen), true);
        String gender = "Female";
        if (!gen)
            gender = "Male";
        textView.setText(gender);

        textView = (TextView)root.findViewById(R.id.textviewAge);
        int age = sharedPreferences.getInt(getResources().getString(R.string.keyAge), -1);
        textView.setText(Integer.valueOf(age).toString());

        textView = (TextView)root.findViewById(R.id.textvieBMI);
        float height = sharedPreferences.getFloat(getResources().getString(R.string.keyHeight), 0);
        float weight = sharedPreferences.getFloat(getResources().getString(R.string.keyWeight), 1);
        String bmi = String.format("%.2f", weight / (height * height * 1.0 / 10000));
        textView.setText(bmi);

        String path = sharedPreferences.getString(getResources().getString(R.string.keyPath), null);
        if (path != null) {
            ImageView imageView = (ImageView) root.findViewById(R.id.imageviewProfile);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            imageView.setImageBitmap(bitmap);
        }

        return root;
    }
}
