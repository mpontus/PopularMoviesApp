package com.mpontus.popularmoviesapp;

import java.util.List;

class MovieListResponse {

    /**
     * The page index of the response in the paginated listing
     */
    int page;

    /**
     * The list of movies in the response
     */
    List<Movie> results;

    /**
     * Total number of results in the paginated listing
     */
    int totalResults;

    /**
     * Total number of pages in the paginated listing
     */
    int totalPages;
}
