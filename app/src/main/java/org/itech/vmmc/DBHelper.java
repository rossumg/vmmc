package org.itech.vmmc;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;


/**
 * Created by Rayce on 8/21/2015.
 */

public class DBHelper extends SQLiteOpenHelper{

    public static String LOG = "gnr";
    private static final int DATABASE_VERSION = 1;
    public Context _context;

    // Database Name
    private static final String DATABASE_NAME = "vmmc.db";

    // assessments.db table names
    private static final String TABLE_ASSESSMENTS = "assessments";
    private static final String TABLE_ASSESSMENTS_ANSWERS = "assessments_answers";
    private static final String TABLE_ASSESSMENTS_QUESTIONS = "assessments_questions";
    // private static final String TABLE_PERSON = "person";
    private static final String TABLE_PERSON_TO_ASSESSMENTS = "person_to_assessments";
    private static final String TABLE_GEOLOCATIONS = "geolocations";
    private static final String TABLE_QUESTION_DROPDOWN_OPTION = "question_dropdown";

    // assessments table column names
    private static final String ASSESSMENTS_ROWID = "rowid";
    private static final String ASSESSMENTS_ASSESSMENT_ID = "assessment_id";
    private static final String ASSESSMENTS_ASSESSMENT_TYPE = "assessment_type";
    private static final String ASSESSMENTS_STATUS = "status";

    // assessments_answers table column names
    private static final String ASSESSMENTS_ANSWERS_ASSESS_ID = "assess_id";
    private static final String ASSESSMENTS_ANSWERS_PERSON = "person";
    private static final String ASSESSMENTS_ANSWERS_FACILITY = "facility";
    private static final String ASSESSMENTS_ANSWERS_DATE_CREATED = "date_created";
    private static final String ASSESSMENTS_ANSWERS_ASSESSMENT_ID = "assessment_id";
    private static final String ASSESSMENTS_ANSWERS_QUESTION = "question";
    private static final String ASSESSMENTS_ANSWERS_ANSWER = "answer";
    private static final String ASSESSMENTS_ANSWERS_ACTIVE = "active";

    // assessments_questions table column names
    private static final String ASSESSMENTS_QUESTIONS_ROWID = "rowid";
    private static final String ASSESSMENTS_QUESTIONS_ASSESSMENTS_QUESTIONS_ID = "assessments_questions_id";
    private static final String ASSESSMENTS_QUESTIONS_ASSESSMENT_ID = "assessment_id";
    private static final String ASSESSMENTS_QUESTIONS_QUESTION = "question";
    private static final String ASSESSMENTS_QUESTIONS_ITEMORDER = "itemorder";
    private static final String ASSESSMENTS_QUESTIONS_ITEMTYPE = "itemtype";
    private static final String ASSESSMENTS_QUESTIONS_STATUS = "status";

    // geolocations table column names
    private static final String GEOLOCATIONS_ID = "rowid";
    private static final String GEOLOCATIONS_LONGITUDE = "longitude";
    private static final String GEOLOCATIONS_LATITUDE = "latitude";
    private static final String GEOLOCATIONS_DEVICE_ID = "device_id";
    private static final String GEOLOCATIONS_TIMESTAMP = "timestamp";
    private static final String GEOLOCATIONS_USERNAME = "username";
    private static final String GEOLOCATIONS_PASSWORD = "password";

    // person table column names
    private static final String PERSON_ROWID = "rowid";
    private static final String PERSON_PERSON_ID = "person_id";
    //private static final String PERSON_FIRST_NAME = "first_name";
    //private static final String PERSON_LAST_NAME = "last_name";
    // private static final String PERSON_NATIONAL_ID = "national_id";
    private static final String PERSON_FACILITY_ID = "facility_id";
    private static final String PERSON_FACILITY_NAME = "facility_name";

    // person_to_assessments table column names
    private static final String PERSON_TO_ASSESSMENTS_PERSON_TO_ASSESSMENTS_ID = "person_to_assessments_id";
    private static final String PERSON_TO_ASSESSMENTS_PERSON_ID = "person_id";
    private static final String PERSON_TO_ASSESSMENTS_FACILITY_ID = "facility_id";
    private static final String PERSON_TO_ASSESSMENTS_DATE_CREATED = "date_created";
    private static final String PERSON_TO_ASSESSMENTS_ASSESSMENT_ID = "assessment_id";
    private static final String PERSON_TO_ASSESSMENTS_USER_ID = "user_id";
    private static final String PERSON_TO_ASSESSMENTS_STATUS = "status";

    // question_dropdown table column names
    private static final String QUESTION_DROPDOWN_OPTION_QUESTION_DROPDOWN_OPTION_ID = "question_dropdown_option_id";
    private static final String QUESTION_DROPDOWN_OPTION_QUESTION_ID = "question_id";
    private static final String QUESTION_DROPDOWN_OPTION_DROPDOWN_OPTION_ID = "dropdown_option_id";
    private static final String QUESTION_DROPDOWN_OPTION_STATUS = "status";

    // answer types
    private static final String ANSWER_TYPE_TEXT = "text";
    private static final String ANSWER_TYPE_QUESTION110 = "question110";
    private static final String ANSWER_TYPE_QUESTIONTEXT = "questiontext";
    private static final String ANSWER_TYPE_QUESTIONYESNO = "questionyesno";
    private static final String ANSWER_TYPE_QUESTIONMULTI = "questiontextarea";
    private static final String ANSWER_TYPE_TITLE = "title";

    // vmmc.db table names
    private static final String TABLE_PERSON      = "person";
    private static final String TABLE_USER        = "user";
    private static final String TABLE_CLIENT      = "client";
    private static final String TABLE_FACILITATOR = "facilitator";
    private static final String TABLE_LOCATION    = "location";
    private static final String TABLE_REGION      = "region";
    private static final String TABLE_CONSTITUENCY = "constituency";
    private static final String TABLE_BOOKING      = "booking";
    private static final String TABLE_INTERACTION  = "interaction";
    private static final String TABLE_GEOLOCATION  = "geolocation";
    private static final String TABLE_FACILITATOR_TYPE = "facilitator_type";
    private static final String TABLE_INTERACTION_TYPE = "interaction_type";

    // person table column names
    private static final String PERSON_ID          = "id";
    private static final String PERSON_FIRST_NAME  = "first_name";
    private static final String PERSON_LAST_NAME   = "last_name";
    private static final String PERSON_NATIONAL_ID = "national_id";
    private static final String PERSON_ADDRESS     = "address";
    private static final String PERSON_PHONE       = "phone";
    private static final String PERSON_DOB         = "dob";
    private static final String PERSON_GENDER      = "gender";
    private static final String PERSON_LATITUDE    = "latitude";
    private static final String PERSON_LONGITUDE   = "longitude";
    private static final String PERSON_IS_DELETED      = "is_deleted";

    // booking table column names
    private static final String BOOKING_ID              = "id";
    private static final String BOOKING_CLIENT_ID       = "client_id";
    private static final String BOOKING_FACILITATOR_ID  = "facilitator_id";
    private static final String BOOKING_LOCATION_ID     = "location_id";
    private static final String BOOKING_PROJECTED_DATE  = "projected_date";
    private static final String BOOKING_ACTUAL_DATE     = "actual_date";

    // user table column names
    private static final String USER_ID        = "id";
    private static final String USER_PERSON_ID = "person_id";
    private static final String USER_USERNAME  = "username";
    private static final String USER_PASSWORD  = "password";

    // client table column names
    private static final String CLIENT_ID        = "id";
    private static final String CLIENT_PERSON_ID = "person_id";
    private static final String CLIENT_STATUS    = "status";

    // facilitator table column names
    private static final String FACILITATOR_ID          = "id";
    private static final String FACILITATOR_PERSON_ID   = "person_id";
    private static final String FACILITATOR_TYPE        = "type_id";
    private static final String FACILITATOR_NOTE        = "note";
    private static final String FACILITATOR_LOCATION_ID = "location_id";
    private static final String FACILITATOR_LAT         = "lat";
    private static final String FACILITATOR_LONG        = "long";
    private static final String FACILITATOR_INSTITUTION = "institution";

    // location table column names
    private static final String LOCATION_ID   = "id";
    private static final String LOCATION_NAME = "name";

    // region table column names
    private static final String REGION_ID          = "id";
    private static final String REGION_NAME        = "name";
    private static final String REGION_LOCATION_ID = "location_id";

    // constituency table column names
    private static final String CONSTITUENCY_ID        = "id";
    private static final String CONSTITUENCY_NAME      = "name";
    private static final String CONSTITUENCY_REGION_ID = "region_id";

