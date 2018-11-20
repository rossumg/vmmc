package org.itech.vmmc;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import org.itech.vmmc.APICommunication.getMySQLTableVolley;
import org.itech.vmmc.APICommunication.putMySQLTableVolley;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static org.itech.vmmc.DatabaseNames.*;

/**
 * Created by Greg on 8/21/2015.
 */

public class DBHelper extends SQLiteOpenHelper{

    public static String LOG = "gnr";
    public static String VMMC_DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";
    public static String VMMC_DATE_FORMAT = "yyyy-MM-dd";
    public static int VMMC_PHONE_NUMBER_LENGTH = 10;
    public static String VMMC_PHONE_NUMBER_REGEX = "\\d+";
    private static final int DATABASE_VERSION = 1;
    public Context _context;

    // Database Name
    private static final String DATABASE_NAME = "vmmc.db";




    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this._context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(LOG, "DBHelper.onCreate0");

        //try { db.execSQL("delete from person;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
        try {
            String CREATE_SYNC_AUDIT_TABLE = "CREATE TABLE IF NOT EXISTS sync_audit(" +
                    "id integer primary key  autoincrement  not null  unique, " +
                    "timestamp datetime default current_timestamp, " +
                    "latitude real default 0.0, " +
                    "longitude real default 0.0, " +
                    "device_id varchar, " +
                    "username varchar, " +
                    "password varchar, " +
                    "progress varchar, " +
                    "status varchar);";
            db.execSQL(CREATE_SYNC_AUDIT_TABLE);

            String CREATE_PERSON_TABLE = "CREATE TABLE IF NOT EXISTS person(" +
                    "id integer primary key  autoincrement  not null  unique, " +
                    "timestamp datetime default current_timestamp, " +
                    "first_name varchar, " +
                    "last_name varchar, " +
                    "national_id varchar, " +
                    "address_id int, " +
                    "phone varchar, " +
                    "dob date, " +
                    "gender varchar, " +
                    "latitude real default 0.0, " +
                    "longitude real default 0.0, " +
                    "is_deleted int, " +
                    "constraint name_constraint unique (first_name, last_name, national_id, phone) );";
            db.execSQL(CREATE_PERSON_TABLE);

            //try { db.execSQL("delete from booking;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
            String CREATE_BOOKING_TABLE = "CREATE TABLE IF NOT EXISTS booking(" +
                    "id integer primary key  autoincrement  not null  unique, " +
                    "timestamp datetime default current_timestamp, " +
                    "first_name varchar, " +
                    "last_name varchar, " +
                    "national_id varchar, " +
                    "phone varchar, " +
                    "location_id int, " +
                    "latitude real default 0.0, " +
                    "longitude real default 0.0, " +
                    "projected_date date, " +
                    "actual_date date, " +
                    "consent varchar, " +
                    "procedure_type_id integer, " +
                    "followup_id integer, " +
                    "followup_date date, " +
                    "alt_contact varchar, " +
                    "constraint name_constraint unique (first_name, last_name, national_id, phone, projected_date) );";
            db.execSQL(CREATE_BOOKING_TABLE);

            //try { db.execSQL("delete from group_activity;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
            String CREATE_GROUP_ACTIVITY_TABLE = "CREATE TABLE IF NOT EXISTS group_activity(" +
                    "id integer primary key  autoincrement  not null  unique, " +
                    "timestamp datetime default current_timestamp, " +
                    "name varchar, " +
                    "location_id int, " +
                    "activity_date date, " +
                    "group_type_id int, " +
                    "institution_id int, " +
                    "males int, " +
                    "females int, " +
                    "messages varchar, " +
                    "latitude real default 0.0, " +
                    "longitude real default 0.0, " +
                    "constraint name_constraint unique (name, group_type_id, activity_date) );";
            db.execSQL(CREATE_GROUP_ACTIVITY_TABLE);

            //try { db.execSQL("delete from user_type;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
            String CREATE_USER_TYPE_TABLE = "CREATE TABLE IF NOT EXISTS user_type(" +
                    "id integer primary key  autoincrement  not null  unique, " +
                    "name varchar);";
            db.execSQL(CREATE_USER_TYPE_TABLE);

            //try { db.execSQL("delete from group_type;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
            String CREATE_GROUP_TYPE_TABLE = "CREATE TABLE IF NOT EXISTS group_type(" +
                    "id integer primary key  autoincrement  not null  unique, " +
                    "name varchar);";
            db.execSQL(CREATE_GROUP_TYPE_TABLE);

            //try { db.execSQL("delete from acl;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
            String CREATE_ACL_TABLE = "CREATE TABLE IF NOT EXISTS acl(" +
                    "id varchar, " +
                    "acl varchar);";
            db.execSQL(CREATE_ACL_TABLE);

            //try { db.execSQL("delete from user_to_acl;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
            String CREATE_USER_TO_ACL_TABLE = "CREATE TABLE IF NOT EXISTS user_to_acl(" +
                    "id integer primary key  autoincrement  not null  unique, " +
                    "timestamp_created datetime default current_timestamp, " +
                    "acl_id varchar, " +
                    "user_id varchar, " +
                    "created_by varchar);";
            db.execSQL(CREATE_USER_TO_ACL_TABLE);

            //try { db.execSQL("delete from user;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
            String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS user(" +
                    "id integer primary key  autoincrement  not null  unique, " +
                    "timestamp datetime default current_timestamp, " +
                    "username varchar, " +
                    "password varchar, " +
                    "email varchar, " +
                    "first_name varchar, " +
                    "last_name varchar, " +
                    "national_id varchar, " +
                    "phone varchar, " +
                    "region_id int, " +
                    "user_type_id int, " +
                    "location_id int, " +
                    "modified_by int, " +
                    "created_by int, " +
                    "is_blocked int, " +
                    "timestamp_updated datetime default current_timestamp, " +
                    "timestamp_created datetime default '0000-00-00', " +
                    "timestamp_last_login datetime default '0000-00-00', " +
                    "constraint name_constraint unique (username, phone) );";
            db.execSQL(CREATE_USER_TABLE);

            //try { db.execSQL("delete from client;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
            String CREATE_CLIENT_TABLE = "CREATE TABLE IF NOT EXISTS client_table(" +
                    "id integer primary key  autoincrement  not null  unique, " +
                    "timestamp datetime default current_timestamp, " +
                    "first_name varchar, " +
                    "last_name varchar, " +
                    "national_id varchar, " +
                    "phone varchar, " +
                    "status_id int, " +
                    "loc_id int, " +
                    "latitude real default 0.0, " +
                    "longitude real default 0.0, " +
                    "institution_id int, " +
                    "group_activity_name varchar, " +
                    "group_activity_date varchar, " +
                    "fac_first_name varchar, " +
                    "fac_last_name varchar, " +
                    "fac_national_id varchar, " +
                    "fac_phone varchar, " +
                    "address_id int, " +
                    "dob date, " +
                    "gender varchar, " +
                    "origination varchar, " +
                    "created_by int default 0, " +
                    "modified_by int default 0, " +
                    "created varchar, " +
                    "modified varchar, " +
            "constraint name_constraint unique (first_name, last_name, national_id, phone) );";
            db.execSQL(CREATE_CLIENT_TABLE);

            //try { db.execSQL("delete from facilitator;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
            String CREATE_FACILITATOR_TABLE = "CREATE TABLE IF NOT EXISTS facilitator(" +
                    "id integer primary key  autoincrement  not null  unique, " +
                    "timestamp datetime default current_timestamp, " +
                    "first_name varchar, " +
                    "last_name varchar, " +
                    "national_id varchar, " +
                    "phone varchar, " +
                    "facilitator_type_id int, " +
                    "note varchar, " +
                    "location_id int, " +
                    "latitude real default 0.0, " +
                    "longitude real default 0.0, " +
                    "institution_id int, " +
                    "address_id int, " +
                    "dob date, " +
                    "gender varchar, " +
                    "constraint name_constraint unique (first_name, last_name, national_id, phone) );";
            db.execSQL(CREATE_FACILITATOR_TABLE);

            //try { db.execSQL("delete from location;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
            String CREATE_LOCATION_TABLE = "CREATE TABLE IF NOT EXISTS location(" +
                    "id integer primary key  autoincrement  not null  unique, " +
                    "name varchar, " +
                    "region_id int)";
            db.execSQL(CREATE_LOCATION_TABLE);

            //try { db.execSQL("delete from location;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
            String CREATE_ADDRESS_TABLE = "CREATE TABLE IF NOT EXISTS address(" +
                    "id integer primary key  autoincrement  not null  unique, " +
                    "name varchar, " +
                    "region_id int)";
            db.execSQL(CREATE_ADDRESS_TABLE);

            //try { db.execSQL("delete from region;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
            String CREATE_REGION_TABLE = "CREATE TABLE IF NOT EXISTS region(" +
                    "id integer primary key  autoincrement  not null  unique, " +
                    "name varchar)";
            db.execSQL(CREATE_REGION_TABLE);

            //try { db.execSQL("delete from region;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
            String CREATE_INSTITUTION_TABLE = "CREATE TABLE IF NOT EXISTS institution(" +
                    "id integer primary key  autoincrement  not null  unique, " +
                    "name varchar, " +
                    "region_id int)";
            db.execSQL(CREATE_INSTITUTION_TABLE);

            //try { db.execSQL("delete from constituency;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
            String CREATE_CONSTITUENCY_TABLE = "CREATE TABLE IF NOT EXISTS constituency(" +
                    "id integer primary key  autoincrement  not null  unique, " +
                    "name varchar, " +
                    "region_id int)";
            db.execSQL(CREATE_CONSTITUENCY_TABLE);

            //try { db.execSQL("delete from interaction;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
            String CREATE_INTERACTION_TABLE = "CREATE TABLE IF NOT EXISTS interaction(" +
                    "id integer primary key  autoincrement  not null  unique, " +
                    "timestamp datetime default current_timestamp, " +
                    "fac_first_name varchar, " +
                    "fac_last_name varchar, " +
                    "fac_national_id varchar, " +
                    "fac_phone varchar, " +
                    "person_first_name varchar, " +
                    "person_last_name varchar, " +
                    "person_national_id varchar, " +
                    "person_phone varchar, " +
                    "interaction_date date, " +
                    "followup_date date, " +
                    "type_id int, " +
                    "note varchar, " +
                    "constraint name_constraint unique (fac_first_name, fac_last_name, fac_national_id, fac_phone, person_first_name, person_last_name, person_national_id, person_phone, interaction_date, type_id ) );";
            db.execSQL(CREATE_INTERACTION_TABLE);

            //try { db.execSQL("delete from interaction_type;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
            String CREATE_INTERACTION_TYPE_TABLE = "CREATE TABLE IF NOT EXISTS interaction_type(" +
                    "id integer primary key  autoincrement  not null  unique, " +
                    "name varchar);";
            db.execSQL(CREATE_INTERACTION_TYPE_TABLE);

            //try { db.execSQL("delete from facilitator_type;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
            String CREATE_FACILITATOR_TYPE_TABLE = "CREATE TABLE IF NOT EXISTS facilitator_type(" +
                    "id integer primary key  autoincrement  not null  unique, " +
                    "name varchar);";
            db.execSQL(CREATE_FACILITATOR_TYPE_TABLE);

            //try { db.execSQL("delete from procedure_type;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
            String CREATE_PROCEDURE_TYPE_TABLE = "CREATE TABLE IF NOT EXISTS procedure_type(" +
                    "id integer primary key  autoincrement  not null  unique, " +
                    "name varchar);";
            db.execSQL(CREATE_PROCEDURE_TYPE_TABLE);

            //try { db.execSQL("delete from followup;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
            String CREATE_FOLLOWUP_TABLE = "CREATE TABLE IF NOT EXISTS followup(" +
                    "id integer primary key  autoincrement  not null  unique, " +
                    "name varchar);";
            db.execSQL(CREATE_FOLLOWUP_TABLE);

            //try { db.execSQL("delete from status_type;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
            String CREATE_STATUS_TYPE_TABLE = "CREATE TABLE IF NOT EXISTS status_type(" +
                    "id integer primary key  autoincrement  not null  unique, " +
                    "name varchar);";
            db.execSQL(CREATE_STATUS_TYPE_TABLE);

            //try { db.execSQL("delete from geolocation;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
            String CREATE_GEOLOCATION_TABLE = "CREATE TABLE IF NOT EXISTS geolocation(" +
                    "geolocations_id integer primary key  autoincrement  not null  unique, " +
                    "lat real, " +
                    "long real, " +
                    "device_id varchar, " +
                    "created_at datetime default current_timestamp, " +
                    "username varchar, " +
                    "password varchar);";
            db.execSQL(CREATE_GEOLOCATION_TABLE);


        } catch (Exception ex) {
            Log.d(LOG, "DBHelper.onCreate catch" + ex.toString());
        }
    }

    public void doCreateDB() {
        Log.d(LOG, "DBHelper.doCreateDB");
        SQLiteDatabase db = this.getReadableDatabase();
        onCreate(db);
//        load_facilitator_type();
//        load_interaction_type();
    }

    public static void importDB() {
        try {
            Log.d(LOG, "importDB0");
            final String inFileName = "/data/data/org.itech.vmmc/databases/vmmc.db.bu";
            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);

            String outFileName = "/data/data/org.itech.vmmc/databases/vmmc.db";
            Log.d(LOG, "importDB1");

            OutputStream output = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer))>0){
                output.write(buffer, 0, length);
            }
            Log.d(LOG, "importDB2");

            output.flush();
            output.close();
            fis.close();

            Log.d(LOG, "importDB:restore successful");
//                Toast.makeText(MyApplication.getAppContext(), "Import Successful!",Toast.LENGTH_SHORT).show();

        } catch (Exception e) {

            Log.d(LOG, "importDB:restore failed" + e.toString());
//            Toast.makeText(MyApplication.getAppContext(), "Import Failed!", Toast.LENGTH_SHORT).show();

        }
    }

    public static void exportDB() {
        try {
            Log.d(LOG, "exportDB0");
            final String inFileName = "/data/data/org.itech.vmmc/databases/vmmc.db";
            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);

            String outFileName = inFileName+".bu";
//            Log.d(LOG, "exportDB1");

            OutputStream output = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer))>0){
                output.write(buffer, 0, length);
            }
//            Log.d(LOG, "exportDB2");

            output.flush();
            output.close();
            fis.close();

            Log.d(LOG, "exportDB:backup successful");
//            Toast.makeText(this._context, "Backup Successful!", Toast.LENGTH_SHORT).show();


        } catch (Exception e) {
            Log.d(LOG, "exportDB:backup failed" + e.toString());
//            Toast.makeText(MyApplication.getAppContext(), "Backup Failed!" + e.toString(), Toast.LENGTH_SHORT).show();

        }
    }

    public void doSyncDB() {
        Log.d(LOG, "DBHelper.doSyncDB");
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);

        this.deleteAllSyncAudit();
        SyncAudit syncAudit = new SyncAudit();
        syncAudit.set_progress("Start"); this.addSyncAudit(syncAudit);


//        new putMySQLBookingTable(this).execute();
//        new putMySQLClientTable(this).execute();
//        new putMySQLFacilitatorTable(this).execute();

//        new putMySQLGroupActivityTable(this).execute();
//        new putMySQLUserTable(this).execute();
//        new getMySQLRegionTable(this._context, this).execute();
//        new getMySQLLocationTable(this._context, this).execute();
//        new getMySQLAddressTable(this._context, this).execute();
//        new getMySQLFacilitatorTypeTable(this._context, this).execute();
//        new getMySQLProcedureTypeTable(this._context, this).execute();
//        new getMySQLFollowupTable(this._context, this).execute();
//        new getMySQLStatusTypeTable(this._context, this).execute();
//        new getMySQLInstitutionTable(this._context, this).execute();

//        new getMySQLBookingTable(this._context, this).execute();
//        new getMySQLClientTable(this._context, this).execute();
//        new getMySQLFacilitatorTable(this._context, this).execute();
//        new getMySQLInteractionTable(this._context, this).execute();
//        new getMySQLUserTable(this._context, this).execute();
//        new getMySQLUserTypeTable(this._context, this).execute();
//        new getMySQLAclTable(this._context, this).execute();
//        new getMySQLUserToAclTable(this._context, this).execute();
//        new getMySQLGroupTypeTable(this._context, this).execute();
//        new getMySQLGroupActivityTable(this._context, this).execute();

