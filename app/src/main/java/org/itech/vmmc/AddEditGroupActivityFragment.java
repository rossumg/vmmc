package org.itech.vmmc;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static org.itech.vmmc.R.id.btnEdit;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddEditGroupActivityFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddEditGroupActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditGroupActivityFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static String TAG = "addEditGroupActivityTag";
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
     * @return A new instance of fragment AddEditGroupActivityFragment.
     */
    // TODO: Rename and change types and number of parameters
    //  public static AddEditGroupActivityFragment newInstance(String param1, String param2) {
    public static AddEditGroupActivityFragment newInstance() {
        AddEditGroupActivityFragment fragment = new AddEditGroupActivityFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    // public AddEditGroupActivityFragment() {
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
        View view = inflater.inflate(R.layout.fragment_add_edit_group_activity, container, false);

        getActivity().setTitle(getResources().getString(R.string.addEditGroupActivityTitle));

        loadGroupActivityNameDropdown(view);
//        loadAssessmentTypeDropdown(view);
//        loadNationalIDDropdown(view);
//        loadFacilityDropdown(view);
//        loadPhoneNumberDropdown(view);

        final ClearableAutoCompleteTextView nameDropdown = (ClearableAutoCompleteTextView) view.findViewById(R.id.name);
//        final ClearableAutoCompleteTextView nationalIdDropdown = (ClearableAutoCompleteTextView) view.findViewById(R.id.national_id);
//        final ClearableAutoCompleteTextView phoneNumberDropdown = (ClearableAutoCompleteTextView) view.findViewById(R.id.phone_number);
        final EditText activityDate = (EditText) view.findViewById(R.id.activity_date);

        et_activity_date = (EditText) view.findViewById(R.id.activity_date);
        final SimpleDateFormat dateFormatter = new SimpleDateFormat(DBHelper.VMMC_DATE_FORMAT);
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog hold_activity_date_picker_dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                et_activity_date.setText(dateFormatter.format(newDate.getTime()));
                Log.d(LOG, "AEBF: onDateSet: " + et_activity_date.getText());
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

        Button btnDisplayGroupActivity = (Button) view.findViewById(R.id.btnDisplay);
        btnDisplayGroupActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                java.util.Date utilDate = cal.getTime();
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                Log.d(LOG, "DisplayGroupActivity button: ");

                String paramName = "";
                String paramActivityDate = "";
                Log.d(LOG, "EditGroupActivity button name: " + nameDropdown.getText().toString() + "<");

                if (nameDropdown.getText().toString().equals("")) {
                    Log.d(LOG, "EditGroupActivity button name is null: ");
                } else {
                    //int GroupActivityId = new Integer(GroupActivity.get_id());
                    //paramGroupActivityId = Integer.toString(GroupActivityId);

                    paramName = nameDropdown.getText().toString();
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

                    Log.d(LOG, "DisplayGroupActivity button name/all: " + paramName + paramActivityDate);
                }

//                boolean complete = false;
//                if (nationalIdDropdown.getText().toString().equals("")) {
//                    Log.d(LOG, "DisplayGroupActivity button nationalID is empty: ");
//                } else {
//                    paramNationalID = nationalIdDropdown.getText().toString();
//                    complete = true;
//                    Log.d(LOG, "DisplayGroupActivity button nationalID: " + paramNationalID);
//                }
//
//                if (phoneNumberDropdown.getText().toString().equals("")) {
//                    Log.d(LOG, "DisplayGroupActivity button phoneNumber is empty: ");
//                } else {
//                    paramPhoneNumber = phoneNumberDropdown.getText().toString();
//                    complete = true;
//                    Log.d(LOG, "DisplayGroupActivity button phoneNumber: " + paramPhoneNumber);
//                }

                Fragment fragment;
                fragment = DisplayFragment.newInstance("displayGroupActivity", paramName + ":" + paramActivityDate);
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, DisplayFragment.TAG).addToBackStack("DisplayGroupActivity").commit();
            }
        });

        Button btnEditGroupActivity = (Button) view.findViewById(btnEdit);
        btnEditGroupActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                java.util.Date utilDate = cal.getTime();
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                Log.d(LOG, "EditGroupActivity button: ");

                String paramName = "";
                String paramActivityDate = "";
                Log.d(LOG, "EditGroupActivity button name: " + nameDropdown.getText().toString() + "<");

                if (nameDropdown.getText().toString().equals("")) {
                    Log.d(LOG, "EditGroupActivity button name is null: ");
                } else {
                    //int GroupActivityId = new Integer(GroupActivity.get_id());
                    //paramGroupActivityId = Integer.toString(GroupActivityId);

                    paramName = nameDropdown.getText().toString();
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

                    Log.d(LOG, "EditGroupActivity button name/all: " + paramName + paramActivityDate);
                }

                boolean complete = true;
//                if (nationalIdDropdown.getText().toString().equals("")) {
//                    Log.d(LOG, "EditGroupActivity button nationalID is empty: ");
//                } else {
//                    paramNationalID = nationalIdDropdown.getText().toString();
//                    complete = true;
//                    Log.d(LOG, "EditGroupActivity button nationalID: " + paramNationalID);
//                }
//
//                if (phoneNumberDropdown.getText().toString().equals("")) {
//                    Log.d(LOG, "EditGroupActivity button phoneNumber is empty: ");
//                } else {
//                    paramPhoneNumber = phoneNumberDropdown.getText().toString();
//                    complete = true;
//                    Log.d(LOG, "EditGroupActivity button phoneNumber: " + paramPhoneNumber);
//                }

                if (complete || !paramName.toString().equals("")  ) {
                    Fragment fragment;
                    fragment = EditGroupActivityFragment.newInstance("editGroupActivity", paramName + ":" + paramActivityDate);
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, EditGroupActivityFragment.TAG).addToBackStack("EditGroupActivity").commit();
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
        void onFragmentInteraction(int position);
    }

    public void onResume() {
        super.onResume();
        Log.d(LOG, "Group Activity fragment:onResume: pop " );

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    Log.d(LOG, "Group Activity fragment:onResume: pop: handle back " );
                    getFragmentManager().popBackStack();
                    Fragment fragment = getFragmentManager().findFragmentByTag(ActionFragment.TAG);
                    if (fragment == null) {
                        fragment = ActionFragment.newInstance("main", "");
                        getFragmentManager().beginTransaction().replace(R.id.container, fragment, ActionFragment.TAG).addToBackStack(MainActivity.currentFragmentId).commit();
                    } else {
                        getFragmentManager().beginTransaction().replace(R.id.container, fragment, ActionFragment.TAG).commit();
                    }
                    MainActivity.currentFragmentId = "Action";
                    return true;
                }
                return false;
            }
        });
    }

    private GroupActivity groupActivity;
    public void loadGroupActivityNameDropdown(View view) {

        List<String> GroupActivityIDs = dbHelp.getAllGroupActivityIDs();
        // convert to array
        String[] stringArrayGroupActivityID = new String[ GroupActivityIDs.size() ];
        GroupActivityIDs.toArray(stringArrayGroupActivityID);

        final ClearableAutoCompleteTextView dropdown = (ClearableAutoCompleteTextView) view.findViewById(R.id.name);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stringArrayGroupActivityID);
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
}
