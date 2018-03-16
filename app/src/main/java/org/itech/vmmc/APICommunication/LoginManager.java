package org.itech.vmmc.APICommunication;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.itech.vmmc.DBHelper;
import org.itech.vmmc.MainActivity;
import org.itech.vmmc.R;
import org.itech.vmmc.SyncAudit;
import org.itech.vmmc.VolleySingleton;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Caleb on 10/08/2017.
 */

public class LoginManager {

    private static String LOG = "csl";
    public static String JWT = "";
    public static boolean LOGGED_IN = false;

    private Context _context;
    private DBHelper dbhelp;


    public LoginManager(Context _context, final DBHelper dbHelper) {
        this._context = _context;
        this.dbhelp = dbHelper;
    }


    public void logIn(final NetworkResponseCallback callback, String username, String password) {
        final SyncAudit syncAudit = new SyncAudit();
        syncAudit.set_progress("login");
        syncAudit.set_status("");
        Log.d(LOG, "Login attempt at " + MainActivity.LOGIN_URL);
        if (hasValidJWT()) {
            Log.d(LOG, "Login successful: already have a jwt");
            LOGGED_IN = true;
        } else {
            //create login request with credentials
            LoginRequest loginRequest = new LoginRequest
                   (Request.Method.POST, MainActivity.LOGIN_URL, null,
                           new Response.Listener<JSONObject>() {
                               @Override
                               public void onResponse(JSONObject response) {
                                   try {
                                        Log.d(LOG, "Login response received");
                                        if (response.getString(MainActivity.TAG_SUCCESS).equals("0")) {
                                            invalidateJWT();
                                            MainActivity._pass = "";
                                            Log.d(LOG, "Login unsuccessful: "
                                                   + response.getString(MainActivity.TAG_MESSAGE));
                                            Toast.makeText(_context, _context.getResources()
                                                    .getString(R.string.authentication_failure), Toast.LENGTH_SHORT).show();
                                            syncAudit.set_status(_context.getResources()
                                                    .getString(R.string.authentication_failure));

                                        } else {
                                            JWT = response.getString("jwt");
                                            Log.d(LOG, "Login success: " + JWT);
                                            LOGGED_IN = true;
                                            callback.onSuccess();
                                        }
                                       dbhelp.addSyncAudit(syncAudit);
                                   } catch (JSONException e) {
                                        Log.d(LOG, "Login unsuccessful: ERROR in deciphering server response");
                                        e.printStackTrace();
                                       invalidateJWT();
                                       dbhelp.addSyncAudit(syncAudit);
                                   }
                               }
                           },
                           new Response.ErrorListener() {
                               @Override
                               public void onErrorResponse(VolleyError error) {
                                   Log.d(LOG, "Login unsuccessful: ERROR in receiving response");
                                   invalidateJWT();
                                   error.printStackTrace();
                                   syncAudit.set_status(_context.getResources()
                                           .getString(R.string.authentication_failure));
                                   dbhelp.addSyncAudit(syncAudit);
                               }
                           }
                   );
            loginRequest.setCredentials(username, password);
            VolleySingleton.getInstance(_context).addToRequestQueue(loginRequest);
            Log.d(LOG, "Login request sent");
        }
    }


    public void invalidateJWT() {
        JWT = "";
        LOGGED_IN = false;
    }


    public boolean hasValidJWT() {
        return (!JWT.equals(""));
    }

}
