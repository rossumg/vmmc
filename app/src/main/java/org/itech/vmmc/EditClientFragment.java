package org.itech.vmmc;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.itech.vmmc.R.string.IllegalEntry;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditClientFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditClientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditClientFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static String TAG = "EditClientTag";
    public static String LOG = "gnr";
    public Context _context;

    private static final String ARG_EDIT_CLIENT_PARAM = "EXTRA_EDIT_CLIENT_PARAM";
    private static final String ARG_EDIT_CLIENT_RECORD_PARAM = "EXTRA_EDIT_CLIENT_RECORD_PARAM";

    View _view;

    // private String mEditClientParam;
    private static String _editClientRecordParam;
    private static Status _status;
    private static VMMCLocation _location;
    private static Institution _institution;
    private static GroupActivity _groupActivity;
    private static Client _client;
    private static TextView _first_name;
    private static TextView _last_name;
    private static TextView _national_id;
    private static TextView _phone;

    private static Address _address;
    private static TextView _phone_number;
    private static TextView _dob;
    private static TextView _gender;
    private static RadioGroup _rg_gender;
    private static RadioButton _rb_gender;
    private static RadioButton _rb_gender_male;
    private static RadioButton _rb_gender_female;

    private EditText et_dob;

    private static TextView _status_id;
    private static TextView _location_id;
    private static TextView _institution_id;
    private static TextView _group_activity;
//    private static TextView _projected_date;
//    private static TextView _actual_date;
    private static TextView _latitude;
    private static TextView _longitude;
    private static TextView _facilitator;


//    private EditText et_projected_date;

    private static OnFragmentInteractionListener mListener;
    private DBHelper dbHelp;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     //* @param param1 Parameter 1.
     //* @param param2 Parameter 2.
     * @return A new instance of fragment EditClientFragment.
     */
    // TODO: Rename and change types and number of parameters
    //  public static CreateFragment newInstance(String param1, String param2) {
    public static EditClientFragment newInstance(String mEditClientParam, String mEditClientRecordParam) {
        EditClientFragment fragment = new EditClientFragment();
        Bundle bundle = new Bundle();
        bundle.putString("ARG_EDIT_CLIENT_PARAM", mEditClientParam);
        bundle.putString("ARG_EDIT_CLIENT_RECORD_PARAM", mEditClientRecordParam);

        _editClientRecordParam = mEditClientRecordParam;

        fragment.setArguments(bundle);
        return fragment;
    }

    // public EditClientFragment() {
    // Required empty public constructor
    //}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _editClientRecordParam = getArguments().getString("ARG_EDIT_CLIENT_RECORD_PARAM");
            Log.d(LOG, "editClientFragment onCreate editClientParam: ");
            Log.d(LOG, "editClientFragment onCreate editClientRecordParam: " + _editClientRecordParam.toString() + "<");
        }

        String params = _editClientRecordParam.toString();
        String parts[] = {};
        parts = params.split(":");

        String name = "";
        String firstName = "";
        String lastName = "";
        String nationalId = "";
        String phoneNumber = "";
