package hcmus.phpthinh.afinal.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

        textView = (TextView)root.findViewById(R.id.joindate);
        textView.setText("" + sharedPreferences.getInt("date", 01) + "-" + sharedPreferences.getInt("month", 01) + "-" + sharedPreferences.getInt("year", 2020));

        String path = sharedPreferences.getString(getResources().getString(R.string.keyPath), null);
        if (path != null) {
            ImageView imageView = (ImageView) root.findViewById(R.id.imageviewProfile);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            imageView.setImageBitmap(bitmap);
        }

        LineChartView lineChartView = root.findViewById(R.id.chart);
        List axisValue = new ArrayList();
        Line line = new Line(axisValue).setColor(Color.parseColor("#19ACBD"));
        for (int i = 0; i < 7; i++)
            if (getAtDiff(i, 0) > 0)
                axisValue.add(new PointValue(6 - i, getAtDiff(i, 0)));
        List lines = new ArrayList();
        lines.add(line);
        axisValue = new ArrayList();
        line = new Line(axisValue).setColor(Color.parseColor("#e3de54"));
        for (int i = 0; i < 7; i++)
            if (getAtDiff(i, 1) > 0)
                axisValue.add(new PointValue(6 - i, getAtDiff(i, 1)));
        lines.add(line);
        axisValue = new ArrayList();
        line = new Line(axisValue).setColor(Color.parseColor("#f26849"));
        for (int i = 0; i < 7; i++)
            if (getAtDiff(i, 2) > 0)
                axisValue.add(new PointValue(6 - i, getAtDiff(i, 2)));
        lines.add(line);

        LineChartData lineChartData = new LineChartData();
        lineChartData.setLines(lines);
        lineChartView.setLineChartData(lineChartData);
        return root;
    }

    private float getAtDiff(int diff, int w){
        File file = new File(getContext().getFilesDir(), "json_data");
        String respone = "";
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder builder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null){
                builder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();// This responce will have Json Format String
            respone = builder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONObject object = new JSONObject(respone);
            JSONArray array = object.getJSONArray("arr");
            for (int i = 0; i < array.length(); i++){
                JSONObject tmp = array.getJSONObject(i);
                int date = tmp.getInt("date");
                int month = tmp.getInt("month");
                int year = tmp.getInt("year");
                if (getDiff(date, month, year) == diff){
                    if (w == 0)
                        return (float) tmp.getDouble("weight");
                    else if (w == 1)
                        return (float) (tmp.getDouble("weight") * 10000.0 / (tmp.getDouble("height") * tmp.getDouble("height")));
                    else
                        return (float) tmp.getDouble("height");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int getDiff(int date, int month, int year){
        Date time = Calendar.getInstance().getTime();
        int d = time.getDate();
        int m = time.getMonth();
        int y = time.getYear();
        if (y != year)
            return 50;
        if (m == month)
            return d - date;
        if (m - month > 1)
            return 40;
        int day = 0;
        if (((1 << month) & 0x0ad5) > 0)
            day = 31;
        else if (((1 << month) & 0x0528) > 0)
            day = 30;
        else if (year % 4 == 0)
            day = 29;
        else
            day = 28;
        return day - date + d;
    }

}
