package com.markdevelopers.rakshak.subscriptions;

import com.markdevelopers.rakshak.data.remote.models.SubscribeWrapper;
import com.markdevelopers.rakshak.data.remote.models.UserResponse;
import com.markdevelopers.rakshak.data.repository.NewsFeedRepository;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Archish on 1/13/2017.
 */

public class SubscriptionPresenter implements SubscriptionContract.NewsFeedActivityPresenter {

    SubscriptionContract.NewsFeedActivityView view;
    NewsFeedRepository newsFeedRepository;

    public SubscriptionPresenter(NewsFeedRepository newsFeedRepository, SubscriptionContract.NewsFeedActivityView view) {
        this.view = view;
        this.newsFeedRepository = newsFeedRepository;
    }

    @Override
    public void getSubscription(String accessToken) {
        newsFeedRepository.getSubscribeFeed(accessToken)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<SubscribeWrapper>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view != null)
                            view.onNetworkException(e);
                    }

                    @Override
                    public void onNext(SubscribeWrapper subscribeWrapper) {
                        if (view != null)
                            view.onSubscribe(subscribeWrapper);
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
