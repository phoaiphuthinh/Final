package hcmus.phpthinh.afinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class DialogUpdate extends DialogFragment {

    EditText editHeight, editWeight;
    ImageButton buttonYes, buttonNo;

    public static DialogUpdate newInstance(String data){
        DialogUpdate dialog = new DialogUpdate();
        Bundle args = new Bundle();
        args.putString("data", data);
        dialog.setArguments(args);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_update, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editHeight = (EditText)view.findViewById(R.id.editUpdateHeight);
        editWeight = (EditText)view.findViewById(R.id.editUpdateWeight);
        buttonNo = (ImageButton)view.findViewById(R.id.buttonNo);
        buttonYes = (ImageButton)view.findViewById(R.id.buttonYes);

        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    private void saveData() {
        String height = editHeight.getText().toString();
        String weight = editWeight.getText().toString();
        if (height.isEmpty() || weight.isEmpty())
            Toast.makeText(getContext(), "Please fill both", Toast.LENGTH_SHORT).show();
        else {
            String current = readCurrentJSON();
            writeJSON(current);
            updateData();
            dismiss();
        }
    }

    private void updateData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(getResources().getString(R.string.keyHeight), Double.valueOf(editHeight.getText().toString()).floatValue());
        editor.putFloat(getResources().getString(R.string.keyWeight), Double.valueOf(editWeight.getText().toString()).floatValue());
        editor.commit();
    }

    private void writeJSON(String current) {
        Date time = Calendar.getInstance().getTime();
        int date = time.getDate();
        int month = time.getMonth();
        int year = time.getYear();
        try {
            JSONObject object;
            if (current == null)
                object = new JSONObject();
            else
                object = new JSONObject(current);
            if (!object.has("arr"))
                object.put("arr", new JSONArray());
            JSONArray arr = object.getJSONArray("arr");
            JSONObject add = new JSONObject();
            add.put("date", date);
            add.put("month", month);
            add.put("year", year);
            add.put("height", Double.valueOf(editHeight.getText().toString()));
            add.put("weight", Double.valueOf(editWeight.getText().toString()));
            for (int i = 0; i < arr.length(); i++){
                JSONObject tmp = arr.getJSONObject(i);
                if (tmp.getInt("date") == date && tmp.getInt("month") == month && tmp.getInt("year") == year) {
                    arr.remove(i);
                    break;
                }
            }
            arr.put(add);
            object.put("arr", arr);
            File file = new File(getContext().getFilesDir(),"json_data");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(object.toString());
            Log.d("@@@", object.toString());
            bufferedWriter.close();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readCurrentJSON(){
        File file = new File(getContext().getFilesDir(), "json_data");
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
            String responce = builder.toString();
            return responce;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
