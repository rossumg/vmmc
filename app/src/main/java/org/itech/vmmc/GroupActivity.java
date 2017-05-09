package org.itech.vmmc;

/**
 * Created by rossumg on 4/4/2017.
 */

public class GroupActivity {

    //private variables
    int _id;
    String _timestamp;
    String _name;
    int _location_id;
    String _activity_date;
    int group_type_id;
    int males;
    int females;
    String messages;
    float _latitude;
    float _longitude;

    public GroupActivity() {}

    public GroupActivity(int _id, String _name, String _timestamp, int _location_id, String _activity_date, int group_type_id, int males, int females, String messages, float _latitude, float _longitude) {
        this._id = _id;
        this._name = _name;
        this._timestamp = _timestamp;
        this._location_id = _location_id;
        this._activity_date = _activity_date;
        this.group_type_id = group_type_id;
        this.males = males;
        this.females = females;
        this.messages = messages;
        this._latitude = _latitude;
        this._longitude = _longitude;
    }

    public GroupActivity(String _name, String _timestamp, int _location_id, String _activity_date, int group_type_id, int males, int females, String messages, float _latitude, float _longitude) {
        this._name = _name;
        this._timestamp = _timestamp;
        this._location_id = _location_id;
        this._activity_date = _activity_date;
        this.group_type_id = group_type_id;
        this.males = males;
        this.females = females;
        this.messages = messages;
        this._latitude = _latitude;
        this._longitude = _longitude;
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

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public int get_location_id() {
        return _location_id;
    }

    public void set_location_id(int _location_id) {
        this._location_id = _location_id;
    }

    public String get_activity_date() {
        return _activity_date;
    }

    public void set_activity_date(String _activity_date) {
        this._activity_date = _activity_date;
    }

    public int get_group_type_id() {
        return group_type_id;
    }

    public void set_group_type_id(int group_type_id) {
        this.group_type_id = group_type_id;
    }

    public int get_males() {
        return males;
    }

    public void set_males(int males) {
        this.males = males;
    }

    public int get_females() {
        return females;
    }

    public void set_females(int females) {
        this.females = females;
    }

    public String get_messages() {
        return messages;
    }

    public void set_messages(String messages) {
        this.messages = messages;
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
}