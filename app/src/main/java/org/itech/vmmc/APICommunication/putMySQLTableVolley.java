package org.itech.vmmc.APICommunication;

import android.app.NotificationManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.itech.vmmc.Booking;
import org.itech.vmmc.Client;
import org.itech.vmmc.DBHelper;
import org.itech.vmmc.Facilitator;
import org.itech.vmmc.GroupActivity;
import org.itech.vmmc.Interaction;
import org.itech.vmmc.MainActivity;
import org.itech.vmmc.Person;
import org.itech.vmmc.R;
import org.itech.vmmc.SyncAudit;
import org.itech.vmmc.SyncTableObjects;
import org.itech.vmmc.User;
import org.itech.vmmc.VolleySingleton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Caleb on 21/07/2017.
 */

public class putMySQLTableVolley {

    Context _context;
    public SQLiteDatabase _db;
    DBHelper dbhelp;
    LoginManager loginManager;
    private static String LOG = "csl";

    NotificationManager mNotifyManager;
    NotificationCompat.Builder mBuilder;

    int SOCKET_TIMEOUT_MS = 60000;
    int MAX_RETRY = 3;

    public putMySQLTableVolley(Context context, final DBHelper dbHelper) {
        _context = context;
        this.dbhelp = dbHelper;
        this._db = dbhelp.getReadableDatabase();
        loginManager = new LoginManager(_context, dbHelper);

        mNotifyManager = (NotificationManager) this._context.getSystemService(this._context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this._context);
    }

    //called by DBHelper.uploadDbData()

    public void putAllTables() {
        mBuilder.setContentTitle("Data Upload")
                .setContentText("Upload in progress")
                .setSmallIcon(R.drawable.upload);
        //check for json web token and login if doesn't exist
        if (!loginManager.hasValidJWT()) {
            loginManager.logIn(new NetworkResponseCallback() {
                @Override
                public void onSuccess() {
                    putSyncTables();
                }
            }, MainActivity._user, MainActivity._pass);
        } else {
            putSyncTables();
        }
    }

    public void putSyncAuditTable() {
        mBuilder.setContentTitle("Data Upload")
                .setContentText("Upload in progress")
                .setSmallIcon(R.drawable.upload);
        //check for json web token and login if doesn't exist
        if (!loginManager.hasValidJWT()) {
            loginManager.logIn(new NetworkResponseCallback() {
                @Override
                public void onSuccess() {
                    putOnlySyncAuditTable();
                }
            }, MainActivity._user, MainActivity._pass);
        } else {
            putOnlySyncAuditTable();
        }
    }

    //put all tables in regular database sync
    private void putSyncTables() {
        /*
        SyncTableObjects syncTableObjects = new SyncTableObjects();
        putTable(syncTableObjects.userTableInfo, MAX_RETRY);
        putTable(syncTableObjects.bookingTableInfo, MAX_RETRY);
        putTable(syncTableObjects.clientTableInfo, MAX_RETRY);
        putTable(syncTableObjects.facilitatorTableInfo, MAX_RETRY);
        putTable(syncTableObjects.groupActivityTableInfo, MAX_RETRY);
        */
    }

    private void putOnlySyncAuditTable() {
        SyncTableObjects syncTableObjects = new SyncTableObjects();
        putTable(syncTableObjects.syncAuditTableInfo, MAX_RETRY);
    }


    private void putTable(final JSONObject tableInfo, final int numRetry) {
        if (numRetry <= 0) {
            return;
        }
        final SyncAudit syncAudit = new SyncAudit();
        try {
            final String dataTable = tableInfo.getString("dataTable");
            final JSONArray fields = tableInfo.getJSONArray("fields");
            final String url = MainActivity.INDEX_URL + "/" + dataTable;
            JSONObject requestData = createRequestData(dataTable, fields);
            syncAudit.set_progress(dataTable + ":" + requestData.getJSONArray("datatable").length());
            syncAudit.set_status("");

            AuthenticatedRequest request = new AuthenticatedRequest
                    (Request.Method.POST, url, requestData,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        if (response.getString(MainActivity.TAG_SUCCESS).equals("1")) {
                                            Log.d(LOG, "Server returned success for POST " + dataTable);
                                        } else {
                                            Log.d(LOG, "Server returned ERROR for POST " + dataTable
                                                    + ": " + response.getString(MainActivity.TAG_MESSAGE));
                                            syncAudit.set_status("Post Error");
                                            if (response.getString(MainActivity.TAG_MESSAGE).contains("jwt")) {
                                                if (loginManager.hasValidJWT()) {
                                                    loginManager.invalidateJWT();
                                                    MainActivity._pass = "";
                                                    Toast.makeText(_context, _context.getResources().getString(R.string.failed_sync_jwt), Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }
                                        dbhelp.addSyncAudit(syncAudit);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        syncAudit.set_status(e.toString());
                                        dbhelp.addSyncAudit(syncAudit);
                                        putTable(tableInfo, numRetry - 1);
                                    }
                                }
                        }, new Response.ErrorListener() {
                            @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.printStackTrace();
                                    Log.d(LOG, error.toString());
                                    Log.d(LOG, "Server ERROR for POST " + dataTable);
                                    syncAudit.set_status(error.toString());
                                    dbhelp.addSyncAudit(syncAudit);
                                    putTable(tableInfo, numRetry - 1);
                                }
                        }
                    );
            request.setRetryPolicy(new DefaultRetryPolicy(
                    SOCKET_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
            );
            VolleySingleton.getInstance(_context).addToRequestQueue(request);
        } catch (JSONException e) {
            Log.d(LOG, "Error in putTable");
            syncAudit.set_status(e.toString());
            dbhelp.addSyncAudit(syncAudit);
            e.printStackTrace();
        }
    }

