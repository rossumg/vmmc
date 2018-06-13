package org.itech.vmmc;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import static org.itech.vmmc.MainActivity.LOG;

/**
 * Created by rossumg on 9/28/2015.
 */
class putMySQLSyncAuditTable extends AsyncTask<String, String, String> {

    private boolean LOGGED_IN = false;
    public SQLiteDatabase _db;
    DBHelper dbhelp;
    int i = 0;

    putMySQLSyncAuditTable(DBHelper dbhelp){
        this.dbhelp = dbhelp;
        this._db = dbhelp.getReadableDatabase();
    }

    @Override
    protected String doInBackground(String... args) {
        Log.d(LOG, "putMySQLSyncAuditTable doInBackground ");

        try {
            //Thread.sleep(4000); // 4 secs
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // TODO Auto-generated method stub
        // Check for success tag
        int success;
        String username = MainActivity._user;
        String password = MainActivity._pass;
        password = "rossumg";
        String datatable = "putSyncAudit";
        try {
            URL url = null;
            try {
                url = new URL(MainActivity.GET_TABLE_URL);
                Log.d(LOG, "putMySQLSyncAuditTable GET_TABLE_URL " + url.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            data += "&" + URLEncoder.encode("datatable", "UTF-8") + "=" + URLEncoder.encode(datatable, "UTF-8");

            List<SyncAudit> syncAuditList = dbhelp.getAllSyncAudits();
            Log.d(LOG, "putMySQLSyncAuditTable build rec: " + syncAuditList.size() );
            data += "&" + URLEncoder.encode("num_recs", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(syncAuditList.size()), "UTF-8");
            i = 0;
            String[] recs = new String[syncAuditList.size()];
            for (SyncAudit syncAudit: syncAuditList) {
                recs[i] =
                        syncAudit.get_timestamp() + "," +
                                syncAudit.get_latitude() + "," +
                                syncAudit.get_longitude() + "," +
                                syncAudit.get_device_id() + "," +
                                syncAudit.get_username() + "," +
                                syncAudit.get_progress() + "," +
                                syncAudit.get_status();

                data += "&" + URLEncoder.encode("recs"+Integer.toString(i), "UTF-8") + "=" + URLEncoder.encode(recs[i], "UTF-8");
                i++;
            }

            JSONParser jsonParser = new JSONParser();
            String reply = jsonParser.makeHttpRequest(url, "POST", data);
            JSONObject json = new JSONObject(reply);
            success = json.getInt(MainActivity.TAG_SUCCESS);
            if (success == 1) {
                LOGGED_IN = true;
                //int num_SyncAudit_recs = json.getInt("number_records");
                Log.d(LOG, "putMySQLSyncAuditTable Success: ");
            } else {
                Log.d(LOG, "putMySQLSyncAuditTable: Not Successful");
                MainActivity._pass = "";
                LOGGED_IN = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(LOG, "putMySQLSyncAuditTable exception > " + e.toString());
        }
        Log.d(LOG, "putMySQLSyncAuditTable.doInBackground end");
        return Integer.toString(i);
    }

    protected void onPostExecute(String result) {
        Log.d(LOG, "putMySQLSyncAuditTable:onPostExecute: " + result);

    }
}
