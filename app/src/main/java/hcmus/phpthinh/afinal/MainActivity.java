package hcmus.phpthinh.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.mbms.MbmsErrors;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton imageButton = (ImageButton) findViewById(R.id.next);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                boolean account = sharedPreferences.getBoolean(getResources().getString(R.string.keyAcc), false);
                Log.d("@@@", "" + sharedPreferences.getString(getResources().getString(R.string.keyName), ""));
                Intent intent;
                if (account) {
                    intent = new Intent(MainActivity.this, NavigationActivity.class);

                } else {
                    intent = new Intent(MainActivity.this, Register.class);
                }
                startActivity(intent);
            }
        });
    }
}