    private JSONObject createRequestData(String dataTable, JSONArray fields) {
        JSONObject requestData = null;
        try {
            if (dataTable.equals("user")) {
                requestData = createUserRequestData();
//            } else if (dataTable.equals("person")) {
//                requestData = createPersonRequestData();
            } else if (dataTable.equals("booking")) {
                requestData = createBookingRequestData();
            } else if (dataTable.equals("sync_audit")) {
                requestData = createSyncAuditRequestData();
            } else if (dataTable.equals("client_table")) {
                requestData = createClientRequestData();
            } else if (dataTable.equals("facilitator")) {
                requestData = createFacilitatorRequestData();
//            } else if (dataTable.equals("interaction")) {
//                requestData = createInteractionRequestData();
            } else if (dataTable.equals("group_activity")) {
                requestData = createGroupActivityRequestData();
            }
        } catch (Exception e) {
            Log.d(LOG, "error in createRequestData for " + dataTable);
            e.printStackTrace();
        }
        return requestData;
    }

    private JSONObject createSyncAuditRequestData() {
        List<SyncAudit> syncAuditList = dbhelp.getAllSyncAudits();
        JSONObject requestObject = new JSONObject();
        JSONArray recsJSON = new JSONArray();
        int i = 0;
        int id = 1;
        int num_recs = syncAuditList.size();
        for (SyncAudit syncAudit: syncAuditList) {
            String rec =  "[\"" +
                    syncAudit.get_timestamp() + "\",\"" +
                    Double.toString(syncAudit.get_latitude()) + "\",\"" +
                    Double.toString(syncAudit.get_longitude()) + "\",\"" +
                    syncAudit.get_device_id() + "\",\"" +
                    syncAudit.get_username() + "\",\"" +
                    syncAudit.get_password() + "\",\"" +
                    syncAudit.get_progress() + "\",\"" +
                    syncAudit.get_status() + "\"]";
            int incr = (int)((i / (float) num_recs) * 100);
            mBuilder.setProgress(100, incr, false);
            mNotifyManager.notify(id, mBuilder.build());
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
        int i = 0;
        int id = 1;
        int num_recs = personList.size();
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
            int incr = (int)((i / (float) num_recs) * 100);
            mBuilder.setProgress(100, incr, false);
            mNotifyManager.notify(id, mBuilder.build());
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
        int i = 0;
        int id = 1;
        int num_recs = facilitatorList.size();
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
            int incr = (int)((i / (float) num_recs) * 100);
            mBuilder.setProgress(100, incr, false);
            mNotifyManager.notify(id, mBuilder.build());

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
        int i = 0;
        int id = 1;
        int num_recs = clientList.size();
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
                            client.get_fac_first_name() + "\",\"" +
                            client.get_fac_last_name() + "\",\"" +
                            client.get_fac_national_id() + "\",\"" +
                            client.get_fac_phone() + "\",\"" +
                            client.get_address_id()   + "\",\"" +
                            client.get_dob()   + "\",\"" +
                            client.get_gender()   + "\",\"" +
                            client.get_origination()   + "\",\"" +
                            client.get_created_by()   + "\",\"" +
                            client.get_modified_by()   + "\",\"" +
                            client.get_created()   + "\",\"" +
                            client.get_modified() + "\"]";
            int incr = (int)((i / (float) num_recs) * 100);
            mBuilder.setProgress(100, incr, false);
            mNotifyManager.notify(id, mBuilder.build());
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
        int i = 0;
        int id = 1;
        int num_recs = BookingList.size();
        for (Booking booking: BookingList) {
            String rec = "[\"" +
                    booking.get_timestamp() + "\",\"" +
                            booking.get_first_name() + "\",\"" +
                            booking.get_last_name() + "\",\"" +
                            booking.get_national_id() + "\",\"" +
                            booking.get_phone() + "\",\"" +
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
            int incr = (int)((i / (float) num_recs) * 100);
            mBuilder.setProgress(100, incr, false);
            mNotifyManager.notify(id, mBuilder.build());
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
        int i = 0;
        int id = 1;
        int num_recs = interactionList.size();
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
                            interaction.get_note() + "\"]";             int incr = (int)((i / (float) num_recs) * 100);             mBuilder.setProgress(100, incr, false);             mNotifyManager.notify(id, mBuilder.build());

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
        int i = 0;
        int id = 1;
        int num_recs = groupActivityList.size();
        for (GroupActivity groupActivity: groupActivityList) {
            //Log.d(LOG, "putMySQLGroupActivityTable loop: " + groupActivity.get_institution_id() + ", " + groupActivity.get_males() + ", " + groupActivity.get_females() + groupActivity.get_messages() );
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
            int incr = (int)((i / (float) num_recs) * 100);
            mBuilder.setProgress(100, incr, false);
            mNotifyManager.notify(id, mBuilder.build());
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
        int i = 0;
        int id = 1;
        int num_recs = userList.size();
        for (User user: userList) {
            //Log.d(LOG, "putMySQLUserTable loop: " + user.get_timestamp() + ":" + user.get_username()  );
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
            int incr = (int)((i / (float) num_recs) * 100);
            mBuilder.setProgress(100, incr, false);
            mNotifyManager.notify(id, mBuilder.build());
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
