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
class putMySQLGroupActivityTable extends AsyncTask<String, String, String> {

    private boolean LOGGED_IN = false;
    public SQLiteDatabase _db;
    DBHelper dbhelp;

    putMySQLGroupActivityTable(DBHelper dbhelp){
        this.dbhelp = dbhelp;
        this._db = dbhelp.getReadableDatabase();
    }

    @Override
    protected String doInBackground(String... args) {
        Log.d(LOG, "putMySQLGroupActivityTable doInBackground ");

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
        String datatable = "putGroupActivity";
        try {
            URL url = null;
            try {
                url = new URL(MainActivity.GET_TABLE_URL);
                Log.d(LOG, "putMySQLGroupActivityTable GET_TABLE_URL " + url.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            data += "&" + URLEncoder.encode("datatable", "UTF-8") + "=" + URLEncoder.encode(datatable, "UTF-8");

            List<GroupActivity> groupActiviyList = dbhelp.getAllGroupActivities();
            Log.d(LOG, "putMySQLGroupActivityTable build rec: " + groupActiviyList.size() );
            data += "&" + URLEncoder.encode("num_recs", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(groupActiviyList.size()), "UTF-8");
            int i = 0;
            String[] recs = new String[groupActiviyList.size()];
            for (GroupActivity groupActivity: groupActiviyList) {
                Log.d(LOG, "putMySQLGroupActivityTable loop: " + groupActivity.get_males() + groupActivity.get_females() + groupActivity.get_messages() );
                recs[i] =
                                groupActivity.get_name() + "," +
                                groupActivity.get_timestamp() + "," +
                                groupActivity.get_location_id() + "," +
                                groupActivity.get_activity_date() + "," +
                                groupActivity.get_group_type_id() + "," +
                                groupActivity.get_males() + "," +
                                groupActivity.get_females() + "," +
                                groupActivity.get_messages() + "," +
                                groupActivity.get_latitude() + "," +
                                groupActivity.get_longitude();

                data += "&" + URLEncoder.encode("recs"+Integer.toString(i), "UTF-8") + "=" + URLEncoder.encode(recs[i], "UTF-8");
                i++;
            }

            JSONParser jsonParser = new JSONParser();
            String reply = jsonParser.makeHttpRequest(url, "POST", data);
            JSONObject json = new JSONObject(reply);
            success = json.getInt(MainActivity.TAG_SUCCESS);
            if (success == 1) {
                LOGGED_IN = true;
                //int num_GroupActiviy_recs = json.getInt("number_records");
                Log.d(LOG, "putMySQLGroupActiviyTable Success: ");
            } else {
                Log.d(LOG, "putMySQLGroupActiviyTable: Not Successful");
                MainActivity._pass = "";
                LOGGED_IN = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(LOG, "putMySQLGroupActiviyTable exception > " + e.toString());
        }
        Log.d(LOG, "putMySQLGroupActiviyTable.doInBackground end");
        return null;
    }
}


