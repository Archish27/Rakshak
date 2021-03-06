package com.markdevelopers.rakshak.ngos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.markdevelopers.rakshak.R;
import com.markdevelopers.rakshak.common.BaseActivity;
import com.markdevelopers.rakshak.common.RakshakApp;
import com.markdevelopers.rakshak.data.local.SharedPreferenceManager;
import com.markdevelopers.rakshak.data.remote.models.Ngo;
import com.markdevelopers.rakshak.data.remote.models.NgoDetail;
import com.markdevelopers.rakshak.data.remote.models.NgoWrapper;
import com.markdevelopers.rakshak.data.repository.NewsFeedRepository;
import com.markdevelopers.rakshak.ngos.detail.NgoDetailActivity;

import java.util.ArrayList;

/**
 * Created by Archish on 1/29/2017.
 */

public class NgoActivity extends BaseActivity implements NgoContract.NgoView, NgoAdapter.LikeItemUpdateListener {
    SwipeRefreshLayout srlLayout;
    RecyclerView rvNgo;
    NgoPresenter ngoPresenter;
    ProgressBar pgProgress;

    @Override
    public void onNetworkException(Throwable e) {
        super.onNetworkException(e);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo);
        initViews();
        NewsFeedRepository newsFeedRepository = ((RakshakApp) getApplication()).getComponent().newsFeedRepository();
        ngoPresenter = new NgoPresenter(newsFeedRepository, this);
        ngoPresenter.getNgo(new SharedPreferenceManager(getApplicationContext()).getAccessToken());
    }

    private void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        srlLayout = (SwipeRefreshLayout) findViewById(R.id.srlNgo);
        pgProgress = (ProgressBar) findViewById(R.id.pgProgress);
        rvNgo = (RecyclerView) findViewById(R.id.rvNgo);
        rvNgo.setHasFixedSize(true);
        rvNgo.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        srlLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ngoPresenter.getNgo(new SharedPreferenceManager(getApplicationContext()).getAccessToken());

            }
        });
    }

    @Override
    public void onNgo(NgoWrapper ngoWrapper) {
        ArrayList<Ngo> ngos = new ArrayList<>();
        for (int i = 0; i < ngoWrapper.data.size(); i++) {
            Ngo ngo = new Ngo(ngoWrapper.data.get(i).getId(),
                    ngoWrapper.data.get(i).getIcon(),
                    ngoWrapper.data.get(i).getNgoname(),
                    ngoWrapper.data.get(i).getCity(),
                    ngoWrapper.data.get(i).getState()
            );
            ngos.add(ngo);
        }
        NgoAdapter ngoAdapter = new NgoAdapter(ngos, this);
        rvNgo.setAdapter(ngoAdapter);
        if (srlLayout.isRefreshing())
            srlLayout.setRefreshing(false);
        pgProgress.setVisibility(View.GONE);
    }

    @Override
    public void onData(NgoDetail ngoDetail) {

    }

    @Override
    public void onItemStatusChanged(Ngo ngo) {

    }

    @Override
    public void onItemCardClicked(Ngo ngo) {
        Intent i = new Intent(NgoActivity.this, NgoDetailActivity.class);
        i.putExtra("id", ngo.getId());
        startActivity(i);
    }
}
