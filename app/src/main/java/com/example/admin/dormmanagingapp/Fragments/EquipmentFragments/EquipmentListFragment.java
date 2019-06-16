package com.example.admin.dormmanagingapp.Fragments.EquipmentFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.dormmanagingapp.Database;
import com.example.admin.dormmanagingapp.Model.Equipment;
import com.example.admin.dormmanagingapp.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EquipmentListFragment.OnEquipmentListFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EquipmentListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EquipmentListFragment extends Fragment implements View.OnClickListener{

    private OnEquipmentListFragmentInteractionListener mListener;

    View inflatedView;
    Button buttonRegisterEquipment;

    public EquipmentListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EquipmentListFragment.
     */
    public static EquipmentListFragment newInstance() {
        EquipmentListFragment fragment = new EquipmentListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflatedView = inflater.inflate(R.layout.fragment_equipment_list, container, false);

        buttonRegisterEquipment = inflatedView.findViewById(R.id.button_register_equipment);
        buttonRegisterEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegisterButtonPressed();
            }
        });

        LinearLayout layout = (LinearLayout)inflatedView.findViewById(R.id.layout_equipment_list);
        List<Equipment> equipmentList = new Database(getContext()).getAllEquipment();
        for(Equipment equipment : equipmentList){
            LinearLayout equipmentDetailsLayout = new LinearLayout(getContext());
            LinearLayout.LayoutParams equipmentDetailsLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            equipmentDetailsLayout.setOrientation(LinearLayout.VERTICAL);
            equipmentDetailsLayout.setLayoutParams(equipmentDetailsLayoutParams);
            equipmentDetailsLayout.setPadding(0, 20, 0, 0);

            TextView textViewCode = new TextView(getContext());
            textViewCode.setText(getString(R.string.equipment_code) + equipment.getCode());

            TextView textViewEquipment = new TextView(getContext());
            textViewEquipment.setText(equipment.getBrand() + " - " + equipment.getName());

            LinearLayout buttonLayout = new LinearLayout(getContext());
            buttonLayout.setOrientation(LinearLayout.HORIZONTAL);

            Button buttonMore = new Button(getContext());

            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(100, 100);
            buttonMore.setLayoutParams(buttonLayoutParams);
            buttonMore.setBackgroundResource(R.mipmap.more);
            buttonMore.setTag(equipment.getId().toString());
            buttonMore.setOnClickListener(this);

            buttonLayout.addView(buttonMore);

            equipmentDetailsLayout.addView(textViewCode);
            equipmentDetailsLayout.addView(textViewEquipment);
            equipmentDetailsLayout.addView(buttonLayout);

            layout.addView(equipmentDetailsLayout);
        }

        return inflatedView;
    }

    public void onRegisterButtonPressed() {
        if (mListener != null) {
            mListener.onEquipmentListFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEquipmentListFragmentInteractionListener) {
            mListener = (OnEquipmentListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnEquipmentListFragmentInteractionListener");
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
    public interface OnEquipmentListFragmentInteractionListener {
        void onEquipmentListFragmentInteraction();
        void onEquipmentListFragmentInteraction(Integer equipmentId);
    }

    @Override
    public void onClick(View v) {
        Button button = (Button)v;

        Integer equipmentId = Integer.parseInt(button.getTag().toString());
        if (mListener != null){
            mListener.onEquipmentListFragmentInteraction(equipmentId);
        }
    }
}
