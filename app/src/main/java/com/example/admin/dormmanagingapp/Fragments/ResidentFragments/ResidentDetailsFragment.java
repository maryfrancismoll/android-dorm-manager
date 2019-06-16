package com.example.admin.dormmanagingapp.Fragments.ResidentFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.dormmanagingapp.Database;
import com.example.admin.dormmanagingapp.Model.Event;
import com.example.admin.dormmanagingapp.Model.Resident;
import com.example.admin.dormmanagingapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResidentDetailsFragment.OnResidentDetailsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResidentDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResidentDetailsFragment extends Fragment {

    private OnResidentDetailsFragmentInteractionListener mListener;
    private static final String TAG = "ResidentDetailsFragment";

    View inflatedView;
    Button buttonSave;
    RadioGroup radioGroupGender;
    RadioButton radioButtonMale, radioButtonFemale;
    TextView textViewResidentId, textViewStudentID, textViewFirstname, textViewLastname, textViewBirthdate;

    private static final String RESIDENT_ID = "residentId";
    private static final String RESIDENT_FIRSTNAME = "residentFirstname";
    private static final String RESIDENT_LASTNAME = "residentLastname";
    private static final String RESIDENT_STUDENT_ID = "residentStudentId";
    private static final String RESIDENT_BIRTHDATE = "residentBirthdate";
    private static final String RESIDENT_GENDER = "residentGender";

    private Integer residentId;
    private String residentFirstname;
    private String residentLastname;
    private String residentStudentId;
    private String residentBirthdate;
    private String residentGender;

    public ResidentDetailsFragment() {
        // Required empty public constructor
    }

    public static ResidentDetailsFragment newInstance(Resident resident) {
        ResidentDetailsFragment fragment = new ResidentDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(RESIDENT_ID, resident.getId());
        args.putString(RESIDENT_FIRSTNAME, resident.getFirstname());
        args.putString(RESIDENT_LASTNAME, resident.getLastname());
        args.putString(RESIDENT_STUDENT_ID, resident.getStudentId());
        args.putString(RESIDENT_BIRTHDATE, resident.getBirthdate());
        args.putString(RESIDENT_GENDER, resident.getGender());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            residentId = getArguments().getInt(RESIDENT_ID);
            residentFirstname = getArguments().getString(RESIDENT_FIRSTNAME);
            residentLastname = getArguments().getString(RESIDENT_LASTNAME);
            residentStudentId = getArguments().getString(RESIDENT_STUDENT_ID);
            residentBirthdate = getArguments().getString(RESIDENT_BIRTHDATE);
            residentGender = getArguments().getString(RESIDENT_GENDER);
            Log.d(TAG, "onCreate: " + residentId + " - " + residentStudentId + " -> " + residentFirstname + " " + residentLastname );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.inflatedView = inflater.inflate(R.layout.fragment_resident_details, container, false);

        buttonSave = inflatedView.findViewById(R.id.button_save_resident_details);
        textViewStudentID = inflatedView.findViewById(R.id.text_student_id);
        textViewFirstname = inflatedView.findViewById(R.id.text_firstname);
        textViewLastname = inflatedView.findViewById(R.id.text_lastname);
        textViewBirthdate = inflatedView.findViewById(R.id.text_birthdate);
        radioGroupGender = inflatedView.findViewById(R.id.radio_group_gender);
        radioButtonMale = inflatedView.findViewById(R.id.radioButton_gender_male);
        radioButtonFemale = inflatedView.findViewById(R.id.radioButton_gender_female);

        if (residentStudentId != null && !residentStudentId.isEmpty()){
            textViewStudentID.setText(residentStudentId.toString());
            textViewFirstname.setText(residentFirstname);
            textViewLastname.setText(residentLastname);
            textViewBirthdate.setText(residentBirthdate);
            if(residentGender.equalsIgnoreCase("Male")){
                radioButtonMale.setChecked(true);
            }else {
                radioButtonFemale.setChecked(true);
            }
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonSaveDetailsPressed();
            }
        });
        return inflatedView;
    }

    public void onButtonSaveDetailsPressed() {
        if (mListener != null) {

            if(isValid()) {
                Resident resident = new Resident();
                resident.setStudentId(textViewStudentID.getText().toString().trim());
                resident.setFirstname(textViewFirstname.getText().toString().trim());
                resident.setLastname(textViewLastname.getText().toString().trim());
                resident.setBirthdate(textViewBirthdate.getText().toString().trim());
                resident.setGender((radioButtonMale.isChecked() ? "Male" : "Female"));

                //create event for birthday
                Event birthdayEvent = new Event();
                birthdayEvent.setDate(textViewBirthdate.getText().toString().trim());
                birthdayEvent.setEventType(new Database(getContext()).getEventTypeByName("Birthday"));
                birthdayEvent.setModelTableName(Database.TABLE_RESIDENTS);
                birthdayEvent.setTitle("Resident's Birthday!");

                Database database = new Database(getContext());
                if (residentId == null || residentId <= 0) { //new resident
                    if (isUnique(resident)) {
                        Long insertedRow = database.createResident(resident);
                        if (insertedRow != -1) { //will this be same as id?
                            Integer residentId = database.getResidentId(resident);
                            resident.setId(residentId);

                            birthdayEvent.setModelId(residentId);
                            Long createdEvent = database.createEvent(birthdayEvent);
                            if (createdEvent == -1) {
                                Toast.makeText(getContext(), "Unable to create event for birthday. Please manually add this event in the Events. ", Toast.LENGTH_LONG).show();
                            }

                            mListener.onResidentDetailsFragmentInteraction(residentId,
                                    resident.getFirstname() + " " + resident.getLastname(),
                                    resident.getStudentId(), null);
                        } else {
                            Toast.makeText(getContext(), getResources().getString(R.string.resident_not_created), Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getContext(), "Student ID already exists.", Toast.LENGTH_LONG).show();
                    }
                }else{
                    birthdayEvent.setModelId(residentId);
                    Long updatedEvent = database.updateEventDate(birthdayEvent);
                    if(updatedEvent < 1){
                        Toast.makeText(getContext(), "Unable to update event for birthday. Please manually edit this event in the Events. ", Toast.LENGTH_LONG).show();
                    }

                    resident.setId(residentId);

                    //validate if student id is unique
                    if(isUniqueForUpdate(resident)) {
                        Integer updatedRows = database.updateResident(resident);
                        if (updatedRows > 0) {
                            resident = database.getResidentById(residentId);
                            mListener.onResidentDetailsFragmentInteraction(residentId,
                                    resident.getFirstname() + " " + resident.getLastname(),
                                    resident.getStudentId(), resident);
                        } else {
                            Toast.makeText(getContext(), getResources().getString(R.string.resident_details_not_saved), Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getContext(), "Student ID already exists.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnResidentDetailsFragmentInteractionListener) {
            mListener = (OnResidentDetailsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnResidentDetailsFragmentInteractionListener");
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
    public interface OnResidentDetailsFragmentInteractionListener {
        void onResidentDetailsFragmentInteraction(Integer id, String name, String studentId, Resident resident);
    }

    private boolean isValid(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Log.d(TAG, "isValid: " + radioButtonMale.isChecked());
        Log.d(TAG, "isValid: " + radioButtonFemale.isChecked());
        Log.d(TAG, "isValid: " + radioGroupGender.getCheckedRadioButtonId());

        if(textViewStudentID.getText().toString().trim().length() > 0 &&
                textViewFirstname.getText().toString().trim().length() > 0 &&
                textViewLastname.getText().toString().trim().length() > 0 &&
                textViewBirthdate.getText().toString().trim().length() == 10 &&
                radioGroupGender.getCheckedRadioButtonId() != -1){
            try{
                Date date = dateFormat.parse(textViewBirthdate.getText().toString().trim());
                //TODO VALIDATE DATE: at least 18 years old
                if (ofAgeAllowed(date)){
                    return true;
                }else{
                    return false;
                }
            }catch (ParseException e){
                e.printStackTrace();
                Log.e(TAG, "isValid: " + e.getMessage(), e);
                Toast.makeText(getContext(), "Invalid date format. Format should be " + getString(R.string.date_format), Toast.LENGTH_LONG).show();
                return false;
            }

        }
        Toast.makeText(this.getContext(), "Please enter all information.", Toast.LENGTH_LONG).show();
        return false;
    }

    private boolean isUnique(Resident resident){
        Integer residentId = new Database(getContext()).getResidentId(resident);

        if(residentId == null || residentId <= 0){
            return true;
        }

        return false;
    }

    private boolean isUniqueForUpdate(Resident resident){
        Integer residentId = new Database(getContext()).getResidentIdByStudentIdAndNotSameId(resident);

        if(residentId == null || residentId <= 0){
            return true;
        }

        return false;
    }

    private boolean ofAgeAllowed(Date birthdate){
        Calendar today = Calendar.getInstance();
        int year = today.get(Calendar.YEAR);
        Calendar birthdateCal = new GregorianCalendar();
        birthdateCal.setTime(birthdate);
        if (year - birthdateCal.get(Calendar.YEAR) >= 18){
            return true;
        }
        Toast.makeText(this.getContext(),
                "Student does not meet minimum age requirement of 18.", Toast.LENGTH_LONG).show();
        return false;
    }
}
