package org.itech.vmmc;

/**
 * Created by rossumg on 3/14/2018.
 */
public class DatabaseNames {

    // vmmc.db table names
    public static final String TABLE_SYNC_AUDIT  = "sync_audit";
    public static final String TABLE_PERSON      = "person";
    public static final String TABLE_USER        = "user";
    public static final String TABLE_USER_TYPE   = "user_type";
    public static final String TABLE_USER_TO_ACL = "user_to_acl";
    public static final String TABLE_ACL         = "acl";
    public static final String TABLE_CLIENT      = "client_table";
    public static final String TABLE_FACILITATOR = "facilitator";
    public static final String TABLE_LOCATION    = "location";
    public static final String TABLE_ADDRESS     = "address";
    public static final String TABLE_REGION      = "region";
    public static final String TABLE_CONSTITUENCY = "constituency";
    public static final String TABLE_BOOKING      = "booking";
    public static final String TABLE_INTERACTION  = "interaction";
    public static final String TABLE_GEOLOCATION  = "geolocation";
    public static final String TABLE_FACILITATOR_TYPE = "facilitator_type";
    public static final String TABLE_PROCEDURE_TYPE = "procedure_type";
    public static final String TABLE_FOLLOWUP       = "followup";
    public static final String TABLE_INTERACTION_TYPE = "interaction_type";
    public static final String TABLE_STATUS_TYPE  = "status_type";
    public static final String TABLE_INSTITUTION  = "institution";
    public static final String TABLE_GROUP_ACTIVITY  = "group_activity";
    public static final String TABLE_GROUP_TYPE  = "group_type";

    // audit_sync table column names
    public static final String SYNC_AUDIT_ID         = "id";
    public static final String SYNC_AUDIT_TIMESTAMP  = "timestamp";
    public static final String SYNC_AUDIT_LATITUDE   = "latitude";
    public static final String SYNC_AUDIT_LONGITUDE  = "longitude";
    public static final String SYNC_AUDIT_DEVICE_ID  = "device_id";
    public static final String SYNC_AUDIT_USERNAME   = "username";
    public static final String SYNC_AUDIT_PASSWORD   = "password";
    public static final String SYNC_AUDIT_PROGRESS   = "progress";
    public static final String SYNC_AUDIT_STATUS     = "status";

    // person table column names
    public static final String PERSON_ID          = "id";
    public static final String PERSON_TIMESTAMP  = "timestamp";
    public static final String PERSON_FIRST_NAME  = "first_name";
    public static final String PERSON_LAST_NAME   = "last_name";
    public static final String PERSON_NATIONAL_ID = "national_id";
    public static final String PERSON_ADDRESS     = "address_id";
    public static final String PERSON_PHONE       = "phone";
    public static final String PERSON_DOB         = "dob";
    public static final String PERSON_GENDER      = "gender";
    public static final String PERSON_LATITUDE    = "latitude";
    public static final String PERSON_LONGITUDE   = "longitude";
    public static final String PERSON_IS_DELETED      = "is_deleted";

    // booking table column names
    public static final String BOOKING_ID              = "id";
    public static final String BOOKING_TIMESTAMP        = "timestamp";
    public static final String BOOKING_FIRST_NAME  = "first_name";
    public static final String BOOKING_LAST_NAME   = "last_name";
    public static final String BOOKING_NATIONAL_ID = "national_id";
    public static final String BOOKING_PHONE       = "phone";

    public static final String BOOKING_LOCATION_ID     = "location_id";
    public static final String BOOKING_LATITUDE        = "latitude";
    public static final String BOOKING_LONGITUDE       = "longitude";

    public static final String BOOKING_PROJECTED_DATE  = "projected_date";
    public static final String BOOKING_ACTUAL_DATE     = "actual_date";

    public static final String BOOKING_CONSENT           = "consent";
    public static final String BOOKING_PROCEDURE_TYPE_ID = "procedure_type_id";
    public static final String BOOKING_FOLLOWUP_ID       = "followup_id";
    public static final String BOOKING_FOLLOWUP_DATE       = "followup_date";
    public static final String BOOKING_ALT_CONTACT       = "alt_contact";

