package com.markdevelopers.rakshak.news;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.markdevelopers.rakshak.R;
import com.markdevelopers.rakshak.assignment.AssignmentAddActivity;
import com.markdevelopers.rakshak.ui.widgets.BaseButton;
import com.markdevelopers.rakshak.ui.widgets.BaseTextView;

/**
 * Created by Archish on 1/28/2017.
 */

public class NewsFeedDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap googleMapView;
    BaseTextView tvName, tvDescription, tvLocation, tvLatitude, tvLongitude, tvStarttime, tvEndtime;
    String name, description, location, latitude, longitude, starttime, endtime;
    int severity;
    ImageView ivSeverity;
    boolean status;
    int did;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        Intent i = getIntent();
        status = i.getBooleanExtra("status", false);
        name = i.getStringExtra("name");
        description = i.getStringExtra("description");
        location = i.getStringExtra("location");
        severity = i.getIntExtra("severity", 0);
        did = i.getIntExtra("did", 0);
        latitude = i.getStringExtra("latitude");
        longitude = i.getStringExtra("longitude");
        starttime = i.getStringExtra("starttime");
        endtime = i.getStringExtra("endtime");

        initViews();
        initilizeMap();
        tvName.setText(name);
        tvDescription.setText(description);
        tvLocation.setText(location);
        tvStarttime.setText(starttime);
        tvEndtime.setText(endtime);
        if (severity > 0 && severity <= 25)
            ivSeverity.setImageResource(R.drawable.lowdanger);
        else if (severity > 25 && severity <= 50)
            ivSeverity.setImageResource(R.drawable.mediumdanger);
        else if (severity > 51 && severity <= 75)
            ivSeverity.setImageResource(R.drawable.highdanger);
        else if (severity > 75)
            ivSeverity.setImageResource(R.drawable.veryhighdanger);
    }

    private void initViews() {
        getSupportActionBar().setTitle(name);
        tvName = (BaseTextView) findViewById(R.id.tvName);
        tvDescription = (BaseTextView) findViewById(R.id.tvDescription);
        tvLocation = (BaseTextView) findViewById(R.id.tvLocation);
        tvStarttime = (BaseTextView) findViewById(R.id.tvStartTime);
        tvEndtime = (BaseTextView) findViewById(R.id.tvEndTime);
        ivSeverity = (ImageView) findViewById(R.id.ivSeverity);
        BaseButton bAdd = (BaseButton) findViewById(R.id.bAdd);
        if (status)
            bAdd.setVisibility(View.VISIBLE);
        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NewsFeedDetailActivity.this, AssignmentAddActivity.class);
                i.putExtra("did", did);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }

    /**
     * function to load map If map is not created it will create it for you
     */
    private void initilizeMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMapView = googleMap;

        // Changing map type
        googleMapView.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Showing / hiding your current location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMapView.setMyLocationEnabled(true);

        // Enable / Disable zooming controls
        googleMapView.getUiSettings().setZoomControlsEnabled(false);

        // Enable / Disable my location button
        googleMapView.getUiSettings().setMyLocationButtonEnabled(true);

        // Enable / Disable Compass icon
        googleMapView.getUiSettings().setCompassEnabled(true);

        // Enable / Disable Rotate gesture
        googleMapView.getUiSettings().setRotateGesturesEnabled(true);

        // Enable / Disable zooming functionality
        googleMapView.getUiSettings().setZoomGesturesEnabled(true);


        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude))).title(location);

        googleMapView.addMarker(marker);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude))).zoom(10).build();

        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));


    }


}
