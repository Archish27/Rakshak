package com.markdevelopers.rakshak.dashboard;

import com.markdevelopers.rakshak.data.remote.models.HomeWrapper;
import com.markdevelopers.rakshak.data.repository.UserRepository;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Archish on 1/29/2017.
 */

public class HomePresenter implements HomeContract.HomePresenter {

    UserRepository userRepository;
    HomeContract.HomeView homeView;

    public HomePresenter(UserRepository userRepository, HomeContract.HomeView homeView) {
        this.userRepository = userRepository;
        this.homeView = homeView;
    }

    @Override
    public void fetchHomeData(String accessToken) {
        userRepository.getHomeData(accessToken)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<HomeWrapper>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (homeView != null)
                            homeView.onNetworkException(e);
                    }

                    @Override
                    public void onNext(HomeWrapper homeWrapper) {
                        if (homeView != null)
                            homeView.onHomeData(homeWrapper);
                    }
                });
    }
}