    // interaction table column names
    private static final String INTERACTION_ID          = "id";
    private static final String INTERACTION_NAME        = "name";
    private static final String INTERACTION_BOOKING_ID  = "booking_id";
    private static final String INTERACTION_TYPE        = "type_id";
    private static final String INTERACTION_NOTE        = "note";

    // interaction_type table column names
    private static final String INTERACTION_TYPE_ID     = "id";
    private static final String INTERACTION_TYPE_NAME   = "name";

    // facilitator_type table column names
    private static final String FACILITATOR_TYPE_ID     = "id";
    private static final String FACILITATOR_TYPE_NAME   = "name";

    // geolocation table column names
    private static final String GEOLOCATION_ID        = "id";
    private static final String GEOLOCATION_LAT       = "lat";
    private static final String GEOLOCATION_LONG      = "long";
    private static final String GEOLOCATION_DEVICE_ID = "device_id";
    private static final String GEOLOCATION_TIMESTAMP = "timestamp";
    private static final String GEOLOCATION_USERNAME  = "username";
    private static final String GEOLOCATION_PASSWORD  = "password";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this._context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(LOG, "DBHelper.onCreate0");

        //try { db.execSQL("delete from person;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
        try {
            String CREATE_PERSON_TABLE = "CREATE TABLE IF NOT EXISTS person(" +
                    "id integer primary key  autoincrement  not null  unique, " +
                    "first_name varchar, " +
                    "last_name varchar, " +
                    "national_id varchar, " +
                    "address varchar, " +
                    "phone varchar, " +
                    "dob date, " +
                    "gender varchar, " +
                    "latitude real, " +
                    "longitude real, " +
                    "is_deleted inti, " +
                    "constraint name_constraint unique (first_name, last_name, national_id, phone) );";
            db.execSQL(CREATE_PERSON_TABLE);

            //try { db.execSQL("delete from booking;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
            String CREATE_BOOKING_TABLE = "CREATE TABLE IF NOT EXISTS booking(" +
                    "id integer primary key  autoincrement  not null  unique, " +
                    "client_id int, " +
                    "facilitator_id int, " +
                    "location_id int, " +
                    "projected_date date, " +
                    "actual_date date)";
            db.execSQL(CREATE_BOOKING_TABLE);

            //try { db.execSQL("delete from user;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
            String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS user(" +
                    "id integer primary key  autoincrement  not null  unique, " +
                    "person_id int, " +
                    "username varchar, " +
                    "password varchar)";
            db.execSQL(CREATE_USER_TABLE);

            //try { db.execSQL("delete from client;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
            String CREATE_CLIENT_TABLE = "CREATE TABLE IF NOT EXISTS client(" +
                    "id integer primary key  autoincrement  not null  unique, " +
                    "person_id int, " +
                    "status varchar)";
            db.execSQL(CREATE_CLIENT_TABLE);

            //try { db.execSQL("delete from facilitator;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
            String CREATE_FACILITATOR_TABLE = "CREATE TABLE IF NOT EXISTS facilitator(" +
                    "id integer primary key  autoincrement  not null  unique, " +
                    "person_id int, " +
                    "type_id int, " +
                    "note varchar, " +
                    "location_id int, " +
                    "lat real, " +
                    "long real, " +
                    "institution varchar)";
            db.execSQL(CREATE_FACILITATOR_TABLE);

            //try { db.execSQL("delete from location;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
            String CREATE_LOCATION_TABLE = "CREATE TABLE IF NOT EXISTS location(" +
                    "id integer primary key  autoincrement  not null  unique, " +
                    "location_name varchar)";
            db.execSQL(CREATE_LOCATION_TABLE);

            //try { db.execSQL("delete from region;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
            String CREATE_REGION_TABLE = "CREATE TABLE IF NOT EXISTS region(" +
                    "id integer primary key  autoincrement  not null  unique, " +
                    "name varchar, " +
                    "location_id int)";
            db.execSQL(CREATE_REGION_TABLE);

            //try { db.execSQL("delete from constituency;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
            String CREATE_CONSTITUENCY_TABLE = "CREATE TABLE IF NOT EXISTS constituency(" +
                    "id integer primary key  autoincrement  not null  unique, " +
                    "name varchar, " +
                    "region_id int)";
            db.execSQL(CREATE_CONSTITUENCY_TABLE);

            //try { db.execSQL("delete from interaction;"); } catch(Exception ex) {Log.d(LOG, "DBHelper.onCreate nothing to delete" + ex.toString());}
            String CREATE_INTERACTION_TABLE = "CREATE TABLE IF NOT EXISTS interaction(" +
                    "id integer primary key  autoincrement  not null  unique, " +
                    "name varchar, " +
                    "booking_id int, " +
                    "type_id int" +
                    "note varchar)";
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

    public void doSyncDB() {
        Log.d(LOG, "DBHelper.doSyncDB");
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
//        load_facilitator_type();
//        load_interaction_type();
//          load_person();
          new putMySQLPersonTable(this).execute();
          new getMySQLPersonTable(this._context, this).execute();
    }

    public void doTestDB() {
        Log.d(LOG, "DBHelper.doTestDB");
        SQLiteDatabase db = this.getReadableDatabase();
        onCreate(db);
//        load_facilitator_type();
//        load_interaction_type();
        load_person();
//        new putMySQLPersonTable(this).execute();
//        new getMySQLPersonTable(this._context, this).execute();
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

        // Create tables again
        onCreate(db);
    }

    protected void load_person() {
        Log.d(LOG, "debugFragment:load_person");

        SQLiteDatabase db = this.getWritableDatabase();
        // db.execSQL("delete from person ");
        Person person0 = new Person("A","B", "n1","a","12","0000-00-00","g",1.100000023841858,2.200000047683716,0); addPerson(person0);
        Person person1 = new Person("C","D", "n2","a","34","0000-00-00","g",1.100000023841858,2.200000047683716,0); addPerson(person1);
        Person person2 = new Person("E","F", "n3","a","56","0000-00-00","g",1.100000023841858,2.200000047683716,0); addPerson(person2);

        Person person3 = getPerson("A","B", "p");
        Log.d(LOG, "debugFragment:load_person:personAB: " +
                person3.get_first_name() + " " +
                person3.get_last_name() + " " +
                person3.get_national_id() + " " +
                person3.get_address() + " " +
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
                    person4.get_address() + " " +
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
    db.close();
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
        db.close();
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
        db.close();
        // return person list
        return readableRecentAssessmentsList;
    }

    public List<String> getRecentAssessments(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> recentAssessements = new ArrayList<String>();

        String[] tableColumns = new String[] {
                PERSON_TO_ASSESSMENTS_PERSON_TO_ASSESSMENTS_ID, PERSON_TO_ASSESSMENTS_PERSON_ID, PERSON_TO_ASSESSMENTS_FACILITY_ID, PERSON_TO_ASSESSMENTS_DATE_CREATED, PERSON_TO_ASSESSMENTS_ASSESSMENT_ID, PERSON_TO_ASSESSMENTS_USER_ID, PERSON_TO_ASSESSMENTS_STATUS

        };

        String whereClause = "1=1 ";

        String[] whereArgs = new String[]{};

        String orderBy = PERSON_TO_ASSESSMENTS_DATE_CREATED;

        Cursor cursor = db.query(TABLE_PERSON_TO_ASSESSMENTS, tableColumns, whereClause, whereArgs, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllPersonIDs  "
//                                + cursor.getString(0) + " "
//                                + cursor.getString(1) + " "
//                                + cursor.getString(2) + " "
//                                + cursor.getString(3) + " "
//                );
                recentAssessements.add(cursor.getString(0) + ", " + cursor.getString(1) + ", " + cursor.getString(2) + ",  " + cursor.getString(3) + ", " + cursor.getString(4) + ", " + cursor.getString(5) + ",  " + cursor.getString(6));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // remove duplicates
//        Set<String> noDups = new LinkedHashSet<>(recentAssessements);
//        recentAssessements.clear();;
//        recentAssessements.addAll(noDups);

        // convert to array
//        String[] stringArrayPersonID = new String[ personID.size() ];
//        personID.toArray(stringArrayPersonID);

        return recentAssessements;
    }

    public int getAssessmentsQuestionsQuestion(int assessment_id, int itemorder){

        SQLiteDatabase db = this.getReadableDatabase();

        String[] tableColumns = new String[] {
                ASSESSMENTS_QUESTIONS_ASSESSMENTS_QUESTIONS_ID
        };

        String whereClause = "1=1 and " +

                ASSESSMENTS_QUESTIONS_ASSESSMENT_ID + " = ? and " +
                ASSESSMENTS_QUESTIONS_ITEMORDER + " = ? ";

        String assessment_id_string = new Integer(assessment_id).toString();
        String itemorder_string = new Integer(itemorder).toString();
        String[] whereArgs = new String [] {
                assessment_id_string, itemorder_string };

        Cursor cursor = db.query(TABLE_ASSESSMENTS_QUESTIONS, tableColumns, whereClause, whereArgs, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
//        Log.d(LOG, "getAssessmentsQuestionsQuestion  "
//                        + cursor.getString(0) + " "
//        );

        int returnQuestion = parseInt(cursor.getString(0));
        cursor.close();
        db.close();
        return returnQuestion;
    }

    public List<String> getAllPersonIDs(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> personID = new ArrayList<String>();

        String[] tableColumns = new String[] {
                PERSON_ID, PERSON_FIRST_NAME, PERSON_LAST_NAME, PERSON_NATIONAL_ID, PERSON_ADDRESS, PERSON_PHONE, PERSON_DOB, PERSON_GENDER, PERSON_LATITUDE, PERSON_LONGITUDE, PERSON_IS_DELETED
        };

        String whereClause = "1=1 ";

        String[] whereArgs = new String[]{};

        String orderBy = PERSON_FIRST_NAME + "," + PERSON_LAST_NAME + "," + PERSON_NATIONAL_ID;

        Cursor cursor = db.query(TABLE_PERSON, tableColumns, whereClause, whereArgs, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllPersonIDs  "
//                                + cursor.getString(1) + " "
//                                + cursor.getString(2) + " "
//                                + cursor.getString(3) + " "
//                                + cursor.getString(5) + " "
//                );
                personID.add(cursor.getString(1).trim() + " " + cursor.getString(2).trim() + ", " + cursor.getString(3).trim() + ", " + cursor.getString(5).trim());
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // remove duplicates
        Set<String> noDups = new LinkedHashSet<>(personID);
        personID.clear();;
        personID.addAll(noDups);

        // convert to array
//        String[] stringArrayPersonID = new String[ personID.size() ];
//        personID.toArray(stringArrayPersonID);

        return personID;
    }

    public List<String> getAllAssessmentTypes(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> assessmentTypes = new ArrayList<String>();

        String[] tableColumns = new String[] {
                ASSESSMENTS_ASSESSMENT_TYPE
        };

        String whereClause = "1=1 ";

        String[] whereArgs = new String[]{};

        String orderBy = ASSESSMENTS_ASSESSMENT_ID;

        Cursor cursor = db.query(TABLE_ASSESSMENTS, tableColumns, whereClause, whereArgs, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllFacilityNames  "
//                                + cursor.getString(0)
//                );
                assessmentTypes.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // remove duplicates
        Set<String> noDups = new LinkedHashSet<>(assessmentTypes);
        assessmentTypes.clear();;
        assessmentTypes.addAll(noDups);

        // convert to array
        String[] stringArrayFacilityNames = new String[ assessmentTypes.size() ];
        assessmentTypes.toArray(stringArrayFacilityNames);

        return assessmentTypes;
    }

    public List<GeoLocations> getAllGeoLocations() {

        List<GeoLocations> geoLocationsList = new ArrayList<GeoLocations>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_GEOLOCATIONS;
         SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
         // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                GeoLocations geoLocations = new GeoLocations();
                geoLocations.set_longitude(parseFloat(cursor.getString(1)));
                geoLocations.set_latitude(parseFloat(cursor.getString(2)));
                geoLocations.set_device_id(cursor.getString(3));
                geoLocations.set_created_at(cursor.getString(4));
                geoLocations.set_username(cursor.getString(5));
                geoLocations.set_password(cursor.getString(6));
                 // Adding person to list
                geoLocationsList.add(geoLocations);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return geoLocationsList;
    }

    public boolean addGeoLocation(){
        SQLiteDatabase db = this.getWritableDatabase();

        GeoLocations geoLocations = new GeoLocations();

        ContentValues values = new ContentValues();
        values.put(GEOLOCATIONS_LONGITUDE, geoLocations.get_longitude());
        values.put(GEOLOCATIONS_LATITUDE, geoLocations.get_latitude());
        values.put(GEOLOCATIONS_DEVICE_ID, geoLocations.get_device_id());
        values.put(GEOLOCATIONS_USERNAME, geoLocations.get_username());
        values.put(GEOLOCATIONS_PASSWORD, geoLocations.get_password());

        try {
            db.insert(TABLE_GEOLOCATIONS, null, values);
            Toast.makeText(this._context, "Record: " + geoLocations.get_longitude() + " " + geoLocations.get_latitude(), Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            db.close();
            Log.d(LOG, "addGeoLocation catch " + ex.toString());
            return false;
        }
        return true;
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
        db.close();

        // remove duplicates
        Set<String> noDups = new LinkedHashSet<>(phone_numbers);
        phone_numbers.clear();;
        phone_numbers.addAll(noDups);

        // convert to array
        String[] stringArrayPhoneNumbers = new String[ phone_numbers.size() ];
        phone_numbers.toArray(stringArrayPhoneNumbers);

        return stringArrayPhoneNumbers;
    }

    public String[] getAllFacilityNames(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> facility_names = new ArrayList<String>();

        String[] tableColumns = new String[] {
                PERSON_FACILITY_NAME
        };

        String whereClause = "1=1 ";

        String[] whereArgs = new String[]{};

        String orderBy = PERSON_FACILITY_NAME;

        Cursor cursor = db.query(TABLE_PERSON, tableColumns, whereClause, whereArgs, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
//                Log.d(LOG, "getAllFacilityNames  "
//                                + cursor.getString(0)
//                );

                facility_names.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // remove duplicates
        Set<String> noDups = new LinkedHashSet<>(facility_names);
        facility_names.clear();;
        facility_names.addAll(noDups);

        // convert to array
        String[] stringArrayFacilityNames = new String[ facility_names.size() ];
        facility_names.toArray(stringArrayFacilityNames);

        return stringArrayFacilityNames;
    }

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
        db.close();
        return facility_id;
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
        db.close();

        // remove duplicates
        Set<String> noDups = new LinkedHashSet<>(nationalIds);
        nationalIds.clear();;
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
        db.close();
        // return person list
        Log.d(LOG, "Return");
        return editPageList;
    }

    public void setEditPageRow(PersonToAssessments pa, int question_id, String new_answer){
        // select from answers, if (answer) update answer else insert answer
        AssessmentsAnswers assessmentsAnswers =
                this.getAssessmentsAnswers(
                        pa.get_person_id(),
                        pa.get_facility_id(),
                        pa.get_date_created(),
                        pa.get_assessment_id(),
                        question_id);

        if(assessmentsAnswers != null){
            this.updateAssessmentsAnswers(assessmentsAnswers.get_assess_id(), new_answer);
            Log.d(LOG, "update: " + assessmentsAnswers.get_assess_id() + " " + assessmentsAnswers.get_question() + " " + new_answer);
        } else {
            this.insertAssessmentsAnswers(
                    pa.get_person_id(),
                    pa.get_facility_id(),
                    pa.get_date_created(),
                    pa.get_assessment_id(),
                    question_id,
                    new_answer );
            Log.d(LOG, "insert: " + " " + question_id + " " +  new_answer);
        }

//            Log.d(LOG, "helperTest setEditPageData editPageObjectList > "
//                            //+ editPageObjectList._rowid + " "
//                            + epo._assessments_questions_id + " "
//                            + epo._question + " "
//                            + epo._itemtype + " "
//                            + epo._itemorder + " "
//                            + epo._answer + " "
//            );
    }

    public void setEditPageData(PersonToAssessments pa, List<EditPageObject> editPageObjectList){
        Log.d(LOG, "setEditPageData");
        Log.d(LOG, "pa >" + pa._person_id + " " + pa._facility_id + " " + pa._date_created + " " + pa._assessment_id);
        // for each questions, select from answers, if (answer) update answer else insert answer
        for (EditPageObject epo : editPageObjectList) {

            AssessmentsAnswers assessmentsAnswers =
                    this.getAssessmentsAnswers(
                            pa.get_person_id(),
                            pa.get_facility_id(),
                            pa.get_date_created(),
                            pa.get_assessment_id(),
                            epo.get_assessments_questions_id());

            if(assessmentsAnswers != null){
                Log.d(LOG, "setEditPageData update: " + epo.get_answer());
                this.updateAssessmentsAnswers(assessmentsAnswers.get_assess_id(), epo.get_answer());
            } else if (epo.get_itemtype() != ANSWER_TYPE_TEXT) {
                Log.d(LOG, "setEditPageData insert: ");
                this.insertAssessmentsAnswers(
                        pa.get_person_id(),
                        pa.get_facility_id(),
                        pa.get_date_created(),
                        pa.get_assessment_id(),
                        epo.get_assessments_questions_id(),
                        epo.get_answer() );
            }

//            Log.d(LOG, "helperTest setEditPageData editPageObjectList > "
//                            //+ editPageObjectList._rowid + " "
//                            + epo._assessments_questions_id + " "
//                            + epo._question + " "
//                            + epo._itemtype + " "
//                            + epo._itemorder + " "
//                            + epo._answer + " "
//            );
        }
    };

    public Assessments getAssessments(int assessments_assessment_id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] tableColumns = new String[] {
                ASSESSMENTS_ROWID, ASSESSMENTS_ASSESSMENT_ID, ASSESSMENTS_ASSESSMENT_TYPE, ASSESSMENTS_STATUS
        };

        String whereClause = "1=1 and " +
                ASSESSMENTS_ASSESSMENT_ID + " = ?";

        String[] whereArgs = new String[]{
                Integer.toString(assessments_assessment_id) };

        Cursor cursor = db.query(TABLE_ASSESSMENTS, tableColumns, whereClause, whereArgs, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        Log.d(LOG, "getAssessments  "
                        + cursor.getString(0) + " "
                        + cursor.getString(1) + " "
                        + cursor.getString(2) + " "
        );

        Assessments assessments = new Assessments(
                parseInt(cursor.getString(1)),
                cursor.getString(2)

        );
        cursor.close();
        db.close();
        return assessments;
    }

    public Assessments getAssessments(String assessments_assessment_type) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] tableColumns = new String[] {
                ASSESSMENTS_ASSESSMENT_ID, ASSESSMENTS_ASSESSMENT_TYPE
        };

        String whereClause = "1=1 and " +
                ASSESSMENTS_ASSESSMENT_TYPE + " = ?";

        String[] whereArgs = new String[]{
                assessments_assessment_type };

        Cursor cursor = db.query(TABLE_ASSESSMENTS, tableColumns, whereClause, whereArgs, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
//        Log.d(LOG, "geAssessments  "
//                        + cursor.getString(0) + " "
//                        + cursor.getString(1) + " "
//                        + cursor.getString(2) + " "
//        );

        Assessments assessments = new Assessments(
                parseInt(cursor.getString(0)),
                cursor.getString(1)

        );
        cursor.close();
        db.close();
        return assessments;
    }

    public AssessmentsAnswers getAssessmentsAnswers(int assess_id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] tableColumns = new String[] {
                ASSESSMENTS_ANSWERS_ASSESS_ID, ASSESSMENTS_ANSWERS_PERSON, ASSESSMENTS_ANSWERS_FACILITY, ASSESSMENTS_ANSWERS_DATE_CREATED, ASSESSMENTS_ANSWERS_ASSESSMENT_ID, ASSESSMENTS_ANSWERS_QUESTION, ASSESSMENTS_ANSWERS_ANSWER, ASSESSMENTS_ANSWERS_ACTIVE,
        };

        String whereClause = "1=1 and " +
                ASSESSMENTS_ANSWERS_ASSESS_ID + " = ?";

        String[] whereArgs = new String[]{
                Integer.toString(assess_id) };

        Cursor cursor = db.query(TABLE_ASSESSMENTS_ANSWERS, tableColumns, whereClause, whereArgs, null, null, null);

        if (cursor.moveToFirst()) {

//        Log.d(LOG, "getAssessmentsAnswers  "
//                        + cursor.getString(0) + " "
//                        + cursor.getString(1) + " "
//                        + cursor.getString(2) + " "
//                        + cursor.getString(3) + " "
//                        + cursor.getString(4) + " "
//                        + cursor.getString(5) + " "
//                        + cursor.getString(6) + " "
//        );

            AssessmentsAnswers assessments_answers = new AssessmentsAnswers(
                    parseInt(cursor.getString(0)),
                    parseInt(cursor.getString(1)),
                    parseInt(cursor.getString(2)),
                    cursor.getString(3),
                    parseInt(cursor.getString(4)),
                    parseInt(cursor.getString(5)),
                    cursor.getString(6)

            );
            cursor.close();
            db.close();
            return assessments_answers;
        }
        else {
            cursor.close();
            db.close();
            return null;
        }
    }

    public AssessmentsAnswers getAssessmentsAnswers(int person, int facility, String date_created, int assessment_id, int question) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] tableColumns = new String[] {
                ASSESSMENTS_ANSWERS_ASSESS_ID, ASSESSMENTS_ANSWERS_PERSON, ASSESSMENTS_ANSWERS_FACILITY, ASSESSMENTS_ANSWERS_DATE_CREATED, ASSESSMENTS_ANSWERS_ASSESSMENT_ID, ASSESSMENTS_ANSWERS_QUESTION, ASSESSMENTS_ANSWERS_ANSWER, ASSESSMENTS_ANSWERS_ACTIVE,
        };

        String whereClause = "1=1 and " +
                ASSESSMENTS_ANSWERS_PERSON + " = ? and " +
                ASSESSMENTS_ANSWERS_FACILITY + " = ? and " +
                ASSESSMENTS_ANSWERS_DATE_CREATED + " = ? and " +
                ASSESSMENTS_ANSWERS_ASSESSMENT_ID + " = ? and " +
                ASSESSMENTS_ANSWERS_QUESTION + " = ?";

        String[] whereArgs = new String[]{
                Integer.toString(person), Integer.toString(facility), date_created, Integer.toString(assessment_id), Integer.toString(question) };

        Cursor cursor = db.query(TABLE_ASSESSMENTS_ANSWERS, tableColumns, whereClause, whereArgs, null, null, null);

        if (cursor.moveToFirst()) {

//        Log.d(LOG, "getAssessmentsAnswers  "
//                        + cursor.getString(0) + " "
//                        + cursor.getString(1) + " "
//                        + cursor.getString(2) + " "
//                        + cursor.getString(3) + " "
//                        + cursor.getString(4) + " "
//                        + cursor.getString(5) + " "
//                        + cursor.getString(6) + " "
//        );

            AssessmentsAnswers assessments_answers = new AssessmentsAnswers(
                    parseInt(cursor.getString(0)),
                    parseInt(cursor.getString(1)),
                    parseInt(cursor.getString(2)),
                    cursor.getString(3),
                    parseInt(cursor.getString(4)),
                    parseInt(cursor.getString(5)),
                    cursor.getString(6)

            );
            cursor.close();
            db.close();
            return assessments_answers;
        }
        else {
            cursor.close();
            db.close();
            return null;
        }
    }

    public void updateAssessmentsAnswers(int person, int facility, String date_created, int assessment_id, int question, String new_answer) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] tableColumns = new String[] {
                ASSESSMENTS_ANSWERS_ASSESS_ID, ASSESSMENTS_ANSWERS_PERSON, ASSESSMENTS_ANSWERS_FACILITY, ASSESSMENTS_ANSWERS_DATE_CREATED, ASSESSMENTS_ANSWERS_ASSESSMENT_ID, ASSESSMENTS_ANSWERS_QUESTION, ASSESSMENTS_ANSWERS_ANSWER, ASSESSMENTS_ANSWERS_ACTIVE,
        };

        String whereClause = "1=1 and " +
                ASSESSMENTS_ANSWERS_PERSON + " = ? and " +
                ASSESSMENTS_ANSWERS_FACILITY + " = ? and " +
                ASSESSMENTS_ANSWERS_DATE_CREATED + " = ? and " +
                ASSESSMENTS_ANSWERS_ASSESSMENT_ID + " = ? and " +
                ASSESSMENTS_ANSWERS_QUESTION + " = ?";

        String[] whereArgs = new String[]{
                Integer.toString(person), Integer.toString(facility), date_created, Integer.toString(assessment_id), Integer.toString(question) };

        Cursor cursor = db.query(TABLE_ASSESSMENTS_ANSWERS, tableColumns, whereClause, whereArgs, null, null, null);

        if (cursor.moveToFirst()) {

//        Log.d(LOG, "getAssessmentsAnswers  "
//                        + cursor.getString(0) + " "
//                        + cursor.getString(1) + " "
//                        + cursor.getString(2) + " "
//                        + cursor.getString(3) + " "
//                        + cursor.getString(4) + " "
//                        + cursor.getString(5) + " "
//                        + cursor.getString(6) + " "
//        );

            AssessmentsAnswers assessments_answers = new AssessmentsAnswers(
                    parseInt(cursor.getString(0)),
                    parseInt(cursor.getString(1)),
                    parseInt(cursor.getString(2)),
                    cursor.getString(3),
                    parseInt(cursor.getString(4)),
                    parseInt(cursor.getString(5)),
                    cursor.getString(6)

            );
            cursor.close();
            // use assess_id to update
            ContentValues cv = new ContentValues();
            cv.put(ASSESSMENTS_ANSWERS_ANSWER, new_answer);
            String updateWhereClause = "1=1 and " + ASSESSMENTS_ANSWERS_ASSESS_ID + " = " + assessments_answers.get_assess_id();
            db.update(TABLE_ASSESSMENTS_ANSWERS, cv, updateWhereClause, null);
            db.close();
        }
        else {
            cursor.close();
            db.close();
        }
    }

    public void updateAssessmentsAnswers(int assess_id, String new_answer) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] tableColumns = new String[] {
                ASSESSMENTS_ANSWERS_ASSESS_ID, ASSESSMENTS_ANSWERS_PERSON, ASSESSMENTS_ANSWERS_FACILITY, ASSESSMENTS_ANSWERS_DATE_CREATED, ASSESSMENTS_ANSWERS_ASSESSMENT_ID, ASSESSMENTS_ANSWERS_QUESTION, ASSESSMENTS_ANSWERS_ANSWER, ASSESSMENTS_ANSWERS_ACTIVE,
        };
            // use assess_id to update
            ContentValues cv = new ContentValues();
            cv.put(ASSESSMENTS_ANSWERS_ANSWER, new_answer);
            String updateWhereClause = "1=1 and " + ASSESSMENTS_ANSWERS_ASSESS_ID + " = " + assess_id;
            db.update(TABLE_ASSESSMENTS_ANSWERS, cv, updateWhereClause, null);
            db.close();
    }

