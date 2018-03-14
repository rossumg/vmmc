package org.itech.vmmc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static org.itech.vmmc.MainActivity.currentFragmentId;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ActionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ActionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActionFragment extends Fragment implements AbsListView.OnItemClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";
    public static String LOG = "gnr";

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mListener != null) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            Log.d(LOG, "actionFragment onItemClick: " + mAdapter.getItem(position).toString());

            if (!mAdapter.getItem(position).toString().equals(getResources().getString(R.string.SYNC))) {
//                Log.d(LOG, "action pop before: " + position);
//                getFragmentManager().popBackStack();
//                Log.d(LOG, "action pop before: " + position);
            }

            if (mAdapter.getItem(position).toString().equals(getResources().getString(R.string.GEOLOCATION))) {
//                dbHelp.addGeoLocation();

            } else if (mAdapter.getItem(position).toString().equals(getResources().getString(R.string.loginTitle))) {
                Log.d(LOG, getResources().getString(R.string.loginTitle) + " btn");
                Fragment fragment;
                fragment = getFragmentManager().findFragmentByTag(LoginFragment.LOG);
                if (fragment == null) {
                    fragment = LoginFragment.newInstance();
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, LoginFragment.LOG).addToBackStack(currentFragmentId).commit();
                } else {
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, LoginFragment.LOG).commit();
                }
                currentFragmentId = "Login";
