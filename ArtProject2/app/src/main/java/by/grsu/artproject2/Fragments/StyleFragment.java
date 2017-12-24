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
import by.grsu.artproject2.model.Style;


public class StyleFragment extends Fragment {
    private ListView styleList;
    ArrayAdapter<Style> arrayAdapter;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_style, container, false);
        styleList = (ListView)view.findViewById(R.id.style_list);
        styleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Style style =arrayAdapter.getItem(position);
                if(style!=null) {
                    AddStyleFragment.id=style.getId();
                    ((MainActivity)getActivity()).addStyle(null);
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

        List<Style> styles = adapter.getStyles();

        arrayAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, styles);
        styleList.setAdapter(arrayAdapter);
        adapter.close();
    }
}
