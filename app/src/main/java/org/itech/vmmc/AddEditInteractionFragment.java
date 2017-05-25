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
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddEditInteractionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddEditInteractionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditInteractionFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static String TAG = "addEditInteractionTag";
    public static String LOG = "gnr";

    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    //private String mParam1;
    //private String mParam2;

    private OnFragmentInteractionListener mListener;
    private AbsListView mListView;

    private EditText et_interaction_date;
    private EditText et_followup_date;

    private ListAdapter mAdapter;
    private DBHelper dbHelp;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     //* @param param1 Parameter 1.
     //* @param param2 Parameter 2.
     * @return A new instance of fragment AddEditInteractionFragment.
     */
    // TODO: Rename and change types and number of parameters
    //  public static AddEditInteractionFragment newInstance(String param1, String param2) {
    public static AddEditInteractionFragment newInstance() {
        AddEditInteractionFragment fragment = new AddEditInteractionFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    // public AddEditInteractionFragment() {
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
        View view = inflater.inflate(R.layout.fragment_add_edit_interaction, container, false);

        getActivity().setTitle(getResources().getString(R.string.addEditInteractionTitle));

        loadFacilitatorAutoComplete(view);
        loadPersonAutoComplete(view);
//        loadInteractionTypeDropdown(view);

        final ClearableAutoCompleteTextView facilitatorAutoComplete = (ClearableAutoCompleteTextView) view.findViewById(R.id.facilitator);
        final ClearableAutoCompleteTextView personAutoComplete = (ClearableAutoCompleteTextView) view.findViewById(R.id.person);
        final Spinner itSpinner = (Spinner) view.findViewById(R.id.interaction_type);
        final EditText interactionDate = (EditText) view.findViewById(R.id.interaction_date);
        final EditText followupDate = (EditText) view.findViewById(R.id.followup_date);
        final EditText note = (EditText) view.findViewById(R.id.note);

//        Spinner iSpinner = (Spinner) view.findViewById(R.id.interaction_type);
//        final InteractionType _interactionType = dbHelp.getInteractionType( iSpinner.getSelectedItem().toString());

        et_interaction_date = (EditText) view.findViewById(R.id.interaction_date);
        SimpleDateFormat dateFormatter = new SimpleDateFormat(dbHelp.VMMC_DATE_FORMAT);
        Calendar newCalendar = Calendar.getInstance();
        final SimpleDateFormat finalDateFormatter1 = dateFormatter;
        DatePickerDialog hold_interaction_date_picker_dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                et_interaction_date.setText(finalDateFormatter1.format(newDate.getTime()));
                Log.d(LOG, ": onDateSet: " + et_interaction_date.getText());
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        final DatePickerDialog interaction_date_picker_dialog = hold_interaction_date_picker_dialog;

        et_interaction_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG, "onClick: ");
                interaction_date_picker_dialog.show();
            }
        });


        et_followup_date = (EditText) view.findViewById(R.id.followup_date);
        dateFormatter = new SimpleDateFormat(dbHelp.VMMC_DATE_FORMAT);
        newCalendar = Calendar.getInstance();
        final SimpleDateFormat finalDateFormatter = dateFormatter;
        DatePickerDialog hold_followup_date_picker_dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                et_followup_date.setText(finalDateFormatter.format(newDate.getTime()));
                Log.d(LOG, ": onDateSet: " + et_followup_date.getText());
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


        Button btnDisplayInteraction = (Button) view.findViewById(R.id.btnDisplayInteraction);
        btnDisplayInteraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                java.util.Date utilDate = cal.getTime();
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                Log.d(LOG, "DisplayInteraction button: ");

                String facName = "";
                String facNationalID = "";
                String facPhoneNumber = "";
                if (facilitatorAutoComplete.getText().toString().equals("")) {
                    Log.d(LOG, "EditInteraction display: facilitator is null: ");
                } else {
                    facName = facilitatorAutoComplete.getText().toString();
                    String parts[] = {};
                    parts = facName.split(", ",4);

                    switch( parts.length)  {
                        case 0: {
                            // add
                            break;
                        }
                        case 1: {
                            facName = parts[0];
                            break;
                        }
                        case 2: {
                            facName = parts[0];
                            facNationalID = parts[1];
                            break;
                        }
                        case 3: {
                            facName = parts[0];
                            facNationalID = parts[1];
                            facPhoneNumber = parts[2];
                            break;
                        }
                    }

                    Log.d(LOG, "DisplayInteraction display facilitator: " + facName + facNationalID + facPhoneNumber);
                }

                String personName = "";
                String personNationalID = "";
                String personPhoneNumber = "";
                if (personAutoComplete.getText().toString().equals("")) {
                    Log.d(LOG, "EditInteraction display: person is null: ");
                } else {
                    personName = personAutoComplete.getText().toString();
                    String parts[] = {};
                    parts = personName.split(", ",4);

                    switch( parts.length)  {
                        case 0: {
                            // add
                            break;
                        }
                        case 1: {
                            personName = parts[0];
                            break;
                        }
                        case 2: {
                            personName = parts[0];
                            personNationalID = parts[1];
                            break;
                        }
                        case 3: {
                            personName = parts[0];
                            personNationalID = parts[1];
                            personPhoneNumber = parts[2];
                            break;
                        }
                    }

                    Log.d(LOG, "DisplayInteraction display person: " + personName + personNationalID + personPhoneNumber);
                }

                String paramInteractionDate = "";
                String paramFollowupDate = "";

                if (interactionDate.getText().toString().equals("")) {
                    Log.d(LOG, "DisplayInteraction button interactionDate is empty: ");
                } else {
                    paramInteractionDate = interactionDate.getText().toString();
                    Log.d(LOG, "DisplayInteraction button interactionDate: " + paramInteractionDate);
                }
                if (followupDate.getText().toString().equals("")) {
                    Log.d(LOG, "DisplayInteraction button followupDate is empty: ");
                } else {
                    paramFollowupDate = followupDate.getText().toString();
                    Log.d(LOG, "DisplayInteraction button followupDate: " + paramFollowupDate);
                }

                Fragment fragment;
                fragment = DisplayFragment.newInstance("displayInteraction",
                        facName + ":" + facNationalID + ":" + facPhoneNumber + "<>" + personName + ":" + personNationalID + ":" + personPhoneNumber + "<>" + paramInteractionDate + ":" + paramFollowupDate
                );
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, DisplayFragment.TAG).addToBackStack("DisplayInteraction").commit();