//            } else if (mAdapter.getItem(position).toString().equals(getResources().getString(R.string.UPLOAD))) {
//
//                if(MainActivity._pass.equals("")) {
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                    builder.setTitle("Password");
//
//                    final EditText input = new EditText(getActivity());
//                     Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
//                    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                    builder.setView(input);
//
//                     Set up the buttons
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            MainActivity._pass = input.getText().toString();
//                            Log.d(TAG, "_pass: " + MainActivity._pass);
//                            dbHelp.uploadDBData();
//                        }
//                    });
//                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    });
//
//                    builder.show();
//                }
//
//                if(MainActivity._pass.equals("")){
//                    requires password
//                    Toast.makeText(v.getContext(), "Valid password required.", Toast.LENGTH_LONG).show();
//                } else {
//                     try
//                    dbHelp.uploadDBData();
//                }
//
//            } else if (mAdapter.getItem(position).toString().equals(getResources().getString(R.string.DOWNLOAD))) {
//
//                if(MainActivity._pass.equals("")) {
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                    builder.setTitle("Password");
//
//                    final EditText input = new EditText(getActivity());
//                     Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
//                    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                    builder.setView(input);
//
//                     Set up the buttons
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            MainActivity._pass = input.getText().toString();
//                            Log.d(TAG, "_pass: " + MainActivity._pass);
//                            dbHelp.downloadDBData();
//                        }
//                    });
//                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    });
//
//                    builder.show();
//                }
//
//                if(MainActivity._pass.equals("")){
//                    requires password
//                    Toast.makeText(v.getContext(), "Valid password required.", Toast.LENGTH_LONG).show();
//                } else {
//                     try
//                    dbHelp.downloadDBData();
//                }
//


            } else if (mAdapter.getItem(position).toString().equals(getResources().getString(R.string.addEditBooking))) {
                Log.d(LOG, "ActionFragment " + getResources().getString(R.string.addEditBooking) + " btn");
                Fragment fragment;
                fragment = getFragmentManager().findFragmentByTag(AddEditBookingFragment.TAG);
                if (fragment == null) {
                    fragment = AddEditBookingFragment.newInstance();
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, AddEditBookingFragment.TAG).addToBackStack(currentFragmentId).commit();
                } else {
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, AddEditBookingFragment.TAG).commit();
                }
                currentFragmentId = "AddEditBooking";

            } else if (mAdapter.getItem(position).toString().equals(getResources().getString(R.string.addEditClient))) {
                Log.d(LOG, "ActionFragment " + getResources().getString(R.string.addEditClient) + " btn");

                Fragment fragment;
                fragment = getFragmentManager().findFragmentByTag(AddEditClientFragment.TAG);
                if (fragment == null) {
                    fragment = AddEditClientFragment.newInstance();
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, AddEditClientFragment.TAG).addToBackStack(currentFragmentId).commit();
                } else {
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, AddEditClientFragment.TAG).commit();
                }
                currentFragmentId = "AddEditClient";

            } else if (mAdapter.getItem(position).toString().equals(getResources().getString(R.string.addEditFacilitatorTitle))) {
                Log.d(LOG, "ActionFragment " + getResources().getString(R.string.addEditFacilitatorTitle) + " btn");
                Fragment fragment;
                fragment = getFragmentManager().findFragmentByTag(AddEditFacilitatorFragment.TAG);
                if (fragment == null) {
                    fragment = AddEditFacilitatorFragment.newInstance();
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, AddEditFacilitatorFragment.TAG).addToBackStack(currentFragmentId).commit();
                } else {
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, AddEditFacilitatorFragment.TAG).commit();
                }
                currentFragmentId = "AddEditFacilitator";

            } else if (mAdapter.getItem(position).toString().equals(getResources().getString(R.string.addEditInteractionTitle))) {
                Log.d(LOG, "ActionFragment " + getResources().getString(R.string.addEditInteractionTitle) + " btn");
                Fragment fragment;
                fragment = getFragmentManager().findFragmentByTag(AddEditInteractionFragment.TAG);
                if (fragment == null) {
                    fragment = AddEditInteractionFragment.newInstance();
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, AddEditInteractionFragment.TAG).addToBackStack(currentFragmentId).commit();
                } else {
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, AddEditInteractionFragment.TAG).commit();
                }
                currentFragmentId = "AddEditInteraction";

            } else if (mAdapter.getItem(position).toString().equals(getResources().getString(R.string.addEditGroupActivityTitle))) {
                Log.d(LOG, "ActionFragment " + getResources().getString(R.string.addEditGroupActivityTitle) + " btn");
                Fragment fragment;
                fragment = getFragmentManager().findFragmentByTag(AddEditGroupActivityFragment.TAG);
                if (fragment == null) {
                    fragment = AddEditGroupActivityFragment.newInstance();
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, AddEditGroupActivityFragment.TAG).addToBackStack(currentFragmentId).commit();
                } else {
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, AddEditGroupActivityFragment.TAG).commit();
                }
                currentFragmentId = "AddEditGroupActivity";

            } else if (mAdapter.getItem(position).toString().equals(getResources().getString(R.string.displayPendingFollowup))) {
                Log.d(LOG, "ActionFragment " + getResources().getString(R.string.displayPendingFollowup) + " btn");
                Fragment fragment;
                fragment = getFragmentManager().findFragmentByTag(DisplayFragment.TAG);
//                if (fragment == null) {
                fragment = DisplayFragment.newInstance("displayPendingFollowup", "");
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, DisplayFragment.TAG).addToBackStack(currentFragmentId).commit();
//                } else {
//                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, DisplayFragment.TAG).commit();
//                }
                currentFragmentId = "DisplayFragment";

            } else if (mAdapter.getItem(position).toString().equals(getResources().getString(R.string.addEditUserTitle))) {
                Log.d(LOG, "ActionFragment " + getResources().getString(R.string.addEditUserTitle) + " btn");

                DBHelper dbHelp = new DBHelper(getActivity());
                if (dbHelp.getUserAccess("edit_user")) {
                    Fragment fragment;
                    fragment = getFragmentManager().findFragmentByTag(AddEditUserFragment.TAG);
                    if (fragment == null) {
                        fragment = AddEditUserFragment.newInstance();
                        getFragmentManager().beginTransaction().replace(R.id.container, fragment, AddEditUserFragment.TAG).addToBackStack(currentFragmentId).commit();
                    } else {
                        getFragmentManager().beginTransaction().replace(R.id.container, fragment, AddEditUserFragment.TAG).commit();
                    }
                    currentFragmentId = "AddEditUser";

                } else {
                    Toast.makeText(view.getContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                }

            } else if (mAdapter.getItem(position).toString().equals(getResources().getString(R.string.SYNC))) {
                Log.d(LOG, "SYNC btn");

                if(MainActivity._pass.equals("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Password");

                    final EditText input = new EditText(getActivity());
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    builder.setView(input);

                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity._pass = input.getText().toString();
                            Log.d(TAG, "_pass: " + MainActivity._pass);
                            dbHelp.doSyncDB();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }

                if(MainActivity._pass.equals("")){
                    //requires password
                    //Toast.makeText(v.getContext(), "Valid password required.", Toast.LENGTH_LONG).show();
                } else {
                    // try
//                    dbHelp.uploadDBData();
                    Log.d(LOG, "actionFragment call sync: _username: " + MainActivity._username );
                    dbHelp.doSyncDB();
                }

            } else if (mAdapter.getItem(position).toString().equals(getResources().getString(R.string.TEST))) {
                Log.d(LOG, "actionFragment call testDB");
                dbHelp.doTestDB();

            } else {} // do nothing

//            Log.d(LOG, "ActionFragment Do Nothing");
        }
    }

    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    public static String TAG = "actionTag";
    public DBHelper dbHelp;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AbsListView mListView;
    private ListAdapter mAdapter;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     //     * @param param1 Parameter 1.
     //     * @param param2 Parameter 2.
     * @return A new instance of fragment RecentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActionFragment newInstance(String searchAssessments, String searchParams) {
        ActionFragment fragment = new ActionFragment();
        Bundle args = new Bundle();
        args.putString("searchAssessments", searchAssessments);
        args.putString("searchParams", searchParams);
        fragment.setArguments(args);
        return fragment;
    }

    public ActionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString("searchAssessments");
            mParam2 = getArguments().getString("searchParams");
            Log.d(LOG, "actionFragment onCreate param1: " + mParam1.toString());
            Log.d(LOG, "actionFragment onCreate param2:>" + mParam2.toString() + "<");
        }

        DBHelper dbHelp = new DBHelper(getActivity());
        this.dbHelp = dbHelp;

        List<String> actions = new ArrayList<String>();

