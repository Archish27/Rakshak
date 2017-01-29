package com.markdevelopers.rakshak.subscriptions;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.markdevelopers.rakshak.R;
import com.markdevelopers.rakshak.common.BaseActivity;
import com.markdevelopers.rakshak.common.RakshakApp;
import com.markdevelopers.rakshak.data.local.SharedPreferenceManager;
import com.markdevelopers.rakshak.data.remote.models.UpdateWrapper;
import com.markdevelopers.rakshak.data.remote.models.Updates;
import com.markdevelopers.rakshak.data.repository.NewsFeedRepository;

import java.util.ArrayList;

/**
 * Created by Archish on 1/28/2017.
 */

public class UpdatesActivity extends BaseActivity implements UpdateContract.UpdateView, UpdateAdapter.LikeItemUpdateListener {
    RecyclerView rvUpdates;
    UpdatePresenter updatePresenter;

    @Override
    public void onNetworkException(Throwable e) {
        super.onNetworkException(e);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates);
        Intent i = getIntent();
        int id = i.getIntExtra("id", 0);
        initViews();
        NewsFeedRepository newsFeedRepository = ((RakshakApp) getApplication()).getComponent().newsFeedRepository();
        updatePresenter = new UpdatePresenter(newsFeedRepository, this);
        updatePresenter.fetchUpdates(new SharedPreferenceManager(getApplicationContext()).getAccessToken(), id);
    }

    private void initViews() {
        rvUpdates = (RecyclerView) findViewById(R.id.rvUpdates);
        rvUpdates.setHasFixedSize(true);
        rvUpdates.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

    }

    @Override
    public void onUpdates(UpdateWrapper updateWrapper) {
        ArrayList<Updates> updates = new ArrayList<>();
        for (int i = 0; i < updateWrapper.data.size(); i++) {
            Updates updates1 = new Updates(updateWrapper.data.get(i).getId()
                    , updateWrapper.data.get(i).getTitle()
                    , updateWrapper.data.get(i).getDescription()
                    , updateWrapper.data.get(i).getImage());
            updates.add(updates1);
        }
        UpdateAdapter updateAdapter = new UpdateAdapter(updates, this);
        rvUpdates.setAdapter(updateAdapter);


    }


    @Override
    public void onItemStatusChanged(Updates updates) {

    }

    @Override
    public void onItemCardClicked(Updates updates) {
        Intent i = new Intent(UpdatesActivity.this, UpdateDetailActivity.class);
        i.putExtra("title", updates.getTitle());
        i.putExtra("description", updates.getDescription());
        i.putExtra("image", updates.getImage());
        startActivity(i);
    }
}
