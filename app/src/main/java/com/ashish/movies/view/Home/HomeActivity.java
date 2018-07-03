package com.ashish.movies.view.Home;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashish.movies.R;
import com.ashish.movies.adapter.MovieListAdapter;
import com.ashish.movies.db.entity.MoviesEntity;
import com.ashish.movies.utils.AppUtils;
import com.ashish.movies.utils.BaseActivity;
import com.ashish.movies.utils.GridSpacingItemDecoration;
import com.ashish.movies.view.Details.MovieDetailsActivity;
import com.ashish.movies.view.interfaces.MovieListClickInterface;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

import static com.ashish.movies.utils.AppConstants.MOVIE_DETAILS_PARCELABLE;
import static com.ashish.movies.utils.AppConstants.MOVIE_IMAGE_TRANSITION;

public class HomeActivity extends BaseActivity implements MovieListClickInterface {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private HomeActivityViewModel viewModel;
    private MovieListAdapter mAdapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.swipe_movies)
    SwipeRefreshLayout mSwipeMovies;
    @BindView(R.id.rv_movies)
    RecyclerView mRvMovies;
    @BindView(R.id.txv_empty_view)
    TextView mTxvEmpty;
    @BindView(android.R.id.content)
    View view;

    @Override
    public void initView() {
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        this.configureDagger();
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeActivityViewModel.class);
        mAdapter = new MovieListAdapter(this);
        int recyclerViewSpanCount = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 4;
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, recyclerViewSpanCount);
        mRvMovies.setLayoutManager(mLayoutManager);
        mRvMovies.addItemDecoration(new GridSpacingItemDecoration(recyclerViewSpanCount, dpToPx(), true));
        mRvMovies.setItemAnimator(new DefaultItemAnimator());
        mRvMovies.setAdapter(mAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        loadMovies();
        mSwipeMovies.setOnRefreshListener(this::loadMovies);
    }

    private void loadMovies() {
        if (!mSwipeMovies.isRefreshing()) {
            showProgress();
        }
        if (AppUtils.isNetworkAvailable(HomeActivity.this)) {
            viewModel.getMovies().observe(this, apiResponse -> mHandler.postDelayed(() -> {
                if (apiResponse != null) {
                    if (apiResponse.getResponse() != null) {
                        if (apiResponse.getResponse().getResults() != null && !apiResponse.getResponse().getResults().isEmpty()) {
                            mAdapter.addMovies(apiResponse.getResponse().getResults());
                            mRvMovies.setVisibility(View.VISIBLE);
                            mTxvEmpty.setVisibility(View.GONE);
                        } else {
                            mRvMovies.setVisibility(View.GONE);
                            mTxvEmpty.setVisibility(View.VISIBLE);
                        }
                    } else if (apiResponse.getT() != null) {
                        AppUtils.setSnackBar(view, getResources().getString(R.string.txt_some_error_occurred));
                    }
                }
                hideProgress();
            }, 500));
            loadGenre();
        } else {
            viewModel.getMoviesFromDb().observe(this, moviesEntities -> mHandler.postDelayed(() -> {
                if (moviesEntities != null) {
                    mAdapter.addMovies(moviesEntities);
                    mRvMovies.setVisibility(View.VISIBLE);
                    mTxvEmpty.setVisibility(View.GONE);
                } else {
                    mRvMovies.setVisibility(View.GONE);
                    mTxvEmpty.setVisibility(View.VISIBLE);
                }
            }, 500));
            AppUtils.setSnackBar(view, getResources().getString(R.string.no_internet_connection));
            hideProgress();
            loadGenre();
        }
    }

    private void loadGenre() {
        viewModel.loadGenre().observe(this, apiResponse -> {
            if (apiResponse != null) {
                if (apiResponse.getResponse() != null) {
                    if (apiResponse.getResponse().getGenres() != null && !apiResponse.getResponse().getGenres().isEmpty()) {
                        // TODO Nothing
                    }
                } else if (apiResponse.getT() != null) {
                    AppUtils.setSnackBar(view, getResources().getString(R.string.txt_some_error_occurred));
                }
            }
        });
    }

    private void configureDagger() {
        AndroidInjection.inject(this);
    }

    private void showProgress() {
        mSwipeMovies.setRefreshing(true);
    }

    private void hideProgress() {
        if (mSwipeMovies.isRefreshing())
            mSwipeMovies.setRefreshing(false);
    }

    private int dpToPx() {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, r.getDisplayMetrics()));
    }

    @Override
    public void onMovieItemClickListener(int position, MoviesEntity moviesEntity, ImageView mImageView, String transitionName) {
        Intent intent = new Intent(HomeActivity.this, MovieDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MOVIE_DETAILS_PARCELABLE, moviesEntity);
        bundle.putString(MOVIE_IMAGE_TRANSITION, transitionName);
        intent.putExtras(bundle);
//        startActivity(intent);
//        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    HomeActivity.this,
                    mImageView,
                    transitionName);
            getWindow().setExitTransition(null);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }
}