//        String projectedDate = "";

        switch (parts.length) {
            case 0: {
                // add
                break;
            }
            case 1: {
                name = parts[0];
                break;
            }
            case 2: {
                name = parts[0];
                nationalId = parts[1];
                break;
            }
            case 3: {
                name = parts[0];
                nationalId = parts[1];
                phoneNumber = parts[2];
                break;
            }
//            case 4: {
//                name = parts[0];
//                nationalId = parts[1];
//                phoneNumber = parts[2];
//                projectedDate = parts[3];
//                break;
//            }
        }

        String nameParts[] = {};
        nameParts = name.split(" ");
        switch (nameParts.length) {
            case 0: {
                break;
            }
            case 1: {
                firstName = nameParts[0];
                break;
            }
            case 2: {
                firstName = nameParts[0];
                lastName = nameParts[1];
                break;
            }
        }

        Log.d(LOG, "ECF First: " + firstName);
        Log.d(LOG, "ECF Last: " + lastName);
        Log.d(LOG, "ECF NationalId: " + nationalId);
        Log.d(LOG, "ECF PhoneNumber: " + phoneNumber);

        dbHelp = new DBHelper(getActivity());

        if (!firstName.equals("") && !lastName.equals("") && !phoneNumber.equals("")) {
            _client = dbHelp.getClient(firstName, lastName, nationalId, phoneNumber);
        }
        else if (!nationalId.equals("") || !phoneNumber.equals("")) {
            _client = dbHelp.getClient(nationalId, phoneNumber);
        }

        if(_client == null) { // use defaults
            _client = new Client();
            _client.set_first_name(firstName);
            _client.set_last_name(lastName);
            _client.set_national_id(nationalId);
            _client.set_phone(phoneNumber);
            _status = dbHelp.getStatus("Pending");
            _location = dbHelp.getLocation("1");
            _institution = dbHelp.getInstitution("1");
            _address = dbHelp.getAddress("1");
            _client.set_gender("M");
        } else {
            _client.set_first_name(firstName);
            _client.set_last_name(lastName);
            _client.set_national_id(nationalId);
            _client.set_phone(phoneNumber);
            _status = dbHelp.getStatus((String.valueOf(_client.get_status_id())));
            _location = dbHelp.getLocation(String.valueOf(_client.get_loc_id()));
            _institution = dbHelp.getInstitution(String.valueOf(_client.get_institution_id()));
            _groupActivity = dbHelp.getGroupActivity(_client.get_group_activity_name(), _client.get_group_activity_date());
            _address = dbHelp.getAddress(String.valueOf(_client.get_address_id()));
        }

        switch (MainActivity.gClientOrigination) {
            case GroupActivity:
                if(MainActivity.gGroupActivity != null) {
                    _groupActivity = MainActivity.gGroupActivity;
                } else {
                    _groupActivity = null;
                }
                break;
            case CommunityRecruiter:
                if (MainActivity.gFacilitator != null) {
                    _client.set_fac_first_name(MainActivity.gFacilitator.get_first_name());
                    _client.set_fac_last_name(MainActivity.gFacilitator.get_last_name());
                    _client.set_fac_national_id("");
                    _client.set_fac_phone(MainActivity.gFacilitator.get_phone());
                }

                break;
            case DirectBooking:
                break;
            default:
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_edit_client, container, false);

        getActivity().setTitle(getResources().getString(R.string.editClientTitle));
        if(_client != null) {
            _first_name = (TextView) _view.findViewById(R.id.first_name);
            _first_name.setText(_client.get_first_name());
            _first_name.setFocusable(true);
            InputMethodManager imm;
            imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
            _first_name.requestFocus();
//            _first_name.setInputType(InputType.TYPE_NULL);
            _last_name = (TextView) _view.findViewById(R.id.last_name);
            _last_name.setText(_client.get_last_name());
//            _last_name.setInputType(InputType.TYPE_NULL);
            _national_id = (TextView) _view.findViewById(R.id.national_id);
            _national_id.setText(_client.get_national_id());
//            _national_id.setInputType(InputType.TYPE_NULL);
            _phone = (TextView) _view.findViewById(R.id.phone_number);
            _phone.setText(_client.get_phone());
//            _phone.setInputType(InputType.TYPE_NULL);
            _dob = (TextView) _view.findViewById(R.id.dob);
            _dob.setText(_client.get_dob());

            Log.d(LOG, "ECF MainActivity.lat: " + MainActivity.lat);
            Log.d(LOG, "ECF MainActivity.lng: " + MainActivity.lng);

            _latitude = (TextView) _view.findViewById(R.id.latitude);
            if(_client.get_latitude() == 0){
                _latitude.setText(String.valueOf(MainActivity.lat));
            } else {
                _latitude.setText(String.valueOf(_client.get_latitude()));
            }
            _longitude = (TextView) _view.findViewById(R.id.longitude);
            if(_client.get_latitude() == 0){
                _longitude.setText(String.valueOf(MainActivity.lng));
            } else {
                _longitude.setText(String.valueOf(_client.get_longitude()));
            }
        }
        _facilitator = (TextView) _view.findViewById(R.id.facilitator);

        if(_client.get_fac_first_name() == null &&
                _client.get_fac_last_name() == null &&
                _client.get_fac_national_id() == null &&
                _client.get_fac_phone() == null) {
        } else {
            _facilitator.setText(_client.get_fac_first_name() + " " + _client.get_fac_last_name() + ", " + _client.get_fac_national_id() + ", " + _client.get_fac_phone());
        }

        loadFacilitatorAutoComplete(_view );
        loadStatusDropdown(_view );
        loadLocationDropdown(_view );
        loadInstitutionDropdown(_view );
        loadGroupActivityDropdown(_view);
        loadAddressDropdown(_view );
        loadGenderRadio(_view);

