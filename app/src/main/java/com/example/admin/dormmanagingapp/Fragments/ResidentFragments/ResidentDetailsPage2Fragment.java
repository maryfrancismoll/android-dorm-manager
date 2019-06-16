package com.example.admin.dormmanagingapp.Fragments.ResidentFragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.dormmanagingapp.Database;
import com.example.admin.dormmanagingapp.Model.Event;
import com.example.admin.dormmanagingapp.Model.Resident;
import com.example.admin.dormmanagingapp.R;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResidentDetailsPage2Fragment.OnResidentDetailsPage2FragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ResidentDetailsPage2Fragment extends Fragment implements View.OnLongClickListener{

    private static final String TAG = "ResidentDetailsPage2";

    private int REQUEST_CODE = 1;

    private OnResidentDetailsPage2FragmentInteractionListener mListener;

    private static final String RESIDENT_ID = "residentId";
    private static final String RESIDENT_NAME = "residentName";
    private static final String RESIDENT_STUDENT_ID = "residentStudentId";
    private static final String RESIDENT_ROOM_NUMBER = "residentRoomNumber";
    private static final String RESIDENT_PROGRAMME = "residentProgramme";
    private static final String RESIDENT_MOVE_IN = "residentMoveIn";
    private static final String RESIDENT_MOVE_OUT = "residentMoveOut";

    private Integer residentId;
    private String residentName;
    private String residentStudentId;
    private Integer residentRoomNumber;
    private String residentProgramme;
    private String residentMoveIn;
    private String residentMoveOut;

    View inflatedView;
    TextView textViewResidentName, textViewResidentStudentID, editTextRoomNumber, editTextProgramme,
            editTextMoveInDate, editTextMoveOutDate;
    Button buttonSaveResidentDetails;
    ImageButton buttonEditPhoto;

    public ResidentDetailsPage2Fragment() {
        // Required empty public constructor
    }

    public static ResidentDetailsPage2Fragment newInstance(Integer id, String name, String studentId, Resident resident) {
        ResidentDetailsPage2Fragment fragment = new ResidentDetailsPage2Fragment();
        Bundle args = new Bundle();
        args.putInt(RESIDENT_ID, id);
        args.putString(RESIDENT_NAME, name);
        args.putString(RESIDENT_STUDENT_ID, studentId);
        if(resident != null) {
            args.putInt(RESIDENT_ROOM_NUMBER, resident.getRoomNumber());
            args.putString(RESIDENT_PROGRAMME, resident.getProgramme());
            args.putString(RESIDENT_MOVE_IN, resident.getMoveInDate());
            args.putString(RESIDENT_MOVE_OUT, resident.getMoveOutDate());
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            residentId = getArguments().getInt(RESIDENT_ID);
            residentName = getArguments().getString(RESIDENT_NAME);
            residentStudentId = getArguments().getString(RESIDENT_STUDENT_ID);
            residentRoomNumber = getArguments().getInt(RESIDENT_ROOM_NUMBER);
            residentProgramme = getArguments().getString(RESIDENT_PROGRAMME);
            residentMoveIn = getArguments().getString(RESIDENT_MOVE_IN);
            residentMoveOut = getArguments().getString(RESIDENT_MOVE_OUT);

            Log.d(TAG, "onCreate: " + residentId + " - " + residentStudentId + " -> " + residentName );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflatedView = inflater.inflate(R.layout.fragment_resident_details_page2, container, false);

        textViewResidentName = inflatedView.findViewById(R.id.textViewResidentName_page2);
        textViewResidentStudentID = inflatedView.findViewById(R.id.textViewResidentStudentID_page2);
        editTextRoomNumber = inflatedView.findViewById(R.id.editTextRoomNumber);
        editTextProgramme = inflatedView.findViewById(R.id.text_programme);
        editTextMoveInDate = inflatedView.findViewById(R.id.text_move_in_date);
        editTextMoveOutDate = inflatedView.findViewById(R.id.text_move_out_date);
        buttonSaveResidentDetails = inflatedView.findViewById(R.id.buttonUpdateResidentDetails);

        buttonEditPhoto = inflatedView.findViewById(R.id.buttonResidentPhoto);
        Bitmap bitmap = new Database(getContext()).getResidentPhoto(residentId);
        Log.d(TAG, "onCreateView: " + bitmap);
        if(bitmap != null){
            buttonEditPhoto.setImageBitmap(bitmap);
            buttonEditPhoto.setBackgroundResource(0);
        }

        buttonEditPhoto.setOnLongClickListener(this);

        textViewResidentName.setText(residentName);
        textViewResidentStudentID.setText(residentStudentId);

        if(residentRoomNumber != null && residentRoomNumber > 0) {
            editTextRoomNumber.setText(residentRoomNumber.toString());
        }

        editTextProgramme.setText(residentProgramme != null ? residentProgramme : "");
        editTextMoveInDate.setText(residentMoveIn != null ? residentMoveIn : "");
        editTextMoveOutDate.setText(residentMoveOut != null ? residentMoveOut : "");

        buttonSaveResidentDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonUpdateResidentDetailsPressed();
            }
        });

        /*buttonSelectMoveInDate = inflatedView.findViewById(R.id.button_select_move_in_date);
        getButtonSelectMoveOutDate = inflatedView.findViewById(R.id.button_select_move_out_date);

        buttonSelectMoveInDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMoveInAndOutDateClick(textViewMoveInDate);
            }
        });
        getButtonSelectMoveOutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMoveInAndOutDateClick(textViewMoveOutDate);
            }
        });*/

        return inflatedView;
    }

    public void onButtonUpdateResidentDetailsPressed() {
        if (mListener != null) {

            if(isValidDetails()) {
                Resident resident = new Resident();
                resident.setId(residentId);
                resident.setRoomNumber(Integer.parseInt(editTextRoomNumber.getText().toString().trim()));
                resident.setProgramme(editTextProgramme.getText().toString().trim());
                resident.setMoveInDate(editTextMoveInDate.getText().toString().trim());
                resident.setMoveOutDate(editTextMoveOutDate.getText().toString().trim());

                Database database = new Database(getContext());
                Event moveInEvent = new Event();
                moveInEvent.setModelId(residentId);
                moveInEvent.setModelTableName(Database.TABLE_RESIDENTS);
                moveInEvent.setEventType(database.getEventTypeByName("Move In"));
                moveInEvent.setTitle("Resident Moving In");
                moveInEvent.setDate(resident.getMoveInDate());

                Event moveOutEvent = new Event();
                moveOutEvent.setModelId(residentId);
                moveOutEvent.setModelTableName(Database.TABLE_RESIDENTS);
                moveOutEvent.setEventType(database.getEventTypeByName("Move Out"));
                moveOutEvent.setTitle("Resident Moving Out");
                moveOutEvent.setDate(resident.getMoveOutDate());

                Database db = new Database(getContext());
                Integer updatedRows = db.updateResidentDetails(resident);
                if (updatedRows == 1) {
                    resident = db.getResidentById(residentId);

                    //create events
                    Long createdEvent = db.updateEventDate(moveInEvent);
                    if(createdEvent == -1){
                        Toast.makeText(getContext(), "Unable to create event for move in date. Please manually add this manually in the Events. ", Toast.LENGTH_LONG).show();
                    }

                    createdEvent = db.updateEventDate(moveOutEvent);
                    if(createdEvent == -1){
                        Toast.makeText(getContext(), "Unable to create event for move in date. Please manually add this manually in the Events. ", Toast.LENGTH_LONG).show();
                    }

                    mListener.onResidentUpdate(resident);
                } else {
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.resident_details_not_saved), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public boolean isValidDetails(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if(editTextRoomNumber.getText().toString().trim().length() > 0 &&
                editTextProgramme.getText().toString().trim().length() > 0 &&
                editTextMoveInDate.getText().toString().trim().length() == 10 &&
                editTextMoveOutDate.getText().toString().trim().length() == 10){
            try{
                Date date = dateFormat.parse(editTextMoveInDate.getText().toString().trim());
                date = dateFormat.parse(editTextMoveOutDate.getText().toString().trim());
                return true;
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnResidentDetailsPage2FragmentInteractionListener) {
            mListener = (OnResidentDetailsPage2FragmentInteractionListener) context;
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
    public interface OnResidentDetailsPage2FragmentInteractionListener {
        void onResidentUpdate(Resident resident);
        String showDatePicker(TextView textView);
    }

    public void onMoveInAndOutDateClick(TextView textView) {
        String selectedDate = "";
        if (mListener != null) {
            mListener.showDatePicker(textView);
        }
        Log.d(TAG, "onMoveInAndOutDateClick: selected date is " + selectedDate);
        ////textView.setText(selectedDate);
    }

    @Override
    public boolean onLongClick(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Photo"), REQUEST_CODE);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri uri = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                Bitmap scaledPhoto = bitmap.createScaledBitmap(bitmap, 200, 200, false);
                buttonEditPhoto.setImageBitmap(scaledPhoto);
                buttonEditPhoto.setBackgroundResource(0);
                //save photo to db
                Integer updatedRow = new Database(getContext()).uploadImage(Database.TABLE_RESIDENTS, Database.COLUMN_RESIDENTS_PHOTO, scaledPhoto, residentId);
            }catch (IOException e){
                e.printStackTrace();
                Log.e(TAG, "onActivityResult: " + e.getMessage(), e);
            }
        }
    }
}
