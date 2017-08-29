package org.itech.vmmc;

import android.app.NotificationManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static org.itech.vmmc.MainActivity.LOG;

/**
 * Created by rossumg on 8/1/2015.
 */
class getMySQLGroupActivityTable extends AsyncTask<String, String, String> {

    private boolean LOGGED_IN = false;
    public Context _context;
    public SQLiteDatabase _db;
    DBHelper dbhelp;

    getMySQLGroupActivityTable(Context context, DBHelper dbhelp) {
        this._context = context;
        this._db = dbhelp.getWritableDatabase();
//        this._db.execSQL("delete from facilitator_table");
    }

    @Override
    protected String doInBackground(String... args) {
        Log.d(LOG, "getMySQLGroupActivityTable doInBackground ");

        try {
            //Thread.sleep(4000); // 4 secs
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // TODO Auto-generated method stub
        // Check for success tag
        int success;
        int i = 0;

        String username = MainActivity._user;
        String password = MainActivity._pass;
        Log.d(LOG, "getMySQLGroupActivityTable username/password: " + username + " " + password);
        String datatable = "getGroupActivity";
        try {
            URL url = null;
            try {
                url = new URL(MainActivity.GET_TABLE_URL);
                Log.d(LOG, "getMySQLGroupActivityTable GET_TABLE_URL " + url.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            data += "&" + URLEncoder.encode("datatable", "UTF-8") + "=" + URLEncoder.encode(datatable, "UTF-8");
            JSONParser jsonParser = new JSONParser();
            String reply = jsonParser.makeHttpRequest(url, "POST", data);
            JSONObject json = new JSONObject(reply);
            success = json.getInt(MainActivity.TAG_SUCCESS);
            int num_recs;
            if (success == 1) {
                LOGGED_IN = true;
                num_recs = json.getInt("number_records");
                JSONArray _array = json.getJSONArray("posts");

                NotificationManager mNotifyManager = (NotificationManager) this._context.getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this._context);

                mBuilder.setContentTitle("Data Download")
                        .setContentText("Download in progress")
                        .setSmallIcon(R.drawable.download);

                int id = 1;

                for (i = 0; i < _array.length(); i++) {
                    JSONObject _rec = _array.getJSONObject(i);
                    // escape single quotes
//                    String _person_id = _rec.getString("person_id");
//                    _person_id = _person_id.replace("'", "''");
                    String name = _rec.getString("name");
                    name = name.replace("'","''");
                    String timestamp = _rec.getString("timestamp");
                    timestamp = timestamp.replace("'","''");
                    String location_id = _rec.getString("location_id");
                    location_id = location_id.replace("'", "''");
                    String activity_date = _rec.getString("activity_date");
                    activity_date = activity_date.replace("'","''");
                    String group_type_id = _rec.getString("group_type_id");
                    group_type_id = group_type_id.replace("'","''");
                    String institution_id = _rec.getString("institution_id");
                    institution_id = institution_id.replace("'","''");
                    String males = _rec.getString("males");
                    males = males.replace("'","''");
                    String females = _rec.getString("females");
                    females = females.replace("'","''");
                    String messages = _rec.getString("messages");
                    messages = messages.replace("'", "''");
                    String latitude = _rec.getString("latitude");
                    latitude = latitude.replace("'", "''");
                    String longitude = _rec.getString("longitude");
                    longitude = longitude.replace("'", "''");


                    Log.d(LOG, "getMySQLGroupActivityTable:doInBackground: " + timestamp + ", " + name + ", " + activity_date );
                    String _insert =
                            "insert or replace into group_activity "
                                    + "(timestamp, name, location_id, activity_date, group_type_id, institution_id, males, females, messages, latitude, longitude) "
                                    + " values("
                                    // + person_id + ","
//                                    + "'" + _person_id + "'" + ","
                                    + "'" + timestamp + "'" + ","
                                    + "'" + name + "'" + ","
                                    + "'" + location_id + "'" + ","
                                    + "'" + activity_date + "'" + ","
                                    + "'" + group_type_id + "'" + ","
                                    + "'" + institution_id + "'" + ","
                                    + "'" + males + "'" + ","
                                    + "'" + females + "'" + ","
                                    + "'" + messages + "'" + ","
                                    + "'" + latitude + "'" + ","
                                    + "'" + longitude + "'" +
                                    ");";

                    try {
                        int incr = (int) ((i / (float) num_recs) * 100);
                        mBuilder.setProgress(100, incr, false);
                        mNotifyManager.notify(id, mBuilder.build());

                        Log.d(LOG, "getMySQLGroupActivityTable _insert " + _insert.toString() + " " + i);
                        //Log.d(LOG, "getMySQLGroupActivityTable personInsert " + " " + i);

                        _db.execSQL(_insert.toString());

                    } catch (Exception ex) {
                        Log.d(LOG, "getMySQLGroupActivityTable loop exception > " + ex.toString());
                    }
                } // foreach
                mBuilder.setContentText(this._context.getResources().getString(R.string.sync_complete) + " (" + i + " records)").setProgress(0, 0, false);
                mNotifyManager.notify(id, mBuilder.build());
            } else {
                Log.d(LOG, "Login Failed");
                Toast.makeText(this._context, "Login Failed", Toast.LENGTH_LONG).show();
                MainActivity._pass = "";
                LOGGED_IN = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(LOG, "getMySQLGroupActivityTable exception > " + e.toString());
        }
        Log.d(LOG, "getMySQLGroupActivityTable.doInBackground end");
        return Integer.toString(i);
    }

    protected void onPostExecute(String result) {
        Log.d(LOG, "getMySQLGroupActivityTable:onPostExecute: " + result);
        //Toast.makeText(this._context, "Downloaded " + result + " persons", Toast.LENGTH_LONG).show();
        //Toast.makeText(this._context, this._context.getResources().getString(R.string.sync_complete), Toast.LENGTH_LONG).show();
    }
}