//        Button btnBooking = (Button) _view.findViewById(R.id.btnBooking);
//        btnBooking.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Log.d(LOG, "Booking button: " +
//                        _first_name.getText() + ", " + _last_name.getText() + ", " + _national_id.getText() + ", " + _phone.getText() +  ", " +
//                        _status.get_id() +  ", "  + _location.get_id() + ", " + _institution.get_id() + _groupActivity.get_name() + ", " + _groupActivity.get_activity_date() + " <");
//
//                Calendar cal = Calendar.getInstance();
//                java.util.Date utilDate = cal.getTime();
//                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
//
//                Fragment fragment = EditBookingFragment.newInstance("editBooking", _first_name.getText() + " " + _last_name.getText() + ":" + _national_id.getText() + ":" + _phone.getText() + ":" + sqlDate);
//                getFragmentManager().beginTransaction().replace(R.id.container, fragment, EditBookingFragment.TAG).addToBackStack("EditBooking").commit();
//
//                Toast.makeText(getActivity(), "Booking button", Toast.LENGTH_LONG).show();
//            }
//        }); gnr: direct to edit frag

        et_dob = (EditText) _view.findViewById(R.id.dob);
        final SimpleDateFormat dateFormatter = new SimpleDateFormat(DBHelper.VMMC_DATE_FORMAT);
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog hold_dob_date_picker_dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                et_dob.setText(dateFormatter.format(newDate.getTime()));
                Log.d(LOG, "EBF: onDateSet: " + et_dob.getText());
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        final DatePickerDialog dob_date_picker_dialog = hold_dob_date_picker_dialog;

        et_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG, "onClick: ");
                dob_date_picker_dialog.show();
            }
        });



        Button btnBookingDisplay = (Button) _view.findViewById(R.id.btnBooking);
        btnBookingDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                java.util.Calendar cal = java.util.Calendar.getInstance();
                java.util.Date utilDate = cal.getTime();
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                Log.d(LOG, "Booking button:test " +
                        _first_name.getText() + ", " + _last_name.getText() + ", " + _national_id.getText() + ", " + _phone.getText() +  ", " +
                        _status.get_id() +  ", "  + _location.get_id() + ", " + _institution.get_id() + _groupActivity.get_name() + ", " + _groupActivity.get_activity_date() + " <");

                Client client = dbHelp.getClient(_first_name.toString(), _last_name.toString(), "", _phone.toString());
                Log.d(LOG, "Booking button: " +  ", " + client.get_first_name() + "< " + client.get_last_name() + " <");
                Fragment fragment;
                if (client != null) {
                    fragment = DisplayFragment.newInstance("displayBooking", _first_name.getText() + " " + _last_name.getText() + ":" + _national_id.getText() + ":" + _phone.getText() + ":" + "");
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, DisplayFragment.TAG).addToBackStack("DisplayBooking").commit();
                } else {
                    Toast.makeText(getActivity(), "Client not defined", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button btnBooking = (Button) _view.findViewById(R.id.btnBooking);
        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                java.util.Calendar cal = java.util.Calendar.getInstance();
                java.util.Date utilDate = cal.getTime();
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                Log.d(LOG, "Booking button: " +
                        _first_name.getText() + ", " + _last_name.getText() + ", " + _national_id.getText() + ", " + _phone.getText() +  ", " +
                        _status.get_id() +  ", "  + _location.get_id() + ", " + _institution.get_id() + "ga can be null" + ", " + " <");

                Client client = dbHelp.getClient(_first_name.getText().toString(), _last_name.getText().toString(), "", _phone.getText().toString());
                Fragment fragment;
                if (client != null) {
                    fragment = EditBookingFragment.newInstance("editBooking", _first_name.getText() + " " + _last_name.getText() + ":" + _national_id.getText() + ":" + _phone.getText() + ":" + "%");
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, EditBookingFragment.TAG).addToBackStack("EditBooking").commit();
                } else {
                    Toast.makeText(getActivity(), "Client not defined", Toast.LENGTH_LONG).show();
                }
            }
        });

//        Button btnPerson = (Button) _view.findViewById(R.id.btnPerson);
//        btnPerson.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Log.d(LOG, "Person button: " +
//                        _first_name.getText() + ", " + _last_name.getText() + ", " + _national_id.getText() + ", " + _phone.getText() +  ", " +
//                        _status.get_id() +  ", "  + _location.get_id() + ", " + _institution.get_id() + _groupActivity.get_name() + ", " + _groupActivity.get_activity_date() + " <");
//
//                Fragment fragment = EditPersonFragment.newInstance("editPerson", _first_name.getText() + " " + _last_name.getText() + ":" + _national_id.getText() + ":" + _phone.getText());
//                getFragmentManager().beginTransaction().replace(R.id.container, fragment, EditPersonFragment.TAG).addToBackStack("EditPerson").commit();
//
//                Toast.makeText(getActivity(), "Person button", Toast.LENGTH_LONG).show();
//            }
//        });

        Button btnUpdateClient = (Button) _view.findViewById(R.id.btnUpdate);
        btnUpdateClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                java.util.Date utilDate = cal.getTime();
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                _first_name = (TextView) _view.findViewById(R.id.first_name); String sFirstName = _first_name.getText().toString();
                _last_name = (TextView) _view.findViewById(R.id.last_name); String sLastName = _last_name.getText().toString();
                _national_id = (TextView) _view.findViewById(R.id.national_id); String sNationalId = _national_id.getText().toString();
                _phone = (TextView) _view.findViewById(R.id.phone_number); String sPhoneNumber = _phone.getText().toString();

                Log.d(LOG, "btnUpdate: " + PhoneNumberUtils.isGlobalPhoneNumber(sPhoneNumber) + "<");
                if(PhoneNumberUtils.isGlobalPhoneNumber(sPhoneNumber) && sPhoneNumber.length() == DBHelper.VMMC_PHONE_NUMBER_LENGTH && sPhoneNumber.matches(DBHelper.VMMC_PHONE_NUMBER_REGEX)) {
                    Log.d(LOG, "btnUpdate:isPhoneNumber: " + "true:" + sPhoneNumber.length());
                }else{
                    Log.d(LOG, "btnUpdate:isPhoneNumber: " + "false:" + sPhoneNumber.length());
                    Toast.makeText(getActivity(), getResources().getString(IllegalEntry), Toast.LENGTH_LONG).show();
                    _phone.setText("");
                    _phone.requestFocus();
                    return;
                };

                Spinner sSpinner = (Spinner) _view.findViewById(R.id.status);
