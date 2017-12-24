package by.grsu.artproject2.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import by.grsu.artproject2.DatabaseAdapter;
import by.grsu.artproject2.R;
import by.grsu.artproject2.model.Genre;

/**
 * Created by Алина on 21.12.2017.
 */

public class AddGenreFragment extends Fragment {
    static public int id=0;
    private DatabaseAdapter adapter;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        adapter=new DatabaseAdapter(getActivity());
        View view=inflater.inflate(R.layout.fragment_selected_item, container, false);
        if (id > 0) {
            // получаем элемент по id из бд
            adapter.open();
            Genre genre = adapter.getGenre(id);
            ((EditText)view.findViewById(R.id.name)).setText(genre.getName());
            if(adapter.isGenreLinked(AddGenreFragment.id)) {
                ((Button)view.findViewById(R.id.deleteButton)).setVisibility(View.GONE);
            }
            adapter.close();
        } else {
            // скрываем кнопку удаления
            ((Button)view.findViewById(R.id.deleteButton)).setVisibility(View.GONE);
        }
        return view;

    }

}
