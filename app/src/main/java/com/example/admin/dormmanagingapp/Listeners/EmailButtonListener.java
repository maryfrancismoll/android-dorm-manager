package com.example.admin.dormmanagingapp.Listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.admin.dormmanagingapp.Database;
import com.example.admin.dormmanagingapp.Model.Resident;

public class EmailButtonListener implements View.OnClickListener {

    Context context;
    String emailAddress;

    public EmailButtonListener(Context context, String emailAddress) {
        this.context = context;
        this.emailAddress = emailAddress;
    }

    @Override
    public void onClick(View v) {
        /*Button button = (Button)v;
        Resident resident = new Database(context).getResidentById(Integer.parseInt(v.getTag().toString()));*/
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{this.emailAddress});
        context.startActivity(Intent.createChooser(intent, "Send email..."));
    }
}
