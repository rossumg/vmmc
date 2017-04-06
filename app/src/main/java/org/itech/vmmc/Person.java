package org.itech.vmmc;

/**
 * Created by rossumg on 8/24/2015.
 */
public class Person {

    //private variables
    int _id;
    String _timestamp;
    String _first_name;
    String _last_name;
    String _national_id;
    String _address;
    String _phone;
    String _dob;
    String _gender;
    double _latitude;
    double _longitude;
    int _is_deleted;


    // Empty constructor
    public Person() {

    }

    public Person(int _id, String _first_name, String _last_name, String _national_id, String _address, String _phone, String _dob, String _gender, double _latitude, double _longitude, int _is_deleted) {
        this._id = _id;
        this._first_name = _first_name;
        this._last_name = _last_name;
        this._national_id = _national_id;
        this._address = _address;
        this._phone = _phone;
        this._dob = _dob;
        this._gender = _gender;
        this._latitude = _latitude;
        this._longitude = _longitude;
        this._is_deleted = _is_deleted;
    }

    public Person(String _first_name, String _last_name, String _national_id, String _address, String _phone, String _dob, String _gender, double _latitude, double _longitude, int _is_deleted) {
        this._first_name = _first_name;
        this._last_name = _last_name;
        this._national_id = _national_id;
        this._address = _address;
        this._phone = _phone;
        this._dob = _dob;
        this._gender = _gender;
        this._latitude = _latitude;
        this._longitude = _longitude;
        this._is_deleted = _is_deleted;
    }

    public Person(String _timestamp, String _first_name, String _last_name, String _national_id, String _address, String _phone, String _dob, String _gender, double _latitude, double _longitude, int _is_deleted) {
        this._timestamp = _timestamp;
        this._first_name = _first_name;
        this._last_name = _last_name;
        this._national_id = _national_id;
        this._address = _address;
        this._phone = _phone;
        this._dob = _dob;
        this._gender = _gender;
        this._latitude = _latitude;
        this._longitude = _longitude;
        this._is_deleted = _is_deleted;
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

    public String get_dob() {
        return _dob;
    }

    public void set_dob(String _dob) {
        this._dob = _dob;
    }

    public String get_gender() {
        return _gender;
    }

    public void set_gender(String _gender) {
        this._gender = _gender;
    }

    public double get_latitude() {
        return _latitude;
    }

    public void set_latitude(float _latitude) {
        this._latitude = _latitude;
    }

    public double get_longitude() {
        return _longitude;
    }

    public void set_longitude(float _longiture) {
        this._longitude = _longiture;
    }

    public int get_is_deleted() {
        return _is_deleted;
    }

    public void set_is_deleted(int _is_deleted) {
        this._is_deleted = _is_deleted;
    }
}

