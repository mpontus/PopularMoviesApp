package com.mpontus.popularmoviesapp.tmdb;

import java.util.List;

public class MovieListResponse {

    /**
     * The page index of the response in the paginated listing
     */
    public int page;

    /**
     * The list of movies in the response
     */
    public List<Movie> results;

    /**
     * Total number of results in the paginated listing
     */
    public int totalResults;

    /**
     * Total number of pages in the paginated listing
     */
    public int totalPages;
}
