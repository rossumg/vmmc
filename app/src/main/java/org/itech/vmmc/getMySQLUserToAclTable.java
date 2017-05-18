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
 * Created by rossumg on 5/4/2017.
 */
class getMySQLUserToAclTable extends AsyncTask<String, String, String> {

    private boolean LOGGED_IN = false;
    public Context _context;
    public SQLiteDatabase _db;
    DBHelper dbhelp;

    getMySQLUserToAclTable(Context context, DBHelper dbhelp) {
        this._context = context;
        this._db = dbhelp.getWritableDatabase();
        this._db.execSQL("delete from user_to_acl");
    }

        @Override
        protected String doInBackground(String... args) {
        Log.d(LOG, "getMySQLUserToAclTable doInBackground ");

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
        Log.d(LOG, "getMySQLUserToAclTable username/password: " + username + " " + password);
        String datatable = "getUserToAcl";
        try {
            URL url = null;
            try {
                url = new URL(MainActivity.GET_TABLE_URL);
                Log.d(LOG, "getMySQLUserToAclTable GET_TABLE_URL " + url.toString());
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
                    int user_to_acl_id = _rec.getInt("id");
                    // escape single quotes
                    String timestamp_created = _rec.getString("timestamp_created");
                    timestamp_created = timestamp_created.replace("'","''");
                    String acl_id = _rec.getString("acl_id");
                    acl_id = acl_id.replace("'","''");
                    String user_id = _rec.getString("user_id");
                    user_id = user_id.replace("'","''");
                    String created_by = _rec.getString("created_by");
                    created_by = created_by.replace("'","''");

                    Log.d(LOG, "getMySQLUserToAclTable:doInBackground: " + user_id + " " + acl_id );
                    String _insert =
                            "insert or replace into user_to_acl "
                                    + "(timestamp_created, acl_id, user_id, created_by) "
                                    + " values("
                                    + "'" + timestamp_created + "'" + ","
                                    + "'" + acl_id + "'" + ","
                                    + "'" + user_id + "'" + ","
                                    + "'" + created_by + "'" + ");";

                    try {
                        int incr = (int) ((i / (float) num_recs) * 100);
                        mBuilder.setProgress(100, incr, false);
                        mNotifyManager.notify(id, mBuilder.build());

                        //Log.d(LOG, "getMySQLUserToAclTable personInsert " + personInsert.toString() + " " + i);
                        //Log.d(LOG, "getMySQLUserToAclTable personInsert " + " " + i);

                        _db.execSQL(_insert.toString());

                    } catch (Exception ex) {
                        Log.d(LOG, "getMySQLUserToAclTable loop exception > " + ex.toString());
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
            Log.d(LOG, "getMySQLUserToAclTable exception > " + e.toString());
        }
        Log.d(LOG, "getMySQLUserToAclTable.doInBackground end");
        return Integer.toString(i);
    }

    protected void onPostExecute(String result) {
        Log.d(LOG, "getMySQLUserToAclTable:onPostExecute: " + result);
        //Toast.makeText(this._context, "Downloaded " + result + " persons", Toast.LENGTH_LONG).show();
        //Toast.makeText(this._context, this._context.getResources().getString(R.string.sync_complete), Toast.LENGTH_LONG).show();
    }
}