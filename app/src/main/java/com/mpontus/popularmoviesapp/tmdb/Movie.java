package com.mpontus.popularmoviesapp.tmdb;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Movie implements Parcelable {

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    private static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/";
    /**
     * Indicates whether the movie is adult-rated or not
     */
    public boolean adult;
    /**
     * Short synopsis of the movie
     */
    public String overview;
    /**
     * Release date
     */
    public Date releaseDate;
    /**
     * List of ids referencing movie genres
     * <p>
     * See: https://developers.themoviedb.org/3/genres/get-movie-list
     */
    public List<Integer> genreIds;
    /**
     * Movie ID in the TMDb
     */
    public int id;
    /**
     * Movie title in the original language
     */
    public String originalTitle;
    /**
     * Original language of the movie
     */
    public String originalLanguage;
    /**
     * Movie title in the language specified in the request
     */
    public String title;
    /**
     * Relative URL to the backdrop picture of the movie
     */
    public String backdropPath;
    /**
     * Popularity score of the movie
     */
    public float popularity;
    /**
     * Number of votes cast for the movie by TMDb users
     */
    public int voteCount;
    /**
     * ???
     */
    public boolean video;
    /**
     * Average value of all votes cast for the movie by TMDB users
     */
    public float voteAverage;
    /**
     * Relative image url of the movie's poster
     */
    private String posterPath;

    public Movie() {
    }

    protected Movie(Parcel in) {
        this.posterPath = in.readString();
        this.adult = in.readByte() != 0;
        this.overview = in.readString();
        long tmpReleaseDate = in.readLong();
        this.releaseDate = tmpReleaseDate == -1 ? null : new Date(tmpReleaseDate);
        this.genreIds = new ArrayList<>();
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

    /**
     * Returns release year
     */
    public int getReleaseYear() {
        Calendar c = Calendar.getInstance();

        c.setTime(releaseDate);

        return c.get(Calendar.YEAR);
    }

    /**
     * Return the absolute url of the movie poster
     *
     * @return URL Poster url
     */
    public Uri getPosterUrl(PosterSize size) {
        // TODO: Find a way to avoid hardcoding this string
        return Uri.parse(BASE_IMAGE_URL)
                .buildUpon()
                .appendPath(size.toString())
                .appendEncodedPath(posterPath)
                .build();
    }

    public Uri getPosterUrl() {
        return getPosterUrl(PosterSize.ORIGINAL);
    }

    /**
     * Return the absolute url of the movie poster
     *
     * @return URL Poster url
     */
    public Uri getBackdropUrl(BackdropSize size) {
        // TODO: Find a way to avoid hardcoding this string
        return Uri.parse(BASE_IMAGE_URL)
                .buildUpon()
                .appendPath(size.toString())
                .appendEncodedPath(backdropPath)
                .build();
    }

    public Uri getBackdropUrl() {
        return getBackdropUrl(BackdropSize.ORIGINAL);
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
        dest.writeLong(this.releaseDate != null ? this.releaseDate.getTime() : -1);
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

    public enum PosterSize {
        W92("w92"),
        W154("w154"),
        W185("w185"),
        W342("w342"),
        W500("w500"),
        W780("w780"),
        ORIGINAL("original");

        private final String code;

        PosterSize(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return this.code;
        }
    }

    public enum BackdropSize {
        W300("w300"),
        W780("w780"),
        W1280("w1280"),
        ORIGINAL("original");

        private final String code;

        BackdropSize(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return this.code;
        }
    }
}
