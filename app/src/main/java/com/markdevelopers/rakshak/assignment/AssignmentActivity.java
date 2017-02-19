package com.markdevelopers.rakshak.assignment;

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
import com.markdevelopers.rakshak.data.remote.models.Assignment;
import com.markdevelopers.rakshak.data.remote.models.AssignmentWrapper;
import com.markdevelopers.rakshak.data.remote.models.UserResponse;
import com.markdevelopers.rakshak.data.repository.NewsFeedRepository;
import com.markdevelopers.rakshak.news.NewsFeedDetailActivity;

import java.util.ArrayList;

/**
 * Created by Archish on 1/29/2017.
 */

public class AssignmentActivity extends BaseActivity implements AssignmentContract.AssignmentView, AssignmentAdapter.LikeItemUpdateListener {

    SwipeRefreshLayout srlNews;
    RecyclerView rvNews;
    AssignmentAdapter assignmentAdapter;
    AssignmentPresenter assignmentPresenter;
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
        setContentView(R.layout.activity_news);
        initViews();
        NewsFeedRepository assignmentRepository = ((RakshakApp) getApplication()).getComponent().newsFeedRepository();
        assignmentPresenter = new AssignmentPresenter(assignmentRepository, this);
        assignmentPresenter.fetchAssignments(new SharedPreferenceManager(getApplicationContext()).getAccessToken());
    }

    private void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        srlNews = (SwipeRefreshLayout) findViewById(R.id.srlNews);
        pgProgress = (ProgressBar) findViewById(R.id.pgProgress);
        rvNews = (RecyclerView) findViewById(R.id.rvNews);
        rvNews.setHasFixedSize(true);
        rvNews.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        srlNews.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                assignmentPresenter.fetchAssignments(new SharedPreferenceManager(getApplicationContext()).getAccessToken());
            }
        });
    }

    @Override
    public void onAssign(AssignmentWrapper assignmentWrapper) {
        ArrayList<Assignment> assignments = new ArrayList<>();
        for (int i = 0; i < assignmentWrapper.data.size(); i++) {
            Assignment assignment = new Assignment(assignmentWrapper.data.get(i).getId()
                    , assignmentWrapper.data.get(i).getName()
                    , assignmentWrapper.data.get(i).getDescription()
                    , assignmentWrapper.data.get(i).getLocation()
                    , assignmentWrapper.data.get(i).getSeverity()
                    , assignmentWrapper.data.get(i).getLatitude()
                    , assignmentWrapper.data.get(i).getLongitude()
                    , assignmentWrapper.data.get(i).getStarttime()
                    , assignmentWrapper.data.get(i).getEndtime()
                    , assignmentWrapper.data.get(i).isSubscribed());

            assignments.add(assignment);
        }
        assignmentAdapter = new AssignmentAdapter(assignments, this);
        rvNews.setAdapter(assignmentAdapter);
        if (srlNews.isRefreshing())
            srlNews.setRefreshing(false);
        pgProgress.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess(UserResponse userResponse) {

    }

    @Override
    public void onItemStatusChanged(Assignment assignment) {

    }

    @Override
    public void onItemCardClicked(Assignment assignment) {
        Intent i = new Intent(getApplicationContext(), NewsFeedDetailActivity.class);
        i.putExtra("did", assignment.getId());
        i.putExtra("status", true);
        i.putExtra("name", assignment.getName());
        i.putExtra("description", assignment.getDescription());
        i.putExtra("location", assignment.getLocation());
        i.putExtra("severity", Integer.parseInt(assignment.getSeverity()));
        i.putExtra("latitude", assignment.getLatitude());
        i.putExtra("longitude", assignment.getLongitude());
        i.putExtra("starttime", assignment.getStarttime());
        i.putExtra("endtime", assignment.getEndtime());
        startActivity(i);
    }
}