    public void insertAssessmentsAnswers(int person, int facility, String date_created, int assessment_id, int question, String answer) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] tableColumns = new String[] {
                ASSESSMENTS_ANSWERS_ASSESS_ID, ASSESSMENTS_ANSWERS_PERSON, ASSESSMENTS_ANSWERS_FACILITY, ASSESSMENTS_ANSWERS_DATE_CREATED, ASSESSMENTS_ANSWERS_ASSESSMENT_ID, ASSESSMENTS_ANSWERS_QUESTION, ASSESSMENTS_ANSWERS_ANSWER, ASSESSMENTS_ANSWERS_ACTIVE,
        };

        // use assess_id to update
        ContentValues cv = new ContentValues();
        cv.put(ASSESSMENTS_ANSWERS_PERSON, person);
        cv.put(ASSESSMENTS_ANSWERS_FACILITY, facility);
        cv.put(ASSESSMENTS_ANSWERS_DATE_CREATED, date_created);
        cv.put(ASSESSMENTS_ANSWERS_ASSESSMENT_ID, assessment_id);
        cv.put(ASSESSMENTS_ANSWERS_QUESTION, question);
        cv.put(ASSESSMENTS_ANSWERS_ANSWER, answer);
        cv.put(ASSESSMENTS_ANSWERS_ACTIVE, "Y"); // not used
        db.insert(TABLE_ASSESSMENTS_ANSWERS, null, cv);
        db.close();
    }

    public void insertAssessmentsAnswers(AssessmentsAnswers assessmentsAnswers) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] tableColumns = new String[] {
                ASSESSMENTS_ANSWERS_ASSESS_ID, ASSESSMENTS_ANSWERS_PERSON, ASSESSMENTS_ANSWERS_FACILITY, ASSESSMENTS_ANSWERS_DATE_CREATED, ASSESSMENTS_ANSWERS_ASSESSMENT_ID, ASSESSMENTS_ANSWERS_QUESTION, ASSESSMENTS_ANSWERS_ANSWER, ASSESSMENTS_ANSWERS_ACTIVE,
        };

        // use assess_id to update
        ContentValues cv = new ContentValues();
        cv.put(ASSESSMENTS_ANSWERS_PERSON, assessmentsAnswers.get_person());
        cv.put(ASSESSMENTS_ANSWERS_FACILITY, assessmentsAnswers.get_facility());
        cv.put(ASSESSMENTS_ANSWERS_DATE_CREATED, assessmentsAnswers.get_date_created());
        cv.put(ASSESSMENTS_ANSWERS_ASSESSMENT_ID, assessmentsAnswers.get_assessment_id());
        cv.put(ASSESSMENTS_ANSWERS_QUESTION, assessmentsAnswers.get_question());
        cv.put(ASSESSMENTS_ANSWERS_ANSWER, assessmentsAnswers.get_answer());
        cv.put(ASSESSMENTS_ANSWERS_ACTIVE, "Y"); // not used
        db.insert(TABLE_ASSESSMENTS_ANSWERS, null, cv);
        db.close();
    }

    public void deleteAssessmentsAnswers(int person, int facility, String date_created, int assessment_id, int question){

        SQLiteDatabase db = this.getReadableDatabase();

        String[] tableColumns = new String[] {
                ASSESSMENTS_ANSWERS_ASSESS_ID, ASSESSMENTS_ANSWERS_PERSON, ASSESSMENTS_ANSWERS_FACILITY, ASSESSMENTS_ANSWERS_DATE_CREATED, ASSESSMENTS_ANSWERS_ASSESSMENT_ID, ASSESSMENTS_ANSWERS_QUESTION, ASSESSMENTS_ANSWERS_ANSWER, ASSESSMENTS_ANSWERS_ACTIVE,
        };

        String whereClause = "1=1 and " +
                ASSESSMENTS_ANSWERS_PERSON + " = ? and " +
                ASSESSMENTS_ANSWERS_FACILITY + " = ? and " +
                ASSESSMENTS_ANSWERS_DATE_CREATED + " = ? and " +
                ASSESSMENTS_ANSWERS_ASSESSMENT_ID + " = ? and " +
                ASSESSMENTS_ANSWERS_QUESTION + " = ?";

        String[] whereArgs = new String[]{
                Integer.toString(person), Integer.toString(facility), date_created, Integer.toString(assessment_id), Integer.toString(question) };

        db.delete(TABLE_ASSESSMENTS_ANSWERS, whereClause, whereArgs);
        db.close();
    }

    public void deleteAssessmentsAnswers(int assess_id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] tableColumns = new String[] {
                ASSESSMENTS_ANSWERS_ASSESS_ID, ASSESSMENTS_ANSWERS_PERSON, ASSESSMENTS_ANSWERS_FACILITY, ASSESSMENTS_ANSWERS_DATE_CREATED, ASSESSMENTS_ANSWERS_ASSESSMENT_ID, ASSESSMENTS_ANSWERS_QUESTION, ASSESSMENTS_ANSWERS_ANSWER, ASSESSMENTS_ANSWERS_ACTIVE,
        };

        String whereClause = "1=1 and " +
                ASSESSMENTS_ANSWERS_ASSESS_ID + " = ?";

        String[] whereArgs = new String[]{
                Integer.toString(assess_id) };

        db.delete(TABLE_ASSESSMENTS_ANSWERS, whereClause, whereArgs);
        db.close();
    }

    public PersonToAssessments getPersonToAssessments(int pa_pa_id) {

        SQLiteDatabase db = this.getReadableDatabase();
        PersonToAssessments person_to_assessments = null;

        String[] tableColumns = new String[]{
                PERSON_TO_ASSESSMENTS_PERSON_TO_ASSESSMENTS_ID, PERSON_TO_ASSESSMENTS_PERSON_ID, PERSON_TO_ASSESSMENTS_FACILITY_ID, PERSON_TO_ASSESSMENTS_DATE_CREATED, PERSON_TO_ASSESSMENTS_ASSESSMENT_ID, PERSON_TO_ASSESSMENTS_USER_ID, PERSON_TO_ASSESSMENTS_STATUS
        };

        String whereClause = "1=1 and " +
                PERSON_TO_ASSESSMENTS_PERSON_TO_ASSESSMENTS_ID + " = ?";

        String[] whereArgs = new String[]{
                Integer.toString(pa_pa_id)};

        Cursor cursor = db.query(TABLE_PERSON_TO_ASSESSMENTS, tableColumns, whereClause, whereArgs, null, null, null);

        if (cursor.moveToFirst()) {
        Log.d(LOG, "getPersonToAssessments  "
                        + cursor.getString(0) + " "
                        + cursor.getString(1) + " "
                        + cursor.getString(2) + " "
                        + cursor.getString(3) + " "
                        + cursor.getString(4) + " "
                        + cursor.getString(5) + " "
                        + cursor.getString(6) + " "
        );
            person_to_assessments = new PersonToAssessments(
                    parseInt(cursor.getString(0)),
                    parseInt(cursor.getString(1)),
                    parseInt(cursor.getString(2)),
                    cursor.getString(3),
                    parseInt(cursor.getString(4)),
                    parseInt(cursor.getString(5))

            );
            cursor.close();
            db.close();
            return person_to_assessments;
        } else {
            cursor.close();
            db.close();
            return person_to_assessments;
        }
    }

    public PersonToAssessments getPersonToAssessments(int person_id, int facility_id, String date_created, int assessment_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        PersonToAssessments personToAssessments = null;

        String[] tableColumns = new String[] {
                PERSON_TO_ASSESSMENTS_PERSON_TO_ASSESSMENTS_ID, PERSON_TO_ASSESSMENTS_PERSON_ID, PERSON_TO_ASSESSMENTS_FACILITY_ID, PERSON_TO_ASSESSMENTS_DATE_CREATED, PERSON_TO_ASSESSMENTS_ASSESSMENT_ID, PERSON_TO_ASSESSMENTS_USER_ID, PERSON_TO_ASSESSMENTS_STATUS
        };

        String whereClause = "1=1 and " +
                PERSON_TO_ASSESSMENTS_PERSON_ID + " = ? and " +
                PERSON_TO_ASSESSMENTS_FACILITY_ID + " = ? and " +
                PERSON_TO_ASSESSMENTS_DATE_CREATED + " = ? and " +
                PERSON_TO_ASSESSMENTS_ASSESSMENT_ID + " = ?";

        String[] whereArgs = new String[]{
                Integer.toString(person_id), Integer.toString(facility_id), date_created, Integer.toString(assessment_id) };

        Cursor cursor = db.query(TABLE_PERSON_TO_ASSESSMENTS, tableColumns, whereClause, whereArgs, null, null, null);

        if ( cursor.moveToFirst() ) {

//        Log.d(LOG, "getPersonToAssessments  "
//                        + cursor.getString(0) + " "
//                        + cursor.getString(1) + " "
//                        + cursor.getString(2) + " "
//                        + cursor.getString(3) + " "
//                        + cursor.getString(4) + " "
//                        + cursor.getString(5) + " "
//                        + cursor.getString(6) + " "
//        );

            personToAssessments = new PersonToAssessments(
                    parseInt(cursor.getString(0)),
                    parseInt(cursor.getString(1)),
                    parseInt(cursor.getString(2)),
                    cursor.getString(3),
                    parseInt(cursor.getString(4)),
                    parseInt(cursor.getString(5))

            );
            cursor.close();
            db.close();
            return personToAssessments;
        } else {
            cursor.close();
            db.close();
            return personToAssessments;
        }
    }

    public boolean addPersonToAssessments(PersonToAssessments pToA){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PERSON_TO_ASSESSMENTS_PERSON_ID, pToA.get_person_id());
        values.put(PERSON_TO_ASSESSMENTS_FACILITY_ID, pToA.get_facility_id());
        values.put(PERSON_TO_ASSESSMENTS_DATE_CREATED, pToA.get_date_created());
        values.put(PERSON_TO_ASSESSMENTS_ASSESSMENT_ID, pToA.get_assessment_id());
        values.put(PERSON_TO_ASSESSMENTS_USER_ID, pToA.get_user_id());
        values.put(PERSON_TO_ASSESSMENTS_STATUS, 1);

        try {
            db.insert(TABLE_PERSON_TO_ASSESSMENTS, null, values);
            Log.d(LOG, "addPersonToAssessments insert: ");
        } catch (Exception ex) {
            db.close();
            Log.d(LOG, "addPersonToAssessments catch " + ex.toString());
            return false;
        }
        return true;
    }

    public boolean addPerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(PERSON_ID, person.get_id());
        values.put(PERSON_FIRST_NAME, person.get_first_name());
        values.put(PERSON_LAST_NAME, person.get_last_name());
        values.put(PERSON_NATIONAL_ID, person.get_national_id());
        values.put(PERSON_ADDRESS,  person.get_address());
        values.put(PERSON_PHONE,  person.get_phone());
        values.put(PERSON_DOB,  person.get_dob());
        values.put(PERSON_GENDER,  person.get_gender());
        values.put(PERSON_LATITUDE,  person.get_latitude());
        values.put(PERSON_LONGITUDE,  person.get_longitude());
        values.put(PERSON_IS_DELETED,  person.get_is_deleted());

        try {
            db.insert(TABLE_PERSON, null, values);
        } catch (Exception ex) {
            db.close();
            Log.d(LOG, "addPerson catch " + ex.toString());
            return false;
        }
        return true;
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
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    parseFloat(cursor.getString(8)),
                    parseFloat(cursor.getString(9)),
                    parseInt(cursor.getString(10))
            );
            cursor.close();
            db.close();
            return person;
        } else {
            cursor.close();
            db.close();
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
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    parseFloat(cursor.getString(8)),
                    parseFloat(cursor.getString(9)),
                    parseInt(cursor.getString(10))
            );
            cursor.close();
            db.close();
            return person;
        } else {
            cursor.close();
            db.close();
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
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                parseFloat(cursor.getString(8)),
                parseFloat(cursor.getString(9)),
                parseInt(cursor.getString(10))
        );
            cursor.close();
            db.close();
            return person;
        } else {
            cursor.close();
            db.close();
            return person;
        }
    }

    public List<Person> getAllPersons() {
        List<Person> personList = new ArrayList<Person>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PERSON;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Person person = new Person();
                person.set_id(parseInt(cursor.getString(0)));
                person.set_first_name(cursor.getString(1));
                person.set_last_name(cursor.getString(2));
                person.set_national_id(cursor.getString(3));
                person.set_address(cursor.getString(4));
                person.set_phone(cursor.getString(5));
                person.set_dob(cursor.getString(6));
                person.set_gender(cursor.getString(7));
                person.set_latitude(parseFloat(cursor.getString(8)));
                person.set_longitude(parseFloat(cursor.getString(9)));
                person.set_is_deleted(parseInt(cursor.getString(10)));

                // Adding person to list
                personList.add(person);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // db.close();
        // return person list
        return personList;
    }

    public List<AssessmentsAnswers> getAllAssessmentsAnswers() {
        List<AssessmentsAnswers> assessmentsAnswersList = new ArrayList<AssessmentsAnswers>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ASSESSMENTS_ANSWERS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AssessmentsAnswers assessmentsAnswers = new AssessmentsAnswers();
                //person.setRowId(Integer.parseInt(cursor.getString(0)));
                assessmentsAnswers.set_assess_id(parseInt(cursor.getString(0)));
                assessmentsAnswers.set_person(parseInt(cursor.getString(1)));
                assessmentsAnswers.set_facility(parseInt(cursor.getString(2)));
                assessmentsAnswers.set_date_created(cursor.getString(3));
                assessmentsAnswers.set_assessment_id(parseInt(cursor.getString(4)));
                assessmentsAnswers.set_question(parseInt(cursor.getString(5)));
                assessmentsAnswers.set_answer(cursor.getString(6));

                // Adding person to list
                assessmentsAnswersList.add(assessmentsAnswers);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return personToAssessments list
        return assessmentsAnswersList;
    }

    public List<PersonToAssessments> getAllPersonToAssessments() {
        List<PersonToAssessments> personToAssessmentsList = new ArrayList<PersonToAssessments>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PERSON_TO_ASSESSMENTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PersonToAssessments personToAssessments = new PersonToAssessments();
                //person.setRowId(Integer.parseInt(cursor.getString(0)));
                personToAssessments.set_person_to_assessments_id(parseInt(cursor.getString(0)));
                personToAssessments.set_person_id(parseInt(cursor.getString(1)));
                personToAssessments.set_facility_id(parseInt(cursor.getString(2)));
                personToAssessments.set_date_created(cursor.getString(3));
                personToAssessments.set_assessment_id(parseInt(cursor.getString(4)));
                personToAssessments.set_user_id(parseInt(cursor.getString(5)));

                // Adding person to list
                personToAssessmentsList.add(personToAssessments);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return personToAssessments list
        return personToAssessmentsList;
    }

    public int getPersonsCount() {
        Log.d(LOG, "getPersonsCount");
        String countQuery = "SELECT  * FROM " + TABLE_PERSON;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        // return count
        int returnVal = cursor.getCount();
        cursor.close();
        db.close();
        return returnVal;
    }

    public boolean updatePerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();

