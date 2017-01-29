package com.markdevelopers.rakshak.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.markdevelopers.rakshak.R;
import com.markdevelopers.rakshak.common.BaseActivity;
import com.markdevelopers.rakshak.common.RakshakApp;
import com.markdevelopers.rakshak.data.local.SharedPreferenceManager;
import com.markdevelopers.rakshak.data.remote.models.NewsFeed;
import com.markdevelopers.rakshak.data.remote.models.NewsFeedWrapper;
import com.markdevelopers.rakshak.data.remote.models.UserResponse;
import com.markdevelopers.rakshak.data.repository.NewsFeedRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Archish on 1/13/2017.
 */

public class NewsFeedActivity extends BaseActivity implements NewsFeedAdapter.LikeItemUpdateListener, NewsFeedContract.NewsFeedActivityView, SearchView.OnQueryTextListener {

    RecyclerView rvNewsFeed;
    NewsFeedPresenter newsFeedActivityPresenter;
    SwipeRefreshLayout swipeRefreshLayout;
    NewsFeedAdapter adapter;
    ArrayList<NewsFeed> newsFeeds;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initViews();
        NewsFeedRepository newsFeedRepository = ((RakshakApp) getApplication()).getComponent().newsFeedRepository();
        newsFeedActivityPresenter = new NewsFeedPresenter(newsFeedRepository, this);
        newsFeedActivityPresenter.getNewsFeed(new SharedPreferenceManager(getApplicationContext()).getAccessToken());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mainactivity, menu);
        final MenuItem item = menu.findItem(R.id.action_search);//Menu Resource, Menu
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                newsFeedActivityPresenter.getNewsFeed(new SharedPreferenceManager(getApplicationContext()).getAccessToken());
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void initViews() {
        rvNewsFeed = (RecyclerView) findViewById(R.id.rvNews);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srlNews);
        rvNewsFeed.setHasFixedSize(true);
        rvNewsFeed.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                newsFeedActivityPresenter.getNewsFeed(new SharedPreferenceManager(getApplicationContext()).getAccessToken());
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void populateView(NewsFeedWrapper newsFeedWrappers) {

        newsFeeds = new ArrayList<>();
        for (int i = 0; i < newsFeedWrappers.data.size(); i++) {

            NewsFeed newsFeed = new NewsFeed(newsFeedWrappers.data.get(i).getId()
                    , newsFeedWrappers.data.get(i).getName()
                    , newsFeedWrappers.data.get(i).getDescription()
                    , newsFeedWrappers.data.get(i).getLocation()
                    , newsFeedWrappers.data.get(i).getSeverity()
                    , newsFeedWrappers.data.get(i).getLatitude()
                    , newsFeedWrappers.data.get(i).getLongitude()
                    , newsFeedWrappers.data.get(i).getStarttime()
                    , newsFeedWrappers.data.get(i).getEndtime()
                    , newsFeedWrappers.data.get(i).isSubscribed());
            newsFeeds.add(newsFeed);
        }
        adapter = new NewsFeedAdapter(newsFeeds, this);
        rvNewsFeed.setAdapter(adapter);
    }

    @Override
    public void onItemStatusChanged(NewsFeed newsFeed) {
        //TODO Network Call for Like
        newsFeedActivityPresenter.markSubscribe(new SharedPreferenceManager(getApplicationContext()).getAccessToken(), newsFeed.getId());
    }

    @Override
    public void onNetworkException(Throwable e) {
        super.onNetworkException(e);
    }

    @Override
    public void onItemCardClicked(NewsFeed newsFeed) {
        Intent i = new Intent(getApplicationContext(), NewsFeedDetailActivity.class);
        i.putExtra("did", newsFeed.getId());
        i.putExtra("name", newsFeed.getName());
        i.putExtra("description", newsFeed.getDescription());
        i.putExtra("location", newsFeed.getLocation());
        i.putExtra("severity", Integer.parseInt(newsFeed.getSeverity()));
        i.putExtra("latitude", newsFeed.getLatitude());
        i.putExtra("longitude", newsFeed.getLongitude());
        i.putExtra("starttime", newsFeed.getStarttime());
        i.putExtra("endtime", newsFeed.getEndtime());
        startActivity(i);
    }


    @Override
    public void onNewsFeed(NewsFeedWrapper newsFeedWrapper) {
        populateView(newsFeedWrapper);
    }

    @Override
    public void markSubscribe(UserResponse userResponse) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<NewsFeed> filter(List<NewsFeed> models, String query) {
        query = query.toLowerCase();

        final List<NewsFeed> filteredModelList = new ArrayList<>();
        for (NewsFeed model : models) {
            String s = model.getName();
            final String text = s.toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
//        newsFeedActivityPresenter.getNewsFeed(new SharedPreferenceManager(getApplicationContext()).getAccessToken());
        final List<NewsFeed> filteredModelList = filter(newsFeeds, newText);
        adapter.animateTo(filteredModelList);
        rvNewsFeed.scrollToPosition(0);
        return false;
    }


}
