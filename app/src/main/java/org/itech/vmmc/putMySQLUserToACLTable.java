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
class putMySQLUserToAclTable extends AsyncTask<String, String, String> {

    private boolean LOGGED_IN = false;
    public SQLiteDatabase _db;
    DBHelper dbhelp;

    putMySQLUserToAclTable(DBHelper dbhelp){
        this.dbhelp = dbhelp;
        this._db = dbhelp.getReadableDatabase();
    }

    @Override
    protected String doInBackground(String... args) {
        Log.d(LOG, "putMySQLUserToAclTable doInBackground ");

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
        String datatable = "putUserToAcl";
        try {
            URL url = null;
            try {
                url = new URL(MainActivity.GET_TABLE_URL);
                Log.d(LOG, "putMySQLUserToAclTable GET_TABLE_URL " + url.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            data += "&" + URLEncoder.encode("datatable", "UTF-8") + "=" + URLEncoder.encode(datatable, "UTF-8");

            List<UserToAcl> userToAclList = dbhelp.getAllUserToAcls();
            Log.d(LOG, "putMySQLUserToAclTable build rec: " + userToAclList.size() );
            data += "&" + URLEncoder.encode("num_recs", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userToAclList.size()), "UTF-8");
            int i = 0;
            String[] recs = new String[userToAclList.size()];
            for (UserToAcl userToAcl: userToAclList) {
                Log.d(LOG, "putMySQLUserToAclTable loop: " + userToAcl.get_user_id() );
                recs[i] =
                        userToAcl.get_timestamp_created() + "," +
                                userToAcl.get_acl_id() + "," +
                                userToAcl.get_user_id() + "," +
                                userToAcl.get_created_by();

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
                Log.d(LOG, "putMySQLUserToAclTable Success: ");
            } else {
                Log.d(LOG, "putMySQLUserToAclTable: Not Successful");
                MainActivity._pass = "";
                LOGGED_IN = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(LOG, "putMySQLUserToAclTable exception > " + e.toString());
        }
        Log.d(LOG, "putMySQLUserToAclTable.doInBackground end");
        return null;
    }
}


