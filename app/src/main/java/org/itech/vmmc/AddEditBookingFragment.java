package org.itech.vmmc;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddEditBookingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddEditBookingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditBookingFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static String TAG = "addEditBookingTag";
    public static String LOG = "gnr";

    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    //private String mParam1;
    //private String mParam2;

    private OnFragmentInteractionListener mListener;
    private AbsListView mListView;

    private ListAdapter mAdapter;
    private DBHelper dbHelp;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     //* @param param1 Parameter 1.
     //* @param param2 Parameter 2.
     * @return A new instance of fragment AddEditBookingFragment.
     */
    // TODO: Rename and change types and number of parameters
    //  public static AddEditBookingFragment newInstance(String param1, String param2) {
    public static AddEditBookingFragment newInstance() {
        AddEditBookingFragment fragment = new AddEditBookingFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    // public AddEditBookingFragment() {
    // Required empty public constructor
    //}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }

        dbHelp = new DBHelper(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_edit_booking, container, false);

        getActivity().setTitle(getResources().getString(R.string.addEditBookingTitle));

        loadBookingNameDropdown(view);
//        loadAssessmentTypeDropdown(view);
        loadNationalIDDropdown(view);
//        loadFacilityDropdown(view);
        loadPhoneNumberDropdown(view);

        final ClearableAutoCompleteTextView nameDropdown = (ClearableAutoCompleteTextView) view.findViewById(R.id.name);
        final ClearableAutoCompleteTextView nationalIdDropdown = (ClearableAutoCompleteTextView) view.findViewById(R.id.national_id);
        final ClearableAutoCompleteTextView phoneNumberDropdown = (ClearableAutoCompleteTextView) view.findViewById(R.id.phone_number);
        final EditText projectedDate = (EditText) view.findViewById(R.id.projected_date);

        Button btnEditBooking = (Button) view.findViewById(R.id.btnEditBooking);
        btnEditBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                java.util.Calendar cal = java.util.Calendar.getInstance();
                java.util.Date utilDate = cal.getTime();
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                Log.d(LOG, "EditBooking button: ");

                String paramName = "";
                String paramNationalID = "";
                String paramPhoneNumber = "";
                String paramProjectedDate = "";
                Log.d(LOG, "EditBooking button name: " + nameDropdown.getText().toString() + "<");

                if (nameDropdown.getText().toString().equals("")) {
                    Log.d(LOG, "EditBooking button name is null: ");
                } else {
                    //int bookingId = new Integer(booking.get_id());
                    //paramBookingId = Integer.toString(bookingId);

                    paramName = nameDropdown.getText().toString();
                    String parts[] = {};
                    parts = paramName.split(", ",4);

                    switch( parts.length)  {
                        case 0: {
                            // add
                            break;
                        }
                        case 1: {
                            paramName = parts[0];
                            break;
                        }
                        case 2: {
                            paramName = parts[0];
                            paramNationalID = parts[1];
                            break;
                        }
                        case 3: {
                            paramName = parts[0];
                            paramNationalID = parts[1];
                            paramPhoneNumber = parts[2];
                            break;
                        }
                        case 4: {
                            paramName = parts[0];
                            paramNationalID = parts[1];
                            paramPhoneNumber = parts[2];
                            paramProjectedDate = parts[3];
                            break;
                        }
                    }

                    Log.d(LOG, "EditBooking button name/all: " + paramName + paramNationalID + paramPhoneNumber + paramProjectedDate);
                }

                if (nationalIdDropdown.getText().toString().equals("")) {
                    Log.d(LOG, "EditBooking button nationalID is empty: ");
                } else {
                    paramNationalID = nationalIdDropdown.getText().toString();
                    Log.d(LOG, "EditBooking button nationalID: " + paramNationalID);
                }

                if (phoneNumberDropdown.getText().toString().equals("")) {
                    Log.d(LOG, "EditBooking button phoneNumber is empty: ");
                } else {
                    paramPhoneNumber = phoneNumberDropdown.getText().toString();
                    Log.d(LOG, "EditBooking button phoneNumber: " + paramPhoneNumber);
                }

                if (projectedDate.getText().toString().equals("")) {
                    Log.d(LOG, "EditBooking button projectedDate is empty: ");
                } else {
                    paramProjectedDate = projectedDate.getText().toString();
                    Log.d(LOG, "EditBooking button projectedDate: " + paramProjectedDate);
                }


                Fragment fragment;
                fragment = EditBookingFragment.newInstance("editBooking", paramName + ":" + paramNationalID + ":" + paramPhoneNumber + ":" + paramProjectedDate);
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, EditBookingFragment.TAG).addToBackStack("EditBooking").commit();
            }
        });

        // Inflate the layout for this fragment
        return view;
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

    private Booking booking;
    public void loadBookingNameDropdown(View view) {

        List<String> bookingIDs = dbHelp.getAllPersonIDs();
        // convert to array
        String[] stringArrayBookingID = new String[ bookingIDs.size() ];
        bookingIDs.toArray(stringArrayBookingID);

        final ClearableAutoCompleteTextView dropdown = (ClearableAutoCompleteTextView) view.findViewById(R.id.name);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stringArrayBookingID);
        dropdown.setThreshold(1);
        dropdown.setAdapter(dataAdapter);

        dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int index, long position) {
                String nameText = dropdown.getText().toString();
                String parts[] = {};
                parts = nameText.split(", ");

                String name = parts[0].trim();
                String national_id =  parts[1].trim();
                String phone_number = parts[2].trim();
//                String projected_date = parts[3].trim();
                Log.d(LOG, "booking person selected: " + name + "." + national_id + "." + phone_number + "." );

//                booking = dbHelp.getBooking(national_id, phone_number, projected_date);
//                Log.d(LOG, "booking_id selected: " + booking.get_id());

            }
        });
    }

    public void loadNationalIDDropdown(View view) {
        String[] nationalIDs = dbHelp.getAllPersonNationalIDs();

        final ClearableAutoCompleteTextView dropdown = (ClearableAutoCompleteTextView) view.findViewById(R.id.national_id);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nationalIDs);
        dropdown.setThreshold(1);
        dropdown.setAdapter(dataAdapter);

        dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int index, long position) {
                Log.d(LOG, "nationalID selected: " + dropdown.getText());
            }
        });
    }

    public void loadPhoneNumberDropdown(View view) {
        String[] phoneNumbers = dbHelp.getAllPersonPhoneNumbers();

        final ClearableAutoCompleteTextView dropdown = (ClearableAutoCompleteTextView) view.findViewById(R.id.phone_number);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, phoneNumbers);
        dropdown.setThreshold(1);
        dropdown.setAdapter(dataAdapter);
        // dropdown.setTextSize(R.dimen.font_size_medium);
        //dropdown.setTextSize(TypedValue.COMPLEX_UNIT_DIP,25);

        dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int index, long position) {
                Log.d(LOG, "phoneNumber selected: " + dropdown.getText());
            }
        });

    }

    private Assessments assessment = null;
    public void loadAssessmentTypeDropdown(View view) {
        final Spinner dropdown = (Spinner) view.findViewById(R.id.assessment_type);
        dropdown.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String assessmentTypeText  = dropdown.getSelectedItem().toString();
                Log.d(LOG, "assessmentTypeText: " + assessmentTypeText + "<");
                // because of the all option, not available in create
                if(!assessmentTypeText.equals("")) {
                    assessment = dbHelp.getAssessments(assessmentTypeText);
                } else assessment = null;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(LOG, "spinner nothing selected");
            }
        });

        List<String> assessmentTypes = dbHelp.getAllAssessmentTypes();
        String all = "";
        assessmentTypes.add(0,all);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, assessmentTypes);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(dataAdapter);

    }
}
