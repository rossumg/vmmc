package org.itech.vmmc;

/**
 * Created by rossumg on 4/4/2017.
 */

public class Interaction {

    int _id;
    String _timestamp;
    String _fac_first_name;
    String _fac_last_name;
    String _fac_national_id;
    String _fac_phone;
    String _person_first_name;
    String _person_last_name;
    String _person_national_id;
    String _person_phone;
    String _interaction_date;
    String _followup_date;
    int _type_id;
    String _note;

    public Interaction(int _id, String _timestamp, String _fac_first_name, String _fac_last_name, String _fac_national_id, String _fac_phone, String _person_first_name, String _person_last_name, String _person_national_id, String _person_phone, String _interaction_date, String _followup_date, int _type_id, String _note) {
        this._id = _id;
        this._timestamp = _timestamp;
        this._fac_first_name = _fac_first_name;
        this._fac_last_name = _fac_last_name;
        this._fac_national_id = _fac_national_id;
        this._fac_phone = _fac_phone;
        this._person_first_name = _person_first_name;
        this._person_last_name = _person_last_name;
        this._person_national_id = _person_national_id;
        this._person_phone = _person_phone;
        this._interaction_date = _interaction_date;
        this._followup_date = _followup_date;
        this._type_id = _type_id;
        this._note = _note;
    }

    public Interaction(String _timestamp, String _fac_first_name, String _fac_last_name, String _fac_national_id, String _fac_phone, String _person_first_name, String _person_last_name, String _person_national_id, String _person_phone, String _interaction_date, String _followup_date, int _type_id, String _note) {
        this._timestamp = _timestamp;
        this._fac_first_name = _fac_first_name;
        this._fac_last_name = _fac_last_name;
        this._fac_national_id = _fac_national_id;
        this._fac_phone = _fac_phone;
        this._person_first_name = _person_first_name;
        this._person_last_name = _person_last_name;
        this._person_national_id = _person_national_id;
        this._person_phone = _person_phone;
        this._interaction_date = _interaction_date;
        this._followup_date = _followup_date;
        this._type_id = _type_id;
        this._note = _note;
    }

    public Interaction() {
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

    public String get_person_first_name() {
        return _person_first_name;
    }

    public void set_person_first_name(String _person_first_name) {
        this._person_first_name = _person_first_name;
    }

    public String get_person_last_name() {
        return _person_last_name;
    }

    public void set_person_last_name(String _person_last_name) {
        this._person_last_name = _person_last_name;
    }

    public String get_person_national_id() {
        return _person_national_id;
    }

    public void set_person_national_id(String _person_national_id) {
        this._person_national_id = _person_national_id;
    }

    public String get_person_phone() {
        return _person_phone;
    }

    public void set_person_phone(String _person_phone) {
        this._person_phone = _person_phone;
    }

    public String get_interaction_date() {
        return _interaction_date;
    }

    public void set_interaction_date(String _interaction_date) {
        this._interaction_date = _interaction_date;
    }

    public String get_followup_date() {
        return _followup_date;
    }

    public void set_followup_date(String _followup_date) {
        this._followup_date = _followup_date;
    }

    public int get_type_id() {
        return _type_id;
    }

    public void set_type_id(int _type_id) {
        this._type_id = _type_id;
    }

    public String get_note() {
        return _note;
    }

    public void set_note(String _note) {
        this._note = _note;
    }
}
