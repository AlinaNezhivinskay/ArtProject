package by.grsu.artproject2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import by.grsu.artproject2.model.Artist;
import by.grsu.artproject2.model.Genre;
import by.grsu.artproject2.model.Picture;
import by.grsu.artproject2.model.Style;

/**
 * Created by Алина on 13.12.2017.
 */

public class DatabaseAdapter {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public DatabaseAdapter(Context context){
        databaseHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public DatabaseAdapter open(){
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        databaseHelper.close();
    }

    private Cursor getAllFromTable(String tableName){
        return  database.query(tableName, databaseHelper.getColumnsByTable(tableName), null, null, null, null, null);
        //return  database.rawQuery("SELECT * FROM " + tableName, null);
        /*String columns="";
        for(int i=0;i<databaseHelper.getColumnsByTable(tableName).length;i++){
            columns=columns+databaseHelper.getColumnsByTable(tableName)[i];
            if(i+1<databaseHelper.getColumnsByTable(tableName).length){
                columns=columns+", ";
            }
        }
        String query = "SELECT " + columns + " FROM " + tableName;
       return database.rawQuery(query, null);*/
    }

    public void setIconToPicture(byte[] icon, int pictureId) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COL_PICTURE, icon);
        database.update(DatabaseHelper.PICTURE_TABLE, cv, DatabaseHelper.COL_PICTURE_ID+"="+pictureId, new String[0]);
    }

    public List<Genre> getGenres(){
        ArrayList<Genre> genres = new ArrayList<>();
        Cursor cursor = getAllFromTable(DatabaseHelper.GENRE_TABLE);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_GENRE_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_GENRE_NAME));
                genres.add(new Genre(id, name));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return  genres;
    }
    public List<Style> getStyles(){
        ArrayList<Style> styles = new ArrayList<>();
        Cursor cursor = getAllFromTable(DatabaseHelper.STYLE_TABLE);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_STYLE_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_STYLE_NAME));
                styles.add(new Style(id, name));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return  styles;
    }
    public List<Picture> getPictures(){
        ArrayList<Picture> pictures = new ArrayList<>();
        Cursor cursor = getAllFromTable(DatabaseHelper.PICTURE_TABLE);
        if(cursor.moveToFirst()){
            do{
                Picture picture=new Picture();
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_PICTURE_ID));
                picture.setId(id);
                picture.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PICTURE_NAME)));
                picture.setYear(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_PICTURE_YEAR)));
                picture.setArtist(getArtist(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_PICTURE_ARTIST))));
                picture.setGenre(getGenre(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_PICTURE_GENRE))));
                picture.setStyle(getStyle(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_PICTURE_STYLE))));
                picture.setIcon(cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.COL_PICTURE)));
                pictures.add(picture);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return  pictures;
    }
    public List<Artist> getArtists(){
        ArrayList<Artist> artists = new ArrayList<>();
        Cursor cursor = getAllFromTable(DatabaseHelper.ARTIST_TABLE);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ARTIST_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ARTIST_NAME));
                String info = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ARTIST_INFO));
                artists.add(new Artist(id, name,info));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return artists;
    }

    public long getPictureCount(){
        return DatabaseUtils.queryNumEntries(database, DatabaseHelper.PICTURE_TABLE);
    }

    public Genre getGenre(int id){
        Genre genre = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DatabaseHelper.GENRE_TABLE, DatabaseHelper.COL_GENRE_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_GENRE_NAME));
            genre = new Genre(id, name);
        }
        cursor.close();
        return  genre;
    }
    public Style getStyle(int id){
        Style style = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DatabaseHelper.STYLE_TABLE, DatabaseHelper.COL_STYLE_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_STYLE_NAME));
            style = new Style(id, name);
        }
        cursor.close();
        return  style;
    }
    public Artist getArtist(int id){
        Artist artist = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DatabaseHelper.ARTIST_TABLE, DatabaseHelper.COL_ARTIST_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ARTIST_NAME));
            String info = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ARTIST_INFO));
            artist = new Artist(id, name,info);
        }
        cursor.close();
        return  artist;
    }
    public Picture getPicture(Integer id) {
        Picture picture = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DatabaseHelper.PICTURE_TABLE, DatabaseHelper.COL_PICTURE_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            picture = new Picture();
            picture.setId(id);
            picture.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PICTURE_NAME)));
            picture.setYear(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_PICTURE_YEAR)));
            picture.setArtist(getArtist(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_PICTURE_ARTIST))));
            picture.setGenre(getGenre(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_PICTURE_GENRE))));
            picture.setStyle(getStyle(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_PICTURE_STYLE))));
            picture.setIcon(cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.COL_PICTURE)));
        }
        cursor.close();
        return  picture;
    }

    public int insertPicture(Picture picture){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COL_PICTURE_NAME, picture.getName());
        if (picture.getArtist() != null) {
            cv.put(DatabaseHelper.COL_PICTURE_ARTIST, picture.getArtist().getId());
        }
        if (picture.getGenre() != null) {
            cv.put(DatabaseHelper.COL_PICTURE_GENRE, picture.getGenre().getId());
        }
        if (picture.getStyle() != null) {
            cv.put(DatabaseHelper.COL_PICTURE_STYLE, picture.getStyle().getId());
        }
        cv.put(DatabaseHelper.COL_PICTURE_YEAR, picture.getYear());
        cv.put(DatabaseHelper.COL_PICTURE, picture.getIcon());
        return (int)database.insert(DatabaseHelper.PICTURE_TABLE, null, cv);
    }
    public int insertGenre(Genre genre){

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COL_GENRE_NAME, genre.getName());
        return (int)database.insert(DatabaseHelper.GENRE_TABLE, null, cv);
    }

    public int insertStyle(Style style){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COL_STYLE_NAME, style.getName());
        return (int)database.insert(DatabaseHelper.STYLE_TABLE, null, cv);
    }

    public int insertArtist(Artist artist){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COL_ARTIST_NAME, artist.getName());
        cv.put(DatabaseHelper.COL_ARTIST_INFO, artist.getInformation());
        return (int)database.insert(DatabaseHelper.ARTIST_TABLE, null, cv);
    }

    public long deleteGenre(int genreId){
        String whereClause = DatabaseHelper.COL_GENRE_ID+" = ?";
        String[] whereArgs = new String[]{String.valueOf(genreId)};
        return database.delete(DatabaseHelper.GENRE_TABLE, whereClause, whereArgs);
    }

    public long updateGenre(Genre genre){
        String whereClause = DatabaseHelper.COL_GENRE_ID + "=" + String.valueOf(genre.getId());
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COL_GENRE_NAME, genre.getName());
        return database.update(DatabaseHelper.GENRE_TABLE, cv, whereClause, null);
    }
    public long deleteStyle(int styleId){
        String whereClause = DatabaseHelper.COL_STYLE_ID+" = ?";
        String[] whereArgs = new String[]{String.valueOf(styleId)};
        return database.delete(DatabaseHelper.STYLE_TABLE, whereClause, whereArgs);
    }

    public long updateStyle(Style style){
        String whereClause = DatabaseHelper.COL_STYLE_ID + "=" + String.valueOf(style.getId());
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COL_STYLE_NAME, style.getName());
        return database.update(DatabaseHelper.STYLE_TABLE, cv, whereClause, null);
    }
    public long deleteArtist(int artistId){
        String whereClause = DatabaseHelper.COL_ARTIST_ID+" = ?";
        String[] whereArgs = new String[]{String.valueOf(artistId)};
        return database.delete(DatabaseHelper.ARTIST_TABLE, whereClause, whereArgs);
    }
    public long deletePicture(int pictureId){
        String whereClause = DatabaseHelper.COL_PICTURE_ID+" = ?";
        String[] whereArgs = new String[]{String.valueOf(pictureId)};
        return database.delete(DatabaseHelper.PICTURE_TABLE, whereClause, whereArgs);
    }

    public long updateArtist(Artist artist){
        String whereClause = DatabaseHelper.COL_ARTIST_ID + "=" + String.valueOf(artist.getId());
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COL_ARTIST_NAME, artist.getName());
        cv.put(DatabaseHelper.COL_ARTIST_INFO, artist.getInformation());
        return database.update(DatabaseHelper.ARTIST_TABLE, cv, whereClause, null);
    }


    public boolean isGenreLinked(int id) {
        String query = String.format("SELECT count(*) FROM %s WHERE %s=?",DatabaseHelper.PICTURE_TABLE, DatabaseHelper.COL_PICTURE_GENRE);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        int count=0;
        if(cursor.moveToFirst()){
            count = cursor.getInt(0);
        }
        return count!=0;
    }
    public boolean isStyleLinked(int id) {
        String query = String.format("SELECT count(*) FROM %s WHERE %s=?",DatabaseHelper.PICTURE_TABLE, DatabaseHelper.COL_PICTURE_STYLE);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        int count=0;
        if(cursor.moveToFirst()){
            count = cursor.getInt(0);
        }
        return count!=0;
    }
    public boolean isArtistLinked(int id) {
        String query = String.format("SELECT count(*) FROM %s WHERE %s=?",DatabaseHelper.PICTURE_TABLE, DatabaseHelper.COL_PICTURE_ARTIST);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        int count=0;
        if(cursor.moveToFirst()){
            count = cursor.getInt(0);
        }
        return count!=0;
    }
}
