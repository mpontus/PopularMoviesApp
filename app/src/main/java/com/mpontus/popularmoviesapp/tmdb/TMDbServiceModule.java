package com.mpontus.popularmoviesapp.tmdb;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mpontus.popularmoviesapp.BuildConfig;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class TMDbServiceModule {
    @Provides
    TMDbService provideTMDbService() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    HttpUrl originalUrl = originalRequest.url();

                    HttpUrl resultUrl = originalUrl.newBuilder()
                            .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                            .build();

                    Request resultRequest = originalRequest.newBuilder()
                            .url(resultUrl)
                            .build();

                    return chain.proceed(resultRequest);
                })
                .build();

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyy-MM-dd")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(TMDbService.class);
    }
}
