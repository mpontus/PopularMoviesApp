package com.mpontus.popularmoviesapp.ui.MovieDetails;

interface MovieDetailsContract {
    interface View {
        void setTitle(String title);

        void setBackdrop(String backdropPath);

        void setDescription(String description);

        void setReviewCount(int count);

        void setVideoCount(int count);

        void setFavoriteChecked(boolean isChecked);
    }

    interface Presenter {
        void attach();

        void detach();

        void onFavoriteChanged(boolean isChecked);
    }

    interface ReviewItemView {
        void attachPresenter(ReviewItemPresenter presenter);

        void setContent(String text);
    }

    interface ReviewItemPresenter {
        void attach();

        void detach();
    }

    interface ReviewItemPresenterFactory {
        ReviewItemPresenter createReviewItemPresenter(ReviewItemView view, int position);
    }

    interface TrailerItemView {
        void attachPresenter(TrailerItemPresenter presenter);

        void setYoutubeVideoId(String id);
    }

    interface TrailerItemPresenter {
        void attach();

        void detach();

        void onClick();
    }

    interface TrailerItemPresenterFactory {
        TrailerItemPresenter createTrailerItemPresenter(TrailerItemView view, int position);
    }
}