//              String sStatusText  = sSpinner.getSelectedItem().toString();
                Status _status = dbHelp.getStatus( sSpinner.getSelectedItem().toString());

                Spinner lSpinner = (Spinner) _view.findViewById(R.id.vmmclocation);
//              String sLocationText  = lSpinner.getSelectedItem().toString();
                VMMCLocation _location = dbHelp.getLocation( lSpinner.getSelectedItem().toString());

                EditText _institutionEditText = (EditText) _view.findViewById(R.id.institution);
                Institution _institution = dbHelp.getInstitution( _institutionEditText.getText().toString());
                if(_institution == null) {
                    Toast.makeText(getActivity(), getResources().getString(IllegalEntry), Toast.LENGTH_LONG).show();
                    _institutionEditText.setText("");
                    _institutionEditText.requestFocus();
                    return;
                }

//                Spinner gaSpinner = (Spinner) _view.findViewById(R.id.group_activity);
                EditText _groupActivityEditText = (EditText) _view.findViewById(R.id.group_activity);
//                String sGroupActivityText  = gaSpinner.getSelectedItem().toString();

                Log.d(LOG, "_groupActivityEditText.getText().toString(): " + _groupActivityEditText.getText().toString() + "<");

                GroupActivity _groupActivity = new GroupActivity();
                String parts[] = _groupActivityEditText.getText().toString().split(", ", 2);

                if(_groupActivityEditText.getText().toString().equals("") || parts.length != 2 ) {
                    _groupActivityEditText.setText("");
                    _groupActivity.set_name("");
                    _groupActivity.set_activity_date("");
                    MainActivity.gGroupActivity = null;
                } else {
                    _groupActivity = dbHelp.getGroupActivity(parts[0], parts[1]);
                }

                String sFacilitator = _facilitator.getText().toString();

                Spinner aSpinner = (Spinner) _view.findViewById(R.id.address);
//              String sAddressText  = aSpinner.getSelectedItem().toString();
                Address _address = dbHelp.getAddress( aSpinner.getSelectedItem().toString());

                String sDOB = _dob.getText().toString();
                EditText _dobEditText = (EditText) _view.findViewById(R.id.dob);
                Log.d(LOG, "btnUpdate: " + DBHelper.isDate(sDOB) + "<");
                if(DBHelper.isDate(sDOB) && sDOB.length() == 10) {
                    Log.d(LOG, "btnUpdate:isDate: " + "true:" + sDOB.length());
                }else{
                    Log.d(LOG, "btnUpdate:isDate: " + "false:" + sDOB.length());
                    Toast.makeText(getActivity(), getResources().getString(IllegalEntry), Toast.LENGTH_LONG).show();
                    _dob.setText(_client.get_dob());
                    _dobEditText.requestFocus();
                    return;
                };

                String sGender = _client.get_gender();

                String sOrigination =  MainActivity.gClientOrigination.toString();

                _latitude = (TextView) _view.findViewById(R.id.latitude); Float fLatitude = Float.valueOf(_latitude.getText().toString());
                _longitude = (TextView) _view.findViewById(R.id.longitude); Float fLongitude = Float.valueOf(_longitude.getText().toString());

//                Log.d(LOG, "UpdateClient button: " +
//                        _first_name.getText() + ", " + _last_name.getText() + ", " + _national_id.getText() + ", " + _phone.getText() +  ", " +
//                        _status.get_id() +  ", "  + _location.get_id() + ", " + _institution.get_id() + "ga can be null" + ", " + _groupActivity.get_activity_date() + " <");

                DisplayParts displayParts = new DisplayParts(sFacilitator);
                boolean complete = true;
                if(sFirstName.matches("") ) complete = false;
                if(sLastName.matches("") ) complete = false;
                if(sPhoneNumber.matches("") ) complete = false;
