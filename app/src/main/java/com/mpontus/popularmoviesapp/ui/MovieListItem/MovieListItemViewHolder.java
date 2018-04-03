package com.mpontus.popularmoviesapp.ui.MovieListItem;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpontus.popularmoviesapp.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListItemViewHolder extends RecyclerView.ViewHolder implements MovieListItemContract.View {
    private MovieListItemContract.Presenter mPresenter;

    @BindView(R.id.title)
    TextView mTitleView;
    @BindView(R.id.backdrop)
    ImageView mBackdropView;

    public MovieListItemViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(this::onClick);
    }

    public void attachPresenter(MovieListItemContract.Presenter presenter) {
        if (mPresenter != null) {
            mPresenter.detach();
        }

        mPresenter = presenter;

        mPresenter.attach();
    }

    @Override
    public void setTitle(String title) {
        mTitleView.setText(title);
    }

    @Override
    public void setBackdrop(String backdropPath) {
        Picasso.with(itemView.getContext())
                .load("https://image.tmdb.org/t/p/w300" + backdropPath)
                .into(mBackdropView);
    }

    public void onClick(View view) {
        mPresenter.onClick();
    }
}
