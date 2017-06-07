package org.itech.vmmc;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditFacilitatorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditFacilitatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditFacilitatorFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static String TAG = "EditFacilitatorTag";
    public static String LOG = "gnr";
    public Context _context;

    private static final String ARG_EDIT_FACILITATOR_PARAM = "EXTRA_EDIT_FACILITATOR_PARAM";
    private static final String ARG_EDIT_FACILITATOR_RECORD_PARAM = "EXTRA_EDIT_FACILITATOR_RECORD_PARAM";

    View _view;

    // private String mEditFacilitatorParam;
    private static String _editFacilitatorRecordParam;
//    private static Booking _booking;
    private static FacilitatorType _facilitatorType;
    private static VMMCLocation _location;
    private static Institution _institution;
    private static Facilitator _facilitator;
    private static TextView _first_name;
    private static TextView _last_name;
    private static TextView _national_id;
    private static TextView _phone;
    private static TextView _note;
    private static TextView _facilitator_type_id;
    private static TextView _location_id;
    private static TextView _institution_id;
//    private static TextView _projected_date;
//    private static TextView _actual_date;


//    private EditText et_projected_date;

    private static OnFragmentInteractionListener mListener;
    private DBHelper dbHelp;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     //* @param param1 Parameter 1.
     //* @param param2 Parameter 2.
     * @return A new instance of fragment EditFacilitatorFragment.
     */
    // TODO: Rename and change types and number of parameters
    //  public static CreateFragment newInstance(String param1, String param2) {
    public static EditFacilitatorFragment newInstance(String mEditFacilitatorParam, String mEditFacilitatorRecordParam) {
        EditFacilitatorFragment fragment = new EditFacilitatorFragment();
        Bundle bundle = new Bundle();
        bundle.putString("ARG_EDIT_FACILITATOR_PARAM", mEditFacilitatorParam);
        bundle.putString("ARG_EDIT_FACILITATOR_RECORD_PARAM", mEditFacilitatorRecordParam);

        _editFacilitatorRecordParam = mEditFacilitatorRecordParam;

        fragment.setArguments(bundle);
        return fragment;
    }

    // public EditFacilitatorFragment() {
    // Required empty public constructor
    //}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _editFacilitatorRecordParam = getArguments().getString("ARG_EDIT_FACILITATOR_RECORD_PARAM");
            Log.d(LOG, "editFacilitatorFragment onCreate editFacilitatorParam: ");
            Log.d(LOG, "editFacilitatorFragment onCreate editFacilitatorRecordParam: " + _editFacilitatorRecordParam.toString() + "<");
        }

        String params = _editFacilitatorRecordParam.toString();
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

        Log.d(LOG, "EFF First: " + firstName);
        Log.d(LOG, "EFF Last: " + lastName);
        Log.d(LOG, "EFF NationalId: " + nationalId);
        Log.d(LOG, "EFF PhoneNumber: " + phoneNumber);

        dbHelp = new DBHelper(getActivity());
        _facilitator = dbHelp.getFacilitator(firstName, lastName, nationalId, phoneNumber);

        if (_facilitator != null) {
            Log.d(LOG, "EBF _facilitator != null " + _facilitator.get_first_name());
        } else { // defaults
            Log.d(LOG, "EBF _facilitator is equal null ");
            Person person = dbHelp.getPerson(firstName, lastName, phoneNumber);
            _facilitator = new Facilitator();
            _facilitator.set_first_name(person.get_first_name());
            _facilitator.set_last_name(person.get_last_name());
            _facilitator.set_national_id(person.get_national_id());
            _facilitator.set_phone(person.get_phone());
            _facilitatorType = dbHelp.getFacilitatorType("3");
            _location = dbHelp.getLocation("1");
            _institution = dbHelp.getInstitution("1");
        }

