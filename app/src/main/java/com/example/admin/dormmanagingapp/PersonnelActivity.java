package com.example.admin.dormmanagingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.admin.dormmanagingapp.Fragments.PersonnelFragments.PersonnelDetailsFragment;
import com.example.admin.dormmanagingapp.Fragments.PersonnelFragments.PersonnelListFragment;
import com.example.admin.dormmanagingapp.Model.Personnel;

public class PersonnelActivity extends AppCompatActivity implements
        PersonnelListFragment.OnPersonnelListFragmentInteractionListener,
        PersonnelDetailsFragment.OnPersonnelDetailsFragmentInteractionListener {

    private static final String TAG = "PersonnelActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnel);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_personnel_fragment, new PersonnelListFragment())
                .commit();
    }

    @Override
    public void onPersonnelListFragmentInteraction(Integer personnelId) {
        Log.d(TAG, "onPersonnelListFragmentInteraction: personnelId -> " + personnelId);
        Personnel personnel = new Database(this).getPersonnelById(personnelId);
        Log.d(TAG, "onPersonnelListFragmentInteraction: " + personnel);
        PersonnelDetailsFragment fragment = PersonnelDetailsFragment.newInstance(personnel);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_personnel_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onPersonnelListFragmentInteraction() {
        PersonnelDetailsFragment fragment = PersonnelDetailsFragment.newInstance(null);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_personnel_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSavePersonnelDetailsFragmentInteraction() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_personnel_fragment, new PersonnelListFragment())
                .commit();
    }

    @Override
    public void onCancelPersonnelDetailsFragmentInteraction() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_personnel_fragment, new PersonnelListFragment())
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
