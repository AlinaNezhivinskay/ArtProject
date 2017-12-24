package by.grsu.artproject2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Алина on 13.12.2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "artDB.db";

    static final String PICTURE_TABLE ="picture";
    static final String COL_PICTURE_ID ="pictureid";
    static final String COL_PICTURE_YEAR="year";
    static final String COL_PICTURE_NAME="picturename";
    static final String COL_PICTURE ="picture";
    static final String COL_PICTURE_GENRE="genreid";
    static final String COL_PICTURE_STYLE="styleid";
    static final String COL_PICTURE_ARTIST="artistid";

    static final String ARTIST_TABLE ="artist";
    static final String COL_ARTIST_ID ="artistid";
    static final String COL_ARTIST_NAME="artistname";
    static final String COL_ARTIST_INFO="information";

    static final String GENRE_TABLE ="genre";
    static final String COL_GENRE_ID ="genreid";
    static final String COL_GENRE_NAME="genrename";

    static final String STYLE_TABLE ="style";
    static final String COL_STYLE_ID ="styleid";
    static final String COL_STYLE_NAME="stylename";

    private static final int SCHEMA = 1;

    Map<String,String[]> map;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
        map=new HashMap<>();
        map.put(ARTIST_TABLE,new String[]{COL_ARTIST_ID,COL_ARTIST_NAME,COL_ARTIST_INFO});
        map.put(GENRE_TABLE,new String[]{COL_GENRE_ID,COL_GENRE_NAME});
        map.put(STYLE_TABLE,new String[]{COL_STYLE_ID,COL_STYLE_NAME});
        map.put(PICTURE_TABLE,new String[]{COL_PICTURE_ID,COL_PICTURE_YEAR,COL_PICTURE_GENRE,COL_PICTURE_STYLE,COL_PICTURE_NAME,COL_PICTURE_ARTIST,COL_PICTURE});
    }

    public String[] getColumnsByTable(String table){
        return map.get(table);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ARTIST_TABLE+" ("+COL_ARTIST_ID+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
                COL_ARTIST_NAME+" TEXT,"+COL_ARTIST_INFO+"	TEXT);");
        db.execSQL("CREATE TABLE "+GENRE_TABLE+" ("+COL_GENRE_ID+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+COL_GENRE_NAME+
                " TEXT NOT NULL);");
        db.execSQL("CREATE TABLE "+STYLE_TABLE+" ("+COL_STYLE_ID+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+COL_STYLE_NAME+
                " TEXT NOT NULL);");
        db.execSQL("CREATE TABLE "+PICTURE_TABLE+" ("+COL_PICTURE_ID+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+COL_PICTURE_YEAR+
                " INTEGER,"+COL_PICTURE_GENRE+"	INTEGER,"+COL_PICTURE_STYLE+" INTEGER,"+COL_PICTURE_NAME+"	TEXT,"+COL_PICTURE_ARTIST+"	INTEGER NOT NULL,"+COL_PICTURE+
                " BLOB NOT NULL, FOREIGN KEY("+COL_PICTURE_GENRE+") REFERENCES "+GENRE_TABLE+"("+COL_GENRE_ID+"), FOREIGN KEY("+COL_PICTURE_STYLE+") REFERENCES "+STYLE_TABLE+"("+COL_STYLE_ID+"), " +
                "FOREIGN KEY("+COL_PICTURE_ARTIST+") REFERENCES "+ARTIST_TABLE+"("+COL_ARTIST_ID+"));");

        db.execSQL("INSERT INTO "+ GENRE_TABLE +" (" + COL_GENRE_NAME+ ") VALUES ('Марина');");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+PICTURE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+GENRE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+STYLE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ARTIST_TABLE);
        onCreate(db);
    }
}
