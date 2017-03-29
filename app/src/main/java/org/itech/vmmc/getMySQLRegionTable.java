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
class getMySQLRegionTable extends AsyncTask<String, String, String> {

    private boolean LOGGED_IN = false;
    public Context _context;
    public SQLiteDatabase _db;
    DBHelper dbhelp;

    getMySQLRegionTable(Context context, DBHelper dbhelp) {
        this._context = context;
        this._db = dbhelp.getWritableDatabase();
        this._db.execSQL("delete from region");
    }

        @Override
        protected String doInBackground(String... args) {
        Log.d(LOG, "getMySQLRegionTable doInBackground ");

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
        Log.d(LOG, "getMySQLRegionTable username/password: " + username + " " + password);
        String datatable = "getRegion";
        try {
            URL url = null;
            try {
                url = new URL(MainActivity.GET_TABLE_URL);
                Log.d(LOG, "getMySQLRegionTable GET_TABLE_URL " + url.toString());
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
                    int region_id = _rec.getInt("id");
                    // escape single quotes
                    String _name = _rec.getString("name");
                    _name = _name.replace("'", "''");

                    Log.d(LOG, "getMySQLRegionTable:doInBackground: " + _name );
                    String _insert =
                            "insert into region "
                                    + "(name) "
                                    + " values("
                                    // + person_id + ","
                                    + "'" + _name + "'" + ");";

                    try {
                        int incr = (int) ((i / (float) num_recs) * 100);
                        mBuilder.setProgress(100, incr, false);
                        mNotifyManager.notify(id, mBuilder.build());

                        //Log.d(LOG, "getMySQLRegionTable personInsert " + personInsert.toString() + " " + i);
                        //Log.d(LOG, "getMySQLRegionTable personInsert " + " " + i);

                        _db.execSQL(_insert.toString());

                    } catch (Exception ex) {
                        Log.d(LOG, "getMySQLRegionTable loop exception > " + ex.toString());
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
            Log.d(LOG, "getMySQLRegionTable exception > " + e.toString());
        }
        Log.d(LOG, "getMySQLRegionTable.doInBackground end");
        return Integer.toString(i);
    }

    protected void onPostExecute(String result) {
        Log.d(LOG, "getMySQLRegionTable:onPostExecute: " + result);
        //Toast.makeText(this._context, "Downloaded " + result + " persons", Toast.LENGTH_LONG).show();
        //Toast.makeText(this._context, this._context.getResources().getString(R.string.sync_complete), Toast.LENGTH_LONG).show();
    }
}