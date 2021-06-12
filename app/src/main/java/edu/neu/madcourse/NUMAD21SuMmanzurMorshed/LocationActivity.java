package edu.neu.madcourse.NUMAD21SuMmanzurMorshed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

public class LocationActivity extends AppCompatActivity implements  LocationListener{

    private LocationManager locationManager;
    private TextView latitudeValue;
    private TextView longitudeValue;
    private LocationProvider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        //provider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
        latitudeValue = (TextView) findViewById(R.id.latitudeValue);
        longitudeValue = (TextView) findViewById(R.id.longitudeValue);

        askPermission();



    }
    

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 50;

    public boolean askPermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {


                new AlertDialog.Builder(this)
                        .setTitle("Permission")
                        .setMessage("Allow Access?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(LocationActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1, this);
            Location curLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            latitudeValue.setText(String.valueOf(curLocation.getLatitude()));
            longitudeValue.setText(String.valueOf(curLocation.getLongitude()));
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //longitudeValue.setText(String.valueOf(requestCode));

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //grantResults[0] = -1;
                    // permission granted.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        //Manifest.permission.ACCESS_FINE_LOCATION = null;
                        //Request location updates:
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1, this);

                        Location curLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        //Location curLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (curLocation != null) {
                            latitudeValue.setText(String.valueOf(curLocation.getLatitude()));
                            longitudeValue.setText(String.valueOf(curLocation.getLongitude()));
                        } else{
                            latitudeValue.setText("Location null");
                            //Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            //startActivity(intent);

                        }
                        //latitudeValue.setText(String.valueOf(curLocation.getLatitude()));
                        //latitudeValue.setText("INSIDE");
                    }

                } else {

                    // permission not granted.
                    latitudeValue.setText("No Permission");
                    longitudeValue.setText("No Permission");

                }
                return;
            }

        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        latitudeValue.setText(String.valueOf(location.getLatitude()));
        longitudeValue.setText(String.valueOf(location.getLongitude()));
        return;
    }
    /*
    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            locationManager.requestLocationUpdates(String.valueOf(provider), 400, 1, this);
        }
    }
    */


    @Override
    protected void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
    /////


}