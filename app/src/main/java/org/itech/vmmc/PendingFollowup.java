package org.itech.vmmc;

/**
 * Created by rossumg on 5/19/2017.
 */

public class PendingFollowup {

    //private variables

    String _first_name;
    String _last_name;
    String _national_id;
    int _difference;
    String _dob;
    String _location;
    String _address;
    String _phone;

    public PendingFollowup() {
    }

    public String get_first_name() {
        return _first_name;
    }

    public void set_first_name(String _first_name) {
        this._first_name = _first_name;
    }

    public String get_last_name() {
        return _last_name;
    }

    public void set_last_name(String _last_name) {
        this._last_name = _last_name;
    }

    public String get_national_id() {
        return _national_id;
    }

    public void set_national_id(String _national_id) {
        this._national_id = _national_id;
    }

    public int get_difference() {
        return _difference;
    }

    public void set_difference(int _difference) {
        this._difference = _difference;
    }

    public String get_dob() {
        return _dob;
    }

    public void set_dob(String _dob) {
        this._dob = _dob;
    }

    public String get_location() {
        return _location;
    }

    public void set_location(String _location) {
        this._location = _location;
    }

    public String get_address() {
        return _address;
    }

    public void set_address(String _address) {
        this._address = _address;
    }

    public String get_phone() {
        return _phone;
    }

    public void set_phone(String _phone) {
        this._phone = _phone;
    }
}