//                if(sProjectedDate.matches("") ) complete = false;

                if(complete) {

                    Client lookupClient = dbHelp.getClient(sFirstName, sLastName, sNationalId, sPhoneNumber );

                    if (lookupClient != null) {
                        lookupClient.set_first_name(sFirstName);
                        lookupClient.set_last_name(sLastName);
                        lookupClient.set_national_id(sNationalId);
                        lookupClient.set_phone(sPhoneNumber);
                        lookupClient.set_status_id(_status.get_id());
                        lookupClient.set_loc_id(_location.get_id());
                        lookupClient.set_latitude(fLatitude);
                        lookupClient.set_longitude(fLongitude);
                        lookupClient.set_loc_id(_location.get_id());
                        lookupClient.set_institution_id(_institution.get_id());
                        lookupClient.set_group_activity_name(_groupActivity.get_name());
                        lookupClient.set_group_activity_date(_groupActivity.get_activity_date());

                        lookupClient.set_fac_first_name(displayParts.get_first_name());
                        lookupClient.set_fac_last_name(displayParts.get_last_name());
                        lookupClient.set_fac_national_id(displayParts.get_national_id());
                        lookupClient.set_fac_phone(displayParts.get_phone());

                        lookupClient.set_address_id(_address.get_id());
                        lookupClient.set_dob(sDOB);
                        lookupClient.set_gender(sGender);
                        lookupClient.set_origination(sOrigination);



                        Log.d(LOG, "UpdateClient update: " +
                                _first_name.getText() + ", " + _last_name.getText() + ", " + _national_id.getText() + ", " + _phone.getText() + ", " + _status.get_id() +  ", "  + _location.get_id() + ", " + _institution.get_id() + " <");

                        if(dbHelp.updateClient(lookupClient))
                            Toast.makeText(getActivity(), "Client Updated", Toast.LENGTH_LONG).show();
                    } else {
                        Client client = new Client();
                        client.set_first_name(sFirstName.toString());
                        client.set_last_name(sLastName);
                        client.set_national_id(sNationalId);
                        client.set_phone(sPhoneNumber);
                        client.set_status_id(_status.get_id());
                        client.set_loc_id(_location.get_id());
                        client.set_latitude(fLatitude);
                        client.set_longitude(fLongitude);
                        client.set_institution_id(_institution.get_id());
                        client.set_group_activity_name(_groupActivity.get_name());
                        client.set_group_activity_date(_groupActivity.get_activity_date());

                        client.set_fac_first_name(displayParts.get_first_name());
                        client.set_fac_last_name(displayParts.get_last_name());
                        client.set_fac_national_id(displayParts.get_national_id());
                        client.set_fac_phone(displayParts.get_phone());

                        client.set_address_id(_address.get_id());
                        client.set_dob(sDOB);
                        client.set_gender(sGender);
                        client.set_origination(sOrigination);

                        Log.d(LOG, "UpdateClient add: " +
                                _first_name.getText() + ", " + _last_name.getText() + ", " + _national_id.getText() + ", " + _phone.getText() + ", " + _status.get_id() +  ", "  + _location.get_id() + ", " + _institution.get_id() + " <");

                        Log.d(LOG, "UpdateClient add:user: " + MainActivity.USER_OBJ.get_username() + ":" + MainActivity.USER_OBJ.get_phone() + ":" + MainActivity.USER_OBJ.get_id());
                        Log.d(LOG, "UpdateClient add:gClientOrigination: " +  MainActivity.gClientOrigination);

                        if(dbHelp.addClient(client))
                            Toast.makeText(getActivity(), "Client Saved", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Must enter First Name, Last Name and Phone Number", Toast.LENGTH_LONG).show();
                }
            }
        });

        switch (MainActivity.gClientOrigination) {
            case GroupActivity:
                Log.d(LOG, "EditClient:user: " + MainActivity.USER_OBJ.get_username());
                Log.d(LOG, "EditClient:gClientOrigination: " +  MainActivity.gClientOrigination);
                disableEditText((EditText) _view.findViewById(R.id.facilitator));
                break;
            case CommunityRecruiter:
                Log.d(LOG, "EditClient:user: " + MainActivity.USER_OBJ.get_username());
                Log.d(LOG, "EditClient:gClientOrigination: " +  MainActivity.gClientOrigination);
                disableEditText((EditText) _view.findViewById(R.id.group_activity));
                break;
            case DirectBooking:
                Log.d(LOG, "EditClient:user: " + MainActivity.USER_OBJ.get_username());
                Log.d(LOG, "EditClient:gClientOrigination: " +  MainActivity.gClientOrigination);
                disableEditText((EditText) _view.findViewById(R.id.facilitator));
                disableEditText((EditText) _view.findViewById(R.id.group_activity));
                break;
            default:

                break;
        }

        return _view;
    }

    private void disableEditText(EditText editText) {
        editText.setText("");
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.LTGRAY);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}

    public void onNothingSelected(AdapterView<?> arg0) {}

    // TODO: Rename method, update argument and hook method into UI event
    public void onListItemPressed(int position) {
        if (mListener != null) {
            mListener.onFragmentInteraction(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

     @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        Log.d(LOG, "Detach: ");
         _client = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener = null;
        Log.d(LOG, "Resume: ");
//        _first_name = (TextView) _view.findViewById(R.id.first_name); _first_name.setText("");
//        _last_name = (TextView) _view.findViewById(R.id.last_name); _last_name.setText("");
//        _national_id = (TextView) _view.findViewById(R.id.national_id); _national_id.setText("");
//        _phone = (TextView) _view.findViewById(R.id.phone_number); _phone.setText("");
//
//        if(_client != null) {
//            _first_name = (TextView) _view.findViewById(R.id.first_name);
//            _first_name.setText(_client.get_first_name());
//            _last_name = (TextView) _view.findViewById(R.id.last_name);
//            _last_name.setText(_client.get_last_name());
//            _national_id = (TextView) _view.findViewById(R.id.national_id);
//            _national_id.setText(_client.get_national_id());
//            _phone = (TextView) _view.findViewById(R.id.phone_number);
//            _phone.setText(_client.get_phone());
//        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int position);

    }



    public void loadPersonIDDropdown(View view) {

        List<String> personIDs = dbHelp.getAllPersonIDs();
        // convert to array
        String[] stringArrayPersonID = new String[ personIDs.size() ];
        personIDs.toArray(stringArrayPersonID);

        final MultiAutoCompleteTextView dropdown = (MultiAutoCompleteTextView) view.findViewById(R.id.search);
        dropdown.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stringArrayPersonID);
        dropdown.setThreshold(1);
        dropdown.setAdapter(dataAdapter);

        dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int index, long position) {
                String text = dropdown.getText().toString();
                Log.d(LOG, "name selected: " + text);
            }
        });
    }


    public void loadFacilitatorAutoComplete(View view) {

        List<String> facilitatorIDs = dbHelp.getAllFacilitatorIDs();
        // convert to array
        String[] stringArrayFacilitatorID = new String[ facilitatorIDs.size() ];
        facilitatorIDs.toArray(stringArrayFacilitatorID);

        final ClearableAutoCompleteTextView dropdown = (ClearableAutoCompleteTextView) view.findViewById(R.id.facilitator);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stringArrayFacilitatorID);
        dropdown.setThreshold(1);
        dropdown.setAdapter(dataAdapter);

        dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int index, long position) {
                String facilitatorText = dropdown.getText().toString();
                String parts[] = {};
                parts = facilitatorText.split(", ");

                String fac_name = parts[0].trim();
                String fac_national_id =  parts[1].trim();
                String fac_phone_number = parts[2].trim();
//                String projected_date = parts[3].trim();
                Log.d(LOG, "booking facilitator selected: " + fac_name + "." + fac_national_id + "." + fac_phone_number + "." );

//                booking = dbHelp.getBooking(national_id, phone_number, projected_date);
//                Log.d(LOG, "booking_id selected: " + booking.get_id());

            }
        });
    }

    public void loadStatusDropdown(View view ) {
        Log.d(LOG, "loadStatusDropdown: " );

        final Spinner sSpinner = (Spinner) view.findViewById(R.id.status);
        List<String> statusNames = new ArrayList<String>();

        statusNames.addAll(dbHelp.getUserStatusTypes());
        if(_client == null) {
            _status = dbHelp.getStatus("Pending");
        } else {
            _client.set_status_id(_status.get_id());
            _status = dbHelp.getStatus(String.valueOf(_client.get_status_id()));
        }
        statusNames.add(0, _status.get_name().toString());

        Set<String> noDups = new LinkedHashSet<>(statusNames);
        statusNames.clear();
        statusNames.addAll(noDups);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, statusNames);
