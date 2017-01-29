package com.markdevelopers.rakshak.dashboard;

import com.markdevelopers.rakshak.common.BaseContract;
import com.markdevelopers.rakshak.data.remote.models.HomeWrapper;

/**
 * Created by Archish on 1/29/2017.
 */

public interface HomeContract {
    interface HomeView extends BaseContract.BaseView{
        void onHomeData(HomeWrapper homeWrapper);
    }
    interface HomePresenter{
        void fetchHomeData(String accessToken);
    }
}
