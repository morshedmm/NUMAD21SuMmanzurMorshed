package edu.neu.madcourse.NUMAD21SuMmanzurMorshed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void aboutMe(View view) {

        Intent activity2Intent = new Intent(getApplicationContext(), AboutMeActivity.class);
        startActivity(activity2Intent);
    }

    public void clicky(View view) {

        Intent activity3Intent = new Intent(getApplicationContext(), ClickyActivity.class);
        startActivity(activity3Intent);
    }

    public void linkCollector(View view) {

        Intent activity4Intent = new Intent(getApplicationContext(), LinkCollectorActivity.class);
        startActivity(activity4Intent);
    }

}