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
class getMySQLFacilitatorTable extends AsyncTask<String, String, String> {

    private boolean LOGGED_IN = false;
    public Context _context;
    public SQLiteDatabase _db;
    DBHelper dbhelp;

    getMySQLFacilitatorTable(Context context, DBHelper dbhelp) {
        this._context = context;
        this._db = dbhelp.getWritableDatabase();
//        this._db.execSQL("delete from facilitator_table");
    }

    @Override
    protected String doInBackground(String... args) {
        Log.d(LOG, "getMySQLFacilitatorTable doInBackground ");

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
        Log.d(LOG, "getMySQLFacilitatorTable username/password: " + username + " " + password);
        String datatable = "getFacilitator";
        try {
            URL url = null;
            try {
                url = new URL(MainActivity.GET_TABLE_URL);
                Log.d(LOG, "getMySQLFacilitatorTable GET_TABLE_URL " + url.toString());
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

                NotificationManager mNotifyManager = (NotificationManager) this._context.getSystemService(this._context.NOTIFICATION_SERVICE);
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
                    String timestamp = _rec.getString("timestamp");
                    timestamp = timestamp.replace("'","''");
                    String first_name = _rec.getString("first_name");
                    first_name = first_name.replace("'","''");
                    String last_name = _rec.getString("last_name");
                    last_name = last_name.replace("'","''");
                    String national_id = _rec.getString("national_id");
                    national_id = national_id.replace("'","''");
                    String phone = _rec.getString("phone");
                    phone = phone.replace("'","''");
                    String facilitator_type_id = _rec.getString("facilitator_type_id");
                    facilitator_type_id = facilitator_type_id.replace("'", "''");
                    String note = _rec.getString("note");
                    note = note.replace("'","''");
                    String location_id = _rec.getString("location_id");
                    location_id = location_id.replace("'", "''");
                    String latitude = _rec.getString("latitude");
                    latitude = latitude.replace("'", "''");
                    String longitude = _rec.getString("longitude");
                    longitude = longitude.replace("'", "''");
                    String institution_id = _rec.getString("institution_id");
                    institution_id = institution_id.replace("'", "''");

                    Log.d(LOG, "getMySQLFacilitatorTable:doInBackground: " + timestamp + ", " + first_name + ", " + last_name + ", " + national_id + ", " + phone + ", " + note );
                    String _insert =
                            "insert or replace into facilitator "
                                    + "(timestamp, first_name, last_name, national_id, phone, facilitator_type_id, note, location_id, latitude, longitude, institution_id) "
                                    + " values("
                                    // + person_id + ","
//                                    + "'" + _person_id + "'" + ","
                                    + "'" + timestamp + "'" + ","
                                    + "'" + first_name + "'" + ","
                                    + "'" + last_name + "'" + ","
                                    + "'" + national_id + "'" + ","
                                    + "'" + phone + "'" + ","
                                    + "'" + facilitator_type_id + "'" + ","
                                    + "'" + note + "'" + ","
                                    + "'" + location_id + "'" + ","
                                    + "'" + latitude + "'" + ","
                                    + "'" + longitude + "'" + ","
                                    + "'" + institution_id + "'" +
                                    ");";

                    try {
                        int incr = (int) ((i / (float) num_recs) * 100);
                        mBuilder.setProgress(100, incr, false);
                        mNotifyManager.notify(id, mBuilder.build());

                        Log.d(LOG, "getMySQLFacilitatorTable _insert " + _insert.toString() + " " + i);
                        //Log.d(LOG, "getMySQLFacilitatorTable personInsert " + " " + i);

                        _db.execSQL(_insert.toString());

                    } catch (Exception ex) {
                        Log.d(LOG, "getMySQLFacilitatorTable loop exception > " + ex.toString());
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
            Log.d(LOG, "getMySQLFacilitatorTable exception > " + e.toString());
        }
        Log.d(LOG, "getMySQLFacilitatorTable.doInBackground end");
        return Integer.toString(i);
    }

    protected void onPostExecute(String result) {
        Log.d(LOG, "getMySQLFacilitatorTable:onPostExecute: " + result);
        //Toast.makeText(this._context, "Downloaded " + result + " persons", Toast.LENGTH_LONG).show();
        //Toast.makeText(this._context, this._context.getResources().getString(R.string.sync_complete), Toast.LENGTH_LONG).show();
    }
}