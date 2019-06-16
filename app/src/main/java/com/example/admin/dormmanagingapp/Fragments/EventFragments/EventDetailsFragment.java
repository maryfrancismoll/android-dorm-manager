package com.example.admin.dormmanagingapp.Fragments.EventFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.dormmanagingapp.Database;
import com.example.admin.dormmanagingapp.Model.Equipment;
import com.example.admin.dormmanagingapp.Model.Event;
import com.example.admin.dormmanagingapp.Model.EventType;
import com.example.admin.dormmanagingapp.Model.Personnel;
import com.example.admin.dormmanagingapp.Model.Resident;
import com.example.admin.dormmanagingapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventDetailsFragment.OnEventDetailsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventDetailsFragment extends Fragment implements AdapterView.OnItemSelectedListener,
        SearchView.OnQueryTextListener, View.OnClickListener{

    private static final String TAG = "EventDetailsFragment";

    private OnEventDetailsFragmentInteractionListener mListener;

    View inflatedView;
    EditText editTextTitle, editTextDate, editTextTime, editTextDetails;
    Spinner spinnerEventTypes, spinnerEventRelationships, spinnerObjects;
    SearchView searchView;
    Button buttonSave, buttonCancel;
    private static EventType eventType;
    private static Integer modelId;

    CursorAdapter cursorSearchAdapter;

    // the fragment initialization parameters
    private static final String EVENT_ID = "eventId";
    private static final String EVENT_TITLE = "eventTitle";
    private static final String EVENT_DATE = "eventDate";
    private static final String EVENT_TIME = "eventTime";
    private static final String EVENT_TYPE = "eventType";
    private static final String EVENT_DETAILS = "eventDetails";
    private static final String EVENT_MODEL_TABLE = "eventModelTable";
    private static final String EVENT_MODEL_ID = "eventModelId";

    private Integer eventId;
    private String eventTitle;
    private String eventDate;
    private String eventTime;
    private Integer eventTypeId;
    private String eventDetails;
    private String eventModelTable;
    private Integer eventModelId;

    public EventDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EventDetailsFragment.
     */
    public static EventDetailsFragment newInstance(Event event) {
        EventDetailsFragment fragment = new EventDetailsFragment();
        Bundle args = new Bundle();

        if(event != null) {
            args.putInt(EVENT_ID, event.getId());
            args.putString(EVENT_TITLE, event.getTitle());
            args.putString(EVENT_DATE, event.getDate());
            args.putString(EVENT_TIME, event.getTime());
            args.putInt(EVENT_TYPE, event.getEventType().getId());
            args.putString(EVENT_MODEL_TABLE, event.getModelTableName());
            args.putInt(EVENT_MODEL_ID, event.getModelId());
            args.putString(EVENT_DETAILS, event.getDetails());
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventId = getArguments().getInt(EVENT_ID);
            eventTitle = getArguments().getString(EVENT_TITLE);
            eventDate = getArguments().getString(EVENT_DATE);
            eventTime = getArguments().getString(EVENT_TIME);
            eventTypeId = getArguments().getInt(EVENT_TYPE);
            eventModelTable = getArguments().getString(EVENT_MODEL_TABLE);
            eventModelId = getArguments().getInt(EVENT_MODEL_ID);
            eventDetails = getArguments().getString(EVENT_DETAILS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflatedView = inflater.inflate(R.layout.fragment_event_details, container, false);

        editTextTitle = inflatedView.findViewById(R.id.editTextEventTitle);
        editTextDate = inflatedView.findViewById(R.id.editTextEventDate);
        editTextTime = inflatedView.findViewById(R.id.editTextEventTime);
        editTextDetails = inflatedView.findViewById(R.id.editTextEventDetails);

        spinnerEventTypes = (Spinner)inflatedView.findViewById(R.id.spinnerEventTypes);
        spinnerEventRelationships = (Spinner)inflatedView.findViewById(R.id.spinnerEventRelationship);
        spinnerObjects = (Spinner)inflatedView.findViewById(R.id.spinnerObjects);

        CursorAdapter cursorAdapter = new SimpleCursorAdapter(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                new Database(getContext()).getAllEventTypesSortedByName(),
                new String[] { Database.COLUMN_EVENT_TYPE_NAME },
                new int[] { android.R.id.text1 },
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        spinnerEventTypes.setAdapter(cursorAdapter);
        spinnerEventTypes.setOnItemSelectedListener(this);

        spinnerEventRelationships.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "spinnerEventRelationships onItemSelected: position [" + position + "], id [" + id + "]" );
                if(eventModelTable != null) {
                    Log.d(TAG, "spinnerEventRelationships onItemSelected: model table [" + eventModelTable + "], selected item[" + getDatabaseTableNameEquivalent(spinnerEventRelationships.getSelectedItem().toString()) + "]");
                    if (!getDatabaseTableNameEquivalent(spinnerEventRelationships.getSelectedItem().toString()).equalsIgnoreCase(eventModelTable)){
                        if(spinnerEventRelationships.getAdapter().getCount() > position + 1){
                            spinnerEventRelationships.setSelection(++position);
                        }
                    }else{
                        eventModelTable = null;
                    }
                }else {
                    updateSpinnerObjectsList();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchView = inflatedView.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);

        updateSpinnerObjectsList();
        spinnerObjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                modelId = new Integer("" + id);
                Log.d(TAG, "spinnerObjects onItemSelected: position [" + position + "], id [" + id + "]" );

                if(eventModelId != null){
                    if(new Long(id).intValue() != eventModelId){
                        spinnerObjects.setSelection(++position);
                    }else{
                        eventModelId = null;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(eventId != null){
            editTextTitle.setText(eventTitle);
            editTextDate.setText(eventDate);
            editTextTime.setText(eventTime);
            editTextDetails.setText(eventDetails);
        }

        buttonSave = inflatedView.findViewById(R.id.button_save_event);
        buttonCancel = inflatedView.findViewById(R.id.button_cancel_event);

        buttonSave.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);

        return inflatedView;
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        if(button.getId() == R.id.button_save_event){
            onSaveButtonPressed();
        }else if (button.getId() == R.id.button_cancel_event){
            onCancelButtonPressed();
        }
    }

    public void onSaveButtonPressed() {
        //validate
        if(isValid() && isUnique()) {
            //create onject
            Event event = new Event();
            event.setTitle(editTextTitle.getText().toString().trim());
            event.setDate(editTextDate.getText().toString().trim());
            event.setTime(editTextTime.getText().length() > 0 ? editTextTime.getText().toString().trim() : "");
            event.setDetails(editTextDetails.getText().toString().trim());
            event.setEventType(eventType);
            if (spinnerEventRelationships.getAdapter().getCount() > 0){
                event.setModelTableName(getDatabaseTableNameEquivalent(spinnerEventRelationships.getSelectedItem().toString()));
                event.setModelId(modelId);
            }

            Log.d(TAG, "onSaveButtonPressed: eventId [" + this.eventId + "]");
            if(this.eventId == null) { //new
                Long insertedRowId = new Database(getContext()).createEvent(event);
                if (insertedRowId > 0) {
                    Toast.makeText(getContext(), getResources().getString(R.string.event_created), Toast.LENGTH_LONG).show();
                    if (mListener != null) {
                        mListener.onEventDetailsFragmentInteraction();
                    }
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.event_unable_to_create), Toast.LENGTH_LONG).show();
                }
            }else{ //update
                event.setId(this.eventId);
                Long updatedRows = new Database(getContext()).updateEvent(event);
                if (updatedRows > 0) {
                    Toast.makeText(getContext(), getResources().getString(R.string.event_details_updated), Toast.LENGTH_LONG).show();
                    if (mListener != null) {
                        mListener.onEventDetailsFragmentInteraction();
                    }
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.event_unable_to_update), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void onCancelButtonPressed() {
        if (mListener != null) {
            mListener.onEventDetailsFragmentInteraction();
        }
    }

    private boolean isValid(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (editTextTitle.getText() != null && !editTextTitle.getText().toString().trim().isEmpty() &&
                editTextDate.getText() != null && editTextDate.getText().toString().trim().length() == 10 &&
                // editTextTime.getText() != null && !editTextTime.getText().toString().trim().isEmpty() &&
                editTextDetails.getText() != null && !editTextDetails.getText().toString().trim().isEmpty() &&
                spinnerEventTypes.getSelectedItemPosition() >= 0){
            //check date format
            try{
                Date date = dateFormat.parse(editTextDate.getText().toString().trim());
                date = dateFormat.parse(editTextDate.getText().toString().trim());
            }catch (ParseException e){
                e.printStackTrace();
                Log.e(TAG, "isValid: " + e.getMessage(), e);
                Toast.makeText(getContext(), "Invalid date format. Format should be " + getString(R.string.date_format), Toast.LENGTH_LONG).show();
                return false;
            }

            //check if relationship spinner has choices
            if (spinnerEventRelationships.getAdapter().getCount() > 0){
                //something has to be selected
                if(spinnerEventRelationships.getSelectedItemPosition() >= 0 &&
                        spinnerObjects.getSelectedItemPosition() > 0){ //first one at index 0 is empty
                    return true;
                }else{
                    Toast.makeText(getContext(), "Please select for what the event is for.", Toast.LENGTH_LONG).show();
                    return false;
                }
            }

            return true;
        }else{
            Toast.makeText(getContext(), "Please fill up all the required fields.", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private boolean isUnique(){
        if(eventType != null) {
            String table = null;
            Integer modelId = null;
            if (spinnerEventRelationships.getAdapter().getCount() > 0){
                String tableDisplayName = spinnerEventRelationships.getSelectedItem().toString();
                table = getDatabaseTableNameEquivalent(tableDisplayName);
                modelId = this.modelId;
            }

            Integer eventId = null;
            if(this.eventId == null){ //new
                eventId = new Database(getContext()).getEventByDateTypeTableAndModelId(editTextDate.getText().toString().trim(), eventType.getId(), table, modelId, null);
            }else{
                eventId = new Database(getContext()).getEventByDateTypeTableAndModelId(editTextDate.getText().toString().trim(), eventType.getId(), table, modelId, this.eventId);
            }


            if(eventId == null){
                return true;
            }else{
                Toast.makeText(getContext(), "An event with similar information already exists. Possible duplicate?", Toast.LENGTH_LONG).show();
                return false;
            }
        }else{
            Toast.makeText(getContext(), "Event type cannot be identified.", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private String getDatabaseTableNameEquivalent(String tableDisplayName){
        String table = null;
        switch (tableDisplayName){
            case "Resident":
                table = Database.TABLE_RESIDENTS;
                break;
            case "Personnel":
                table = Database.TABLE_PERSONNEL;
                break;
            case "Equipment":
                table = Database.TABLE_EQUIPMENT;
                break;
        }
        return table;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "type onItemSelected: position [" + position + "], id [" + id + "]");
        eventType = new Database(getContext()).getEventTypeById(new Integer("" + id));

        List<String> tablesAllowed = this.tablesAllowed(eventType);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, tablesAllowed);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEventRelationships.setAdapter(adapter);

        Log.d(TAG, "onItemSelected: eventTypeId [" + eventTypeId + "]");
        if(eventTypeId != null && eventTypeId.intValue() > 0) {
            if (new Long(id).intValue() != eventTypeId) {
                Log.d(TAG, "type onItemSelected: changing for initial value for edit ");
                if(spinnerEventTypes.getAdapter().getCount() > ++position){
                    spinnerEventTypes.setSelection(position);
                }

            }else{
                eventTypeId = null;
            }
        }

    }

    private void updateSpinnerObjectsList(){

        Log.d(TAG, "updateSpinnerObjectsList: start with selected position [" + spinnerObjects.getSelectedItemPosition() + "]");

        if(searchView.getQuery() != null && !searchView.getQuery().toString().trim().isEmpty() &&
                spinnerEventRelationships.getSelectedItem() != null){
            cursorSearchAdapter = new SimpleCursorAdapter(getContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    new Database(getContext()).search(spinnerEventRelationships.getSelectedItem().toString(), searchView.getQuery().toString().trim()),
                    new String[]{"_name"},
                    new int[]{android.R.id.text1},
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        }else if(spinnerEventRelationships.getSelectedItemPosition() >= 0 && eventModelId != null){
            cursorSearchAdapter = new SimpleCursorAdapter(getContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    new Database(getContext()).search(spinnerEventRelationships.getSelectedItem().toString(), ""),
                    new String[]{"_name"},
                    new int[]{android.R.id.text1},
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        }else{
            cursorSearchAdapter = new SimpleCursorAdapter(getContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    null,
                    new String[]{"_name"},
                    new int[]{android.R.id.text1},
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        }

        spinnerObjects.setAdapter(cursorSearchAdapter);
        Log.d(TAG, "updateSpinnerObjectsList: end");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //return false;
        Log.d(TAG, "onQueryTextChange: new text [" + newText + "]");
        try {
            updateSpinnerObjectsList();
        }catch (Exception e){
            Log.d(TAG, "onQueryTextChange: " + e.getMessage());
        }
        return true;
    }

    private List<String> tablesAllowed(EventType eventType){
        List<String> tablesAllowed = new ArrayList<>();

        List<String> tableNames = Arrays.asList(eventType.getModelTblAllowed().split(","));
        for(String table : tableNames){
            if (table.trim().equalsIgnoreCase(Database.TABLE_EQUIPMENT)){
                tablesAllowed.add("Equipment");
            }else if(table.trim().equalsIgnoreCase(Database.TABLE_RESIDENTS)){
                tablesAllowed.add("Resident");
            }else if(table.trim().equalsIgnoreCase(Database.TABLE_PERSONNEL)){
                tablesAllowed.add("Personnel");
            }
        }

        return tablesAllowed;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEventDetailsFragmentInteractionListener) {
            mListener = (OnEventDetailsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnEventDetailsFragmentInteractionListener");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnEventDetailsFragmentInteractionListener {
        void onEventDetailsFragmentInteraction();
    }
}
