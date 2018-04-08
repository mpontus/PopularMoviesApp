package com.mpontus.popularmoviesapp.ui.MovieDetails;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpontus.popularmoviesapp.R;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.annotations.Nullable;

public class MovieDetailsActivity extends DaggerAppCompatActivity implements MovieDetailsContract.View {

    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";

    @Inject
    MovieDetailsContract.Presenter mPresenter;

    @Inject
    ReviewListAdapter mReviewListAdapter;

    @Nullable
    @BindView(R.id.ivBackdrop)
    ImageView mBackdropView;
    @BindView(R.id.tvTitle)
    TextView mTitleView;
    @BindView(R.id.tvDescription)
    TextView mDescriptionView;
    @BindView(R.id.btnFavorite)
    Button mFavoriteButton;
    @BindView(R.id.btnUnfavorite)
    Button mUnfavoriteButton;
    @BindView(R.id.rvReviews)
    RecyclerView mReviewsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_details);

        ActionBar actionBar = getActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ButterKnife.bind(this);

        mReviewsView.setAdapter(mReviewListAdapter);
        mReviewsView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

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

    public void showFavoriteButton() {
        mFavoriteButton.setVisibility(View.VISIBLE);
    }

    public void hideFavoriteButton() {
        mFavoriteButton.setVisibility(View.INVISIBLE);
    }

    public void showUnfavoriteButton() {
        mUnfavoriteButton.setVisibility(View.VISIBLE);
    }

    public void hideUnfavoriteButton() {
        mUnfavoriteButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setReviewCount(int count) {
        mReviewListAdapter.setItemCount(count);
    }

    @OnClick(R.id.btnFavorite)
    public void onFavoriteClick(View view) {
        mPresenter.onFavoriteClick();
    }

    @OnClick(R.id.btnUnfavorite)
    public void onUnfavoriteClick(View view) {
        mPresenter.onUnfavoriteClick();
    }
}
