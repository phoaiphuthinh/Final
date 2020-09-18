package hcmus.phpthinh.afinal.ui.food;

import android.app.ProgressDialog;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import hcmus.phpthinh.afinal.Ingredients;
import hcmus.phpthinh.afinal.IngredientsAdapter;
import hcmus.phpthinh.afinal.R;

public class FoodFragment extends Fragment {

    ImageButton _imageButton;
    EditText _textSearch;
    TextView _textView;
    ListView _list;
    ArrayList<Ingredients> _arraylist;
    IngredientsAdapter _adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_food, container, false);
        _imageButton = (ImageButton)root.findViewById(R.id.foodSearch);
        _textSearch = (EditText)root.findViewById(R.id.editFood);
        _textView = (TextView)root.findViewById(R.id.textHint);
        _list = (ListView)root.findViewById(R.id.listFood);
        _arraylist = new ArrayList<>();
        //_arraylist.add(new Ingredients("abc", "sbbs", "https://www.edamam.com/food-img/0e6/0e61ebadd945b35dc23fa8a39274f2ac.jpg", "abcb",
               // new ArrayList<Double>()));
        _adapter = new IngredientsAdapter(getContext(), _arraylist);
        _list.setAdapter(_adapter);

        _imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showResults();
            }
        });

        return root;
    }

    private void showResults(){
        _textView.setText("Results");
        _arraylist.clear();
        new loadIngredients(_textSearch.getText().toString()).execute();
        _adapter.notifyDataSetChanged();
        _list.invalidateViews();
    }

    private class loadIngredients extends AsyncTask<Void, Void, JSONObject>{

        private ProgressDialog dialog;
        private String query;

        public loadIngredients(String query){
            this.query = query;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(getContext());
            this.dialog.setMessage("Please wait...");
            this.dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {
            BufferedReader reader = null;
            try {
                URL url = new URL("https://api.edamam.com/api/food-database/v2/parser?app_id=38fe98fe&app_key=768d43811c54df7e58977a291f95f9af&ingr=" + query);
                HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream stream = urlConnection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");

                }
                urlConnection.disconnect();
                return new JSONObject(buffer.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (dialog.isShowing())
                dialog.dismiss();
            if (jsonObject == null)
                return;
            _arraylist.clear();
            try {
                JSONArray array = jsonObject.getJSONArray("parsed");
                for (int i = 0; i < array.length(); i++){
                    JSONObject object = array.getJSONObject(i).getJSONObject("food");
                    String name = object.getString("label");
                    String brand = null;
                    if (object.has("brand"))
                        brand = object.getString("brand");
                    String category = object.getString("category");
                    String path = null;
                    if (object.has("image"))
                        path = object.getString("image");
                    ArrayList<Double> newArr = new ArrayList<>();
                    JSONObject nutrients = object.getJSONObject("nutrients");
                    newArr.add(Double.valueOf(nutrients.getDouble("ENERC_KCAL")));
                    newArr.add(Double.valueOf(nutrients.getDouble("PROCNT")));
                    newArr.add(Double.valueOf(nutrients.getDouble("FAT")));
                    newArr.add(Double.valueOf(nutrients.getDouble("CHOCDF")));
                    _arraylist.add(new Ingredients(name, category, path, brand,newArr));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
