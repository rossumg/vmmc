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
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import static org.itech.vmmc.R.id.btnEdit;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddEditUserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddEditUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditUserFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static String TAG = "addEditUserTag";
    public static String LOG = "gnr";

    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    //private String mParam1;
    //private String mParam2;

    private OnFragmentInteractionListener mListener;
    private AbsListView mListView;

    private EditText et_activity_date;

    private ListAdapter mAdapter;
    private DBHelper dbHelp;
    /**m@
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     //* @param param1 Parameter 1.
     //* @param param2 Parameter 2.
     * @return A new instance of fragment AddEditUserFragment.
     */
    // TODO: Rename and change types and number of parameters
    //  public static AddEditUserFragment newInstance(String param1, String param2) {
    public static AddEditUserFragment newInstance() {
        AddEditUserFragment fragment = new AddEditUserFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    // public AddEditUserFragment() {
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
        View view = inflater.inflate(R.layout.fragment_add_edit_user, container, false);

        getActivity().setTitle(getResources().getString(R.string.addEditUserTitle));

        loadUsernameDropdown(view);
        loadPhoneNumberDropdown(view);

        final ClearableAutoCompleteTextView usernameDropdown = (ClearableAutoCompleteTextView) view.findViewById(R.id.username);
        final ClearableAutoCompleteTextView phoneDropdown = (ClearableAutoCompleteTextView) view.findViewById(R.id.phone_number);
//        final ClearableAutoCompleteTextView nationalIdDropdown = (ClearableAutoCompleteTextView) view.findViewById(R.id.national_id);
//        final ClearableAutoCompleteTextView phoneNumberDropdown = (ClearableAutoCompleteTextView) view.findViewById(R.id.phone_number);

        Button btnDisplayUser = (Button) view.findViewById(R.id.btnDisplay);
        btnDisplayUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                java.util.Date utilDate = cal.getTime();
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                Log.d(LOG, "DisplayUser button: ");

                String paramUsername = "";
                String paramPhone = "";
                Log.d(LOG, "EditUser button name: " + usernameDropdown.getText().toString() + "<");

                if (usernameDropdown.getText().toString().equals("")) {
                    Log.d(LOG, "EditUser button name is null: ");
                } else {
                    //int UserId = new Integer(User.get_id());
                    //paramUserId = Integer.toString(UserId);

                    paramUsername = usernameDropdown.getText().toString();
                    String parts[] = {};
                    parts = paramUsername.split(", ",2);

                    switch( parts.length)  {
                        case 0: {
                            // add
                            break;
                        }
                        case 1: {
                            paramUsername = parts[0];
                            break;
                        }
                        case 2: {
                            paramUsername = parts[0];
                            paramPhone = parts[1];
                            break;
                        }
                    }

                    Log.d(LOG, "DisplayUser button name/all: " + paramUsername + paramPhone);
                }

//                boolean complete = false;
//                if (nationalIdDropdown.getText().toString().equals("")) {
//                    Log.d(LOG, "DisplayUser button nationalID is empty: ");
//                } else {
//                    paramNationalID = nationalIdDropdown.getText().toString();
//                    complete = true;
//                    Log.d(LOG, "DisplayUser button nationalID: " + paramNationalID);
//                }
//
//                if (phoneNumberDropdown.getText().toString().equals("")) {
//                    Log.d(LOG, "DisplayUser button phoneNumber is empty: ");
//                } else {
//                    paramPhoneNumber = phoneNumberDropdown.getText().toString();
//                    complete = true;
//                    Log.d(LOG, "DisplayUser button phoneNumber: " + paramPhoneNumber);
//                }

                Fragment fragment;
                fragment = DisplayFragment.newInstance("displayUser", paramUsername + ":" + paramPhone);
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, DisplayFragment.TAG).addToBackStack("DisplayUser").commit();
            }
        });

        Button btnEditUser = (Button) view.findViewById(btnEdit);
        btnEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                java.util.Date utilDate = cal.getTime();
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                Log.d(LOG, "EditUser button: ");

                String paramName = "";
                String paramActivityDate = "";
                Log.d(LOG, "EditUser button name: " + usernameDropdown.getText().toString() + "<");

                if (usernameDropdown.getText().toString().equals("")) {
                    Log.d(LOG, "EditUser button name is null: ");
                } else {
                    //int UserId = new Integer(User.get_id());
                    //paramUserId = Integer.toString(UserId);

                    paramName = usernameDropdown.getText().toString();
                    String parts[] = {};
                    parts = paramName.split(", ",2);

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
                            paramActivityDate = parts[1];
                            break;
                        }
                    }

                    Log.d(LOG, "EditUser button name/all: " + paramName + paramActivityDate);
                }

                boolean complete = true;
//                if (nationalIdDropdown.getText().toString().equals("")) {
//                    Log.d(LOG, "EditUser button nationalID is empty: ");
//                } else {
//                    paramNationalID = nationalIdDropdown.getText().toString();
//                    complete = true;
//                    Log.d(LOG, "EditUser button nationalID: " + paramNationalID);
//                }
//
//                if (phoneNumberDropdown.getText().toString().equals("")) {
//                    Log.d(LOG, "EditUser button phoneNumber is empty: ");
//                } else {
//                    paramPhoneNumber = phoneNumberDropdown.getText().toString();
//                    complete = true;
//                    Log.d(LOG, "EditUser button phoneNumber: " + paramPhoneNumber);
//                }

                if (complete || !paramName.toString().equals("")  ) {
                    Fragment fragment;
                    fragment = EditUserFragment.newInstance("editUser", paramName + ":" + paramActivityDate);
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, EditUserFragment.TAG).addToBackStack("EditUser").commit();
                } else {
                    Toast.makeText(getActivity(), "Must enter Name", Toast.LENGTH_LONG).show();
                }
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

    private User user;
    public void loadUsernameDropdown(View view) {

        List<String> UserIDs = dbHelp.getAllUserIDs();
        // convert to array
        String[] stringArrayUserID = new String[ UserIDs.size() ];
        UserIDs.toArray(stringArrayUserID);

        final ClearableAutoCompleteTextView dropdown = (ClearableAutoCompleteTextView) view.findViewById(R.id.username);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stringArrayUserID);
        dropdown.setThreshold(1);
        dropdown.setAdapter(dataAdapter);

        dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int index, long position) {
                String nameText = dropdown.getText().toString();
                String parts[] = {};
                parts = nameText.split(", ");

                String name = parts[0].trim();
                String activity_date =  parts[1].trim();
                Log.d(LOG, "Group Activity selected: " + name + "." + activity_date + "." );


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

        List<String> phoneNumbers = dbHelp.getAllUserPhoneNumbers();

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
}
