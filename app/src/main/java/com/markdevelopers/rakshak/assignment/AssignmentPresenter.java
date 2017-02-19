package com.markdevelopers.rakshak.assignment;

import com.markdevelopers.rakshak.data.remote.models.AssignmentWrapper;
import com.markdevelopers.rakshak.data.remote.models.UserResponse;
import com.markdevelopers.rakshak.data.repository.NewsFeedRepository;

import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Archish on 1/29/2017.
 */

public class AssignmentPresenter implements AssignmentContract.AssignmentPresenter {
    NewsFeedRepository newsFeedRepository;
    AssignmentContract.AssignmentView view;

    public AssignmentPresenter(NewsFeedRepository newsFeedRepository, AssignmentContract.AssignmentView view) {
        this.newsFeedRepository = newsFeedRepository;
        this.view = view;
    }

    @Override
    public void fetchAssignments(String accessToken) {
        newsFeedRepository.
                getAssignments(accessToken)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<AssignmentWrapper>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view != null)
                            view.onNetworkException(e);
                    }

                    @Override
                    public void onNext(AssignmentWrapper assignmentWrapper) {
                        if (view != null)
                            view.onAssign(assignmentWrapper);
                    }
                });
    }

    @Override
    public void onSendPost(String accessToken, String title, String description, RequestBody postimage, int did) {
        newsFeedRepository.setPost(accessToken, title, description, postimage, did)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
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
                            view.onSuccess(userResponse);
                    }
                });
    }

    @Override
    public void onSendPostNoImage(String accessToken, String title, String description, int did) {
        newsFeedRepository.setNewsPostNoImage(accessToken, title, description, did)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
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
                            view.onSuccess(userResponse);
                    }
                });

    }
}
