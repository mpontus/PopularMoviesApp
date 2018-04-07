package com.mpontus.popularmoviesapp.data.local;

import android.net.Uri;
import android.provider.BaseColumns;

public class PopularMoviesContract {
    public static final String CONTENT_AUTHORITY = "com.mpontus.popularmoviesapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FAVORITE_MOVIES = "favoriteMovies";

    public static final class FavoriteMoviesEntry implements BaseColumns {
        public static final Uri CONTENT_URL = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FAVORITE_MOVIES)
                .build();

        public static final String TABLE_NAME = "favoriteMovies";

        public static final String COLUMN_SERIALIZED_VALUE = "serializedValue";

        public static final String COLUMN_CREATED_AT = "createdAt";
    }
}
