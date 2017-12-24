package by.grsu.artproject2.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import by.grsu.artproject2.DatabaseAdapter;
import by.grsu.artproject2.MainActivity;
import by.grsu.artproject2.R;
import by.grsu.artproject2.model.Artist;

/**
 * Created by Алина on 21.12.2017.
 */

public class ArtistFragment extends Fragment {
    private ListView artistList;
    ArrayAdapter<Artist> arrayAdapter;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_artist, container, false);
        artistList = (ListView)view.findViewById(R.id.artist_list);
        artistList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Artist artist =arrayAdapter.getItem(position);
                if(artist!=null) {
                    AddArtistFragment.id=artist.getId();
                    ((MainActivity)getActivity()).addArtist(null);
                }
            }
        });
        return view;
    }

    public void addArtist(View view){

    }
    @Override
    public void onResume() {
        super.onResume();
        DatabaseAdapter adapter = new DatabaseAdapter(this.getActivity());
        adapter.open();

        List<Artist> artists = adapter.getArtists();

        arrayAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, artists);
        artistList.setAdapter(arrayAdapter);
        adapter.close();
    }
}
