package org.itech.vmmc;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
 * {@link EditBookingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditBookingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditBookingFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static String TAG = "EditBookingTag";
    public static String LOG = "gnr";
    public Context _context;

    private static final String ARG_EDIT_BOOKING_PARAM = "EXTRA_EDIT_BOOKING_PARAM";
    private static final String ARG_EDIT_BOOKING_RECORD_PARAM = "EXTRA_EDIT_BOOKING_RECORD_PARAM";

    View _view;

    // private String mEditBookingParam;
    private static String _editBookingRecordParam;
    private static Booking _booking;
    private static Status _status;
    private static Client _client;
    private static VMMCLocation _location;
    private static ProcedureType _procedureType;
    private static Followup _followup;
    private static TextView _first_name;
    private static TextView _last_name;
    private static TextView _national_id;
    private static TextView _phone;
    private static TextView _facilitator;
    private static TextView _location_id;
    private static TextView _projected_date;
    private static TextView _actual_date;

    private static TextView _consent;
    private static RadioGroup _rg_consent;
    private static RadioButton _rb_consent;
    private static RadioButton _rb_consent_no;
    private static RadioButton _rb_consent_yes;

    private static TextView _procedure_type_id;
    private static TextView _followup_id;
    private static TextView _followup_date;
    private static TextView _contact;
    private static TextView _alt_contact;

    private EditText et_projected_date;
    private EditText et_actual_date;
    private EditText et_followup_date;

    private static TextView _latitude;
    private static TextView _longitude;

    private static OnFragmentInteractionListener mListener;
    private DBHelper dbHelp;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     //* @param param1 Parameter 1.
     //* @param param2 Parameter 2.
     * @return A new instance of fragment EditBookingFragment.
     */
    // TODO: Rename and change types and number of parameters
    //  public static CreateFragment newInstance(String param1, String param2) {
    public static EditBookingFragment newInstance(String mEditBookingParam, String mEditBookingRecordParam) {
        EditBookingFragment fragment = new EditBookingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("ARG_EDIT_BOOKING_PARAM", mEditBookingParam);
        bundle.putString("ARG_EDIT_BOOKING_RECORD_PARAM", mEditBookingRecordParam);

        _editBookingRecordParam = mEditBookingRecordParam;

        fragment.setArguments(bundle);
        return fragment;
    }

    // public EditBookingFragment() {
    // Required empty public constructor
    //}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _editBookingRecordParam = getArguments().getString("ARG_EDIT_BOOKING_RECORD_PARAM");
            Log.d(LOG, "editBookingFragment onCreate editBookingParam: ");
            Log.d(LOG, "editBookingFragment onCreate editBookingRecordParam: " + _editBookingRecordParam.toString() + "<");
        }

        String params = _editBookingRecordParam.toString();
        String parts[] = {};
        parts = params.split(":");

        String name = "";
        String firstName = "";
        String lastName = "";
        String nationalId = "";
        String phoneNumber = "";
        String facilitator = "";
        String projectedDate = "";
        String actualDate = "";

        String consent = "";
        int procedure_type_id = 1;
        int followup_id = 1;
        String followupDate = "";
        String alt_contact = "";

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
            case 4: {
                name = parts[0];
                nationalId = parts[1];
                phoneNumber = parts[2];
                projectedDate = parts[3];
                break;
            }
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

        Log.d(LOG, "EBF First: " + firstName);
        Log.d(LOG, "EBF Last: " + lastName);
        Log.d(LOG, "EBF NationalId: " + nationalId);
        Log.d(LOG, "EBF PhoneNumber: " + phoneNumber);
        Log.d(LOG, "EBF ProjectedDate: " + projectedDate);


        dbHelp = new DBHelper(getActivity());

        _booking = dbHelp.getBooking(firstName, lastName, nationalId, phoneNumber, projectedDate);
//        Log.d(LOG, "after getBooking _booking.get_followup_date: " + _booking.get_followup_date());

