package org.itech.vmmc;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


public class MainActivity extends ActionBarActivity implements NavigationDrawerCallbacks,
        LoginFragment.OnFragmentInteractionListener,
        ActionFragment.OnFragmentInteractionListener,
//        AddPersonFragment.OnFragmentInteractionListener,
        EditPersonFragment.OnFragmentInteractionListener,
        AddEditBookingFragment.OnFragmentInteractionListener,
        AddEditClientFragment.OnFragmentInteractionListener,
        EditClientFragment.OnFragmentInteractionListener,
        AddEditFacilitatorFragment.OnFragmentInteractionListener,
        EditFacilitatorFragment.OnFragmentInteractionListener,
        AddEditInteractionFragment.OnFragmentInteractionListener,
        EditInteractionFragment.OnFragmentInteractionListener,
        AddEditGroupActivityFragment.OnFragmentInteractionListener,
        EditGroupActivityFragment.OnFragmentInteractionListener,
        AddEditUserFragment.OnFragmentInteractionListener,
        EditUserFragment.OnFragmentInteractionListener,
//        AddBookingFragment.OnFragmentInteractionListener,
        EditBookingFragment.OnFragmentInteractionListener,
        DisplayFragment.OnFragmentInteractionListener
{
    public static Boolean LOGGED_IN = false;

    public static String currentFragmentId = "";

    public static String COUNTRY = "vmmc";
    public static String _version = "3.10";
//    public static String COUNTRY = "mobile_demo";
//    public static String COUNTRY = "zimbabwe";

    public static final String BASE_URL = "http://android.trainingdata.org/";
    public static final String DEV_URL = "http://10.0.2.2:4567/";
    public static final String GET_TABLE_URL = BASE_URL + COUNTRY + "/" + "getTable.php";

    public static final String LOGIN_URL = BASE_URL + COUNTRY + "/api/login.php";
    public static final String INDEX_URL = BASE_URL + COUNTRY + "/api/index.php";

    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public static SQLiteDatabase db;
    public static User USER_OBJ;
    public static String _user = "rossumg";
    public static String _pass = "";
    public static String _username = "";
    public static String _password = "";
    public static boolean configChange = false;
    public static String LOG = "gnr";

    private static LocationManager locationManager;
    private static String provider;
    public static float lat = 0;
    public static float lng = 0;
    public static int MAX_RETRIES = 3;

    public static String deviceId = "";
    public static GroupActivity gGroupActivity = null;
    public static Facilitator gFacilitator = null;
    public enum ClientOrigination {
        GroupActivity, CommunityRecruiter, DirectBooking
    }
    public static ClientOrigination gClientOrigination = null;

//    public static ArrayList<String> _CREDENTIALS = new ArrayList<String>();

//                "user@itech.org:password", "bar@example.com:world", "u@:pa", "a@:pa"

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        BroadcastReceiver mReceiver = new ScreenReceiver();
        registerReceiver(mReceiver, filter);
        ScreenReceiver.wasScreenOn = TRUE;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);
        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        // populate the navigation drawer
        //mNavigationDrawerFragment.setUserData("Rayce Rossum", "Rayce.Rossum@gmail.com", BitmapFactory.decodeResource(getResources(), R.drawable.avatar));
        //mNavigationDrawerFragment.setUserData("Zimbabwe", "Rayce.Rossum");

        final TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmIMEI, tmSerial, androidId;
        tmIMEI = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmIMEI.hashCode() << 32) | tmSerial.hashCode());
        MainActivity.deviceId = tmIMEI.toString();
