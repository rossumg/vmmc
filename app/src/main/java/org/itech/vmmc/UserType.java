package org.itech.vmmc;

/**
 * Created by rossumg on 4/4/2017.
 */

public class UserType {

    //private variables
    int _id;
    String _name;

    public UserType() {
    }

    public UserType(int _id, String _name) {
        this._id = _id;
        this._name = _name;
    }

    public UserType(String _name) {
        this._name = _name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }
}
