package by.grsu.artproject2;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.DrawableUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ResourceBundle;

import by.grsu.artproject2.Fragments.PageFragment;
import by.grsu.artproject2.model.Picture;

/**
 * Created by Алина on 21.12.2017.
 */

public class PictureFragmentPagerAdapter extends FragmentPagerAdapter {
    DatabaseAdapter adapter;
    List<Picture> pictures;
    public PictureFragmentPagerAdapter(FragmentManager mgr,DatabaseAdapter adapter) {
        super(mgr);
        this.adapter=adapter;
        this.adapter.open();
        pictures=adapter.getPictures();
        this.adapter.close();
    }
    @Override
    public int getCount() {
        return pictures.size();
    }
    @Override
    public Fragment getItem(int position) {
        //Bitmap
       // Resources.getSystem()..getDrawable(R.mipmap.ic_launcher);
        //FileR
       // getDrawable(R.mipmap.ic_launcher);
        return(PageFragment.newInstance(position,pictures.get(position)));
    }

    public Picture getPicture(int pos){
        return pictures.get(pos);
    }
    private byte[] BitmaptoBytes(Bitmap b){
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG,70,stream);
        return stream.toByteArray();
    }

}
