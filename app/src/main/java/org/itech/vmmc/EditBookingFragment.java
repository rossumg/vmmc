package org.itech.vmmc;

import android.app.Activity;
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
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


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
    private static TextView _first_name;
    private static TextView _last_name;
    private static TextView _national_id;
    private static TextView _phone;
    private static TextView _location_id;
    private static TextView _projected_date;
    private static TextView _actual_date;

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
        String projectedDate = "";

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

        Log.d(LOG, "EBF First: " + firstName);
        Log.d(LOG, "EBF Last: " + lastName);
        Log.d(LOG, "EBF NationalId: " + nationalId);
        Log.d(LOG, "EBF PhoneNumber: " + phoneNumber);
        Log.d(LOG, "EBF ProjectedDate: " + projectedDate);

        dbHelp = new DBHelper(getActivity());
        if (!nationalId.equals("") || !phoneNumber.equals("")) {
            //_booking = dbHelp.getBooking(firstName, lastName, nationalId, phoneNumber, projectedDate);
            _booking = dbHelp.getBooking(nationalId, phoneNumber, projectedDate);
        }
        if(_booking != null) {
            Log.d(LOG, "EBF _booking != null ");
            //Log.d(LOG, "EBF _booking != null " + _booking.get_first_name());
        } else {
            Log.d(LOG, "EBF _booking is equal null ");
            _booking = new Booking();
            _booking.set_first_name(firstName);
            _booking.set_last_name(lastName);
            _booking.set_national_id(nationalId);
            _booking.set_phone(phoneNumber);
            _booking.set_projected_date(projectedDate);
            //Log.d(LOG, "EBF _booking is equal null " + _booking.get_first_name());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_edit_booking, container, false);

        getActivity().setTitle(getResources().getString(R.string.editBookingTitle));
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
//            _dob = (TextView) _view.findViewById(R.id.dob);
//            _dob.setText(_person.get_dob());
//            _gender = (TextView) _view.findViewById(R.id.gender);
//            _gender.setText(_person.get_gender());
        }

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
                _projected_date= (TextView) _view.findViewById(R.id.projected_date);

                Log.d(LOG, "UpdateBooking button: " +
                        _first_name.getText() + ", " + _last_name.getText() + ", " + _national_id.getText() + ", " + _phone.getText() + " <");

                String sFirstName = _first_name.getText().toString();
                String sLastName = _last_name.getText().toString();
                String sNationalId = _national_id.getText().toString();
                String sPhoneNumber = _phone.getText().toString();
                String sProjectedDate = _projected_date.getText().toString();
//                DateFormat df = new android.text.format.DateFormat();
//                String dProjectedDate = df.format(VMMC_DATE_FORMAT, projected_date);
//                String sDOB = _dob.getText().toString();
//                String sGender = _gender.getText().toString();

                Log.d(LOG, "UpdateBooking button2: " +
                        _first_name.getText() + ", " + _last_name.getText() + ", " + _national_id.getText() + ", " + _phone.getText() + ", " + _projected_date.getText() +" <");

                boolean complete = true;
                if(sFirstName.matches("") ) complete = false;
                if(sLastName.matches("") ) complete = false;
                if(sPhoneNumber.matches("") ) complete = false;
                if(sProjectedDate.matches("") ) complete = false;

                if(complete) {

                    Booking lookupBooking = dbHelp.getBooking(sFirstName, sLastName, sNationalId, sPhoneNumber, sProjectedDate);

                    if (lookupBooking != null) {
                        lookupBooking.set_first_name(sFirstName);
                        lookupBooking.set_last_name(sLastName);
                        lookupBooking.set_national_id(sNationalId);
                        lookupBooking.set_phone(sPhoneNumber);
                        lookupBooking.set_projected_date(sProjectedDate);
//                        lookupPerson.set_gender(sGender);
                        Log.d(LOG, "UpdateBooking update: " +
                                _first_name.getText() + ", " + _last_name.getText() + ", " + _national_id.getText() + ", " + _phone.getText() + ", " + _projected_date.getText() +" <");
                        if(dbHelp.updateBooking(lookupBooking))
                            Toast.makeText(getActivity(), "Booking Updated", Toast.LENGTH_LONG).show();;
                    } else {
                        Booking booking = new Booking();
                        booking.set_first_name(sFirstName.toString());
                        booking.set_last_name(sLastName);
                        booking.set_national_id(sNationalId);
                        booking.set_phone(sPhoneNumber);
                        booking.set_projected_date(sProjectedDate);
//                        person.set_dob(sDOB);
//                        person.set_gender(sGender);
                        Log.d(LOG, "UpdateBooking add: " +
                                _first_name.getText() + ", " + _last_name.getText() + ", " + _national_id.getText() + ", " + _phone.getText() + ", " + _projected_date.getText() +" <");
                        if(dbHelp.addBooking(booking))
                            Toast.makeText(getActivity(), "Booking Saved", Toast.LENGTH_LONG).show();;
                    }

                    //Toast.makeText(getActivity(), "Person Saved", Toast.LENGTH_LONG).show();
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



    public void loadAssessmentTypeDropdown(View view) {
        Spinner dropdown = (Spinner) view.findViewById(R.id.assessment_type);
        dropdown.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                Log.d(LOG, "assessment_type item selected: " + item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(LOG, "spinner nothing selected");
            }
        });


//        List<String> assessmentTypes = dbHelp.getAllAssessmentTypes();
//
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, assessmentTypes);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        dropdown.setAdapter(dataAdapter);

    }

}
