package org.itech.vmmc;

import android.app.NotificationManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import org.itech.vmmc.VolleySingleton;

import org.itech.vmmc.DBHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.itech.vmmc.MainActivity.LOG;

/**
 * Created by Caleb on 21/07/2017.
 */

public class putMySQLTableVolley {

    public boolean LOGGED_IN = false;
    Context mContext;
    public SQLiteDatabase _db;
    DBHelper dbhelp;
    static String LOG = "csl";

    public putMySQLTableVolley(Context context, final DBHelper dbHelper) {
        mContext = context;
        this.dbhelp = dbHelper;
        this._db = dbhelp.getReadableDatabase();
    }

    //put tables for uploadDbData()
    public void putAllDBData() {
        //check for json web token and login if doesn't exist
        if (MainActivity.jwt.equals("")) {
            Log.d(LOG, "Login attempt at " + MainActivity.LOGIN_URL);
            //Response handler on success
            Response.Listener<JSONObject> loginResponseListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try{
                        if (response.getString(MainActivity.TAG_SUCCESS).equals("0")) {
                            MainActivity.jwt = "";
                            MainActivity._pass = "";
                            LOGGED_IN = false;
                            Log.d(LOG, "Login unsuccessful in putAllTables");
                            Log.d(LOG, response.getString(MainActivity.TAG_MESSAGE));
                        } else {
                            MainActivity.jwt = response.getString("jwt");
                            Log.d(LOG, "Login success: " + MainActivity.jwt);
                            LOGGED_IN = true;
                            putTable(dbhelp.geolocationTableInfo);
                            putTable(dbhelp.personToAssessmentsTableInfo);
                            putTable(dbhelp.assessmentsAnswersTableInfo);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            //response handler on error
            Response.ErrorListener loginResponseErrorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(LOG, "error on login attempt");
                    error.printStackTrace();
                }
            };
            //make login request
            try {
                JSONObject credentials = new JSONObject("{username:" + MainActivity._user + ",password:" + MainActivity._pass + "}");
                JsonObjectRequest request = new JsonObjectRequest
                        (Request.Method.POST, MainActivity.LOGIN_URL, credentials, loginResponseListener, loginResponseErrorListener);
                VolleySingleton.getInstance(mContext).addToRequestQueue(request);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else { //already had a web token. login unneeded attempt all puts
            putTable(dbhelp.geolocationTableInfo);
            putTable(dbhelp.personToAssessmentsTableInfo);
            putTable(dbhelp.assessmentsAnswersTableInfo);
        }
    }

    //put all tables in sync process
    public void putAllTables() {
        //check for json web token and login if doesn't exist
        if (MainActivity.jwt.equals("")) {
            Log.d(LOG, "Login attempt at " + MainActivity.LOGIN_URL);
            //Response handler on success
            Response.Listener<JSONObject> loginResponseListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try{
                        if (response.getString(MainActivity.TAG_SUCCESS).equals("0")) {
                            MainActivity.jwt = "";
                            MainActivity._pass = "";
                            LOGGED_IN = false;
                            Log.d(LOG, "Login unsuccessful in putAllTables");
                            Log.d(LOG, response.getString(MainActivity.TAG_MESSAGE));
                        } else {
                            MainActivity.jwt = response.getString("jwt");
                            Log.d(LOG, "Login success: " + MainActivity.jwt);
                            LOGGED_IN = true;
                            putAllTablesVerified();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            //response handler on error
            Response.ErrorListener loginResponseErrorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(LOG, "error on login attempt");
                    error.printStackTrace();
                }
            };
            //make login request
            try {
                JSONObject credentials = new JSONObject("{username:" + MainActivity._user + ",password:" + MainActivity._pass + "}");
                JsonObjectRequest request = new JsonObjectRequest
                        (Request.Method.POST, MainActivity.LOGIN_URL, credentials, loginResponseListener, loginResponseErrorListener);
                VolleySingleton.getInstance(mContext).addToRequestQueue(request);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else { //already had a web token. login unneeded attempt all puts
            putAllTablesVerified();
        }
    }
    public void putAllTablesVerified() {
        putTable(dbhelp.personTableInfo);
        putTable(dbhelp.userTableInfo);
        putTable(dbhelp.bookingTableInfo);
        putTable(dbhelp.clientTableInfo);
        putTable(dbhelp.facilitatorTableInfo);
        putTable(dbhelp.interactionTableInfo);
        putTable(dbhelp.groupActivityTableInfo);
    }


    private void putTable(JSONObject tableInfo) {
        try {
            final String dataTable = tableInfo.getString("dataTable");
            final JSONArray fields = tableInfo.getJSONArray("fields");
            final String url = MainActivity.INDEX_URL + "/" + dataTable;
            Log.d(LOG, url);

            //response listener on success
            Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getString(MainActivity.TAG_SUCCESS).equals("1")) {
                            //int num_GroupActiviy_recs = json.getInt("number_records");
                            Log.d(LOG, "Server returned success for POST " + dataTable);
                            //LOGGED_IN = true;
                        } else {
                            Log.d(LOG, "Server returned ERROR for POST " + dataTable);
                            MainActivity._pass = "";
                            MainActivity.jwt = "";
                            LOGGED_IN = false;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            //response listener called on error
            Response.ErrorListener responseErrorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Log.d(LOG, error.toString());
                }
            };

            JsonObjectRequest request = new JsonObjectRequest
                    (Request.Method.POST, url, createRequestData(dataTable, fields), responseListener, responseErrorListener){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    String auth = "Bearer " + MainActivity.jwt;
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", auth);
                    return headers;
                }
            };
            VolleySingleton.getInstance(mContext).addToRequestQueue(request);
        } catch (JSONException e) {
            Log.d(LOG, "Error in putTable");
            e.printStackTrace();
        }
    }

    private JSONObject createRequestData(String dataTable, JSONArray fields) {
        JSONObject requestData = null;
        try {
            if (dataTable.equals("user")) {
                requestData = createUserRequestData();
            } else if (dataTable.equals("person")) {
                requestData = createPersonRequestData();
            } else if (dataTable.equals("booking")) {
                requestData = createBookingRequestData();
            } else if (dataTable.equals("client_table")) {
                requestData = createClientRequestData();
            } else if (dataTable.equals("facilitator")) {
                requestData = createFacilitatorRequestData();
            } else if (dataTable.equals("interaction")) {
                requestData = createInteractionRequestData();
            } else if (dataTable.equals("group_activity")) {
                requestData = createGroupActivityRequestData();

            } else if (dataTable.equals("geolocations")) {
                requestData = createGeolocationRequestData();
            } else if (dataTable.equals("person_to_assessments")) {
                requestData = createPersonToAssessmentsRequestData();
            } else if (dataTable.equals("assessments_answers")) {
                requestData = createAssessmentsAnswersRequestData();
            }

        } catch (Exception e) {
            Log.d(LOG, "error in createRequestData for " + dataTable);
        }
        return requestData;
    }

    private JSONObject createAssessmentsAnswersRequestData() {
        List<AssessmentsAnswers> assessmentsAnswersList = dbhelp.getAllAssessmentsAnswers();
        JSONObject requestObject = new JSONObject();
        JSONArray recsJSON = new JSONArray();
        for (AssessmentsAnswers poa: assessmentsAnswersList) {
            String rec =  "[\"" +
                    poa.get_assess_id() + "\",\"" +
                    poa.get_person() + "\",\"" +
                    poa.get_facility() + "\",\"" +
                    poa.get_date_created() + "\",\"" +
                    poa.get_assessment_id() + "\",\"" +
                    poa.get_question() + "\",\"" +
                    poa.get_answer() + "\"]";
            try {
                JSONArray recJSON = new JSONArray(rec);
                recsJSON.put(recJSON);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            requestObject.put("datatable", recsJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObject;
    }

    private JSONObject createPersonToAssessmentsRequestData() {
        List<PersonToAssessments> personToAssessmentsList = dbhelp.getAllPersonToAssessments();
        JSONObject requestObject = new JSONObject();
        JSONArray recsJSON = new JSONArray();
        for (PersonToAssessments poa: personToAssessmentsList) {
            String rec =  "[\"" +
                    poa.get_person_id() + "\",\"" +
                    poa.get_facility_id() + "\",\"" +
                    poa.get_date_created() + "\",\"" +
                    poa.get_assessment_id() + "\",\"" +
                    poa.get_user_id() + "\"]";
            try {
                JSONArray recJSON = new JSONArray(rec);
                recsJSON.put(recJSON);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            requestObject.put("datatable", recsJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObject;
    }

    private JSONObject createGeolocationRequestData() {
        List<GeoLocations> geoLocationsList = dbhelp.getAllGeoLocations();
        JSONObject requestObject = new JSONObject();
        JSONArray recsJSON = new JSONArray();
        for (GeoLocations geoLocations: geoLocationsList) {
            String rec =  "[\"" +
                    geoLocations.get_longitude() + "\",\"" +
                    geoLocations.get_latitude() + "\",\"" +
                    geoLocations.get_device_id() + "\",\"" +
                    geoLocations.get_created_at() + "\",\"" +
                    geoLocations.get_username() + "\",\"" +
                    geoLocations.get_password() + "\"]";
            try {
                JSONArray recJSON = new JSONArray(rec);
                recsJSON.put(recJSON);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            requestObject.put("datatable", recsJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObject;
    }

    private JSONObject createPersonRequestData() {
        List<Person> personList = dbhelp.getAllPersons();
        JSONObject requestObject = new JSONObject();
        JSONArray recsJSON = new JSONArray();
        for (Person person: personList) {
            String rec =  "[\"" +
                    person.get_timestamp() + "\",\"" +
                    person.get_first_name() + "\",\"" +
                    person.get_last_name() + "\",\"" +
                    person.get_national_id() + "\",\"" +
                    person.get_address_id() + "\",\"" +
                    person.get_phone() + "\",\"" +
                    person.get_dob() + "\",\"" +
                    person.get_gender() + "\",\"" +
                    Double.toString(person.get_latitude()) + "\",\"" +
                    Double.toString(person.get_longitude()) + "\",\"" +
                    person.get_is_deleted() + "\"]";
            try {
                JSONArray recJSON = new JSONArray(rec);
                recsJSON.put(recJSON);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            requestObject.put("datatable", recsJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObject;
    }

    private JSONObject createFacilitatorRequestData() {
        List<Facilitator> facilitatorList = dbhelp.getAllFacilitators();
        Log.d(LOG, "putMySQLFacilitatorTable build rec: " + facilitatorList.size() );
        JSONObject requestObject = new JSONObject();
        JSONArray recsJSON = new JSONArray();
        for (Facilitator facilitator: facilitatorList) {
            String rec = "[\"" +
                    facilitator.get_timestamp() + "\",\"" +
                    facilitator.get_first_name() + "\",\"" +
                    facilitator.get_last_name() + "\",\"" +
                    facilitator.get_national_id() + "\",\"" +
                    facilitator.get_phone() + "\",\"" +
                    facilitator.get_facilitator_type_id() + "\",\"" +
                    facilitator.get_note() + "\",\"" +
                    facilitator.get_location_id() + "\",\"" +
                    facilitator.get_latitude() + "\",\"" +
                    facilitator.get_longitude()   + "\",\"" +
                    facilitator.get_institution_id() + "\",\"" +
                    facilitator.get_address_id() + "\",\"" +
                    facilitator.get_dob() + "\",\"" +
                    facilitator.get_gender() + "\"]";

            try {
                JSONArray recJSON = new JSONArray(rec);
                recsJSON.put(recJSON);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            requestObject.put("datatable", recsJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObject;
    }

    private JSONObject createClientRequestData() {
        List<Client> clientList = dbhelp.getAllClients();
        Log.d(LOG, "putMySQLClientTable build rec: " + clientList.size() );
        JSONObject requestObject = new JSONObject();
        JSONArray recsJSON = new JSONArray();
        for (Client client: clientList) {
            String rec = "[\"" +
                    client.get_timestamp() + "\",\"" +
                            client.get_first_name() + "\",\"" +
                            client.get_last_name() + "\",\"" +
                            client.get_national_id() + "\",\"" +
                            client.get_phone() + "\",\"" +
                            client.get_status_id() + "\",\"" +
                            client.get_loc_id() + "\",\"" +
                            client.get_latitude() + "\",\"" +
                            client.get_longitude()   + "\",\"" +
                            client.get_institution_id()   + "\",\"" +
                            client.get_group_activity_name()   + "\",\"" +
                            client.get_group_activity_date()   + "\",\"" +
                            client.get_address_id()   + "\",\"" +
                            client.get_dob()   + "\",\"" +
                            client.get_gender() + "\"]";
            try {
                JSONArray recJSON = new JSONArray(rec);
                recsJSON.put(recJSON);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            requestObject.put("datatable", recsJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObject;
    }

    private JSONObject createBookingRequestData() {
        List<Booking> BookingList = dbhelp.getAllBookings();
        Log.d(LOG, "putMySQLBookingTable build rec: " + BookingList.size() );
        JSONObject requestObject = new JSONObject();
        JSONArray recsJSON = new JSONArray();
        for (Booking booking: BookingList) {
            String rec = "[\"" +
                    booking.get_timestamp() + "\",\"" +
                            booking.get_first_name() + "\",\"" +
                            booking.get_last_name() + "\",\"" +
                            booking.get_national_id() + "\",\"" +
                            booking.get_phone() + "\",\"" +
                            booking.get_fac_first_name() + "\",\"" +
                            booking.get_fac_last_name() + "\",\"" +
                            booking.get_fac_national_id() + "\",\"" +
                            booking.get_fac_phone() + "\",\"" +
                            booking.get_location_id() + "\",\"" +
                            booking.get_latitude() + "\",\"" +
                            booking.get_longitude() + "\",\"" +
                            booking.get_projected_date() + "\",\"" +
//                        Double.toString(Booking.get_latitude()) + "\",\"" +
//                        Double.toString(Booking.get_longitude()) + "\",\"" +
                            booking.get_actual_date() + "\",\"" +
                            booking.get_consent() + "\",\"" +
                            booking.get_procedure_type_id() + "\",\"" +
                            booking.get_followup_id() + "\",\"" +
                            booking.get_followup_date() + "\",\"" +
//                                booking.get_contact() + "\",\"" +
                            booking.get_alt_contact() + "\"]";
            try {
                JSONArray recJSON = new JSONArray(rec);
                recsJSON.put(recJSON);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            requestObject.put("datatable", recsJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObject;
    }

    private JSONObject createInteractionRequestData() {
        List<Interaction> interactionList = dbhelp.getAllInteractions();
        Log.d(LOG, "putMySQLInteractionTable build rec: " + interactionList.size() );
        JSONObject requestObject = new JSONObject();
        JSONArray recsJSON = new JSONArray();
        for (Interaction interaction: interactionList) {
            String rec = "[\"" +
                    interaction.get_timestamp() + "\",\"" +
                            interaction.get_fac_first_name() + "\",\"" +
                            interaction.get_fac_last_name() + "\",\"" +
                            interaction.get_fac_national_id() + "\",\"" +
                            interaction.get_fac_phone() + "\",\"" +
                            interaction.get_person_first_name() + "\",\"" +
                            interaction.get_person_last_name() + "\",\"" +
                            interaction.get_person_national_id() + "\",\"" +
                            interaction.get_person_phone() + "\",\"" +
                            interaction.get_interaction_date() + "\",\"" +
                            interaction.get_followup_date() + "\",\"" +
                            interaction.get_type_id() + "\",\"" +
                            interaction.get_note() + "\"]";

            try {
                JSONArray recJSON = new JSONArray(rec);
                recsJSON.put(recJSON);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            requestObject.put("datatable", recsJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObject;
    }

    private JSONObject createGroupActivityRequestData() {
        List<GroupActivity> groupActivityList = dbhelp.getAllGroupActivities();
        Log.d(LOG, "putMySQLGroupActivityTable build rec: " + groupActivityList.size() );
        JSONObject requestObject = new JSONObject();
        JSONArray recsJSON = new JSONArray();
        for (GroupActivity groupActivity: groupActivityList) {
            Log.d(LOG, "putMySQLGroupActivityTable loop: " + groupActivity.get_institution_id() + ", " + groupActivity.get_males() + ", " + groupActivity.get_females() + groupActivity.get_messages() );
            String rec = "[\"" +
                    groupActivity.get_name() + "\",\"" +
                            groupActivity.get_timestamp() + "\",\"" +
                            groupActivity.get_location_id() + "\",\"" +
                            groupActivity.get_activity_date() + "\",\"" +
                            groupActivity.get_group_type_id() + "\",\"" +
                            groupActivity.get_institution_id() + "\",\"" +
                            groupActivity.get_males() + "\",\"" +
                            groupActivity.get_females() + "\",\"" +
                            groupActivity.get_messages() + "\",\"" +
                            groupActivity.get_latitude() + "\",\"" +
                            groupActivity.get_longitude() + "\"]";
            try {
                JSONArray recJSON = new JSONArray(rec);
                recsJSON.put(recJSON);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            requestObject.put("datatable", recsJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObject;
    }

    private JSONObject createUserRequestData() {
        List<User> userList = dbhelp.getAllUsers();
        Log.d(LOG, "putMySQLUserTable build rec: " + userList.size() );
        JSONObject requestObject = new JSONObject();
        JSONArray recsJSON = new JSONArray();
        for (User user: userList) {
            Log.d(LOG, "putMySQLUserTable loop: " + user.get_timestamp() + ":" + user.get_username()  );
            String rec = "[\"" +
                    user.get_timestamp() + "\",\"" +
                    user.get_username() + "\",\"" +
                    user.get_password() + "\",\"" +
                    user.get_email() + "\",\"" +
                    user.get_first_name() + "\",\"" +
                    user.get_last_name() + "\",\"" +
                    user.get_national_id() + "\",\"" +
                    user.get_phone() + "\",\"" +
                    user.get_region_id() + "\",\"" +
                    user.get_user_type_id() + "\",\"" +
                    user.get_location_id() + "\",\"" +
                    user.get_modified_by() + "\",\"" +
                    user.get_created_by() + "\",\"" +
                    user.get_is_blocked() + "\",\"" +
                    user.get_timestamp_updated() + "\",\"" +
                    user.get_timestamp_created() + "\",\"" +
                    user.get_timestamp_last_login() + "\"]";
            try {
                JSONArray recJSON = new JSONArray(rec);
                recsJSON.put(recJSON);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            requestObject.put("datatable", recsJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObject;
    }
}