    // user table column names
    public static final String USER_ID = "id";
    public static final String USER_TIMESTAMP = "timestamp";
    public static final String USER_USERNAME = "username";
    public static final String USER_PASSWORD = "password";
    public static final String USER_EMAIL = "email";
    public static final String USER_FIRST_NAME = "first_name";
    public static final String USER_LAST_NAME = "last_name";
    public static final String USER_NATIONAL_ID = "national_id";
    public static final String USER_PHONE = "phone";
    public static final String USER_REGION_ID = "region_id";
    public static final String USER_USER_TYPE_ID = "user_type_id";
    public static final String USER_LOCATION_ID = "location_id";
    public static final String USER_MODIFIED_BY = "modified_by";
    public static final String USER_CREATED_BY = "created_by";
    public static final String USER_IS_BLOCKED = "is_blocked";
    public static final String USER_TIMESTAMP_UPDATED = "timestamp_updated";
    public static final String USER_TIMESTAMP_CREATED = "timestamp_created";
    public static final String USER_TIMESTAMP_LAST_LOGIN = "timestamp_last_login";

    // user_type table column names
    public static final String USER_TYPE_ID = "id";
    public static final String USER_TYPE_NAME = "name";

    // user_to_acl table column names
    public static final String USER_TO_ACL_ID = "id";
    public static final String USER_TO_ACL_TIMESTAMP_CREATED = "timestamp_created";
    public static final String USER_TO_ACL_ACL_ID = "acl_id";
    public static final String USER_TO_ACL_USER_ID = "user_id";
    public static final String USER_TO_ACL_CREATED_BY = "created_by";

    // acl table column names
    public static final String ACL_ID = "id";
    public static final String ACL_ACL = "acl";

    // client table column names
    public static final String CLIENT_ID        = "id";
    public static final String CLIENT_TIMESTAMP  = "timestamp";
    public static final String CLIENT_FIRST_NAME  = "first_name";
    public static final String CLIENT_LAST_NAME   = "last_name";
    public static final String CLIENT_NATIONAL_ID = "national_id";
    public static final String CLIENT_PHONE       = "phone";
    public static final String CLIENT_STATUS_ID    = "status_id";
    public static final String CLIENT_LOC_ID    = "loc_id";
    public static final String CLIENT_LATITUDE    = "latitude";
    public static final String CLIENT_LONGITUDE    = "longitude";
    public static final String CLIENT_INSTITUTION_ID    = "institution_id";
    public static final String CLIENT_GROUP_ACTIVITY_NAME    = "group_activity_name";
    public static final String CLIENT_GROUP_ACTIVITY_DATE    = "group_activity_date";

    public static final String CLIENT_FAC_FIRST_NAME  = "fac_first_name";
    public static final String CLIENT_FAC_LAST_NAME   = "fac_last_name";
    public static final String CLIENT_FAC_NATIONAL_ID = "fac_national_id";
    public static final String CLIENT_FAC_PHONE       = "fac_phone";

    public static final String CLIENT_ADDRESS_ID    = "address_id";
    public static final String CLIENT_DOB    = "dob";
    public static final String CLIENT_GENDER    = "gender";

    // facilitator table column names
    public static final String FACILITATOR_ID          = "id";
    public static final String FACILITATOR_TIMESTAMP  = "timestamp";
    public static final String FACILITATOR_FIRST_NAME  = "first_name";
    public static final String FACILITATOR_LAST_NAME = "last_name";
    public static final String FACILITATOR_NATIONAL_ID = "national_id";
    public static final String FACILITATOR_PHONE       = "phone";
    public static final String FACILITATOR_FACILITATOR_TYPE_ID  = "facilitator_type_id";
    public static final String FACILITATOR_NOTE        = "note";
    public static final String FACILITATOR_LOCATION_ID = "location_id";
    public static final String FACILITATOR_LATITUDE    = "latitude";
    public static final String FACILITATOR_LONGITUDE   = "longitude";
    public static final String FACILITATOR_INSTITUTION_ID = "institution_id";
    public static final String FACILITATOR_ADDRESS_ID    = "address_id";
    public static final String FACILITATOR_DOB    = "dob";
    public static final String FACILITATOR_GENDER    = "gender";

