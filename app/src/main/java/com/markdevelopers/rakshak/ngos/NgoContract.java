package com.markdevelopers.rakshak.ngos;


import com.markdevelopers.rakshak.common.BaseContract;
import com.markdevelopers.rakshak.data.remote.models.NgoDetail;
import com.markdevelopers.rakshak.data.remote.models.NgoWrapper;

/**
 * Created by Archish on 1/14/2017.
 */

public interface NgoContract {
    interface NgoView extends BaseContract.BaseView {
        void onNgo(NgoWrapper ngoWrapper);

        void onData(NgoDetail ngoDetail);
    }

    interface NgoPresenter {
        void getNgo(String accessToken);

        void getNgoMission(String accessToken, int id);
        void getNgoStories(String accessToken, int id);
        void getNgoContact(String accessToken, int id);

    }
}
