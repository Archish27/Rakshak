package com.markdevelopers.rakshak.subscriptions;

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
import com.markdevelopers.rakshak.data.remote.models.Subscribe;
import com.markdevelopers.rakshak.data.remote.models.SubscribeWrapper;
import com.markdevelopers.rakshak.data.remote.models.UserResponse;
import com.markdevelopers.rakshak.data.repository.NewsFeedRepository;

import java.util.ArrayList;

/**
 * Created by Archish on 1/13/2017.
 */

public class SubscriptionsActivity extends BaseActivity implements SubscriptionAdapter.LikeItemUpdateListener, SubscriptionContract.NewsFeedActivityView {
    ProgressBar pgProgress;
    RecyclerView rvNewsFeed;
    SubscriptionPresenter newsFeedActivityPresenter;
    SwipeRefreshLayout swipeRefreshLayout;
    SubscriptionAdapter adapter;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initViews();
        NewsFeedRepository newsFeedRepository = ((RakshakApp) getApplication()).getComponent().newsFeedRepository();
        newsFeedActivityPresenter = new SubscriptionPresenter(newsFeedRepository, this);
        newsFeedActivityPresenter.getSubscription(new SharedPreferenceManager(getApplicationContext()).getAccessToken());
    }


    private void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        rvNewsFeed = (RecyclerView) findViewById(R.id.rvNews);
        pgProgress = (ProgressBar) findViewById(R.id.pgProgress);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srlNews);
        rvNewsFeed.setHasFixedSize(true);
        rvNewsFeed.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                newsFeedActivityPresenter.getSubscription(new SharedPreferenceManager(getApplicationContext()).getAccessToken());
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void populateView(SubscribeWrapper subscribeWrapper) {

        ArrayList<Subscribe> newsFeeds = new ArrayList<>();
        for (int i = 0; i < subscribeWrapper.data.size(); i++) {

            Subscribe subscribe = new Subscribe(subscribeWrapper.data.get(i).getId(),
                    subscribeWrapper.data.get(i).getName(),
                    subscribeWrapper.data.get(i).getDescription(),
                    subscribeWrapper.data.get(i).isSubscribed());
            newsFeeds.add(subscribe);
        }
        adapter = new SubscriptionAdapter(newsFeeds, this);

        rvNewsFeed.setAdapter(adapter);
    }

    @Override
    public void onItemStatusChanged(Subscribe subscribe) {
        //TODO Network Call for Like
        newsFeedActivityPresenter.markSubscribe(new SharedPreferenceManager(getApplicationContext()).getAccessToken(), subscribe.getId());
    }

    @Override
    public void onNetworkException(Throwable e) {
        super.onNetworkException(e);
    }

    @Override
    public void onItemCardClicked(Subscribe subscribe) {
        Intent i = new Intent(getApplicationContext(), UpdatesActivity.class);
        i.putExtra("id",subscribe.getId());
        startActivity(i);
    }


    @Override
    public void onSubscribe(SubscribeWrapper subscribeWrapper) {
        populateView(subscribeWrapper);
        pgProgress.setVisibility(View.GONE);
    }

    @Override
    public void markSubscribe(UserResponse userResponse) {

    }
}
