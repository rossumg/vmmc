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
class getMySQLPersonTable extends AsyncTask<String, String, String> {

    private boolean LOGGED_IN = false;
    public Context _context;
    public SQLiteDatabase _db;
    DBHelper dbhelp;
    int i = 0;
    SyncAudit syncAudit = new SyncAudit();

    getMySQLPersonTable(Context context, DBHelper dbhelp){
        this.dbhelp = dbhelp;
        this._context = context;
        this._db = dbhelp.getWritableDatabase();
//        this._db.execSQL("delete from person");
    }

    @Override
    protected String doInBackground(String... args) {
        Log.d(LOG, "getMySQLPersonTable doInBackground ");

        try {
            //Thread.sleep(4000); // 4 secs
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // TODO Auto-generated method stub
        // Check for success tag
        int success;
        i = 0;

        String username = MainActivity._user;
        String password = MainActivity._pass;
        Log.d(LOG, "getMySQLPersonTable username/password: " + username + " " + password);
        String datatable = "getPerson";
        try {
            URL url = null;
            try {
                url = new URL(MainActivity.GET_TABLE_URL);
                Log.d(LOG, "getMySQLPersonTable GET_TABLE_URL " + url.toString());
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
            int num_person_recs;
            if (success == 1) {
                LOGGED_IN = true;
                num_person_recs = json.getInt("number_records");
                JSONArray person_array = json.getJSONArray("posts");

                NotificationManager mNotifyManager = (NotificationManager) this._context.getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this._context);

                mBuilder.setContentTitle("Data Download")
                        .setContentText("Download in progress")
                        .setSmallIcon(R.drawable.download);

                int id = 1;

                for (i = 0; i< person_array.length(); i++) {
                    JSONObject person_rec = person_array.getJSONObject(i);
                    int person_id = person_rec.getInt("id");
                    // escape single quotes
                    String timestamp = person_rec.getString("timestamp");
                    timestamp = timestamp.replace("'","''");
                    String first_name = person_rec.getString("first_name");
                    first_name = first_name.replace("'","''");
                    String last_name = person_rec.getString("last_name");
                    last_name = last_name.replace("'","''");
                    String national_id = person_rec.getString("national_id");
                    String address_id = person_rec.getString("address_id");
                    address_id = address_id.replace("'","''");
                    String phone = person_rec.getString("phone");
                    phone = phone.replace("'","''");
                    String dob = person_rec.getString("dob");
                    dob = dob.replace("'","''");
                    String gender = person_rec.getString("gender");
                    gender = gender.replace("'","''");
                    String latitude = person_rec.getString("latitude");
                    String longitude = person_rec.getString("longitude");
                    String is_deleted = person_rec.getString("is_deleted");
                    Log.d(LOG, "getMYSQLPersonTable:doInBackground: " + first_name + ":" + last_name + ":" + phone);
                    String personInsert =
                            "insert or replace into person "
                                    + "(timestamp, first_name, last_name, national_id, address_id, phone, dob, gender, latitude, longitude, is_deleted) "
                                    + " values("
                                    // + person_id + ","
                                    + "'" + timestamp + "'" + ","
                                    + "'" + first_name + "'" + ","
                                    + "'" + last_name + "'" + ","
                                    + "'" + national_id + "'" + ","
                                    + "'" + address_id + "'" + ","
                                    + "'" + phone + "'" + ","
                                    + "'" + dob + "'" + ","
                                    + "'" + gender + "'" + ","
                                    + latitude + ","
                                    + longitude + ","
                                    + "'" + is_deleted + "'" + ");";
                    try {
                        // no longer used
                        //int progress = (int)((i / (float) num_person_recs) * 100);
                        //publishProgress(Integer.toString(progress));

                        int incr = (int)((i / (float) num_person_recs) * 100);
                        mBuilder.setProgress(100, incr, false);
                        mNotifyManager.notify(id, mBuilder.build());

                        //Log.d(LOG, "getMySQLPersonTable personInsert " + personInsert.toString() + " " + i);
                        //Log.d(LOG, "getMySQLPersonTable personInsert " + " " + i);

                       _db.execSQL(personInsert.toString());

                    } catch (Exception ex) {
                        Log.d(LOG, "getMySQLPersonTable loop exception > " + ex.toString());
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
            Log.d(LOG, "getMySQLPersonTable exception > " + e.toString());
            syncAudit.set_status("getMySQLPersonTable exception:" + e.toString());
        }
        Log.d(LOG, "getMySQLPersonTable.doInBackground end");
        return Integer.toString(i);
    }

    protected void onPostExecute(String result) {
        Log.d(LOG, "getMySQLPersonTable:onPostExecute: " + result);
        syncAudit.set_progress("getMySQLPersonTable:" + result);
        dbhelp.addSyncAudit(syncAudit);
        //Toast.makeText(this._context, "Downloaded " + result + " persons", Toast.LENGTH_LONG).show();
        //Toast.makeText(this._context, this._context.getResources().getString(R.string.sync_complete), Toast.LENGTH_LONG).show();
    }
}


