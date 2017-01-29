package com.markdevelopers.rakshak.subscriptions;


import com.markdevelopers.rakshak.common.BaseContract;
import com.markdevelopers.rakshak.data.remote.models.SubscribeWrapper;
import com.markdevelopers.rakshak.data.remote.models.UserResponse;

/**
 * Created by Archish on 1/14/2017.
 */

public interface SubscriptionContract {
    interface NewsFeedActivityView extends BaseContract.BaseView {
        void onSubscribe(SubscribeWrapper subscribeWrapper);
        void markSubscribe(UserResponse userResponse);

    }

    interface NewsFeedActivityPresenter {
        void getSubscription(String accessToken);
        void markSubscribe(String accessToken, int nid);

    }
}
