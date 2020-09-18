package hcmus.phpthinh.afinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class IngredientsAdapter extends ArrayAdapter<Ingredients> {


    public IngredientsAdapter(@NonNull Context context, ArrayList<Ingredients> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = createView(position, (ListView)parent);
        return convertView;
    }

    private View createView(int position, ListView parent) {
        View item = LayoutInflater.from(getContext()).inflate(R.layout.item_ingredients, parent, false);
        Ingredients ingredients = getItem(position);
        DisplayInfo(item, ingredients);
        return item;
    }

    private void DisplayInfo(View item, Ingredients ingredients) {
        ImageView imageView = (ImageView)item.findViewById(R.id.imageIngredients);
        if (ingredients.getImagePath() != null)
            new loadImage(imageView, ingredients.getImagePath()).execute();
        TextView textView = (TextView)item.findViewById(R.id.ingredientsName);
        textView.setText(ingredients.getLable());

        textView = (TextView)item.findViewById(R.id.ingredientsCategory);
        textView.setText(ingredients.getCategory());

        textView = (TextView)item.findViewById(R.id.ingredientsDetails);
        String nutients = "Energy: " + ingredients.getNutients().get(0) + " kcal\n" +
                        "Protein: " + ingredients.getNutients().get(1) + " g\n" +
                        "Fat: " + ingredients.getNutients().get(2) + " g\n" +
                        "Carbs: " + ingredients.getNutients().get(3) + " g\n";
        if (ingredients.getBrand() != null)
            nutients += "Brand: " + ingredients.getBrand();
        textView.setText(nutients);
    }

    private class loadImage extends AsyncTask<Void, Void, Bitmap>{
        private String path;
        private ImageView imageView;
        public loadImage(ImageView imageView, String path){
            this.path = path;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(path);
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                Log.d("@@@", ""+ bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null)
                imageView.setImageBitmap(bitmap);
        }
    }

}
