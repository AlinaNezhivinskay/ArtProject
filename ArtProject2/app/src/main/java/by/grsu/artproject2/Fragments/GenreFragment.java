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
import by.grsu.artproject2.model.Genre;


public class GenreFragment extends Fragment {

    private ListView genreList;
    ArrayAdapter<Genre> arrayAdapter;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_genre, container, false);
        genreList = (ListView)view.findViewById(R.id.genre_list);
        genreList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Genre genre =arrayAdapter.getItem(position);
                if(genre!=null) {
                    AddGenreFragment.id=genre.getId();
                    ((MainActivity)getActivity()).addGenre(null);
                }
            }
        });
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        DatabaseAdapter adapter = new DatabaseAdapter(this.getActivity());
        adapter.open();

        List<Genre> genres = adapter.getGenres();

        arrayAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, genres);
        genreList.setAdapter(arrayAdapter);
        adapter.close();
    }
}
