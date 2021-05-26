package edu.neu.madcourse.NUMAD21SuMmanzurMorshed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AboutMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        TextView emailView = findViewById(R.id.aboutEmail);
        TextView textView = findViewById(R.id.myName);
        textView.setText("MManzur Morshed");
        emailView.setText("morshed.mm@northeastern.edu");
    }
}