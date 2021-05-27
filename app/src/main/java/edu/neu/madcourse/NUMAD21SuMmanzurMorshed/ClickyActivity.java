package edu.neu.madcourse.NUMAD21SuMmanzurMorshed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ClickyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicky);

        Button buttonOne = findViewById(R.id.clickyButton);
        /*buttonOne.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //System.out.println("Button Clicked");

                Intent activity2Intent = new Intent(getApplicationContext(), ClickyActivity.class);
                startActivity(activity2Intent);
            }
        });*/
    }
}