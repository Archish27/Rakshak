package com.markdevelopers.rakshak.news;


import com.markdevelopers.rakshak.common.BaseContract;
import com.markdevelopers.rakshak.data.remote.models.NewsFeedWrapper;
import com.markdevelopers.rakshak.data.remote.models.UserResponse;

/**
 * Created by Archish on 1/14/2017.
 */

public interface NewsFeedContract {
    interface NewsFeedActivityView extends BaseContract.BaseView {
        void onNewsFeed(NewsFeedWrapper newsFeedWrapper);

        void markSubscribe(UserResponse userResponse);
    }

    interface NewsFeedActivityPresenter {
        void getNewsFeed(String accessToken);

        void markSubscribe(String accessToken, int nid);
    }
}
