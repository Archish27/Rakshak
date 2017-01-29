package com.markdevelopers.rakshak.news;

import android.util.Log;

import com.markdevelopers.rakshak.data.remote.models.NewsFeedWrapper;
import com.markdevelopers.rakshak.data.remote.models.UserResponse;
import com.markdevelopers.rakshak.data.repository.NewsFeedRepository;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Archish on 1/13/2017.
 */

public class NewsFeedPresenter implements NewsFeedContract.NewsFeedActivityPresenter {

    NewsFeedContract.NewsFeedActivityView view;
    NewsFeedRepository newsFeedRepository;

    public NewsFeedPresenter(NewsFeedRepository newsFeedRepository, NewsFeedContract.NewsFeedActivityView view) {
        this.view = view;
        this.newsFeedRepository = newsFeedRepository;
    }

    @Override
    public void getNewsFeed(String accessToken) {
        newsFeedRepository.
                getRecentFeed(accessToken)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<NewsFeedWrapper>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("ErrorMessage", String.valueOf(e));
                    }

                    @Override
                    public void onNext(NewsFeedWrapper newsFeedWrapper) {
                        if (view != null)
                            view.onNewsFeed(newsFeedWrapper);
                    }
                });

    }


    @Override
    public void markSubscribe(String accessToken, int nid) {
        newsFeedRepository.markAsSubscribe(accessToken, nid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<UserResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view != null)
                            view.onNetworkException(e);
                    }

                    @Override
                    public void onNext(UserResponse userResponse) {
                        if (view != null)
                            view.markSubscribe(userResponse);
                    }
                });
    }


}