//        new putMySQLSyncAuditTable(this).execute();

        putMySQLTableVolley tablePutter = new putMySQLTableVolley(this._context, this);
        tablePutter.putAllTables();

        getMySQLTableVolley tableGetter = new getMySQLTableVolley(this._context, this);
        tableGetter.getAllTables();

        new SyncAuditThread(tablePutter).execute();

        Log.d(LOG, "DBHelper after sync: _username: " + MainActivity._username );

        if (MainActivity._username == "sync@"){
            User _user = new User();
            _user.set_username(MainActivity._username);
            _user.set_password(MainActivity._password);
            _user.set_email(MainActivity._username);
            _user.set_user_type_id(1);
            _user.set_region_id(1);
            this.addUser(_user);

        }

        Toast.makeText(this._context, this._context.getResources().getString(R.string.sync_complete), Toast.LENGTH_LONG).show();
    }

    public boolean deleteAllSyncAudit(){
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            // public int delete(String table, String whereClause, String[] whereArgs) {
            String[] whereArgs = new String[]{ "" };
            db.delete(TABLE_SYNC_AUDIT, "1=1", null);
        } catch (Exception ex) {
            // db.close();
            Log.d(LOG, "deleteSyncAudit catch " + ex.toString());
            return false;
        }
        return true;
    }


    public boolean addSyncAudit(SyncAudit syncAudit){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        Calendar calendar = Calendar.getInstance();
        Timestamp oTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());

        values.put(SYNC_AUDIT_TIMESTAMP, oTimestamp.toString());
        values.put(SYNC_AUDIT_LONGITUDE, syncAudit.get_longitude());
        values.put(SYNC_AUDIT_LATITUDE, syncAudit.get_latitude());
        values.put(SYNC_AUDIT_DEVICE_ID, MainActivity.deviceId);
        values.put(SYNC_AUDIT_USERNAME, MainActivity._username);
        values.put(SYNC_AUDIT_PASSWORD, MainActivity._password);
        values.put(SYNC_AUDIT_PROGRESS, syncAudit.get_progress());
        values.put(SYNC_AUDIT_STATUS, syncAudit.get_status());

        try {
            db.insert(TABLE_SYNC_AUDIT, null, values);
        } catch (Exception ex) {
            // db.close();
            Log.d(LOG, "addSyncAudit catch " + ex.toString());
            return false;
        }
        return true;
    }

    public boolean addSyncAudit(String _progress, String _status){
        SQLiteDatabase db = this.getWritableDatabase();

        SyncAudit syncAudit = new SyncAudit();
        syncAudit.set_progress(_progress);
        syncAudit.set_status(_status);
        ContentValues values = new ContentValues();
        Calendar calendar = Calendar.getInstance();
        Timestamp oTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());

        values.put(SYNC_AUDIT_TIMESTAMP, oTimestamp.toString());
        values.put(SYNC_AUDIT_LONGITUDE, syncAudit.get_longitude());
        values.put(SYNC_AUDIT_LATITUDE, syncAudit.get_latitude());
        values.put(SYNC_AUDIT_DEVICE_ID, MainActivity.deviceId);
        values.put(SYNC_AUDIT_USERNAME, MainActivity._username);
        values.put(SYNC_AUDIT_PASSWORD, MainActivity._password);
        values.put(SYNC_AUDIT_PROGRESS, syncAudit.get_progress());
        values.put(SYNC_AUDIT_STATUS, syncAudit.get_status());

        try {
            db.insert(TABLE_SYNC_AUDIT, null, values);
        } catch (Exception ex) {
            // db.close();
            Log.d(LOG, "addSyncAudit catch " + ex.toString());
            return false;
        }
        return true;
    }



    public User getUser(String username, String password, String phone){
        User _user = null;
        Log.d(LOG, "DBHelper.getUser");
        SQLiteDatabase db = this.getReadableDatabase();

        String[] tableColumns = new String[] {
                USER_ID, USER_TIMESTAMP, USER_USERNAME, USER_PASSWORD, USER_EMAIL, USER_FIRST_NAME, USER_LAST_NAME, USER_NATIONAL_ID, USER_PHONE, USER_REGION_ID, USER_USER_TYPE_ID, USER_LOCATION_ID, USER_MODIFIED_BY, USER_CREATED_BY, USER_IS_BLOCKED, USER_TIMESTAMP_UPDATED, USER_TIMESTAMP_CREATED, USER_TIMESTAMP_LAST_LOGIN
        };

        String whereClause = "trim(" +
                USER_USERNAME + ") like ? and trim(" +
                USER_PASSWORD + ") like ? and trim(" +
                USER_PHONE + ") like ? "
                ;

        String[] whereArgs = new String[]{ username.replaceAll("'","''"), password, phone.replaceAll("'","''") };

        Cursor cursor = db.query(TABLE_USER, tableColumns, whereClause, whereArgs, null, null, null);

        if (cursor.moveToFirst()) {
            Log.d(LOG, "getUser  "
                    + cursor.getString(2) + ", "
                    + cursor.getString(8)
            );
            _user = new User(
                    parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    parseInt(cursor.getString(9)),
                    parseInt(cursor.getString(10)),
                    parseInt(cursor.getString(11)),
                    parseInt(cursor.getString(12)),
                    parseInt(cursor.getString(13)),
                    parseInt(cursor.getString(14)),
                    cursor.getString(15),
                    cursor.getString(16),
                    cursor.getString(17)
            );

            cursor.close();
            // db.close();
            return _user;
        } else {
            cursor.close();
            return _user;
        }
    }


    public boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(PERSON_ID, person.get_id());
        Calendar calendar = Calendar.getInstance();
        Timestamp oTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
        values.put( USER_TIMESTAMP, oTimestamp.toString());
        values.put( USER_USERNAME, user.get_username().replaceAll("'","''"));
        values.put( USER_PASSWORD, user.get_password());
        values.put( USER_EMAIL, user.get_email());
        values.put( USER_FIRST_NAME, user.get_first_name());
        values.put( USER_LAST_NAME, user.get_last_name());
        values.put( USER_NATIONAL_ID, user.get_national_id());
        values.put( USER_PHONE, user.get_phone().replaceAll("'","''"));
        values.put( USER_REGION_ID, user.get_region_id());
        values.put( USER_USER_TYPE_ID, user.get_user_type_id());
        values.put( USER_LOCATION_ID, user.get_location_id());
        values.put( USER_MODIFIED_BY, user.get_modified_by());
        values.put( USER_CREATED_BY, user.get_created_by());
        values.put( USER_IS_BLOCKED, user.get_is_blocked());
        values.put( USER_TIMESTAMP_UPDATED, user.get_timestamp_updated());
        values.put( USER_TIMESTAMP_CREATED, user.get_timestamp_created());
        values.put( USER_TIMESTAMP_LAST_LOGIN, user.get_timestamp_last_login());

        try {
            db.insert(TABLE_USER, null, values);
            DBHelper.exportDB();
        } catch (Exception ex) {
            // db.close();
            Log.d(LOG, "addUser catch " + ex.toString());
            return false;
        }

        return true;
    }

    public ArrayList<String> getUserPerms(String username){
        ArrayList<String> _userPerms = new ArrayList<String>();
        Log.d(LOG, "DBHelper.getUserPerms");
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select replace(uta.acl_id, 'status_', '') " +
                "from user u " +
                "join user_type ut on u.user_type_id = ut.id " +
                "join user_to_acl uta on uta.user_id = u.id " +
                "where 1=1 " +
                "and u.username = ? " +
                "and ut.id = u.user_type_id " +
                "and uta.acl_id not like 'status_%'; ";

        Cursor cursor = db.rawQuery(query, new String[]{ username });

        if (cursor.moveToFirst()) {
            do {
                Log.d(LOG, "getUserPerms  "
                        + cursor.getString(0)
                );
                _userPerms.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        // db.close();

        // remove duplicates
        Set<String> noDups = new LinkedHashSet<>(_userPerms);
        _userPerms.clear();
        _userPerms.addAll(noDups);

        return _userPerms;
    }

    public ArrayList<String> getUserStatusList(String username){
        ArrayList<String> _userStatusList = new ArrayList<String>();
        Log.d(LOG, "DBHelper.getUserStatusList");
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select replace(uta.acl_id, 'status_', '') " +
                "from user u " +
                "join user_type ut on u.user_type_id = ut.id " +
                "join user_to_acl uta on uta.user_id = u.id " +
                "where 1=1 " +
                "and u.username = ? " +
                "and ut.id = u.user_type_id " +
                "and uta.acl_id like 'status_%'; ";

        Cursor cursor = db.rawQuery(query, new String[]{ username });

        if (cursor.moveToFirst()) {
            do {
                Log.d(LOG, "getUserStatusList  "
                        + cursor.getString(0)
                );
                _userStatusList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        // db.close();

        // remove duplicates
        Set<String> noDups = new LinkedHashSet<>(_userStatusList);
        _userStatusList.clear();
        _userStatusList.addAll(noDups);

        return _userStatusList;
    }

    public void doTestDB() {
        Log.d(LOG, "DBHelper.doTestDB");
        SQLiteDatabase db = this.getReadableDatabase();
        onCreate(db);

        DBHelper dbHelp = new DBHelper(_context);
        List<User> userList = dbHelp.getAllUsers();
        for (User user: userList) {
            Log.d(LOG, "DBHelper.doTestDB:username: " + user.get_username());
        }

//        String _credentials = "a@:pa";
//        Log.d(LOG, "DBHelper.doTestDB:user/pass " + MainActivity._user + "/" + MainActivity._pass);
//        Log.d(LOG, "DBHelper.doTestDB:username/password " + MainActivity._username + "/" + MainActivity._password);
//        User _user = new User(dbHelp,MainActivity._username + ":" + MainActivity._password);


//        if (!MainActivity._username.equals("sync@")) {
//            User _user = new User(dbHelp, MainActivity._username + ":" + MainActivity._password);
//            Log.d(LOG, "DBHelper.doTestDB:username/password: " + _user.get_username() + ", " + _user.get_password());
//            Log.d(LOG, "DBHelper.doTestDB:_user._user_type_id: " + _user._user_type_id);
//            Log.d(LOG, "DBHelper.doTestDB:_user._region_id: " + _user._region_id);
//
//            int i = 0;
//            do {
//                Log.d(LOG, "DBHelper.doTestDB:_user.userPerms: " + _user.userPerms.get(i));
//            } while(i++ < _user.userPerms.size()-1);
//
//             i = 0;
//            do {
//                Log.d(LOG, "DBHelper.doTestDB:_user.userStatusList: " + _user.userStatusList.get(i));
//            } while(i++ < _user.userStatusList.size()-1);

//            if (_user.userPerms.contains("edit_group")) {
//                Log.d(LOG, "actionFragment:edit_group");
//            }
//            if (_user.userPerms.contains("edit_not")) {
//                Log.d(LOG, "actionFragment:edit_not");
//            }
//            if (_user.userPerms.contains("edit_recruiter")) {
//                Log.d(LOG, "actionFragment:edit_recruiter");
//            }
//            if (_user.userStatusList.contains("status_Lost")) {
//                Log.d(LOG, "actionFragment:status_Lost");
//            }
//        }


//        load_facilitator_type();
//        load_interaction_type();
//        load_person();
//        new putMySQLPersonTable(this).execute();
//        new getMySQLPersonTable(this._context, this).execute();
//        display_bookings();

//        List<Client> _list = getAllClients();

//        List<Client> clientList = new ArrayList<Client>();
//        String selectQuery = "SELECT  * FROM " + TABLE_CLIENT + " order by first_name, last_name ";
//
//        Cursor cursor1 = null;
//
//        cursor1 = db.rawQuery(selectQuery, null);
//            cursor1.moveToFirst();
//try {
//    Log.d(LOG, "getAllClients: " + cursor1.getString(1) + ":" + cursor1.getString(2) + ":" + cursor1.getString(3) + ":" + cursor1.getString(4) + ":");
//} catch (Exception ex){
//    Log.d(LOG, "Exception: " + ex);
//}
//
//        if (cursor1.moveToFirst()) {
//            do {
//                Log.d(LOG, "getAllClients: loop: " + cursor1.getString(1));
//                Client client = new Client();
//                client.set_id(parseInt(cursor1.getString(0)));
//                client.set_timestamp(cursor1.getString(1));
//                client.set_first_name(cursor1.getString(2));
//                client.set_last_name(cursor1.getString(3));
//                client.set_national_id(cursor1.getString(4));
//                client.set_phone(cursor1.getString(5));
//                client.set_status_id(parseInt(cursor1.getString(6)));
//                client.set_loc_id(parseInt(cursor1.getString(7)));
//                client.set_latitude(parseFloat(cursor1.getString(8)));
//                client.set_longitude(parseFloat(cursor1.getString(9)));
//                client.set_institution_id(parseInt(cursor1.getString(10)));
//
//                // Adding person to list
//                clientList.add(client);
//            } while (cursor1.moveToNext());
//        }
//        cursor1.close();
    }

    protected void display_bookings() {
        Log.d(LOG, "DebugFrag:display_bookings");

        SQLiteDatabase db = this.getWritableDatabase();
        List<Booking> BookingList = getAllBookings();
        int i=0;
        String[] recs = new String[BookingList.size()];
        for (Booking Booking: BookingList) {
            recs[i] =
                    Booking.get_timestamp() + "," +
                            Booking.get_first_name() + "," +
                            Booking.get_last_name() + "," +
                            Booking.get_national_id() + "," +
                            Booking.get_phone() + "," +

                            Booking.get_location_id() + "," +
                            Booking.get_projected_date() + "," +
                            Booking.get_actual_date();

//            Log.d(LOG, "loop: " + recs[i] + "<");
            i++;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSON);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FACILITATOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONSTITUENCY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FACILITATOR_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INTERACTION_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GEOLOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTITUTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INTERACTION);

        // Create tables again
        onCreate(db);
    }

    protected void load_person() {
        Log.d(LOG, "debugFragment:load_person");

        SQLiteDatabase db = this.getWritableDatabase();
        // db.execSQL("delete from person ");
        Person person0 = new Person("A","B", "n1",1,"12","0000-00-00","g",1.100000023841858,2.200000047683716,0); addPerson(person0);
        Person person1 = new Person("C","D", "n2",1,"34","0000-00-00","g",1.100000023841858,2.200000047683716,0); addPerson(person1);
        Person person2 = new Person("E","F", "n3",1,"56","0000-00-00","g",1.100000023841858,2.200000047683716,0); addPerson(person2);

        Person person3 = getPerson("A","B", "p");
        Log.d(LOG, "debugFragment:load_person:personAB: " +
                person3.get_first_name() + " " +
                person3.get_last_name() + " " +
                person3.get_national_id() + " " +
                person3.get_address_id() + " " +
                person3.get_phone() + " " +
                person3.get_dob() + " " +
                person3.get_gender() + " " +
                person3.get_latitude() + " " +
                person3.get_longitude() + " " +
                person3.get_is_deleted());

        Person person4 = getPerson(2);
        if(person4 != null) {
            Log.d(LOG, "debugFragment:load_person:person2: " +
                    person4.get_first_name() + " " +
                    person4.get_last_name() + " " +
                    person4.get_national_id() + " " +
                    person4.get_address_id() + " " +
                    person4.get_phone() + " " +
                    person4.get_dob() + " " +
                    person4.get_gender() + " " +
                    person4.get_latitude() + " " +
                    person4.get_longitude() + " " +
                    person4.get_is_deleted());
        } else {
            Log.d(LOG, "person 2 not found");
        }


        //db.execSQL("insert into person values (1,\"A\", \"B\", \"N\", \"A\", \"P\", \"dob\", \"G\", 1.1, 2.2, 0 );");
        //db.execSQL("insert into person values (2,\"C\", \"D\", \"N\", \"A\", \"P\", \"dob\", \"G\", 1.1, 2.2, 0 );");
        //db.execSQL("insert into person values (3,\"E\", \"F\", \"N\", \"A\", \"P\", \"dob\", \"G\", 1.1, 2.2, 0 );");

    }
    protected void load_facilitator_type() {
        Log.d(LOG, "debugFragment:load_facilitator_type");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from facilitator_type ");
        db.execSQL("insert into facilitator_type values (1,\"Mobilizer\");");
        db.execSQL("insert into facilitator_type values (2,\"User\");");
        db.execSQL("insert into facilitator_type values (2,\"Activity\");");

    }
    protected void load_interaction_type() {
        Log.d(LOG, "debugFragment:load_interaction_type");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from interaction_type ");
        db.execSQL("insert into interaction_type values (1,\"phone call\");");
        db.execSQL("insert into interaction_type values (2,\"home visit\");");
        db.execSQL("insert into interaction_type values (2,\"clinic visit\");");


    }

    public void helperTest(){

        Log.d(LOG, "helperTest0 ");

        try {

//            String databaseName = this.getDatabaseName();
//            //SQLiteDatabase db = this.getReadableDatabase();
//            //SQLiteDatabase db = this.getWritableDatabase();
//            Person person0 = this.getPerson("Greg", "Rossum", "%");
//
//            //db.execSQL("drop table assessments");
//            //db.execSQL("drop table person");
//            //db.execSQL("drop table person_to_assessments");
//            //db.execSQL("drop table assessments_answers");
//            //db.execSQL("drop table assessments_questions");
//            Log.d(LOG, "helperTest databaseName> " + databaseName );
//            Log.d(LOG, "helperTest person0> "
//                            + person0._rowid + " "
//                            + person0._person_id + " "
//                            + person0._first_name + " "
//                            + person0._last_name + " "
//                            + person0._national_id + " "
//                            + person0._facility_id + " "
//                            + person0._facility_name
//            );
//
//            Person person1 = new Person(999, "First", "Last", "national_id", 3, "All Souls' Clinic");
//            boolean _success = this.addPerson(person1);
//
//            List<Person> personList = this.getAllPersons();
//            for (Person p : personList) {
//                Log.d(LOG, "helperTest personList> "
//                                //+ p._rowid + " "
//                                + p._person_id + " "
//                                + p._first_name + " "
//                                + p._last_name + " "
//                                + p._national_id + " "
//                                + p._facility_id + " "
//                                + p._facility_name
//                );
//            }
//
//            Log.d(LOG, "helperTest personCount> " + this.getPersonsCount());
//
//            PersonToAssessments pa = new PersonToAssessments();
//            pa = this.getPersonToAssessments(1);
//            Log.d(LOG, "pa.get_assessment_id > " + pa.get_assessment_id());
//
//            Assessments assessment = new Assessments();
//            assessment = getAssessments(pa.get_assessment_id());
//            Log.d(LOG, "assessment.get_assessment_type > " + assessment.get_assessment_type());
////            assessment = getAssessments(2);
//
//
//            Person person2 = new Person();
//            person2 = this.getPerson(pa.get_person_id());
////            person2 = this.getPerson(1);
//
//            List<EditPageObject> editPageObjectList = this.getEditPageData( pa );
////            for (EditPageObject epo : editPageObjectList) {
////                Log.d(LOG, "helperTest editPageObjectList > "
////                                //+ editPageObjectList._rowid + " "
////                                + epo._assessments_questions_id + " "
////                                + epo._question + " "
////                                + epo._itemtype + " "
////                                + epo._itemorder + " "
////                                + epo._answer + " "
////                );
////            }
//            for (EditPageObject epo : editPageObjectList) {
//                Log.d(LOG, "question and answer: " + epo.get_question() + " " + epo.get_answer());
//                //epo.set_answer("new "+ epo.get_answer());
//            }
//
//            setEditPageData(pa, editPageObjectList); //  insert/update answers
//
//            Log.d(LOG, "helperTest personCount> " + this.getPersonsCount());
//
////            if(assessmentsAnswers != null)
////                Log.d(LOG, "helperTest assessmentsAnswers "
////                        + assessmentsAnswers._assess_id + " "
////                        + assessmentsAnswers._person + " "
////                        + assessmentsAnswers._facility + " "
////                        + assessmentsAnswers._date_created + " "
////                        + assessmentsAnswers._assessment_id + " "
////                        + assessmentsAnswers._question + " "
////                        + assessmentsAnswers._answer);
////            else
////                Log.d("request1", "assessments_answers record not found");
//
//            this.updateAssessmentsAnswers(1, 1, "2015-07-07", 2, 22, Integer.toString((getPersonsCount())));
//            AssessmentsAnswers assessmentsAnswers1 = this.getAssessmentsAnswers(1, 1, "2015-07-07", 2, 22);
//            Log.d(LOG, "helperTest assessmentsAnswers1 " + assessmentsAnswers1.get_answer());
//            this.updateAssessmentsAnswers(assessmentsAnswers1.get_assess_id(), Integer.toString((getPersonsCount() + 1)));
//            AssessmentsAnswers assessmentsAnswers2 = this.getAssessmentsAnswers(1, 1, "2015-07-07", 2, 22);
//            Log.d(LOG, "helperTest assessmentsAnswers2 " + assessmentsAnswers2.get_answer());
//            this.insertAssessmentsAnswers(0, 1, "2015-07-07", 2, 22, "this is a new answer");
//            AssessmentsAnswers assessmentsAnswers3 = this.getAssessmentsAnswers(0, 1, "2015-07-07", 2, 22);
//            AssessmentsAnswers assessmentsAnswers4 = this.getAssessmentsAnswers(assessmentsAnswers3.get_assess_id());
//            Log.d(LOG, "helperTest inserted new assess_id from insert: " +  assessmentsAnswers3.get_assess_id());
//            Log.d(LOG, "helperTest inserted new answer from insert: " +  assessmentsAnswers4.get_answer());
//            this.deleteAssessmentsAnswers(0, 1, "2015-07-07", 2, 22);


            // if you know it's there
//            PersonToAssessments personToAssessments0 = this.getPersonToAssessments(1, 1, "2015-09-15", 2);
//
//            // if you're checking if it's there
//            try {
//                PersonToAssessments personToAssessments1 = this.getPersonToAssessments(1, 1, "not_found", 2);
//            } catch (Exception ex) { Log.d(LOG, "helperTest getPersonToAssessments not found");}
//
//            List<PersonToAssessments> personToAssessmentsList = this.getAllPersonToAssessments();
//            for (PersonToAssessments poa: personToAssessmentsList) { poa.dump(); }


            // returns "person_id_last_name first_name national_id facility_name"
            //  split off person_id using "_", might have to use hashmap with person_id as key and rest as value
            //  sorted by last, first, national




//            List<String> allPersonID = getAllPersonIDs();
//            String parts[] = {};
//            for (String personID : allPersonID){
//                parts = personID.split("_");
//                Log.d(LOG, "person_id: " + parts[0] + " last, first, national, facility: " + parts[1]);
//            }

//            // returns unique sorted facility_names
//            String[] allFacilityNames = {""};
//            allFacilityNames = getAllFacilityNames();
//            for (String facility_name : allFacilityNames){
//                Log.d(LOG, "facility_name: " + facility_name);
//            }

//            // returns unique sorted national_ids
//            String[] allNationalIDs = {""};
//            allNationalIDs = getAllNationalIDs();
//            for (String nationalId : allNationalIDs){
//                    Log.d(LOG, "national_id: " + nationalId);
//            }

//            // returns Person object based on first_name, last_name, national_id
//            Person person0 = this.getPerson("Greg", "Rossum", "%");
//            Log.d(LOG, "helperTest person0> "
//                            //+ person0._rowid + " "
//                            + person0.get_person_id() + " "
//                            + person0.get_first_name() + " "
//                            + person0.get_last_name() + " "
//                            + person0.get_national_id() + " "
//                            + person0.get_facility_id() + " "
//                            + person0.get_facility_name()
//            );


            // returns all Person objects
//            List<Person> personList = this.getAllPersons();
//            for (Person p : personList) {
//
//                Log.d(LOG, " first_last_name: " +
//                                p.get_first_name() + " " + p.get_last_name() + " " +
//                                p.get_national_id() + " " +
//                                p.get_facility_name()
//                );
//            }
//            int question =  this.getAssessmentsQuestionsQuestion(2, 26);
//            Log.d(LOG, "question: " + question);
            Log.d(LOG, "helperTest Done");

        } catch (Exception ex) {
            Log.d(LOG, "helperTest catch " + ex.toString());
        }
    }

    public boolean getUserAccess(String acl){
        Log.d(LOG, "DBHelper.getUserAccess");
        SQLiteDatabase db = this.getReadableDatabase();
        User _user = new User(this, MainActivity._username + ":" + MainActivity._password);

        boolean _permission = false;
        int i = 0;
        do {
            if(_user.userPerms.size() == 0 ) { _permission = false; break; }
            else if (_user.userPerms.get(i).equalsIgnoreCase(acl)) { _permission = true; }
        } while(i++ < _user.userPerms.size()-1);

        UserType _userType = getUserType(Integer.toString(_user.get_user_type_id()));
        if(_userType.get_name().equalsIgnoreCase("admin")) { _permission = true; }

        return _permission;
    }

    public ArrayList<String> getCredentials(){
        Log.d(LOG, "DBHelper.getCredentials");
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> credentials = new ArrayList<String>();

        String[] tableColumns = new String[] {
                USER_USERNAME, USER_PASSWORD
        };

        String whereClause = "1=1 ";
        whereClause = whereClause + "and " + USER_IS_BLOCKED + " = 0 ";

        String[] whereArgs = new String[]{};

        Cursor cursor = db.query(TABLE_USER, tableColumns, whereClause, whereArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Log.d(LOG, "getCredentials  "
                        + cursor.getString(0)
                        + cursor.getString(1)
                );
                credentials.add(cursor.getString(0) + ":" + cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        // db.close();

        // remove duplicates
        Set<String> noDups = new LinkedHashSet<>(credentials);
        credentials.clear();
        credentials.addAll(noDups);

        return credentials;
    }

    public ArrayList<String> getDropdownOptions(int assessments_question_id) {
//        Log.d(LOG, "getDropdownOptions: ");
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> dropdownOptionsList = new ArrayList<String>();
        String whereClause = "";
        whereClause = whereClause + "and assessment_question_id = " + assessments_question_id + " ";

        String query =
                "select " +
                        "dropdown_option " +
                        "from question_dropdown_option " +
                        "where  1=1 " +
                        whereClause;

//    Log.d(LOG, "Query: " + query);
        Cursor cursor = db.rawQuery(query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String dropdownOptions = "";
                dropdownOptions =
                        cursor.getString(0);

                // Adding object to list
                dropdownOptionsList.add(dropdownOptions);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // db.close();
        // return list
        return dropdownOptionsList;
    }

    public List<String> getReadableAssessments(String person_id, String national_id, String facility_name, String assessment_type, String from_date, String to_date) {
        List<String> readableRecentAssessmentsList = new ArrayList<String>();
        Log.d(LOG, "readableRecentAssessments: ");
        // all params can be null or ""
        String whereClause = "";

//        if (person == null) {
//            Log.d(LOG, "Search button person is null: ");
//        } else {
//            Log.d(LOG, "Search button person: " + person.get_person_id());
//        }
//        if (assessment == null) {
//            Log.d(LOG, "Search button assessment is null: ");
//        } else {
//            Log.d(LOG, "Search button assessment: " + assessment.get_assessment_type());
//        }
//        if (facility_name.equals("")) {
//            Log.d(LOG, "Search button facility is null: ");
//        } else {
//            Log.d(LOG, "Search button facilityName: " + facility_name);
//        }
//        if (national_id.equals("")) {
//            Log.d(LOG, "Search button nationalID is null: ");
//        } else {
//            Log.d(LOG, "Search button nationalID: " + national_id);
//        }

        if (person_id.equals("")) {
            Log.d(LOG, "person is null: ");
        } else {
            Log.d(LOG, "person: " + person_id);
            whereClause = whereClause + "and p.person_id = " + person_id + " ";
        }
        if (assessment_type.equals("")) {
            Log.d(LOG, "assessment is null: ");
        } else {
            Log.d(LOG, "assessment: " + assessment_type);
            whereClause = whereClause + "and a.assessment_type = '" + assessment_type + "' ";
        }
        if (facility_name.equals("")) {
            Log.d(LOG, "facility is null: ");
        } else {
            Log.d(LOG, "facilityName: " + facility_name);
            // test for single quote
            StringBuilder name = new StringBuilder(facility_name);
            if(facility_name.indexOf("'") != -1) {
                name.setCharAt(facility_name.indexOf("'"), '_');
            }
            whereClause = whereClause + "and p.facility_name like '" + name + "' ";
        }
        if (national_id.equals("")) {
            Log.d(LOG, "nationalID is null: ");
        } else {
            Log.d(LOG, "nationalID: " + national_id);
            // test for single quote
            StringBuilder id = new StringBuilder(national_id);
            if(national_id.indexOf("'") != -1) {
                id.setCharAt(facility_name.indexOf("'"), '_');
            }
            whereClause = whereClause + "and p.national_id like '" + id + "' ";
        }
        if (from_date.equals("")||to_date.equals("")) {
            Log.d(LOG, "from_date || to_date is null: ");
        } else {
            Log.d(LOG, "from_date: " + from_date);
            Log.d(LOG, "to_date: " + to_date);
            whereClause = whereClause + "and ptoa.date_created between '" + from_date + "' and '" + to_date + " ";
        }

        String query =
                "select " +
                        "ptoa.person_to_assessments_id, " +
                        "p.first_name, " +
                        "p.last_name, " +
                        "p.facility_name, " +
                        "ptoa.date_created, " +
                        "a.assessment_type " +
                        "from person_to_assessments ptoa " +
                        "join person p on p.person_id = ptoa.person_id " +
                        "join assessments a on ptoa.assessment_id = a.assessment_id " +
                        "where  1=1 " +
                        whereClause +
                        "order by ptoa.person_to_assessments_id desc " +
                        "limit 20";

        Log.d(LOG, "Query: " + query);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String readableRecentAssessment = "";
                readableRecentAssessment =
                        cursor.getString(0) + ") " +
                                cursor.getString(1) + " " +
                                cursor.getString(2) + " " +
                                cursor.getString(3) + " " +
                                cursor.getString(4) + "\r\n\t" +
                                cursor.getString(5);

                // Adding object to list
                readableRecentAssessmentsList.add(readableRecentAssessment);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // db.close();
        // return person list
        return readableRecentAssessmentsList;
    }

    public List<String> getReadableRecentAssessments() {
        List<String> readableRecentAssessmentsList = new ArrayList<String>();
        Log.d(LOG, "readableRecentAssessments: ");
        String query =
                "select " +
                        "ptoa.person_to_assessments_id, " +
                        "p.first_name, " +
                        "p.last_name, " +
                        "p.facility_name, " +
                        "ptoa.date_created, " +
                        "a.assessment_type " +
                        "from person_to_assessments ptoa " +
                        "join person p on p.person_id = ptoa.person_id " +
                        "join assessments a on ptoa.assessment_id = a.assessment_id " +
                        "where  1=1 " +
                        "order by ptoa.person_to_assessments_id desc " +
                        "limit 20";

        Log.d(LOG, "Query: " + query);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String readableRecentAssessment = "";
                readableRecentAssessment =
                        cursor.getString(0) + ") " +
                                cursor.getString(1) + " " +
                                cursor.getString(2) + "\r\n\t" +
                                cursor.getString(4) + " " +
                                cursor.getString(5) + "\r\n\t" +
                                cursor.getString(3);

                // Adding object to list
                readableRecentAssessmentsList.add(readableRecentAssessment);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // db.close();
        // return person list
        return readableRecentAssessmentsList;
    }

    public List<String> getAllUserIDs(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> _ID = new ArrayList<String>();

        String[] tableColumns = new String[] {
                USER_ID, USER_TIMESTAMP, USER_USERNAME, USER_PASSWORD, USER_EMAIL, USER_FIRST_NAME, USER_LAST_NAME, USER_NATIONAL_ID, USER_PHONE, USER_REGION_ID, USER_USER_TYPE_ID, USER_LOCATION_ID, USER_MODIFIED_BY, USER_CREATED_BY, USER_IS_BLOCKED, USER_TIMESTAMP_UPDATED, USER_TIMESTAMP_CREATED, USER_TIMESTAMP_LAST_LOGIN
        };

        String whereClause = "1=1 ";

        String[] whereArgs = new String[]{};

        String orderBy = USER_USERNAME;

        Cursor cursor = db.query(TABLE_USER, tableColumns, whereClause, whereArgs, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllPersonIDs  "
//                                + cursor.getString(1) + " "
//                                + cursor.getString(2) + " "
//                                + cursor.getString(3) + " "
//                                + cursor.getString(5) + " "
//                );
                _ID.add(cursor.getString(2).trim() + ", " + cursor.getString(5).trim() + " " + cursor.getString(6).trim() + ", " + cursor.getString(8).trim());
            } while (cursor.moveToNext());
        }

        cursor.close();
        // db.close();

        // remove duplicates
        Set<String> noDups = new LinkedHashSet<>(_ID);
        _ID.clear();
        _ID.addAll(noDups);

        // convert to array
//        String[] stringArrayPersonID = new String[ personID.size() ];
//        personID.toArray(stringArrayPersonID);

        return _ID;
    }

    public List<String> getAllUserPhoneNumbers(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> _phone_numbers = new ArrayList<String>();

        String[] tableColumns = new String[] {
                USER_ID, USER_TIMESTAMP, USER_USERNAME, USER_PASSWORD, USER_EMAIL, USER_FIRST_NAME, USER_LAST_NAME, USER_NATIONAL_ID, USER_PHONE, USER_REGION_ID, USER_USER_TYPE_ID, USER_LOCATION_ID, USER_MODIFIED_BY, USER_CREATED_BY, USER_IS_BLOCKED, USER_TIMESTAMP_UPDATED, USER_TIMESTAMP_CREATED, USER_TIMESTAMP_LAST_LOGIN
        };

        String whereClause = "1=1 ";

        String[] whereArgs = new String[]{};

        String orderBy = USER_PHONE;

        Cursor cursor = db.query(TABLE_USER, tableColumns, whereClause, whereArgs, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllPersonIDs  "
//                                + cursor.getString(1) + " "
//                                + cursor.getString(2) + " "
//                                + cursor.getString(3) + " "
//                                + cursor.getString(5) + " "
//                );
                _phone_numbers.add(cursor.getString(8).trim());
            } while (cursor.moveToNext());
        }

        cursor.close();
        // db.close();

        // remove duplicates
        Set<String> noDups = new LinkedHashSet<>(_phone_numbers);
        _phone_numbers.clear();
        _phone_numbers.addAll(noDups);

        // convert to array
//        String[] stringArrayPersonID = new String[ _phone_numbers.size() ];
//        _phone_numbers.toArray(stringArrayPersonID);

        return _phone_numbers;
    }

    public String getRegionString(){
        Log.d(LOG, "getRegionString: " + MainActivity.USER_OBJ.get_region_id());
        String regionString = new String ("");
        switch ( MainActivity.USER_OBJ.get_region_id() ) {
            case 0:
            default:
                regionString = " ( 0 ) ";
                break;
            case 1:
                regionString = " ( 1 ) ";
                break;
            case 2:
                regionString = " ( 2 ) ";
                break;
            case 3:
                regionString = " ( 1,2 ) ";
                break;
            case 4:
                regionString = " ( 3 ) ";
                break;
            case 5:
                regionString = " ( 3,1 ) ";
                break;
            case 6:
                regionString = " ( 3,2 ) ";
                break;
            case 7:
                regionString = " ( 3,2,1 ) ";
                break;
        };
        return regionString;
    }

    public List<String> getAllPersonIDs(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> personID = new ArrayList<String>();

        String[] tableColumns = new String[] {
                PERSON_ID, PERSON_FIRST_NAME, PERSON_LAST_NAME, PERSON_NATIONAL_ID, PERSON_ADDRESS, PERSON_PHONE, PERSON_DOB, PERSON_GENDER, PERSON_LATITUDE, PERSON_LONGITUDE, PERSON_IS_DELETED
        };

        String selectQuery =
                "select p.* from " + TABLE_PERSON + " p\n" +
                        "join address a on p.address_id = a.id \n" +
                        "join user u \n" +
                        "where 1=1 \n" +
                        "and a.region_id in " + getRegionString() + " \n" +
                        "and u.username = '" + MainActivity.USER_OBJ.get_username() + "'\n";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllPersonIDs  "
//                                + cursor.getString(1) + " "
//                                + cursor.getString(2) + " "
//                                + cursor.getString(3) + " "
//                                + cursor.getString(5) + " "
//                );
                personID.add(cursor.getString(2).trim() + " " + cursor.getString(3).trim() + ", " + cursor.getString(4).trim() + ", " + cursor.getString(6).trim());
            } while (cursor.moveToNext());
        }

        cursor.close();
        // db.close();

        // remove duplicates
        Set<String> noDups = new LinkedHashSet<>(personID);
        personID.clear();
        personID.addAll(noDups);

        // convert to array
//        String[] stringArrayPersonID = new String[ personID.size() ];
//        personID.toArray(stringArrayPersonID);

        return personID;
    }

    public List<String> getAllClientIDs(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> clientID = new ArrayList<String>();

        String[] tableColumns = new String[]{
                CLIENT_ID, CLIENT_TIMESTAMP, CLIENT_FIRST_NAME, CLIENT_LAST_NAME, CLIENT_NATIONAL_ID, CLIENT_PHONE, CLIENT_STATUS_ID, CLIENT_LOC_ID, CLIENT_LATITUDE, CLIENT_LONGITUDE, CLIENT_INSTITUTION_ID, CLIENT_GROUP_ACTIVITY_NAME, CLIENT_GROUP_ACTIVITY_DATE, CLIENT_ADDRESS_ID, CLIENT_DOB, CLIENT_GENDER, CLIENT_ORIGINATION, CLIENT_CREATED_BY, CLIENT_MODIFIED_BY, CLIENT_CREATED, CLIENT_MODIFIED
        };

        String selectQuery =
                "select c.* from " + TABLE_CLIENT + " c\n" +
                        "join address a on c.address_id = a.id \n" +
                        "join user u \n" +
                        "where 1=1 \n" +
                        "and a.region_id in " + getRegionString() + " \n" +
                        "and u.username = '" + MainActivity.USER_OBJ.get_username() + "'\n";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllPersonIDs  "
//                                + cursor.getString(1) + " "
//                                + cursor.getString(2) + " "
//                                + cursor.getString(3) + " "
//                                + cursor.getString(5) + " "
//                );
                clientID.add(cursor.getString(2).trim() + " " + cursor.getString(3).trim() + ", " + ", " + cursor.getString(5).trim());
            } while (cursor.moveToNext());
        }

        cursor.close();
        // db.close();

        // remove duplicates
        Set<String> noDups = new LinkedHashSet<>(clientID);
        clientID.clear();
        clientID.addAll(noDups);

        // convert to array
//        String[] stringArrayPersonID = new String[ personID.size() ];
//        personID.toArray(stringArrayPersonID);

        return clientID;
    }



    public List<String> getAllFacilitatorIDs(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> _ID = new ArrayList<String>();

        String[] tableColumns = new String[] {
                FACILITATOR_ID, FACILITATOR_TIMESTAMP, FACILITATOR_FIRST_NAME, FACILITATOR_LAST_NAME, FACILITATOR_NATIONAL_ID, FACILITATOR_PHONE, FACILITATOR_FACILITATOR_TYPE_ID, FACILITATOR_NOTE, FACILITATOR_LOCATION_ID, FACILITATOR_LATITUDE, FACILITATOR_LONGITUDE, FACILITATOR_INSTITUTION_ID
        };

        String whereClause = "1=1 ";

        String[] whereArgs = new String[]{};

        String orderBy = FACILITATOR_FIRST_NAME + "," + FACILITATOR_LAST_NAME + "," + FACILITATOR_NATIONAL_ID;

        Cursor cursor = db.query(TABLE_FACILITATOR, tableColumns, whereClause, whereArgs, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllFacilitatorIDs  "
//                                + cursor.getString(1) + " "
//                                + cursor.getString(2) + " "
//                                + cursor.getString(3) + " "
//                                + cursor.getString(5) + " "
//                );
                _ID.add(cursor.getString(2).trim() + " " + cursor.getString(3).trim() + ", " + cursor.getString(4).trim() + ", " + cursor.getString(5).trim());
            } while (cursor.moveToNext());
        }

        cursor.close();
        // db.close();

        // remove duplicates
        Set<String> noDups = new LinkedHashSet<>(_ID);
        _ID.clear();
        _ID.addAll(noDups);

        // convert to array
