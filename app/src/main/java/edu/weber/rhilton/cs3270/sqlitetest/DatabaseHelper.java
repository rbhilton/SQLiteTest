package edu.weber.rhilton.cs3270.sqlitetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rbhilton on 2/26/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase database;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SQLiteDatabase open()
    {
        database = getWritableDatabase();
        return database;
    }

    public void close()
    {
        if (database != null)
            database.close();
    }

    public long insertSong(String title, String artist, String genre)
    {
        long rowID = -1;

        ContentValues newSong = new ContentValues();
        newSong.put("title", title);
        newSong.put("artist", artist);
        newSong.put("genre", genre);
        if (open() != null) {
            rowID = database.insert("songs",null, newSong);
            close();
        }
        return rowID;
    }

    public Cursor getAllSongs() {
        Cursor cursor = null;
        if (open() != null) {
            cursor=database.rawQuery("SELECT * FROM Songs", null);
        }

        return cursor;
    }

    public Cursor getOneSong(long id)
    {
        String[] params = new String[1];
        params[0] = "" + id;
        Cursor cursor = null;
        if (open() != null) {
            cursor = database.rawQuery("SELECT * FROM Songs WHERE _id = ?",params);
        }
        return cursor;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE Songs " +
                "(_id integer primary key autoincrement, " +
                "title TEXT, artist TEXT, genre TEXT);";
        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
