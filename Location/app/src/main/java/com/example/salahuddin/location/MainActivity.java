package com.example.salahuddin.location;

import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    private TextView tvLatitude,vlongtitude;
    private Button show;
    private FusedLocationProviderClient client;
    Geocoder geocoder;
    List<android.location.Address> addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLatitude = (TextView) findViewById(R.id.latitude);
        vlongtitude = (TextView) findViewById(R.id.longitude);
        show = (Button) findViewById(R.id.showId);

        geocoder = new Geocoder(this, Locale.getDefault());

        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(this);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if (location != null){

                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                           // Toast.makeText(getApplicationContext(),"Latitude"+latitude+"\n"+"Longitude"+longitude,Toast.LENGTH_LONG).show();

                            try {
                                addresses = geocoder.getFromLocation(latitude,longitude,1);

                                String address = addresses.get(0).getAddressLine(0);
                                String area = addresses.get(0).getLocality();
                                String city = addresses.get(0).getSubAdminArea();
                                String country = addresses.get(0).getCountryName();
                                String postalCode = addresses.get(0).getPostalCode();

                                String fullAddress = address+", "+area;

                                vlongtitude.setText(fullAddress);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });

    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);
    }

    public void Address(){


    }
}

