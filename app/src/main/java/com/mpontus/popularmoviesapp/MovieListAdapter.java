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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    final class MovieViewHolder extends RecyclerView.ViewHolder {
        final private Context mContext;
        final private OnClickListener mOnClickListener;

        private Movie mMovie;

        @BindView(R.id.ivPoster) ImageView mPosterView;

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
                    .load(movie.getPosterUrl(Movie.PosterSize.W342))
                    .into(mPosterView);
        }
    }
}
