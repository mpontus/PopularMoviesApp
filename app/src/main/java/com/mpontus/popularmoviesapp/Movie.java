package com.mpontus.popularmoviesapp;

import android.net.Uri;

import java.util.List;


class Movie {

    /**
     * Relative image url of the movie's poster
     */
    private String posterPath;

    /**
     * Indicates whether the movie is adult-rated or not
     */
    boolean adult;

    /**
     * Short synopsis of the movie
     */
    String overview;

    /**
     * List of ids referencing movie genres
     *
     * See: https://developers.themoviedb.org/3/genres/get-movie-list
     */
    List<Integer> genreIds;

    /**
     * Movie ID in the TMDb
     */
    int id;

    /**
     * Movie title in the original language
     */
    String originalTitle;

    /**
     * Original language of the movie
     */
    String originalLanguage;

    /**
     * Movie title in the language specified in the request
     */
    String title;

    /**
     * Relative URL to the backdrop picture of the movie
     */
    String backdropPath;

    /**
     * Popularity score of the movie
     */
    float popularity;

    /**
     * Number of votes cast for the movie by TMDb users
     */
    int voteCount;

    /**
     * ???
     *
     * TODO: Find out what this attribute means
     */
    boolean video;

    /**
     * Average value of all votes cast for the movie by TMDB users
     */
    float voteAverage;

    /**
     * Return the absolute url of the movie poster
     *
     * @return URL Poster url
     */
    Uri getPosterUrl() {
        // TODO: Find a way to avoid hardcoding this string
        return Uri.parse("https://image.tmdb.org/t/p/w185/")
                .buildUpon()
                .appendEncodedPath(posterPath)
                .build();
    }
}
