package com.mpontus.popularmoviesapp.ui.MovieDetails;

import android.app.ActionBar;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpontus.popularmoviesapp.R;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import io.reactivex.annotations.Nullable;

public class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsContract.View {

    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";

    @Inject
    MovieDetailsContract.Presenter mPresenter;

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
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ActionBar actionBar = getActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ButterKnife.bind(this);

        mPresenter.attach();
    }

    @Override
    public void setTitle(String title, int year) {
        String formattedTitle = getString(R.string.movie_title_format, title, year);

        mTitleView.setText(formattedTitle);
    }

    @Override
    public void setBackdrop(Uri backdropUrl) {
        Picasso.with(this)
                .load(backdropUrl)
                .into(mBackdropView);
    }

    @Override
    public void setPoster(Uri posterUrl) {
        Picasso.with(this)
                .load(posterUrl)
                .into(mPosterView);
    }

    @Override
    public void setRating(float voteAverage, int voteCount) {
        String formattedRating = getString(R.string.movie_rating_format,
                voteAverage,
                getResources().getQuantityString(R.plurals.numberOfVotes, voteCount, voteCount));

        mRatingView.setText(formattedRating);
    }

    @Override
    public void setDescription(String description) {
        mDescriptionView.setText(description);
    }
}
