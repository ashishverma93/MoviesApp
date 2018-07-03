package com.ashish.movies.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.ashish.movies.di.interfaces.ViewModelKey;
import com.ashish.movies.factory.ViewModelFactory;
import com.ashish.movies.view.Details.MovieDetailsViewModel;
import com.ashish.movies.view.Home.HomeActivityViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeActivityViewModel.class)
    abstract ViewModel bindHomeViewModel(HomeActivityViewModel movieListActivityViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel.class)
    abstract ViewModel bindMovieDetailsViewModel(MovieDetailsViewModel movieDetailsViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);


}
