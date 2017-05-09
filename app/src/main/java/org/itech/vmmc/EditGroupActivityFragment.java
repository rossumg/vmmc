package org.itech.vmmc;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditGroupActivityFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditGroupActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditGroupActivityFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static String TAG = "EditGroupActivityTag";
    public static String LOG = "gnr";
    public Context _context;

    private static final String ARG_EDIT_GROUPACTIVITY_PARAM = "EXTRA_EDIT_GROUPACTIVITY_PARAM";
    private static final String ARG_EDIT_GROUPACTIVITY_RECORD_PARAM = "EXTRA_EDIT_GROUPACTIVITY_RECORD_PARAM";

    View _view;

    // private String mEditGroupActivityParam;
    private static String _editGroupActivityRecordParam;
//    private static Booking _booking;
//    private static GroupActivityType _groupActivityType;

    private static GroupActivity _groupActivity;
    private static GroupActivityType _groupActivityType;
    private static TextView _name;
    private static TextView _timestamp;
    private static VMMCLocation _location;

    private static TextView _activity_date;
    private static TextView _group_type_id;

    private static TextView _males;
    private static TextView _females;
    private static TextView _messages;
    private static TextView _latitude;
    private static TextView _longitude;

    private EditText et_activity_date;

    private static OnFragmentInteractionListener mListener;
    private DBHelper dbHelp;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     //* @param param1 Parameter 1.
     //* @param param2 Parameter 2.
     * @return A new instance of fragment EditGroupActivityFragment.
     */
    // TODO: Rename and change types and number of parameters
    //  public static CreateFragment newInstance(String param1, String param2) {
    public static EditGroupActivityFragment newInstance(String mEditGroupActivityParam, String mEditGroupActivityRecordParam) {
        EditGroupActivityFragment fragment = new EditGroupActivityFragment();
        Bundle bundle = new Bundle();
        bundle.putString("ARG_EDIT_GROUPACTIVITY_PARAM", mEditGroupActivityParam);
        bundle.putString("ARG_EDIT_GROUPACTIVITY_RECORD_PARAM", mEditGroupActivityRecordParam);

        _editGroupActivityRecordParam = mEditGroupActivityRecordParam;

        fragment.setArguments(bundle);
        return fragment;
    }

    // public EditGroupActivityFragment() {
    // Required empty public constructor
    //}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _editGroupActivityRecordParam = getArguments().getString("ARG_EDIT_GROUPACTIVITY_RECORD_PARAM");
            Log.d(LOG, "editGroupActivityFragment onCreate editGroupActivityParam: ");
            Log.d(LOG, "editGroupActivityFragment onCreate editGroupActivityRecordParam: " + _editGroupActivityRecordParam.toString() + "<");
        }

        String params = _editGroupActivityRecordParam.toString();
        String parts[] = {};
        parts = params.split(":", 2);

        String _name = "";
        String _date = "";

        switch (parts.length) {
            case 0: {
                // add
                break;
            }
            case 1: {
                _name = parts[0];
                break;
            }
            case 2: {
                _name = parts[0];
                _date = parts[1];
                break;
            }
        }

        Log.d(LOG, "EG Name: " + _name);
        Log.d(LOG, "EG Date: " + _date);

        dbHelp = new DBHelper(getActivity());
        _groupActivity = dbHelp.getGroupActivity(_name, _date);

//        if (!nationalId.equals("") || !phoneNumber.equals("")) {
//            _groupActivity = new GroupActivity();
//            _groupActivity = dbHelp.getGroupActivity(firstName, lastName, nationalId, phoneNumber, projectedDate);
//            _groupActivity = dbHelp.getGroupActivity(nationalId, phoneNumber);
//        }
//        if (_groupActivity != null) {
//            Log.d(LOG, "EBF _groupActivity != null ");
//            Log.d(LOG, "EBF _groupActivity != null " + _groupActivity.get_first_name());
//        } else { // defaults
//            Log.d(LOG, "EBF _groupActivity is equal null ");
//            should check for more info like person frag, GNR
//            if (!firstName.equals("") && !lastName.equals("") && !phoneNumber.equals("")) {
//                _person = dbHelp.getPerson(firstName, lastName, phoneNumber);
//            } else if (!nationalId.equals("") || !phoneNumber.equals("")) {
//                _person = dbHelp.getPerson(nationalId, phoneNumber);
//            }



//            _groupActivity.set_name(_groupActivity.get_first_name());
//            _groupActivity.set_last_name(_groupActivity.get_last_name());
//            _groupActivity.set_national_id(_groupActivity.get_national_id());
//            _groupActivity.set_phone(_groupActivity.get_phone());
//            _groupActivityType = dbHelp.getGroupActivityType("3");
//            _location = dbHelp.getLocation("1");

