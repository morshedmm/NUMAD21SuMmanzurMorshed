package edu.neu.madcourse.NUMAD21SuMmanzurMorshed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class LocationActivity extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager;
    private TextView latitudeValue;
    private TextView longitudeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        latitudeValue = (TextView) findViewById(R.id.latitudeValue);
        longitudeValue = (TextView) findViewById(R.id.longitudeValue);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {


    }
}