package com.mpontus.popularmoviesapp.di;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mpontus.popularmoviesapp.tmdb.TMDbService;

import javax.inject.Singleton;

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
    private final String mBaseUrl;
    private final String mApiKey;

    public TMDbServiceModule(String baseUrl, String apiKey) {
        mBaseUrl = baseUrl;
        mApiKey = apiKey;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyy-MM-dd")
                .create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    HttpUrl originalUrl = originalRequest.url();

                    HttpUrl resultUrl = originalUrl.newBuilder()
                            .addQueryParameter("api_key", mApiKey)
                            .build();

                    Request resultRequest = originalRequest.newBuilder()
                            .url(resultUrl)
                            .build();

                    return chain.proceed(resultRequest);
                })
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    TMDbService provideTMDbService(Retrofit retrofit) {
        return retrofit.create(TMDbService.class);
    }
}