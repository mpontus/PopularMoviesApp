package com.mpontus.popularmoviesapp.ui.MovieList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpontus.popularmoviesapp.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.Nullable;

public class MovieListViewHolder extends RecyclerView.ViewHolder {
    private MovieListPresenter.ItemPresenter mPresenter;

    @Nullable
    @BindView(R.id.title)
    TextView mTitleView;
    @Nullable
    @BindView(R.id.backdrop)
    ImageView mBackdropView;
    @Nullable
    @BindView(R.id.poster)
    ImageView mPosterView;

    public MovieListViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(this::onClick);
    }

    public void attachPresenter(MovieListPresenter.ItemPresenter presenter) {
        if (mPresenter != null) {
            mPresenter.detach();
        }

        mPresenter = presenter;

        mPresenter.attach();
    }

    public void setTitle(String title) {
        if (mTitleView == null) {
            return;
        }

        mTitleView.setText(title);
    }

    public void setBackdrop(String backdropPath) {
        if (mBackdropView == null) {
            return;
        }

        Picasso.with(itemView.getContext())
                .load("https://image.tmdb.org/t/p/w300" + backdropPath)
                .into(mBackdropView);
    }

    public void setPoster(String posterPath) {
        if (mPosterView == null) {
            return;
        }

        Picasso.with(itemView.getContext())
                .load("https://image.tmdb.org/t/p/w342" + posterPath)
                .into(mPosterView);
    }

    public void onClick(View view) {
        if (mPresenter != null) {
            mPresenter.onClick(view, mBackdropView, mPosterView);
        }
    }
}
