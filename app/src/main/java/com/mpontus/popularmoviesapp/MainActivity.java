package com.mpontus.popularmoviesapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    MovieListAdapter mMovieListAdapter;
    GridLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieListAdapter = new MovieListAdapter();
        mLayoutManager = new GridLayoutManager(this, 2);

        RecyclerView movieList = findViewById(R.id.rvMovies);
        movieList.setAdapter(mMovieListAdapter);
        movieList.setLayoutManager(mLayoutManager);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gsonBuilder.setDateFormat("yyyy-MM-dd");
        Gson gson = gsonBuilder.create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        TMDbService service = retrofit.create(TMDbService.class);

        service.getPopularMovies(BuildConfig.TMDB_API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(response -> response.results)
                .subscribe(movies -> {
                    mMovieListAdapter.setMovies(movies);
                });
    }

    private final class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {
        private List<Movie> mMovies;

        MovieListAdapter() {
            this(new ArrayList<>());
        }

        MovieListAdapter(List<Movie> movies) {
            mMovies = movies;
        }

        @Override
        public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            View v = LayoutInflater.from(context)
                    .inflate(R.layout.movie_card, parent, false);

            ViewGroup.LayoutParams p = v.getLayoutParams();

            p.width = parent.getWidth() / mLayoutManager.getSpanCount();
            p.height = (int) (4.8 / 3 * p.width);

            v.setLayoutParams(p);

            return new MovieViewHolder(context, v);
        }

        @Override
        public void onBindViewHolder(MovieViewHolder holder, int position) {
            Movie movie = mMovies.get(position);

            holder.setMovie(movie);
        }

        @Override
        public int getItemCount() {
            return mMovies.size();
        }

        public void setMovies(List<Movie> movies) {
            mMovies = movies;

            notifyDataSetChanged();
        }

        final class MovieViewHolder extends RecyclerView.ViewHolder {
            private Context mContext;
            private ImageView mPoster;

            MovieViewHolder(Context context, View itemView) {
                super(itemView);

                mContext = context;
                mPoster = itemView.findViewById(R.id.ivPoster);
            }

            void setMovie(Movie movie) {
                mPoster.setContentDescription(movie.title);

                Picasso.with(mContext)
                        .load(movie.getPosterUrl())
                        .into(mPoster);
            }
        }
    }
}