//        }

//        _groupActivity_type = new Status(String.valueOf(_groupActivity.get_groupActivity_type_id()));
//        _location = new VMMCLocation(String.valueOf(_groupActivity.get_loc_id()));
//        _institution = new Institution(String.valueOf(_groupActivity.get_institution_id()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_edit_group_activity, container, false);

        getActivity().setTitle(getResources().getString(R.string.editGroupActivityTitle));
        if(_groupActivity != null) {
            _name = (TextView) _view.findViewById(R.id.name);
            _name.setText(_groupActivity.get_name());
//            _location_id = (TextView) _view.findViewById(R.id.location);
//            _location_id.setText(_groupActivity.get_location_id());
            _activity_date = (TextView) _view.findViewById(R.id.activity_date);
            _activity_date.setText(_groupActivity.get_activity_date());
//            _group_type_id = (TextView) _view.findViewById(R.id.group_type);
//            _group_type_id.setText(_groupActivity.get_group_type_id());
            _males = (TextView) _view.findViewById(R.id.males);
            _males.setText(String.valueOf(_groupActivity.get_males()));
            _females = (TextView) _view.findViewById(R.id.females);
            _females.setText(String.valueOf(_groupActivity.get_females()));
            _messages = (TextView) _view.findViewById(R.id.messages);
            _messages.setText(_groupActivity.get_messages());
//            _latitude = (TextView) _view.findViewById(R.id.latitude);
//            _latitude.setText(String.valueOf(_groupActivity.get_latitude()));
//            _longitude = (TextView) _view.findViewById(R.id.longitude);
//            _longitude.setText(String.valueOf(_groupActivity.get_longitude()));
        }

        loadLocationDropdown(_view );
        loadGroupActivityTypeDropdown(_view );

        et_activity_date = (EditText) _view.findViewById(R.id.activity_date);
        final SimpleDateFormat dateFormatter = new SimpleDateFormat(dbHelp.VMMC_DATE_FORMAT);
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog hold_activity_date_picker_dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                et_activity_date.setText(dateFormatter.format(newDate.getTime()));
                Log.d(LOG, "EBF: onDateSet: " + et_activity_date.getText());
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        final DatePickerDialog activity_date_picker_dialog = hold_activity_date_picker_dialog;

        et_activity_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG, "onClick: ");
                activity_date_picker_dialog.show();
            }
        });

        Button btnUpdate = (Button) _view.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                java.util.Date utilDate = cal.getTime();
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                _name = (TextView) _view.findViewById(R.id.name); String sName = _name.getText().toString();
                _activity_date = (TextView) _view.findViewById(R.id.activity_date); String sActivityDate = _activity_date.getText().toString();
                _males = (TextView) _view.findViewById(R.id.males);
                Integer iMales = Integer.valueOf(_males.getText().toString());
                _females = (TextView) _view.findViewById(R.id.females);
                Integer iFemales = Integer.valueOf(_females.getText().toString());


              Spinner gtSpinner = (Spinner) _view.findViewById(R.id.group_type);
              String gtGroupType  = gtSpinner.getSelectedItem().toString();
              GroupActivityType _groupActivityType = dbHelp.getGroupActivityType( gtSpinner.getSelectedItem().toString());

                Spinner lSpinner = (Spinner) _view.findViewById(R.id.vmmclocation);
