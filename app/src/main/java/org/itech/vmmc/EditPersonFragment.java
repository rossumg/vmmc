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
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static org.itech.vmmc.R.id.dob;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditPersonFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditPersonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditPersonFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static String TAG = "EditPersonTag";
    public static String LOG = "gnr";
    public Context _context;

    private static final String ARG_EDIT_PERSON_PARAM = "EXTRA_EDIT_PERSON_PARAM";
    private static final String ARG_EDIT_PERSON_RECORD_PARAM = "EXTRA_EDIT_PERSON_RECORD_PARAM";

    View _view;

    // private String mEditPersonParam;
    private static String _editPersonRecordParam;
    private static Person _person;
    private static TextView _first_name;
    private static TextView _last_name;
    private static TextView _national_id;
    private static TextView _address;
    private static TextView _phone_number;
    private static TextView _dob;
    private static TextView _gender;
    private static RadioGroup _rg_gender;
    private static RadioButton _rb_gender;
    private static RadioButton _rb_gender_male;
    private static RadioButton _rb_gender_female;

    private EditText et_dob;

    private static OnFragmentInteractionListener mListener;
    private DBHelper dbHelp;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     //* @param param1 Parameter 1.
     //* @param param2 Parameter 2.
     * @return A new instance of fragment CreatePersonFragment.
     */
    // TODO: Rename and change types and number of parameters
    //  public static CreateFragment newInstance(String param1, String param2) {
    public static EditPersonFragment newInstance(String mEditPersonParam, String mEditPersonRecordParam) {
        EditPersonFragment fragment = new EditPersonFragment();
        Bundle bundle = new Bundle();
        bundle.putString("ARG_EDIT_PERSON_PARAM", mEditPersonParam);
        bundle.putString("ARG_EDIT_PERSON_RECORD_PARAM", mEditPersonRecordParam);

        _editPersonRecordParam = mEditPersonRecordParam;

        fragment.setArguments(bundle);
        return fragment;
    }

    // public CreatePersonFragment() {
    // Required empty public constructor
    //}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _editPersonRecordParam = getArguments().getString("ARG_EDIT_PERSON_RECORD_PARAM");
            Log.d(LOG, "editPersonFragment onCreate editPersonParam: ");
            Log.d(LOG, "editPersonFragment onCreate editPersonRecordParam: " + _editPersonRecordParam.toString() + "<");
        }

        String params = _editPersonRecordParam.toString();
        String parts[] = {};
        parts = params.split(":");

        String name = "";
        String firstName = "";
        String lastName = "";
        String nationalId = "";
        String phoneNumber = "";

        switch( parts.length)  {
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
        }

        String nameParts[] = {};
        nameParts = name.split(" ");
        switch( nameParts.length )  {
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

        Log.d(LOG, "First: " + firstName);
        Log.d(LOG, "Last: " + lastName);
        Log.d(LOG, "NationalId: " + nationalId);
        Log.d(LOG, "PhoneNumber: " + phoneNumber);

        dbHelp = new DBHelper(getActivity());
        if (!firstName.equals("") && !lastName.equals("") && !phoneNumber.equals("")) {
            _person = dbHelp.getPerson(firstName, lastName, phoneNumber);
        } else if (!nationalId.equals("") || !phoneNumber.equals("")) {
            _person = dbHelp.getPerson(nationalId, phoneNumber);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_edit_person, container, false);

        getActivity().setTitle(getResources().getString(R.string.editPersonTitle));
        if(_person != null) {
            _first_name = (TextView) _view.findViewById(R.id.first_name);
            _first_name.setText(_person.get_first_name());
            _last_name = (TextView) _view.findViewById(R.id.last_name);
            _last_name.setText(_person.get_last_name());
            _national_id = (TextView) _view.findViewById(R.id.national_id);
            _national_id.setText(_person.get_national_id());
            _address = (TextView) _view.findViewById(R.id.address);
            _address.setText(_person.get_address());
            _phone_number = (TextView) _view.findViewById(R.id.phone_number);
            _phone_number.setText(_person.get_phone());
            _dob = (TextView) _view.findViewById(dob);
            _dob.setText(_person.get_dob());
        }

        loadGenderRadio(_view);

        et_dob = (EditText) _view.findViewById(R.id.dob);
        final SimpleDateFormat dateFormatter = new SimpleDateFormat(dbHelp.VMMC_DATE_FORMAT);
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

        Button btnUpdatePerson = (Button) _view.findViewById(R.id.btnUpdatePerson);
        btnUpdatePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                java.util.Calendar cal = java.util.Calendar.getInstance();
                java.util.Date utilDate = cal.getTime();
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                _first_name = (TextView) _view.findViewById(R.id.first_name);
                _last_name = (TextView) _view.findViewById(R.id.last_name);
                _phone_number = (TextView) _view.findViewById(R.id.phone_number);

                Log.d(LOG, "UpdatePerson button: " +
                        _first_name.getText() + _last_name.getText() + _phone_number.getText() + "<");

                String sFirstName = _first_name.getText().toString();
                String sLastName = _last_name.getText().toString();
                String sNationalId = _national_id.getText().toString();
                String sAddress = _address.getText().toString();
                String sPhoneNumber = _phone_number.getText().toString();
                String sDOB = _dob.getText().toString();
                String sGender = _person.get_gender();

                Log.d(LOG, "UpdatePerson button2: " +
                        sFirstName + sLastName + sPhoneNumber + sDOB + "<");

                boolean complete = true;
                if(sFirstName.matches("") ) complete = false;
                if(sLastName.matches("") ) complete = false;
                if(sPhoneNumber.matches("") ) complete = false;

                if(complete) {

                    Person lookupPerson = dbHelp.getPerson(sFirstName, sLastName, sPhoneNumber);

                    if (lookupPerson != null) {
                        lookupPerson.set_first_name(sFirstName);
                        lookupPerson.set_last_name(sLastName);
                        lookupPerson.set_national_id(sNationalId);
                        lookupPerson.set_address(sAddress);
                        lookupPerson.set_phone(sPhoneNumber);
                        lookupPerson.set_dob(sDOB);
                        lookupPerson.set_gender(sGender);
                        Log.d(LOG, "UpdatePerson update: " + lookupPerson.get_gender());
                        if(dbHelp.updatePerson(lookupPerson))
                            Toast.makeText(getActivity(), "Person Updated", Toast.LENGTH_LONG).show();;
                    } else {
                        Person person = new Person();
                        person.set_first_name(sFirstName.toString());
                        person.set_last_name(sLastName);
                        person.set_national_id(sNationalId);
                        person.set_address(sAddress);
                        person.set_phone(sPhoneNumber);
                        person.set_dob(sDOB);
                        person.set_gender(sGender);
                        Log.d(LOG, "UpdatePerson add: " + person.get_gender());
                        if(dbHelp.addPerson(person))
                            Toast.makeText(getActivity(), "Person Saved", Toast.LENGTH_LONG).show();;
                    }

                    //Toast.makeText(getActivity(), "Person Saved", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Must enter First Name, Last Name and Phone Number", Toast.LENGTH_LONG).show();
                }
            }
        });

        //loadPersonIDDropdown(view);
        //loadAssessmentTypeDropdown(view);

        //nameView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        // Inflate the layout for this fragment
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
         _person = null;