//        actions.add(getResources().getString(R.string.GEOLOCATION));
//        actions.add(getResources().getString(R.string.UPLOAD));
//        actions.add(getResources().getString(R.string.DOWNLOAD));

//        actions.add(getResources().getString(R.string.TEST));
//        actions.add(getResources().getString(R.string.loginTitle));
//        actions.add(getResources().getString(R.string.SYNC));
//        actions.add(getResources().getString(R.string.addEditPerson));
//        actions.add(getResources().getString(R.string.addEditFacilitatorTitle));
//        actions.add(getResources().getString(R.string.addEditClient));
//        actions.add(getResources().getString(R.string.addEditBooking));
//        actions.add(getResources().getString(R.string.addEditInteraction));
//        actions.add(getResources().getString(R.string.addEditGroupActivity));
//        actions.add(getResources().getString(R.string.displayPendingFollowup));
//        actions.add(getResources().getString(R.string.addEditUserTitle));

        String[] _stringArray = new String[ actions.size() ];
        actions.toArray(_stringArray);
        mAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, actions);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_action, container, false);

        getActivity().setTitle(getResources().getString(R.string.homeTitle));
        TextView tv = (TextView) view.findViewById(R.id.textViewHomeVersion);
        tv.setText(tv.getText() + MainActivity._version);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);
        // Inflate the layout for this fragment
        return view;
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

    public void onResume() {
        super.onResume();
        Log.d(LOG, "action:onResume: :pop" );

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
//                     handle back button
                    Log.d(LOG, "action fragment:onResume: pop: handle back " );
//                    getFragmentManager().popBackStack();
//                    getFragmentManager().popBackStackImmediate(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
//                    Fragment fragment = getFragmentManager().findFragmentByTag(ActionFragment.TAG);
//                    if (fragment == null) {
//                        fragment = ActionFragment.newInstance("main", "");
//                        getFragmentManager().beginTransaction().replace(R.id.container, fragment, ActionFragment.TAG).addToBackStack(MainActivity.currentFragmentId).commit();
//                    } else {
//                        getFragmentManager().beginTransaction().replace(R.id.container, fragment, ActionFragment.TAG).commit();
//                    }
//                    MainActivity.currentFragmentId = "Action";

                    return true;
                }
                return false;
            }
        });
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

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}