//                if (complete && !paramProjectedDate.toString().equals("") ||
//                        !paramName.toString().equals("") && !paramProjectedDate.toString().equals("") ) {
//
//                    Fragment fragment;
//                    fragment = DisplayFragment.newInstance("displayInteraction", paramName + ":" + paramNationalID + ":" + paramPhoneNumber + ":" + paramProjectedDate);
//                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, DisplayFragment.TAG).addToBackStack("DisplayInteraction").commit();
//                } else {
//                    Toast.makeText(getActivity(), "Must enter Name or ID or Phone and Date", Toast.LENGTH_LONG).show();
//                }
            }
        });

        Button btnEditInteraction = (Button) view.findViewById(R.id.btnEditInteraction);
        btnEditInteraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                java.util.Date utilDate = cal.getTime();
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                Log.d(LOG, "EditInteraction button: ");

                String paramFacName = "";
                String paramFacNationalID = "";
                String paramFacPhoneNumber = "";
                String paramPersonName = "";
                String paramPersonNationalID = "";
                String paramPersonPhoneNumber = "";
                String paramInteractionDate = "";
                Log.d(LOG, "EditInteraction button name: " + personAutoComplete.getText().toString() + "<");

                if (personAutoComplete.getText().toString().equals("")) {
                    Log.d(LOG, "EditInteraction button name is null: ");
                } else {
                    //int interactionId = new Integer(interaction.get_id());
                    //paramInteractionId = Integer.toString(interactionId);

                    paramFacName = facilitatorAutoComplete.getText().toString();
                    String parts[] = {};
                    parts = paramFacName.split(", ",3);

                    switch( parts.length)  {
                        case 0: {
                            // add
                            break;
                        }
                        case 1: {
                            paramFacName = parts[0];
                            break;
                        }
                        case 2: {
                            paramFacName = parts[0];
                            paramFacNationalID = parts[1];
                            break;
                        }
                        case 3: {
                            paramFacName = parts[0];
                            paramFacNationalID = parts[1];
                            paramFacPhoneNumber = parts[2];
                            break;
                        }
                    }

                    paramPersonName = personAutoComplete.getText().toString();
                    String personParts[] = {};
                    parts = paramPersonName.split(", ",3);

                    switch( personParts.length)  {
                        case 0: {
                            // add
                            break;
                        }
                        case 1: {
                            paramPersonName = parts[0];
                            break;
                        }
                        case 2: {
                            paramPersonName = parts[0];
                            paramPersonNationalID = parts[1];
                            break;
                        }
                        case 3: {
                            paramPersonName = parts[0];
                            paramPersonNationalID = parts[1];
                            paramPersonPhoneNumber = parts[2];
                            break;
                        }
                    }

                    Log.d(LOG, "EditInteraction button name/all: " + paramFacName + paramFacNationalID + paramFacPhoneNumber + paramPersonName + paramPersonNationalID + paramPersonPhoneNumber + paramInteractionDate);
                }

                boolean complete = false;
                /*
                if (nationalIdDropdown.getText().toString().equals("")) {
                    Log.d(LOG, "EditInteraction button nationalID is empty: ");
                } else {
                    paramNationalID = nationalIdDropdown.getText().toString();
                    complete = true;
                    Log.d(LOG, "EditInteraction button nationalID: " + paramNationalID);
                }

                if (phoneNumberDropdown.getText().toString().equals("")) {
                    Log.d(LOG, "EditInteraction button phoneNumber is empty: ");
                } else {
                    paramPhoneNumber = phoneNumberDropdown.getText().toString();
                    complete = true;
                    Log.d(LOG, "EditInteraction button phoneNumber: " + paramPhoneNumber);
                }

                if (projectedDate.getText().toString().equals("")) {
                    Log.d(LOG, "EditInteraction button projectedDate is empty: ");
                } else {
                    paramProjectedDate = projectedDate.getText().toString();
                    Log.d(LOG, "EditInteraction button projectedDate: " + paramProjectedDate);
                }
                */