//        if (!nationalId.equals("") || !phoneNumber.equals("")) {
//            _booking = dbHelp.getBooking(nationalId, phoneNumber, projectedDate);
//        }

        if (_booking != null) {
            Log.d(LOG, "EBF _booking != null ");
            //Log.d(LOG, "EBF _booking != null " + _booking.get_first_name());
            _location = dbHelp.getLocation(String.valueOf(_booking.get_location_id()));

            Log.d(LOG, "EBF after getBooking: "
                    + _booking.get_fac_first_name() + ", "
                    + _booking.get_fac_last_name() + ", "
                    + _booking.get_fac_national_id() + ", "
                    + _booking.get_fac_phone() + ", "
            );
            Log.d(LOG, "EBF ActualDate: " + actualDate);

        } else {
            Log.d(LOG, "EBF _booking is equal null ");
            _location = dbHelp.getLocation("1"); // Default

//            Person person = dbHelp.getPerson(firstName, lastName, phoneNumber);
            Client client = dbHelp.getClient(firstName, lastName, "", phoneNumber);
            _booking = new Booking();
            _booking.set_first_name(client.get_first_name());
            _booking.set_last_name(client.get_last_name());
            _booking.set_national_id(client.get_national_id());
            _booking.set_phone(client.get_phone());
            _booking.set_projected_date(projectedDate);

            if (MainActivity.gFacilitator != null) {
                _booking.set_fac_first_name(MainActivity.gFacilitator.get_first_name());
                _booking.set_fac_last_name(MainActivity.gFacilitator.get_last_name());
                _booking.set_fac_national_id("");
                _booking.set_fac_phone(MainActivity.gFacilitator.get_phone());
            }

            _booking.set_consent(consent);
            _booking.set_procedure_type_id(procedure_type_id);
            _booking.set_followup_id(followup_id);
            _booking.set_alt_contact(alt_contact);


            //Log.d(LOG, "EBF _booking is equal null " + _booking.get_first_name());
        }

        _client = dbHelp.getClient(_booking.get_first_name(), _booking.get_last_name(), _booking.get_national_id(), _booking.get_phone());
        if(_client == null) {
            _client = new Client();
            _client.set_first_name(_booking.get_first_name());
            _client.set_last_name(_booking.get_last_name());
            _client.set_national_id(_booking.get_national_id());
            _client.set_phone(_booking.get_phone());
            _status = dbHelp.getStatus("Pending");
            _client.set_status_id(_status.get_id());

            GroupActivity _groupActivity = null;
            if(MainActivity.gGroupActivity == null) {
                 _groupActivity = dbHelp.getGroupActivity("Default Group", "2000-01-01");
            } else {
                 _groupActivity = MainActivity.gGroupActivity;
            }

            _client.set_group_activity_name(_groupActivity.get_name());
            _client.set_group_activity_date(_groupActivity.get_activity_date());
            Institution _institution = dbHelp.getInstitution("1");
            _client.set_institution_id(_institution.get_id());
            dbHelp.addClient( _client );
        } else {
            _status = dbHelp.getStatus( (String.valueOf(_client.get_status_id())));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_edit_booking, container, false);

        getActivity().setTitle(getResources().getString(R.string.editBookingTitle));
        if(_booking != null) {
            _first_name = (TextView) _view.findViewById(R.id.first_name);
            _first_name.setText(_booking.get_first_name());
//            _first_name.setInputType(InputType.TYPE_NULL);
            _last_name = (TextView) _view.findViewById(R.id.last_name);
            _last_name.setText(_booking.get_last_name());
//            _last_name.setInputType(InputType.TYPE_NULL);
            _national_id = (TextView) _view.findViewById(R.id.national_id);
            _national_id.setText(_booking.get_national_id());
//            _national_id.setInputType(InputType.TYPE_NULL);
            _phone = (TextView) _view.findViewById(R.id.phone_number);
            _phone.setText(_booking.get_phone());
//            _phone.setInputType(InputType.TYPE_NULL);
            _facilitator = (TextView) _view.findViewById(R.id.facilitator);

            if(_booking.get_fac_first_name() == null &&
                    _booking.get_fac_last_name() == null &&
                    _booking.get_fac_national_id() == null &&
                    _booking.get_fac_phone() == null) {
            } else {
                _facilitator.setText(_booking.get_fac_first_name() + " " + _booking.get_fac_last_name() + ", " + _booking.get_fac_national_id() + ", " + _booking.get_fac_phone());
            }

            _projected_date = (EditText) _view.findViewById(R.id.projected_date);
            _projected_date.setText(_booking.get_projected_date());
            _actual_date = (EditText) _view.findViewById(R.id.actual_date);
            _actual_date.setText(_booking.get_actual_date());
            _followup_date = (EditText) _view.findViewById(R.id.followup_date);
            _followup_date.setText(_booking.get_followup_date());

//            _contact = (TextView) _view.findViewById(R.id.contact);
//            _contact.setText(_booking.get_contact());
            _alt_contact = (TextView) _view.findViewById(R.id.altContact);
            _alt_contact.setText(_booking.get_alt_contact());

            Log.d(LOG, "ECF MainActivity.lat: " + MainActivity.lat);
            Log.d(LOG, "ECF MainActivity.lng: " + MainActivity.lng);

            _latitude = (TextView) _view.findViewById(R.id.latitude);
            if(_booking.get_latitude() == 0){
                _latitude.setText(String.valueOf(MainActivity.lat));
            } else {
                _latitude.setText(String.valueOf(_booking.get_latitude()));
            }
            _longitude = (TextView) _view.findViewById(R.id.longitude);
            if(_booking.get_latitude() == 0){
                _longitude.setText(String.valueOf(MainActivity.lng));
            } else {
                _longitude.setText(String.valueOf(_booking.get_longitude()));
            }
        }

        loadFacilitatorAutoComplete(_view );
        loadStatusDropdown(_view );
        loadLocationDropdown(_view );
        loadProcedureTypeDropdown(_view );
        loadFollowupDropdown(_view );
        loadConsentRadio(_view);

        et_projected_date = (EditText) _view.findViewById(R.id.projected_date);
        final SimpleDateFormat dateFormatter = new SimpleDateFormat(DBHelper.VMMC_DATE_FORMAT);
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog hold_projected_date_picker_dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                et_projected_date.setText(dateFormatter.format(newDate.getTime()));
                Log.d(LOG, "EBF: onDateSet: " + et_projected_date.getText());
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        final DatePickerDialog projected_date_picker_dialog = hold_projected_date_picker_dialog;

        et_projected_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG, "onClick: ");
                projected_date_picker_dialog.show();
            }
        });

        et_actual_date = (EditText) _view.findViewById(R.id.actual_date);
