package hcmus.phpthinh.afinal.ui.home.ui;

public class Exercise {
    String _name;
    String _url;
    int _set, _rep;
    Double _power;

    public Exercise(String _name, String _url, int _set, int _rep, Double _power) {
        this._name = _name;
        this._url = _url;
        this._set = _set;
        this._rep = _rep;
        this._power = _power;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_url() {
        return _url;
    }

    public void set_url(String _url) {
        this._url = _url;
    }

    public int get_set() {
        return _set;
    }

    public void set_set(int _set) {
        this._set = _set;
    }

    public int get_rep() {
        return _rep;
    }

    public void set_rep(int _rep) {
        this._rep = _rep;
    }

    public Double get_power() {
        return _power;
    }

    public void set_power(Double _power) {
        this._power = _power;
    }

}
