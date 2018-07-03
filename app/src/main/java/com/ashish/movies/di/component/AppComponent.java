package com.ashish.movies.di.component;

import android.app.Application;

import com.ashish.movies.application.App;
import com.ashish.movies.di.module.ActivityModule;
import com.ashish.movies.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {ActivityModule.class, AppModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(App app);
}
