package org.itech.vmmc;

import android.app.NotificationManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.v4.app.NotificationCompat;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import org.itech.vmmc.VolleySingleton;

import org.itech.vmmc.DBHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Caleb on 21/07/2017.
 */

public class getMySQLTableVolley {

    private boolean LOGGED_IN = false;
    public Context _context;
    public SQLiteDatabase _db;
    DBHelper dbhelp;
    static String LOG = "csl";

    public getMySQLTableVolley(Context context, DBHelper dbHelper) {
        this._context = context;
        this.dbhelp = dbHelper;
        this._db = dbHelper.getWritableDatabase();
    }

    public void getAllTables() {
        //if no jwt, attempt login. getTables called in login response handler
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
                            Log.d(LOG, "Login unsuccessful in getAllTables");
                            Log.d(LOG, response.getString(MainActivity.TAG_MESSAGE));
                        } else {
                            MainActivity.jwt = response.getString("jwt");
                            Log.d(LOG, "Login success: " + MainActivity.jwt);
                            LOGGED_IN = true;
                            getAllTablesVerified();
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
                VolleySingleton.getInstance(_context).addToRequestQueue(request);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else { //already had a web token. login unneeded attempt all puts
            getAllTablesVerified();
        }
    }

    public void getAllDBData() {
        //if no jwt, attempt login. getTables called in login response handler
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
                            Log.d(LOG, "Login unsuccessful in getAllDBData");
                            Log.d(LOG, response.getString(MainActivity.TAG_MESSAGE));
                        } else {
                            MainActivity.jwt = response.getString("jwt");
                            Log.d(LOG, "Login success: " + MainActivity.jwt);
                            LOGGED_IN = true;
                            Log.d(LOG, "downloadDBData getMySQLPersonTable");
                            getTable(dbhelp.personTableInfo);
                            Log.d(LOG, "downloadDBData getMySQLAssessmentsQuestionsTable");
                            getTable(dbhelp.assessmentsQuestionsTableInfo);
                            Log.d(LOG, "downloadDBData getMySQLAssessmentsTable");
                            getTable(dbhelp.assessmentsTableInfo);
                            Log.d(LOG, "downloadDBData getMySQLQuestionDropdownOptionTable");
                            getTable(dbhelp.questionDropdownTableInfo);
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
                VolleySingleton.getInstance(_context).addToRequestQueue(request);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else { //already had a web token. login unneeded attempt all puts
            Log.d(LOG, "downloadDBData getMySQLPersonTable");
            getTable(dbhelp.personTableInfo);
            Log.d(LOG, "downloadDBData getMySQLAssessmentsQuestionsTable");
            getTable(dbhelp.assessmentsQuestionsTableInfo);
            Log.d(LOG, "downloadDBData getMySQLAssessmentsTable");
            getTable(dbhelp.assessmentsTableInfo);
            Log.d(LOG, "downloadDBData getMySQLQuestionDropdownOptionTable");
            getTable(dbhelp.questionDropdownTableInfo);
        }
    }

