package edu.neu.madcourse.NUMAD21SuMmanzurMorshed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ClickyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicky);

    }

    public void clickyResponse(View view) {

        TextView textView = findViewById(R.id.response);
        textView.setText("MManzur Morshed");


    }
}