//        Log.d(LOG, "mainActivity:onCreate:deviceId: " + deviceId + ":" + tmIMEI + ":" + tmSerial + ":" + androidId);
        Log.d(LOG, "mainActivity:onCreate:tmIMEI: " + tmIMEI );
        Log.d(LOG, "mainActivity:onCreate:DEV_URL:" + MainActivity.DEV_URL + "api/login.php" );


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the location provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            Log.d(LOG, "location is null");
            //latituteField.setText("Location not available");
            //longitudeField.setText("Location not available");
        }
    }

    /* Request updates at startup */
    @Override
    protected void onResume() {
        if (!ScreenReceiver.wasScreenOn) {
            // THIS IS WHEN ONRESUME() IS CALLED DUE TO A SCREEN STATE CHANGE
            System.out.println("SCREEN TURNED ON");
            Log.d(LOG, "mainActivity:onResume: ON " );
            ScreenReceiver.wasScreenOn = TRUE;
        } else {
            // THIS IS WHEN ONRESUME() IS CALLED WHEN THE SCREEN STATE HAS NOT CHANGED
        }
        super.onResume();
        Log.d(LOG, "mainActivity:onResume: ON " );
        //locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        if (ScreenReceiver.wasScreenOn) {
            // THIS IS THE CASE WHEN ONPAUSE() IS CALLED BY THE SYSTEM DUE TO A SCREEN STATE CHANGE
            System.out.println("SCREEN TURNED OFF");
            Log.d(LOG, "mainActivity:onPause: OFF " );
            ScreenReceiver.wasScreenOn = FALSE;
        } else {
            // THIS IS WHEN ONPAUSE() IS CALLED WHEN THE SCREEN STATE HAS NOT CHANGED
        }
        super.onPause();
        //locationManager.removeUpdates(this);
    }

    //@Override
    public void onLocationChanged(Location location) {
        lat = (float) (location.getLatitude());
        lng = (float) (location.getLongitude());
        Log.d(LOG, "mainActivity:lat: " + String.valueOf((lat)));
        Log.d(LOG, "mainActivity:lng: " + String.valueOf((lng)));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments

        if(MainActivity.configChange == true) { // config change from edit
            MainActivity.configChange = false;
            return;
        }

        if (!MainActivity.LOGGED_IN) {
            position = 0;
        } else {

            DBHelper dbHelp = new DBHelper(this);
            if(!_username.equals("sync@")) {
                User _user = new User(dbHelp, MainActivity._username + ":" + MainActivity._password);
                int i = 0;
                do {
//                Log.d(LOG, "DBHelper.doTestDB:_user.userPerms: gnr: redo user_to_acl " + _user.userPerms.get(i));
                } while(i++ < _user.userPerms.size()-1);

                if (_user.userPerms.contains("edit_group")) {
                    Log.d(LOG, "actionFragment:edit_group");
                }
            }
        }

        Log.d(LOG, "Main Loop: " + position);
//        Log.d(LOG, "main pop before: " + position);
//        getFragmentManager().popBackStack();

        getFragmentManager().popBackStackImmediate(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);

        Fragment fragment;
        final DBHelper dbHelp = new DBHelper(this);
        switch(position) {

            case 7:
//                final DBHelper userDBHelp = new DBHelper(this);
                if  (_username.equals("sync@")) {
                    dbHelp.importDB();
                    Toast.makeText(this, "Restore Complete", Toast.LENGTH_LONG).show();
                    fragment = getFragmentManager().findFragmentByTag(LoginFragment.LOG);
                    if (fragment == null) {
                        fragment = LoginFragment.newInstance();
                        getFragmentManager().beginTransaction().replace(R.id.container, fragment, LoginFragment.LOG).addToBackStack(currentFragmentId).commit();
                    } else {
                        getFragmentManager().beginTransaction().replace(R.id.container, fragment, LoginFragment.LOG).commit();
                    }
                    currentFragmentId = "Login";
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();

                    fragment = getFragmentManager().findFragmentByTag(ActionFragment.TAG);
                    if (fragment == null) {
                        fragment = ActionFragment.newInstance("main", "");
                        getFragmentManager().beginTransaction().replace(R.id.container, fragment, ActionFragment.TAG).addToBackStack(currentFragmentId).commit();
                    } else {
                        getFragmentManager().beginTransaction().replace(R.id.container, fragment, ActionFragment.TAG).commit();
                    }
                }


                break;

            case 8:
                fragment = getFragmentManager().findFragmentByTag(DisplayFragment.TAG);
                getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("DisplayFragment"));
                if (fragment == null) {
                    //fragment = DisplayFragment.newInstance("displaySyncAudit", "");
                    fragment = DisplayFragment.newInstance("displaySyncReady", "");
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, DisplayFragment.TAG).addToBackStack(MainActivity.currentFragmentId).commit();
                } else {
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, DisplayFragment.TAG).commit();
                }
                MainActivity.currentFragmentId = "DisplayFragment";

                break;

            case 0:
                fragment = getFragmentManager().findFragmentByTag(LoginFragment.LOG);
                if (fragment == null) {
                    fragment = LoginFragment.newInstance();
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, LoginFragment.LOG).addToBackStack(currentFragmentId).commit();
                } else {
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, LoginFragment.LOG).commit();
                }
                currentFragmentId = "Login";

                break;

            case 1:

            Log.d(LOG, "SYNC btn");
            if(MainActivity._pass.equals("")) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Password");

                final EditText input = new EditText(this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity._pass = input.getText().toString();
                        dbHelp.doSyncDB();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }

            if(MainActivity._pass.equals("")){
                //requires password
                //Toast.makeText(v.getContext(), "Valid password required.", Toast.LENGTH_LONG).show();
            } else {
                // try
//                    dbHelp.uploadDBData();
                Log.d(LOG, "actionFragment call sync: _username: " + MainActivity._username );
                dbHelp.doSyncDB();
            }

                fragment = getFragmentManager().findFragmentByTag(ActionFragment.TAG);
                if (fragment == null) {
                    fragment = ActionFragment.newInstance("main", "");
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, ActionFragment.TAG).addToBackStack(currentFragmentId).commit();
                } else {
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, ActionFragment.TAG).commit();
                }
                currentFragmentId = "Action";

        break;

            case 91:
                fragment = getFragmentManager().findFragmentByTag(ActionFragment.TAG);
                if (fragment == null) {
                    fragment = ActionFragment.newInstance("main", "");
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, ActionFragment.TAG).addToBackStack(currentFragmentId).commit();
                } else {
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, ActionFragment.TAG).commit();
                }
                currentFragmentId = "Action";

                break;

            case 3:
                fragment = getFragmentManager().findFragmentByTag(AddEditFacilitatorFragment.TAG);
                if (fragment == null) {
                    fragment = AddEditFacilitatorFragment.newInstance();
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, AddEditFacilitatorFragment.TAG).addToBackStack(currentFragmentId).commit();
                } else {
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, AddEditFacilitatorFragment.TAG).commit();
                }
                currentFragmentId = "AddEditFacilitator";

                break;

            case 94:
                fragment = getFragmentManager().findFragmentByTag(AddEditClientFragment.TAG);
                if (fragment == null) {
                    fragment = AddEditClientFragment.newInstance();
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, AddEditClientFragment.TAG).addToBackStack(currentFragmentId).commit();
                } else {
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, AddEditClientFragment.TAG).commit();
                }
                currentFragmentId = "AddEditClient";

                break;

            case 4:
                fragment = getFragmentManager().findFragmentByTag(AddEditBookingFragment.TAG);
                if (fragment == null) {
                    fragment = AddEditBookingFragment.newInstance();
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, AddEditBookingFragment.TAG).addToBackStack(currentFragmentId).commit();
                } else {
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, AddEditBookingFragment.TAG).commit();
                }
                currentFragmentId = "AddEditBooking";

                break;

            case 99:
                fragment = getFragmentManager().findFragmentByTag(AddEditInteractionFragment.TAG);
                if (fragment == null) {
                    fragment = AddEditInteractionFragment.newInstance();
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, AddEditInteractionFragment.TAG).addToBackStack(currentFragmentId).commit();
                } else {
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, AddEditInteractionFragment.TAG).commit();
                }
                currentFragmentId = "AddEditInteraction";

                break;

            case 2:
                fragment = getFragmentManager().findFragmentByTag(AddEditGroupActivityFragment.TAG);
                if (fragment == null) {
                    fragment = AddEditGroupActivityFragment.newInstance();
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, AddEditGroupActivityFragment.TAG).addToBackStack(currentFragmentId).commit();
                } else {
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, AddEditGroupActivityFragment.TAG).commit();
                }
                currentFragmentId = "AddEditGroupActivity";

                break;

            case 5:
                fragment = getFragmentManager().findFragmentByTag(DisplayFragment.TAG);
                getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("DisplayFragment"));
                if (fragment == null) {
                    fragment = DisplayFragment.newInstance("displayPendingFollowup", "");
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, DisplayFragment.TAG).addToBackStack(MainActivity.currentFragmentId).commit();
                } else {
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, DisplayFragment.TAG).commit();
                }
                MainActivity.currentFragmentId = "DisplayFragment";

                break;

            case 6:
