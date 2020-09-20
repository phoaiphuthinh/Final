package hcmus.phpthinh.afinal.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.List;

import hcmus.phpthinh.afinal.R;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

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

        LineChartView lineChartView = root.findViewById(R.id.chart);
        double[] yaxis = {1,3,2,4};
        List axisValue = new ArrayList();
        Line line = new Line(axisValue).setColor(Color.parseColor("#19ACBD"));
        for (int i = 0; i < yaxis.length; i++)
            axisValue.add(new PointValue(i, (float) yaxis[i]));
        List lines = new ArrayList();
        lines.add(line);
        double[] axis = {2,4,1,3};
        axisValue = new ArrayList();
        line = new Line(axisValue).setColor(Color.parseColor("#e3de54"));
        for (int i = 0; i < yaxis.length; i++)
            axisValue.add(new PointValue(i, (float) axis[i]));
        lines.add(line);
        LineChartData lineChartData = new LineChartData();
        lineChartData.setLines(lines);
        lineChartView.setLineChartData(lineChartData);
        return root;
    }
}
