package com.markdevelopers.rakshak.ngos.detail;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.gustavofao.materialtabs.SlidingFragmentPagerAdapter;
import com.gustavofao.materialtabs.SlidingTabLayout;
import com.gustavofao.materialtabs.TabType;
import com.markdevelopers.rakshak.R;
import com.markdevelopers.rakshak.common.BaseActivity;

public class NgoDetailActivity extends BaseActivity {

    private SlidingTabLayout tabLayout;
    private ViewPager viewPager;
    private TabAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ngo);

        tabLayout = (SlidingTabLayout) findViewById(R.id.tab_host);
        viewPager = (ViewPager) findViewById(R.id.view_pager);


        adapter = new TabAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter); // Necessario

        tabLayout.setTextSize(18);
        tabLayout.setDistributeEvenly(true);
        tabLayout.setTabType(TabType.TEXT_ONLY);
        tabLayout.setSelectedIndicatorColors(getResources().getColor(android.R.color.white));
        tabLayout.setSelectedIndicatorColors(getResources().getColor(android.R.color.white));
        tabLayout.setCustomFocusedColor(getResources().getColor(android.R.color.white));
        tabLayout.setCustomUnfocusedColor(getResources().getColor(android.R.color.darker_gray));

        tabLayout.setViewPager(viewPager); // Necessário
    }


    public class TabAdapter extends SlidingFragmentPagerAdapter {

        private String[] titles = {
                "Mission",
                "Success Stories",
                "Contact",
        };

        private int[] icons = {
                R.drawable.message,
                R.drawable.subscribe,
                R.drawable.lowdanger,

        };
        private Context context;

        public TabAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new MissionFragment();
                case 1:
                    return new StoriesFragment();
                case 2:
                    return new ContactFragment();

                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return icons.length == titles.length ? icons.length : 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Drawable getPageDrawable(int position) {
            return context.getResources().getDrawable(icons[position]);
        }

        @Override
        public String getToolbarTitle(int position) {
            return titles[position];
        }
    }


}
