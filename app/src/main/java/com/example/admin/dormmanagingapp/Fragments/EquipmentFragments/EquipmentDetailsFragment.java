package com.example.admin.dormmanagingapp.Fragments.EquipmentFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.dormmanagingapp.Database;
import com.example.admin.dormmanagingapp.Model.Equipment;
import com.example.admin.dormmanagingapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EquipmentDetailsFragment.OnEquipmentDetailsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EquipmentDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EquipmentDetailsFragment extends Fragment implements View.OnClickListener{

    private OnEquipmentDetailsFragmentInteractionListener mListener;

    View inflatedView;

    Button buttonSave, buttonCancel;
    EditText editTextCode, editTextName, editTextSerialNumber, editTextYearPurchased, editTextBrand;

    private static final String EQUIPMENT_ID = "equipmentId";
    private static final String EQUIPMENT_CODE = "equipmentCode";
    private static final String EQUIPMENT_NAME = "equipmentName";
    private static final String EQUIPMENT_SERIAL_NUMBER = "equipmentSerialNumber";
    private static final String EQUIPMENT_YEAR_PURCHASED = "equipmentYearPurchased";
    private static final String EQUIPMENT_BRAND = "equipmentBrand";

    private Integer equipmentId;
    private String equipmentCode;
    private String equipmentName;
    private String equipmentSerialNumber;
    private Integer equipmentYearPurchased;
    private String equipmentBrand;

    public EquipmentDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EquipmentDetailsFragment.
     */
    public static EquipmentDetailsFragment newInstance(Equipment equipment) {
        EquipmentDetailsFragment fragment = new EquipmentDetailsFragment();
        Bundle args = new Bundle();
        if(equipment != null) {
            args.putInt(EQUIPMENT_ID, equipment.getId());
            args.putString(EQUIPMENT_CODE, equipment.getCode());
            args.putString(EQUIPMENT_NAME, equipment.getName());
            args.putString(EQUIPMENT_SERIAL_NUMBER, equipment.getSerialNumber());
            args.putInt(EQUIPMENT_YEAR_PURCHASED, equipment.getYearPurchased());
            args.putString(EQUIPMENT_BRAND, equipment.getBrand());
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            equipmentId = getArguments().getInt(EQUIPMENT_ID);
            equipmentCode = getArguments().getString(EQUIPMENT_CODE);
            equipmentName = getArguments().getString(EQUIPMENT_NAME);
            equipmentSerialNumber = getArguments().getString(EQUIPMENT_SERIAL_NUMBER);
            equipmentYearPurchased = getArguments().getInt(EQUIPMENT_YEAR_PURCHASED);
            equipmentBrand = getArguments().getString(EQUIPMENT_BRAND);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflatedView = inflater.inflate(R.layout.fragment_equipment_details, container, false);

        editTextCode = inflatedView.findViewById(R.id.editTextEquipmentCode);
        editTextName = inflatedView.findViewById(R.id.editTextEquipmentName);
        editTextSerialNumber = inflatedView.findViewById(R.id.editTextEquipmentSerialNumber);
        editTextYearPurchased = inflatedView.findViewById(R.id.editTextEquipmentYearPurchased);
        editTextBrand = inflatedView.findViewById(R.id.editTextEquipmentBrand);

        if(equipmentId != null){
            editTextCode.setText(equipmentCode);
            editTextName.setText(equipmentName);
            editTextSerialNumber.setText(equipmentSerialNumber);
            editTextYearPurchased.setText(equipmentYearPurchased.toString());
            editTextBrand.setText(equipmentBrand);
        }

        buttonSave = inflatedView.findViewById(R.id.button_save_equipment);
        buttonCancel = inflatedView.findViewById(R.id.button_cancel_equipment);

        buttonSave.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);

        return inflatedView;
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onEquipmentDetailsFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEquipmentDetailsFragmentInteractionListener) {
            mListener = (OnEquipmentDetailsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnEquipmentDetailsFragmentInteractionListener");
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
    public interface OnEquipmentDetailsFragmentInteractionListener {
        void onEquipmentDetailsFragmentInteraction();
        void onCancelEquipmentDetailsFragmentInteraction();
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        if (button.getId() == R.id.button_save_equipment){
            if(equipmentId == null) { //new
                if (isValid() && isUnique(null)) {
                    Equipment equipment = new Equipment();
                    equipment.setCode(editTextCode.getText().toString().trim());
                    equipment.setName(editTextName.getText().toString().trim());
                    equipment.setSerialNumber(editTextSerialNumber.getText().toString().trim());
                    equipment.setYearPurchased(Integer.parseInt(editTextYearPurchased.getText().toString()));
                    equipment.setBrand(editTextBrand.getText().toString().trim());

                    //save
                    Long insertedRowId = new Database(getContext()).createEquipment(equipment);

                    if (insertedRowId != null && insertedRowId > 0) {
                        Toast.makeText(getContext(), getContext().getResources().getString(R.string.equipment_registered), Toast.LENGTH_LONG).show();
                        if (mListener != null) {
                            mListener.onEquipmentDetailsFragmentInteraction();
                        }
                    } else {
                        Toast.makeText(getContext(), getContext().getResources().getString(R.string.equipment_unable_to_register), Toast.LENGTH_LONG).show();
                    }
                }
            }else{ //update
                if(isValid() && isUnique(equipmentId)){
                    Equipment equipment = new Equipment();
                    equipment.setId(equipmentId);
                    equipment.setCode(editTextCode.getText().toString().trim());
                    equipment.setName(editTextName.getText().toString().trim());
                    equipment.setSerialNumber(editTextSerialNumber.getText().toString().trim());
                    equipment.setYearPurchased(Integer.parseInt(editTextYearPurchased.getText().toString()));
                    equipment.setBrand(editTextBrand.getText().toString().trim());

                    //save
                    Integer updatedRow = new Database(getContext()).updateEquipment(equipment);

                    if (updatedRow != null && updatedRow > 0) {
                        Toast.makeText(getContext(), getContext().getResources().getString(R.string.equipment_details_updated), Toast.LENGTH_LONG).show();
                        if (mListener != null) {
                            mListener.onEquipmentDetailsFragmentInteraction();
                        }
                    } else {
                        Toast.makeText(getContext(), getContext().getResources().getString(R.string.equipment_unable_to_update), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }else if (button.getId() == R.id.button_cancel_equipment){
            if (mListener != null) {
                mListener.onCancelEquipmentDetailsFragmentInteraction();
            }
        }
    }

    private boolean isValid(){
        if(editTextCode.getText().length() > 0 &&
                editTextName.getText().length() > 0 &&
                editTextSerialNumber.getText().length() > 0 &&
                editTextYearPurchased.getText().length() == 4 &&
                editTextBrand.getText().length() > 0){
            return true;
        }else{
            Toast.makeText(getContext(), "All fields are required. Please complete all details.", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private boolean isUnique(Integer id){
        //check if code and serial number are unique
        Integer equipmentId = new Database(getContext()).getUniqueEquipment(editTextCode.getText().toString().trim(), editTextSerialNumber.getText().toString().trim(), id);

        if(equipmentId != null && equipmentId > 0){ //code or serial number already exists in the table
            Toast.makeText(getContext(), "Equipment code and / or serial number already exists. Please double check and try again.", Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }
}
