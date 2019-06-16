package com.example.admin.dormmanagingapp.Fragments.EventFragments;

import android.content.Context;
import android.net.Uri;
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
import com.example.admin.dormmanagingapp.Model.Event;
import com.example.admin.dormmanagingapp.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventListFragment.OnEventListFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventListFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "EventListFragment";

    private OnEventListFragmentInteractionListener mListener;

    View inflatedView;
    LinearLayout layout;
    Button buttonCreateEvent;

    public EventListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EventListFragment.
     */
    public static EventListFragment newInstance() {
        EventListFragment fragment = new EventListFragment();
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
        inflatedView = inflater.inflate(R.layout.fragment_event_list, container, false);

        layout = inflatedView.findViewById(R.id.layout_event_list);
        buttonCreateEvent = inflatedView.findViewById(R.id.button_create_new_event);
        buttonCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateEventButtonPressed();
            }
        });

        List<Event> eventList = new Database(getContext()).getAllActiveEvents();
        Log.d(TAG, "onCreateView: " + eventList.size() + " events found");
        for(Event event : eventList){
            LinearLayout eventDetailsLayout = new LinearLayout(getContext());
            LinearLayout.LayoutParams eventDetailsLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            eventDetailsLayout.setOrientation(LinearLayout.VERTICAL);
            eventDetailsLayout.setLayoutParams(eventDetailsLayoutParams);
            eventDetailsLayout.setPadding(0, 20, 0, 0);

            TextView textViewDate = new TextView(getContext());
            textViewDate.setText(event.getDate());

            TextView textViewTitle = new TextView(getContext());
            textViewTitle.setText(event.getTitle());

            Button buttonMore = new Button(getContext());

            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(100, 100);
            buttonMore.setLayoutParams(buttonLayoutParams);
            buttonMore.setBackgroundResource(R.mipmap.more);
            buttonMore.setTag(event.getId().toString());
            buttonMore.setOnClickListener(this);

            LinearLayout buttonLayout = new LinearLayout(getContext());
            buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
            buttonLayout.addView(buttonMore);

            eventDetailsLayout.addView(textViewDate);
            eventDetailsLayout.addView(textViewTitle);
            eventDetailsLayout.addView(buttonLayout);

            layout.addView(eventDetailsLayout);
        }

        return inflatedView;
    }

    @Override
    public void onClick(View v) {
        Button button = (Button)v;
        if (mListener != null) {
            mListener.onEventListFragmentInteraction(Integer.parseInt(button.getTag().toString()));
        }
    }

    public void onCreateEventButtonPressed() {
        if (mListener != null) {
            mListener.onEventListFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEventListFragmentInteractionListener) {
            mListener = (OnEventListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnEventListFragmentInteractionListener");
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
    public interface OnEventListFragmentInteractionListener {
        void onEventListFragmentInteraction();
        void onEventListFragmentInteraction(Integer eventId);
    }
}
