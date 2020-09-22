package hcmus.phpthinh.afinal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class Register extends AppCompatActivity{

    private static final int REQ_CODE = 0x23fa;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText editName, editAge, editHeight, editWeight;
    RadioButton radioMale, radioFemale;
    ImageButton imageButton, imageNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editName = (EditText)findViewById(R.id.editName);

        String name = sharedPreferences.getString(getResources().getString(R.string.keyName), null);
        if (name != null)
            editName.setHint(name);

        editAge = (EditText)findViewById(R.id.editAge);
        int age = sharedPreferences.getInt(getResources().getString(R.string.keyAge), -1);
        if (age > -1)
            editAge.setHint("" + age);

        editHeight = (EditText)findViewById(R.id.editHeight);
        float height = sharedPreferences.getFloat(getResources().getString(R.string.keyHeight), 0);
        if (height > 0)
            editHeight.setHint("" + height);

        editWeight = (EditText)findViewById(R.id.editWeight);
        float weight = sharedPreferences.getFloat(getResources().getString(R.string.keyWeight), 0);
        if (weight > 0)
            editWeight.setHint("" + weight);


        radioMale = (RadioButton)findViewById(R.id.radio_male);

        radioFemale = (RadioButton)findViewById(R.id.radio_female);

        imageButton = (ImageButton)findViewById(R.id.profile);
        String path = sharedPreferences.getString(getResources().getString(R.string.keyPath), null);
        if (path != null){
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            imageButton.setImageBitmap(bitmap);
        }

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE);
            }
        });

        imageNext = (ImageButton)findViewById(R.id.imageNext);
        imageNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editAge.getText().toString().isEmpty() || editHeight.getText().toString().isEmpty() ||
                        editName.getText().toString().isEmpty() || editWeight.getText().toString().isEmpty() ||
                        !(radioFemale.isChecked() || radioMale.isChecked()) ) {
                    Toast.makeText(getBaseContext(), "You should fill in all fields!", Toast.LENGTH_SHORT).show();
                } else {
                    storeAndCall();
                }
            }
        });
    }

    private void storeAndCall() {
        String name = editName.getText().toString();
        int age = Integer.valueOf(editAge.getText().toString());
        double height = Double.valueOf(editHeight.getText().toString());
        double weight = Double.valueOf(editWeight.getText().toString());
        boolean female = true;
        if (radioMale.isChecked())
            female = false;
        savePathFromImage(R.string.keyName, name);
        editor.putInt(getResources().getString(R.string.keyAge), age);
        editor.putFloat(getResources().getString(R.string.keyHeight), (float) height);
        editor.putFloat(getResources().getString(R.string.keyWeight), (float) weight);
        editor.putBoolean(getResources().getString(R.string.keyGen), female);
        editor.putBoolean(getResources().getString(R.string.keyAcc), true);
        editor.commit();
        saveJSON(height, weight);
        Intent intent = new Intent(Register.this, NavigationActivity.class);
        startActivity(intent);
    }

    private void saveJSON(double height, double weight) {
        Date time = Calendar.getInstance().getTime();
        int date = time.getDate();
        int month = time.getMonth();
        int year = time.getYear();
        File file = new File(getBaseContext().getFilesDir(), "json_data");
        String respone = null;
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
            JSONObject object;
            if (respone == null)
                object = new JSONObject();
            else
                object = new JSONObject(respone);
            if (!object.has("arr"))
                object.put("arr", new JSONArray());
            JSONArray arr = object.getJSONArray("arr");
            JSONObject add = new JSONObject();
            add.put("date", date);
            add.put("month", month);
            add.put("year", year);
            add.put("height", height);
            add.put("weight", weight);
            for (int i = 0; i < arr.length(); i++){
                JSONObject tmp = arr.getJSONObject(i);
                if (tmp.getInt("date") == date && tmp.getInt("month") == month && tmp.getInt("year") == year) {
                    arr.remove(i);
                    break;
                }
            }
            arr.put(add);
            object.put("arr", arr);
            file = new File(getBaseContext().getFilesDir(),"json_data");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE && resultCode == RESULT_OK && data != null){
            Uri selected = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selected);
                imageButton.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            savePathFromImage(R.string.keyPath, getRealPathFromURI(selected));
        }
    }

    private void savePathFromImage(int p, String realPathFromURI) {
        editor.putString(getResources().getString(p), realPathFromURI);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = this.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


}