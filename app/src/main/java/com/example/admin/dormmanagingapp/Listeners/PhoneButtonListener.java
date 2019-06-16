package com.example.admin.dormmanagingapp.Listeners;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;

import com.example.admin.dormmanagingapp.Database;
import com.example.admin.dormmanagingapp.Model.Resident;

public class PhoneButtonListener implements View.OnClickListener {

    Context context;
    String number;

    public PhoneButtonListener(Context context, String number) {
        this.context = context;
        this.number = number;
    }

    @Override
    public void onClick(View v) {
        /*Button button = (Button)v;
        Resident resident = new Database(context).getResidentById(Integer.parseInt(v.getTag().toString()));*/
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        context.startActivity(intent);
    }
}
