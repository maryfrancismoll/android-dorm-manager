package com.example.admin.dormmanagingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin.dormmanagingapp.Fragments.EventFragments.EventDetailsFragment;
import com.example.admin.dormmanagingapp.Fragments.EventFragments.EventListFragment;
import com.example.admin.dormmanagingapp.Model.Event;

public class EventActivity extends AppCompatActivity implements
        EventListFragment.OnEventListFragmentInteractionListener,
        EventDetailsFragment.OnEventDetailsFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_event_fragment, new EventListFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onEventListFragmentInteraction() {
        EventDetailsFragment fragment = EventDetailsFragment.newInstance(null);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_event_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onEventListFragmentInteraction(Integer eventId) {
        Event event = new Database(this).getEventById(eventId);
        EventDetailsFragment fragment = EventDetailsFragment.newInstance(event);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_event_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onEventDetailsFragmentInteraction() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_event_fragment, new EventListFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
