package by.grsu.artproject2.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

import by.grsu.artproject2.DatabaseAdapter;
import by.grsu.artproject2.R;
import by.grsu.artproject2.model.Picture;

/**
 * Created by Алина on 21.12.2017.
 */

public class PageFragment extends Fragment {
    private int pageNumber;
    Picture picture;

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public static PageFragment newInstance(Integer page, Picture picture) {
        PageFragment fragment = new PageFragment();
        Bundle args=new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);
        fragment.setPicture(picture);
        return fragment;
    }

    public PageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //picture.setIcon(BitmaptoBytes());
        View result=inflater.inflate(R.layout.fragment_page, container, false);
        if(picture.getName()!=null)
        {
            TextView name=(TextView)result.findViewById(R.id.picture_name);
            name.setText(picture.getName());
        }
        TextView year=(TextView)result.findViewById(R.id.picture_year);
        year.setText(Integer.toString(picture.getYear()));
        if(picture.getArtist()!=null)
        {
            TextView artist=(TextView)result.findViewById(R.id.picture_artist);
            artist.setText(picture.getArtist().toString());
        }
        if(picture.getGenre()!=null)
        {
            TextView genre=(TextView)result.findViewById(R.id.picture_genre);
            genre.setText(picture.getGenre().toString());
        }
        if(picture.getStyle()!=null)
        {
            TextView style=(TextView)result.findViewById(R.id.picture_style);
            style.setText(picture.getStyle().toString());
        }
        ImageView imageView=(ImageView)result.findViewById(R.id.icon);
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(picture.getIcon(),0,picture.getIcon().length));
        return result;
    }

    /*private byte[] BitmaptoBytes(){
        Drawable dr=getResources().getDrawable(R.mipmap.ic_launcher);
        Bitmap bitmap=((BitmapDrawable)dr).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,70,stream);
        return stream.toByteArray();
    }*/
}

