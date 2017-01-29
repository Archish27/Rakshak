package com.markdevelopers.rakshak.subscriptions;

import com.markdevelopers.rakshak.data.remote.models.UpdateWrapper;
import com.markdevelopers.rakshak.data.repository.NewsFeedRepository;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Archish on 1/29/2017.
 */

public class UpdatePresenter implements UpdateContract.UpdatePresenter {
    NewsFeedRepository newsFeedRepository;
    UpdateContract.UpdateView view;

    public UpdatePresenter(NewsFeedRepository newsFeedRepository, UpdateContract.UpdateView view) {
        this.newsFeedRepository = newsFeedRepository;
        this.view = view;
    }

    @Override
    public void fetchUpdates(String accessToken, int did) {
        newsFeedRepository.getUpdates(accessToken, did)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<UpdateWrapper>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view != null)
                            view.onNetworkException(e);
                    }

                    @Override
                    public void onNext(UpdateWrapper updateWrapper) {
                        if(view!=null)
                            view.onUpdates(updateWrapper);
                    }
                });
    }
}
