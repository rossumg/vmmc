package org.itech.vmmc;

/**
 * Created by rossumg on 5/3/2017.
 */

public class UserToAcl {

    int _id;
    String _acl_id;
    String _user_id;
    String _created_by;
    String _timestamp_created;

    public UserToAcl(){}

    public UserToAcl(int _id, String _acl_id, String _user_id, String _created_by, String _timestamp_created) {
        this._id = _id;
        this._acl_id = _acl_id;
        this._user_id = _user_id;
        this._created_by = _created_by;
        this._timestamp_created = _timestamp_created;
    }

    public UserToAcl(String _acl_id, String _user_id, String _created_by, String _timestamp_created) {
        this._acl_id = _acl_id;
        this._user_id = _user_id;
        this._created_by = _created_by;
        this._timestamp_created = _timestamp_created;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_acl_id() {
        return _acl_id;
    }

    public void set_acl_id(String _acl_id) {
        this._acl_id = _acl_id;
    }

    public String get_user_id() {
        return _user_id;
    }

    public void set_user_id(String _user_id) {
        this._user_id = _user_id;
    }

    public String get_created_by() {
        return _created_by;
    }

    public void set_created_by(String _created_by) {
        this._created_by = _created_by;
    }

    public String get_timestamp_created() {
        return _timestamp_created;
    }

    public void set_timestamp_created(String _timestamp_created) {
        this._timestamp_created = _timestamp_created;
    }
}
