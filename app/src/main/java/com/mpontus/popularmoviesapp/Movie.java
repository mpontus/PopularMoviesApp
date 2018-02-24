package com.mpontus.popularmoviesapp;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


class Movie implements Parcelable {

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.posterPath);
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.overview);
        dest.writeList(this.genreIds);
        dest.writeInt(this.id);
        dest.writeString(this.originalTitle);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.title);
        dest.writeString(this.backdropPath);
        dest.writeFloat(this.popularity);
        dest.writeInt(this.voteCount);
        dest.writeByte(this.video ? (byte) 1 : (byte) 0);
        dest.writeFloat(this.voteAverage);
    }

    public Movie() {
    }

    protected Movie(Parcel in) {
        this.posterPath = in.readString();
        this.adult = in.readByte() != 0;
        this.overview = in.readString();
        this.genreIds = new ArrayList<Integer>();
        in.readList(this.genreIds, Integer.class.getClassLoader());
        this.id = in.readInt();
        this.originalTitle = in.readString();
        this.originalLanguage = in.readString();
        this.title = in.readString();
        this.backdropPath = in.readString();
        this.popularity = in.readFloat();
        this.voteCount = in.readInt();
        this.video = in.readByte() != 0;
        this.voteAverage = in.readFloat();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
