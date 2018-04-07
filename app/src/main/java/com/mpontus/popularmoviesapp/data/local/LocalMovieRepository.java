package com.mpontus.popularmoviesapp.data.local;

import android.content.ContentResolver;
import android.database.Cursor;

import com.google.gson.Gson;
import com.mpontus.popularmoviesapp.tmdb.Movie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class LocalMovieRepository {

    private static final String[] PROJECTION_FIELDS =
            new String[]{PopularMoviesContract.FavoriteMoviesEntry.COLUMN_SERIALIZED_VALUE};

    private static final int SERIALIZED_VALUE_INDEX = 0;

    private ContentResolver mContentResolver;
    private Gson mGson;

    @Inject
    LocalMovieRepository(ContentResolver contentResolver, Gson gson) {
        mContentResolver = contentResolver;
    }

    public Observable<List<Movie>> getFavoriteMovies() {
        return Observable.defer(() -> {
            Cursor cursor = mContentResolver.query(PopularMoviesContract.FavoriteMoviesEntry.CONTENT_URL,
                    PROJECTION_FIELDS,
                    null,
                    null,
                    PopularMoviesContract.FavoriteMoviesEntry.COLUMN_CREATED_AT + " DESC");

            return Observable.just(cursorToList(cursor));
        });
    }

    private List<Movie> cursorToList(Cursor cursor) {
        ArrayList<Movie> result = new ArrayList<>();

        if (cursor == null || !cursor.moveToFirst()) {
            return result;
        }

        do {
            String serializedValue = cursor.getString(SERIALIZED_VALUE_INDEX);
            Movie movie = mGson.fromJson(serializedValue, Movie.class);

            result.add(movie);
        } while (cursor.moveToNext());

        cursor.close();

        return result;
    }
}
