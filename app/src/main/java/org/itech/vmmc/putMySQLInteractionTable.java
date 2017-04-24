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
class putMySQLInteractionTable extends AsyncTask<String, String, String> {

    private boolean LOGGED_IN = false;
    public SQLiteDatabase _db;
    DBHelper dbhelp;

    putMySQLInteractionTable(DBHelper dbhelp){
        this.dbhelp = dbhelp;
        this._db = dbhelp.getReadableDatabase();
    }

    @Override
    protected String doInBackground(String... args) {
        Log.d(LOG, "putMySQLInteractionTable doInBackground ");

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
        String datatable = "putInteraction";
        try {
            URL url = null;
            try {
                url = new URL(MainActivity.GET_TABLE_URL);
                Log.d(LOG, "putMySQLInteractionTable GET_TABLE_URL " + url.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            data += "&" + URLEncoder.encode("datatable", "UTF-8") + "=" + URLEncoder.encode(datatable, "UTF-8");

            List<Interaction> interactionList = dbhelp.getAllInteractions();
            Log.d(LOG, "putMySQLInteractionTable build rec: " + interactionList.size() );
            data += "&" + URLEncoder.encode("num_recs", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(interactionList.size()), "UTF-8");
            int i = 0;
            String[] recs = new String[interactionList.size()];
            for (Interaction interaction: interactionList) {
                recs[i] =
                                interaction.get_timestamp() + "," +
                                interaction.get_fac_first_name() + "," +
                                interaction.get_fac_last_name() + "," +
                                interaction.get_fac_national_id() + "," +
                                interaction.get_fac_phone() + "," +
                                interaction.get_person_first_name() + "," +
                                interaction.get_person_last_name() + "," +
                                interaction.get_person_national_id() + "," +
                                interaction.get_person_phone() + "," +
                                interaction.get_interaction_date() + "," +
                                interaction.get_followup_date() + "," +
                                interaction.get_type_id() + "," +
                                interaction.get_note();

                data += "&" + URLEncoder.encode("recs"+Integer.toString(i), "UTF-8") + "=" + URLEncoder.encode(recs[i], "UTF-8");
                i++;
            }

            JSONParser jsonParser = new JSONParser();
            String reply = jsonParser.makeHttpRequest(url, "POST", data);
            JSONObject json = new JSONObject(reply);
            success = json.getInt(MainActivity.TAG_SUCCESS);
            if (success == 1) {
                LOGGED_IN = true;
                //int num_Interaction_recs = json.getInt("number_records");
                Log.d(LOG, "putMySQLInteractionTable Success: ");
            } else {
                Log.d(LOG, "putMySQLInteractionTable: Not Successful");
                MainActivity._pass = "";
                LOGGED_IN = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(LOG, "putMySQLInteractionTable exception > " + e.toString());
        }
        Log.d(LOG, "putMySQLInteractionTable.doInBackground end");
        return null;
    }
}


