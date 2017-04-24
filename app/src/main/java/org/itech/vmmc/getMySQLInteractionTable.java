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
class getMySQLInteractionTable extends AsyncTask<String, String, String> {

    private boolean LOGGED_IN = false;
    public Context _context;
    public SQLiteDatabase _db;
    DBHelper dbhelp;

    getMySQLInteractionTable(Context context, DBHelper dbhelp) {
        this._context = context;
        this._db = dbhelp.getWritableDatabase();
//        this._db.execSQL("delete from interaction_table");
    }

    @Override
    protected String doInBackground(String... args) {
        Log.d(LOG, "getMySQLInteractionTable doInBackground ");

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
        Log.d(LOG, "getMySQLInteractionTable username/password: " + username + " " + password);
        String datatable = "getInteraction";
        try {
            URL url = null;
            try {
                url = new URL(MainActivity.GET_TABLE_URL);
                Log.d(LOG, "getMySQLInteractionTable GET_TABLE_URL " + url.toString());
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
                    String fac_first_name = _rec.getString("fac_first_name");
                    fac_first_name = fac_first_name.replace("'","''");
                    String fac_last_name = _rec.getString("fac_last_name");
                    fac_last_name = fac_last_name.replace("'","''");
                    String fac_national_id = _rec.getString("fac_national_id");
                    fac_national_id = fac_national_id.replace("'","''");
                    String fac_phone = _rec.getString("fac_phone");
                    fac_phone = fac_phone.replace("'","''");
                    String person_first_name = _rec.getString("person_first_name");
                    person_first_name = person_first_name.replace("'","''");
                    String person_last_name = _rec.getString("person_last_name");
                    person_last_name = person_last_name.replace("'","''");
                    String person_national_id = _rec.getString("person_national_id");
                    person_national_id = person_national_id.replace("'","''");
                    String person_phone = _rec.getString("person_phone");
                    person_phone = person_phone.replace("'","''");
                    String interaction_date = _rec.getString("interaction_date");
                    interaction_date = interaction_date.replace("'", "''");
                    String followup_date = _rec.getString("followup_date");
                    followup_date = followup_date.replace("'", "''");
                    String type_id = _rec.getString("type_id");
                    type_id = type_id.replace("'", "''");
                    String note = _rec.getString("note");
                    note = note.replace("'", "''");

//                    Log.d(LOG, "getMySQLInteractionTable:doInBackground: " + timestamp + ", " + first_name + ", " + last_name + ", " + national_id + ", " + phone + ", " + status_id );
                    String _insert =
                            "insert or replace into interaction "
                    + "(timestamp, fac_first_name, fac_last_name, fac_national_id, fac_phone, person_first_name, person_last_name, person_national_id, person_phone, interaction_date, followup_date, type_id, note) "
                                    + " values("
                                    // + person_id + ","
//                                    + "'" + _person_id + "'" + ","
                                    + "'" + timestamp + "'" + ","
                                    + "'" + fac_first_name + "'" + ","
                                    + "'" + fac_last_name + "'" + ","
                                    + "'" + fac_national_id + "'" + ","
                                    + "'" + fac_phone + "'" + ","
                                    + "'" + person_first_name + "'" + ","
                                    + "'" + person_last_name + "'" + ","
                                    + "'" + person_national_id + "'" + ","
                                    + "'" + person_phone + "'" + ","
                                    + "'" + interaction_date + "'" + ","
                                    + "'" + followup_date + "'" + ","
                                    + "'" + type_id + "'" + ","
                                    + "'" + note + "'" +
                                    ");";

                    try {
                        int incr = (int) ((i / (float) num_recs) * 100);
                        mBuilder.setProgress(100, incr, false);
                        mNotifyManager.notify(id, mBuilder.build());

                        Log.d(LOG, "getMySQLInteractionTable _insert " + _insert.toString() + " " + i);
                        //Log.d(LOG, "getMySQLInteractionTable personInsert " + " " + i);

                        _db.execSQL(_insert.toString());

                    } catch (Exception ex) {
                        Log.d(LOG, "getMySQLInteractionTable loop exception > " + ex.toString());
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
            Log.d(LOG, "getMySQLInteractionTable exception > " + e.toString());
        }
        Log.d(LOG, "getMySQLInteractionTable.doInBackground end");
        return Integer.toString(i);
    }

    protected void onPostExecute(String result) {
        Log.d(LOG, "getMySQLInteractionTable:onPostExecute: " + result);
        //Toast.makeText(this._context, "Downloaded " + result + " persons", Toast.LENGTH_LONG).show();
        //Toast.makeText(this._context, this._context.getResources().getString(R.string.sync_complete), Toast.LENGTH_LONG).show();
    }
}