//        {
//            @Override
//            public boolean isEnabled(int position) {
//                return position != 1;
//            }
//
//            @Override
//            public boolean areAllItemsEnabled() {
//                return false;
//            }

//            @Override
//            public View getDropDownView(int position, View convertView, ViewGroup parent){
//                View v = convertView;
//                if (v == null) {
//                    Context mContext = this.getContext();
//                    LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                    v = vi.inflate(R.layout.simple_spinner_item, null);
//                }
////                Spinner spinner = (Spinner) v.findViewById(R.id.status);
//                Log.d(LOG, "loadStatusDropdown:position: " + position + ":" + statusNames.get(position));
//                TextView tv = (TextView) v.findViewById(R.id.spinnerTarget);
////                tv.setText(statusNames.get(position));
//
//                switch (position) {
//                    case 0:
////                        tv.setTextColor(Color.RED);
//                        break;
//                    case 1:
////                        tv.setTextColor(Color.BLUE);
//                        break;
//                    default:
////                        tv.setTextColor(Color.BLACK);
//                        break;
//                }
//                return v;
//            }
//        };
        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
        sSpinner.setAdapter(dataAdapter);

        String compareValue = _status.get_name();
        if (!compareValue.equals(null)) {
            int spinnerPosition = dataAdapter.getPosition(compareValue);
            sSpinner.setSelection(spinnerPosition);
        }

        sSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String statusText  = sSpinner.getSelectedItem().toString();
                _status = dbHelp.getStatus(statusText);