//        final SimpleDateFormat dateFormatter = new SimpleDateFormat(dbHelp.VMMC_DATE_FORMAT);
//        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog hold_actual_date_picker_dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                et_actual_date.setText(dateFormatter.format(newDate.getTime()));
                Log.d(LOG, "EBF: onDateSet: " + et_actual_date.getText());
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        final DatePickerDialog actual_date_picker_dialog = hold_actual_date_picker_dialog;

        et_actual_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG, "onClick: ");
                actual_date_picker_dialog.show();
            }
        });

        et_followup_date = (EditText) _view.findViewById(R.id.followup_date);
//        final SimpleDateFormat dateFormatter = new SimpleDateFormat(dbHelp.VMMC_DATE_FORMAT);
//        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog hold_followup_date_picker_dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                et_followup_date.setText(dateFormatter.format(newDate.getTime()));
                Log.d(LOG, "EBF: onDateSet: " + et_followup_date.getText());
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        final DatePickerDialog followup_date_picker_dialog = hold_followup_date_picker_dialog;

        et_followup_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG, "onClick: ");
                followup_date_picker_dialog.show();
            }
        });

        Button btnUpdateBooking = (Button) _view.findViewById(R.id.btnUpdateBooking);
        btnUpdateBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                java.util.Calendar cal = java.util.Calendar.getInstance();
                java.util.Date utilDate = cal.getTime();
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                _first_name = (TextView) _view.findViewById(R.id.first_name);
                _last_name = (TextView) _view.findViewById(R.id.last_name);
                _national_id = (TextView) _view.findViewById(R.id.national_id);
                _phone = (TextView) _view.findViewById(R.id.phone_number);
                _facilitator = (TextView) _view.findViewById(R.id.facilitator);

                Spinner lSpinner = (Spinner) _view.findViewById(R.id.vmmclocation);
