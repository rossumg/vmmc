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
class getMySQLBookingTable extends AsyncTask<String, String, String> {

    private boolean LOGGED_IN = false;
    public Context _context;
    public SQLiteDatabase _db;
    DBHelper dbhelp;

    getMySQLBookingTable(Context context, DBHelper dbhelp){
        this._context = context;
        this._db = dbhelp.getWritableDatabase();
//        this._db.execSQL("delete from Booking");
    }

    @Override
    protected String doInBackground(String... args) {
        Log.d(LOG, "getMySQLBookingTable doInBackground ");

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
        Log.d(LOG, "getMySQLBookingTable username/password: " + username + " " + password);
        String datatable = "getBooking";
        try {
            URL url = null;
            try {
                url = new URL(MainActivity.GET_TABLE_URL);
                Log.d(LOG, "getMySQLBookingTable GET_TABLE_URL " + url.toString());
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
            int num_Booking_recs;
            if (success == 1) {
                LOGGED_IN = true;
                num_Booking_recs = json.getInt("number_records");
                JSONArray Booking_array = json.getJSONArray("posts");

                NotificationManager mNotifyManager = (NotificationManager) this._context.getSystemService(this._context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this._context);

                mBuilder.setContentTitle("Data Download")
                        .setContentText("Download in progress")
                        .setSmallIcon(R.drawable.download);

                int id = 1;

                for (i = 0; i< Booking_array.length(); i++) {
                    JSONObject _rec = Booking_array.getJSONObject(i);
                    int Booking_id = _rec.getInt("id");
                    // escape single quotes

                    String timestamp = _rec.getString("timestamp"); timestamp = timestamp.replace("'","''");

                    String first_name = _rec.getString("first_name"); first_name = first_name.replace("'","''");
                    String last_name = _rec.getString("last_name"); last_name = last_name.replace("'","''");
                    String national_id = _rec.getString("national_id");
                    String phone = _rec.getString("phone"); phone = phone.replace("'","''");

                    String fac_first_name = _rec.getString("fac_first_name"); fac_first_name = fac_first_name.replace("'","''");
                    String fac_last_name = _rec.getString("fac_last_name"); fac_last_name = fac_last_name.replace("'","''");
                    String fac_national_id = _rec.getString("fac_national_id"); fac_national_id= fac_national_id.replace("'","''");
                    String fac_phone = _rec.getString("fac_phone"); fac_phone = fac_phone.replace("'","''");

                    String location_id = _rec.getString("location_id"); location_id = location_id.replace("'","''");
                    String latitude = _rec.getString("latitude"); latitude = latitude.replace("'","''");
                    String longitude = _rec.getString("longitude"); longitude = longitude.replace("'","''");
                    String projected_date = _rec.getString("projected_date"); projected_date = projected_date.replace("'","''");
                    String actual_date = _rec.getString("actual_date");

                    String consent = _rec.getString("consent");
                    int procedure_type_id  = _rec.getInt("procedure_type_id");
                    int followup_id  = _rec.getInt("followup_id");
                    String followup_date  = _rec.getString("followup_date");
                    String alt_contact = _rec.getString("alt_contact");

                    Log.d(LOG, "getMYSQLBookingTable:doInBackground: " + first_name + ":" + last_name + ":" + phone);

                    String BookingInsert =
                            "insert or replace into Booking "
                                    + "(timestamp, first_name, last_name, national_id, phone, fac_first_name, fac_last_name, fac_national_id, fac_phone, location_id, latitude, longitude, projected_date, actual_date, consent, procedure_type_id, followup_id, followup_date, alt_contact )"
                                    + " values("
                                    // + Booking_id + ","
                                    + "'" + timestamp + "'" + ","
                                    + "'" + first_name + "'" + ","
                                    + "'" + last_name + "'" + ","
                                    + "'" + national_id + "'" + ","
                                    + "'" + phone + "'" + ","
                                    + "'" + fac_first_name + "'" + ","
                                    + "'" + fac_last_name + "'" + ","
                                    + "'" + fac_national_id + "'" + ","
                                    + "'" + fac_phone + "'" + ","
                                    + "'" + location_id + "'" + ","
                                    + "'" + latitude + "'" + ","
                                    + "'" + longitude + "'" + ","
                                    + "'" + projected_date + "'" + ","
                                    + "'" + actual_date + "'" + ","
                                    + "'" + consent + "'" + ","
                                    + "'" + procedure_type_id + "'" + ","
                                    + "'" + followup_id + "'" + ","
                                    + "'" + followup_date + "'" + ","
                                    + "'" + alt_contact + "'" + ");";

                    try {
                        // no longer used
                        //int progress = (int)((i / (float) num_Booking_recs) * 100);
                        //publishProgress(Integer.toString(progress));

                        int incr = (int)((i / (float) num_Booking_recs) * 100);
                        mBuilder.setProgress(100, incr, false);
                        mNotifyManager.notify(id, mBuilder.build());

                        //Log.d(LOG, "getMySQLBookingTable BookingInsert " + BookingInsert.toString() + " " + i);
                        //Log.d(LOG, "getMySQLBookingTable BookingInsert " + " " + i);

                       _db.execSQL(BookingInsert.toString());

                    } catch (Exception ex) {
                        Log.d(LOG, "getMySQLBookingTable loop exception > " + ex.toString());
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
            Log.d(LOG, "getMySQLBookingTable exception > " + e.toString());
        }
        Log.d(LOG, "getMySQLBookingTable.doInBackground end");
        return Integer.toString(i);
    }

    protected void onPostExecute(String result) {
        Log.d(LOG, "getMYSQLBookingTable:onPostExecute: " + result);
        //Toast.makeText(this._context, "Downloaded " + result + " Bookings", Toast.LENGTH_LONG).show();
        //Toast.makeText(this._context, this._context.getResources().getString(R.string.sync_complete), Toast.LENGTH_LONG).show();
    }
}


