package com.mpontus.popularmoviesapp;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpontus.popularmoviesapp.tmdb.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.Nullable;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";

    @Nullable
    @BindView(R.id.ivBackdrop)
    ImageView mBackdropView;
    @BindView(R.id.ivPoster)
    ImageView mPosterView;
    @BindView(R.id.tvTitle)
    TextView mTitleView;
    @BindView(R.id.tvRating)
    TextView mRatingView;
    @BindView(R.id.tvDescription)
    TextView mDescriptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ButterKnife.bind(this);

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        String formattedTitle = getString(R.string.movie_title_format,
                movie.title,
                movie.getReleaseYear());

        String formattedRating = getString(R.string.movie_rating_format,
                movie.voteAverage,
                getResources().getQuantityString(R.plurals.numberOfVotes, movie.voteCount, movie.voteCount));


        mTitleView.setText(formattedTitle);
        mRatingView.setText(formattedRating);
        mDescriptionView.setText(movie.overview);

        Picasso.with(this)
                .load(movie.getPosterUrl(Movie.PosterSize.W780))
                .into(mPosterView);

        if (mBackdropView != null) {
            Picasso.with(this)
                    .load(movie.getBackdropUrl(Movie.BackdropSize.W780))
                    .into(mBackdropView);
        }
    }
}