    private void getTable(JSONObject tableInfo) {
        try {
            final String dataTable = tableInfo.getString("dataTable");
            final JSONArray fields = tableInfo.getJSONArray("fields");
            final String url = MainActivity.INDEX_URL + "/" + dataTable;
            Log.d(LOG, "GET table request at " + url);

            //Response handler on success
            Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getString(MainActivity.TAG_SUCCESS).equals("0")) {
                            Log.d(LOG, "Server returned ERROR for GET "
                                    + dataTable + ": " + response.getString(MainActivity.TAG_MESSAGE));
                            if (response.getString(MainActivity.TAG_MESSAGE).contains("Invalid jwt")) {
                                MainActivity.jwt = "";
                                MainActivity._pass = "";
                                LOGGED_IN = false;
                            }
                            return;
                        }
                        Log.d(LOG, "Server returned success for GET "
                                + dataTable);
                        JSONArray results_JSONarray = response.getJSONArray("posts");
                        int num_recs = results_JSONarray.length();
                        int num_fields = fields.length();
                        int id = 1;
                        int i = 0;

                        NotificationManager mNotifyManager = (NotificationManager) _context.getSystemService(_context.NOTIFICATION_SERVICE);
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(_context);
                        mBuilder.setContentTitle("Data Download")
                                .setContentText("Download in progress")
                                .setSmallIcon(R.drawable.download);

                        String fields_string = fields.join(", ");
                        //create strings from received data for sql statement
                        //loop for each record received
                        for (i = 0; i < num_recs; ++i) {
                            JSONObject _rec = results_JSONarray.getJSONObject(i);
                            String results_string = "";
                            //loop to format data for insert or replace
                            for (int j = 0; j < num_fields; ++j) {
                                String value = fields.getString(j);
                                int SQLindex = j + 1;
                                if (j == 0) {
                                    results_string += "'" + encodeForSQL(_rec.getString(value)) + "'";
                                } else {
                                    results_string += ", '" + encodeForSQL(_rec.getString(value)) + "'";
                                }
                            }

                            SQLiteStatement _insert = _db.compileStatement(
                                    "insert or replace into " + dataTable.toLowerCase()
                                            + " (" + fields_string + ") "
                                            + " values(" + results_string + ");");
                            try {
                                int incr = (int) ((i / (float) num_recs) * 100);
                                mBuilder.setProgress(100, incr, false);
                                mNotifyManager.notify(id, mBuilder.build());
                                //Log.d(LOG, "getMySQLUserTable personInsert " + personInsert.toString() + " " + i);
                                //Log.d(LOG, "getMySQLUserTable personInsert " + " " + i);

                                _insert.execute();
                            } catch (Exception ex) {
                                Log.d(LOG, "getMySQLTable loop exception > " + ex.toString());
                            }
                        }
                        mBuilder.setContentText(_context.getResources().getString(R.string.sync_complete) + " (" + i + " records)").setProgress(0, 0, false);
                        mNotifyManager.notify(id, mBuilder.build());
                    } catch (JSONException e) {
                        Log.d(LOG, "exception > GET request for: " + dataTable);
                        Log.d(LOG, "exception > Fields: " + fields.toString());
                        Log.d(LOG, "exception > received JSONObject" + response.toString());
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        Log.d(LOG, "exception > GET request for: " + dataTable);
                        Log.d(LOG, "exception > Fields: " + fields.toString());
                        e.printStackTrace();
                    }
                }
            };
            //Response handler on error
            Response.ErrorListener responseErrorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(LOG, "error on GET at " + url);
                    error.printStackTrace();
                }
            };
            //attach jwt and make send request
            JsonObjectRequest request = new JsonObjectRequest
                    (Request.Method.GET, url, null, responseListener, responseErrorListener) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    String auth = "Bearer " + MainActivity.jwt;
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", auth);
                    return headers;
                }
            };
            VolleySingleton.getInstance(_context).addToRequestQueue(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String encodeForSQL(String string) {
        if (string == null)
            return null;
        String encodedSQL = string.replace("'", "''");
        return encodedSQL;
    }

    public void getAllTablesVerified() {
        getTable(dbhelp.personTableInfo);
        getTable(dbhelp.userTableInfo);
        getTable(dbhelp.userTypeTableInfo);
        getTable(dbhelp.userToAclTableInfo);
        getTable(dbhelp.aclTableInfo);
        getTable(dbhelp.clientTableInfo);
        getTable(dbhelp.facilitatorTableInfo);
        getTable(dbhelp.locationTableInfo);
        getTable(dbhelp.addressTableInfo);
        getTable(dbhelp.regionTableInfo);
        getTable(dbhelp.constituencyTableInfo);
        getTable(dbhelp.bookingTableInfo);
        getTable(dbhelp.interactionTableInfo);
        getTable(dbhelp.geolocationTableInfo);
        getTable(dbhelp.facilitatorTypeTableInfo);
        getTable(dbhelp.procedureTypeTableInfo);
        getTable(dbhelp.followupTableInfo);
        getTable(dbhelp.interactionTypeTableInfo);
        getTable(dbhelp.statusTypeTableInfo);
        getTable(dbhelp.institutionTableInfo);
        getTable(dbhelp.groupActivityTableInfo);
        getTable(dbhelp.groupTypeTableInfo);
    }
}
