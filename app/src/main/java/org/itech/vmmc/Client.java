package org.itech.vmmc;

/**
 * Created by rossumg on 4/4/2017.
 */

public class Client {

    //private variables
    int _id;
    String _timestamp;
    String _first_name;
    String _last_name;
    String _national_id;
    String _phone;
    int _status_id;
    int _loc_id;
    float _latitude;
    float _longitude;
    int _institution_id;

    public Client() {
    }

    public Client(int _id, String _timestamp, String _first_name, String _last_name, String _national_id, String _phone, int _status_id, int _loc_id, float _latitude, float _longitude, int _institution_id) {
        this._id = _id;
        this._timestamp = _timestamp;
        this._first_name = _first_name;
        this._last_name = _last_name;
        this._national_id = _national_id;
        this._phone = _phone;
        this._status_id = _status_id;
        this._loc_id = _loc_id;
        this._latitude = _latitude;
        this._longitude = _longitude;
        this._institution_id = _institution_id;
    }

    public Client(String _timestamp, String _first_name, String _last_name, String _national_id, String _phone, int _status_id, int _loc_id, float _latitude, float _longitude, int _institution_id) {
        this._timestamp = _timestamp;
        this._first_name = _first_name;
        this._last_name = _last_name;
        this._national_id = _national_id;
        this._phone = _phone;
        this._status_id = _status_id;
        this._loc_id = _loc_id;
        this._latitude = _latitude;
        this._longitude = _longitude;
        this._institution_id = _institution_id;
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

    public int get_status_id() {
        return _status_id;
    }

    public void set_status_id(int _status_id) {
        this._status_id = _status_id;
    }

    public int get_loc_id() {
        return _loc_id;
    }

    public void set_loc_id(int _loc_id) {
        this._loc_id = _loc_id;
    }

    public float get_latitude() {
        return _latitude;
    }

    public void set_latitude(float _latitude) {
        this._latitude = _latitude;
    }

    public float get_longitude() {
        return _longitude;
    }

    public void set_longitude(float _longitude) {
        this._longitude = _longitude;
    }

    public int get_institution_id() {
        return _institution_id;
    }

    public void set_institution_id(int _institution_id) {
        this._institution_id = _institution_id;
    }
}