//    ContentValues values = new ContentValues();
//    values.put(KEY_NAME, person.getName());
//    values.put(KEY_PH_NO, person.getPhoneNumber());
//
//    // updating row
//    return db.update(TABLE_PERSON, values, KEY_ID + " = ?",
//            new String[] { String.valueOf(person.getID()) });
        db.close();
        return true;
    }

    public boolean deletePerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();
        //db.delete(TABLE_PERSON, KEY_ID + " = ?", new String[] { String.valueOf(person.getID()) });

        db.close();
        return true;
    }

    public void dropDatabase() {
    }

    public void uploadDBData() {
        Log.d(LOG, "uploadDBData ");
        new putMySQLGeoLocationsTable(this).execute();
        new putMySQLPersonToAssessmentsTable(this).execute();
        new putMySQLAssessmentsAnswersTable(this._context, this).execute();
    }

    public void downloadDBData() {
        Log.d(LOG, "load person_to_assessments ");
        load_person_to_assessments();
        Log.d(LOG, "load assessments_answers ");
        load_assessments_answers();
        Log.d(LOG, "downloadDBData getMySQLPersonTable");
        new getMySQLPersonTable(this._context, this).execute();
        Log.d(LOG, "downloadDBData getMySQLAssessmentsQuestionsTable");
        new getMySQLAssessmentsQuestionsTable(this).execute();
        Log.d(LOG, "downloadDBData getMySQLAssessmentsTable");
        new getMySQLAssessmentsTable(this).execute();
        Log.d(LOG, "downloadDBData getMySQLQuestionDropdownOptionTable");
        new getMySQLQuestionDropdownOptionTable(this).execute();
    }

    protected void load_person_to_assessments() {
        SQLiteDatabase db = this.getWritableDatabase();

//        db.execSQL("delete from person_to_assessments ");
//        db.execSQL("insert into person_to_assessments values (19,1,1,\"2015-09-15\",2,1,1);");
    }

    protected void load_assessments_answers() {
        SQLiteDatabase db = this.getWritableDatabase();

        //db.execSQL("delete from assessments_answers ");

//        db.execSQL("insert into assessments_answers values (3485,1,1,\"2015-09-15\",2,14,\"A\",\"Y\");");
//        db.execSQL("insert into assessments_answers values (3486,1,1,\"2015-09-15\",2,16,\"B\",\"Y\");");
//        db.execSQL("insert into assessments_answers values (3487,1,1,\"2015-09-15\",2,17,\"text area\",\"Y\");");
//        db.execSQL("insert into assessments_answers values (3488,1,1,\"2015-09-15\",2,18,\"text area\",\"Y\");");
//        db.execSQL("insert into assessments_answers values (3489,1,1,\"2015-09-15\",2,19,\"D\",\"Y\");");
//        db.execSQL("insert into assessments_answers values (3490,1,1,\"2015-09-15\",2,21,\"E\",\"Y\");");
//        db.execSQL("insert into assessments_answers values (3491,1,1,\"2015-09-15\",2,22,\"3.2\",\"Y\");");
//        db.execSQL("insert into assessments_answers values (3492,1,1,\"2015-09-15\",2,23,\"3.3\",\"Y\");");
//        db.execSQL("insert into assessments_answers values (3493,1,1,\"2015-09-15\",2,24,\"3.4\",\"Y\");");
//        db.execSQL("insert into assessments_answers values (3494,1,1,\"2015-09-15\",2,25,\"3.5\",\"Y\");");
//        db.execSQL("insert into assessments_answers values (3495,1,1,\"2015-09-15\",2,26,\"3.6\",\"Y\");");
//        db.execSQL("insert into assessments_answers values (3496,1,1,\"2015-09-15\",2,27,\"3.7\",\"Y\");");
//        db.execSQL("insert into assessments_answers values (3497,1,1,\"2015-09-15\",2,28,\"3.8\",\"Y\");");
//        db.execSQL("insert into assessments_answers values (3498,1,1,\"2015-09-15\",2,29,\"3.9\",\"Y\");");
//        db.execSQL("insert into assessments_answers values (3499,1,1,\"2015-09-15\",2,30,\"3.ten\",\"Y\");");
//        db.execSQL("insert into assessments_answers values (3500,1,1,\"2015-09-15\",2,40,\"5.1\",\"Y\");");

    }

    protected void not_load_person_to_assessments() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from person_to_assessments ");
        db.execSQL("insert into person_to_assessments values (1,1,1,\"2015-07-07\",2,1,0);");
        db.execSQL("insert into person_to_assessments values (2,1,1,\"2015-09-07\",2,1,0);");
        db.execSQL("insert into person_to_assessments values (3,1,1,\"2015-11-07\",2,1,0);");
        db.execSQL("insert into person_to_assessments values (4,1,1,\"2015-07-07\",3,1,0);");
        db.execSQL("insert into person_to_assessments values (5,1,1,\"2015-09-07\",3,1,0);");
        db.execSQL("insert into person_to_assessments values (6,1,1,\"2015-07-13\",2,1,0);");
        db.execSQL("insert into person_to_assessments values (7,16,3,\"2015-07-13\",2,1,0);");
        db.execSQL("insert into person_to_assessments values (8,42,417,\"2015-07-16\",4,1,0);");
        db.execSQL("insert into person_to_assessments values (14,42,417,\"2015-07-17\",4,1,0);");
        db.execSQL("insert into person_to_assessments values (15,42,417,\"2015-07-17\",3,1,0);");
        db.execSQL("insert into person_to_assessments values (16,42,417,\"2015-07-17\",2,1,0);");
        db.execSQL("insert into person_to_assessments values (17,42,417,\"2015-07-20\",15,1,0);");
        db.execSQL("insert into person_to_assessments values (18,42,417,\"2015-07-21\",15,1,0);");

    }

    protected void not_load_assessments_answers() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from assessments_answers ");
        db.execSQL("insert into assessments_answers values (3343,1,1,\"2015-07-07\",2,14,\"A\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3345,1,1,\"2015-07-07\",2,16,\"B\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3346,1,1,\"2015-07-07\",2,17,\"C\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3347,1,1,\"2015-07-07\",2,18,\"D\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3348,1,1,\"2015-07-07\",2,19,\"E\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3350,1,1,\"2015-07-07\",2,21,\"F\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3351,1,1,\"2015-07-07\",2,22,\"3.2.\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3352,1,1,\"2015-07-07\",2,23,\"3.3.\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3353,1,1,\"2015-07-07\",2,24,\"3.4.\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3354,1,1,\"2015-07-07\",2,25,\"3.5.\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3355,1,1,\"2015-07-07\",2,26,\"3.6.\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3356,1,1,\"2015-07-07\",2,27,\"3.7.\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3357,1,1,\"2015-07-07\",2,28,\"3.8.\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3358,1,1,\"2015-07-07\",2,29,\"3.9.\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3359,1,1,\"2015-07-07\",2,30,\"3.10\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3361,1,1,\"2015-07-07\",2,32,\"4.1.\",\"Y\");");
        //db.execSQL("insert into assessments_answers values (3362,1,1,\"2015-07-07\",2,33,\"4.2.\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3363,1,1,\"2015-07-07\",2,34,\"4.3.\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3364,1,1,\"2015-07-07\",2,36,\"4.5.\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3374,1,1,\"2015-07-07\",2,38,\"4.7.\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3377,1,1,\"2015-07-07\",2,40,\"5.1.\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3378,1,1,\"2015-07-07\",2,35,\"4.4.\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3379,1,1,\"2015-07-07\",2,37,\"4.6.\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3380,1,1,\"2015-09-07\",2,14,\"A\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3381,1,1,\"2015-09-07\",2,16,\"B\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3382,1,1,\"2015-09-07\",2,17,\"C\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3383,1,1,\"2015-09-07\",2,18,\"D\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3384,1,1,\"2015-09-07\",2,19,\"E\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3385,1,1,\"2015-09-07\",2,21,\"F\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3386,1,1,\"2015-09-07\",2,22,\"3.2. save\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3387,1,1,\"2015-09-07\",2,23,\"3.3.  2015-09-07\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3388,1,1,\"2015-09-07\",2,24,\"3.4.\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3389,1,1,\"2015-09-07\",2,25,\"3.5.\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3390,1,1,\"2015-09-07\",2,26,\"3.6.\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3391,1,1,\"2015-09-07\",2,27,\"3.7.\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3392,1,1,\"2015-09-07\",2,28,\"3.8.\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3393,1,1,\"2015-09-07\",2,29,\"3.9.\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3394,1,1,\"2015-09-07\",2,30,\"3.10\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3395,1,1,\"2015-09-07\",2,32,\"4.1.\",\"Y\");");
        //db.execSQL("insert into assessments_answers values (3396,1,1,\"2015-09-07\",2,33,\"4.2.\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3397,1,1,\"2015-09-07\",2,34,\"4.3.\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3398,1,1,\"2015-09-07\",2,36,\"4.5.\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3399,1,1,\"2015-09-07\",2,38,\"4.7.\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3400,1,1,\"2015-09-07\",2,40,\"5.1.\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3401,1,1,\"2015-07-07\",3,42,\"A\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3402,1,1,\"2015-07-07\",3,43,\"test comment loooooooooooooooooooooooooooooooooooooooooooooog comment\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3403,1,1,\"2015-07-07\",3,44,\"B\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3404,1,1,\"2015-07-07\",3,46,\"C\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3405,1,1,\"2015-07-07\",3,48,\"F\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3406,1,1,\"2015-07-07\",3,50,\"F\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3407,1,1,\"2015-07-07\",3,52,\"F\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3408,1,1,\"2015-07-07\",3,54,\"F\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3409,1,1,\"2015-07-07\",3,56,\"F\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3410,1,1,\"2015-07-07\",3,58,\"F\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3411,1,1,\"2015-07-07\",3,60,\"F\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3412,1,1,\"2015-07-07\",3,62,\"F\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3413,1,1,\"2015-07-07\",3,64,\"F\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3414,1,1,\"2015-07-07\",3,66,\"F\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3415,1,1,\"2015-07-07\",3,68,\"F\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3416,1,1,\"2015-09-07\",2,35,\"4.4. \",\"Y\");");
        db.execSQL("insert into assessments_answers values (3417,1,1,\"2015-09-07\",2,37,\"4.6.\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3418,1,1,\"2015-11-07\",2,14,\"A\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3419,1,1,\"2015-11-07\",2,16,\"B\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3420,1,1,\"2015-11-07\",2,17,\"C\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3421,1,1,\"2015-11-07\",2,18,\"D\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3422,1,1,\"2015-11-07\",2,19,\"E\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3423,1,1,\"2015-11-07\",2,21,\"F\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3424,1,1,\"2015-11-07\",2,40,\"2015-11-07\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3425,1,1,\"2015-11-07\",2,22,\"3.1.\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3426,1,1,\"2015-11-07\",2,23,\"2015-11-07\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3427,1,1,\"2015-07-07\",3,45,\"test 2\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3428,1,1,\"2015-07-07\",3,47,\"test 3\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3450,1,1,\"2015-07-13\",3,42,\"A\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3449,1,1,\"2015-07-13\",2,40,\"test test test\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3448,1,1,\"2015-07-13\",2,23,\"3.2 2015-07-13\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3480,1,1,\"2015-07-13\",2,22,\"3.1 update\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3446,1,1,\"2015-07-13\",2,21,\"F\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3445,1,1,\"2015-07-13\",2,19,\"E\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3444,1,1,\"2015-07-13\",2,18,\"D\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3443,1,1,\"2015-07-13\",2,17,\"C\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3442,1,1,\"2015-07-13\",2,16,\"B\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3441,1,1,\"2015-07-13\",2,14,\"A\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3451,1,1,\"2015-07-13\",3,43,\"comment 1 test looooooooooooooooooooooooooooooooooooooooooooooooooooooooog comment\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3452,1,1,\"2015-07-13\",3,44,\"F\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3453,1,1,\"2015-07-13\",3,46,\"F\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3454,1,1,\"2015-07-13\",3,48,\"F\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3455,1,1,\"2015-07-13\",3,50,\"F\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3456,1,1,\"2015-07-13\",3,52,\"F\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3457,1,1,\"2015-07-13\",3,54,\"F\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3458,1,1,\"2015-07-13\",3,56,\"F\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3459,1,1,\"2015-07-13\",3,58,\"F\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3460,1,1,\"2015-07-13\",3,60,\"F\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3461,1,1,\"2015-07-13\",3,62,\"F\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3462,1,1,\"2015-07-13\",3,64,\"F\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3463,1,1,\"2015-07-13\",3,66,\"F\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3464,1,1,\"2015-07-13\",3,68,\"A\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3465,1,1,\"2015-07-13\",3,69,\"comment 14\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3466,1,1,\"2015-07-07\",3,49,\"test 4\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3467,16,3,\"2015-07-13\",2,14,\"A\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3468,16,3,\"2015-07-13\",2,16,\"B\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3469,16,3,\"2015-07-13\",2,17,\"C\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3470,16,3,\"2015-07-13\",2,18,\"D\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3471,16,3,\"2015-07-13\",2,19,\"E\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3472,16,3,\"2015-07-13\",2,21,\"F\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3473,42,417,\"2015-07-16\",4,136,\"F\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3474,42,417,\"2015-07-20\",15,135,\"This is a test answer for q1\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3475,42,417,\"2015-07-20\",15,137,\"This is a textbox answer for q2\",\"Y\");");

