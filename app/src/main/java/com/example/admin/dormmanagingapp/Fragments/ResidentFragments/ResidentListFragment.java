package com.example.admin.dormmanagingapp.Fragments.ResidentFragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.admin.dormmanagingapp.Database;
import com.example.admin.dormmanagingapp.Listeners.EmailButtonListener;
import com.example.admin.dormmanagingapp.Listeners.MessageButtonListener;
import com.example.admin.dormmanagingapp.Listeners.PhoneButtonListener;
import com.example.admin.dormmanagingapp.Model.Resident;
import com.example.admin.dormmanagingapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResidentListFragment.OnResidentListFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResidentListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResidentListFragment extends Fragment{

    private static final String TAG = "ResidentListFragment";
    View inflatedView;
    public List<Resident> residentList;

    private OnResidentListFragmentInteractionListener mListener;
    Button buttonCreateNewResident;

    public ResidentListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResidentListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResidentListFragment newInstance(String param1, String param2) {
        ResidentListFragment fragment = new ResidentListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: START");
        
        this.inflatedView = inflater.inflate(R.layout.fragment_resident_list, container, false);
        LinearLayout layout = (LinearLayout)inflatedView.findViewById(R.id.layout_resident_list);

        buttonCreateNewResident = (Button)inflatedView.findViewById(R.id.button_new_resident);
        buttonCreateNewResident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonAddNewResidentPressed();
            }
        });

        residentList = new ArrayList<>();
        residentList = new Database(getContext()).getAllCurrentResidents();

        Log.d(TAG, "onCreateView: " + residentList.size() + " residents found. ");
        TableLayout tableLayout = new TableLayout(getContext());
        TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
        tableLayout.setLayoutParams(tableLayoutParams);
        int counter = 0;
        for (final Resident resident : residentList){
            counter++;
            TableRow residentRow = new TableRow(getContext());
            residentRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            LinearLayout imageSectionLayout = new LinearLayout(getContext());
            imageSectionLayout.setOrientation(LinearLayout.VERTICAL);

            Bitmap bitmap = new Database(getContext()).getResidentPhoto(resident.getId());
            ImageView residentPhoto = new ImageView(getContext());
            residentPhoto.setId(2 + counter + resident.getId());
            if(bitmap != null) {
                residentPhoto.setImageBitmap(bitmap);
            }else{
                if (resident.getGender().equalsIgnoreCase("Male")) {
                    residentPhoto.setImageResource(R.drawable.profile_blank_male);
                }else{
                    residentPhoto.setImageResource(R.drawable.profile_blank_female);
                }
            }
            LinearLayout.LayoutParams photoParams = new LinearLayout.LayoutParams(150, 150);
            residentPhoto.setLayoutParams(photoParams);

            TextView dormRoomNumber = new TextView(getContext());
            dormRoomNumber.setId(3 + counter + resident.getId());
            dormRoomNumber.setText(resident.getRoomNumber().toString());
            LinearLayout.LayoutParams roomNumberLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            roomNumberLayoutParams.gravity = Gravity.CENTER;
            dormRoomNumber.setLayoutParams(roomNumberLayoutParams);

            imageSectionLayout.addView(residentPhoto);
            imageSectionLayout.addView(dormRoomNumber);

            LinearLayout residentDetailsLayout = new LinearLayout(getContext());
            LinearLayout.LayoutParams detailsLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //residentDetailsLayout.setLayoutParams(detailsLayoutParams);
            residentDetailsLayout.setOrientation(LinearLayout.VERTICAL);

            TextView textViewStudentIdNumber = new TextView(getContext());
            TextView textViewResidentName = new TextView(getContext());
            textViewResidentName.setTypeface(textViewResidentName.getTypeface(), Typeface.BOLD);

            textViewStudentIdNumber.setText(resident.getStudentId());
            textViewResidentName.setText(resident.getFirstname() + " " + resident.getLastname());

            LinearLayout buttonLayout = new LinearLayout(getContext());
            buttonLayout.setOrientation(LinearLayout.HORIZONTAL);

            Button buttonMore = new Button(getContext());
            Button buttonPhone = new Button(getContext());
            Button buttonMessage = new Button(getContext());
            Button buttonEmail = new Button(getContext());

            buttonPhone.setTag(resident.getId().toString());
            buttonMessage.setTag(resident.getId().toString());
            buttonEmail.setTag(resident.getId().toString());

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
                    onButtonViewResidentDetailsPressed(resident.getId());
                }
            });
            buttonPhone.setOnClickListener(new PhoneButtonListener(getContext(), resident.getMobileNumber()));
            buttonMessage.setOnClickListener(new MessageButtonListener(getContext(), resident.getMobileNumber()));
            buttonEmail.setOnClickListener(new EmailButtonListener(getContext(), resident.getEmailAddress()));

            if (resident.getMobileNumber() == null){
                buttonPhone.setVisibility(View.INVISIBLE);
                buttonMessage.setVisibility(View.INVISIBLE);
            }

            if(resident.getEmailAddress() == null){
                buttonEmail.setVisibility(View.INVISIBLE);
            }

            buttonLayout.addView(buttonMore);
            buttonLayout.addView(buttonPhone);
            buttonLayout.addView(buttonMessage);
            buttonLayout.addView(buttonEmail);

            residentDetailsLayout.addView(textViewStudentIdNumber);
            residentDetailsLayout.addView(textViewResidentName);
            residentDetailsLayout.addView(buttonLayout);

            //add views to tablerow
            residentRow.addView(imageSectionLayout);
            residentRow.addView(residentDetailsLayout);

            tableLayout.addView(residentRow);
        }

        layout.addView(tableLayout);

        Log.d(TAG, "onCreateView: END");
        return inflatedView;
    }

    public void onButtonAddNewResidentPressed() {
        if (mListener != null) {
            mListener.onResidentListFragmentInteractionListener();
        }
    }

    public void onButtonViewResidentDetailsPressed(Integer residentId){
        if (mListener != null){
            mListener.onResidentListItemViewListener(residentId);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnResidentListFragmentInteractionListener) {
            mListener = (OnResidentListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnResidentListFragmentInteractionListener");
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
    public interface OnResidentListFragmentInteractionListener {
        void onResidentListFragmentInteractionListener();
        void onResidentListItemViewListener(Integer residentId);
    }

    public class ViewButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Button button = (Button) v;
            onButtonViewResidentDetailsPressed(Integer.parseInt(v.getTag().toString()));
        }
    }


}