//                if (complete && !paramProjectedDate.toString().equals("") || !paramName.toString().equals("") && !paramProjectedDate.toString().equals("") ) {
                if (true) {
                    Fragment fragment;
                    fragment = EditInteractionFragment.newInstance("editInteraction", paramFacName + ":" + paramFacNationalID + ":" + paramFacPhoneNumber + "<>" + paramPersonName + ":" + paramPersonNationalID + ":" + paramPersonPhoneNumber + "<>" + paramInteractionDate);
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, EditInteractionFragment.TAG).addToBackStack("EditInteraction").commit();
                } else {
                    Toast.makeText(getActivity(), "Must enter Name or ID or Phone and Date", Toast.LENGTH_LONG).show();
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

    public void onResume() {
        super.onResume();
        Log.d(LOG, "interaction fragment:onResume: pop " );

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    Log.d(LOG, "interaction fragment:onResume: pop: handle back " );
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

    public void loadFacilitatorAutoComplete(View view) {

        List<String> facilitatorIDs = dbHelp.getAllFacilitatorIDs();
        // convert to array
        String[] stringArrayFacilitatorID = new String[ facilitatorIDs.size() ];
        facilitatorIDs.toArray(stringArrayFacilitatorID);

        final ClearableAutoCompleteTextView facilitatorAutocomplete = (ClearableAutoCompleteTextView) view.findViewById(R.id.facilitator);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stringArrayFacilitatorID);
        facilitatorAutocomplete.setThreshold(1);
        facilitatorAutocomplete.setAdapter(dataAdapter);

        facilitatorAutocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int index, long position) {
                String nameText = facilitatorAutocomplete.getText().toString();
                String parts[] = {};
                parts = nameText.split(", ");

                String name = parts[0].trim();
                String national_id =  parts[1].trim();
                String phone_number = parts[2].trim();
//                String projected_date = parts[3].trim();
                Log.d(LOG, "AddEditIteractionFragment facilitator selected: " + name + "." + national_id + "." + phone_number + "." );

            }
        });
    }

    public void loadPersonAutoComplete(View view) {

        List<String> personIDs = dbHelp.getAllPersonIDs();
        // convert to array
        String[] stringArrayPersonID = new String[ personIDs.size() ];
        personIDs.toArray(stringArrayPersonID);

        final ClearableAutoCompleteTextView personAutocomplete = (ClearableAutoCompleteTextView) view.findViewById(R.id.person);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stringArrayPersonID);
        personAutocomplete.setThreshold(1);
        personAutocomplete.setAdapter(dataAdapter);

        personAutocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int index, long position) {
                String nameText = personAutocomplete.getText().toString();
                String parts[] = {};
                parts = nameText.split(", ");

                String name = parts[0].trim();
                String national_id =  parts[1].trim();
                String phone_number = parts[2].trim();
//                String projected_date = parts[3].trim();
                Log.d(LOG, "AddEditIteractionFragment person selected: " + name + "." + national_id + "." + phone_number + "." );

            }
        });
    }

    Interaction _interaction = new Interaction();
    InteractionType _interactionType = new InteractionType();
    public void loadInteractionTypeDropdown(View view ) {
        Log.d(LOG, "loadInteractionTypeDropdown: " );

        final Spinner itSpinner = (Spinner) view.findViewById(R.id.interaction_type);
        final List<String> interactionTypeNames = dbHelp.getAllInteractionTypes();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, interactionTypeNames);
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
        itSpinner.setAdapter(dataAdapter);
        _interactionType = dbHelp.getInteractionType(String.valueOf(_interaction.get_type_id()));
        if (_interactionType == null) {
            _interactionType = dbHelp.getInteractionType("2"); // default: Phone Call
        }
        String compareValue = _interactionType.get_name();
        if (!compareValue.equals(null)) {
            int spinnerPosition = dataAdapter.getPosition(compareValue);
            itSpinner.setSelection(spinnerPosition);
        }

        itSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String interactionTypeText  = itSpinner.getSelectedItem().toString();
                _interactionType = dbHelp.getInteractionType(interactionTypeText);
                _interaction.set_type_id(_interactionType.get_id());
                Log.d(LOG, "interactionType: " + _interactionType.get_id() + _interactionType.get_name());
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


}
