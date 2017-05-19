package com.moive.sus.moviemodule;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.moive.sus.library.base.BaseLazyFragment;
import com.moive.sus.library.base.widget.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linksus on 5/18 0018.
 */
@Route(path = "/fragment/movie")
public class MovieFragment extends BaseLazyFragment implements MaterialSearchBar.OnSearchActionListener {

    private DrawerLayout drawer_layout;
    //    private Toolbar toolbar;
    private NavigationView movie_navigation_view;
    private List<String> lastSearches;
    private MaterialSearchBar searchBar;
    private MaterialViewPager mViewPager;

    @Override
    protected void loadLazyData() {

//        final View logo = findViewById(R.id.logo_white);
//        if (logo != null) {
//            logo.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mViewPager.notifyHeaderChanged();
//                }
//            });
//        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.movie_fragment_layout;
    }

    @Override
    protected void initViews(View rootView, Bundle savedInstanceState) {
        super.initViews(rootView, savedInstanceState);
        searchBar = (MaterialSearchBar) rootView.findViewById(R.id.searchBar);
        searchBar.setHint("请输入搜索内容");
        searchBar.setSpeechMode(true);
        //enable searchbar callbacks
        searchBar.setOnSearchActionListener(this);
        searchBar.setText("Hello World!");
        searchBar.setPlaceHolder("电影/影人");
        searchBar.setNavButtonEnabled(true);
        //restore last queries from disk
        lastSearches = loadSearchSuggestionFromDisk();
        searchBar.setLastSuggestions(lastSearches);
        drawer_layout = (DrawerLayout) rootView.findViewById(R.id.drawer_layout);
        movie_navigation_view = (NavigationView) rootView.findViewById(R.id.movie_navigation_view);
        mViewPager = (MaterialViewPager) rootView.findViewById(R.id.materialViewPager);
        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position % 2) {
                    default:
                        return TheatersFragment.newInstance();
                }


            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 2) {
                    case 0:
                        return "正在上映";
                    case 1:
                        return "即将上映";
                }
                return "";
            }
        });
        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.green,
                                "http://phandroid.s3.amazonaws.com/wp-content/uploads/2014/06/android_google_moutain_google_now_1920x1080_wallpaper_Wallpaper-HD_2560x1600_www.paperhi.com_-640x400.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue,
                                "http://www.hdiphonewallpapers.us/phone-wallpapers/540x960-1/540x960-mobile-wallpapers-hd-2218x5ox3.jpg");
                }

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

    }

    private List<String> loadSearchSuggestionFromDisk() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("search:" + i);
        }
        return list;
    }

    @Override
    protected void onRetryClick() {

    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_NAVIGATION:
                drawer_layout.openDrawer(Gravity.LEFT);
                break;
            case MaterialSearchBar.BUTTON_SPEECH:
//                openVoiceRecognizer();
        }
    }
}
