package org.itech.vmmc;

/**
 * Created by rossumg on 4/4/2017.
 */

public class Facilitator {

    //private variables
    int _id;
    String _timestamp;
    String _first_name;
    String _last_name;
    String _national_id;
    String _phone;
    int _facilitator_type_id;
    String _note;
    int _location_id;
    float _latitude;
    float _longitude;
    int _institution_id;

    public Facilitator() {
    }

    public Facilitator(int _id, String _timestamp, String _first_name, String _last_name, String _national_id, String _phone, int _facilitator_type_id, String _note, int _location_id, float _latitude, float _longitude, int _institution_id) {
        this._id = _id;
        this._timestamp = _timestamp;
        this._first_name = _first_name;
        this._last_name = _last_name;
        this._national_id = _national_id;
        this._phone = _phone;
        this._facilitator_type_id = _facilitator_type_id;
        this._note = _note;
        this._location_id = _location_id;
        this._latitude = _latitude;
        this._longitude = _longitude;
        this._institution_id = _institution_id;
    }

    public Facilitator(String _timestamp, String _first_name, String _last_name, String _national_id, String _phone, int _facilitator_type_id, String _note, int _location_id, float _latitude, float _longitude, int _institution_id) {
        this._timestamp = _timestamp;
        this._first_name = _first_name;
        this._last_name = _last_name;
        this._national_id = _national_id;
        this._phone = _phone;
        this._facilitator_type_id = _facilitator_type_id;
        this._note = _note;
        this._location_id = _location_id;
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

    public int get_facilitator_type_id() {
        return _facilitator_type_id;
    }

    public void set_facilitator_type_id(int _facilitator_type_id) {
        this._facilitator_type_id = _facilitator_type_id;
    }

    public String get_note() {
        return _note;
    }

    public void set_note(String _note) {
        this._note = _note;
    }

    public int get_location_id() {
        return _location_id;
    }

    public void set_location_id(int _location_id) {
        this._location_id = _location_id;
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