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
class putMySQLUserTable extends AsyncTask<String, String, String> {

    private boolean LOGGED_IN = false;
    public SQLiteDatabase _db;
    DBHelper dbhelp;

    putMySQLUserTable(DBHelper dbhelp){
        this.dbhelp = dbhelp;
        this._db = dbhelp.getReadableDatabase();
    }

    @Override
    protected String doInBackground(String... args) {
        Log.d(LOG, "putMySQLUserTable doInBackground ");

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
        String datatable = "putUser";
        try {
            URL url = null;
            try {
                url = new URL(MainActivity.GET_TABLE_URL);
                Log.d(LOG, "putMySQLUserTable GET_TABLE_URL " + url.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            data += "&" + URLEncoder.encode("datatable", "UTF-8") + "=" + URLEncoder.encode(datatable, "UTF-8");

            List<User> userList = dbhelp.getAllUsers();
            Log.d(LOG, "putMySQLUserTable build rec: " + userList.size() );
            data += "&" + URLEncoder.encode("num_recs", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userList.size()), "UTF-8");
            int i = 0;
            String[] recs = new String[userList.size()];
            for (User user: userList) {
                Log.d(LOG, "putMySQLUserTable loop: " + user.get_username()  );
                recs[i] =
                        user.get_timestamp() + "," +
                        user.get_username() + "," +
                                user.get_password() + "," +
                                user.get_email() + "," +
                                user.get_first_name() + "," +
                                user.get_last_name() + "," +
                                user.get_national_id() + "," +
                                user.get_phone() + "," +
                                user.get_region_id() + "," +
                                user.get_user_type_id() + "," +
                                user.get_location_id() + "," +
                                user.get_modified_by() + "," +
                                user.get_created_by() + "," +
                                user.get_is_blocked() + "," +
                                user.get_timestamp_updated() + "," +
                                user.get_timestamp_created() + "," +
                                user.get_timestamp_last_login();

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
                Log.d(LOG, "putMySQLUerTable Success: ");
            } else {
                Log.d(LOG, "putMySQLUserTable: Not Successful");
                MainActivity._pass = "";
                LOGGED_IN = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(LOG, "putMySQLUserTable exception > " + e.toString());
        }
        Log.d(LOG, "putMySQLUserTable.doInBackground end");
        return null;
    }
}


