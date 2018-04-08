package com.mpontus.popularmoviesapp.data.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.google.gson.Gson;
import com.mpontus.popularmoviesapp.tmdb.Movie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
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
        mGson = gson;
    }

    public Observable<List<Movie>> getFavoriteMovies() {
        return Observable.defer(() -> {
            Cursor cursor = mContentResolver.query(PopularMoviesContract.FavoriteMoviesEntry.CONTENT_URI,
                    PROJECTION_FIELDS,
                    null,
                    null,
                    PopularMoviesContract.FavoriteMoviesEntry.COLUMN_CREATED_AT + " DESC");

            return Observable.just(cursorToList(cursor));
        });
    }

    public Observable<Boolean> isMovieFavorite(Movie movie) {
        Uri movieUri = PopularMoviesContract.FavoriteMoviesEntry.CONTENT_URI
                .buildUpon()
                .appendEncodedPath(Integer.toString(movie.id))
                .build();

        return Observable.create(observer -> {
            Boolean isFavorite = false;

            Cursor cursor = mContentResolver.query(movieUri,
                    PROJECTION_FIELDS,
                    null,
                    null,
                    PopularMoviesContract.FavoriteMoviesEntry.COLUMN_CREATED_AT + " DESC");

            if (cursor != null) {
                isFavorite = cursor.getCount() > 0;

                cursor.close();
            }

            observer.onNext(isFavorite);
            observer.onComplete();
        });
    }

    public Completable favoriteMovie(Movie movie) {
        return Completable.create(observer -> {
            ContentValues values = new ContentValues();
            String serializedValue = mGson.toJson(movie);

            values.put(PopularMoviesContract.FavoriteMoviesEntry._ID, movie.id);
            values.put(PopularMoviesContract.FavoriteMoviesEntry.COLUMN_SERIALIZED_VALUE,
                    serializedValue);

            mContentResolver.insert(PopularMoviesContract.FavoriteMoviesEntry.CONTENT_URI, values);

            observer.onComplete();
        });
    }

    public Completable unfavoriteMovie(Movie movie) {
        return Completable.create(observer -> {
            Uri movieUri = PopularMoviesContract.FavoriteMoviesEntry.CONTENT_URI
                    .buildUpon()
                    .appendEncodedPath(Integer.toString(movie.id))
                    .build();

            mContentResolver.delete(movieUri, null, null);

            observer.onComplete();
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