//        _facilitator_type = new Status(String.valueOf(_facilitator.get_facilitator_type_id()));
//        _location = new VMMCLocation(String.valueOf(_facilitator.get_loc_id()));
//        _institution = new Institution(String.valueOf(_facilitator.get_institution_id()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_edit_facilitator, container, false);

        getActivity().setTitle(getResources().getString(R.string.editFacilitatorTitle));
        if(_facilitator != null) {
            _first_name = (TextView) _view.findViewById(R.id.first_name);
            _first_name.setText(_facilitator.get_first_name());
            _first_name.setInputType(InputType.TYPE_NULL);
            _last_name = (TextView) _view.findViewById(R.id.last_name);
            _last_name.setText(_facilitator.get_last_name());
            _last_name.setInputType(InputType.TYPE_NULL);
            _national_id = (TextView) _view.findViewById(R.id.national_id);
            _national_id.setText(_facilitator.get_national_id());
            _national_id.setInputType(InputType.TYPE_NULL);
            _phone = (TextView) _view.findViewById(R.id.phone_number);
            _phone.setText(_facilitator.get_phone());
            _phone.setInputType(InputType.TYPE_NULL);
            _note = (TextView) _view.findViewById(R.id.note);
            _note.setText(_facilitator.get_note());
//            _projected_date = (EditText) _view.findViewById(R.id.projected_date);
//            _projected_date.setText(_facilitator.get_projected_date());
//            _dob = (TextView) _view.findViewById(R.id.dob);
//            _dob.setText(_person.get_dob());
//            _gender = (TextView) _view.findViewById(R.id.gender);
//            _gender.setText(_person.get_gender());
        }

        loadFacilitatorTypeDropdown(_view );
        loadLocationDropdown(_view );
        loadInstitutionDropdown(_view );

//        et_projected_date = (EditText) _view.findViewById(R.id.projected_date);
//        final SimpleDateFormat dateFormatter = new SimpleDateFormat(dbHelp.VMMC_DATE_FORMAT);
//            Calendar newCalendar = Calendar.getInstance();
//            DatePickerDialog hold_projected_date_picker_dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
//                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                    Calendar newDate = Calendar.getInstance();
//                    newDate.set(year, monthOfYear, dayOfMonth);
//                    et_projected_date.setText(dateFormatter.format(newDate.getTime()));
//                    Log.d(LOG, "EBF: onDateSet: " + et_projected_date.getText());
//                }
//            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
//
//        final DatePickerDialog projected_date_picker_dialog = hold_projected_date_picker_dialog;
//
//        et_projected_date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(LOG, "onClick: ");
//                projected_date_picker_dialog.show();
//            }
//        });

        Button btnClient = (Button) _view.findViewById(R.id.btnClient);
        btnClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(LOG, "Client button: " +
//                        _first_name.getText() + ", " + _last_name.getText() + ", " + _national_id.getText() + ", " + _phone.getText() +  ", " +
                      " <");

                Fragment fragment = getFragmentManager().findFragmentByTag(AddEditClientFragment.TAG);
                if (fragment == null) {
                    fragment = AddEditClientFragment.newInstance();
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, AddEditClientFragment.TAG).addToBackStack(MainActivity.currentFragmentId).commit();
                } else {
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, AddEditClientFragment.TAG).commit();
                }

//                Toast.makeText(getActivity(), "Person button", Toast.LENGTH_LONG).show();
            }
        });

        Button btnUpdateFacilitator = (Button) _view.findViewById(R.id.btnUpdate);
        btnUpdateFacilitator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                java.util.Date utilDate = cal.getTime();
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                _first_name = (TextView) _view.findViewById(R.id.first_name);
                String sFirstName = _first_name.getText().toString();
                _last_name = (TextView) _view.findViewById(R.id.last_name);
                String sLastName = _last_name.getText().toString();
                _national_id = (TextView) _view.findViewById(R.id.national_id);
                String sNationalId = _national_id.getText().toString();
                _phone = (TextView) _view.findViewById(R.id.phone_number);
                String sPhoneNumber = _phone.getText().toString();
                _note = (TextView) _view.findViewById(R.id.note);
                String sNote = _note.getText().toString();

                Spinner ftSpinner = (Spinner) _view.findViewById(R.id.facilitator_type);