//                final DBHelper userDBHelp = new DBHelper(this);
                if (dbHelp.getUserAccess("edit_user")) {
                    fragment = getFragmentManager().findFragmentByTag(AddEditUserFragment.TAG);
                    if (fragment == null) {
                        fragment = AddEditUserFragment.newInstance();
                        getFragmentManager().beginTransaction().replace(R.id.container, fragment, AddEditUserFragment.TAG).addToBackStack(currentFragmentId).commit();
                    } else {
                        getFragmentManager().beginTransaction().replace(R.id.container, fragment, AddEditUserFragment.TAG).commit();
                    }
                    currentFragmentId = "AddEditUser";
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();

                    fragment = getFragmentManager().findFragmentByTag(ActionFragment.TAG);
                    if (fragment == null) {
                        fragment = ActionFragment.newInstance("main", "");
                        getFragmentManager().beginTransaction().replace(R.id.container, fragment, ActionFragment.TAG).addToBackStack(currentFragmentId).commit();
                    } else {
                        getFragmentManager().beginTransaction().replace(R.id.container, fragment, ActionFragment.TAG).commit();
                    }
                    currentFragmentId = "Action";
                }

                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
            return;
        }

        int count = getFragmentManager().getBackStackEntryCount();
        Log.d("Back!", "onBackPressed: " + count + ", " + currentFragmentId);
