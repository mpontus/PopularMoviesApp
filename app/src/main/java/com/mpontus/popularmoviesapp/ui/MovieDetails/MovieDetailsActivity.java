package com.mpontus.popularmoviesapp.ui.MovieDetails;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mpontus.popularmoviesapp.R;
import com.mpontus.popularmoviesapp.ui.common.CheckableFloatingActionButton;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.annotations.Nullable;

public class MovieDetailsActivity extends DaggerAppCompatActivity implements MovieDetailsContract.View {

    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";

    @Inject
    MovieDetailsContract.Presenter mPresenter;

    @Inject
    ReviewListAdapter mReviewListAdapter;

    @Inject
    TrailerListAdapter mTrailerListAdapter;

    @Nullable
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ivBackdrop)
    ImageView mBackdropView;
    @BindView(R.id.tvTitle)
    TextView mTitleView;
    @BindView(R.id.ratingBar)
    RatingBar mRatingBar;
    @BindView(R.id.voteCount)
    TextView mVoteCount;
    @BindView(R.id.voteAverage)
    TextView mVoteAverage;
    @BindView(R.id.tvDescription)
    TextView mDescriptionView;
    @BindView(R.id.btnFavorite)
    CheckableFloatingActionButton mFavoriteButtonView;
    @BindView(R.id.rvReviews)
    RecyclerView mReviewsView;
    @BindView(R.id.rvTrailers)
    RecyclerView mTrailersView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }

        mFavoriteButtonView.setOnCheckedChangeListener((view, isChecked) -> {
            mPresenter.onFavoriteChanged(isChecked);
        });

        mReviewsView.setAdapter(mReviewListAdapter);
        mReviewsView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mTrailersView.setAdapter(mTrailerListAdapter);
        mTrailersView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mPresenter.attach();
    }

    public void setTitle(String title) {
        mTitleView.setText(title);
    }

    public void setDescription(String description) {
        mDescriptionView.setText(description);
    }

    public void setBackdrop(String backdropPath) {
        Picasso.with(this)
                .load("https://image.tmdb.org/t/p/w300" + backdropPath)
                .into(mBackdropView);
    }

    @Override
    public void setFavoriteChecked(boolean isChecked) {
        mFavoriteButtonView.setChecked(isChecked);
    }

    @Override
    public void setVoteAverage(float average) {
        mRatingBar.setRating(average / 2);
        mVoteAverage.setText(getString(R.string.vote_average_format, average));
    }

    @Override
    public void setVoteCount(int count) {
        mVoteCount.setText(getResources().getQuantityString(R.plurals.number_of_votes, count, count));
    }

    @Override
    public void setReviewCount(int count) {
        mReviewListAdapter.setItemCount(count);
    }

    @Override
    public void setVideoCount(int count) {
        mTrailerListAdapter.setItemCount(count);
    }
}
