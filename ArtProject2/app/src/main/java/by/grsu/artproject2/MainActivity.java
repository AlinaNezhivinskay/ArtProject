package by.grsu.artproject2;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import by.grsu.artproject2.Fragments.AddArtistFragment;
import by.grsu.artproject2.Fragments.AddGenreFragment;
import by.grsu.artproject2.Fragments.AddPictureFragment;
import by.grsu.artproject2.Fragments.AddStyleFragment;
import by.grsu.artproject2.Fragments.ArtistFragment;
import by.grsu.artproject2.Fragments.GalleryFragment;
import by.grsu.artproject2.Fragments.GenreFragment;
import by.grsu.artproject2.Fragments.PageFragment;
import by.grsu.artproject2.Fragments.StyleFragment;
import by.grsu.artproject2.model.Artist;
import by.grsu.artproject2.model.Genre;
import by.grsu.artproject2.model.Picture;
import by.grsu.artproject2.model.Style;

public class MainActivity extends AppCompatActivity {

    Fragment fragment = null;
    private DatabaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = new GalleryFragment();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();*/
        adapter = new DatabaseAdapter(this);

        //addPictute(null);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int id = item.getItemId();

       // Class<?> clazz=null;
            if (id == R.id.gallery_action) {
                fragment=new GalleryFragment();
                //clazz=GalleryFragment.class;
            }
            if (id == R.id.artists_action) {
                fragment = new ArtistFragment();
                //clazz=ArtistFragment.class;
            }
            if (id == R.id.genres_action) {
                fragment = new GenreFragment();
                //clazz=GenreFragment.class;
            }
            if (id == R.id.style_action) {
                fragment = new StyleFragment();
                //clazz=StyleFragment.class;
            }
        if (id == R.id.add_picture_action) {
            fragment = new AddPictureFragment();
            //clazz=StyleFragment.class;
        }

        /*if(fragmentManager.getFragments()!=null) {
            for (Fragment fr :fragmentManager.getFragments()) {
                if (fr.getClass().isAssignableFrom(clazz)) {
                    fragment = fr;
                    break;
                }
            }
        }*/

        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        setTitle(item.getTitle());
        return super.onOptionsItemSelected(item);
    }
    public void addGenre(View view){
        if(view!=null){
            AddGenreFragment.id=0;
        }
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container,new AddGenreFragment()).commit();
    }
    public void addStyle(View view){
        if(view!=null){
            AddStyleFragment.id=0;
        }
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container,new AddStyleFragment()).commit();
    }

    public void addArtist(View view){
        if(view!=null){
            AddArtistFragment.id=0;
        }
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container,new AddArtistFragment()).commit();
    }
    public void addPictute(View view){
        adapter.open();
        Picture picture=new Picture();
        picture.setName("Крик");
        picture.setArtist(adapter.getArtist(1));
        picture.setGenre(adapter.getGenre(1));
        picture.setStyle(adapter.getStyle(1));
        picture.setYear(2000);
        adapter.insertPicture(picture);
        adapter.close();
    }
    private void goHome(){
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();
    }
    public void save(View view){
        if(fragment instanceof GenreFragment){
            String name = ((EditText)findViewById(R.id.name)).getText().toString();
            Genre genre = new Genre(AddGenreFragment.id, name);
            adapter.open();
            if (AddGenreFragment.id > 0) {
                adapter.updateGenre(genre);
            } else {
                adapter.insertGenre(genre);
            }
            adapter.close();
        }
        else {
            String name = ((EditText)findViewById(R.id.name)).getText().toString();
            Style style = new Style(AddStyleFragment.id, name);
            adapter.open();
            if (AddStyleFragment.id > 0) {
                adapter.updateStyle(style);
            } else {
                adapter.insertStyle(style);
            }
            adapter.close();
        }
        goHome();

    }
    public void saveArtist(View view){
        String name = ((EditText)findViewById(R.id.artist_name)).getText().toString();
        String info = ((EditText)findViewById(R.id.info)).getText().toString();
        Artist artist = new Artist(AddArtistFragment.id, name,info);
        adapter.open();
        if (AddArtistFragment.id > 0) {
            adapter.updateArtist(artist);
        } else {
            adapter.insertArtist(artist);
        }
        adapter.close();
        goHome();
    }
    public void delete(View view) {
        if(fragment instanceof GenreFragment){
            adapter.open();
            adapter.deleteGenre(AddGenreFragment.id);
            adapter.close();
        }
        else {
            adapter.open();
            adapter.deleteStyle(AddStyleFragment.id);
            adapter.close();
        }
        goHome();
    }
    public void deleteArtist(View view) {
        adapter.open();
        adapter.deleteArtist(AddArtistFragment.id);
        adapter.close();
        goHome();
    }
    public void addIcon(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        final int SELECT_PHOTO = 1234;
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }
    Uri selectedImage;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 1234:
                if(resultCode == RESULT_OK){

                    selectedImage= imageReturnedIntent.getData();
                    ImageView photoRegistration = (ImageView) findViewById(R.id.add_icon);
                    try {
                        InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                        Bitmap selectedImages = BitmapFactory.decodeStream(imageStream);
                        photoRegistration.setImageBitmap(selectedImages);
                    }
                    catch (Exception e){
                    }
                }
        }
    }
    public void addPicture(View view) {
        Picture picture=new Picture();
        EditText name = (EditText) findViewById(R.id.add_picture_name);
        picture.setName(name.getText().toString());
        EditText year = (EditText) findViewById(R.id.add_picture_year);
        picture.setYear(Integer.parseInt(year.getText().toString()));
        Spinner artist =(Spinner) findViewById(R.id.add_picture_artist);
        picture.setArtist((Artist)artist.getSelectedItem());
        Spinner genre =(Spinner) findViewById(R.id.add_picture_genre);
        picture.setGenre((Genre)genre.getSelectedItem());
        Spinner style =(Spinner) findViewById(R.id.add_picture_style);
        picture.setStyle((Style) style.getSelectedItem());

        try {
            InputStream imageStream = getContentResolver().openInputStream(selectedImage);
            Bitmap selectedImages = BitmapFactory.decodeStream(imageStream);
            picture.setIcon(bitmaptoBytes(selectedImages));

        }
        catch (Exception e){
        }

        adapter.open();
        adapter.insertPicture(picture);
        adapter.close();
    }
    public void deletePicture(View view){
        ViewPager pagerView = (ViewPager)view.getParent().getParent().getParent();
        PictureFragmentPagerAdapter adapter = (PictureFragmentPagerAdapter)pagerView.getAdapter();
        this.adapter.open();
        this.adapter.deletePicture(adapter.getPicture(pagerView.getCurrentItem()).getId());
        this.adapter.close();
    }

    private byte[] bitmaptoBytes(Bitmap b){
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG,70,stream);
        return stream.toByteArray();
    }
}
