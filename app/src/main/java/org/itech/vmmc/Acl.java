package org.itech.vmmc;

/**
 * Created by rossumg on 4/4/2017.
 */

public class Acl {

    //private variables
    String _id;
    String _acl;

    public Acl() {
    }

    public Acl(String _id, String _acl) {
        this._id = _id;
        this._acl = _acl;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_acl() {
        return _acl;
    }

    public void set_acl(String _acl) {
        this._acl = _acl;
    }
}
