package com.jackal.cowbeapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.jackal.cowbeapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    CallbackManager callbackManager;
    private AccessToken accessToken;
    private Toolbar toolBar;

    public MainActivityFragment() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        accessToken = AccessToken.getCurrentAccessToken();
        //startRequest();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected Toolbar setupToolBar(AppCompatActivity a) {
        toolBar = (Toolbar) getView().findViewById(R.id.toolbar);
        if (toolBar == null) return null;
        a.setSupportActionBar(toolBar);
        a.getSupportActionBar().setTitle("");
        a.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return toolBar;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager viewPager = (ViewPager) getView().findViewById(R.id.viewPager);
        CreateViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) getView().findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        Toast.makeText(getContext(), "position=" + position, Toast.LENGTH_SHORT).show();
                        setToolBarTitle((AppCompatActivity) getActivity(), "八卦");
                        break;
                    case 1:
                        Toast.makeText(getContext(), "position=" + position, Toast.LENGTH_SHORT).show();
                        setToolBarTitle((AppCompatActivity) getActivity(), "男女");
                        break;
                    case 2:
                        Toast.makeText(getContext(), "position=" + position, Toast.LENGTH_SHORT).show();
                        setToolBarTitle((AppCompatActivity) getActivity(), "職場");
                        break;
                    case 3:
                        Toast.makeText(getContext(), "position=" + position, Toast.LENGTH_SHORT).show();
                        setToolBarTitle((AppCompatActivity) getActivity(), "校園");
                        break;
                    case 4:
                        Toast.makeText(getContext(), "position=" + position, Toast.LENGTH_SHORT).show();
                        setToolBarTitle((AppCompatActivity) getActivity(), "娛樂");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupToolBar((AppCompatActivity) getActivity());


    }

    protected Toolbar setToolBarTitle(AppCompatActivity a, String title) {
        toolBar = (Toolbar) getView().findViewById(R.id.toolbar);
        if (toolBar == null) return null;
        a.setSupportActionBar(toolBar);
        a.getSupportActionBar().setTitle(title);
        a.getSupportActionBar().setDisplayShowTitleEnabled(false);
        return toolBar;
    }

    public void CreateViewPager(ViewPager viewPager) {
        String CowbeGossip[] = getResources().getStringArray(R.array.CowbeGossip);
        String CowbeBoyAndGirl[] = getResources().getStringArray(R.array.CowbeBoyAndGirl);
        String CowbeJob[] = getResources().getStringArray(R.array.CowbeJob);
        String CowbeFunny[] = getResources().getStringArray(R.array.CowbeFunny);
        String CowbeCampus[] = getResources().getStringArray(R.array.CowbeCampus);


        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        pagerAdapter.addFrag( BandFragment.newInstance("八卦", "",CowbeGossip ), "八卦");
        pagerAdapter.addFrag( BandFragment.newInstance("男女", "",CowbeBoyAndGirl ), "男女");
        pagerAdapter.addFrag( BandFragment.newInstance("職場", "",CowbeJob ), "職場");
        pagerAdapter.addFrag( BandFragment.newInstance("校園", "",CowbeFunny ), "校園");
        pagerAdapter.addFrag( BandFragment.newInstance("娛樂", "",CowbeCampus ), "娛樂");

        viewPager.setAdapter(pagerAdapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
