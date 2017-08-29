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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static org.itech.vmmc.DBHelper.VMMC_DATE_FORMAT;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditInteractionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditInteractionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditInteractionFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static String TAG = "EditInteractionTag";
    public static String LOG = "gnr";
    public Context _context;

    private static final String ARG_EDIT_INTERACTION_PARAM = "EXTRA_EDIT_INTERACTION_PARAM";
    private static final String ARG_EDIT_INTERACTION_RECORD_PARAM = "EXTRA_EDIT_INTERACTION_RECORD_PARAM";

    View _view;

    private static String _editInteractionParam;
    private static String _editInteractionRecordParam;
    private static Interaction _interaction;
    private static VMMCLocation _location;
    private static TextView _fac_first_name;
    private static TextView _fac_last_name;
    private static TextView _fac_national_id;
    private static TextView _fac_phone;
    private static TextView _person_first_name;
    private static TextView _person_last_name;
    private static TextView _person_national_id;
    private static TextView _person_phone;
    private static TextView _facilitator;
    private static TextView _person;
    private static TextView _location_id;
    private static TextView _interaction_date;
    private static TextView _followup_date;
    private static Spinner _type_id;
    private static TextView _note;


    private EditText et_interaction_date;
    private EditText et_followup_date;

    private static OnFragmentInteractionListener mListener;
    private DBHelper dbHelp;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     //* @param param1 Parameter 1.
     //* @param param2 Parameter 2.
     * @return A new instance of fragment EditInteractionFragment.
     */
    // TODO: Rename and change types and number of parameters
    //  public static CreateFragment newInstance(String param1, String param2) {
    public static EditInteractionFragment newInstance(String mEditInteractionParam, String mEditInteractionRecordParam) {
        EditInteractionFragment fragment = new EditInteractionFragment();
        Bundle bundle = new Bundle();
        bundle.putString("ARG_EDIT_INTERACTION_PARAM", mEditInteractionParam);
        bundle.putString("ARG_EDIT_INTERACTION_RECORD_PARAM", mEditInteractionRecordParam);

        _editInteractionParam = mEditInteractionParam;
        _editInteractionRecordParam = mEditInteractionRecordParam;

        fragment.setArguments(bundle);
        return fragment;
    }

    // public EditInteractionFragment() {
    // Required empty public constructor
    //}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _editInteractionParam = getArguments().getString("ARG_EDIT_INTERACTION_PARAM");
            _editInteractionRecordParam = getArguments().getString("ARG_EDIT_INTERACTION_RECORD_PARAM");
            Log.d(LOG, "editInteractionFragment onCreate editInteractionParam: ");
            Log.d(LOG, "editInteractionFragment onCreate editInteractionRecordParam: " + _editInteractionRecordParam.toString() + "<");
        }

        String parts[] = _editInteractionRecordParam.toString().split("<>", 3);

        Log.d(LOG, " from Display:0: " + parts.length );

        IndexParts facParts = new IndexParts();
        IndexParts personParts = new IndexParts();
        String dateParts[] = {};
        String _interactionDate = "";
        String _followupDate = "";

        if (parts[0].toString() != "" ) { facParts = new IndexParts(parts[0]); }
        if (parts[1].toString() != "" ) { personParts = new IndexParts(parts[1]); }
        if (parts[2].toString() != "" ) {

            dateParts = parts[2].toString().split(":");

            if (dateParts[0].toString() != "") {
                _interactionDate = dateParts[0];
            }
            if(dateParts.length == 2) {
                if (dateParts[1].toString() != "") {
                    _followupDate = dateParts[1];
                }
            }
        }

        Log.d(LOG, " from Display:1: " + _editInteractionParam);
