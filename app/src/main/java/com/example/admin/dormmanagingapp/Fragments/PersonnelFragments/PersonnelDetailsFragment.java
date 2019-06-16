package com.example.admin.dormmanagingapp.Fragments.PersonnelFragments;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.dormmanagingapp.Database;
import com.example.admin.dormmanagingapp.Model.Personnel;
import com.example.admin.dormmanagingapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PersonnelDetailsFragment.OnPersonnelDetailsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PersonnelDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonnelDetailsFragment extends Fragment implements View.OnClickListener{

    // the fragment initialization parameters
    private static final String PERSONNEL_ID = "personnelId";
    private static final String PERSONNEL_EMPLOYEE_ID = "personnelEmployeeId";
    private static final String PERSONNEL_FIRSTNAME = "personnelFirstname";
    private static final String PERSONNEL_LASTNAME = "personnelLastname";
    private static final String PERSONNEL_GENDER = "personnelGender";
    private static final String PERSONNEL_TYPE = "personnelType";
    private static final String PERSONNEL_MOBILE_NUMBER = "personnelMobileNumber";
    private static final String PERSONNEL_EMAIL_ADDRESS = "personnelEmailAddress";

    private Integer personnelId;
    private String personnelEmployeeId;
    private String personnelFirstname;
    private String personnelLastname;
    private String personnelGender;
    private String personnelType;
    private String personnelMobileNumber;
    private String personnelEmailAddress;

    private OnPersonnelDetailsFragmentInteractionListener mListener;

    View inflatedView;
    Button buttonSave, buttonCancel;
    EditText editTextEmployeeId, editTextFirstname, editTextLastname, editTextType, editTextMobileNumber, editTextEmailAddress;
    RadioGroup radioGroupGender;
    RadioButton radioButtonMale, radioButtonFemale;
    TextView textViewTitle;

    public PersonnelDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param personnel .
     * @return A new instance of fragment PersonnelDetailsFragment.
     */
    public static PersonnelDetailsFragment newInstance(Personnel personnel) {
        PersonnelDetailsFragment fragment = new PersonnelDetailsFragment();
        Bundle args = new Bundle();
        if(personnel != null) {
            args.putInt(PERSONNEL_ID, personnel.getId());
            args.putString(PERSONNEL_EMPLOYEE_ID, personnel.getEmployeeId());
            args.putString(PERSONNEL_FIRSTNAME, personnel.getFirstname());
            args.putString(PERSONNEL_LASTNAME, personnel.getLastname());
            args.putString(PERSONNEL_GENDER, personnel.getGender());
            args.putString(PERSONNEL_TYPE, personnel.getType());
            args.putString(PERSONNEL_MOBILE_NUMBER, personnel.getMobileNumber());
            args.putString(PERSONNEL_EMAIL_ADDRESS, personnel.getEmailAddress());
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            personnelId = getArguments().getInt(PERSONNEL_ID);
            personnelEmployeeId = getArguments().getString(PERSONNEL_EMPLOYEE_ID);
            personnelFirstname = getArguments().getString(PERSONNEL_FIRSTNAME);
            personnelLastname = getArguments().getString(PERSONNEL_LASTNAME);
            personnelGender = getArguments().getString(PERSONNEL_GENDER);
            personnelType = getArguments().getString(PERSONNEL_TYPE);
            personnelMobileNumber = getArguments().getString(PERSONNEL_MOBILE_NUMBER);
            personnelEmailAddress = getArguments().getString(PERSONNEL_EMAIL_ADDRESS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflatedView = inflater.inflate(R.layout.fragment_personnel_details, container, false);

        textViewTitle = inflatedView.findViewById(R.id.textViewPersonelDetailsTitle);
        editTextEmployeeId = inflatedView.findViewById(R.id.editTextPersonnelEmployeeId);
        editTextFirstname = inflatedView.findViewById(R.id.editTextPersonnelFirstname);
        editTextLastname = inflatedView.findViewById(R.id.editTextPersonnelLastname);
        editTextType = inflatedView.findViewById(R.id.editTextPersonnelType);
        editTextMobileNumber = inflatedView.findViewById(R.id.editTextPersonnelMobileNumber);
        editTextEmailAddress = inflatedView.findViewById(R.id.editTextPersonnelEmailAddress);

        radioGroupGender = inflatedView.findViewById(R.id.radioGroupPersonnelGender);
        radioButtonMale = inflatedView.findViewById(R.id.radioButtonPersonnelGenderMale);
        radioButtonFemale = inflatedView.findViewById(R.id.radioButtonPersonnelGenderFemale);

        if(personnelId != null && personnelId > 0){
            textViewTitle.setText("Update Personnel Details");
            editTextEmployeeId.setText(personnelEmployeeId != null ? personnelEmployeeId : "");
            editTextFirstname.setText(personnelFirstname != null ? personnelFirstname : "");
            editTextLastname.setText(personnelLastname != null ? personnelLastname : "");
            editTextType.setText(personnelType != null ? personnelType : "");
            editTextMobileNumber.setText(personnelMobileNumber != null ? personnelMobileNumber : "");
            editTextEmailAddress.setText(personnelEmailAddress != null ? personnelEmailAddress : "");

            if(personnelGender != null && personnelGender.equalsIgnoreCase("Male")){
                radioButtonMale.setChecked(true);
            }else if (personnelGender != null && personnelGender.equalsIgnoreCase("Female")){
                radioButtonFemale.setChecked(true);
            }

        }

        buttonSave = inflatedView.findViewById(R.id.button_save_personnel_details);
        buttonCancel = inflatedView.findViewById(R.id.button_cancel_personnel_details);
        buttonSave.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);

        return inflatedView;
    }

    @Override
    public void onClick(View v) {
        Button button = (Button)v;
        if(button.getId() == buttonSave.getId()){
            //validate

            //save
            Database database = new Database(getContext());
            Personnel personnel = new Personnel();
            personnel.setEmployeeId(editTextEmployeeId.getText().toString().trim());
            personnel.setFirstname(editTextFirstname.getText().toString().trim());
            personnel.setLastname(editTextLastname.getText().toString().trim());
            personnel.setGender(radioButtonMale.isChecked() ? "Male" : "Female");
            personnel.setType(editTextType.getText().toString().trim());
            personnel.setMobileNumber(editTextMobileNumber.getText().toString().trim());
            personnel.setEmailAddress(!editTextEmailAddress.getText().toString().trim().isEmpty() ? editTextEmailAddress.getText().toString().trim() : ""); //email address optional

            if(personnelId == null || personnelId < 1) {//new
                Long insertedRowId = database.createPersonnel(personnel);
                if (insertedRowId > 0) {
                    Toast.makeText(getContext(), inflatedView.getResources().getString(R.string.personnel_registered), Toast.LENGTH_LONG).show();
                    if (mListener != null) {
                        mListener.onSavePersonnelDetailsFragmentInteraction();
                    }
                } else {
                    Toast.makeText(getContext(), inflatedView.getResources().getString(R.string.personnel_unable_to_create), Toast.LENGTH_LONG).show();
                }
            }else{//update
                personnel.setId(personnelId);
                Integer updatedRow = database.updatePersonnel(personnel);
                if (updatedRow > 0) {
                    Toast.makeText(getContext(), inflatedView.getResources().getString(R.string.personnel_details_updated), Toast.LENGTH_LONG).show();
                    if (mListener != null) {
                        mListener.onSavePersonnelDetailsFragmentInteraction();
                    }
                } else {
                    Toast.makeText(getContext(), inflatedView.getResources().getString(R.string.personnel_unable_to_update), Toast.LENGTH_LONG).show();
                }
            }

        }else if (button.getId() == buttonCancel.getId()){
            if (mListener != null) {
                mListener.onCancelPersonnelDetailsFragmentInteraction();
            }
        }
    }

    public void onSaveButtonPressed() {
        if (mListener != null) {
            mListener.onSavePersonnelDetailsFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPersonnelDetailsFragmentInteractionListener) {
            mListener = (OnPersonnelDetailsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onPersonnelDetailsFragmentInteraction");
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
    public interface OnPersonnelDetailsFragmentInteractionListener {
        void onSavePersonnelDetailsFragmentInteraction();
        void onCancelPersonnelDetailsFragmentInteraction();
    }
}