//              String sLocationText  = lSpinner.getSelectedItem().toString();
                VMMCLocation _location = dbHelp.getLocation( lSpinner.getSelectedItem().toString());

                _projected_date= (TextView) _view.findViewById(R.id.projected_date);
                _actual_date= (TextView) _view.findViewById(R.id.actual_date);
                _followup_date= (TextView) _view.findViewById(R.id.followup_date);

                Log.d(LOG, "UpdateBooking button: " +
                        _first_name.getText() + ", " + _last_name.getText() + ", " + _national_id.getText() + ", " + _phone.getText() + ", " + _facilitator.getText() + " <");

                String sFirstName = _first_name.getText().toString();
                String sLastName = _last_name.getText().toString();
                String sNationalId = _national_id.getText().toString();
                String sPhoneNumber = _phone.getText().toString();
                if(PhoneNumberUtils.isGlobalPhoneNumber(sPhoneNumber) && sPhoneNumber.length() == DBHelper.VMMC_PHONE_NUMBER_LENGTH && sPhoneNumber.matches(DBHelper.VMMC_PHONE_NUMBER_REGEX)) {
//                    Log.d(LOG, "btnUpdate:isPhoneNumber: " + "true:" + sPhoneNumber.length());
                }else{
//                    Log.d(LOG, "btnUpdate:isPhoneNumber: " + "false:" + sPhoneNumber.length());
                    Toast.makeText(getActivity(), getResources().getString(IllegalEntry), Toast.LENGTH_LONG).show();
                    _phone.setText(_booking.get_phone());
                    _phone.requestFocus();
                    return;
                };
                String sFacilitator = _facilitator.getText().toString();

                String sProjectedDate = _projected_date.getText().toString();
                if(DBHelper.isDate(sProjectedDate) && sProjectedDate.length() == 10) {
//                    Log.d(LOG, "btnUpdate:isDate: " + "true:" + sProjectedDate.length());
                }else{
//                    Log.d(LOG, "btnUpdate:isDate: " + "false:" + sProjectedDate.length());
                    Toast.makeText(getActivity(), getResources().getString(IllegalEntry), Toast.LENGTH_LONG).show();
                    _projected_date.setText(_booking.get_projected_date());
                    _projected_date.requestFocus();
                    return;
                };
                String sActualDate = _actual_date.getText().toString();
                if((DBHelper.isDate(sActualDate) && sActualDate.length() == 10) || sActualDate.equals("")) {
//                    Log.d(LOG, "btnUpdate:isDate: " + "true:" + sProjectedDate.length());
                }else{
//                    Log.d(LOG, "btnUpdate:isDate: " + "false:" + sProjectedDate.length());
                    Toast.makeText(getActivity(), getResources().getString(IllegalEntry), Toast.LENGTH_LONG).show();
                    _actual_date.setText(_booking.get_actual_date());
                    _actual_date.requestFocus();
                    return;
                };
//                DateFormat df = new android.text.format.DateFormat();
//                String dProjectedDate = df.format(VMMC_DATE_FORMAT, projected_date);
//                String sDOB = _dob.getText().toString();
//                String sGender = _gender.getText().toString();

