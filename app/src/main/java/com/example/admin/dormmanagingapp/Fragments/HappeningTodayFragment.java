package com.example.admin.dormmanagingapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.dormmanagingapp.Database;
import com.example.admin.dormmanagingapp.Model.Event;
import com.example.admin.dormmanagingapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HappeningTodayFragment.OnHappeningTodayFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HappeningTodayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HappeningTodayFragment extends Fragment {

    public static final String TAG = "HappeningTodayFragment";
    View inflatedView;
    Button buttonManageDorm, buttonPrevious, buttonNext;
    TextView textViewEventInfo;

    List<Event> eventList;
    int currentEventListIndex = -1;

    private OnHappeningTodayFragmentInteractionListener mListener;

    public HappeningTodayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HappeningTodayFragment.
     */
    public static HappeningTodayFragment newInstance() {
        HappeningTodayFragment fragment = new HappeningTodayFragment();
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
        this.inflatedView = inflater.inflate(R.layout.fragment_happening_today, container, false);

        textViewEventInfo = inflatedView.findViewById(R.id.textViewEventInfo);
        buttonPrevious = inflatedView.findViewById(R.id.button_previous);
        buttonNext = inflatedView.findViewById(R.id.button_next);
        buttonPrevious.setEnabled(false);
        buttonNext.setEnabled(false);

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPrevious(v);
            }
        });
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNext(v);
            }
        });

        buttonManageDorm = (Button) inflatedView.findViewById(R.id.buttonManageDorm);
        buttonManageDorm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tell activity to change fragment
                onManageDormButtonPressed();
            }
        });

        eventList = new ArrayList<>();
        eventList = new Database(getContext()).getEventsByDateForHome();
        Log.d(TAG, "onCreateView: eventList size [" + eventList.size() + "]");
        if(eventList != null && eventList.size() > 0){
            displayEvent(++currentEventListIndex);
        }else{
            textViewEventInfo.setText(getText(R.string.no_events_today));
        }

        /*HappeningNowThread happeningNowThread = new HappeningNowThread();
        Thread thread = new Thread(happeningNowThread);
        thread.start();*/

        return inflatedView;
    }

    private void displayEvent(int index){
        Event event = eventList.get(index);
        textViewEventInfo.setText(event.getTitle() + " \n" + event.getDetails());

        if (index >= 1){
            buttonPrevious.setEnabled(true);
        }else{
            buttonPrevious.setEnabled(false);
        }

        if(index + 1 < eventList.size()){
            buttonNext.setEnabled(true);
        }else{
            buttonNext.setEnabled(false);
        }

    }

    public void onManageDormButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    public void getPrevious(View view){
        if(currentEventListIndex - 1 >= 0) {
            displayEvent(--currentEventListIndex);
        }
    }

    public void getNext(View view){
        if(currentEventListIndex + 1 < eventList.size()){
            displayEvent(++currentEventListIndex);
        }
    }

    /*public class HappeningNowThread implements Runnable{
        private String TAG = "HappeningNowThread";
        @Override
        public void run() {
            Log.d(TAG, "run thread on events happening today: ");
            Log.d(TAG, "run: eventlist size [" + eventList.size() + "], current index [" + currentEventListIndex);
            try {
                while (true) {[
                    Thread.sleep(1000);
                    if (eventList.size() > 0 && currentEventListIndex + 1 < eventList.size()) {
                        currentEventListIndex = -1;
                        displayEvent(++currentEventListIndex);
                    }
                }
            }catch (InterruptedException e){
                Log.e(TAG, "InterruptedException: ", e);
            }
        }
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnHappeningTodayFragmentInteractionListener) {
            mListener = (OnHappeningTodayFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnHappeningTodayFragmentInteractionListener");
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
    public interface OnHappeningTodayFragmentInteractionListener {
        void onFragmentInteraction();
    }
}
