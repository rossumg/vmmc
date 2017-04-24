package org.itech.vmmc;

/**
 * Created by rossumg on 3/30/2017.
 */

public class Booking {

    //private variables
    int _id;
    String _timestamp;
    String _first_name;
    String _last_name;
    String _national_id;
    String _phone;

    String _fac_first_name;
    String _fac_last_name;
    String _fac_national_id;
    String _fac_phone;

    String _location_id;
    String _projected_date;
    String _actual_date;

    public Booking(int _id, String _timestamp, String _first_name, String _last_name, String _national_id, String _phone, String _fac_first_name, String _fac_last_name, String _fac_national_id, String _fac_phone, String _location_id, String _projected_date, String _actual_date) {
        this._id = _id;
        this._timestamp = _timestamp;
        this._first_name = _first_name;
        this._last_name = _last_name;
        this._national_id = _national_id;
        this._phone = _phone;
        this._fac_first_name = _fac_first_name;
        this._fac_last_name = _fac_last_name;
        this._fac_national_id = _fac_national_id;
        this._fac_phone = _fac_phone;
        this._location_id = _location_id;
        this._projected_date = _projected_date;
        this._actual_date = _actual_date;
    }

    public Booking(String _timestamp, String _first_name, String _last_name, String _national_id, String _phone, String _fac_first_name, String _fac_last_name, String _fac_national_id, String _fac_phone, String _location_id, String _projected_date, String _actual_date) {
        this._timestamp = _timestamp;
        this._first_name = _first_name;
        this._last_name = _last_name;
        this._national_id = _national_id;
        this._phone = _phone;
        this._fac_first_name = _fac_first_name;
        this._fac_last_name = _fac_last_name;
        this._fac_national_id = _fac_national_id;
        this._fac_phone = _fac_phone;
        this._location_id = _location_id;
        this._projected_date = _projected_date;
        this._actual_date = _actual_date;
    }

    public Booking() {
    }

    public Booking(int _id, String _timestamp, String _first_name, String _last_name, String _national_id, String _phone, String _location_id, String _projected_date, String _actural_date) {
        this._id = _id;
        this._timestamp = _timestamp;
        this._first_name = _first_name;
        this._last_name = _last_name;
        this._national_id = _national_id;
        this._phone = _phone;
        this._location_id = _location_id;
        this._projected_date = _projected_date;
        this._actual_date = _actural_date;
    }

    public Booking(String _timestamp, String _first_name, String _last_name, String _national_id, String _phone, String _location_id, String _projected_date, String _actural_date) {
        this._timestamp = _timestamp;
        this._first_name = _first_name;
        this._last_name = _last_name;
        this._national_id = _national_id;
        this._phone = _phone;
        this._location_id = _location_id;
        this._projected_date = _projected_date;
        this._actual_date = _actural_date;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_timestamp() {
        return _timestamp;
    }

    public void set_timestamp(String _timestamp) {
        this._timestamp = _timestamp;
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

    public String get_phone() {
        return _phone;
    }

    public void set_phone(String _phone) {
        this._phone = _phone;
    }

    public String get_location_id() {
        return _location_id;
    }

    public void set_location_id(String _location_id) {
        this._location_id = _location_id;
    }

    public String get_projected_date() {
        return _projected_date;
    }

    public void set_projected_date(String _projected_date) {
        this._projected_date = _projected_date;
    }

    public String get_actual_date() {
        return _actual_date;
    }

    public void set_actual_date(String _actural_date) {
        this._actual_date = _actural_date;
    }

    public String get_fac_first_name() {
        return _fac_first_name;
    }

    public void set_fac_first_name(String _fac_first_name) {
        this._fac_first_name = _fac_first_name;
    }

    public String get_fac_last_name() {
        return _fac_last_name;
    }

    public void set_fac_last_name(String _fac_last_name) {
        this._fac_last_name = _fac_last_name;
    }

    public String get_fac_national_id() {
        return _fac_national_id;
    }

    public void set_fac_national_id(String _fac_national_id) {
        this._fac_national_id = _fac_national_id;
    }

    public String get_fac_phone() {
        return _fac_phone;
    }

    public void set_fac_phone(String _fac_phone) {
        this._fac_phone = _fac_phone;
    }
}