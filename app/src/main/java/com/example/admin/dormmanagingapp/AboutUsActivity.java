package com.example.admin.dormmanagingapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutUsActivity extends AppCompatActivity {

    Button buttonMap, buttonMessage, buttonPhone, buttonEmail;
    TextView textViewAddress, textViewWebsite, textViewContactNumber, textViewEmailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        buttonMap = findViewById(R.id.button_map);
        textViewAddress = findViewById(R.id.textViewAddress);
        textViewWebsite = findViewById(R.id.textViewWebsite);
        textViewContactNumber = findViewById(R.id.textViewContactNumber);
        textViewEmailAddress = findViewById(R.id.textViewEmailAddress);

        buttonMessage = findViewById(R.id.button_message_company);
        buttonPhone = findViewById(R.id.button_phone_company);
        buttonEmail = findViewById(R.id.button_email_company);

        buttonMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "sms:" + textViewContactNumber.getText().toString()));
                startActivity(intent);
            }
        });
        buttonPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + textViewContactNumber.getText().toString()));
                startActivity(intent);
            }
        });
        buttonEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{textViewEmailAddress.getText().toString()});
                startActivity(Intent.createChooser(intent, "Send email..."));
            }
        });
    }

    public void viewMap(View view){
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + textViewAddress.getText().toString());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void viewWebsite(View view){
        Intent webViewIntent = new Intent(this, WebBrowseActivity.class);
        webViewIntent.putExtra("url", textViewWebsite.getText().toString());
        startActivity(webViewIntent);
    }
}
