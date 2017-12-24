package by.grsu.artproject2.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import by.grsu.artproject2.DatabaseAdapter;
import by.grsu.artproject2.PictureFragmentPagerAdapter;
import by.grsu.artproject2.R;

/**
 * Created by Алина on 23.12.2017.
 */

public class AddPictureFragment extends Fragment {

    DatabaseAdapter adapter;

    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_add_picture, container, false);
        adapter=new DatabaseAdapter(getActivity());
        adapter.open();
        Spinner artists=(Spinner) view.findViewById(R.id.add_picture_artist);
        artists.setAdapter(new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, adapter.getArtists()));
        Spinner genres=(Spinner) view.findViewById(R.id.add_picture_genre);
        genres.setAdapter(new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, adapter.getGenres()));
        Spinner styles=(Spinner) view.findViewById(R.id.add_picture_style);
        styles.setAdapter(new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, adapter.getStyles()));
        adapter.close();

        return view;

    }
}