//        String[] stringArrayPersonID = new String[ _ID.size() ];
//        _ID.toArray(stringArrayPersonID);

        return _ID;
    }

    public List<String> getAllBookingIDs(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> _ID = new ArrayList<String>();

        String[] tableColumns = new String[] {
                BOOKING_ID, BOOKING_TIMESTAMP, BOOKING_FIRST_NAME, BOOKING_LAST_NAME, BOOKING_NATIONAL_ID, BOOKING_PHONE, BOOKING_LOCATION_ID, BOOKING_PROJECTED_DATE, BOOKING_ACTUAL_DATE, BOOKING_CONSENT, BOOKING_PROCEDURE_TYPE_ID, BOOKING_FOLLOWUP_ID, BOOKING_FOLLOWUP_DATE, BOOKING_ALT_CONTACT
        };

        String whereClause = "1=1 ";

        String[] whereArgs = new String[]{};

        String orderBy = BOOKING_ID;

        Cursor cursor = db.query(TABLE_BOOKING, tableColumns, whereClause, whereArgs, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllBookingIDs  "
//                                + cursor.getString(1) + " "
//                                + cursor.getString(2) + " "
//                                + cursor.getString(3) + " "
//                                + cursor.getString(5) + " "
//                );
                _ID.add(cursor.getString(2).trim() + " " + cursor.getString(3).trim() + ", " + cursor.getString(4).trim() + ", " + cursor.getString(5).trim() + ", " + cursor.getString(7).trim());
            } while (cursor.moveToNext());
        }

        cursor.close();
        // db.close();

        // remove duplicates
        Set<String> noDups = new LinkedHashSet<>(_ID);
        _ID.clear();
        _ID.addAll(noDups);

        // convert to array
//        String[] stringArrayPersonID = new String[ personID.size() ];
//        personID.toArray(stringArrayPersonID);

        return _ID;
    }

    public List<String> getAllGroupActivityIDs(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> _ID = new ArrayList<String>();

        String[] tableColumns = new String[] {
                GROUP_ACTIVITY_ID, GROUP_ACTIVITY_TIMESTAMP, GROUP_ACTIVITY_NAME, GROUP_ACTIVITY_LOCATION_ID, GROUP_ACTIVITY_ACTIVITY_DATE, GROUP_ACTIVITY_GROUP_TYPE_ID, GROUP_ACTIVITY_INSTITUTION_ID, GROUP_ACTIVITY_MALES, GROUP_ACTIVITY_FEMALES, GROUP_ACTIVITY_MESSAGES, GROUP_ACTIVITY_LATITUDE, GROUP_ACTIVITY_LONGITUDE
        };

        String selectQuery =
                "select ga.* from " + TABLE_GROUP_ACTIVITY + " ga \n" +
                        "join location l on ga.location_id = l.id \n" +
                        "join user u \n" +
                        "where 1=1 \n" +
                        "and l.region_id in " + getRegionString() + " \n" +
                        "and u.username = '" + MainActivity.USER_OBJ.get_username() + "'\n";

        Log.d(LOG, "getAllLikeGroupActivitieyIDs selectQuery: " + selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllBookingIDs  "
//                                + cursor.getString(1) + " "
//                                + cursor.getString(2) + " "
//                                + cursor.getString(3) + " "
//                                + cursor.getString(5) + " "
//                );
                _ID.add(cursor.getString(2).trim() + ", " + cursor.getString(4).trim());
            } while (cursor.moveToNext());
        }

        cursor.close();
        // db.close();

        // remove duplicates
        Set<String> noDups = new LinkedHashSet<>(_ID);
        _ID.clear();
        _ID.addAll(noDups);

        // convert to array
