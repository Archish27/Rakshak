package com.markdevelopers.rakshak.ngos;

import com.markdevelopers.rakshak.data.remote.models.NgoDetail;
import com.markdevelopers.rakshak.data.remote.models.NgoWrapper;
import com.markdevelopers.rakshak.data.repository.NewsFeedRepository;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Archish on 1/29/2017.
 */

public class NgoPresenter implements NgoContract.NgoPresenter {
    NewsFeedRepository newsFeedRepository;
    NgoContract.NgoView view;

    public NgoPresenter(NewsFeedRepository newsFeedRepository, NgoContract.NgoView view) {
        this.newsFeedRepository = newsFeedRepository;
        this.view = view;
    }

    @Override
    public void getNgo(String accessToken) {
        newsFeedRepository.getNgos(accessToken)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<NgoWrapper>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view != null)
                            view.onNetworkException(e);

                    }

                    @Override
                    public void onNext(NgoWrapper ngoWrapper) {
                        if (view != null)
                            view.onNgo(ngoWrapper);
                    }
                });

    }

    @Override
    public void getNgoMission(String accessToken, int id) {
        newsFeedRepository.getNgoMission(accessToken,id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<NgoDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view != null)
                            view.onNetworkException(e);

                    }

                    @Override
                    public void onNext(NgoDetail ngoDetail) {
                        if (view != null)
                            view.onData(ngoDetail);
                    }
                });

    }

    @Override
    public void getNgoStories(String accessToken, int id) {
        newsFeedRepository.getNgoStories(accessToken,id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<NgoDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view != null)
                            view.onNetworkException(e);

                    }

                    @Override
                    public void onNext(NgoDetail ngoDetail) {
                        if (view != null)
                            view.onData(ngoDetail);
                    }
                });

    }

    @Override
    public void getNgoContact(String accessToken, int id) {
        newsFeedRepository.getNgoContact(accessToken,id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<NgoDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view != null)
                            view.onNetworkException(e);

                    }

                    @Override
                    public void onNext(NgoDetail ngoDetail) {
                        if (view != null)
                            view.onData(ngoDetail);
                    }
                });

    }

}
