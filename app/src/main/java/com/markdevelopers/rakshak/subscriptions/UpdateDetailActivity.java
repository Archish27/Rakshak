package com.markdevelopers.rakshak.subscriptions;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.markdevelopers.rakshak.R;
import com.markdevelopers.rakshak.common.Config;
import com.markdevelopers.rakshak.ui.widgets.BaseTextView;
import com.squareup.picasso.Picasso;

/**
 * Created by Archish on 1/28/2017.
 */

public class UpdateDetailActivity extends AppCompatActivity {
    private GoogleMap googleMapView;
    BaseTextView tvTitle, tvDescription;
    String name, description, image;
    int severity;
    ImageView ivImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details);
        Intent i = getIntent();
        name = i.getStringExtra("title");
        description = i.getStringExtra("description");
        image = i.getStringExtra("image");
        initViews();

        tvTitle.setText(name);
        tvDescription.setText(description);
        Picasso.with(getApplicationContext()).load(Config.BASE_URL + image).into(ivImage);
    }

    private void initViews() {
        getSupportActionBar().setTitle(name);
        tvTitle = (BaseTextView) findViewById(R.id.tvTitle);
        tvDescription = (BaseTextView) findViewById(R.id.tvDescription);
        ivImage = (ImageView) findViewById(R.id.ivImage);
    }


}
