package com.ashish.movies.di.module;

import com.ashish.movies.db.dao.AppDao;
import com.ashish.movies.repository.AppRepository;
import com.ashish.movies.repository.api.ApiInterface;
import com.ashish.movies.repository.api.interceptors.AuthenticationInterceptor;
import com.ashish.movies.utils.AppConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {ViewModelModule.class, OkHttpClientModule.class, DatabaseModule.class, ApiModule.class})
public class AppModule {

    // --- REPOSITORY INJECTION ---
    @Provides
    Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    @Singleton
    AppRepository provideAppRepository(ApiInterface apiInterface, AppDao appDao, Executor executor) {
        return new AppRepository(apiInterface, appDao, executor);
    }
}