//        String[] stringArrayPersonID = new String[ personID.size() ];
//        personID.toArray(stringArrayPersonID);

        return _ID;
    }

    public List<GroupActivity> getAllGroupActivities() {
        List<GroupActivity> groupActivityList = new ArrayList<GroupActivity>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] tableColumns = new String[] {
                GROUP_ACTIVITY_ID, GROUP_ACTIVITY_TIMESTAMP, GROUP_ACTIVITY_NAME, GROUP_ACTIVITY_LOCATION_ID, GROUP_ACTIVITY_ACTIVITY_DATE, GROUP_ACTIVITY_GROUP_TYPE_ID, GROUP_ACTIVITY_INSTITUTION_ID, GROUP_ACTIVITY_MALES, GROUP_ACTIVITY_FEMALES, GROUP_ACTIVITY_MESSAGES, GROUP_ACTIVITY_LATITUDE, GROUP_ACTIVITY_LONGITUDE
        };

        String selectQueryAll = "select * from " + TABLE_GROUP_ACTIVITY;

        String selectQuery =
                "select ga.* from " + TABLE_GROUP_ACTIVITY + " ga\n" +
                        "join location l on ga.location_id = l.id \n" +
                        "join user u \n" +
                        "where 1=1 \n" +
                        "and l.region_id in " + getRegionString() + " \n" +
                        "and u.username = '" + MainActivity.USER_OBJ.get_username() + "'\n" +
                        "and trim(ga." + GROUP_ACTIVITY_NAME + ") like ? \n" +
                        "and trim(ga." + GROUP_ACTIVITY_ACTIVITY_DATE + ") like ? ";

        String[] whereArgs = new String [] {
                "%", "%" };

        Cursor cursor = db.rawQuery(selectQueryAll, null);

        if (cursor.moveToFirst()) {
            do {
                GroupActivity groupActivity = new GroupActivity();
                groupActivity.set_id(cursor.getInt(0));
                groupActivity.set_timestamp(cursor.getString(1));
                groupActivity.set_name(cursor.getString(2));
                groupActivity.set_location_id(parseInt(cursor.getString(3)));
                groupActivity.set_activity_date(cursor.getString(4));
                groupActivity.set_group_type_id(parseInt(cursor.getString(5)));
                groupActivity.set_institution_id(parseInt(cursor.getString(6)));
                groupActivity.set_males(parseInt(cursor.getString(7)));
                groupActivity.set_females(parseInt(cursor.getString(8)));
                groupActivity.set_messages(cursor.getString(9));
                groupActivity.set_longitude(parseFloat(cursor.getString(10)));
                groupActivity.set_latitude(parseFloat(cursor.getString(11)));

//                Log.d(LOG, "getAllGroupActivities loop: " + groupActivity.get_name() );
//                Log.d(LOG, "getAllGroupActivities loop: " + cursor.getString(0) + "," + cursor.getString(1) + "," + cursor.getString(2) + "," + cursor.getString(3) + "," + cursor.getString(6) );

                // Adding groupActivity to list
                groupActivityList.add(groupActivity);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // db.close();

        return groupActivityList;
    }

    public List<String> getAllFacilitatorTypes(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> facilitatorTypes = new ArrayList<String>();

        String[] tableColumns = new String[] {
                FACILITATOR_TYPE_NAME
        };

        String whereClause = "1=1 ";

        String[] whereArgs = new String[]{};

        String orderBy = FACILITATOR_TYPE_ID;

        Cursor cursor = db.query(TABLE_FACILITATOR_TYPE, tableColumns, whereClause, whereArgs, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllFacilityNames  "
//                                + cursor.getString(0)
//                );
                facilitatorTypes.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        // db.close();

        // remove duplicates
        Set<String> noDups = new LinkedHashSet<>(facilitatorTypes);
        facilitatorTypes.clear();
        facilitatorTypes.addAll(noDups);

        // convert to array
        String[] stringArrayNames = new String[ facilitatorTypes.size() ];
        facilitatorTypes.toArray(stringArrayNames);

        return facilitatorTypes;
    }

    public List<String> getAllInteractionTypes(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> interactionTypes = new ArrayList<String>();

        String[] tableColumns = new String[] {
                INTERACTION_TYPE_NAME
        };

        String whereClause = "1=1 ";

        String[] whereArgs = new String[]{};

        String orderBy = INTERACTION_TYPE_ID;

        Cursor cursor = db.query(TABLE_INTERACTION_TYPE, tableColumns, whereClause, whereArgs, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllFacilityNames  "
//                                + cursor.getString(0)
//                );
                interactionTypes.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        // db.close();

        // remove duplicates
        Set<String> noDups = new LinkedHashSet<>(interactionTypes);
        interactionTypes.clear();
        interactionTypes.addAll(noDups);

        // convert to array
        String[] stringArrayNames = new String[ interactionTypes.size() ];
        interactionTypes.toArray(stringArrayNames);

        return interactionTypes;
    }

    public List<String> getAllStatusTypes(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> statusTypes = new ArrayList<String>();

        String[] tableColumns = new String[] {
                STATUS_TYPE_NAME
        };

        String whereClause = "1=1 ";

        String[] whereArgs = new String[]{};

        String orderBy = STATUS_TYPE_ID;

        Cursor cursor = db.query(TABLE_STATUS_TYPE, tableColumns, whereClause, whereArgs, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllFacilityNames  "
//                                + cursor.getString(0)
//                );
                statusTypes.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        // db.close();

        // remove duplicates
        Set<String> noDups = new LinkedHashSet<>(statusTypes);
        statusTypes.clear();
        statusTypes.addAll(noDups);

        // convert to array
        String[] stringArrayNames = new String[ statusTypes.size() ];
        statusTypes.toArray(stringArrayNames);

        return statusTypes;
    }

    public List<String> getAllUserTypes(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> userTypes = new ArrayList<String>();

        String[] tableColumns = new String[] {
                USER_TYPE_NAME
        };

        String whereClause = "1=1 ";

        String[] whereArgs = new String[]{};

        String orderBy = USER_TYPE_NAME;

        Cursor cursor = db.query(TABLE_USER_TYPE, tableColumns, whereClause, whereArgs, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllFacilityNames  "
//                                + cursor.getString(0)
//                );
                userTypes.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        // db.close();

        // remove duplicates
        Set<String> noDups = new LinkedHashSet<>(userTypes);
        userTypes.clear();
        userTypes.addAll(noDups);

        // convert to array
        String[] stringArrayNames = new String[ userTypes.size() ];
        userTypes.toArray(stringArrayNames);

        return userTypes;
    }

    public List<String> getUserStatusTypes(){

        if(MainActivity.USER_OBJ.userStatusList.size() == 0) {
            List<String> _statusList = Arrays.asList();
            switch (MainActivity.USER_OBJ.get_user_type_id()) {
                case 1: // admin
                    _statusList = Arrays.asList("MC Completed", "Clinically Deferred", "Pending", "Refused", "Lost");
                    break;
                case 2: // mobilizer
                    _statusList = Arrays.asList("Pending", "Refused", "Lost");
                    break;
                case 3: // clerk
                    _statusList = Arrays.asList("MC Completed", "Clinically Deferred", "Lost");
                    break;
            }
            return _statusList;
        } else {
            return MainActivity.USER_OBJ.userStatusList;
        }
    }

//    status_MC Completed
//    status_Clinically Deferred
//    status_Pending
//    status_Refused
//    status_Lost

    public Status getStatus( String status_id ) {
        Status status = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(LOG, "getStatus: " + status_id);

        String[] tableColumns = new String[] {
                STATUS_TYPE_ID, STATUS_TYPE_NAME
        };

        String whereClause = "trim(" +
                STATUS_TYPE_ID + ") like ? or trim(" +
                STATUS_TYPE_NAME + ") like ? "
                ;

        Log.d(LOG, "getStatus whereClause: " + whereClause);

        String[] whereArgs = new String [] {
                status_id, status_id };  // gnr: looks strange because method finds rec using either id or name

        Log.d(LOG, "getStatus whereArgs:" + whereArgs[0] );

        Cursor cursor = db.query(TABLE_STATUS_TYPE, tableColumns, whereClause, whereArgs, null, null, null);

        if (cursor.moveToFirst()) {
            Log.d(LOG, "getStatus  "
                    + cursor.getString(0) + " "
                    + cursor.getString(1) + " "
            );

            status = new Status(
                    parseInt(cursor.getString(0)),
                    cursor.getString(1)
            );
            cursor.close();
            // db.close();
            return status;
        } else {
            cursor.close();
            // db.close();
            return status;
        }
    }

    public List<String> getAllGroupActivityTypes(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> groupActivityTypes = new ArrayList<String>();

        String[] tableColumns = new String[] {
                GROUP_TYPE_NAME
        };

        String whereClause = "1=1 ";

        String[] whereArgs = new String[]{};

        String orderBy = GROUP_TYPE_NAME;

        Cursor cursor = db.query(TABLE_GROUP_TYPE, tableColumns, whereClause, whereArgs, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllFacilityNames  "
//                                + cursor.getString(0)
//                );
                groupActivityTypes.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        // db.close();

        // remove duplicates
        Set<String> noDups = new LinkedHashSet<>(groupActivityTypes);
        groupActivityTypes.clear();
        groupActivityTypes.addAll(noDups);

        // convert to array
        String[] stringArrayNames = new String[ groupActivityTypes.size() ];
        groupActivityTypes.toArray(stringArrayNames);

        return groupActivityTypes;
    }

    public GroupActivityType getGroupActivityType( String group_type_id ) {
        GroupActivityType groupActivityType = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(LOG, "getGroupActivityType: " + group_type_id);

        String[] tableColumns = new String[] {
                GROUP_TYPE_ID, GROUP_TYPE_NAME
        };

        String whereClause = "trim(" +
                GROUP_TYPE_ID + ") like ? or trim(" +
                GROUP_TYPE_NAME + ") like ? "
                ;

        Log.d(LOG, "getGroupActivityType whereClause: " + whereClause);

        String[] whereArgs = new String [] {
                group_type_id, group_type_id };  // gnr: looks strange because method finds rec using either id or name

        Log.d(LOG, "getGroupActivityType whereArgs:" + whereArgs[0] );

        Cursor cursor = db.query(TABLE_GROUP_TYPE, tableColumns, whereClause, whereArgs, null, null, null);

        if (cursor.moveToFirst()) {
            Log.d(LOG, "getGroupActivityType  "
                    + cursor.getString(0) + " "
                    + cursor.getString(1) + " "
            );

            groupActivityType = new GroupActivityType(
                    parseInt(cursor.getString(0)),
                    cursor.getString(1)
            );
            cursor.close();
            // db.close();
            return groupActivityType;
        } else {
            cursor.close();
            // db.close();
            return groupActivityType;
        }
    }

    public FacilitatorType getFacilitatorType( String facilitator_type_id ) {
        FacilitatorType facilitatorType = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(LOG, "getFacilitatorType: " + facilitator_type_id);

        String[] tableColumns = new String[] {
                FACILITATOR_TYPE_ID, FACILITATOR_TYPE_NAME
        };

        String whereClause = "trim(" +
                FACILITATOR_TYPE_ID + ") like ? or trim(" +
                FACILITATOR_TYPE_NAME + ") like ? "
                ;

        Log.d(LOG, "getFacilitatorType whereClause: " + whereClause);

        String[] whereArgs = new String [] {
                facilitator_type_id, facilitator_type_id };  // gnr: looks strange because method finds rec using either id or name

        Log.d(LOG, "getFacilitatorType whereArgs:" + whereArgs[0] );

        Cursor cursor = db.query(TABLE_FACILITATOR_TYPE, tableColumns, whereClause, whereArgs, null, null, null);

        if (cursor.moveToFirst()) {
            Log.d(LOG, "getFacilitatorType  "
                    + cursor.getString(0) + " "
                    + cursor.getString(1) + " "
            );

            facilitatorType = new FacilitatorType(
                    parseInt(cursor.getString(0)),
                    cursor.getString(1)
            );
            cursor.close();
            // db.close();
            return facilitatorType;
        } else {
            cursor.close();
            // db.close();
            return facilitatorType;
        }
    }

    public List<String> getAllProcedureTypeNames(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> procedureTypes = new ArrayList<String>();

        String[] tableColumns = new String[] {
                PROCEDURE_TYPE_NAME
        };

        String whereClause = "1=1 ";

        String[] whereArgs = new String[]{};

        String orderBy = PROCEDURE_TYPE_ID;

        Cursor cursor = db.query(TABLE_PROCEDURE_TYPE, tableColumns, whereClause, whereArgs, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllFacilityNames  "
//                                + cursor.getString(0)
//                );
                procedureTypes.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        // db.close();

        // remove duplicates
        Set<String> noDups = new LinkedHashSet<>(procedureTypes);
        procedureTypes.clear();
        procedureTypes.addAll(noDups);

        // convert to array
        String[] stringArrayNames = new String[ procedureTypes.size() ];
        procedureTypes.toArray(stringArrayNames);

        return procedureTypes;
    }

    public ProcedureType getProcedureType( String procedure_type_id ) {
        ProcedureType procedureType = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(LOG, "getProcedureType: " + procedure_type_id);

        String[] tableColumns = new String[] {
                PROCEDURE_TYPE_ID, PROCEDURE_TYPE_NAME
        };

        String whereClause = "trim(" +
                PROCEDURE_TYPE_ID + ") like ? or trim(" +
                PROCEDURE_TYPE_NAME + ") like ? "
                ;

        Log.d(LOG, "getProcedureType whereClause: " + whereClause);

        String[] whereArgs = new String [] {
                procedure_type_id, procedure_type_id };  // gnr: looks strange because method finds rec using either id or name

        Log.d(LOG, "getProcedureType whereArgs:" + whereArgs[0] );

        Cursor cursor = db.query(TABLE_PROCEDURE_TYPE, tableColumns, whereClause, whereArgs, null, null, null);

        if (cursor.moveToFirst()) {
            Log.d(LOG, "getProcedureType  "
                    + cursor.getString(0) + " "
                    + cursor.getString(1) + " "
            );

            procedureType = new ProcedureType(
                    parseInt(cursor.getString(0)),
                    cursor.getString(1)
            );
            cursor.close();
            // db.close();
            return procedureType;
        } else {
            cursor.close();
            // db.close();
            return procedureType;
        }
    }

    public List<String> getAllFollowupNames(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> followups = new ArrayList<String>();

        String[] tableColumns = new String[] {
                FOLLOWUP_NAME
        };

        String whereClause = "1=1 ";

        String[] whereArgs = new String[]{};

        String orderBy = FOLLOWUP_ID;

        Cursor cursor = db.query(TABLE_FOLLOWUP, tableColumns, whereClause, whereArgs, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllFacilityNames  "
//                                + cursor.getString(0)
//                );
                followups.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        // db.close();

        // remove duplicates
        Set<String> noDups = new LinkedHashSet<>(followups);
        followups.clear();
        followups.addAll(noDups);

        // convert to array
        String[] stringArrayNames = new String[ followups.size() ];
        followups.toArray(stringArrayNames);

        return followups;
    }

    public Followup getFollowup( String followup_id ) {
        Followup followup = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(LOG, "getFollowup: " + followup_id);

        String[] tableColumns = new String[] {
                FOLLOWUP_ID, FOLLOWUP_NAME
        };

        String whereClause = "trim(" +
                FOLLOWUP_ID + ") like ? or trim(" +
                FOLLOWUP_NAME + ") like ? "
                ;

        Log.d(LOG, "getFollowup whereClause: " + whereClause);

        String[] whereArgs = new String [] {
                followup_id, followup_id };  // gnr: looks strange because method finds rec using either id or name

        Log.d(LOG, "getFollowup whereArgs:" + whereArgs[0] );

        Cursor cursor = db.query(TABLE_FOLLOWUP, tableColumns, whereClause, whereArgs, null, null, null);

        if (cursor.moveToFirst()) {
            Log.d(LOG, "getFollowup  "
                    + cursor.getString(0) + " "
                    + cursor.getString(1) + " "
            );

            followup = new Followup(
                    parseInt(cursor.getString(0)),
                    cursor.getString(1)
            );
            cursor.close();
            // db.close();
            return followup;
        } else {
            cursor.close();
            // db.close();
            return followup;
        }
    }

    public InteractionType getInteractionType( String interaction_type_id ) {
        InteractionType interactionType = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(LOG, "getInteractionType: " + interaction_type_id);

        String[] tableColumns = new String[] {
                INTERACTION_TYPE_ID, INTERACTION_TYPE_NAME
        };

        String whereClause = "trim(" +
                INTERACTION_TYPE_ID + ") like ? or trim(" +
                INTERACTION_TYPE_NAME + ") like ? "
                ;

        Log.d(LOG, "getInteractionType whereClause: " + whereClause);

        String[] whereArgs = new String [] {
                interaction_type_id, interaction_type_id };  // gnr: looks strange because method finds rec using either id or name

        Log.d(LOG, "getInteractionType whereArgs:" + whereArgs[0] );

        Cursor cursor = db.query(TABLE_INTERACTION_TYPE, tableColumns, whereClause, whereArgs, null, null, null);

        if (cursor.moveToFirst()) {
            Log.d(LOG, "getInteractionType  "
                    + cursor.getString(0) + " "
                    + cursor.getString(1) + " "
            );

            interactionType = new InteractionType(
                    parseInt(cursor.getString(0)),
                    cursor.getString(1)
            );
            cursor.close();
            // db.close();
            return interactionType;
        } else {
            cursor.close();
            // db.close();
            return interactionType;
        }
    }

    public UserType getUserType( String user_type_id ) {
        UserType userType = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(LOG, "getUserType: " + user_type_id);

        String[] tableColumns = new String[] {
                USER_TYPE_ID, USER_TYPE_NAME
        };

        String whereClause = "trim(" +
                USER_TYPE_ID + ") like ? or trim(" +
                USER_TYPE_NAME + ") like ? "
                ;

        Log.d(LOG, "getUserType whereClause: " + whereClause);

        String[] whereArgs = new String [] {
                user_type_id, user_type_id };  // gnr: looks strange because method finds rec using either id or name

        Log.d(LOG, "getUserType whereArgs:" + whereArgs[0] );

        Cursor cursor = db.query(TABLE_USER_TYPE, tableColumns, whereClause, whereArgs, null, null, null);

        if (cursor.moveToFirst()) {
            Log.d(LOG, "getUserType  "
                    + cursor.getString(0) + " "
                    + cursor.getString(1) + " "
            );

            userType = new UserType(
                    parseInt(cursor.getString(0)),
                    cursor.getString(1)
            );
            cursor.close();
            // db.close();
            return userType;
        } else {
            cursor.close();
            // db.close();
            return userType;
        }
    }

    public List<String> getAllLocationNamesWithoutRegionJoin(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> locationNames = new ArrayList<String>();

        String[] tableColumns = new String[] {
                LOCATION_NAME
        };

        String selectQuery =
                "select l." + LOCATION_NAME + " from " + TABLE_LOCATION + " l \n" +
                        "where 1=1 \n";

        String whereClause = "1=1 ";

        String[] whereArgs = new String[]{};

        String orderBy = LOCATION_ID;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllLocationNames  "
//                                + cursor.getString(0)
//                );
                locationNames.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        // db.close();

        // remove duplicates
        Set<String> noDups = new LinkedHashSet<>(locationNames);
        locationNames.clear();
        locationNames.addAll(noDups);

        // convert to array
        String[] stringArrayNames = new String[ locationNames.size() ];
        locationNames.toArray(stringArrayNames);

        return locationNames;
    }



    public List<String> getAllLocationNames(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> locationNames = new ArrayList<String>();

        String[] tableColumns = new String[] {
                LOCATION_NAME
        };

        // gnr: we understand that this is a cartesian product, see getRegionString and user.region_id
        String selectQuery =
                "select l." + LOCATION_NAME + " from " + TABLE_LOCATION + " l \n" +
                        "join user u \n" +
                        "where 1=1 \n" +
                        "and l.region_id in " + getRegionString() + " \n" +
                        "and u.username = '" + MainActivity.USER_OBJ.get_username() + "' \n";

        String whereClause = "1=1 ";

        String[] whereArgs = new String[]{};

        String orderBy = LOCATION_ID;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllLocationNames  "
//                                + cursor.getString(0)
//                );
                locationNames.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        // db.close();

        // remove duplicates
        Set<String> noDups = new LinkedHashSet<>(locationNames);
        locationNames.clear();
        locationNames.addAll(noDups);

        // convert to array
        String[] stringArrayNames = new String[ locationNames.size() ];
        locationNames.toArray(stringArrayNames);

        return locationNames;
    }

    public VMMCLocation getLocation( String location_id ) {
        VMMCLocation location = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(LOG, "getLocation: " + location_id);

        String[] tableColumns = new String[] {
                LOCATION_ID, LOCATION_NAME
        };

        String whereClause = "trim(" +
                LOCATION_ID + ") like ? or trim(" +
                LOCATION_NAME + ") like ? "
                ;

        Log.d(LOG, "getLocaction whereClause: " + whereClause);

        String[] whereArgs = new String [] {
                location_id, location_id }; // gnr: looks strange because method finds rec using either id or name

        Log.d(LOG, "getLocaction whereArgs:" + whereArgs[0] );

        Cursor cursor = db.query(TABLE_LOCATION, tableColumns, whereClause, whereArgs, null, null, null);

        if (cursor.moveToFirst()) {
            Log.d(LOG, "getLocaction  "
                    + cursor.getString(0) + " "
                    + cursor.getString(1) + " "
            );

            location = new VMMCLocation(
                    parseInt(cursor.getString(0)),
                    cursor.getString(1)
            );
            cursor.close();
            // db.close();
            return location;
        } else {
            cursor.close();
            // db.close();
            return location;
        }
    }


    public List<String> getAllAddressNames(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> addressNames = new ArrayList<String>();

        String[] tableColumns = new String[] {
                ADDRESS_NAME
        };

        String selectQuery =
                "select a.name from " + TABLE_ADDRESS + " a\n" +
                        "join user u \n" +
                        "where 1=1 \n" +
                        "and a.region_id in " + getRegionString() + " \n" +
                        "and u.username = '" + MainActivity.USER_OBJ.get_username() + "'\n";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllAddressNames  "
//                                + cursor.getString(0)
//                );
                addressNames.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        // db.close();

        // remove duplicates
        Set<String> noDups = new LinkedHashSet<>(addressNames);
        addressNames.clear();
        addressNames.addAll(noDups);

        // convert to array
        String[] stringArrayNames = new String[ addressNames.size() ];
        addressNames.toArray(stringArrayNames);

        return addressNames;
    }

    public Address getAddress( String address_id ) {
        Address address = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(LOG, "getAddress: " + address_id);

        String[] tableColumns = new String[] {
                ADDRESS_ID, ADDRESS_NAME
        };

        String whereClause = "trim(" +
                ADDRESS_ID + ") like ? or trim(" +
                ADDRESS_NAME + ") like ? "
                ;

        Log.d(LOG, "getLocaction whereClause: " + whereClause);

        String[] whereArgs = new String [] {
                address_id, address_id }; // gnr: looks strange because method finds rec using either id or name

        Log.d(LOG, "getLocaction whereArgs:" + whereArgs[0] );

        Cursor cursor = db.query(TABLE_ADDRESS, tableColumns, whereClause, whereArgs, null, null, null);

        if (cursor.moveToFirst()) {
            Log.d(LOG, "getLocaction  "
                    + cursor.getString(0) + " "
                    + cursor.getString(1) + " "
            );

            address = new Address(
                    parseInt(cursor.getString(0)),
                    cursor.getString(1)
            );
            cursor.close();
            // db.close();
            return address;
        } else {
            cursor.close();
            // db.close();
            return address;
        }
    }

    public List<String> getAllInstitutionNames(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> institutionNames = new ArrayList<String>();

        String[] tableColumns = new String[] {
                INSTITUTION_NAME
        };

        String selectQuery =
                "select i.name from " + TABLE_INSTITUTION + " i\n" +
                        "join user u \n" +
                        "where 1=1 \n" +
                        "and i.region_id in " + getRegionString() + " \n" +
                        "and u.username = '" + MainActivity.USER_OBJ.get_username() + "'\n";

        String whereClause = "1=1 ";

        String[] whereArgs = new String[]{};

        String orderBy = INSTITUTION_ID;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllInstitutionNames  "
//                                + cursor.getString(0)
//                );
                institutionNames.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        // db.close();

        // remove duplicates
        Set<String> noDups = new LinkedHashSet<>(institutionNames);
        institutionNames.clear();
        institutionNames.addAll(noDups);

        // convert to array
        String[] stringArrayNames = new String[ institutionNames.size() ];
        institutionNames.toArray(stringArrayNames);

        return institutionNames;
    }

    public Institution getInstitution( String institution_id ) {
        Institution institution = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(LOG, "getInstitution: " + institution_id);

        String[] tableColumns = new String[] {
                INSTITUTION_ID, INSTITUTION_NAME
        };

        String whereClause = "trim(" +
                INSTITUTION_ID + ") like trim(?) or trim(" +
                INSTITUTION_NAME + ") like trim(?) "
                ;

        Log.d(LOG, "getInstitution whereClause: " + whereClause);

        String[] whereArgs = new String [] {
                institution_id, institution_id };  // gnr: looks strange because method finds rec using either id or name

        Log.d(LOG, "getInstitution whereArgs:" + whereArgs[0] );

        Cursor cursor = db.query(TABLE_INSTITUTION, tableColumns, whereClause, whereArgs, null, null, null);

        if (cursor.moveToFirst()) {
            Log.d(LOG, "getInstitution  "
                    + cursor.getString(0) + " "
                    + cursor.getString(1) + " "
            );

            institution = new Institution(
                    parseInt(cursor.getString(0)), cursor.getString(1)
            );
            cursor.close();
            // db.close();
            return institution;
        } else { //do not return null for autocomplete in case junk typed in
            cursor.close();
            //institution = new Institution(1, "IUM"); //default
            // db.close();
            return null;
        }
    }

    public List<PendingFollowup> getAllPendingFollowups() {

        List<PendingFollowup> _List = new ArrayList<PendingFollowup>();
        // Select All Query
        String selectQuery =
                "select \n" +
                        "c.first_name, \n" +
                        "c.last_name, \n" +
                        "c.national_id, \n" +
                        "ifnull(round(julianday('now')-julianday(b.projected_date)),0) as difference,\n" +
                        "c.dob,\n" +
                        "l.name as location,\n" +
                        "a.name,\n" +
                        "c.phone\n" +
                        "from client_table c\n" +
                        "join status_type s on s.id = c.status_id\n" +
                        "join booking b on \n" +
                        "  c.first_name = b.first_name and\n" +
                        "  c.last_name = b.last_name and\n" +
                        "  c.national_id = b.national_id and\n" +
                        "  c.phone = b.phone\n" +
                        "join location l on c.loc_id = l.id\n" +
                        "join address a on c.address_id = a.id\n" +
                        "join user u \n" +
                        "where s.name = 'Pending'\n" +
                        "and l.region_id in " + getRegionString() + " \n" +
                        "and u.username = '" + MainActivity.USER_OBJ.get_username() + "'\n" +
                        "order by difference desc ";

        Log.d(LOG, "getAllPendingFollowups:select: " + selectQuery.toString());

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PendingFollowup pendingFollowup = new PendingFollowup();
                pendingFollowup.set_first_name(cursor.getString(0));
                pendingFollowup.set_last_name(cursor.getString(1));
                pendingFollowup.set_national_id(cursor.getString(2));
                pendingFollowup.set_difference(parseInt(cursor.getString(3)));
                pendingFollowup.set_dob(cursor.getString(4));
                pendingFollowup.set_location(cursor.getString(5));
                pendingFollowup.set_address(cursor.getString(6));
                pendingFollowup.set_phone(cursor.getString(7));
                // Adding person to list
                _List.add(pendingFollowup);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // db.close();

        return _List;
    }

    public boolean getSyncReady() {
        boolean syncReady = true;
        List<String> tableList = new ArrayList<String>();

        tableList.add("user");
        tableList.add("user_type");
        tableList.add("user_to_acl");
        tableList.add("client_table");
        tableList.add("facilitator");
        tableList.add("location");
        tableList.add("address");
        tableList.add("region");
        tableList.add("booking");
        tableList.add("facilitator_type");
        tableList.add("procedure_type");
        tableList.add("followup");
        tableList.add("interaction_type");
        tableList.add("status_type");
        tableList.add("institution");
        tableList.add("group_activity");
        tableList.add("group_type");

        SQLiteDatabase db = this.getReadableDatabase();

        for (int i = 0; i < tableList.size(); i++) {
            String selectQuery = "SELECT  * FROM " + TABLE_SYNC_AUDIT +
                    " where timestamp = (select max(timestamp) from " + TABLE_SYNC_AUDIT + " where progress like '" + tableList.get(i) +
                    ":%' ) and status = '' ";
            Log.d(LOG, "getSyncReady:selectQuery: " + selectQuery);
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (!cursor.moveToFirst()) {
                syncReady = false;
            }
            cursor.close();
        }
        return syncReady;
    }

    public List<SyncAudit> getAllSyncAudits() {

        List<SyncAudit> _List = new ArrayList<SyncAudit>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SYNC_AUDIT +  " order by id desc";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SyncAudit syncAudit = new SyncAudit();
                syncAudit.set_id(cursor.getInt(0));
                syncAudit.set_timestamp(cursor.getString(1));
                syncAudit.set_longitude(parseFloat(cursor.getString(2)));
                syncAudit.set_latitude(parseFloat(cursor.getString(3)));
                syncAudit.set_device_id(cursor.getString(4));
                syncAudit.set_username(cursor.getString(5));
                syncAudit.set_progress(cursor.getString(7));
                syncAudit.set_status(cursor.getString(8));
                // Adding record to list
                _List.add(syncAudit);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // db.close();

        return _List;
    }

    public String[] getAllPersonPhoneNumbers() {
        return getAllPhoneNumbers();
    }

    public String[] getAllPhoneNumbers(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> phone_numbers = new ArrayList<String>();

        String[] tableColumns = new String[] {
                PERSON_PHONE
        };

        String whereClause = "1=1 ";

        String[] whereArgs = new String[]{};

        String orderBy = PERSON_PHONE;

        Cursor cursor = db.query(TABLE_PERSON, tableColumns, whereClause, whereArgs, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllPhoneNumbers  "
//                                + cursor.getString(0)
//                );

                phone_numbers.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        // db.close();

        // remove duplicates
        Set<String> noDups = new LinkedHashSet<>(phone_numbers);
        phone_numbers.clear();
        phone_numbers.addAll(noDups);

        // convert to array
        String[] stringArrayPhoneNumbers = new String[ phone_numbers.size() ];
        phone_numbers.toArray(stringArrayPhoneNumbers);

        return stringArrayPhoneNumbers;
    }

    public String[] getAllBookingPhoneNumbers(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> phone_numbers = new ArrayList<String>();

        String[] tableColumns = new String[] {
                BOOKING_PHONE
        };

        String whereClause = "1=1 ";

        String[] whereArgs = new String[]{};

        String orderBy = BOOKING_PHONE;

        Cursor cursor = db.query(TABLE_BOOKING, tableColumns, whereClause, whereArgs, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllPhoneNumbers  "
//                                + cursor.getString(0)
//                );

                phone_numbers.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        // db.close();

        // remove duplicates
        Set<String> noDups = new LinkedHashSet<>(phone_numbers);
        phone_numbers.clear();
        phone_numbers.addAll(noDups);

        // convert to array
        String[] stringArrayPhoneNumbers = new String[ phone_numbers.size() ];
        phone_numbers.toArray(stringArrayPhoneNumbers);

        return stringArrayPhoneNumbers;
    }

//    public String[] getAllFacilityNames(){
//        SQLiteDatabase db = this.getReadableDatabase();
//        List<String> facility_names = new ArrayList<String>();
//
//        String[] tableColumns = new String[] {
//                PERSON_FACILITY_NAME
//        };
//
//        String whereClause = "1=1 ";
//
//        String[] whereArgs = new String[]{};
//
//        String orderBy = PERSON_FACILITY_NAME;
//
//        Cursor cursor = db.query(TABLE_PERSON, tableColumns, whereClause, whereArgs, null, null, orderBy);
//
//        if (cursor.moveToFirst()) {
//            do {
////                Log.d(LOG, "getAllFacilityNames  "
////                                + cursor.getString(0)
////                );
//
//                facility_names.add(cursor.getString(0));
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        // db.close();
//
//        // remove duplicates
//        Set<String> noDups = new LinkedHashSet<>(facility_names);
//        facility_names.clear();
//        facility_names.addAll(noDups);
//
//        // convert to array
//        String[] stringArrayFacilityNames = new String[ facility_names.size() ];
//        facility_names.toArray(stringArrayFacilityNames);
//
//        return stringArrayFacilityNames;
//    }

    public int getFacilityID(String facility_name){
        SQLiteDatabase db = this.getReadableDatabase();
        int facility_id;
        Log.d(LOG, "getFacilityID: ");
        String query =
                "select " +
                        "p.facility_id " +
                        "from person p " +
                        "where  1=1 " +
                        "and facility_name = '" + facility_name + "' " +
                        "limit 1";

        Log.d(LOG, "Query: " + query);

        Cursor cursor = db.rawQuery(query, null);
        facility_id = parseInt(cursor.getString(0));

        cursor.close();
        // db.close();
        return facility_id;
    }

    public String[] getAllBookingNationalIDs(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> nationalIds = new ArrayList<String>();

        String[] tableColumns = new String[] {
                BOOKING_NATIONAL_ID
        };

        String whereClause = "1=1 ";

        String[] whereArgs = new String[]{};

        String orderBy = BOOKING_NATIONAL_ID;

        Cursor cursor = db.query(TABLE_BOOKING, tableColumns, whereClause, whereArgs, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllNationalIds  "
//                                + cursor.getString(0)
//                );

                nationalIds.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        // db.close();

        // remove duplicates
        Set<String> noDups = new LinkedHashSet<>(nationalIds);
        nationalIds.clear();
        nationalIds.addAll(noDups);

        // convert to array
        String[] stringArrayNationalIDS = new String[ nationalIds.size() ];
        nationalIds.toArray(stringArrayNationalIDS);

        return stringArrayNationalIDS;
    }

    public String[] getAllPersonNationalIDs() {
        return getAllNationalIDs();
    }

    public String[] getAllNationalIDs(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> nationalIds = new ArrayList<String>();

        String[] tableColumns = new String[] {
                PERSON_NATIONAL_ID
        };

        String whereClause = "1=1 ";

        String[] whereArgs = new String[]{};

        String orderBy = PERSON_NATIONAL_ID;

        Cursor cursor = db.query(TABLE_PERSON, tableColumns, whereClause, whereArgs, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllNationalIds  "
//                                + cursor.getString(0)
//                );

                nationalIds.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        // db.close();

        // remove duplicates
        Set<String> noDups = new LinkedHashSet<>(nationalIds);
        nationalIds.clear();
        nationalIds.addAll(noDups);

        // convert to array
        String[] stringArrayNationalIDS = new String[ nationalIds.size() ];
        nationalIds.toArray(stringArrayNationalIDS);

        return stringArrayNationalIDS;
    }

    public List<EditPageObject> getEditPageData(PersonToAssessments person_to_assessment) {
        List<EditPageObject> editPageList = new ArrayList<EditPageObject>();
        Log.d(LOG, "getEditPageData > ");
        String query =
                "select " +
                        "aq.assessments_questions_id, " +
                        "aq.question, " +
                        "aq.itemtype, " +
                        "aq.itemorder, " +
                        "ifnull((select aa.answer from assessments_answers aa " +
                        "  where aa.person = pa.person_id " +
                        "  and aa.facility = pa.facility_id " +
                        "  and aa.date_created = pa.date_created " +
                        "  and a.assessment_id = aq.assessment_id  " +
                        "  and aa.question = aq.assessments_questions_id" +
                        "), '') as answer " +
                        "from person_to_assessments pa " +
                        "join person p on p.person_id = pa.person_id " +
                        "join assessments a on pa.assessment_id = a.assessment_id " +
                        "join assessments_questions aq on a.assessment_id = aq.assessment_id " +

                        "where  1=1 " +
                        " and pa.person_id = " + person_to_assessment.get_person_id() +
                        " and pa.facility_id = " + person_to_assessment.get_facility_id() +
                        " and pa.date_created = '" + person_to_assessment.get_date_created() + "' " +
                        " and pa.assessment_id = " + person_to_assessment.get_assessment_id() +
                        " and aq.itemtype != 'text' " +
                        //" and aq.status = 1 " +
                        " union " +
                        "select " +
                        "aq.assessments_questions_id, " +
                        "aq.question, " +
                        "aq.itemtype, " +
                        "aq.itemorder, " +
                        "null " +
                        "from person_to_assessments pa " +
                        "join person p on p.person_id = pa.person_id " +
                        "join assessments a on pa.assessment_id = a.assessment_id " +
                        "join assessments_questions aq on a.assessment_id = aq.assessment_id " +
                        "where  1=1 " +
                        " and pa.person_id = " + person_to_assessment.get_person_id() +
                        " and pa.facility_id = " + person_to_assessment.get_facility_id() +
                        " and pa.date_created = '" + person_to_assessment.get_date_created() + "' " +
                        " and pa.assessment_id = " + person_to_assessment.get_assessment_id() +
                        //" and aq.status = 1 " +
                        " and aq.itemtype = 'text' " +
                        "order by aq.itemorder ";

        Log.d(LOG, "Query: " + query);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                EditPageObject editPageObject = new EditPageObject();
                editPageObject.set_assessments_questions_id(parseInt(cursor.getString(0)));
                editPageObject.set_question(cursor.getString(1));
                editPageObject.set_itemtype(cursor.getString(2));
                editPageObject.set_itemorder(parseInt(cursor.getString(3)));
                editPageObject.set_answer(cursor.getString(4));
                editPageObject.set_dropdown_tablename("");

                // Adding object to list
                editPageList.add(editPageObject);
            } while (cursor.moveToNext());


        }
        cursor.close();
        // db.close();
        // return person list
        Log.d(LOG, "Return");
        return editPageList;
    }



//    dbHelp.addClient(_booking.get_first_name(), _booking.get_last_name(), _booking.get_national_id(), _booking.get_phone());

    public boolean addClient(Client client) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Calendar calendar = Calendar.getInstance();
        Timestamp oTimestamp = new Timestamp(calendar.getTime().getTime());
        DateFormat df = new android.text.format.DateFormat();
        client.set_timestamp(DateFormat.format(VMMC_DATE_TIME_FORMAT, oTimestamp).toString());
        client.set_modified(DateFormat.format(VMMC_DATE_TIME_FORMAT, oTimestamp).toString());
        client.set_modified_by(MainActivity.USER_OBJ.get_id());
        client.set_created(DateFormat.format(VMMC_DATE_TIME_FORMAT, oTimestamp).toString());
        client.set_created_by(MainActivity.USER_OBJ.get_id());

        values.put(CLIENT_TIMESTAMP, oTimestamp.toString());
        values.put(CLIENT_FIRST_NAME, client.get_first_name());
        values.put(CLIENT_LAST_NAME, client.get_last_name());
        values.put(CLIENT_NATIONAL_ID, client.get_national_id());
        values.put(CLIENT_PHONE,  client.get_phone());
        values.put(CLIENT_STATUS_ID,  client.get_status_id());
        values.put(CLIENT_LOC_ID,  client.get_loc_id());
        values.put(CLIENT_LATITUDE,  client.get_latitude());
        values.put(CLIENT_LONGITUDE,  client.get_longitude());
        values.put(CLIENT_INSTITUTION_ID,  client.get_institution_id());
        values.put(CLIENT_GROUP_ACTIVITY_NAME,  client.get_group_activity_name());
        values.put(CLIENT_GROUP_ACTIVITY_DATE,  client.get_group_activity_date());

        values.put(CLIENT_FAC_FIRST_NAME,  client.get_fac_first_name());
        values.put(CLIENT_FAC_LAST_NAME,  client.get_fac_last_name());
        values.put(CLIENT_FAC_NATIONAL_ID,  client.get_fac_national_id());
        values.put(CLIENT_FAC_PHONE,  client.get_fac_phone());

        values.put(CLIENT_ADDRESS_ID,  client.get_address_id());
        values.put(CLIENT_DOB,  client.get_dob());
        values.put(CLIENT_GENDER,  client.get_gender());
        values.put(CLIENT_ORIGINATION,  client.get_origination());

        values.put(CLIENT_CREATED_BY,  client.get_created_by());
        values.put(CLIENT_MODIFIED_BY,  client.get_modified_by());
        values.put(CLIENT_CREATED,  client.get_created());
        values.put(CLIENT_MODIFIED,  client.get_modified());

        try {
            db.insert(TABLE_CLIENT, null, values);
            DBHelper.exportDB();
        } catch (Exception ex) {
            // db.close();
            Log.d(LOG, "addClient catch " + ex.toString());
            return false;
        }
        return true;
    }

    public boolean updateClient(Client client) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Calendar calendar = Calendar.getInstance();
        Timestamp oTimestamp = new Timestamp(calendar.getTime().getTime());
        DateFormat df = new android.text.format.DateFormat();
        client.set_timestamp(DateFormat.format(VMMC_DATE_TIME_FORMAT, oTimestamp).toString());
        client.set_modified(DateFormat.format(VMMC_DATE_TIME_FORMAT, oTimestamp).toString());
        client.set_modified_by(MainActivity.USER_OBJ.get_id());

        values.put(CLIENT_TIMESTAMP, client.get_timestamp());
        values.put(CLIENT_FIRST_NAME, client.get_first_name());
        values.put(CLIENT_LAST_NAME, client.get_last_name());
        values.put(CLIENT_NATIONAL_ID, client.get_national_id());
        values.put(CLIENT_PHONE,  client.get_phone());
        values.put(CLIENT_STATUS_ID,  client.get_status_id());
        values.put(CLIENT_LOC_ID,  client.get_loc_id());
        values.put(CLIENT_LATITUDE,  client.get_latitude());
        values.put(CLIENT_LONGITUDE,  client.get_longitude());
        values.put(CLIENT_INSTITUTION_ID,  client.get_institution_id());
        values.put(CLIENT_GROUP_ACTIVITY_NAME,  client.get_group_activity_name());
        values.put(CLIENT_GROUP_ACTIVITY_DATE,  client.get_group_activity_date());

        values.put(CLIENT_FAC_FIRST_NAME,  client.get_fac_first_name());
        values.put(CLIENT_FAC_LAST_NAME,  client.get_fac_last_name());
        values.put(CLIENT_FAC_NATIONAL_ID,  client.get_fac_national_id());
        values.put(CLIENT_FAC_PHONE,  client.get_fac_phone());

        values.put(CLIENT_ADDRESS_ID,  client.get_address_id());
        values.put(CLIENT_DOB,  client.get_dob());
        values.put(CLIENT_GENDER,  client.get_gender());
        values.put(CLIENT_ORIGINATION,  client.get_origination());

        values.put(CLIENT_CREATED_BY,  client.get_created_by());
        values.put(CLIENT_MODIFIED_BY,  client.get_modified_by());
        values.put(CLIENT_CREATED,  client.get_created());
        values.put(CLIENT_MODIFIED,  client.get_modified());

        String[] tableColumns = new String[]{
                CLIENT_ID, CLIENT_TIMESTAMP, CLIENT_FIRST_NAME, CLIENT_LAST_NAME, CLIENT_NATIONAL_ID, CLIENT_PHONE, CLIENT_STATUS_ID, CLIENT_LOC_ID, CLIENT_LATITUDE, CLIENT_LONGITUDE, CLIENT_INSTITUTION_ID, CLIENT_GROUP_ACTIVITY_NAME, CLIENT_GROUP_ACTIVITY_DATE, CLIENT_FAC_FIRST_NAME, CLIENT_FAC_LAST_NAME, CLIENT_NATIONAL_ID, CLIENT_FAC_PHONE, CLIENT_ADDRESS_ID, CLIENT_DOB, CLIENT_GENDER, CLIENT_ORIGINATION, CLIENT_CREATED_BY, CLIENT_MODIFIED_BY, CLIENT_CREATED, CLIENT_MODIFIED
        };

//        String whereClause = "1=1 and trim(" +
//                CLIENT_FIRST_NAME + ") like ? or trim(" +
//                CLIENT_LAST_NAME + ") like ? or trim(" +
//                CLIENT_NATIONAL_ID + ") like ? or trim(" +
//                CLIENT_PHONE + ") like ? ";

//        Log.d(LOG, "updatePerson whereClause: " + whereClause);

//        String[] whereArgs = new String[]{
//                client.get_first_name(), client.get_last_name(), client._national_id, client.get_phone()};

        String updateWhereClause = "1=1 and " +
                CLIENT_FIRST_NAME + " = '" + values.get(CLIENT_FIRST_NAME).toString().replaceAll("'","''") + "' and " +
                CLIENT_LAST_NAME + " = '" + values.get(CLIENT_LAST_NAME).toString().replaceAll("'","''") + "' and " +
                CLIENT_NATIONAL_ID + " = '" + values.get(CLIENT_NATIONAL_ID).toString().replaceAll("'","''") + "' and " +
                CLIENT_PHONE + " = '" + values.get(CLIENT_PHONE).toString().replaceAll("'","''") + "'";

        db.update(TABLE_CLIENT, values, updateWhereClause, null);
        DBHelper.exportDB();
        // db.close();
        return true;
    }

    public boolean updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Calendar calendar = Calendar.getInstance();
        Timestamp oTimestamp = new Timestamp(calendar.getTime().getTime());
        DateFormat df = new android.text.format.DateFormat();
        user.set_timestamp(DateFormat.format(VMMC_DATE_TIME_FORMAT, oTimestamp).toString());
        Log.d(LOG, "updateUser timestamp: " + user.get_timestamp());
        values.put(USER_TIMESTAMP, user.get_timestamp());

        values.put(USER_USERNAME, user.get_username());
        values.put(USER_PASSWORD, user.get_password());
        values.put(USER_EMAIL, user.get_email());
        values.put(USER_FIRST_NAME, user.get_first_name());
        values.put(USER_LAST_NAME, user.get_last_name());
        values.put(USER_NATIONAL_ID, user.get_national_id());
        values.put(USER_PHONE, user.get_phone ());
        values.put(USER_REGION_ID, user.get_region_id());
        values.put(USER_USER_TYPE_ID, user.get_user_type_id());
        values.put(USER_LOCATION_ID, user.get_location_id());
        values.put(USER_MODIFIED_BY, user.get_modified_by());
        values.put(USER_CREATED_BY, user.get_created_by());
        values.put(USER_IS_BLOCKED, user.get_is_blocked());
        values.put(USER_TIMESTAMP_UPDATED, user.get_timestamp_updated());
        values.put(USER_TIMESTAMP_CREATED, user.get_timestamp_created());
        values.put(USER_TIMESTAMP_LAST_LOGIN, user.get_timestamp_last_login());

        String[] tableColumns = new String[]{
                USER_ID, USER_TIMESTAMP, USER_USERNAME, USER_PASSWORD, USER_EMAIL, USER_FIRST_NAME, USER_LAST_NAME, USER_NATIONAL_ID, USER_PHONE, USER_REGION_ID, USER_USER_TYPE_ID, USER_LOCATION_ID, USER_MODIFIED_BY, USER_CREATED_BY, USER_IS_BLOCKED, USER_TIMESTAMP_UPDATED, USER_TIMESTAMP_CREATED, USER_TIMESTAMP_LAST_LOGIN
        };

        String updateWhereClause = "1=1 and " +
                USER_USERNAME + " = '" + values.get(USER_USERNAME) + "' and " +
                USER_PHONE + " = '" + values.get(USER_PHONE) + "'";

        db.update(TABLE_USER, values, updateWhereClause, null);
        DBHelper.exportDB();
        // db.close();
        return true;
    }

    public boolean addInteraction(Interaction interaction) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        Calendar calendar = Calendar.getInstance();
        Timestamp oTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
        values.put(INTERACTION_TIMESTAMP, oTimestamp.toString());

        values.put(INTERACTION_FAC_FIRST_NAME, interaction.get_fac_first_name().replaceAll("'","''"));
        values.put(INTERACTION_FAC_LAST_NAME, interaction.get_fac_last_name().replaceAll("'","''"));
        values.put(INTERACTION_FAC_NATIONAL_ID, interaction.get_fac_national_id().replaceAll("'","''"));
        values.put(INTERACTION_FAC_PHONE,  interaction.get_fac_phone().replaceAll("'","''"));

        values.put(INTERACTION_PERSON_FIRST_NAME, interaction.get_person_first_name().replaceAll("'","''"));
        values.put(INTERACTION_PERSON_LAST_NAME, interaction.get_person_last_name().replaceAll("'","''"));
        values.put(INTERACTION_PERSON_NATIONAL_ID, interaction.get_person_national_id().replaceAll("'","''"));
        values.put(INTERACTION_PERSON_PHONE,  interaction.get_person_phone().replaceAll("'","''"));

        values.put(INTERACTION_DATE, interaction.get_interaction_date() );
        values.put(INTERACTION_FOLLOWUP_DATE, interaction.get_followup_date() );
        values.put(INTERACTION_TYPE, interaction.get_type_id() );
        values.put(INTERACTION_NOTE , interaction.get_note() );

        try {
            db.insert(TABLE_INTERACTION, null, values);
            DBHelper.exportDB();
        } catch (Exception ex) {
            // db.close();
            Log.d(LOG, "addInteraction catch " + ex.toString());
            return false;
        }
        return true;
    }

    public boolean updateInteraction(Interaction interaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Calendar calendar = Calendar.getInstance();
        Timestamp oTimestamp = new Timestamp(calendar.getTime().getTime());
        DateFormat df = new android.text.format.DateFormat();
        interaction.set_timestamp(DateFormat.format(VMMC_DATE_TIME_FORMAT, oTimestamp).toString());
        Log.d(LOG, "updateInteraction timestamp: " + interaction.get_timestamp());

        values.put(INTERACTION_ID, interaction.get_id() );
        values.put(INTERACTION_TIMESTAMP, interaction.get_timestamp() );
        values.put(INTERACTION_FAC_FIRST_NAME, interaction.get_fac_first_name() );
        values.put(INTERACTION_FAC_LAST_NAME, interaction.get_fac_last_name() );
        values.put(INTERACTION_FAC_NATIONAL_ID, interaction.get_fac_national_id() );
        values.put(INTERACTION_FAC_PHONE, interaction.get_fac_phone() );
        values.put(INTERACTION_PERSON_FIRST_NAME, interaction.get_person_first_name() );
        values.put(INTERACTION_PERSON_LAST_NAME, interaction.get_person_last_name() );
        values.put(INTERACTION_PERSON_NATIONAL_ID, interaction.get_person_national_id() );
        values.put(INTERACTION_PERSON_PHONE, interaction.get_person_phone() );
        values.put(INTERACTION_DATE, interaction.get_interaction_date() );
        values.put(INTERACTION_FOLLOWUP_DATE, interaction.get_followup_date() );
        values.put(INTERACTION_TYPE, interaction.get_type_id() );
        values.put(INTERACTION_NOTE , interaction.get_note() );

        String[] tableColumns = new String[]{
                INTERACTION_ID, INTERACTION_TIMESTAMP, INTERACTION_FAC_FIRST_NAME, INTERACTION_FAC_LAST_NAME, INTERACTION_FAC_NATIONAL_ID, INTERACTION_FAC_PHONE, INTERACTION_PERSON_FIRST_NAME, INTERACTION_PERSON_LAST_NAME, INTERACTION_PERSON_NATIONAL_ID, INTERACTION_PERSON_PHONE, INTERACTION_DATE, INTERACTION_FOLLOWUP_DATE, INTERACTION_TYPE, INTERACTION_NOTE
        };

        String updateWhereClause = "1=1 and " +
                INTERACTION_FAC_FIRST_NAME + " = '" + values.get(INTERACTION_FAC_FIRST_NAME) + "' and " +
                INTERACTION_FAC_LAST_NAME + " = '" + values.get(INTERACTION_FAC_LAST_NAME) + "' and " +
                INTERACTION_FAC_NATIONAL_ID + " = '" + values.get(INTERACTION_FAC_NATIONAL_ID) + "' and " +
                INTERACTION_FAC_PHONE + " = '" + values.get(INTERACTION_FAC_PHONE) + "' and " +
                INTERACTION_PERSON_FIRST_NAME + " = '" + values.get(INTERACTION_PERSON_FIRST_NAME) + "' and " +
                INTERACTION_PERSON_LAST_NAME + " = '" + values.get(INTERACTION_PERSON_LAST_NAME) + "' and " +
                INTERACTION_PERSON_NATIONAL_ID + " = '" + values.get(INTERACTION_PERSON_NATIONAL_ID) + "' and " +
                INTERACTION_PERSON_PHONE + " = '" + values.get(INTERACTION_PERSON_PHONE) + "' and " +
                INTERACTION_DATE + " = '" + values.get(INTERACTION_DATE) + "'";
//                INTERACTION_DATE + " = '" + values.get(INTERACTION_DATE) + "' and " +
//                INTERACTION_FOLLOWUP_DATE + " = '" + values.get(INTERACTION_FOLLOWUP_DATE) + "'";

        db.update(TABLE_INTERACTION, values, updateWhereClause, null);
        DBHelper.exportDB();
        // db.close();
        return true;
    }

    public boolean addPerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(PERSON_ID, person.get_id());
        Calendar calendar = Calendar.getInstance();
        Timestamp oTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
        values.put(PERSON_TIMESTAMP, oTimestamp.toString());

        values.put(PERSON_FIRST_NAME, person.get_first_name().replaceAll("'","''"));
        values.put(PERSON_LAST_NAME, person.get_last_name().replaceAll("'","''"));
        values.put(PERSON_NATIONAL_ID, person.get_national_id().replaceAll("'","''"));
        values.put(PERSON_ADDRESS,  person.get_address_id());
        values.put(PERSON_PHONE,  person.get_phone().replaceAll("'","''"));
        values.put(PERSON_DOB,  person.get_dob());
        values.put(PERSON_GENDER,  person.get_gender());
        values.put(PERSON_LATITUDE,  person.get_latitude());
        values.put(PERSON_LONGITUDE,  person.get_longitude());
        values.put(PERSON_IS_DELETED,  person.get_is_deleted());

        try {
            db.insert(TABLE_PERSON, null, values);
            DBHelper.exportDB();
        } catch (Exception ex) {
            // db.close();
            Log.d(LOG, "addPerson catch " + ex.toString());
            return false;
        }
        return true;
    }

    public Client getClient( String first_name, String last_name, String national_id, String phone ) {
        Client client = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(LOG, "getClient: " + first_name + ", " + last_name + ", " + national_id + ", " + phone);

        String[] tableColumns = new String[] {
                CLIENT_ID, CLIENT_TIMESTAMP, CLIENT_FIRST_NAME, CLIENT_LAST_NAME, CLIENT_NATIONAL_ID, CLIENT_PHONE, CLIENT_STATUS_ID, CLIENT_LOC_ID, CLIENT_LATITUDE, CLIENT_LONGITUDE, CLIENT_INSTITUTION_ID, CLIENT_GROUP_ACTIVITY_NAME, CLIENT_GROUP_ACTIVITY_DATE, CLIENT_FAC_FIRST_NAME, CLIENT_FAC_LAST_NAME, CLIENT_FAC_NATIONAL_ID, CLIENT_FAC_PHONE, CLIENT_ADDRESS_ID, CLIENT_DOB, CLIENT_GENDER, CLIENT_ORIGINATION, CLIENT_CREATED_BY, CLIENT_MODIFIED_BY, CLIENT_CREATED, CLIENT_MODIFIED
        };

        String whereClause = "1=1 and trim(" +
                CLIENT_FIRST_NAME + ") like trim(?) and trim(" +
                CLIENT_LAST_NAME + ") like trim(?) and trim(" +
                CLIENT_NATIONAL_ID + ") like trim(?) and trim(" +
                CLIENT_PHONE + ") like trim(?) ";

        Log.d(LOG, "getClient whereClause: " + whereClause);

        String[] whereArgs = new String [] {
                first_name, last_name, national_id, phone };
//                first_name, last_name, national_id, phone };

        Log.d(LOG, "getClient whereArgs:" + whereArgs[0] + ":" + whereArgs[1] + ":" + whereArgs[2] + ":" + whereArgs[3] + ":");

        Cursor cursor = db.query(TABLE_CLIENT, tableColumns, whereClause, whereArgs, null, null, null);

        if (cursor.moveToFirst()) {

            client = new Client(
                    parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    parseInt(cursor.getString(6)),
                    parseInt(cursor.getString(7)),
                    parseFloat(cursor.getString(8)),
                    parseFloat(cursor.getString(9)),
                    parseInt(cursor.getString(10)),
                    cursor.getString(11),
                    cursor.getString(12),

                    cursor.getString(13),
                    cursor.getString(14),
                    cursor.getString(15),
                    cursor.getString(16),

                    parseInt(cursor.getString(17)),
                    cursor.getString(18),
                    cursor.getString(19),
                    cursor.getString(20),

                    parseInt(cursor.getString(21)),
                    parseInt(cursor.getString(22)),
                    cursor.getString(23),
                    cursor.getString(24)
            );
            cursor.close();
            // db.close();
            return client;
        } else {
            cursor.close();
            // db.close();
            return client;
        }
    }

    public Client getClient( String national_id, String phone_number ) {
        Client client = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(LOG, "getClient: " + national_id + ", " + phone_number );

        String[] tableColumns = new String[] {
                CLIENT_ID, CLIENT_TIMESTAMP, CLIENT_FIRST_NAME, CLIENT_LAST_NAME, CLIENT_NATIONAL_ID, CLIENT_PHONE, CLIENT_STATUS_ID, CLIENT_LOC_ID, CLIENT_LATITUDE, CLIENT_LONGITUDE, CLIENT_INSTITUTION_ID, CLIENT_GROUP_ACTIVITY_NAME, CLIENT_GROUP_ACTIVITY_DATE, CLIENT_FAC_FIRST_NAME, CLIENT_FAC_LAST_NAME, CLIENT_FAC_NATIONAL_ID, CLIENT_FAC_PHONE, CLIENT_ADDRESS_ID, CLIENT_DOB, CLIENT_GENDER, CLIENT_ORIGINATION, CLIENT_CREATED_BY, CLIENT_MODIFIED_BY, CLIENT_CREATED, CLIENT_MODIFIED
        };

        String whereClause = "1=1 and trim(" +
                CLIENT_NATIONAL_ID + ") like ? or trim(" +
                CLIENT_PHONE + ") like ? ";

        Log.d(LOG, "getClient whereClause: " + whereClause);

        String[] whereArgs = new String [] {
                national_id, phone_number };

        Log.d(LOG, "getClient whereArgs:" + whereArgs[0] + ":" + whereArgs[1] + ":");

        Cursor cursor = db.query(TABLE_CLIENT, tableColumns, whereClause, whereArgs, null, null, null);

        if (cursor.moveToFirst()) {
//            Log.d(LOG, "getClient  "
//                    + cursor.getString(0) + " "
//                    + cursor.getString(1) + " "
//                    + cursor.getString(2) + " "
//                    + cursor.getString(3) + " "
//                    + cursor.getString(4) + " "
//                    + cursor.getString(5) + " "
//                    + cursor.getString(6) + " "
//                    + cursor.getString(7) + " "
//                    + cursor.getString(8) + " "
//                    + cursor.getString(9) + " "
//                    + cursor.getString(10) + " "
//            );

            client = new Client(
                    parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    parseInt(cursor.getString(6)),
                    parseInt(cursor.getString(7)),
                    parseFloat(cursor.getString(8)),
                    parseFloat(cursor.getString(9)),
                    parseInt(cursor.getString(10)),
                    cursor.getString(11),
                    cursor.getString(12),

                    cursor.getString(13),
                    cursor.getString(14),
                    cursor.getString(15),
                    cursor.getString(16),

                    parseInt(cursor.getString(17)),
                    cursor.getString(18),
                    cursor.getString(19),
                    cursor.getString(20),
                    parseInt(cursor.getString(21)),
                    parseInt(cursor.getString(22)),
                    cursor.getString(23),
                    cursor.getString(24)
            );
            cursor.close();
            // db.close();
            return client;
        } else {
            cursor.close();
            // db.close();
            return client;
        }
    }

    public List<Client> getAllClients() {
        List<Client> clientList = new ArrayList<Client>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CLIENT + " order by first_name, last_name ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor1 = null;
        cursor1 = db.rawQuery(selectQuery, null);
// GNR: very odd behavior, had to add following moveToFirst only for getAllClients, not getAllPersons or getAllBookings
        cursor1.moveToFirst();

//        Log.d(LOG, "getAllClients: " + cursor1.getString(1) + ":" + cursor1.getString(2) + ":" + cursor1.getString(3) + ":" + cursor1.getString(4) + ":" );

        // looping through all rows and adding to list
        if (cursor1.moveToFirst()) {
            do {
                /*
                Log.d(LOG, "getAllClients: loop: "
                        + cursor1.getString(1) + ":"
                        + cursor1.getString(2) + ":"
                        + cursor1.getString(3) + ":"
                        + cursor1.getString(4) + ":"
                        + cursor1.getString(5) + ":"
                        + cursor1.getString(6) + ":"
                        + cursor1.getString(7) + ":" );
                        */
                Client client = new Client();
                client.set_id(parseInt(cursor1.getString(0)));
                client.set_timestamp(cursor1.getString(1));
                client.set_first_name(cursor1.getString(2));
                client.set_last_name(cursor1.getString(3));
                client.set_national_id(cursor1.getString(4));
                client.set_phone(cursor1.getString(5));
                client.set_status_id(parseInt(cursor1.getString(6)));
                client.set_loc_id(parseInt(cursor1.getString(7)));
                client.set_latitude(parseFloat(cursor1.getString(8)));
                client.set_longitude(parseFloat(cursor1.getString(9)));
                client.set_institution_id(parseInt(cursor1.getString(10)));
                client.set_group_activity_name(cursor1.getString(11));
                client.set_group_activity_date(cursor1.getString(12));

                client.set_fac_first_name(cursor1.getString(13));
                client.set_fac_last_name(cursor1.getString(14));
                client.set_fac_national_id(cursor1.getString(15));
                client.set_fac_phone(cursor1.getString(16));

                client.set_address_id(parseInt(cursor1.getString(17)));
                client.set_dob(cursor1.getString(18));
                client.set_gender(cursor1.getString(19));
                client.set_origination(cursor1.getString(20));

                client.set_created_by(parseInt(cursor1.getString(21)));
                client.set_modified_by(parseInt(cursor1.getString(22)));
                client.set_created(cursor1.getString(23));
                client.set_modified(cursor1.getString(24));

                // Adding person to list
                clientList.add(client);
            } while (cursor1.moveToNext());
        }
        cursor1.close();
//        // db.close();
        return clientList;
    }

    public List<UserToAcl> getAllUserToAcls() {
        List<UserToAcl> _List = new ArrayList<UserToAcl>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USER_TO_ACL ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllUsers: loop: "
//                        + cursor1.getString(1) + ":"
//                        + cursor1.getString(2) + ":"
//                        + cursor1.getString(3) + ":"
//                        + cursor1.getString(4) + ":"
//                        + cursor1.getString(5) + ":"
//                        + cursor1.getString(6) + ":"
//                        + cursor1.getString(7) + ":" );
                UserToAcl userToAcl = new UserToAcl();
                userToAcl.set_id(parseInt(cursor.getString(0)));
                userToAcl.set_timestamp_created(cursor.getString(1));
                userToAcl.set_acl_id(cursor.getString(2));
                userToAcl.set_user_id(cursor.getString(3));
                userToAcl.set_created_by(cursor.getString(4));

                // Adding user to list
                _List.add(userToAcl);
            } while (cursor.moveToNext());
        }
        cursor.close();
//        // db.close();
        return _List;
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USER + " order by username ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllUsers: loop: " + cursor.getString(2));
                User user = new User();
                user.set_id(parseInt(cursor.getString(0)));
                user.set_timestamp(cursor.getString(1));
                user.set_username(cursor.getString(2));
                user.set_password(cursor.getString(3));
                user.set_email(cursor.getString(4));
                user.set_first_name(cursor.getString(5));
                user.set_last_name(cursor.getString(6));
                user.set_national_id(cursor.getString(7));
                user.set_phone(cursor.getString(8));
                user.set_region_id(parseInt(cursor.getString(9)));
                user.set_user_type_id(parseInt(cursor.getString(10)));
                user.set_location_id(parseInt(cursor.getString(11)));
                user.set_modified_by(parseInt(cursor.getString(12)));
                user.set_created_by(parseInt(cursor.getString(13)));
                user.set_is_blocked(parseInt(cursor.getString(14)));
                user.set_timestamp_updated(cursor.getString(15));
                user.set_timestamp_created(cursor.getString(16));
                user.set_timestamp_last_login(cursor.getString(17));
                // Adding user to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
//        // db.close();
        return userList;
    }

    public List<Client> getAllLikeClients(String index) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Client> clientList = new ArrayList<Client>();
        IndexParts indexParts = new IndexParts(index);

        String[] tableColumns = new String[] {
                CLIENT_ID, CLIENT_TIMESTAMP, CLIENT_FIRST_NAME, CLIENT_LAST_NAME, CLIENT_NATIONAL_ID, CLIENT_PHONE, CLIENT_STATUS_ID, CLIENT_LOC_ID, CLIENT_LATITUDE, CLIENT_LONGITUDE, CLIENT_INSTITUTION_ID, CLIENT_GROUP_ACTIVITY_NAME, CLIENT_GROUP_ACTIVITY_DATE, CLIENT_FAC_FIRST_NAME, CLIENT_FAC_LAST_NAME, CLIENT_FAC_NATIONAL_ID, CLIENT_FAC_PHONE, CLIENT_ADDRESS_ID, CLIENT_DOB, CLIENT_GENDER, CLIENT_ORIGINATION, CLIENT_CREATED_BY, CLIENT_MODIFIED_BY, CLIENT_CREATED, CLIENT_MODIFIED
        };

        String selectQuery =
                "select c.* from " + TABLE_CLIENT + " c\n" +
                        "join location l on c.loc_id = l.id \n" +
                        "join user u \n" +
                        "where 1=1 \n" +
                        "and l.region_id in " + getRegionString() + " \n" +
                        "and u.username = '" + MainActivity.USER_OBJ.get_username() + "'\n" +
                        "and trim(c." + CLIENT_FIRST_NAME + ") like ? " +
                        "and trim(c." + CLIENT_LAST_NAME + ") like ? " +
                        "and trim(c." + CLIENT_NATIONAL_ID + ") like ? " +
                        "and trim(c." +   CLIENT_PHONE + ") like ? ";

        String[] whereArgs = new String [] {
                "%" + indexParts.get_first_name() + "%", "%" + indexParts.get_last_name() + "%", "%" + indexParts.get_national_id() + "%", "%" +indexParts.get_phone() + "%" };

        Cursor cursor = db.rawQuery(selectQuery, whereArgs);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllClients: loop: "
//                        + cursor1.getString(1) + ":"
//                        + cursor1.getString(2) + ":"
//                        + cursor1.getString(3) + ":"
//                        + cursor1.getString(4) + ":"
//                        + cursor1.getString(5) + ":"
//                        + cursor1.getString(6) + ":"
//                        + cursor1.getString(7) + ":" );
                Client client = new Client();
                client.set_id(parseInt(cursor.getString(0)));
                client.set_timestamp(cursor.getString(1));
                client.set_first_name(cursor.getString(2));
                client.set_last_name(cursor.getString(3));
                client.set_national_id(cursor.getString(4));
                client.set_phone(cursor.getString(5));
                client.set_status_id(parseInt(cursor.getString(6)));
                client.set_loc_id(parseInt(cursor.getString(7)));
                client.set_latitude(parseFloat(cursor.getString(8)));
                client.set_longitude(parseFloat(cursor.getString(9)));
                client.set_institution_id(parseInt(cursor.getString(10)));
                client.set_group_activity_name(cursor.getString(11));
                client.set_group_activity_date(cursor.getString(12));

                client.set_fac_first_name(cursor.getString(13));
                client.set_fac_last_name(cursor.getString(14));
                client.set_fac_national_id(cursor.getString(15));
                client.set_fac_phone(cursor.getString(16));

                client.set_address_id(parseInt(cursor.getString(17)));
                client.set_dob(cursor.getString(18));
                client.set_gender(cursor.getString(19));
                client.set_origination(cursor.getString(20));

                client.set_created_by(parseInt(cursor.getString(21)));
                client.set_modified_by(parseInt(cursor.getString(22)));
                client.set_created(cursor.getString(23));
                client.set_modified(cursor.getString(24));

                // Adding person to list
                clientList.add(client);
            } while (cursor.moveToNext());
        }
        cursor.close();
