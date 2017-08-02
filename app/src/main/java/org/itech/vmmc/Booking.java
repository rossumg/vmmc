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
    int    _location_id;
    float _latitude;
    float _longitude;
    String _projected_date;
    String _actual_date;
    String _consent;
    int    _procedure_type_id;
    int    _followup_id;
    String _followup_date;
    String _alt_contact;

    public Booking() {
    }

    public Booking(String _timestamp, String _first_name, String _last_name, String _national_id, String _phone, String _fac_first_name, String _fac_last_name, String _fac_national_id, String _fac_phone, int _location_id, float _latitude, float _longitude, String _projected_date, String _actual_date, String _consent, int _procedure_type_id, int _followup_id, String _followup_date, String _alt_contact) {

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
        this._latitude = _latitude;
        this._longitude = _longitude;
        this._projected_date = _projected_date;
        this._actual_date = _actual_date;
        this._consent = _consent;
        this._procedure_type_id = _procedure_type_id;
        this._followup_id = _followup_id;
        this._followup_date = _followup_date;
        this._alt_contact = _alt_contact;
    }

    public Booking(int _id, String _timestamp, String _first_name, String _last_name, String _national_id, String _phone, String _fac_first_name, String _fac_last_name, String _fac_national_id, String _fac_phone, int _location_id, float _latitude, float _longitude, String _projected_date, String _actual_date, String _consent, int _procedure_type_id, int _followup_id, String _followup_date, String _alt_contact) {

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
        this._latitude = _latitude;
        this._longitude = _longitude;
        this._projected_date = _projected_date;
        this._actual_date = _actual_date;
        this._consent = _consent;
        this._procedure_type_id = _procedure_type_id;
        this._followup_id = _followup_id;
        this._followup_date = _followup_date;
        this._alt_contact = _alt_contact;
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

    public String get_projected_date() {
        return _projected_date;
    }

    public void set_projected_date(String _projected_date) {
        this._projected_date = _projected_date;
    }

    public String get_actual_date() {
        return _actual_date;
    }

    public void set_actual_date(String _actual_date) {
        this._actual_date = _actual_date;
    }

    public String get_consent() {
        return _consent;
    }

    public void set_consent(String _consent) {
        this._consent = _consent;
    }

    public int get_procedure_type_id() {
        return _procedure_type_id;
    }

    public void set_procedure_type_id(int _procedure_type_id) {
        this._procedure_type_id = _procedure_type_id;
    }

    public int get_followup_id() {
        return _followup_id;
    }

    public void set_followup_id(int _followup_id) {
        this._followup_id = _followup_id;
    }

    public String get_followup_date() {
        return _followup_date;
    }

    public void set_followup_date(String _followup_date) {
        this._followup_date = _followup_date;
    }

    public String get_alt_contact() {
        return _alt_contact;
    }

    public void set_alt_contact(String _alt_contact) {
        this._alt_contact = _alt_contact;
    }
}