//		MainActivity.db.execSQL("insert into assessments_answers values (3476,42,417,\"2015-07-20\",15,138,\"This is a long test answer for q2a
//				This is a long test answer for q2b
//		This is a long test answer for q2c
//		This is a long test answer for q2d
//		This is a long test answer for q2e
//		This is a long test answer for q2f
//		This is a long test answer for q2a
//		T...\",\"Y\");");

        db.execSQL("insert into assessments_answers values (3477,42,417,\"2015-07-21\",15,135,\"textarea q1\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3478,42,417,\"2015-07-21\",15,137,\"-\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3479,42,417,\"2015-07-21\",15,138,\"textbox q2\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3481,42,417,\"2015-07-21\",15,140,\"textarea q3\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3482,42,417,\"2015-07-21\",15,139,\"-\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3483,42,417,\"2015-07-21\",15,141,\"-\",\"Y\");");
        db.execSQL("insert into assessments_answers values (3484,42,417,\"2015-07-21\",15,142,\"F\",\"Y\");");
    }

    public String[][] getQuestionData(int personID, int facilityID, int date, int assessmentID) {
        //String query = "select * from person";
//        String query = "select " +
//                "aq.question, " +
//                "aq.itemtype, " +

//                "(select aa.answer from assessments_answers aa where aa.person = pa.person_id and aa.facility = pa.facility_id and aa.date_created = " +
//                "pa.date_created and a.assessment_id = aq.assessment_id  and aa.question = aq.assessments_questions_id) as answer " +
//                "from person_to_assessments pa " +
//                "join person p on p.person_id = pa.person_id " +
//                "join assessments a on pa.assessment_id = a.assessment_id " +
//                "join assessments_questions aq on a.assessment_id = aq.assessment_id " +
//                "where 1=1 " +
//                " and pa.person_id = " + personID +
//                " and pa.facility_id = " + facilityID +
//                " and pa.date_created = " + "\'2015-07-07\'" +
//                " and pa.assessment_id = " + assessmentID +
//                " and aq.status = 1 " +
//                "order by aq.itemorder; ";
//        Log.d("Query: ", query);

//        Cursor c = MainActivity.db.rawQuery(query, null);
//        while (c.moveToNext()) {
//
//        }
//        c.close();
        int question = 0;
        int itemtype = 1;
        int answer = 2;
        String[][] questionData = new String[30][3];

        String[] itemTypes = {"text", "question110", "questiontext", "questionyesno", "questionmulti"};
        Random r = new Random();

        questionData[0][question] = "What time is it?";
        questionData[0][itemtype] = "title";
        questionData[0][answer] = "3:00PM";


        for (int i=1; i<30; i++) {
            questionData[i][question] = "What time is it?";
            //questionData[i][itemtype] = itemTypes[r.nextInt(itemTypes.length)];
            questionData[i][itemtype] = itemTypes[1];
            questionData[i][answer] = "3:00PM";
        }

//        keyData[0] = c.getString(0);
//        keyData[1] = c.getString(1);
//        keyData[2] = c.getString(2);
//        keyData[3] = c.getString(3);

//        c.close();
        return questionData;
    }

} // class
