package org.itech.vmmc;

import android.app.NotificationManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

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

    private static String TASK_NAME = "getMySQLRegionTable";
    private boolean LOGGED_IN = false;
    public Context _context;
    public SQLiteDatabase _db;
    DBHelper dbhelp;
    int i = 0;
    int success = 0;
    int retries = 0;
    SyncAudit syncAudit = new SyncAudit();

    getMySQLRegionTable(Context context, DBHelper dbhelp) {
        this.dbhelp = dbhelp;
        this._context = context;
        this._db = dbhelp.getWritableDatabase();
        this._db.execSQL("delete from region");
    }

    @Override
    protected String doInBackground(String... args) {
        Log.d(LOG, TASK_NAME + ": doInBackground ");

        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        success = 0;
        i = 0;

        String username = MainActivity._user;
        String password = MainActivity._pass;
        Log.d(LOG, TASK_NAME + ": username/password: " + username + " " + password);
        String datatable = "getRegion";

        while (success == 0 && retries < MainActivity.MAX_RETRIES) {
            syncAudit.set_status("null");
            retries++;
            try {
                URL url = null;
                try {
                    url = new URL(MainActivity.GET_TABLE_URL);
                    Log.d(LOG, TASK_NAME + "GET_TABLE_URL " + url.toString());
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
                        int region_id = _rec.getInt("id");
                        // escape single quotes
                        String _name = _rec.getString("name");
                        _name = _name.replace("'", "''");

                        String _insert =
                                "insert into region "
                                        + "(name) "
                                        + " values("
                                        + "'" + _name + "'" + ");";

                        try {
                            int incr = (int) ((i / (float) num_recs) * 100);
                            mBuilder.setProgress(100, incr, false);
                            mNotifyManager.notify(id, mBuilder.build());

                            _db.execSQL(_insert.toString());

                        } catch (Exception ex) {
                            success = 0;
                            Log.d(LOG, TASK_NAME + " loop exception > " + ex.toString());
                            syncAudit.set_status(TASK_NAME + ": exception: " + ex.toString());
                            dbhelp.addSyncAudit(syncAudit);
                        }
                    } // foreach
                    mBuilder.setContentText(this._context.getResources().getString(R.string.sync_complete) + " (" + i + " records)").setProgress(0, 0, false);
                    mNotifyManager.notify(id, mBuilder.build());
                } else {
                    Log.d(LOG, "Login Failed");
                    success = 0;
                    syncAudit.set_status(TASK_NAME + ": Login Failed");
                    dbhelp.addSyncAudit(syncAudit);
                    MainActivity._pass = "";
                    LOGGED_IN = false;

                }
            } catch (Exception e) {
                success = 0;
                e.printStackTrace();
                Log.d(LOG, TASK_NAME + ": exception > " + e.toString());
                if (e instanceof java.lang.RuntimeException)
                    syncAudit.set_status(TASK_NAME + ": exception R:" + e.toString());
                else if (e instanceof org.json.JSONException)
                    syncAudit.set_status(TASK_NAME + ": exception J:" + e.toString());
                else
                    syncAudit.set_status(TASK_NAME + ": exception D:" + e.toString());
                dbhelp.addSyncAudit(syncAudit);
            }

        } // retry loop
        Log.d(LOG, TASK_NAME + ":doInBackground end");
        return Integer.toString(i);
    }

    protected void onPostExecute(String result) {
        Log.d(LOG, TASK_NAME + ":onPostExecute: " + result);
        syncAudit.set_progress(TASK_NAME + ":" + result);
        dbhelp.addSyncAudit(syncAudit);
    }
}