//        // db.close();
        return clientList;
    }

    public List<User> getAllLikeUsers(String index) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<User> _List = new ArrayList<User>();
        String parts[] = index.split(":", 2);

        String[] tableColumns = new String[] {
                USER_ID, USER_TIMESTAMP, USER_USERNAME, USER_PASSWORD, USER_EMAIL, USER_FIRST_NAME, USER_LAST_NAME, USER_NATIONAL_ID, USER_PHONE, USER_REGION_ID, USER_USER_TYPE_ID, USER_LOCATION_ID, USER_MODIFIED_BY, USER_CREATED_BY, USER_IS_BLOCKED, USER_TIMESTAMP_UPDATED, USER_TIMESTAMP_CREATED, USER_TIMESTAMP_LAST_LOGIN
        };

        String whereClause = "1=1 and trim(" +
                USER_USERNAME + ") like ? and trim(" +
                USER_PHONE + ") like ? ";

        Log.d(LOG, "getUser whereClause: " + whereClause);

        String[] whereArgs = new String [] {
                "%" + parts[0] + "%", "%" + parts[1] + "%" };

        String orderBy = USER_USERNAME;

        Cursor cursor = db.query(TABLE_USER, tableColumns, whereClause, whereArgs, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllClients: loop: "
//                        + cursor1.getString(1) + ":"
//                        + cursor1.getString(2) + ":"
//                        + cursor1.getString(3) + ":"
//                        + cursor1.getString(4) + ":"
//                        + cursor1.getString(5) + ":"
//                        + cursor1.getString(6) + ":"
//                        + cursor1.getString(7) + ":" );
                User _user = new User();
                _user.set_id(parseInt(cursor.getString(0)));
                _user.set_timestamp(cursor.getString(1));
                _user.set_username(cursor.getString(2));
                _user.set_password(cursor.getString(3));
                _user.set_email(cursor.getString(4));
                _user.set_first_name(cursor.getString(5));
                _user.set_last_name(cursor.getString(6));
                _user.set_national_id(cursor.getString(7));
                _user.set_phone(cursor.getString(8));
                _user.set_region_id(parseInt(cursor.getString(9)));
                _user.set_user_type_id(parseInt(cursor.getString(10)));
                _user.set_location_id(parseInt(cursor.getString(11)));
                _user.set_modified_by(parseInt(cursor.getString(12)));
                _user.set_created_by(parseInt(cursor.getString(13)));
                _user.set_is_blocked(parseInt(cursor.getString(14)));
                _user.set_timestamp_updated(cursor.getString(15));
                _user.set_timestamp_created(cursor.getString(16));
                _user.set_timestamp_last_login(cursor.getString(17));

                _List.add(_user);
            } while (cursor.moveToNext());
        }
        cursor.close();
