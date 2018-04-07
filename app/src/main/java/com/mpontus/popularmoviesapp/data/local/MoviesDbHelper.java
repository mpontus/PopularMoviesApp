package com.mpontus.popularmoviesapp.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MoviesDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movies.db";
    private static final int VERSION = 1;

    MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE " +
                PopularMoviesContract.FavoriteMoviesEntry.TABLE_NAME + " (" +
                PopularMoviesContract.FavoriteMoviesEntry._ID + " TEXT PRIMARY KEY, " +
                PopularMoviesContract.FavoriteMoviesEntry.COLUMN_SERIALIZED_VALUE +
                " TEXT NOT NULL, " +
                PopularMoviesContract.FavoriteMoviesEntry.COLUMN_CREATED_AT +
                " DATETIME DEFAULT CURRENT_TIMESTAMP);";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PopularMoviesContract.FavoriteMoviesEntry.TABLE_NAME);
        onCreate(db);
    }
}
