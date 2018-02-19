package com.mpontus.popularmoviesapp;

import java.util.List;


public class Movie {

    /**
     * Relative image url of the movie's poster
     */
    String posterPath;

    /**
     * Indicates whether the movie is adult-rated or not
     */
    boolean adult;

    /**
     * Short synopsis of the movie
     */
    String overview;

    /**
     * List of ids referending movie genres
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
}
