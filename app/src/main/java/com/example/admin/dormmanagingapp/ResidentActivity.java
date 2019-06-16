package com.example.admin.dormmanagingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.admin.dormmanagingapp.Fragments.DatePickerFragment;
import com.example.admin.dormmanagingapp.Fragments.ResidentFragments.ResidentDetailsFragment;
import com.example.admin.dormmanagingapp.Fragments.ResidentFragments.ResidentDetailsPage2Fragment;
import com.example.admin.dormmanagingapp.Fragments.ResidentFragments.ResidentDetailsPage3Fragment;
import com.example.admin.dormmanagingapp.Fragments.ResidentFragments.ResidentListFragment;
import com.example.admin.dormmanagingapp.Model.Resident;

public class ResidentActivity extends AppCompatActivity
        implements ResidentDetailsFragment.OnResidentDetailsFragmentInteractionListener,
            ResidentListFragment.OnResidentListFragmentInteractionListener,
            ResidentDetailsPage2Fragment.OnResidentDetailsPage2FragmentInteractionListener,
            ResidentDetailsPage3Fragment.OnResidentDetailsPage3FragmentInteractionListener{

    private static final String TAG = "ResidentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_resident_fragment, new ResidentListFragment())
                .commit();
    }

    @Override
    public void onResidentDetailsFragmentInteraction(Integer id, String name, String studentId, Resident resident) {
        ResidentDetailsPage2Fragment fragment = ResidentDetailsPage2Fragment.newInstance(id, name, studentId, resident);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_resident_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onResidentListFragmentInteractionListener() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_resident_fragment, new ResidentDetailsFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onResidentUpdate(Resident resident) {
        ResidentDetailsPage3Fragment fragment = ResidentDetailsPage3Fragment.newInstance(resident);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_resident_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public String showDatePicker(TextView textView) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "date picker");
        textView.setText(newFragment.selectedDate);
        return newFragment.selectedDate;
    }

    @Override
    public void onResidentContactDetailsFragmentInteraction() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_resident_fragment, new ResidentListFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onResidentListItemViewListener(Integer residentId) {
        Log.d(TAG, "onResidentListItemViewListener: residentId -> " + residentId);
        Resident resident = new Database(this).getResidentById(residentId);
        Log.d(TAG, "onResidentListItemViewListener: " + resident);
        ResidentDetailsFragment fragment = ResidentDetailsFragment.newInstance(resident);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_resident_fragment, fragment)
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
