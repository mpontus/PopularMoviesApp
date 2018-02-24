package com.mpontus.popularmoviesapp;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {
    private List<Movie> mMovies;

    MovieListAdapter() {
        this(new ArrayList<>());
    }

    MovieListAdapter(List<Movie> movies) {
        mMovies = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context)
                .inflate(R.layout.movie_card, parent, false);

        return new MovieViewHolder(context, v);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = mMovies.get(position);

        holder.setMovie(movie);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    void setMovies(List<Movie> movies) {
        mMovies = movies;

        notifyDataSetChanged();
    }

    final class MovieViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private ImageView mPoster;

        MovieViewHolder(Context context, View itemView) {
            super(itemView);

            mContext = context;
            mPoster = itemView.findViewById(R.id.ivPoster);
        }

        void setMovie(Movie movie) {
            mPoster.setContentDescription(movie.title);

            Picasso.with(mContext)
                    .load(movie.getPosterUrl())
                    .into(mPoster);
        }
    }
}
