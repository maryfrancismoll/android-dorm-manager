package com.example.admin.dormmanagingapp;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.admin.dormmanagingapp.Fragments.HappeningTodayFragment;
import com.example.admin.dormmanagingapp.Fragments.MenuOptionsFragment;

public class MainActivity extends AppCompatActivity implements
        HappeningTodayFragment.OnHappeningTodayFragmentInteractionListener,
        MenuOptionsFragment.OnMenuOptionsFragmentInteractionListener {

    private static final String TAG = "MainActivity";
    Fragment fragmentView;
    Button buttonAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonAbout = findViewById(R.id.button_view_about);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container_main, new HappeningTodayFragment())
                .commit();

    }

    @Override
    public void onFragmentInteraction() {
        Log.d(TAG, "onFragmentInteraction: ");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_main, new MenuOptionsFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onFragmentInteraction(View view) {
        Log.d(TAG, "onFragmentInteraction: ");
        if(view instanceof Button) {
            Button button = (Button) view;
            if (button.getId() == R.id.buttonResidents) {
                Intent intent = new Intent(this, ResidentActivity.class);
                startActivity(intent);
            }else if (button.getId() == R.id.buttonPersonnel){
                Intent intent = new Intent(this, PersonnelActivity.class);
                startActivity(intent);
            }else if (button.getId() == R.id.buttonEvents){
                Intent intent = new Intent(this, EventActivity.class);
                startActivity(intent);
            }else if(button.getId() == R.id.buttonEquipment){
                Intent intent = new Intent(this, EquipmentActivity.class);
                startActivity(intent);
            }
        }
    }

    public void buttonAboutClicked(View view){
        Intent aboutIntent = new Intent(this, AboutUsActivity.class);
        startActivity(aboutIntent);
    }
}
