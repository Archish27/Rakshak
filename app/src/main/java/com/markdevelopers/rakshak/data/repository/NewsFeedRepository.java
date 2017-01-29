package com.markdevelopers.rakshak.data.repository;


import com.markdevelopers.rakshak.data.remote.models.AssignmentWrapper;
import com.markdevelopers.rakshak.data.remote.models.NewsFeedWrapper;
import com.markdevelopers.rakshak.data.remote.models.SubscribeWrapper;
import com.markdevelopers.rakshak.data.remote.models.UpdateWrapper;
import com.markdevelopers.rakshak.data.remote.models.UserResponse;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by Archish on 1/14/2017.
 */

public interface NewsFeedRepository {
    Observable<NewsFeedWrapper> getRecentFeed(String accessToken);

    Observable<SubscribeWrapper> getSubscribeFeed(String accessToken);

    Observable<UserResponse> markAsSubscribe(String accessToken, int nid);

    Observable<UpdateWrapper> getUpdates(String accessToken, int did);

    Observable<AssignmentWrapper> getAssignments(String accessToken);

    Observable<UserResponse> setPost(String accessToken, String title, String description, RequestBody postimage,int did);
}
