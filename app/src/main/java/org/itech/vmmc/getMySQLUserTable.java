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
class getMySQLUserTable extends AsyncTask<String, String, String> {

    private boolean LOGGED_IN = false;
    public Context _context;
    public SQLiteDatabase _db;
    DBHelper dbhelp;

    getMySQLUserTable(Context context, DBHelper dbhelp) {
        this._context = context;
        this._db = dbhelp.getWritableDatabase();
//        this._db.execSQL("delete from user");
    }

        @Override
        protected String doInBackground(String... args) {
        Log.d(LOG, "getMySQLUserTable doInBackground ");

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
        Log.d(LOG, "getMySQLUserTable username/password: " + username + " " + password);
        String datatable = "getUser";
        try {
            URL url = null;
            try {
                url = new URL(MainActivity.GET_TABLE_URL);
                Log.d(LOG, "getMySQLUserTable GET_TABLE_URL " + url.toString());
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
                    int user_id = _rec.getInt("id");
                    // escape single quotes
                    String _timestamp = _rec.getString("timestamp");
                    _timestamp = _timestamp.replace("'","''");
                    String _username = _rec.getString("username");
                    _username = _username.replace("'","''");
                    String _password = _rec.getString("password");
                    _password = _password.replace("'","''");
                    String email = _rec.getString("email");
                    email = email.replace("'","''");
                    String first_name = _rec.getString("first_name");
                    first_name = first_name.replace("'","''");
                    String last_name = _rec.getString("last_name");
                    last_name = last_name.replace("'","''");
                    String national_id = _rec.getString("national_id");
                    String phone = _rec.getString("phone");
                    phone = phone.replace("'","''");

                    String region_id = _rec.getString("region_id");
                    region_id = region_id.replace("'","''");
                    String user_type_id = _rec.getString("user_type_id");
                    user_type_id = user_type_id.replace("'","''");
                    String location_id = _rec.getString("location_id");
                    location_id = location_id.replace("'","''");
                    String modified_by = _rec.getString("modified_by");
                    modified_by = modified_by.replace("'","''");
                    String created_by = _rec.getString("created_by");
                    created_by = created_by.replace("'","''");
                    String is_blocked = _rec.getString("is_blocked");
                    is_blocked = is_blocked.replace("'","''");

                    String timestamp_updated = _rec.getString("timestamp_updated");
                    timestamp_updated = timestamp_updated.replace("'","''");
                    String timestamp_created = _rec.getString("timestamp_created");
                    timestamp_created = timestamp_created.replace("'","''");
                    String timestamp_last_login = _rec.getString("timestamp_last_login");
                    timestamp_last_login = timestamp_last_login.replace("'","''");

                    Log.d(LOG, "getMySQLUserTable:doInBackground: " + _username );
                    String _insert =
                            "insert or replace into user "
                                    + "(id, timestamp, username, password, email, first_name, last_name, national_id, phone, region_id, user_type_id, location_id, modified_by, created_by, is_blocked, timestamp_updated, timestamp_created, timestamp_last_login) "
                                    + " values("
                                    + "'" + user_id + "'" + ","
                                    + "'" + _timestamp + "'" + ","
                                    + "'" + _username + "'" + ","
                                    + "'" + _password + "'" + ","
                                    + "'" + email + "'" + ","
                                    + "'" + first_name + "'" + ","
                                    + "'" + last_name + "'" + ","
                                    + "'" + national_id + "'" + ","
                                    + "'" + phone + "'" + ","
                                    + "'" + region_id + "'" + ","
                                    + "'" + user_type_id + "'" + ","
                                    + "'" + location_id + "'" + ","
                                    + "'" + modified_by + "'" + ","
                                    + "'" + created_by + "'" + ","
                                    + "'" + is_blocked + "'" + ","
                                    + "'" + timestamp_updated + "'" + ","
                                    + "'" + timestamp_created + "'" + ","
                                    + "'" + timestamp_last_login + "'" + ");";

                    try {
                        int incr = (int) ((i / (float) num_recs) * 100);
                        mBuilder.setProgress(100, incr, false);
                        mNotifyManager.notify(id, mBuilder.build());

                        //Log.d(LOG, "getMySQLUserTable personInsert " + personInsert.toString() + " " + i);
                        //Log.d(LOG, "getMySQLUserTable personInsert " + " " + i);

                        _db.execSQL(_insert.toString());

                    } catch (Exception ex) {
                        Log.d(LOG, "getMySQLUserTable loop exception > " + ex.toString());
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
            Log.d(LOG, "getMySQLUserTable exception > " + e.toString());
        }
        Log.d(LOG, "getMySQLUserTable.doInBackground end");
        return Integer.toString(i);
    }

    protected void onPostExecute(String result) {
        Log.d(LOG, "getMySQLUserTable:onPostExecute: " + result);
        //Toast.makeText(this._context, "Downloaded " + result + " persons", Toast.LENGTH_LONG).show();
        //Toast.makeText(this._context, this._context.getResources().getString(R.string.sync_complete), Toast.LENGTH_LONG).show();
    }
}