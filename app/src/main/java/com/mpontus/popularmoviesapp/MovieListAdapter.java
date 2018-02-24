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
    private List<Movie> mMovies = new ArrayList<>();
    final private OnClickListener mOnClickListener;

    MovieListAdapter(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context)
                .inflate(R.layout.movie_card, parent, false);

        return new MovieViewHolder(context, v, mOnClickListener);
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

    interface OnClickListener {
        void onClick(View v, Movie m);
    }

    final class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final private Context mContext;
        final private ImageView mPoster;
        final private OnClickListener mOnClickListener;
        private Movie mMovie;

        MovieViewHolder(Context context, View itemView, OnClickListener onClickListener) {
            super(itemView);

            mContext = context;
            mPoster = itemView.findViewById(R.id.ivPoster);
            mOnClickListener = onClickListener;

            itemView.setOnClickListener(this);
        }

        void setMovie(Movie movie) {
            mMovie = movie;

            mPoster.setContentDescription(movie.title);

            Picasso.with(mContext)
                    .load(movie.getPosterUrl())
                    .into(mPoster);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onClick(v, mMovie);
        }
    }
}
