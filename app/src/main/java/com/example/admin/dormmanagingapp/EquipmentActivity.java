package com.example.admin.dormmanagingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin.dormmanagingapp.Fragments.EquipmentFragments.EquipmentDetailsFragment;
import com.example.admin.dormmanagingapp.Fragments.EquipmentFragments.EquipmentListFragment;
import com.example.admin.dormmanagingapp.Model.Equipment;

public class EquipmentActivity extends AppCompatActivity implements EquipmentListFragment.OnEquipmentListFragmentInteractionListener,
        EquipmentDetailsFragment.OnEquipmentDetailsFragmentInteractionListener{

    private static final String TAG = "EquipmentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_equipment_fragment, new EquipmentListFragment())
                .commit();
    }

    @Override
    public void onEquipmentDetailsFragmentInteraction() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_equipment_fragment, new EquipmentListFragment())
                .commit();
    }

    @Override
    public void onCancelEquipmentDetailsFragmentInteraction() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_equipment_fragment, new EquipmentListFragment())
                .commit();
    }

    @Override
    public void onEquipmentListFragmentInteraction() {
        EquipmentDetailsFragment fragment = EquipmentDetailsFragment.newInstance(null);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_equipment_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onEquipmentListFragmentInteraction(Integer equipmentId) {
        Equipment equipment = new Database(this).getEquipmentById(equipmentId);
        EquipmentDetailsFragment fragment = EquipmentDetailsFragment.newInstance(equipment);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_equipment_fragment, fragment)
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