//        // db.close();
        return _List;
    }

    public List<Interaction> getAllInteractions() {
        List<Interaction> interactionList = new ArrayList<Interaction>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_INTERACTION;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery(selectQuery, null);

//        Log.d(LOG, "getAllInteractions: " + cursor1.getString(1) + ":" + cursor1.getString(2) + ":" + cursor1.getString(3) + ":" + cursor1.getString(4) + ":" );

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllClients: loop: "
//                        + cursor.getString(1) + ":"
//                        + cursor.getString(2) + ":"
//                        + cursor.getString(3) + ":"
//                        + cursor.getString(4) + ":"
//                        + cursor.getString(5) + ":"
//                        + cursor.getString(6) + ":"
//                        + cursor.getString(7) + ":" );
                Interaction interaction = new Interaction();
                interaction.set_id(parseInt(cursor.getString(0)));
                interaction.set_timestamp(cursor.getString(1));
                interaction.set_fac_first_name(cursor.getString(2));
                interaction.set_fac_last_name(cursor.getString(3));
                interaction.set_fac_national_id(cursor.getString(4));
                interaction.set_fac_phone(cursor.getString(5));
                interaction.set_person_first_name(cursor.getString(6));
                interaction.set_person_last_name(cursor.getString(7));
                interaction.set_person_national_id(cursor.getString(8));
                interaction.set_person_phone(cursor.getString(9));
                interaction.set_interaction_date(cursor.getString(10));
                interaction.set_followup_date(cursor.getString(11));
                interaction.set_type_id(parseInt(cursor.getString(12)));
                interaction.set_note(cursor.getString(13));

                // Adding person to list
                interactionList.add(interaction);
            } while (cursor.moveToNext());
        }
        cursor.close();
//        // db.close();
        return interactionList;
    }

    public List<Interaction> getAllLikeInteractions(String index) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Interaction> interactionList = new ArrayList<Interaction>();

        String[] parts = index.split("<>",3);
        String facIndex = parts[0];
        String personIndex = parts[1];
        String dateIndex = parts[2];
        String[] dates = dateIndex.split(":",2);
        String _interactionDate = dates[0];
        String _followupDate = dates[1];
        IndexParts facIndexParts = new IndexParts(facIndex);
        IndexParts personIndexParts = new IndexParts(personIndex);

        String[] tableColumns = new String[] {
                INTERACTION_ID, INTERACTION_TIMESTAMP, INTERACTION_FAC_FIRST_NAME, INTERACTION_FAC_LAST_NAME, INTERACTION_FAC_NATIONAL_ID, INTERACTION_FAC_PHONE, INTERACTION_PERSON_FIRST_NAME, INTERACTION_PERSON_LAST_NAME, INTERACTION_PERSON_NATIONAL_ID, INTERACTION_PERSON_PHONE, INTERACTION_DATE, INTERACTION_FOLLOWUP_DATE, INTERACTION_TYPE, INTERACTION_NOTE
        };

        String whereClause = "1=1 and trim(" +
                INTERACTION_FAC_FIRST_NAME + ") like ? and trim(" +
                INTERACTION_FAC_LAST_NAME + ") like ? and trim(" +
                INTERACTION_FAC_NATIONAL_ID + ") like ? and trim(" +
                INTERACTION_FAC_PHONE + ") like ? and trim(" +

                INTERACTION_PERSON_FIRST_NAME + ") like ? and trim(" +
                INTERACTION_PERSON_LAST_NAME + ") like ? and trim(" +
                INTERACTION_PERSON_NATIONAL_ID + ") like ? and trim(" +
                INTERACTION_PERSON_PHONE + ") like ? and trim(" +

                INTERACTION_DATE + ") like ? and trim(" +
                INTERACTION_FOLLOWUP_DATE + ") like ? ";

        Log.d(LOG, "getAllLikeInteractions whereClause: " + whereClause);

        String[] whereArgs = new String [] {
                "%" + facIndexParts.get_first_name() + "%", "%" + facIndexParts.get_last_name() + "%", "%" + facIndexParts.get_national_id() + "%", "%" +facIndexParts.get_phone() +
                "%", "%" + personIndexParts.get_first_name() + "%", "%" + personIndexParts.get_last_name() + "%", "%" + personIndexParts.get_national_id() + "%", "%" +personIndexParts.get_phone() +
                "%", "%" + _interactionDate + "%", "%" + _followupDate
        };

        Log.d(LOG, "getAllLikeInteractions whereArgs: " + whereArgs[0]);

        Cursor cursor = db.query(TABLE_INTERACTION, tableColumns, whereClause, whereArgs, null, null, null);
//        Cursor cursor1 = null;
//        cursor1 = db.rawQuery(selectQuery, null);
// GNR: very odd behavior, had to add following moveToFirst only for getAllClients, not getAllPersons or getAllBookings
//        cursor1.moveToFirst();

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllLikeInteractions: loop: "
//                        + cursor1.getString(1) + ":"
//                        + cursor1.getString(2) + ":"
//                        + cursor1.getString(3) + ":"
//                        + cursor1.getString(4) + ":"
//                        + cursor1.getString(5) + ":"
//                        + cursor1.getString(6) + ":"
//                        + cursor1.getString(7) + ":" );
                Interaction interaction = new Interaction();
                interaction.set_id(parseInt(cursor.getString(0)));
                interaction.set_timestamp(cursor.getString(1));
                interaction.set_fac_first_name(cursor.getString(2));
                interaction.set_fac_last_name(cursor.getString(3));
                interaction.set_fac_national_id(cursor.getString(4));
                interaction.set_fac_phone(cursor.getString(5));
                interaction.set_person_first_name(cursor.getString(6));
                interaction.set_person_last_name(cursor.getString(7));
                interaction.set_person_national_id(cursor.getString(8));
                interaction.set_person_phone(cursor.getString(9));
                interaction.set_interaction_date(cursor.getString(10));
                interaction.set_followup_date(cursor.getString(11));
                interaction.set_type_id(parseInt(cursor.getString(12)));
                interaction.set_note(cursor.getString(13));

                // Adding person to list
                interactionList.add(interaction);
            } while (cursor.moveToNext());
        }
        cursor.close();
//        // db.close();
        return interactionList;
    }

    public List<GroupActivity> getAllLikeGroupActivities(String index) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<GroupActivity> _List = new ArrayList<GroupActivity>();
        Log.d(LOG, "getAllLikeGroupActivities:index: " + index);

        String parts[] = index.split(":",2);
        String _name = parts[0];
        String _date = parts[1];

        String[] tableColumns = new String[] {
                GROUP_ACTIVITY_ID, GROUP_ACTIVITY_TIMESTAMP, GROUP_ACTIVITY_NAME, GROUP_ACTIVITY_LOCATION_ID, GROUP_ACTIVITY_ACTIVITY_DATE, GROUP_ACTIVITY_GROUP_TYPE_ID, GROUP_ACTIVITY_INSTITUTION_ID, GROUP_ACTIVITY_MALES, GROUP_ACTIVITY_FEMALES, GROUP_ACTIVITY_MESSAGES, GROUP_ACTIVITY_LATITUDE, GROUP_ACTIVITY_LONGITUDE
        };

        String selectQuery =
                "select ga.* from " + TABLE_GROUP_ACTIVITY + " ga\n" +
                        "join location l on ga.location_id = l.id \n" +
                        "join user u \n" +
                        "where 1=1 \n" +
                        "and l.region_id in " + getRegionString() + " \n" +
                        "and u.username = '" + MainActivity.USER_OBJ.get_username() + "'\n" +
                        "and trim(ga." + GROUP_ACTIVITY_NAME + ") like ? \n" +
                        "and trim(ga." + GROUP_ACTIVITY_ACTIVITY_DATE + ") like ? ";

        String[] whereArgs = new String [] {
                "%" +_name + "%", "%" + _date + "%"
        };

        Log.d(LOG, "getAllLikeGroupActivities selectQuery: " + selectQuery);
        Log.d(LOG, "getAllLikeGroupActivities whereArgs: " + whereArgs[0] + ", " + whereArgs[1]);

        Cursor cursor1 = db.rawQuery(selectQuery, whereArgs);

        if (cursor1.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllClients: loop: "
//                        + cursor1.getString(1) + ":"
//                        + cursor1.getString(2) + ":"
//                        + cursor1.getString(3) + ":"
//                        + cursor1.getString(4) + ":"
//                        + cursor1.getString(5) + ":"
//                        + cursor1.getString(6) + ":"
//                        + cursor1.getString(7) + ":" );
                GroupActivity group_activity = new GroupActivity();
                group_activity.set_id(parseInt(cursor1.getString(0)));
                group_activity.set_timestamp(cursor1.getString(1));
                group_activity.set_name(cursor1.getString(2));
                group_activity.set_location_id(parseInt(cursor1.getString(3)));
                group_activity.set_activity_date(cursor1.getString(4));
                group_activity.set_group_type_id(parseInt(cursor1.getString(5)));
                group_activity.set_institution_id(parseInt(cursor1.getString(6)));
                group_activity.set_males(parseInt(cursor1.getString(7)));
                group_activity.set_females(parseInt(cursor1.getString(8)));
                group_activity.set_messages(cursor1.getString(9));
                group_activity.set_latitude(parseFloat(cursor1.getString(10)));
                group_activity.set_longitude(parseFloat(cursor1.getString(11)));

                // Adding person to list
                _List.add(group_activity);
            } while (cursor1.moveToNext());
        }
        cursor1.close();
//        // db.close();
        return _List;
    }

    public List<Facilitator> getAllFacilitators() {
        List<Facilitator> facilitatorList = new ArrayList<Facilitator>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FACILITATOR + " order by first_name, last_name ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery(selectQuery, null);
// GNR: very odd behavior, had to add following moveToFirst only for getAllClients, not getAllPersons or getAllBookings
        cursor.moveToFirst();

//        Log.d(LOG, "getAllFacilitators: " + cursor1.getString(1) + ":" + cursor1.getString(2) + ":" + cursor1.getString(3) + ":" + cursor1.getString(4) + ":" );

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllFacilitators: loop: "
//                        + cursor1.getString(1) + ":"
//                        + cursor1.getString(2) + ":"
//                        + cursor1.getString(3) + ":"
//                        + cursor1.getString(4) + ":"
//                        + cursor1.getString(5) + ":"
//                        + cursor1.getString(6) + ":"
//                        + cursor1.getString(7) + ":" );

                Facilitator facilitator = new Facilitator();
                facilitator.set_id(parseInt(cursor.getString(0)));
                facilitator.set_timestamp(cursor.getString(1));
                facilitator.set_first_name(cursor.getString(2));
                facilitator.set_last_name(cursor.getString(3));
                facilitator.set_national_id(cursor.getString(4));
                facilitator.set_phone(cursor.getString(5));
                facilitator.set_facilitator_type_id(parseInt(cursor.getString(6)));
                facilitator.set_note(cursor.getString(7));
                facilitator.set_location_id(parseInt(cursor.getString(8)));
                facilitator.set_latitude(parseFloat(cursor.getString(9)));
                facilitator.set_longitude(parseFloat(cursor.getString(10)));
                facilitator.set_institution_id(parseInt(cursor.getString(11)));
                facilitator.set_address_id(parseInt(cursor.getString(12)));
                facilitator.set_dob(cursor.getString(13));
                facilitator.set_gender(cursor.getString(14));

                // Adding facilitator to list
                facilitatorList.add(facilitator);
            } while (cursor.moveToNext());
        }
        cursor.close();
//        // db.close();
        return facilitatorList;
    }

    public List<Facilitator> getAllLikeFacilitators(String index) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Facilitator> facilitatorList = new ArrayList<Facilitator>();
        IndexParts indexParts = new IndexParts(index);

        String[] tableColumns = new String[] {
                FACILITATOR_TYPE_ID, FACILITATOR_TIMESTAMP, FACILITATOR_FIRST_NAME, FACILITATOR_LAST_NAME, FACILITATOR_NATIONAL_ID, FACILITATOR_PHONE, FACILITATOR_FACILITATOR_TYPE_ID, FACILITATOR_NOTE, FACILITATOR_LOCATION_ID, FACILITATOR_LATITUDE, FACILITATOR_LONGITUDE, FACILITATOR_INSTITUTION_ID
        };

        String selectQuery =
                "select f.* from " + TABLE_FACILITATOR + " f\n" +
                        "join location l on f.location_id = l.id \n" +
                        "join user u \n" +
                        "where 1=1 \n" +
                        "and l.region_id in " + getRegionString() + " \n" +
                        "and u.username = '" + MainActivity.USER_OBJ.get_username() + "'\n" +
                        "and trim(f." + FACILITATOR_FIRST_NAME + ") like ? \n" +
                        "and trim(f." + FACILITATOR_LAST_NAME + ") like ? \n" +
                        "and trim(f." + FACILITATOR_NATIONAL_ID + ") like ? \n" +
                        "and trim(f." + FACILITATOR_PHONE + ") like ? ";

        String[] whereArgs = new String [] {
                "%" + indexParts.get_first_name() + "%", "%" + indexParts.get_last_name() + "%", "%" + indexParts.get_national_id() + "%", "%" +indexParts.get_phone() + "%" };

        Cursor cursor = db.rawQuery(selectQuery, whereArgs);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllFacilitators: loop: "
