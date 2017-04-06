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
    String _type;
    String _note;
    String _location_id;
    String _lat;
    String _long;
    String _institution_id;

    public Facilitator() {
    }

    public Facilitator(int _id, String _timestamp, String _first_name, String _last_name, String _national_id, String _phone, String _type, String _note, String _location_id, String _lat, String _long, String _institution_id) {
        this._id = _id;
        this._timestamp = _timestamp;
        this._first_name = _first_name;
        this._last_name = _last_name;
        this._national_id = _national_id;
        this._phone = _phone;
        this._type = _type;
        this._note = _note;
        this._location_id = _location_id;
        this._lat = _lat;
        this._long = _long;
        this._institution_id = _institution_id;
    }

    public Facilitator(String _timestamp, String _first_name, String _last_name, String _national_id, String _phone, String _type, String _note, String _location_id, String _lat, String _long, String _institution_id) {
        this._timestamp = _timestamp;
        this._first_name = _first_name;
        this._last_name = _last_name;
        this._national_id = _national_id;
        this._phone = _phone;
        this._type = _type;
        this._note = _note;
        this._location_id = _location_id;
        this._lat = _lat;
        this._long = _long;
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

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public String get_note() {
        return _note;
    }

    public void set_note(String _note) {
        this._note = _note;
    }

    public String get_location_id() {
        return _location_id;
    }

    public void set_location_id(String _location_id) {
        this._location_id = _location_id;
    }

    public String get_lat() {
        return _lat;
    }

    public void set_lat(String _lat) {
        this._lat = _lat;
    }

    public String get_long() {
        return _long;
    }

    public void set_long(String _long) {
        this._long = _long;
    }

    public String get_institution_id() {
        return _institution_id;
    }

    public void set_institution_id(String _institution_id) {
        this._institution_id = _institution_id;
    }
}