package com.mpontus.popularmoviesapp.tmdb;

import com.google.gson.annotations.SerializedName;

public class Video {
    public String id;

    @SerializedName("iso_639_1")
    public String iso_639_1;

    @SerializedName("iso_3166_1")
    public String iso_3166_1;

    public String key;

    public String name;

    public String site;

    public int size;

    public String type;
}