//        Log.d(LOG, " from Display:2: " +
//                facParts.get_first_name() + " " + facParts.get_last_name() + " " + facParts.get_national_id() + " " + facParts.get_phone() + " " +
//                personParts.get_first_name() + " " + personParts.get_last_name() + " " + personParts.get_national_id() + " " + personParts.get_phone() + " " +
//                _interactionDate + " " + _followupDate);



            dbHelp = new DBHelper(getActivity());
            _interaction = dbHelp.getInteraction(facParts, personParts, _interactionDate, _followupDate);



        if (_interaction != null) {
            Log.d(LOG, "EBF _interaction != null ");
        } else {
            Log.d(LOG, "EBF _interaction is equal null ");
            _interaction = new Interaction();
//            _interaction.set_first_name(person.get_first_name());
//            _interaction.set_last_name(person.get_last_name());
//            _interaction.set_national_id(person.get_national_id());
//            _interaction.set_phone(person.get_phone());
//            _interaction.set_projected_date(projectedDate);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_edit_interaction, container, false);

        getActivity().setTitle(getResources().getString(R.string.editInteractionTitle));
        if(_interaction != null) {
            _facilitator = (TextView) _view.findViewById(R.id.facilitator);
            if(_interaction.get_fac_first_name() == null &&
                    _interaction.get_fac_last_name() == null &&
                    _interaction.get_fac_national_id() == null &&
                    _interaction.get_fac_phone() == null) {
            } else {
                _facilitator.setText(_interaction.get_fac_first_name() + " " + _interaction.get_fac_last_name() + ", " + _interaction.get_fac_national_id() + ", " + _interaction.get_fac_phone());
            }

            _person = (TextView) _view.findViewById(R.id.person);
            if(_interaction.get_person_first_name() == null &&
                    _interaction.get_person_last_name() == null &&
                    _interaction.get_person_national_id() == null &&
                    _interaction.get_person_phone() == null) {
            } else {
                _person.setText(_interaction.get_person_first_name() + " " + _interaction.get_person_last_name() + ", " + _interaction.get_person_national_id() + ", " + _interaction.get_person_phone());
            }

            _interaction_date = (EditText) _view.findViewById(R.id.interaction_date);
            _interaction_date.setText(_interaction.get_interaction_date());
            _followup_date = (EditText) _view.findViewById(R.id.followup_date);
            _followup_date.setText(_interaction.get_followup_date());
            _note = (EditText) _view.findViewById(R.id.note);
            _note.setText(_interaction.get_note());
        }

        loadFacilitatorAutoComplete(_view );
        loadPersonAutoComplete(_view );
        loadInteractionTypeDropdown(_view );

        et_interaction_date = (EditText) _view.findViewById(R.id.interaction_date);
        SimpleDateFormat dateFormatter = new SimpleDateFormat(VMMC_DATE_FORMAT);
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


        et_followup_date = (EditText) _view.findViewById(R.id.followup_date);
        dateFormatter = new SimpleDateFormat(VMMC_DATE_FORMAT);
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


        final DatePickerDialog actual_date_picker_dialog = hold_followup_date_picker_dialog;

        et_followup_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG, "onClick: ");
                actual_date_picker_dialog.show();
            }
        });

        Button btnUpdateInteraction = (Button) _view.findViewById(R.id.btnUpdateInteraction);
        btnUpdateInteraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                java.util.Date utilDate = cal.getTime();
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                _person = (TextView) _view.findViewById(R.id.person);
                _facilitator = (TextView) _view.findViewById(R.id.facilitator);

                Spinner itSpinner = (Spinner) _view.findViewById(R.id.interaction_type);
