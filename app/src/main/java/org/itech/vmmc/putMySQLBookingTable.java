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
class putMySQLBookingTable extends AsyncTask<String, String, String> {

    private boolean LOGGED_IN = false;
    public SQLiteDatabase _db;
    DBHelper dbhelp;

    putMySQLBookingTable(DBHelper dbhelp){
        this.dbhelp = dbhelp;
        this._db = dbhelp.getReadableDatabase();
    }

    @Override
    protected String doInBackground(String... args) {
        Log.d(LOG, "putMySQLBookingTable doInBackground ");

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
        String datatable = "putBooking";
        try {
            URL url = null;
            try {
                url = new URL(MainActivity.GET_TABLE_URL);
                Log.d(LOG, "putMySQLBookingTable GET_TABLE_URL " + url.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            data += "&" + URLEncoder.encode("datatable", "UTF-8") + "=" + URLEncoder.encode(datatable, "UTF-8");

            List<Booking> BookingList = dbhelp.getAllBookings();
            Log.d(LOG, "putMySQLBookingTable build rec: " + BookingList.size() );
            data += "&" + URLEncoder.encode("num_recs", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(BookingList.size()), "UTF-8");
            int i = 0;
            String[] recs = new String[BookingList.size()];
            for (Booking booking: BookingList) {
                recs[i] =
                        booking.get_timestamp() + "," +
                                booking.get_first_name() + "," +
                                booking.get_last_name() + "," +
                                booking.get_national_id() + "," +
                                booking.get_phone() + "," +
                                booking.get_fac_first_name() + "," +
                                booking.get_fac_last_name() + "," +
                                booking.get_fac_national_id() + "," +
                                booking.get_fac_phone() + "," +
                                booking.get_location_id() + "," +
                                booking.get_projected_date() + "," +
//                        Double.toString(Booking.get_latitude()) + "," +
//                        Double.toString(Booking.get_longitude()) + "," +
                                booking.get_actual_date() + "," +

                                booking.get_consent() + "," +
                                booking.get_procedure_type_id() + "," +
                                booking.get_followup_id() + "," +
                                booking.get_contact() + "," +
                                booking.get_alt_contact();

                        Log.d(LOG, "loop: " + recs[i] + "<");

                data += "&" + URLEncoder.encode("recs"+Integer.toString(i), "UTF-8") + "=" + URLEncoder.encode(recs[i], "UTF-8");
                i++;
            }

            JSONParser jsonParser = new JSONParser();
            String reply = jsonParser.makeHttpRequest(url, "POST", data);
            JSONObject json = new JSONObject(reply);
            success = json.getInt(MainActivity.TAG_SUCCESS);
            if (success == 1) {
                LOGGED_IN = true;
                //int num_Booking_recs = json.getInt("number_records");
                Log.d(LOG, "putMySQLBookingTable Success: ");
            } else {
                Log.d(LOG, "putMySQLBookingTable: Not Successful");
                MainActivity._pass = "";
                LOGGED_IN = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(LOG, "putMySQLBookingTable exception > " + e.toString());
        }
        Log.d(LOG, "putMySQLBookingTable.doInBackground end");
        return null;
    }
}