//                        + cursor1.getString(1) + ":"
//                        + cursor1.getString(2) + ":"
//                        + cursor1.getString(3) + ":"
//                        + cursor1.getString(4) + ":"
//                        + cursor1.getString(5) + ":"
//                        + cursor1.getString(6) + ":"
//                        + cursor1.getString(7) + ":" );

                Facilitator facilitator = new Facilitator();
                facilitator.set_id(parseInt(cursor.getString(0)));
                facilitator.set_timestamp(cursor.getString(1));
                facilitator.set_first_name(cursor.getString(2));
                facilitator.set_last_name(cursor.getString(3));
                facilitator.set_national_id(cursor.getString(4));
                facilitator.set_phone(cursor.getString(5));
                facilitator.set_facilitator_type_id(parseInt(cursor.getString(6)));
                facilitator.set_note(cursor.getString(7));
                facilitator.set_location_id(parseInt(cursor.getString(8)));
                facilitator.set_latitude(parseFloat(cursor.getString(9)));
                facilitator.set_longitude(parseFloat(cursor.getString(10)));
                facilitator.set_institution_id(parseInt(cursor.getString(11)));
                facilitator.set_address_id(parseInt(cursor.getString(12)));
                facilitator.set_dob(cursor.getString(13));
                facilitator.set_gender(cursor.getString(14));

                // Adding person to list
                facilitatorList.add(facilitator);
            } while (cursor.moveToNext());
        }
        cursor.close();
//        // db.close();
        return facilitatorList;
    }

    public boolean addFacilitator(Facilitator facilitator) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        Calendar calendar = Calendar.getInstance();
        Timestamp oTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
        values.put(FACILITATOR_TIMESTAMP, oTimestamp.toString());
        values.put(FACILITATOR_FIRST_NAME, facilitator.get_first_name());
        values.put(FACILITATOR_LAST_NAME, facilitator.get_last_name());
        values.put(FACILITATOR_NATIONAL_ID, facilitator.get_national_id());
        values.put(FACILITATOR_PHONE,  facilitator.get_phone());
        values.put(FACILITATOR_FACILITATOR_TYPE_ID,  facilitator.get_facilitator_type_id());
        values.put(FACILITATOR_NOTE,  facilitator.get_note());
        values.put(FACILITATOR_LOCATION_ID,  facilitator.get_location_id());
        values.put(FACILITATOR_LATITUDE,  facilitator.get_latitude());
        values.put(FACILITATOR_LONGITUDE,  facilitator.get_longitude());
        values.put(FACILITATOR_INSTITUTION_ID,  facilitator.get_institution_id());
        values.put(FACILITATOR_ADDRESS_ID,  facilitator.get_address_id());
        values.put(FACILITATOR_DOB,  facilitator.get_dob());
        values.put(FACILITATOR_GENDER,  facilitator.get_gender());

        try {
            db.insert(TABLE_FACILITATOR, null, values);
            DBHelper.exportDB();
        } catch (Exception ex) {
            // db.close();
            Log.d(LOG, "addFacilitator catch " + ex.toString());
            return false;
        }
        return true;
    }

    public boolean updateFacilitator(Facilitator facilitator) {
        Log.d(LOG, "updateFacilitator: " + facilitator.get_first_name() + ", " + facilitator.get_last_name() + ", " + facilitator.get_facilitator_type_id() );
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Calendar calendar = Calendar.getInstance();
        Timestamp oTimestamp = new Timestamp(calendar.getTime().getTime());
        DateFormat df = new android.text.format.DateFormat();
        facilitator.set_timestamp(DateFormat.format(VMMC_DATE_TIME_FORMAT, oTimestamp).toString());
        values.put(FACILITATOR_TIMESTAMP, oTimestamp.toString());
        values.put(FACILITATOR_FIRST_NAME, facilitator.get_first_name());
        values.put(FACILITATOR_LAST_NAME, facilitator.get_last_name());
        values.put(FACILITATOR_NATIONAL_ID, facilitator.get_national_id());
        values.put(FACILITATOR_PHONE,  facilitator.get_phone());
        values.put(FACILITATOR_FACILITATOR_TYPE_ID,  facilitator.get_facilitator_type_id());
        values.put(FACILITATOR_NOTE,  facilitator.get_note());
        values.put(FACILITATOR_LOCATION_ID,  facilitator.get_location_id());
        values.put(FACILITATOR_LATITUDE,  facilitator.get_latitude());
        values.put(FACILITATOR_LONGITUDE,  facilitator.get_longitude());
        values.put(FACILITATOR_INSTITUTION_ID,  facilitator.get_institution_id());
        values.put(FACILITATOR_ADDRESS_ID,  facilitator.get_address_id());
        values.put(FACILITATOR_DOB,  facilitator.get_dob());
        values.put(FACILITATOR_GENDER,  facilitator.get_gender());

        String[] tableColumns = new String[]{
                FACILITATOR_TIMESTAMP, FACILITATOR_FIRST_NAME, FACILITATOR_LAST_NAME, FACILITATOR_NATIONAL_ID, FACILITATOR_PHONE, FACILITATOR_FACILITATOR_TYPE_ID, FACILITATOR_NOTE, FACILITATOR_LOCATION_ID, FACILITATOR_LATITUDE, FACILITATOR_LONGITUDE, FACILITATOR_INSTITUTION_ID
        };

        String updateWhereClause = "1=1 and " +
                FACILITATOR_FIRST_NAME + " = '" + values.get(FACILITATOR_FIRST_NAME).toString().replaceAll("'","''") + "' and " +
                FACILITATOR_LAST_NAME + " = '" + values.get(FACILITATOR_LAST_NAME).toString().replaceAll("'","''") + "' and " +
                FACILITATOR_NATIONAL_ID + " = '" + values.get(FACILITATOR_NATIONAL_ID).toString().replaceAll("'","''") + "' and " +
                FACILITATOR_PHONE + " = '" + values.get(FACILITATOR_PHONE).toString().replaceAll("'","''") + "'";

        db.update(TABLE_FACILITATOR, values, updateWhereClause, null);
        DBHelper.exportDB();
        // db.close();
        return true;
    }

    public Facilitator getFacilitator( String first_name, String last_name, String national_id, String phone ) {
        Facilitator facilitator = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(LOG, "getFacilitator: " + first_name + ", " + last_name + ", " + national_id + ", " + phone);

        String[] tableColumns = new String[] {
                FACILITATOR_TYPE_ID, FACILITATOR_TIMESTAMP, FACILITATOR_FIRST_NAME, FACILITATOR_LAST_NAME, FACILITATOR_NATIONAL_ID, FACILITATOR_PHONE, FACILITATOR_FACILITATOR_TYPE_ID, FACILITATOR_NOTE, FACILITATOR_LOCATION_ID, FACILITATOR_LATITUDE, FACILITATOR_LONGITUDE, FACILITATOR_INSTITUTION_ID, FACILITATOR_ADDRESS_ID, FACILITATOR_DOB, FACILITATOR_GENDER
        };

        String whereClause = "1=1 and trim(" +
                FACILITATOR_FIRST_NAME + ") like ? and trim(" +
                FACILITATOR_LAST_NAME + ") like ? and trim(" +
                FACILITATOR_NATIONAL_ID + ") like ? and trim(" +
                FACILITATOR_PHONE + ") like ? ";

        Log.d(LOG, "getFacilitator whereClause: " + whereClause);

        String[] whereArgs = new String [] {
                first_name, last_name, national_id, phone };

        Log.d(LOG, "getFacilitor whereArgs:" + whereArgs[0] + ":" + whereArgs[1] + ":" + whereArgs[2] + ":" + whereArgs[3] + ":");

        Cursor cursor = db.query(TABLE_FACILITATOR, tableColumns, whereClause, whereArgs, null, null, null);
        cursor.moveToFirst();
        if (cursor.moveToFirst()) {

            facilitator = new Facilitator(
                    parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    parseInt(cursor.getString(6)),
                    cursor.getString(7),
                    parseInt(cursor.getString(8)),
                    parseFloat(cursor.getString(9)),
                    parseFloat(cursor.getString(10)),
                    parseInt(cursor.getString(11)),
                    parseInt(cursor.getString(12)),
                    cursor.getString(13),
                    cursor.getString(14)
            );
            cursor.close();
            // db.close();
            return facilitator;
        } else {
            cursor.close();
            // db.close();
            return facilitator;
        }
    }

    public Facilitator getFacilitator( String national_id, String phone_number ) {
        Facilitator facilitator = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(LOG, "getFacilitator: " + national_id + ", " + phone_number );

        String[] tableColumns = new String[] {
                FACILITATOR_ID, FACILITATOR_TIMESTAMP, FACILITATOR_FIRST_NAME, FACILITATOR_LAST_NAME, FACILITATOR_NATIONAL_ID, FACILITATOR_PHONE, FACILITATOR_FACILITATOR_TYPE_ID, FACILITATOR_NOTE, FACILITATOR_LOCATION_ID, FACILITATOR_LATITUDE, FACILITATOR_LONGITUDE, FACILITATOR_INSTITUTION_ID
        };

        String whereClause = "1=1 and trim(" +
                FACILITATOR_NATIONAL_ID + ") like ? or trim(" +
                FACILITATOR_PHONE + ") like ? ";

        Log.d(LOG, "getFacilitator whereClause: " + whereClause);

        String[] whereArgs = new String [] {
                national_id, phone_number };

        Log.d(LOG, "getFacilitator whereArgs:" + whereArgs[0] + ":" + whereArgs[1] + ":");

        Cursor cursor = db.query(TABLE_FACILITATOR, tableColumns, whereClause, whereArgs, null, null, null);

        if (cursor.moveToFirst()) {
//            Log.d(LOG, "getFacilitator  "
//                    + cursor.getString(0) + " "
//                    + cursor.getString(1) + " "
//                    + cursor.getString(2) + " "
//                    + cursor.getString(3) + " "
//                    + cursor.getString(4) + " "
//                    + cursor.getString(5) + " "
//                    + cursor.getString(6) + " "
//                    + cursor.getString(7) + " "
//                    + cursor.getString(8) + " "
//                    + cursor.getString(9) + " "
//                    + cursor.getString(10) + " "
//            );

            facilitator = new Facilitator(
                    parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    parseInt(cursor.getString(6)),
                    cursor.getString(7),
                    parseInt(cursor.getString(8)),
                    parseFloat(cursor.getString(9)),
                    parseFloat(cursor.getString(10)),
                    parseInt(cursor.getString(11)),
                    parseInt(cursor.getString(12)),
                    cursor.getString(13),
                    cursor.getString(14)
            );
            cursor.close();
            // db.close();
            return facilitator;
        } else {
            cursor.close();
            // db.close();
            return facilitator;
        }
    }

    public boolean addGroupActivity(GroupActivity groupActivity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        Calendar calendar = Calendar.getInstance();
        Timestamp oTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());

        values.put(GROUP_ACTIVITY_NAME, groupActivity.get_name().replaceAll("'","''"));
        values.put(GROUP_ACTIVITY_TIMESTAMP, oTimestamp.toString());
        values.put(GROUP_ACTIVITY_LOCATION_ID, groupActivity.get_location_id());
        values.put(GROUP_ACTIVITY_ACTIVITY_DATE, groupActivity.get_activity_date());
        values.put(GROUP_ACTIVITY_GROUP_TYPE_ID,  groupActivity.get_group_type_id());
        values.put(GROUP_ACTIVITY_INSTITUTION_ID,  groupActivity.get_institution_id());
        values.put(GROUP_ACTIVITY_MALES,  groupActivity.get_males());
        values.put(GROUP_ACTIVITY_FEMALES,  groupActivity.get_females());
        values.put(GROUP_ACTIVITY_MESSAGES,  groupActivity.get_messages());
        values.put(GROUP_ACTIVITY_LATITUDE,  groupActivity.get_latitude());
        values.put(GROUP_ACTIVITY_LONGITUDE,  groupActivity.get_longitude());

        try {
            db.insert(TABLE_GROUP_ACTIVITY, null, values);
            DBHelper.exportDB();
        } catch (Exception ex) {
            // db.close();
            Log.d(LOG, "addGroupActivity catch " + ex.toString());
            return false;
        }
        return true;
    }

    public boolean updateGroupActivity(GroupActivity groupActivity) {
        Log.d(LOG, "updateGroupActivity: " + groupActivity.get_name() + ", " + groupActivity.get_activity_date() );
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Calendar calendar = Calendar.getInstance();
        Timestamp oTimestamp = new Timestamp(calendar.getTime().getTime());
        DateFormat df = new android.text.format.DateFormat();
        groupActivity.set_timestamp(DateFormat.format(VMMC_DATE_TIME_FORMAT, oTimestamp).toString());

        values.put(GROUP_ACTIVITY_TIMESTAMP, oTimestamp.toString());
        values.put(GROUP_ACTIVITY_NAME, groupActivity.get_name());
        values.put(GROUP_ACTIVITY_LOCATION_ID, groupActivity.get_location_id());
        values.put(GROUP_ACTIVITY_ACTIVITY_DATE, groupActivity.get_activity_date());
        values.put(GROUP_ACTIVITY_GROUP_TYPE_ID,  groupActivity.get_group_type_id());
        values.put(GROUP_ACTIVITY_INSTITUTION_ID,  groupActivity.get_institution_id());
        values.put(GROUP_ACTIVITY_MALES,  groupActivity.get_males());
        values.put(GROUP_ACTIVITY_FEMALES,  groupActivity.get_females());
        values.put(GROUP_ACTIVITY_MESSAGES,  groupActivity.get_messages());
        values.put(GROUP_ACTIVITY_LATITUDE,  groupActivity.get_latitude());
        values.put(GROUP_ACTIVITY_LONGITUDE,  groupActivity.get_longitude());

        String[] tableColumns = new String[]{
                GROUP_ACTIVITY_TIMESTAMP, GROUP_ACTIVITY_NAME, GROUP_ACTIVITY_LOCATION_ID, GROUP_ACTIVITY_ACTIVITY_DATE, GROUP_ACTIVITY_GROUP_TYPE_ID, GROUP_ACTIVITY_INSTITUTION_ID, GROUP_ACTIVITY_MALES, GROUP_ACTIVITY_FEMALES, GROUP_ACTIVITY_MESSAGES, GROUP_ACTIVITY_LATITUDE, GROUP_ACTIVITY_LONGITUDE
        };

        String whereClause = "1=1 and trim(" +
                GROUP_ACTIVITY_NAME + ") like ? or trim(" +
                GROUP_ACTIVITY_ACTIVITY_DATE + ") like ? ";

        Log.d(LOG, "updateGroupActivity whereClause: " + whereClause);

        String[] whereArgs = new String[]{
                groupActivity.get_name(), groupActivity.get_activity_date() };

        String updateWhereClause = "1=1 and " +
                GROUP_ACTIVITY_NAME + " = '" + values.get(GROUP_ACTIVITY_NAME) + "' and " +
                GROUP_ACTIVITY_ACTIVITY_DATE + " = '" + values.get(GROUP_ACTIVITY_ACTIVITY_DATE) + "'";

        db.update(TABLE_GROUP_ACTIVITY, values, updateWhereClause, null);
        DBHelper.exportDB();
        // db.close();
        return true;
    }

    public GroupActivity getGroupActivity( String name, String activity_date ) {
        GroupActivity groupActivity = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(LOG, "getGroupActivity: " + name + ", " + activity_date  );

        String[] tableColumns = new String[] {
                GROUP_ACTIVITY_ID, GROUP_ACTIVITY_NAME, GROUP_ACTIVITY_TIMESTAMP, GROUP_ACTIVITY_LOCATION_ID, GROUP_ACTIVITY_ACTIVITY_DATE, GROUP_ACTIVITY_GROUP_TYPE_ID, GROUP_ACTIVITY_INSTITUTION_ID, GROUP_ACTIVITY_MALES, GROUP_ACTIVITY_FEMALES, GROUP_ACTIVITY_MESSAGES, GROUP_ACTIVITY_LATITUDE, GROUP_ACTIVITY_LONGITUDE
        };

        String whereClause = "1=1 and trim(" +
                GROUP_ACTIVITY_NAME + ") like ? and trim(" +
                GROUP_ACTIVITY_ACTIVITY_DATE + ") like ? ";

        Log.d(LOG, "getGroupActivity whereClause: " + whereClause);

        String[] whereArgs = new String [] {
                name, activity_date };

        Log.d(LOG, "getGroupActivity whereArgs:" + whereArgs[0] + ":" + whereArgs[1] );

        Cursor cursor = db.query(TABLE_GROUP_ACTIVITY, tableColumns, whereClause, whereArgs, null, null, null);
        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
//            Log.d(LOG, "getGroupActivity  "
//                            + cursor.getString(0) + " "
//                            + cursor.getString(1) + " "
//                            + cursor.getString(2) + " "
//                            + cursor.getString(3) + " "
//                            + cursor.getString(4) + " "
//                            + cursor.getString(5) + " "
//                            + cursor.getString(6) + " "
//                    + cursor.getString(7) + " "
//                    + cursor.getString(8) + " "
//                    + cursor.getString(9) + " "
//                    + cursor.getString(10) + " "
//            );

            groupActivity = new GroupActivity(
                    parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    parseInt(cursor.getString(3)),
                    cursor.getString(4),
                    parseInt(cursor.getString(5)),
                    parseInt(cursor.getString(6)),
                    parseInt(cursor.getString(7)),
                    parseInt(cursor.getString(8)),
                    cursor.getString(9),
                    parseFloat(cursor.getString(10)),
                    parseFloat(cursor.getString(11))
            );
            cursor.close();
            // db.close();
            return groupActivity;
        } else {
            cursor.close();
            // db.close();
            return groupActivity;
        }
    }


    public Booking getBooking( String first_name, String last_name, String national_id, String phone, String projected_date) {

        Booking booking = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(LOG, "getBooking5: " + first_name + ", " + last_name + ", " + national_id + ", " + phone + ", " + projected_date );
//        DateFormat df = new android.text.format.DateFormat();
//        String sProjectedDate = df.format(VMMC_DATE_TIME_FORMAT, projected_date).toString();

        String[] tableColumns = new String[] {
                BOOKING_ID, BOOKING_TIMESTAMP, BOOKING_FIRST_NAME, BOOKING_LAST_NAME, BOOKING_NATIONAL_ID, BOOKING_PHONE, BOOKING_LOCATION_ID, BOOKING_LATITUDE, BOOKING_LONGITUDE, BOOKING_PROJECTED_DATE, BOOKING_ACTUAL_DATE, BOOKING_CONSENT, BOOKING_PROCEDURE_TYPE_ID, BOOKING_FOLLOWUP_ID, BOOKING_FOLLOWUP_DATE, BOOKING_ALT_CONTACT
        };

        String whereClause = "1=1 and trim(" +
                BOOKING_FIRST_NAME + ") like ? and trim(" +
                BOOKING_LAST_NAME + ") like ? and trim(" +
                BOOKING_NATIONAL_ID + ") like ? and trim(" +
                BOOKING_PHONE + ") like ? and trim(" +
                BOOKING_PROJECTED_DATE + ") like ? ";


        Log.d(LOG, "getBooking whereClause: " + whereClause);

        String[] whereArgs = new String [] {
                first_name, last_name, national_id, phone,  projected_date};

        Log.d(LOG, "getBooking whereArgs:" + whereArgs[0] + ":" + whereArgs[1] + ":" + whereArgs[2] + ":" + whereArgs[3] + ":" + whereArgs[4] + ":");

        Cursor cursor = db.query(TABLE_BOOKING, tableColumns, whereClause, whereArgs, null, null, null);


        if (cursor.moveToFirst()) {
            booking = new Booking(
                    parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),

                    parseInt(cursor.getString(6)),
                    parseFloat(cursor.getString(7)),
                    parseFloat(cursor.getString(8)),
                    cursor.getString(9),
                    cursor.getString(10),
                    cursor.getString(11),
                    parseInt(cursor.getString(12)),
                    parseInt(cursor.getString(13)),
                    cursor.getString(14),
                    cursor.getString(15)
//                    parseFloat(cursor.getString(9)),
//                    parseInt(cursor.getString(10))
            );
            cursor.close();
            // db.close();
            return booking;
        } else {
//            cursor.close();
            // db.close();
            return booking;
        }
    }

    public Interaction getInteraction( IndexParts facParts, IndexParts personParts, String interactionDate, String followupDate) {
        Interaction interaction = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(LOG, "getInteraction: " );

        String[] tableColumns = new String[] {
                INTERACTION_ID, INTERACTION_TIMESTAMP, INTERACTION_FAC_FIRST_NAME, INTERACTION_FAC_LAST_NAME, INTERACTION_FAC_NATIONAL_ID, INTERACTION_FAC_PHONE, INTERACTION_PERSON_FIRST_NAME, INTERACTION_PERSON_LAST_NAME, INTERACTION_PERSON_NATIONAL_ID, INTERACTION_PERSON_PHONE, INTERACTION_DATE, INTERACTION_FOLLOWUP_DATE, INTERACTION_TYPE, INTERACTION_NOTE
        };

        String whereClause = "1=1 and trim(" +
                INTERACTION_FAC_FIRST_NAME + ") like ? and trim(" +
                INTERACTION_FAC_LAST_NAME + ") like ? and trim(" +
                INTERACTION_FAC_NATIONAL_ID + ") like ? and trim(" +
                INTERACTION_FAC_PHONE + ") like ? and trim(" +
                INTERACTION_PERSON_FIRST_NAME + ") like ? and trim(" +
                INTERACTION_PERSON_LAST_NAME + ") like ? and trim(" +
                INTERACTION_PERSON_NATIONAL_ID + ") like ? and trim(" +
                INTERACTION_PERSON_PHONE + ") like ? and trim(" +
                INTERACTION_DATE + ") like ? ";
//                INTERACTION_DATE + ") like ? and trim(" +
//                INTERACTION_FOLLOWUP_DATE + ") like ? ";


        Log.d(LOG, "getInteraction whereClause: " + whereClause);

        String[] whereArgs = new String [] {facParts.get_first_name(), facParts.get_last_name(), facParts.get_national_id(), facParts.get_phone(),
                personParts.get_first_name(), personParts.get_last_name(), personParts.get_national_id(), personParts.get_phone(),
                interactionDate
        };

        Log.d(LOG, "getInteraction whereArgs:" + whereArgs[0] + ":" + whereArgs[1] + ":" + whereArgs[2] + ":" + whereArgs[3] + ":" + whereArgs[4] + ":");

        Cursor cursor = db.query(TABLE_INTERACTION, tableColumns, whereClause, whereArgs, null, null, null);


        if (cursor.moveToFirst()) {
//            Log.d(LOG, "getBooking  "
//                    + cursor.getString(0) + " "
//                    + cursor.getString(1) + " "
//                    + cursor.getString(2) + " "
//                    + cursor.getString(3) + " "
//                    + cursor.getString(4) + " "
//                    + cursor.getString(5) + " "
//                    + cursor.getString(6) + " "
//                    + cursor.getString(7) + " "
//                    + cursor.getString(8) + " "
//                    + cursor.getString(9) + " "
//                    + cursor.getString(10) + " "
//            );

            interaction = new Interaction(
                    parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9),
                    cursor.getString(10),
                    cursor.getString(11),
                    parseInt(cursor.getString(12)),
                    cursor.getString(13)

//                    parseFloat(cursor.getString(9)),
//                    parseInt(cursor.getString(10))
            );
            cursor.close();
            // db.close();
            return interaction;
        } else {
            cursor.close();
            // db.close();
            return interaction;
        }
    }

    public Booking getBooking( String national_id, String phone_number, String projected_date ) {
        Booking booking = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(LOG, "getBooking2: " + national_id + ", " + phone_number );
        Person person = getPerson(national_id, phone_number);
        Client client = getClient(person.get_first_name(), person.get_last_name(), person.get_national_id(), person.get_phone());
        booking = getBooking(person.get_first_name(), person.get_last_name(), person.get_national_id(), person.get_phone(), projected_date);
        return booking;
    }

    public boolean deleteBooking( String first_name, String last_name, String national_id, String phone_number ){
        SQLiteDatabase db = this.getWritableDatabase();

        try {

            String whereClause = "1=1 and trim(" +
                    BOOKING_FIRST_NAME + ") like ? and trim(" +
                    BOOKING_LAST_NAME + ") like ? and trim(" +
//                BOOKING_NATIONAL_ID + ") like ? and trim(" +
                    BOOKING_PHONE + ") like ? ";

            String[] whereArgs = new String[]{ first_name, last_name, phone_number };
            db.delete(TABLE_BOOKING, whereClause, whereArgs);
        } catch (Exception ex) {
            // db.close();
            Log.d(LOG, "deleteBooking catch " + ex.toString());
            return false;
        }
        return true;
    }


    public boolean addBooking(Booking booking) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(PERSON_ID, person.get_id());
        Calendar calendar = Calendar.getInstance();
        Timestamp oTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
        values.put(BOOKING_TIMESTAMP, oTimestamp.toString());

        values.put(BOOKING_FIRST_NAME, booking.get_first_name());
        values.put(BOOKING_LAST_NAME, booking.get_last_name());
        values.put(BOOKING_NATIONAL_ID, booking.get_national_id());
        values.put(BOOKING_PHONE,  booking.get_phone());

        values.put(BOOKING_LOCATION_ID, booking.get_location_id());

        values.put(BOOKING_LATITUDE, booking.get_latitude());
        values.put(BOOKING_LONGITUDE, booking.get_longitude());

        values.put(BOOKING_PROJECTED_DATE,  booking.get_projected_date());
        values.put(BOOKING_ACTUAL_DATE,  booking.get_actual_date());

        values.put(BOOKING_CONSENT,  booking.get_consent());
        values.put(BOOKING_PROCEDURE_TYPE_ID,  booking.get_procedure_type_id());
        values.put(BOOKING_FOLLOWUP_ID,  booking.get_followup_id());
        values.put(BOOKING_FOLLOWUP_DATE,  booking.get_followup_date());
        values.put(BOOKING_ALT_CONTACT,  booking.get_alt_contact());

        try {
            db.insert(TABLE_BOOKING, null, values);
            DBHelper.exportDB();
        } catch (Exception ex) {
            // db.close();
            Log.d(LOG, "addBooking catch " + ex.toString());
            return false;
        }
        return true;
    }

    public boolean updateBooking(Booking booking) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(PERSON_ID, person.get_id());
        Calendar calendar = Calendar.getInstance();
        Timestamp oTimestamp = new Timestamp(calendar.getTime().getTime());
        DateFormat df = new android.text.format.DateFormat();
        booking.set_timestamp(DateFormat.format(VMMC_DATE_TIME_FORMAT, oTimestamp).toString());
        Log.d(LOG, "updateBooking timestamp: " + booking.get_timestamp());

        String[] tableColumns = new String[]{
                BOOKING_ID, BOOKING_TIMESTAMP, BOOKING_FIRST_NAME, BOOKING_LAST_NAME, BOOKING_NATIONAL_ID, BOOKING_PHONE, BOOKING_LOCATION_ID, BOOKING_LATITUDE, BOOKING_LONGITUDE, BOOKING_PROJECTED_DATE, BOOKING_ACTUAL_DATE, BOOKING_CONSENT, BOOKING_PROCEDURE_TYPE_ID, BOOKING_FOLLOWUP_ID, BOOKING_FOLLOWUP_DATE, BOOKING_ALT_CONTACT
        };

        String whereClause = "1=1 and trim(" +
                BOOKING_FIRST_NAME + ") like ? and trim(" +
                BOOKING_LAST_NAME + ") like ? and trim(" +
