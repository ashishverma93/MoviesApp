package com.ashish.movies.di.module;

import android.content.Context;

import com.ashish.movies.di.interfaces.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @ApplicationContext
    @Provides
    @Singleton
    public Context getContext() {
        return context.getApplicationContext();
    }
}