//                String sContact = _contact.getText().toString();
                String sAltContact = _alt_contact.getText().toString();

                Spinner pSpinner = (Spinner) _view.findViewById(R.id.procedureType);
                ProcedureType _procedureType = dbHelp.getProcedureType( pSpinner.getSelectedItem().toString());

                Spinner fSpinner = (Spinner) _view.findViewById(R.id.followup);
                Followup _followup = dbHelp.getFollowup( fSpinner.getSelectedItem().toString());
                String sFollowupDate = _followup_date.getText().toString();
                if((DBHelper.isDate(sFollowupDate) && sFollowupDate.length() == 10) || sFollowupDate.equals("")) {
//                    Log.d(LOG, "btnUpdate:isDate: " + "true:" + sProjectedDate.length());
                }else{
//                    Log.d(LOG, "btnUpdate:isDate: " + "false:" + sProjectedDate.length());
                    Toast.makeText(getActivity(), getResources().getString(IllegalEntry), Toast.LENGTH_LONG).show();
                    _followup_date.setText(_booking.get_followup_date());
                    _followup_date.requestFocus();
                    return;
                };

                String sConsent = _booking.get_consent();

                _latitude = (TextView) _view.findViewById(R.id.latitude); Float fLatitude = Float.valueOf(_latitude.getText().toString());
                _longitude = (TextView) _view.findViewById(R.id.longitude); Float fLongitude = Float.valueOf(_longitude.getText().toString());

                Log.d(LOG, "UpdateBooking button2: " +
                        _first_name.getText() + ", " + _last_name.getText() + ", " + _national_id.getText() + ", " + _phone.getText() + ", " +
                        _actual_date.getText() + ", " +
                        sConsent + ", " +
                        _procedureType.get_name() + ", " +
                        _followup.get_name() + ", " +
                        sFollowupDate + ", " +
//                        _contact.getText() + ", " +
                        _alt_contact.getText() + " <");

                DisplayParts displayParts = new DisplayParts(sFacilitator);
                Log.d(LOG, "UpdateBooking button3: " +
                        displayParts.get_first_name() + ", " +displayParts.get_last_name() + ", " + displayParts.get_national_id() + ", " + displayParts.get_phone());

                boolean complete = true;
                if(sFirstName.matches("") ) complete = false;
                if(sLastName.matches("") ) complete = false;
                if(sPhoneNumber.matches("") ) complete = false;
                if(sProjectedDate.matches("") ) complete = false;

                if(complete) {
                    dbHelp.updateClient(_client);

                    Booking lookupBooking = dbHelp.getBooking(sFirstName, sLastName, sNationalId, sPhoneNumber, sProjectedDate);

                    if (lookupBooking != null) {
                        lookupBooking.set_first_name(sFirstName);
                        lookupBooking.set_last_name(sLastName);
                        lookupBooking.set_national_id(sNationalId);
                        lookupBooking.set_phone(sPhoneNumber);
                        lookupBooking.set_fac_first_name(displayParts.get_first_name());
                        lookupBooking.set_fac_last_name(displayParts.get_last_name());
                        lookupBooking.set_fac_national_id(displayParts.get_national_id());
                        lookupBooking.set_fac_phone(displayParts.get_phone());
                        lookupBooking.set_location_id(_location.get_id());
                        lookupBooking.set_latitude(fLatitude);
                        lookupBooking.set_longitude(fLongitude);
                        lookupBooking.set_projected_date(sProjectedDate);
                        lookupBooking.set_actual_date(sActualDate);

                        lookupBooking.set_consent(sConsent);
                        lookupBooking.set_procedure_type_id(_procedureType.get_id());
                        lookupBooking.set_followup_id(_followup.get_id());
                        lookupBooking.set_followup_date(sFollowupDate);
//                        lookupBooking.set_contact(sContact);
                        lookupBooking.set_alt_contact(sAltContact);
                        Log.d(LOG, "UpdateBooking update: " +
                                _first_name.getText() + " " + _last_name.getText() + ", " + _national_id.getText() + ", " + _phone.getText() + ", " + _projected_date.getText() +" <");
                        if(dbHelp.updateBooking(lookupBooking))
                            Toast.makeText(getActivity(), "Booking Updated", Toast.LENGTH_LONG).show();
                    } else {
                        Booking booking = new Booking();
                        booking.set_first_name(sFirstName.toString());
                        booking.set_last_name(sLastName);
                        booking.set_national_id(sNationalId);
                        booking.set_phone(sPhoneNumber);
                        booking.set_fac_first_name(displayParts.get_first_name());
                        booking.set_fac_last_name(displayParts.get_last_name());
                        booking.set_fac_national_id(displayParts.get_national_id());
                        booking.set_fac_phone(displayParts.get_phone());
                        booking.set_location_id(_location.get_id());
                        booking.set_latitude(fLatitude);
                        booking.set_longitude(fLongitude);
                        booking.set_projected_date(sProjectedDate);
                        booking.set_actual_date(sActualDate);
                        booking.set_consent(sConsent);
                        booking.set_procedure_type_id(_procedureType.get_id());
                        booking.set_followup_id(_followup.get_id());
                        booking.set_followup_date(sFollowupDate);
//                        booking.set_contact(sContact);
                        booking.set_alt_contact(sAltContact);
                        Log.d(LOG, "UpdateBooking add: " +
                                _first_name.getText() + ", " + _last_name.getText() + ", " + _national_id.getText() + ", " + _phone.getText() + ", " + _projected_date.getText() +" <");
                        if(dbHelp.addBooking(booking))
                            Toast.makeText(getActivity(), "Booking Saved", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Must enter First Name, Last Name and Phone Number", Toast.LENGTH_LONG).show();
                }
            }
        });

        return _view;
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
         _booking = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener = null;
        Log.d(LOG, "Resume: ");
        _first_name = (TextView) _view.findViewById(R.id.first_name); _first_name.setText("");
        _last_name = (TextView) _view.findViewById(R.id.last_name); _last_name.setText("");
        _national_id = (TextView) _view.findViewById(R.id.national_id); _national_id.setText("");
        _phone = (TextView) _view.findViewById(R.id.phone_number); _phone.setText("");
        _projected_date = (TextView) _view.findViewById(R.id.projected_date); _projected_date.setText("");
        _actual_date = (TextView) _view.findViewById(R.id.actual_date); _actual_date.setText("");
        _followup_date = (TextView) _view.findViewById(R.id.followup_date); _followup_date.setText("");
