package org.itech.vmmc;

/**
 * Created by rossumg on 3/7/2018.
 */

public class SyncAudit {
    int _id;
    String _timestamp;
    float _latitude;
    float _longitude;
    String _device_id;
    String _username;
    String _password;
    String _email;
    String _progress;
    String _status;

    public SyncAudit(String _timestamp, float _latitude, float _longitude, String _device_id, String _username, String _password, String _email, String _progress, String _status) {
        this._timestamp = _timestamp;
        this._latitude = _latitude;
        this._longitude = _longitude;
        this._device_id = _device_id;
        this._username = _username;
        this._password = _password;
        this._email = _email;
        this._progress = _progress;
        this._status = _status;
    }

    public SyncAudit(){};

    public SyncAudit(int _id, String _timestamp, float _latitude, float _longitude, String _device_id, String _username, String _password, String _email, String _progress, String _status) {

        this._id = _id;
        this._timestamp = _timestamp;
        this._latitude = _latitude;
        this._longitude = _longitude;
        this._device_id = _device_id;
        this._username = _username;
        this._password = _password;
        this._email = _email;
        this._progress = _progress;
        this._status = _status;
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

    public String get_device_id() {
        return _device_id;
    }

    public void set_device_id(String _device_id) {
        this._device_id = _device_id;
    }

    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_progress() {
        return _progress;
    }

    public void set_progress(String _progress) {
        this._progress = _progress;
    }

    public String get_status() {
        return _status;
    }

    public void set_status(String _status) {
        this._status = _status;
    }

}

