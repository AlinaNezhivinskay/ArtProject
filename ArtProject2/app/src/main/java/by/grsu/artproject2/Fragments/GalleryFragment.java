package by.grsu.artproject2.Fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import by.grsu.artproject2.DatabaseAdapter;
import by.grsu.artproject2.PictureFragmentPagerAdapter;
import by.grsu.artproject2.R;
import by.grsu.artproject2.model.Picture;

/**
 * Created by Алина on 21.12.2017.
 */

public class GalleryFragment extends Fragment {

    ViewPager pager;
    PagerAdapter pagerAdapter;
    DatabaseAdapter adapter;
    View view;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        pager=(ViewPager)view.findViewById(R.id.pager);
        pager.setAdapter(new PictureFragmentPagerAdapter(getActivity().getSupportFragmentManager(),adapter));
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapter=new DatabaseAdapter(getActivity());
        view=inflater.inflate(R.layout.fragment_gallery, container, false);

        pager=(ViewPager)view.findViewById(R.id.pager);
        pager.setAdapter(new PictureFragmentPagerAdapter(getActivity().getSupportFragmentManager(),adapter));

        /*pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });*/
        //pictureList = (ListView)view.findViewById(R.id.genre_list);
        return view;

    }
}