//        FragmentManager.BackStackEntry entry = getFragmentManager().getBackStackEntryAt(count-1);
        for (int i = count-1; i > -1; i--){
            Log.d("Back!", "onBackPressed:stack: " + i + ", " + getFragmentManager().getBackStackEntryAt(i).getName());
        }

        Log.d("Back!", "onBackPressed:test: " + currentFragmentId + ", " + getFragmentManager().getBackStackEntryAt(count-1).getName());

        if(currentFragmentId.equals("Recent")) {
            return;
        }

        if (count > 1) {
            getFragmentManager().popBackStack();
            currentFragmentId = getFragmentManager().getBackStackEntryAt(count-1).getName();
        } else {
//            Log.d("Back!", "onBackPressed:exit: " + count + ", " + currentFragmentId);
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("Confirm exit");
//             Set up the buttons
//            builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    MainActivity.super.onBackPressed();
//                }
//            });
//            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.cancel();
//                }
//            });
//
//            builder.show();

            Fragment fragment;
            fragment = getFragmentManager().findFragmentByTag(ActionFragment.TAG);
            if (fragment == null) {
                fragment = ActionFragment.newInstance("main", "");
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, ActionFragment.TAG).addToBackStack(currentFragmentId).commit();
            } else {
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, ActionFragment.TAG).commit();
            }
            currentFragmentId = "Action";
        }

    }

//    @Override
//    public void onBackPressed() {
//        if (mNavigationDrawerFragment.isDrawerOpen())
//            mNavigationDrawerFragment.closeDrawer();
//        else {
//            Log.d(LOG, "main:onBackPressed: ");
//            super.onBackPressed();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            //getMenuInflater().inflate(R.menu.main, menu);
           // return true;
        //}
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(int position) {
    }
}
