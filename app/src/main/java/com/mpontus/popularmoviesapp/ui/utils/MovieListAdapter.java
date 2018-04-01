package com.mpontus.popularmoviesapp.ui.utils;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mpontus.popularmoviesapp.R;
import com.mpontus.popularmoviesapp.tmdb.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {
    final private OnClickListener mOnClickListener;
    private List<Movie> mMovies = new ArrayList<>();

    public MovieListAdapter(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context)
                .inflate(R.layout.movie_item, parent, false);

        return new MovieViewHolder(context, v, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = mMovies.get(position);

        holder.setMovie(movie);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void setMovies(List<Movie> movies) {
        mMovies = movies;

        notifyDataSetChanged();
    }

    public interface OnClickListener {
        void onClick(View v, Movie m);
    }

    final class MovieViewHolder extends RecyclerView.ViewHolder {
        final private Context mContext;
        final private OnClickListener mOnClickListener;
        @BindView(R.id.ivPoster)
        ImageView mPosterView;
        private Movie mMovie;

        MovieViewHolder(Context context, View itemView, OnClickListener onClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mContext = context;
            mOnClickListener = onClickListener;
        }

        @OnClick
        void onClick(View v) {
            mOnClickListener.onClick(v, mMovie);
        }

        void setMovie(Movie movie) {
            mMovie = movie;

            mPosterView.setContentDescription(movie.title);

            Picasso.with(mContext)
                    .load(movie.getPosterUrl(Movie.POSTER_SIZE_W342))
                    .into(mPosterView);
        }
    }
}