//        _dob = (TextView) _view.findViewById(R.id.dob); _dob.setText("");
//        _gender = (TextView) _view.findViewById(R.id.gender); _gender.setText("");

        if(_booking != null) {
            _first_name = (TextView) _view.findViewById(R.id.first_name);
            _first_name.setText(_booking.get_first_name());
            _last_name = (TextView) _view.findViewById(R.id.last_name);
            _last_name.setText(_booking.get_last_name());
            _national_id = (TextView) _view.findViewById(R.id.national_id);
            _national_id.setText(_booking.get_national_id());
            _phone = (TextView) _view.findViewById(R.id.phone_number);
            _phone.setText(_booking.get_phone());
            _projected_date = (TextView) _view.findViewById(R.id.projected_date);
            _projected_date.setText(_booking.get_projected_date());
            _actual_date = (TextView) _view.findViewById(R.id.actual_date);
            _actual_date.setText(_booking.get_actual_date());
            _followup_date = (TextView) _view.findViewById(R.id.followup_date);
            _followup_date.setText(_booking.get_followup_date());

        }
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

    private Booking booking;
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

        final Spinner pSpinner = (Spinner) view.findViewById(R.id.status);
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
        pSpinner.setAdapter(dataAdapter);
        String compareValue = _status.get_name();
        if (!compareValue.equals(null)) {
            int spinnerPosition = dataAdapter.getPosition(compareValue);
            pSpinner.setSelection(spinnerPosition);
        }

        pSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String statusText  = pSpinner.getSelectedItem().toString();
                _status = dbHelp.getStatus(statusText);
                _client.set_status_id(_status.get_id());
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

    public void loadLocationDropdown(View view ) {
        Log.d(LOG, "loadLoactionDropdown: " );

        final Spinner lSpinner = (Spinner) view.findViewById(R.id.vmmclocation);
        final List<String> locationNames = dbHelp.getAllLocationNames();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, locationNames);

        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
        lSpinner.setAdapter(dataAdapter);
        _location = dbHelp.getLocation(String.valueOf(_booking.get_location_id()));
        if (_location == null) {
            _location = dbHelp.getLocation("1"); // Default
        }
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
                _client.set_loc_id(_location.get_id());
                Log.d(LOG, "location: " + _location.get_id() + _location.get_name());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(LOG, "spinner nothing selected");
            }
        });
    }

    public void loadProcedureTypeDropdown(View view ) {
        Log.d(LOG, "loadProcedureTypeDropdown: " );

        final Spinner pSpinner = (Spinner) view.findViewById(R.id.procedureType);
        final List<String> procedureTypeNames = dbHelp.getAllProcedureTypeNames();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, procedureTypeNames);

        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
        pSpinner.setAdapter(dataAdapter);
        _procedureType = dbHelp.getProcedureType(String.valueOf(_booking.get_procedure_type_id()));
        if (_procedureType == null) {
            _procedureType = dbHelp.getProcedureType("1"); // Default
        }
        String compareValue = _procedureType.get_name();
        if (!compareValue.equals(null)) {
            int spinnerPosition = dataAdapter.getPosition(compareValue);
            pSpinner.setSelection(spinnerPosition);
        }

        pSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String procedureTypeText  = pSpinner.getSelectedItem().toString();
                _procedureType = dbHelp.getProcedureType(procedureTypeText);
                _booking.set_procedure_type_id(_procedureType.get_id());
                Log.d(LOG, "procedureType: " + _procedureType.get_id() + _procedureType.get_name());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(LOG, "spinner nothing selected");
            }
        });
    }

    public void loadFollowupDropdown(View view ) {
        Log.d(LOG, "loadFollowupDropdown: " );

        final Spinner fSpinner = (Spinner) view.findViewById(R.id.followup);
        final List<String> followupNames = dbHelp.getAllFollowupNames();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, followupNames);

        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
        fSpinner.setAdapter(dataAdapter);
        _followup = dbHelp.getFollowup(String.valueOf(_booking.get_followup_id()));
        if (_followup == null) {
            _followup = dbHelp.getFollowup("1"); // Default
        }
        String compareValue = _followup.get_name();
        if (!compareValue.equals(null)) {
            int spinnerPosition = dataAdapter.getPosition(compareValue);
            fSpinner.setSelection(spinnerPosition);
        }

        fSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String followupText  =fSpinner.getSelectedItem().toString();
                _followup = dbHelp.getFollowup(followupText);
                _booking.set_followup_id(_followup.get_id());
                Log.d(LOG, "followup: " + _followup.get_id() + _followup.get_name());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(LOG, "spinner nothing selected");
            }
        });
    }

    public void loadConsentRadio(View view ) {
        Log.d(LOG, "loadConsentRadio: ");

        _rg_consent = (RadioGroup) _view.findViewById(R.id.consent_radio_group);
        _rb_consent_no = (RadioButton) _view.findViewById(R.id.radioButtonNo);
        _rb_consent_yes = (RadioButton) _view.findViewById(R.id.radioButtonYes);

        if(_booking.get_consent() == null){
            _booking.set_consent("N"); // default
        }
        if (_booking.get_consent().equals("N") ) {
            _rb_consent_no.setChecked(true);
            _rb_consent_yes.setChecked(false);
        } else {
            _rb_consent_no.setChecked(false);
            _rb_consent_yes.setChecked(true);
        }

        _rb_consent_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId=_rg_consent.getCheckedRadioButtonId();
                _rb_consent=(RadioButton) _view.findViewById(selectedId);
                Log.d(LOG, "rb_consent: " + _rb_consent.getText() );
                _booking.set_consent("N");
            }
        });

        _rb_consent_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId=_rg_consent.getCheckedRadioButtonId();
                _rb_consent=(RadioButton) _view.findViewById(selectedId);
                Log.d(LOG, "rb_consent: " + _rb_consent.getText() );
                _booking.set_consent("Y");
            }
        });
    }

}