//                _client.set_status_id(_status.get_id());
                Log.d(LOG, "_status: " + _status.get_id() + _status.get_name());
//                status_type = dbHelp.getStatusType(statusText);


                //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
//                Log.d(LOG, "statusId/Name selected: " + status.get_id() + " " + status.get_name());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(LOG, "spinner nothing selected");
            }
        });
    }

    public void loadGroupActivityDropdown(View view ) {
//        final Spinner gaSpinner = (Spinner) view.findViewById(R.id.group_activity);
        final ClearableAutoCompleteTextView gaAutoComplete = (ClearableAutoCompleteTextView) view.findViewById(R.id.group_activity);
        final List<String> groupActivityNames = dbHelp.getAllGroupActivityIDs();

//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, groupActivityNames);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, groupActivityNames);
        gaAutoComplete.setThreshold(1);
        gaAutoComplete.setAdapter(dataAdapter);

        if (_groupActivity == null) {
            gaAutoComplete.setText("");
        } else {
            _client.set_group_activity_name(_groupActivity.get_name());
            _client.set_group_activity_date(_groupActivity.get_activity_date());
            _groupActivity = dbHelp.getGroupActivity(_client.get_group_activity_name(), _client.get_group_activity_date());
            gaAutoComplete.setText(_groupActivity.get_name() + ", " + _groupActivity.get_activity_date());
        }


        gaAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int index, long position) {
                String gaID  = gaAutoComplete.getText().toString();
                String parts[] = gaID.split(", ", 2);
                _groupActivity = dbHelp.getGroupActivity(parts[0], parts[1]);
                _client.set_group_activity_name(_groupActivity.get_name());
                _client.set_group_activity_date(_groupActivity.get_activity_date());
            }
        });
//        {
//            @Override
//            public boolean isEnabled(int position) {
//                return position != 1;
//            }
//
//            @Override
//            public boolean areAllItemsEnabled() {
//                return false;
//            }

//            @Override
//            public View getDropDownView(int position, View convertView, ViewGroup parent){
//                View v = convertView;
//                if (v == null) {
//                    Context mContext = this.getContext();
//                    LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                    v = vi.inflate(R.layout.simple_spinner_item, null);
//                }
////                Spinner spinner = (Spinner) v.findViewById(R.id.status);
//                Log.d(LOG, "loadStatusDropdown:position: " + position + ":" + statusNames.get(position));
//                TextView tv = (TextView) v.findViewById(R.id.spinnerTarget);
////                tv.setText(statusNames.get(position));
//
//                switch (position) {
//                    case 0:
////                        tv.setTextColor(Color.RED);
//                        break;
//                    case 1:
////                        tv.setTextColor(Color.BLUE);
//                        break;
//                    default:
////                        tv.setTextColor(Color.BLACK);
//                        break;
//                }
//                return v;
//            }
//        };
//        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
//        gaSpinner.setAdapter(dataAdapter);
//        if(_client == null) {
//            _groupActivity = dbHelp.getGroupActivity("Default Group", "2000-01-01");
//        } else {
//            _client.set_group_activity_name(_groupActivity.get_name());
//            _client.set_group_activity_date(_groupActivity.get_activity_date());
//            _groupActivity = dbHelp.getGroupActivity(_client.get_group_activity_name(), _client.get_group_activity_date());
//        }
//        String compareValue = _groupActivity.get_name() + ", " + _groupActivity.get_activity_date();
//        if (!compareValue.equals(null)) {
//            int spinnerPosition = dataAdapter.getPosition(compareValue);
//            gaSpinner.setSelection(spinnerPosition);
//        }
//
//        gaSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String gaID  = gaSpinner.getSelectedItem().toString();
//                String parts[] = gaID.split(", ", 2);
//                _groupActivity = dbHelp.getGroupActivity(parts[0], parts[1]);
////                _client.set_group_activity_name(_groupActivity.get_name());
////                _client.set_group_activity_date(_groupActivity.get_activity_date());
//                Log.d(LOG, "_groupActivity: " + _groupActivity.get_name() + _groupActivity.get_activity_date());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                Log.d(LOG, "spinner nothing selected");
//            }
//        });
    }

    public void loadLocationDropdown(View view ) {
        Log.d(LOG, "loadLoactionDropdown: " );

        final Spinner lSpinner = (Spinner) view.findViewById(R.id.vmmclocation);
        final List<String> locationNames = dbHelp.getAllLocationNames();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, locationNames);

        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
        lSpinner.setAdapter(dataAdapter);

        _location = dbHelp.getLocation(String.valueOf(_client.get_loc_id()));
        if(_location == null) {
            _location = dbHelp.getLocation("1");
        }
        _client.set_loc_id(_location.get_id());

        String compareValue = _location.get_name();
        if (!compareValue.equals(null)) {
            int spinnerPosition = dataAdapter.getPosition(compareValue);
            lSpinner.setSelection(spinnerPosition);
        }

        lSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String locationText  = lSpinner.getSelectedItem().toString();
                _location = dbHelp.getLocation(locationText);
