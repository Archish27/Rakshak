package com.markdevelopers.rakshak.subscriptions;

import com.markdevelopers.rakshak.common.BaseContract;
import com.markdevelopers.rakshak.data.remote.models.UpdateWrapper;

/**
 * Created by Archish on 1/29/2017.
 */

public interface UpdateContract {
    interface UpdateView extends BaseContract.BaseView {
        void onUpdates(UpdateWrapper updateWrapper);
    }

    interface UpdatePresenter {
        void fetchUpdates(String accessToken, int did);
    }
}