//                BOOKING_NATIONAL_ID + ") like ? and trim(" +
                BOOKING_PHONE + ") like ? and trim(" +
                BOOKING_PROJECTED_DATE + ") like ? ";

        Log.d(LOG, "updateBooking whereClause: " + whereClause);

        String[] whereArgs = new String[]{
                booking.get_first_name(), booking.get_last_name(), booking.get_phone(), booking.get_projected_date()};

        Log.d(LOG, "updateBooking whereArgs: " + whereArgs[0] + ":" + whereArgs[1] + ":" + whereArgs[2] + ":" + whereArgs[3]);

        Cursor cursor = db.query(TABLE_BOOKING, tableColumns, whereClause, whereArgs, null, null, null);

        if (cursor.moveToFirst()) {

//            Log.d(LOG, "dbHelp.updateBooking  "
//                    + cursor.getString(0) + " "
//                    + cursor.getString(1) + " "
//                    + cursor.getString(2) + " "
//                    + cursor.getString(3) + " "
//                    + cursor.getString(4) + " "
//                    + cursor.getString(5) + " "
//                    + cursor.getString(6) + " "
//                    + cursor.getString(7) + " "
//            );

            values.put(BOOKING_ID, parseInt(cursor.getString(0)));
            values.put(BOOKING_TIMESTAMP, oTimestamp.toString());
            values.put(BOOKING_FIRST_NAME, booking.get_first_name());
            values.put(BOOKING_LAST_NAME, booking.get_last_name());
            values.put(BOOKING_NATIONAL_ID, booking.get_national_id());
            values.put(BOOKING_PHONE,  booking.get_phone());

            values.put(BOOKING_LOCATION_ID, booking.get_location_id());

            values.put(BOOKING_LATITUDE, booking.get_latitude());
            values.put(BOOKING_LONGITUDE, booking.get_longitude());

            values.put(BOOKING_PROJECTED_DATE,  booking.get_projected_date());
            values.put(BOOKING_ACTUAL_DATE,  booking.get_actual_date());

            values.put(BOOKING_CONSENT,  booking.get_consent());
            values.put(BOOKING_PROCEDURE_TYPE_ID,  booking.get_procedure_type_id());
            values.put(BOOKING_FOLLOWUP_ID,  booking.get_followup_id());
            values.put(BOOKING_FOLLOWUP_DATE,  booking.get_followup_date());
            values.put(BOOKING_ALT_CONTACT,  booking.get_alt_contact());

            cursor.close();

            String updateWhereClause = "1=1 and " + BOOKING_ID + " = " + values.get(BOOKING_ID);
            Log.d(LOG, "updateBooking 2 updateWhereClause: " + updateWhereClause);
            Log.d(LOG, "updateBooking 2 updateWhereClause: " +
                    values.get(BOOKING_ID) + " " +
                    values.get(BOOKING_TIMESTAMP) + " " +
                    values.get(BOOKING_FIRST_NAME) + " " +
                    values.get(BOOKING_LAST_NAME) + " " +
                    values.get(BOOKING_NATIONAL_ID) + " " +
                    values.get(BOOKING_PHONE) + " " +

                    values.get(BOOKING_LOCATION_ID) + " " +
                    values.get(BOOKING_LATITUDE) + " " +
                    values.get(BOOKING_LONGITUDE) + " " +
                    values.get(BOOKING_PROJECTED_DATE) + " " +
                    values.get(BOOKING_ACTUAL_DATE) + " " +
                    values.get(BOOKING_CONSENT) + " " +
                    values.get(BOOKING_PROCEDURE_TYPE_ID) + " " +
                    values.get(BOOKING_FOLLOWUP_ID) + " " +
                    values.get(BOOKING_FOLLOWUP_DATE) + " " +
                    values.get(BOOKING_ALT_CONTACT)
            );
            db.update(TABLE_BOOKING, values, updateWhereClause, null);
            DBHelper.exportDB();
            // db.close();
            return true;

        } else {
            cursor.close();
            // db.close();
            return false;
        }
    }

    public Person getPerson(String person_first_name, String person_last_name, String person_phone ) {
        Person person = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(LOG, "getPerson: " + person_first_name + " " + person_last_name + " " + person_phone );

        String[] tableColumns = new String[] {
                PERSON_ID, PERSON_FIRST_NAME, PERSON_LAST_NAME, PERSON_NATIONAL_ID, PERSON_ADDRESS, PERSON_PHONE, PERSON_DOB, PERSON_GENDER, PERSON_LATITUDE, PERSON_LONGITUDE, PERSON_IS_DELETED
        };

        String whereClause = "1=1 and trim(" +
                PERSON_FIRST_NAME + ") like ? and trim(" +
                PERSON_LAST_NAME + ") like ? and trim(" +
                PERSON_PHONE + ") like ? ";

        Log.d(LOG, "getPerson whereClause: " + whereClause);

        String[] whereArgs = new String [] {
                person_first_name, person_last_name, person_phone };

        Log.d(LOG, "getPerson whereArgs:" + whereArgs[0] + ":" + whereArgs[1] + ":" + whereArgs[2] + ":");

        Cursor cursor = db.query(TABLE_PERSON, tableColumns, whereClause, whereArgs, null, null, null);

        if (cursor.moveToFirst()) {


            person = new Person(
                    parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    parseInt(cursor.getString(4)),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    parseFloat(cursor.getString(8)),
                    parseFloat(cursor.getString(9)),
                    parseInt(cursor.getString(10))
            );
            cursor.close();
            // db.close();
            return person;
        } else {
            cursor.close();
            // db.close();
            return person;
        }
    }

    public Person getPerson( String national_id, String phone_number ) {
        Person person = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(LOG, "getPerson: " + national_id + ", " + phone_number );

        String[] tableColumns = new String[] {
                PERSON_ID, PERSON_FIRST_NAME, PERSON_LAST_NAME, PERSON_NATIONAL_ID, PERSON_ADDRESS, PERSON_PHONE, PERSON_DOB, PERSON_GENDER, PERSON_LATITUDE, PERSON_LONGITUDE, PERSON_IS_DELETED
        };

        String whereClause = "1=1 and trim(" +
                PERSON_NATIONAL_ID + ") like ? or trim(" +
                PERSON_PHONE + ") like ? ";

        Log.d(LOG, "getPerson whereClause: " + whereClause);

        String[] whereArgs = new String [] {
                national_id, phone_number };

        Log.d(LOG, "getPerson whereArgs:" + whereArgs[0] + ":" + whereArgs[1] + ":");

        Cursor cursor = db.query(TABLE_PERSON, tableColumns, whereClause, whereArgs, null, null, null);

        if (cursor.moveToFirst()) {
            Log.d(LOG, "getPerson  "
                    + cursor.getString(0) + " "
                    + cursor.getString(1) + " "
                    + cursor.getString(2) + " "
                    + cursor.getString(3) + " "
                    + cursor.getString(4) + " "
                    + cursor.getString(5) + " "
                    + cursor.getString(6) + " "
                    + cursor.getString(7) + " "
                    + cursor.getString(8) + " "
                    + cursor.getString(9) + " "
                    + cursor.getString(10) + " "
            );

            person = new Person(
                    parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    parseInt(cursor.getString(4)),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    parseFloat(cursor.getString(8)),
                    parseFloat(cursor.getString(9)),
                    parseInt(cursor.getString(10))
            );
            cursor.close();
            // db.close();
            return person;
        } else {
            cursor.close();
            // db.close();
            return person;
        }
    }

    public Person getPerson(int person_id) {
        Person person = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String[] tableColumns = new String[] {
                PERSON_ID, PERSON_FIRST_NAME, PERSON_LAST_NAME, PERSON_NATIONAL_ID, PERSON_ADDRESS, PERSON_PHONE, PERSON_DOB, PERSON_GENDER, PERSON_LATITUDE, PERSON_LONGITUDE, PERSON_IS_DELETED
        };

        String whereClause = "1=1 and " +
                PERSON_ID + " = ?";

        String[] whereArgs = new String[]{
                Integer.toString(person_id) };

        Cursor cursor = db.query(TABLE_PERSON, tableColumns, whereClause, whereArgs, null, null, null);
        Log.d(LOG, "getPerson:where:  " + whereClause + " " + whereArgs);
        if (cursor.moveToFirst()) {
            Log.d(LOG, "getPerson  "
                    + cursor.getString(0) + " "
                    + cursor.getString(1) + " "
                    + cursor.getString(2) + " "
                    + cursor.getString(3) + " "
                    + cursor.getString(4) + " "
                    + cursor.getString(5) + " "
                    + cursor.getString(6) + " "
                    + cursor.getString(7) + " "
                    + cursor.getString(8) + " "
                    + cursor.getString(9) + " "
                    + cursor.getString(10) + " "
            );

            person = new Person(
                    parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    parseInt(cursor.getString(4)),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    parseFloat(cursor.getString(8)),
                    parseFloat(cursor.getString(9)),
                    parseInt(cursor.getString(10))
            );
            cursor.close();
            // db.close();
            return person;
        } else {
            cursor.close();
            // db.close();
            return person;
        }
    }

    public List<Person> getAllPersons() {
        List<Person> personList = new ArrayList<Person>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PERSON + " order by first_name, last_name ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllPersons: loop: " + cursor.getString(1));
                Person person = new Person();
                person.set_id(parseInt(cursor.getString(0)));
                person.set_timestamp(cursor.getString(1));
                person.set_first_name(cursor.getString(2));
                person.set_last_name(cursor.getString(3));
                person.set_national_id(cursor.getString(4));
                person.set_address_id(parseInt(cursor.getString(5)));
                person.set_phone(cursor.getString(6));
                person.set_dob(cursor.getString(7));
                person.set_gender(cursor.getString(8));
                person.set_latitude(parseFloat(cursor.getString(9)));
                person.set_longitude(parseFloat(cursor.getString(10)));
                person.set_is_deleted(parseInt(cursor.getString(11)));

                // Adding person to list
                personList.add(person);
            } while (cursor.moveToNext());
        }
        cursor.close();
//        // db.close();
        // return person list
        return personList;
    }

    public List<Person> getAllLikePersons(String index) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Person> personList = new ArrayList<Person>();

        IndexParts indexParts = new IndexParts(index);

        Log.d(LOG, "getAllLikePersons indexParts: " +
                indexParts.get_first_name() + ", " + indexParts.get_last_name() + ", " + indexParts.get_national_id() + ", " + indexParts.get_phone());

        String[] tableColumns = new String[] {
                PERSON_ID, PERSON_TIMESTAMP, PERSON_FIRST_NAME, PERSON_LAST_NAME, PERSON_NATIONAL_ID, PERSON_ADDRESS, PERSON_PHONE, PERSON_DOB, PERSON_GENDER, PERSON_LATITUDE, PERSON_LONGITUDE, PERSON_IS_DELETED
        };

        String selectQuery =
                "select p.* from " + TABLE_PERSON + " p\n" +
                        "join address a on p.address_id = a.id \n" +
                        "join user u \n" +
                        "where 1=1 \n" +
                        "and a.region_id in " + getRegionString() + " \n" +
                        "and u.username = '" + MainActivity.USER_OBJ.get_username() + "'\n" +
                        "and trim(p." + PERSON_FIRST_NAME + ") like ? \n" +
                        "and trim(p." + PERSON_LAST_NAME + ") like ? \n" +
                        "and trim(p." + PERSON_NATIONAL_ID + ") like ? \n" +
                        "and trim(p." + PERSON_PHONE + ") like ? ";

        String[] whereArgs = new String [] {
                "%" + indexParts.get_first_name() + "%", "%" + indexParts.get_last_name() + "%", "%" + indexParts.get_national_id() + "%", "%" +indexParts.get_phone() + "%" };

        Cursor cursor = db.rawQuery(selectQuery, whereArgs);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllPersons: loop: " + cursor.getString(1));
                Person person = new Person();
                person.set_id(parseInt(cursor.getString(0)));
                person.set_timestamp(cursor.getString(1));
                person.set_first_name(cursor.getString(2));
                person.set_last_name(cursor.getString(3));
                person.set_national_id(cursor.getString(4));
                person.set_address_id(parseInt(cursor.getString(5)));
                person.set_phone(cursor.getString(6));
                person.set_dob(cursor.getString(7));
                person.set_gender(cursor.getString(8));
                person.set_latitude(parseFloat(cursor.getString(9)));
                person.set_longitude(parseFloat(cursor.getString(10)));
                person.set_is_deleted(parseInt(cursor.getString(11)));

                // Adding person to list
                personList.add(person);
            } while (cursor.moveToNext());
        }
        cursor.close();
//        // db.close();
        // return person list
        return personList;
    }

    public List<Booking> getAllBookings() {
        List<Booking> _List = new ArrayList<Booking>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_BOOKING + " order by first_name, last_name, projected_date ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Booking booking = new Booking();
                booking.set_id(parseInt(cursor.getString(0)));
                booking.set_timestamp(cursor.getString(1));
                booking.set_first_name(cursor.getString(2));
                booking.set_last_name(cursor.getString(3));
                booking.set_national_id(cursor.getString(4));
                booking.set_phone(cursor.getString(5));

                booking.set_location_id(parseInt(cursor.getString(6)));
                booking.set_latitude(parseFloat(cursor.getString(7)));
                booking.set_longitude(parseFloat(cursor.getString(8)));
                booking.set_projected_date(cursor.getString(9));
                booking.set_actual_date(cursor.getString(10));

                booking.set_consent(cursor.getString(11));
                booking.set_procedure_type_id(parseInt(cursor.getString(12)));
                booking.set_followup_id(parseInt(cursor.getString(13)));
                booking.set_followup_date(cursor.getString(14));

                booking.set_alt_contact(cursor.getString(15));

                // Adding booking to list
                _List.add(booking);
            } while (cursor.moveToNext());
        }
        cursor.close();
//        // db.close();
        // return person list
        return _List;
    }

    public List<Booking> getAllLikeBookings(String index) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Booking> _List = new ArrayList<Booking>();

        IndexParts indexParts = new IndexParts(index);

        String[] tableColumns = new String[] {
                BOOKING_ID, BOOKING_TIMESTAMP, BOOKING_FIRST_NAME, BOOKING_LAST_NAME, BOOKING_NATIONAL_ID, BOOKING_PHONE, BOOKING_LOCATION_ID, BOOKING_LATITUDE, BOOKING_LONGITUDE, BOOKING_PROJECTED_DATE, BOOKING_ACTUAL_DATE, BOOKING_CONSENT, BOOKING_PROCEDURE_TYPE_ID, BOOKING_FOLLOWUP_ID, BOOKING_FOLLOWUP_DATE, BOOKING_ALT_CONTACT
        };

        String selectQuery =
                "select b.* from " + TABLE_BOOKING + " b\n" +
                        "join location l on b.location_id = l.id \n" +
                        "join user u \n" +
                        "where 1=1 \n" +
                        "and l.region_id in " + getRegionString() + " \n" +
                        "and u.username = '" + MainActivity.USER_OBJ.get_username() + "'\n" +
                        "and trim(b." + BOOKING_FIRST_NAME + ") like ? \n" +
                        "and trim(b." + BOOKING_LAST_NAME + ") like ? \n" +
                        "and trim(b." + BOOKING_NATIONAL_ID + ") like ? \n" +
                        "and trim(b." + BOOKING_PHONE + ") like ? \n" +
                        "and trim(b." + BOOKING_PROJECTED_DATE + ") like ? ";

        String[] whereArgs = new String [] {
                "%" + indexParts.get_first_name() + "%",
                "%" + indexParts.get_last_name() + "%",
                "%" + indexParts.get_national_id() + "%",
                "%" + indexParts.get_phone() + "%",
                "%" + indexParts.get_projected_date() + "%" };

        Log.d(LOG, "getAllLikeBookings whereArgs: " + whereArgs[0].toString());

        Cursor cursor = db.rawQuery(selectQuery, whereArgs);

        if (cursor.moveToFirst()) {
            do {
                Booking booking = new Booking();
                booking.set_id(parseInt(cursor.getString(0)));
                booking.set_timestamp(cursor.getString(1));
                booking.set_first_name(cursor.getString(2));
                booking.set_last_name(cursor.getString(3));
                booking.set_national_id(cursor.getString(4));
                booking.set_phone(cursor.getString(5));

                booking.set_location_id(parseInt(cursor.getString(6)));
                booking.set_latitude(parseFloat(cursor.getString(7)));
                booking.set_longitude(parseFloat(cursor.getString(8)));
                booking.set_projected_date(cursor.getString(9));
                booking.set_actual_date(cursor.getString(10));

                booking.set_consent(cursor.getString(11));
                booking.set_procedure_type_id(parseInt(cursor.getString(12)));
                booking.set_followup_id(parseInt(cursor.getString(13)));

                booking.set_alt_contact(cursor.getString(14));

                // Adding booking to list
                _List.add(booking);
            } while (cursor.moveToNext());
        }
        cursor.close();
//        // db.close();
        // return person list
        return _List;
    }

    public int getPersonsCount() {
        Log.d(LOG, "getPersonsCount");
        String countQuery = "SELECT  * FROM " + TABLE_PERSON;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        // return count
        int returnVal = cursor.getCount();
        cursor.close();
        // db.close();
        return returnVal;
    }


    public boolean updatePerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(PERSON_ID, person.get_id());
        Calendar calendar = Calendar.getInstance();
        Timestamp oTimestamp = new Timestamp(calendar.getTime().getTime());
        DateFormat df = new android.text.format.DateFormat();
        person.set_timestamp(DateFormat.format(VMMC_DATE_TIME_FORMAT, oTimestamp).toString());
        Log.d(LOG, "updatePerson timestamp: " + person.get_timestamp());
        values.put(PERSON_TIMESTAMP, person.get_timestamp());
        values.put(PERSON_FIRST_NAME, person.get_first_name());
        values.put(PERSON_LAST_NAME, person.get_last_name());
        values.put(PERSON_NATIONAL_ID, person.get_national_id());
        values.put(PERSON_ADDRESS,  person.get_address_id());
        values.put(PERSON_PHONE,  person.get_phone());
        values.put(PERSON_DOB,  person.get_dob());
        values.put(PERSON_GENDER,  person.get_gender());
        values.put(PERSON_LATITUDE,  person.get_latitude());
        values.put(PERSON_LONGITUDE,  person.get_longitude());
        values.put(PERSON_IS_DELETED,  person.get_is_deleted());

        String[] tableColumns = new String[]{
                PERSON_ID, PERSON_TIMESTAMP, PERSON_FIRST_NAME, PERSON_LAST_NAME, PERSON_NATIONAL_ID, PERSON_ADDRESS, PERSON_PHONE, PERSON_DOB, PERSON_GENDER, PERSON_LATITUDE, PERSON_LONGITUDE, PERSON_IS_DELETED
        };


        String updateWhereClause = "1=1 and " +
                PERSON_FIRST_NAME + " = '" + values.get(PERSON_FIRST_NAME).toString().replaceAll("'","''") + "' and " +
                PERSON_LAST_NAME + " = '" + values.get(PERSON_LAST_NAME).toString().replaceAll("'","''") + "' and " +
                PERSON_PHONE + " = '" + values.get(PERSON_PHONE).toString().replaceAll("'","''") + "'";

        Log.d(LOG, "updatePerson updateWhereClause: " + updateWhereClause);

        db.update(TABLE_PERSON, values, updateWhereClause, null);
        DBHelper.exportDB();
        // db.close();
        return true;
    }

    public boolean deletePerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();
        //db.delete(TABLE_PERSON, KEY_ID + " = ?", new String[] { String.valueOf(person.getID()) });

        // db.close();
        return true;
    }

    public void dropDatabase() {
    }

    static public boolean isDate(String testDate){
        Log.d(LOG, "isDate: " + testDate);
//        String regEx = regEx ="^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d{2}$";
//        String regEx = regEx ="^(19|20)\\d{2}[- /.](0[1-9]|1[012])[- /.]0[1-9]|[12][0-9]|3[01]$";
//        Matcher matcherObj = Pattern.compile(regEx).matcher(testDate);
//        if (matcherObj.matches()) return true;
//        else return false;
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DBHelper.VMMC_DATE_FORMAT);
        try {
            dateFormatter.parse(testDate);
            return true;
        }
        catch(java.text.ParseException e){
            return false;
        }
    };



} // class

