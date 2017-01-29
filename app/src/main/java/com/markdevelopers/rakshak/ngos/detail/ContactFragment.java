package com.markdevelopers.rakshak.ngos.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.markdevelopers.rakshak.R;
import com.markdevelopers.rakshak.common.BaseFragment;
import com.markdevelopers.rakshak.common.RakshakApp;
import com.markdevelopers.rakshak.data.local.SharedPreferenceManager;
import com.markdevelopers.rakshak.data.remote.models.NgoDetail;
import com.markdevelopers.rakshak.data.remote.models.NgoWrapper;
import com.markdevelopers.rakshak.data.repository.NewsFeedRepository;
import com.markdevelopers.rakshak.ngos.NgoContract;
import com.markdevelopers.rakshak.ngos.NgoPresenter;

/**
 * Created by Archish on 1/29/2017.
 */

public class ContactFragment extends BaseFragment implements NgoContract.NgoView {
    WebView wvMain;
    NgoPresenter ngoPresenter;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        wvMain = (WebView) view.findViewById(R.id.wvMain);
        NewsFeedRepository newsFeedRepository = ((RakshakApp) getActivity().getApplication()).getComponent().newsFeedRepository();
        ngoPresenter = new NgoPresenter(newsFeedRepository, this);
        int id = getActivity().getIntent().getExtras().getInt("id");
        ngoPresenter.getNgoContact(new SharedPreferenceManager(getActivity().getApplicationContext()).getAccessToken(), id);

        return view;
    }

    @Override
    public void onNgo(NgoWrapper ngoWrapper) {

    }

    @Override
    public void onData(NgoDetail ngoDetail) {
        for(int i = 0;i<ngoDetail.getData().size();i++)
        {
            wvMain.loadData(ngoDetail.getData().get(i).getCode(), "text/html; charset=utf-8", "UTF-8");
        }

    }


    @Override
    public void onNetworkException(Throwable e) {
        super.onNetworkException(e);
    }
}
