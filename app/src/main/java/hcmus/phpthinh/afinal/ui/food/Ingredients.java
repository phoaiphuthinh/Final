package hcmus.phpthinh.afinal.ui.food;

import java.util.ArrayList;

public class Ingredients {

    private String _lable;
    private String _category;
    private String _imagePath;

    public String getBrand() {
        return _brand;
    }

    public void setBrand(String brand) {
        this._brand = _brand;
    }

    private String _brand;
    private ArrayList<Double> _nutients;

    public Ingredients(String _lable, String _category, String _imagePath, String _brand, ArrayList<Double> _nutients) {
        this._lable = _lable;
        this._category = _category;
        this._imagePath = _imagePath;
        this._nutients = _nutients;
        this._brand = _brand;
    }


    public String getLable() {
        return _lable;
    }

    public void setLable(String lable) {
        this._lable = lable;
    }

    public String getCategory() {
        return _category;
    }

    public void setCategory(String category) {
        this._category = category;
    }

    public String getImagePath() {
        return _imagePath;
    }

    public void setImagePath(String imagePath) {
        this._imagePath = imagePath;
    }

    public ArrayList<Double> getNutients() {
        return _nutients;
    }

    public void setNutients(ArrayList<Double> nutients) {
        this._nutients = nutients;
    }
}