//              String sStatusText  = sSpinner.getSelectedItem().toString();
                FacilitatorType _facilitatorType = dbHelp.getFacilitatorType( ftSpinner.getSelectedItem().toString());

                Spinner lSpinner = (Spinner) _view.findViewById(R.id.vmmclocation);
//              String sLocationText  = lSpinner.getSelectedItem().toString();
                VMMCLocation _location = dbHelp.getLocation( lSpinner.getSelectedItem().toString());

                EditText _institutioinEditText = (EditText) _view.findViewById(R.id.institution);
                Institution _institution = dbHelp.getInstitution( _institutioinEditText.getText().toString());

//                Log.d(LOG, "UpdateFacilitator button: " +
//                        _first_name.getText() + ", " + _last_name.getText() + ", " + _national_id.getText() + ", " + _phone.getText() +  ", " +
//                        _facilitatorType.get_id() +  ", "  + _location.get_id() + ", " + _institution.get_id() + " <");

                boolean complete = true;
                if(sFirstName.matches("") ) complete = false;
                if(sLastName.matches("") ) complete = false;
                if(sPhoneNumber.matches("") ) complete = false;
                if(_institution == null ) complete = false;

                if(complete) {

                    Facilitator lookupFacilitator = dbHelp.getFacilitator(sFirstName, sLastName, sNationalId, sPhoneNumber );

                    if (lookupFacilitator != null) {
                        lookupFacilitator.set_first_name(sFirstName);
                        lookupFacilitator.set_last_name(sLastName);
                        lookupFacilitator.set_national_id(sNationalId);
                        lookupFacilitator.set_phone(sPhoneNumber);
                        lookupFacilitator.set_facilitator_type_id(_facilitatorType.get_id());
                        lookupFacilitator.set_note(sNote);
                        lookupFacilitator.set_location_id(_location.get_id());
                        lookupFacilitator.set_institution_id(_institution.get_id());
                        Log.d(LOG, "UpdateFacilitator update: " +
                                _first_name.getText() + ", " + _last_name.getText() + ", " + _national_id.getText() + ", " + _phone.getText() + ", " + _facilitatorType.get_id() +  ", "  + _location.get_id() + ", " + _institution.get_id() + " <");
                        if(dbHelp.updateFacilitator(lookupFacilitator))
                            Toast.makeText(getActivity(), "Recruiter Updated", Toast.LENGTH_LONG).show();
                    } else {
                        Facilitator facilitator = new Facilitator();
                        facilitator.set_first_name(sFirstName.toString());
                        facilitator.set_last_name(sLastName);
                        facilitator.set_national_id(sNationalId);
                        facilitator.set_phone(sPhoneNumber);
                        facilitator.set_note(sNote);
                        facilitator.set_facilitator_type_id(_facilitatorType.get_id());
                        facilitator.set_location_id(_location.get_id());
                        facilitator.set_institution_id(_institution.get_id());
                        Log.d(LOG, "UpdateFacilitator add: " +
                                _first_name.getText() + ", " + _last_name.getText() + ", " + _national_id.getText() + ", " + _phone.getText() + ", " + _facilitatorType.get_id() +  ", "  + _location.get_id() + ", " + _institution.get_id() + " <");
                        if(dbHelp.addFacilitator(facilitator))
                            Toast.makeText(getActivity(), "Recruiter Saved", Toast.LENGTH_LONG).show();;
                    }
                } else {
                    Toast.makeText(getActivity(), "Must enter First Name, Last Name, Phone Number and Institution", Toast.LENGTH_LONG).show();
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
         _facilitator = null;
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
        _note = (TextView) _view.findViewById(R.id.note); _note.setText("");
//        _status_id = (TextView) _view.findViewById(R.id.status); _status_id.setText("");
//        _location_id= (TextView) _view.findViewById(R.id.vmmclocation); _location_id.setText("");
//        _institution_id = (TextView) _view.findViewById(R.id.institution); _institution_id.setText("");
//        _projected_date = (TextView) _view.findViewById(R.id.projected_date); _projected_date.setText("");
//        _dob = (TextView) _view.findViewById(R.id.dob); _dob.setText("");
//        _gender = (TextView) _view.findViewById(R.id.gender); _gender.setText("");

        if(_facilitator != null) {
            _first_name = (TextView) _view.findViewById(R.id.first_name);
            _first_name.setText(_facilitator.get_first_name());
            _last_name = (TextView) _view.findViewById(R.id.last_name);
            _last_name.setText(_facilitator.get_last_name());
            _national_id = (TextView) _view.findViewById(R.id.national_id);
            _national_id.setText(_facilitator.get_national_id());
            _phone = (TextView) _view.findViewById(R.id.phone_number);
            _phone.setText(_facilitator.get_phone());
            _note = (TextView) _view.findViewById(R.id.note);
            _note.setText(_facilitator.get_note());
//            _projected_date = (TextView) _view.findViewById(R.id.projected_date);
//            _projected_date.setText(_facilitator.get_projected_date());
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

    public void loadFacilitatorTypeDropdown(View view ) {
        Log.d(LOG, "loadFacilitatorTypeDropdown: " );

        final Spinner ftSpinner = (Spinner) view.findViewById(R.id.facilitator_type);
        final List<String> facilitatorTypeNames = dbHelp.getAllFacilitatorTypes();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, facilitatorTypeNames);
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
        ftSpinner.setAdapter(dataAdapter);
        _facilitatorType = dbHelp.getFacilitatorType(String.valueOf(_facilitator.get_facilitator_type_id()));
        if (_facilitatorType == null) {
            _facilitatorType = dbHelp.getFacilitatorType("3"); // MOBILIZER
        }
        String compareValue = _facilitatorType.get_name();
        if (!compareValue.equals(null)) {
            int spinnerPosition = dataAdapter.getPosition(compareValue);
            ftSpinner.setSelection(spinnerPosition);
        }

        ftSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String facilitatorTypeText  = ftSpinner.getSelectedItem().toString();
                _facilitatorType = dbHelp.getFacilitatorType(facilitatorTypeText);
                _facilitator.set_facilitator_type_id(_facilitatorType.get_id());
                Log.d(LOG, "facilitatorType: " + _facilitatorType.get_id() + _facilitatorType.get_name());
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
        _location = dbHelp.getLocation(String.valueOf(_facilitator.get_location_id()));
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
                _facilitator.set_location_id(_location.get_id());
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

        _institution = dbHelp.getInstitution(String.valueOf(_facilitator.get_institution_id()));
        if(_institution == null) {
            _institution = dbHelp.getInstitution("1");
        }
        _facilitator.set_institution_id(_institution.get_id());

        dropdown.setText(_institution.get_name());

        dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int index, long position) {
                _institution = dbHelp.getInstitution(dropdown.getText().toString());
                _facilitator.set_institution_id(_institution.get_id());
            }
        });
    }

    public void loadInstitutionDropdown1(View view ) {
        Log.d(LOG, "loadInstitutionDropdown: " );

        final Spinner iSpinner = (Spinner) view.findViewById(R.id.institution);
        final List<String> institutionNames = dbHelp.getAllInstitutionNames();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, institutionNames);

        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
        iSpinner.setAdapter(dataAdapter);
        _institution = dbHelp.getInstitution(String.valueOf(_facilitator.get_institution_id()));
        if (_institution == null) {
            _institution = dbHelp.getInstitution("3"); // Default
        }
        String compareValue = _institution.get_name();
        if (!compareValue.equals(null)) {
            int spinnerPosition = dataAdapter.getPosition(compareValue);
            iSpinner.setSelection(spinnerPosition);
        }

        iSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String institutionText  = iSpinner.getSelectedItem().toString();
                _institution = dbHelp.getInstitution(institutionText);
                _facilitator.set_institution_id(_institution.get_id());
                Log.d(LOG, "institution: " + _institution.get_id() + _institution.get_name());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(LOG, "spinner nothing selected");
            }
        });
    }
}

