package com.mpontus.popularmoviesapp.ui.MovieDetails;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.mpontus.popularmoviesapp.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailerViewHolder extends RecyclerView.ViewHolder implements MovieDetailsContract.TrailerItemView {

    @BindView(R.id.ivThumbnail)
    ImageView mThumnailView;

    private MovieDetailsContract.TrailerItemPresenter mPresenter;

    public TrailerViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    @Override
    public void attachPresenter(MovieDetailsContract.TrailerItemPresenter presenter) {
        if (mPresenter != null) {
            mPresenter.detach();
        }

        presenter.attach();
    }

    @Override
    public void setYoutubeVideoId(String videoId) {
        Uri thumbnail = Uri.parse("https://i.ytimg.com/vi/")
                .buildUpon()
                .appendPath(videoId)
                .appendPath("hqdefault.jpg")
                .build();

        Picasso.with(itemView.getContext())
                .load(thumbnail)
                .into(mThumnailView);
    }
}