//              String sLocationText  = lSpinner.getSelectedItem().toString();
                DBHelper dbHelp = new DBHelper(getActivity());
                InteractionType _interactionType = dbHelp.getInteractionType( itSpinner.getSelectedItem().toString());

                _interaction_date= (TextView) _view.findViewById(R.id.interaction_date);
                _followup_date= (TextView) _view.findViewById(R.id.followup_date);
                _note= (TextView) _view.findViewById(R.id.note);

                Log.d(LOG, "UpdateInteraction button: " +
                        _person.getText() + ", " + _facilitator.getText() + " <");

                String sPerson = _person.getText().toString();
                String sFacilitator = _facilitator.getText().toString();

                String sInteractionDate = _interaction_date.getText().toString();
                String sFollowupDate = _followup_date.getText().toString();
                String sNote = _note.getText().toString();

                Log.d(LOG, "UpdateInteraction button2: " +
                        _facilitator.getText() + " - " + _person.getText() +" <");

                DisplayParts dFacParts = new DisplayParts(sFacilitator);
                DisplayParts dPersonParts = new DisplayParts(sPerson);
                IndexParts iFacParts = new IndexParts(dFacParts.get_first_name() + " " +  dFacParts.get_last_name() + ":" +  dFacParts.get_national_id() + ":" + dFacParts.get_phone());
                IndexParts iPersonParts = new IndexParts(dPersonParts.get_first_name() + " " +  dPersonParts.get_last_name() + ":" +  dPersonParts.get_national_id() + ":" + dPersonParts.get_phone());


                Log.d(LOG, "UpdateInteraction button3: " +
                        dFacParts.get_first_name() + ", " + dFacParts.get_last_name() + ", " + dFacParts.get_national_id() + ", " + dFacParts.get_phone() + " - " +
                        dPersonParts.get_first_name() + ", " + dPersonParts.get_last_name() + ", " + dPersonParts.get_national_id() + ", " + dPersonParts.get_phone() + "i: " +
                        iFacParts.get_first_name().toString() + ":" + iPersonParts.get_first_name().toString() + ":" +  sInteractionDate.toString()
                );

                if( iFacParts.get_first_name().toString() != "" && iPersonParts.get_first_name().toString() != "" && sInteractionDate.toString() != "" ) {
                    Interaction lookupInteraction = dbHelp.getInteraction(
                            iFacParts, iPersonParts, sInteractionDate, sFollowupDate);

                    if (lookupInteraction != null) {
                        lookupInteraction.set_fac_first_name(iFacParts.get_first_name());
                        lookupInteraction.set_fac_last_name(iFacParts.get_last_name());
                        lookupInteraction.set_fac_national_id(iFacParts.get_national_id());
                        lookupInteraction.set_fac_phone(iFacParts.get_phone());
                        lookupInteraction.set_person_first_name(iPersonParts.get_first_name());
                        lookupInteraction.set_person_last_name(iPersonParts.get_last_name());
                        lookupInteraction.set_person_national_id(iPersonParts.get_national_id());
                        lookupInteraction.set_person_phone(iPersonParts.get_phone());
                        lookupInteraction.set_interaction_date(sInteractionDate);
                        lookupInteraction.set_followup_date(sFollowupDate);
                        lookupInteraction.set_type_id(_interactionType.get_id());
                        lookupInteraction.set_note(sNote);
                        if(dbHelp.updateInteraction(lookupInteraction))
                            Toast.makeText(getActivity(), "Interaction Updated", Toast.LENGTH_LONG).show();
                    } else {
                        Interaction interaction = new Interaction();
                        interaction.set_fac_first_name(iFacParts.get_first_name());
                        interaction.set_fac_last_name(iFacParts.get_last_name());
                        interaction.set_fac_national_id(iFacParts.get_national_id());
                        interaction.set_fac_phone(iFacParts.get_phone());
                        interaction.set_person_first_name(iPersonParts.get_first_name());
                        interaction.set_person_last_name(iPersonParts.get_last_name());
                        interaction.set_person_national_id(iPersonParts.get_national_id());
                        interaction.set_person_phone(iPersonParts.get_phone());
                        interaction.set_interaction_date(sInteractionDate);
                        interaction.set_followup_date(sFollowupDate);
                        interaction.set_type_id(_interactionType.get_id());
                        interaction.set_note(sNote);
                        Log.d(LOG, "UpdateBtn add: " +
                                iFacParts.get_first_name() + ", " + iFacParts.get_last_name() + ", " + iFacParts.get_national_id() + ", " + iFacParts.get_phone() + " - " +
                                iPersonParts.get_first_name() + ", " + iPersonParts.get_last_name() + ", " + iPersonParts.get_national_id() + ", " + iPersonParts.get_phone() );
                        if(dbHelp.addInteraction(interaction))
                            Toast.makeText(getActivity(), "Interaction Saved", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Must enter Facilitator, Person and Interaction Date", Toast.LENGTH_LONG).show();
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
         _interaction = null;
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
//        _projected_date = (TextView) _view.findViewById(R.id.projected_date); _projected_date.setText("");
//        _actual_date = (TextView) _view.findViewById(R.id.actual_date); _actual_date.setText("");

        loadFacilitatorAutoComplete(_view );
        loadPersonAutoComplete(_view );
        loadInteractionTypeDropdown(_view );
        /*
        if(_interaction != null) {
            _facilitator = (TextView) _view.findViewById(R.id.facilitator);
            _facilitator.setText(_interaction.get_first_name());
            _person = (TextView) _view.findViewById(R.id.person);
            _person.setText(_interaction.get_last_name());

            final Spinner itSpinner = (Spinner) _view.findViewById(R.id.interaction_type);


            _national_id.setText(_interaction.get_national_id());
            _phone = (TextView) _view.findViewById(R.id.phone_number);
            _phone.setText(_interaction.get_phone());
            _projected_date = (TextView) _view.findViewById(R.id.projected_date);
            _projected_date.setText(_interaction.get_projected_date());
            _actual_date = (TextView) _view.findViewById(R.id.actual_date);
            _actual_date.setText(_interaction.get_actual_date());

        }
        */
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

//    public void loadStatusDropdown(View view ) {
//        Log.d(LOG, "loadStatusDropdown: " );
//
//        final Spinner pSpinner = (Spinner) view.findViewById(R.id.status);
//        final List<String> statusNames = dbHelp.getAllStatusTypes();
//
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, statusNames);
////        {
////            @Override
////            public boolean isEnabled(int position) {
////                return position != 1;
////            }
////
////            @Override
////            public boolean areAllItemsEnabled() {
////                return false;
////            }
//
////            @Override
////            public View getDropDownView(int position, View convertView, ViewGroup parent){
////                View v = convertView;
////                if (v == null) {
////                    Context mContext = this.getContext();
////                    LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////                    v = vi.inflate(R.layout.simple_spinner_item, null);
////                }
//////                Spinner spinner = (Spinner) v.findViewById(R.id.status);
////                Log.d(LOG, "loadStatusDropdown:position: " + position + ":" + statusNames.get(position));
////                TextView tv = (TextView) v.findViewById(R.id.spinnerTarget);
//////                tv.setText(statusNames.get(position));
////
////                switch (position) {
////                    case 0:
//////                        tv.setTextColor(Color.RED);
////                        break;
////                    case 1:
//////                        tv.setTextColor(Color.BLUE);
////                        break;
////                    default:
//////                        tv.setTextColor(Color.BLACK);
////                        break;
////                }
////                return v;
////            }
////        };
//
//        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
//        pSpinner.setAdapter(dataAdapter);
//        String compareValue = _status.get_name();
//        if (!compareValue.equals(null)) {
//            int spinnerPosition = dataAdapter.getPosition(compareValue);
//            pSpinner.setSelection(spinnerPosition);
//        }
//
//        pSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String statusText  = pSpinner.getSelectedItem().toString();
//                _status = dbHelp.getStatus(statusText);
//                _client.set_status_id(_status.get_id());
//                Log.d(LOG, "_status: " + _status.get_id() + _status.get_name());
////                status_type = dbHelp.getStatusType(statusText);
//
//
//                //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
////                Log.d(LOG, "statusId/Name selected: " + status.get_id() + " " + status.get_name());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                Log.d(LOG, "spinner nothing selected");
//            }
//        });
//    }

//    public void loadLocationDropdown(View view ) {
//        Log.d(LOG, "loadLoactionDropdown: " );
//
//        final Spinner lSpinner = (Spinner) view.findViewById(R.id.vmmclocation);
//        final List<String> locationNames = dbHelp.getAllLocationNames();
//
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, locationNames);
//
//        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
//        lSpinner.setAdapter(dataAdapter);
//        _location = dbHelp.getLocation(String.valueOf(_interaction.get_location_id()));
//        if (_location == null) {
//            _location = dbHelp.getLocation("1"); // Default
//        }
//        String compareValue = _location.get_name();
//        if (!compareValue.equals(null)) {
//            int spinnerPosition = dataAdapter.getPosition(compareValue);
//            lSpinner.setSelection(spinnerPosition);
//        }
//
//        lSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String locationText  = lSpinner.getSelectedItem().toString();
//                _location = dbHelp.getLocation(locationText);
//                _client.set_loc_id(_location.get_id());
//                Log.d(LOG, "location: " + _location.get_id() + _location.get_name());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                Log.d(LOG, "spinner nothing selected");
//            }
//        });
//    }

    public void loadFacilitatorAutoComplete(View view) {
        DBHelper dbHelp = new DBHelper(getActivity());
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
        DBHelper dbHelp = new DBHelper(getActivity());
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

    InteractionType _interactionType = new InteractionType();
    public void loadInteractionTypeDropdown(View view ) {
        Log.d(LOG, "loadInteractionTypeDropdown: " + _interaction.get_type_id());

        final Spinner itSpinner = (Spinner) view.findViewById(R.id.interaction_type);
        final DBHelper dbHelp = new DBHelper(getActivity());
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