//                _client.set_loc_id(_location.get_id());
                Log.d(LOG, "location: " + _location.get_id() + _location.get_name());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(LOG, "spinner nothing selected");
            }
        });
    }

    public void loadInstitutionDropdown(View view) {

        List<String> _institutionNames = dbHelp.getAllInstitutionNames();
        // convert to array
        String[] stringArrayNames = new String[ _institutionNames.size() ];
        _institutionNames.toArray(stringArrayNames);

        final ClearableAutoCompleteTextView dropdown = (ClearableAutoCompleteTextView) view.findViewById(R.id.institution);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stringArrayNames);
        dropdown.setThreshold(1);
        dropdown.setAdapter(dataAdapter);

        if (_client == null || _client.get_institution_id() == 0) {
            _institution = dbHelp.getInstitution("IUM"); // Default
        } else {
            _institution = dbHelp.getInstitution(String.valueOf(_client.get_institution_id()));
            _client.set_institution_id(_institution.get_id());
        }
        dropdown.setText(_institution.get_name());

        dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int index, long position) {
                _institution = dbHelp.getInstitution(dropdown.getText().toString());
                _client.set_institution_id(_institution.get_id());
            }
        });
    }

    public void loadAddressDropdown(View view ) {
        Log.d(LOG, "loadAddressDropdown: " );

        final Spinner lSpinner = (Spinner) view.findViewById(R.id.address);
        final List<String> addressNames = dbHelp.getAllAddressNames();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, addressNames);

        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
        lSpinner.setAdapter(dataAdapter);

        _address = dbHelp.getAddress(String.valueOf(_client.get_address_id()));
        if(_address == null) {
            _address = dbHelp.getAddress("1");
        }
        _client.set_address_id(_address.get_id());

        String compareValue = _address.get_name();
        if (!compareValue.equals(null)) {
            int spinnerPosition = dataAdapter.getPosition(compareValue);
            lSpinner.setSelection(spinnerPosition);
        }

        lSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String addressText  = lSpinner.getSelectedItem().toString();
                _address = dbHelp.getAddress(addressText);
//                _client.set_loc_id(_location.get_id());
                Log.d(LOG, "address: " + _address.get_id() + _address.get_name());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(LOG, "spinner nothing selected");
            }
        });
    }

    public void loadGenderRadio(View view ) {
        Log.d(LOG, "loadRegionRadio: ");

        _rg_gender = (RadioGroup) _view.findViewById(R.id.gender_radio_group);
        _rb_gender_male = (RadioButton) _view.findViewById(R.id.radioButtonMale);
        _rb_gender_female = (RadioButton) _view.findViewById(R.id.radioButtonFemale);

        if(_client.get_gender() == null){
            _client.set_gender("M"); // default
        }
        if (_client.get_gender().equals("M") ) {
            _rb_gender_male.setChecked(true);
            _rb_gender_female.setChecked(false);
        } else {
            _rb_gender_male.setChecked(false);
            _rb_gender_female.setChecked(true);
        }

        _rb_gender_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId=_rg_gender.getCheckedRadioButtonId();
                _rb_gender=(RadioButton) _view.findViewById(selectedId);
                Log.d(LOG, "rb_gender: " + _rb_gender.getText() );
                _client.set_gender("M");
            }
        });

        _rb_gender_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId=_rg_gender.getCheckedRadioButtonId();
                _rb_gender=(RadioButton) _view.findViewById(selectedId);
                Log.d(LOG, "rb_gender: " + _rb_gender.getText() );
                _client.set_gender("F");
            }
        });
    }

}

