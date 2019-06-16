package com.example.admin.dormmanagingapp.Fragments.PersonnelFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.dormmanagingapp.Database;
import com.example.admin.dormmanagingapp.Listeners.EmailButtonListener;
import com.example.admin.dormmanagingapp.Listeners.MessageButtonListener;
import com.example.admin.dormmanagingapp.Listeners.PhoneButtonListener;
import com.example.admin.dormmanagingapp.Model.Personnel;
import com.example.admin.dormmanagingapp.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PersonnelListFragment.OnPersonnelListFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PersonnelListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonnelListFragment extends Fragment /*implements View.OnClickListener*/{

    public static final String TAG = "PersonnelListFragment";

    private OnPersonnelListFragmentInteractionListener mListener;

    View inflatedView;
    Button buttonRegisterNewPersonnel;

    public PersonnelListFragment() {
        // Required empty public constructor
    }

    public static PersonnelListFragment newInstance() {
        PersonnelListFragment fragment = new PersonnelListFragment();
        fragment.setArguments(null);
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
        inflatedView = inflater.inflate(R.layout.fragment_personnel_list, container, false);

        buttonRegisterNewPersonnel = inflatedView.findViewById(R.id.button_register_new_personnel);
        buttonRegisterNewPersonnel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegisterPersonnelButtonPressed();
            }
        });

        LinearLayout layout = (LinearLayout)inflatedView.findViewById(R.id.layout_personnel_list);
        List<Personnel> personnelList = new Database(getContext()).getAllPersonnel();
        for (final Personnel personnel : personnelList){

            LinearLayout personnelDetailsLayout = new LinearLayout(getContext());
            LinearLayout.LayoutParams personnelDetailsLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            personnelDetailsLayout.setOrientation(LinearLayout.VERTICAL);
            personnelDetailsLayout.setLayoutParams(personnelDetailsLayoutParams);
            personnelDetailsLayout.setPadding(0, 20, 0, 0);

            TextView textViewName = new TextView(getContext());
            textViewName.setText("Name: " + personnel.getFirstname() + " " + personnel.getLastname());

            /*LinearLayout secondRowLayout = new LinearLayout(getContext());
            secondRowLayout.setOrientation(LinearLayout.HORIZONTAL);*/

            TextView textViewEmployeeId = new TextView(getContext());
            TextView textViewEmploymentType = new TextView(getContext());

            textViewEmployeeId.setText(getString(R.string.employee_id) + ": " + personnel.getEmployeeId());
            textViewEmploymentType.setText(getString(R.string.employment_type) + ": " + personnel.getType());

            /*secondRowLayout.addView(textViewEmployeeId);
            secondRowLayout.addView(textViewEmploymentType);*/

            LinearLayout buttonLayout = new LinearLayout(getContext());
            buttonLayout.setOrientation(LinearLayout.HORIZONTAL);

            Button buttonMore = new Button(getContext());
            Button buttonPhone = new Button(getContext());
            Button buttonMessage = new Button(getContext());
            Button buttonEmail = new Button(getContext());

            buttonPhone.setTag(personnel.getId().toString());
            buttonMessage.setTag(personnel.getId().toString());
            buttonEmail.setTag(personnel.getId().toString());

            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(100, 100);
            buttonMore.setLayoutParams(buttonLayoutParams);
            buttonPhone.setLayoutParams(buttonLayoutParams);
            buttonMessage.setLayoutParams(buttonLayoutParams);
            buttonEmail.setLayoutParams(buttonLayoutParams);

            buttonMore.setBackgroundResource(R.mipmap.more);
            buttonPhone.setBackgroundResource(R.mipmap.phone);
            buttonMessage.setBackgroundResource(R.mipmap.sms);
            buttonEmail.setBackgroundResource(R.mipmap.email);

            buttonMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMoreButtonPressed(personnel.getId());
                }
            });

            buttonLayout.addView(buttonMore);

            if (personnel.getMobileNumber() != null && personnel.getMobileNumber().trim().length() > 0){
                buttonPhone.setOnClickListener(new PhoneButtonListener(getContext(), personnel.getMobileNumber()));
                buttonMessage.setOnClickListener(new MessageButtonListener(getContext(), personnel.getMobileNumber()));
                buttonLayout.addView(buttonPhone);
                buttonLayout.addView(buttonMessage);
            }

            Log.d(TAG, "onCreateView: personnel email [" + personnel.getEmailAddress()+ "]");
            if(personnel.getEmailAddress() != null && personnel.getEmailAddress().trim().length() > 0){
                Log.d(TAG, "onCreateView: adding email button for " + personnel.getFirstname());
                buttonEmail.setOnClickListener(new EmailButtonListener(getContext(), personnel.getEmailAddress()));
                buttonLayout.addView(buttonEmail);
            }

            personnelDetailsLayout.addView(textViewName);
            personnelDetailsLayout.addView(textViewEmployeeId);
            personnelDetailsLayout.addView(textViewEmploymentType);
            personnelDetailsLayout.addView(buttonLayout);

            layout.addView(personnelDetailsLayout);
        }

        return inflatedView;
    }

    /*@Override
    public void onClick(View v) {
        Button button = (Button)v;

        Integer personnelId = Integer.parseInt(button.getTag().toString());
        if (mListener != null){
            mListener.onPersonnelListFragmentInteraction(personnelId);
        }
    }*/

    public void onRegisterPersonnelButtonPressed() {
        if (mListener != null) {
            mListener.onPersonnelListFragmentInteraction();
        }
    }

    public void onMoreButtonPressed(Integer personnelId) {
        if (mListener != null) {
            mListener.onPersonnelListFragmentInteraction(personnelId);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPersonnelListFragmentInteractionListener) {
            mListener = (OnPersonnelListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnPersonnelListFragmentInteractionListener {
        void onPersonnelListFragmentInteraction(Integer personnelId);
        void onPersonnelListFragmentInteraction();
    }
}
