package com.mpontus.popularmoviesapp.ui.MovieList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpontus.popularmoviesapp.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListViewHolder extends RecyclerView.ViewHolder {
    private MovieListPresenter.ItemPresenter mPresenter;

    @BindView(R.id.title)
    TextView mTitleView;
    @BindView(R.id.backdrop)
    ImageView mBackdropView;

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
        mTitleView.setText(title);
    }

    public void setBackdrop(String backdropPath) {
        Picasso.with(itemView.getContext())
                .load("https://image.tmdb.org/t/p/w300" + backdropPath)
                .into(mBackdropView);
    }

    public void onClick(View view) {
        if (mPresenter != null) {
            mPresenter.onClick();
        }
    }
}