//        _first_name = (TextView) _view.findViewById(R.id.first_name); _first_name.setText("first");
//        _last_name = (TextView) _view.findViewById(R.id.last_name); _last_name.setText("last");
//        _national_id = (TextView) _view.findViewById(R.id.national_id); _national_id.setText("");
//        _address = (TextView) _view.findViewById(R.id.address); _address.setText("");
//        _phone_number = (TextView) _view.findViewById(R.id.phone_number); _phone_number.setText("");
//        _dob = (TextView) _view.findViewById(R.id.dob); _dob.setText("");
//        _gender = (TextView) _view.findViewById(R.id.gender); _gender.setText("");
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener = null;
        Log.d(LOG, "Resume: ");
        _first_name = (TextView) _view.findViewById(R.id.first_name); _first_name.setText("");
        _last_name = (TextView) _view.findViewById(R.id.last_name); _last_name.setText("");
        _national_id = (TextView) _view.findViewById(R.id.national_id); _national_id.setText("");
        _address = (TextView) _view.findViewById(R.id.address); _address.setText("");
        _phone_number = (TextView) _view.findViewById(R.id.phone_number); _phone_number.setText("");
        _dob = (TextView) _view.findViewById(dob); _dob.setText("");


        if(_person != null) {
            _first_name = (TextView) _view.findViewById(R.id.first_name);
            _first_name.setText(_person.get_first_name());
            _last_name = (TextView) _view.findViewById(R.id.last_name);
            _last_name.setText(_person.get_last_name());
            _national_id = (TextView) _view.findViewById(R.id.national_id);
            _national_id.setText(_person.get_national_id());
            _address = (TextView) _view.findViewById(R.id.address);
            _address.setText(_person.get_address());
            _phone_number = (TextView) _view.findViewById(R.id.phone_number);
            _phone_number.setText(_person.get_phone());
            _dob = (TextView) _view.findViewById(dob);
            _dob.setText(_person.get_dob());
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

    public void loadGenderRadio(View view ) {
        Log.d(LOG, "loadRegionRadio: ");

        _rg_gender = (RadioGroup) _view.findViewById(R.id.gender_radio_group);
        _rb_gender_male = (RadioButton) _view.findViewById(R.id.radioButtonMale);
        _rb_gender_female = (RadioButton) _view.findViewById(R.id.radioButtonFemale);

        if (_person.get_gender().equals("M") ) {
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
                _person.set_gender("M");
            }
        });

        _rb_gender_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId=_rg_gender.getCheckedRadioButtonId();
                _rb_gender=(RadioButton) _view.findViewById(selectedId);
                Log.d(LOG, "rb_gender: " + _rb_gender.getText() );
                _person.set_gender("F");
            }
        });
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
}
