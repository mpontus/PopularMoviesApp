package com.mpontus.popularmoviesapp.ui.MovieDetails;

interface MovieDetailsContract {
    interface View {
        void setTitle(String title);

        void setBackdrop(String backdropPath);

        void setDescription(String description);

        void showFavoriteButton();

        void hideFavoriteButton();

        void showUnfavoriteButton();

        void hideUnfavoriteButton();

        void setReviewCount(int count);

        void setVideoCount(int count);
    }

    interface Presenter {
        void attach();

        void detach();

        void onFavoriteClick();

        void onUnfavoriteClick();
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
