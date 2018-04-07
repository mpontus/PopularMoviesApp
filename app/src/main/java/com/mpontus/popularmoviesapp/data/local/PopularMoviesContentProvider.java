package com.mpontus.popularmoviesapp.data.local;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class PopularMoviesContentProvider extends ContentProvider {
    public static final int MOVIE_SOURCE_POPULAR = 1;
    public static final int MOVIE_SOURCE_TOP_RATED = 2;
    public static final int MOVIE_SOURCE_FAVORITE = 3;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int KEY_FAVORITE_MOVIES = 100;
    private static final int KEY_FAVORITE_MOVIE_WITH_ID = 101;

    static {
        sUriMatcher.addURI(PopularMoviesContract.CONTENT_AUTHORITY, PopularMoviesContract.PATH_FAVORITE_MOVIES, KEY_FAVORITE_MOVIES);
        sUriMatcher.addURI(PopularMoviesContract.CONTENT_AUTHORITY, PopularMoviesContract.PATH_FAVORITE_MOVIES + "/#", KEY_FAVORITE_MOVIE_WITH_ID);
    }

    private SQLiteOpenHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new MoviesDbHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor;

        switch (sUriMatcher.match(uri)) {
            case KEY_FAVORITE_MOVIES:
                cursor = db.query(PopularMoviesContract.FavoriteMoviesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;

            case KEY_FAVORITE_MOVIE_WITH_ID:
                String id = uri.getLastPathSegment();

                cursor = db.query(PopularMoviesContract.FavoriteMoviesEntry.TABLE_NAME,
                        projection,
                        "_id=?",
                        new String[]{id},
                        null,
                        null,
                        null);

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        Context context = getContext();

        if (context != null) {
            cursor.setNotificationUri(context.getContentResolver(), uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Uri returnUri;

        switch (sUriMatcher.match(uri)) {
            case KEY_FAVORITE_MOVIES:
                long id = db.insert(PopularMoviesContract.FavoriteMoviesEntry.TABLE_NAME,
                        null,
                        values);

                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(uri, id);
                } else {
                    throw new SQLException("Failed to insert row into: " + uri);
                }

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        Context context = getContext();

        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);
        }

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rowsDeleted;

        switch (sUriMatcher.match(uri)) {
            case KEY_FAVORITE_MOVIE_WITH_ID:
                String id = uri.getLastPathSegment();

                rowsDeleted = db.delete(PopularMoviesContract.FavoriteMoviesEntry.TABLE_NAME,
                        "_id=?",
                        new String[]{id});

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsDeleted != 0) {
            Context context = getContext();

            if (context != null) {
                context.getContentResolver().notifyChange(uri, null);
            }
        }

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