    // group_activity table column names
    public static final String GROUP_ACTIVITY_ID          = "id";
    public static final String GROUP_ACTIVITY_NAME  = "name";
    public static final String GROUP_ACTIVITY_TIMESTAMP  = "timestamp";
    public static final String GROUP_ACTIVITY_LOCATION_ID = "location_id";
    public static final String GROUP_ACTIVITY_ACTIVITY_DATE = "activity_date";
    public static final String GROUP_ACTIVITY_GROUP_TYPE_ID  = "group_type_id";
    public static final String GROUP_ACTIVITY_INSTITUTION_ID   = "institution_id";
    public static final String GROUP_ACTIVITY_MALES  = "males";
    public static final String GROUP_ACTIVITY_FEMALES  = "females";
    public static final String GROUP_ACTIVITY_MESSAGES        = "messages";
    public static final String GROUP_ACTIVITY_LATITUDE    = "latitude";
    public static final String GROUP_ACTIVITY_LONGITUDE   = "longitude";

    // location table column names
    public static final String LOCATION_ID   = "id";
    public static final String LOCATION_NAME = "name";
    public static final String LOCATION_REGION_ID = "region_id";

    // address table column names
    public static final String ADDRESS_ID   = "id";
    public static final String ADDRESS_NAME = "name";
    public static final String ADDRESS_REGION_ID = "region_id";

    // institution table column names
    public static final String INSTITUTION_ID          = "id";
    public static final String INSTITUTION_NAME        = "name";
    public static final String INSTITUTION_REGION_ID = "region_id";

    // group_type table column names
    public static final String GROUP_TYPE_ID          = "id";
    public static final String GROUP_TYPE_NAME        = "name";

    // region table column names
    public static final String REGION_ID          = "id";
    public static final String REGION_NAME        = "name";

    // constituency table column names
    public static final String CONSTITUENCY_ID        = "id";
    public static final String CONSTITUENCY_NAME      = "name";
    public static final String CONSTITUENCY_REGION_ID = "region_id";

    // interaction table column names
    public static final String INTERACTION_ID          = "id";
    public static final String INTERACTION_TIMESTAMP  = "timestamp";
    public static final String INTERACTION_FAC_FIRST_NAME  = "fac_first_name";
    public static final String INTERACTION_FAC_LAST_NAME  = "fac_last_name";
    public static final String INTERACTION_FAC_NATIONAL_ID  = "fac_national_id";
    public static final String INTERACTION_FAC_PHONE  = "fac_phone";
    public static final String INTERACTION_PERSON_FIRST_NAME  = "person_first_name";
    public static final String INTERACTION_PERSON_LAST_NAME  = "person_last_name";
    public static final String INTERACTION_PERSON_NATIONAL_ID  = "person_national_id";
    public static final String INTERACTION_PERSON_PHONE  = "person_phone";
    public static final String INTERACTION_DATE = "interaction_date";
    public static final String INTERACTION_FOLLOWUP_DATE = "followup_date";
    public static final String INTERACTION_TYPE        = "type_id";
    public static final String INTERACTION_NOTE        = "note";

    // interaction_type table column names
    public static final String INTERACTION_TYPE_ID     = "id";
    public static final String INTERACTION_TYPE_NAME   = "name";


    // facilitator_type table column names
    public static final String FACILITATOR_TYPE_ID     = "id";
    public static final String FACILITATOR_TYPE_NAME   = "name";

    // procedure_type table column names
    public static final String PROCEDURE_TYPE_ID     = "id";
    public static final String PROCEDURE_TYPE_NAME   = "name";

    // followup table column names
    public static final String FOLLOWUP_ID     = "id";
    public static final String FOLLOWUP_NAME   = "name";

    // status_type table column names
    public static final String STATUS_TYPE_ID     = "id";
    public static final String STATUS_TYPE_NAME   = "name";

    // geolocation table column names
    public static final String GEOLOCATION_ID        = "id";
    public static final String GEOLOCATION_LAT       = "lat";
    public static final String GEOLOCATION_LONG      = "long";
    public static final String GEOLOCATION_DEVICE_ID = "device_id";
    public static final String GEOLOCATION_TIMESTAMP = "timestamp";
    public static final String GEOLOCATION_USERNAME  = "username";
    public static final String GEOLOCATION_PASSWORD  = "password";

}

