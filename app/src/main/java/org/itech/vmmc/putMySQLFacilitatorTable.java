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
class putMySQLFacilitatorTable extends AsyncTask<String, String, String> {

    private boolean LOGGED_IN = false;
    public SQLiteDatabase _db;
    DBHelper dbhelp;

    putMySQLFacilitatorTable(DBHelper dbhelp){
        this.dbhelp = dbhelp;
        this._db = dbhelp.getReadableDatabase();
    }

    @Override
    protected String doInBackground(String... args) {
        Log.d(LOG, "putMySQLFacilitatorTable doInBackground ");

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
        String datatable = "putFacilitator";
        try {
            URL url = null;
            try {
                url = new URL(MainActivity.GET_TABLE_URL);
                Log.d(LOG, "putMySQLFacilitatorTable GET_TABLE_URL " + url.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            data += "&" + URLEncoder.encode("datatable", "UTF-8") + "=" + URLEncoder.encode(datatable, "UTF-8");

            List<Facilitator> facilitatorList = dbhelp.getAllFacilitators();
            Log.d(LOG, "putMySQLFacilitatorTable build rec: " + facilitatorList.size() );
            data += "&" + URLEncoder.encode("num_recs", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(facilitatorList.size()), "UTF-8");
            int i = 0;
            String[] recs = new String[facilitatorList.size()];
            for (Facilitator facilitator: facilitatorList) {
                recs[i] =
                                facilitator.get_timestamp() + "," +
                                facilitator.get_first_name() + "," +
                                facilitator.get_last_name() + "," +
                                facilitator.get_national_id() + "," +
                                facilitator.get_phone() + "," +
                                facilitator.get_facilitator_type_id() + "," +
                                facilitator.get_note() + "," +
                                facilitator.get_location_id() + "," +
                                facilitator.get_latitude() + "," +
                                facilitator.get_longitude()   + "," +
                                facilitator.get_institution_id() + "," +
                                facilitator.get_address_id() + "," +
                                facilitator.get_dob() + "," +
                                facilitator.get_gender();

                data += "&" + URLEncoder.encode("recs"+Integer.toString(i), "UTF-8") + "=" + URLEncoder.encode(recs[i], "UTF-8");
                i++;
            }

            JSONParser jsonParser = new JSONParser();
            String reply = jsonParser.makeHttpRequest(url, "POST", data);
            JSONObject json = new JSONObject(reply);
            success = json.getInt(MainActivity.TAG_SUCCESS);
            if (success == 1) {
                LOGGED_IN = true;
                //int num_Facilitator_recs = json.getInt("number_records");
                Log.d(LOG, "putMySQLFacilitatorTable Success: ");
            } else {
                Log.d(LOG, "putMySQLFacilitatorTable: Not Successful");
                MainActivity._pass = "";
                LOGGED_IN = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(LOG, "putMySQLFacilitatorTable exception > " + e.toString());
        }
        Log.d(LOG, "putMySQLFacilitatorTable.doInBackground end");
        return null;
    }
}


