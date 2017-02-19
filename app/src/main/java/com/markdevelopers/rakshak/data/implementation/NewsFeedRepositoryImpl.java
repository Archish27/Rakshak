package com.markdevelopers.rakshak.data.implementation;


import com.markdevelopers.rakshak.data.remote.NewsFeedRestService;
import com.markdevelopers.rakshak.data.remote.models.AssignmentWrapper;
import com.markdevelopers.rakshak.data.remote.models.NewsFeedWrapper;
import com.markdevelopers.rakshak.data.remote.models.NgoDetail;
import com.markdevelopers.rakshak.data.remote.models.NgoWrapper;
import com.markdevelopers.rakshak.data.remote.models.SubscribeWrapper;
import com.markdevelopers.rakshak.data.remote.models.UpdateWrapper;
import com.markdevelopers.rakshak.data.remote.models.UserResponse;
import com.markdevelopers.rakshak.data.repository.NewsFeedRepository;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by Archish on 1/10/2017.
 */

public class NewsFeedRepositoryImpl implements NewsFeedRepository {

    private NewsFeedRestService newsFeedRestService;

    public NewsFeedRepositoryImpl(NewsFeedRestService newsFeedRestService) {
        this.newsFeedRestService = newsFeedRestService;

    }


    @Override
    public Observable<NewsFeedWrapper> getRecentFeed(String accessToken) {
        return newsFeedRestService.getRecentFeed(accessToken);
    }

    @Override
    public Observable<SubscribeWrapper> getSubscribeFeed(String accessToken) {
        return newsFeedRestService.getSubscribeFeed(accessToken);
    }


    @Override
    public Observable<UserResponse> markAsSubscribe(String accessToken, int nid) {
        return newsFeedRestService.markAsSubscribe(accessToken, nid);
    }

    @Override
    public Observable<UpdateWrapper> getUpdates(String accessToken, int did) {
        return newsFeedRestService.getUpdates(accessToken, did);
    }

    @Override
    public Observable<AssignmentWrapper> getAssignments(String accessToken) {
        return newsFeedRestService.getAssignments(accessToken);
    }

    @Override
    public Observable<NgoWrapper> getNgos(String accessToken) {
        return newsFeedRestService.getNgos(accessToken);
    }

    @Override
    public Observable<UserResponse> setPost(String accessToken, String title, String description, RequestBody postimage, int did) {
        return newsFeedRestService.setNewsPost(accessToken, title, description, postimage, did);
    }

    @Override
    public Observable<NgoDetail> getNgoMission(String accessToken, int id) {
        return newsFeedRestService.getNgoMission(accessToken, id);
    }

    @Override
    public Observable<NgoDetail> getNgoStories(String accessToken, int id) {
        return newsFeedRestService.getNgoStories(accessToken, id);
    }

    @Override
    public Observable<NgoDetail> getNgoContact(String accessToken, int id) {
        return newsFeedRestService.getNgoContact(accessToken, id);
    }

    @Override
    public Observable<UserResponse> setNewsPostNoImage(String accessToken, String title, String description, int did) {
        return newsFeedRestService.setNewsPostNoImage(accessToken, title, description, did);
    }


}