//              String sLocationText  = lSpinner.getSelectedItem().toString();
                VMMCLocation _location = dbHelp.getLocation( lSpinner.getSelectedItem().toString());

                Log.d(LOG, "UpdateGroupActivity button: " +
                        _name.getText() + ", " + _activity_date.getText() + ", " + _males.getText() + ", " + _females.getText() + " <");

                boolean complete = true;
                if(sName.matches("") ) complete = false;
                if(sActivityDate.matches("") ) complete = false;

                if(complete) {
                    GroupActivity lookupGroupActivity = dbHelp.getGroupActivity(sName, sActivityDate );

                    if (lookupGroupActivity != null) {
                        lookupGroupActivity.set_name(sName);
                        lookupGroupActivity.set_group_type_id(_groupActivityType.get_id());
                        lookupGroupActivity.set_location_id(_location.get_id());
                        lookupGroupActivity.set_males(iMales);
                        lookupGroupActivity.set_females(iFemales);
                        Log.d(LOG, "UpdateGroupActivity update: " +
                                _name.getText() + ", " +  _activity_date.getText() + ", " + iMales.toString() + ", " + iFemales.toString() +"<");
                        if(dbHelp.updateGroupActivity(lookupGroupActivity))
                            Toast.makeText(getActivity(), "GroupActivity Updated", Toast.LENGTH_LONG).show();
                    } else {
                        GroupActivity groupActivity = new GroupActivity();
                        groupActivity.set_name(sName.toString());
                        groupActivity.set_group_type_id(_groupActivityType.get_id());
                        groupActivity.set_location_id(_location.get_id());
                        groupActivity.set_males(iMales);
                        groupActivity.set_females(iFemales);
                        Log.d(LOG, "UpdateGroupActivity add: " +
                                _name.getText() + ", " +  " <");
                        if(dbHelp.addGroupActivity(groupActivity))
                            Toast.makeText(getActivity(), "GroupActivity Saved", Toast.LENGTH_LONG).show();;
                    }
                } else {
                    Toast.makeText(getActivity(), "Must enter Name and Activity Date ", Toast.LENGTH_LONG).show();
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
         _groupActivity = null;
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
//        _note = (TextView) _view.findViewById(R.id.note); _note.setText("");
//        _status_id = (TextView) _view.findViewById(R.id.status); _status_id.setText("");
//        _location_id= (TextView) _view.findViewById(R.id.vmmclocation); _location_id.setText("");
//        _institution_id = (TextView) _view.findViewById(R.id.institution); _institution_id.setText("");
//        _projected_date = (TextView) _view.findViewById(R.id.projected_date); _projected_date.setText("");
//        _dob = (TextView) _view.findViewById(R.id.dob); _dob.setText("");
//        _gender = (TextView) _view.findViewById(R.id.gender); _gender.setText("");

        if(_groupActivity != null) {
            _name = (TextView) _view.findViewById(R.id.name);
            _name.setText(_groupActivity.get_name());
//            _last_name = (TextView) _view.findViewById(R.id.last_name);
//            _last_name.setText(_groupActivity.get_last_name());
//            _national_id = (TextView) _view.findViewById(R.id.national_id);
//            _national_id.setText(_groupActivity.get_national_id());
//            _phone = (TextView) _view.findViewById(R.id.phone_number);
//            _phone.setText(_groupActivity.get_phone());
//            _note = (TextView) _view.findViewById(R.id.note);
//            _note.setText(_groupActivity.get_note());
//            _projected_date = (TextView) _view.findViewById(R.id.projected_date);
//            _projected_date.setText(_groupActivity.get_projected_date());
//            _dob = (TextView) _view.findViewById(R.id.dob);
//            _dob.setText(_person.get_dob());
//            _gender = (TextView) _view.findViewById(R.id.gender);
//            _gender.setText(_person.get_gender());
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
        public void onFragmentInteraction(int position);

    }

    public void loadGroupActivityTypeDropdown(View view ) {
        Log.d(LOG, "loadGroupActivityTypeDropdown: " );

        final Spinner gtSpinner = (Spinner) view.findViewById(R.id.group_type);
        final List<String> groupActivityTypeNames = dbHelp.getAllGroupActivityTypes();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, groupActivityTypeNames);
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
        gtSpinner.setAdapter(dataAdapter);
        _groupActivityType = dbHelp.getGroupActivityType(String.valueOf(_groupActivity.get_group_type_id()));
        if (_groupActivityType == null) {
            _groupActivityType = dbHelp.getGroupActivityType("Natural Group");
        }
        String compareValue = _groupActivityType.get_name();
        if (!compareValue.equals(null)) {
            int spinnerPosition = dataAdapter.getPosition(compareValue);
            gtSpinner.setSelection(spinnerPosition);
        }

        gtSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String groupActivityTypeText  = gtSpinner.getSelectedItem().toString();
                _groupActivityType = dbHelp.getGroupActivityType(groupActivityTypeText);
                _groupActivity.set_group_type_id(_groupActivityType.get_id());
                Log.d(LOG, "groupActivityType: " + _groupActivityType.get_id() + _groupActivityType.get_name());
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
        Log.d(LOG, "loadLocationDropdown: " );

        final Spinner lSpinner = (Spinner) view.findViewById(R.id.vmmclocation);
        final List<String> locationNames = dbHelp.getAllLocationNames();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, locationNames);

        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
        lSpinner.setAdapter(dataAdapter);
        _location = dbHelp.getLocation(String.valueOf(_groupActivity.get_location_id()));
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
                _groupActivity.set_location_id(_location.get_id());
                Log.d(LOG, "location: " + _location.get_id() + _location.get_name());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(LOG, "spinner nothing selected");
            }
        });
    }

}

