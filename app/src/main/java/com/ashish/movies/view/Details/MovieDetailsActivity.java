package com.ashish.movies.view.Details;

import android.annotation.TargetApi;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ashish.movies.R;
import com.ashish.movies.adapter.MovieCastMemberAdapter;
import com.ashish.movies.adapter.MovieGenreAdapter;
import com.ashish.movies.adapter.MovieImageAdapter;
import com.ashish.movies.adapter.MovieVideoAdapter;
import com.ashish.movies.db.entity.MoviesEntity;
import com.ashish.movies.utils.AppConstants;
import com.ashish.movies.utils.AppUtils;
import com.ashish.movies.utils.BaseActivity;
import com.ashish.movies.view.interfaces.MovieVideoClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.ashish.movies.utils.AppConstants.MOVIE_DETAILS_PARCELABLE;
import static com.ashish.movies.utils.AppConstants.MOVIE_IMAGE_TRANSITION;

public class MovieDetailsActivity extends BaseActivity implements MovieVideoClickListener {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private MovieDetailsViewModel viewModel;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.imv_movie_poster)
    ImageView mImvMoviePoster;
    @BindView(R.id.imv_movie_banner)
    ImageView mImvMovieBanner;
    @BindView(R.id.txv_movie_name)
    TextView mTxvMovieName;
    @BindView(R.id.txv_movie_year)
    TextView mTxvMovieReleaseDate;
    @BindView(R.id.txv_movie_rating)
    TextView mTxvMovieRating;
    @BindView(R.id.tv_movie_description)
    TextView mTxvMovieDescription;
    @BindView(R.id.rv_movie_genre)
    RecyclerView mRvMovieGenre;
    @BindView(R.id.rv_cast_member)
    RecyclerView mRvMovieCastMember;
    @BindView(R.id.lay_movie_cast)
    LinearLayout mLayMovieCast;
    @BindView(R.id.rv_videos)
    RecyclerView mRvMovieVideos;
    @BindView(R.id.lay_movie_videos)
    LinearLayout mLayMovieVideos;
    @BindView(R.id.rv_movie_images)
    RecyclerView mRvMovieImages;
    @BindView(R.id.lay_movie_images)
    LinearLayout mLayMovieImages;
    @BindView(android.R.id.content)
    View view;

    private MoviesEntity moviesEntity;
    private String transitionName;
    private RoundedBitmapDrawable roundedBitmapDrawable;
    private MovieCastMemberAdapter castMemberAdapter;
    private MovieGenreAdapter movieGenreAdapter;
    private MovieVideoAdapter movieVideoAdapter;
    private MovieImageAdapter movieImageAdapter;


    @Override
    public void initView() {
        setContentView(R.layout.activity_movie_details);
//        supportPostponeEnterTransition();
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        this.configureDagger();
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailsViewModel.class);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        castMemberAdapter = new MovieCastMemberAdapter(MovieDetailsActivity.this);
        movieGenreAdapter = new MovieGenreAdapter(MovieDetailsActivity.this);
        movieVideoAdapter = new MovieVideoAdapter(MovieDetailsActivity.this);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvMovieCastMember.setLayoutManager(llm);
        mRvMovieCastMember.setAdapter(castMemberAdapter);
        mRvMovieCastMember.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = dpToPx();
                outRect.right = dpToPx() == state.getItemCount() - 1 ? dpToPx() : 0;
                outRect.top = 0;
                outRect.bottom = 0;
            }
        });
        mRvMovieCastMember.setNestedScrollingEnabled(true);

        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        flowLayoutManager.setAutoMeasureEnabled(true);
        mRvMovieGenre.setLayoutManager(flowLayoutManager);
        mRvMovieGenre.setAdapter(movieGenreAdapter);

        LinearLayoutManager llmVideos = new LinearLayoutManager(this);
        llmVideos.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvMovieVideos.setLayoutManager(llmVideos);
        mRvMovieVideos.setAdapter(movieVideoAdapter);
        mRvMovieVideos.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = dpToPx();
                outRect.right = dpToPx() == state.getItemCount() - 1 ? dpToPx() : 0;
                outRect.top = 0;
                outRect.bottom = 0;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            moviesEntity = (MoviesEntity) extras.getSerializable(MOVIE_DETAILS_PARCELABLE);
            transitionName = extras.getString(MOVIE_IMAGE_TRANSITION);
        }

//        supportPostponeEnterTransition();

        populateUi(moviesEntity, transitionName);

        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void configureDagger() {
        AndroidInjection.inject(this);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void populateUi(MoviesEntity moviesEntity, String transitionName) {
        startPostponedEnterTransition();
        Bitmap placeholder = BitmapFactory.decodeResource(getResources(), R.color.colorAccent);
        roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), placeholder);
        roundedBitmapDrawable.setCornerRadius(25F);
        if (moviesEntity != null) {
            String bannerImagePath = AppConstants.BACKDROP_BASE_PATH + moviesEntity.getBackdropPath();
            Glide.with(MovieDetailsActivity.this)
                    .load(bannerImagePath)
                    .apply(new RequestOptions()
                            .placeholder(R.color.colorAccent)
                            .error(R.color.colorAccent))
                    .into(mImvMovieBanner);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mImvMoviePoster.setTransitionName(transitionName);
        }

        loadMoviePosterImage(true);

        /*TransitionSet set = new TransitionSet();
        set.addTransition(new ChangeImageTransform());
        set.addTransition(new ChangeBounds());
        set.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                loadMoviePosterImage(false);
            }

            @Override
            public void onTransitionCancel(Transition transition) {
            }

            @Override
            public void onTransitionPause(Transition transition) {
            }

            @Override
            public void onTransitionResume(Transition transition) {
            }
        });*/

//        getWindow().setSharedElementEnterTransition(set);

        assert moviesEntity != null;
        mTxvMovieName.setText(moviesEntity.getTitle());
        String ratingAndCount = String.valueOf(moviesEntity.getVoteAverage()) + "/10 (" + moviesEntity.getVoteCount() + ")";
        mTxvMovieRating.setText(ratingAndCount);
        mTxvMovieReleaseDate.setText(AppUtils.convertDate(moviesEntity.getReleaseDate(), AppConstants.DF1, AppConstants.DF2));
        String overView = "Desc : " + moviesEntity.getOverview();
        mTxvMovieDescription.setText(overView);

        List<Integer> genreIds = moviesEntity.getGenreIds();
        loadGenre(genreIds);
        loadVideos(moviesEntity.getMovieId());
    }

    private void loadMoviePosterImage(boolean retrieveFromCache) {
        if (moviesEntity.getBackdropPath() != null) {
            Glide.with(this)
                    .load(AppConstants.POSTER_BASE_PATH + moviesEntity.getPosterPath())
                    .apply(new RequestOptions()
                                    .placeholder(roundedBitmapDrawable)
                                    .error(roundedBitmapDrawable)
                                    .dontAnimate()
                                    .dontTransform()
                            /*.onlyRetrieveFromCache(retrieveFromCache)*/)
                    .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(25, 0)))
                    /*.listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        supportStartPostponedEnterTransition();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        supportStartPostponedEnterTransition();
                            return false;
                        }
                    })*/
                    .into(mImvMoviePoster);
        } else {
            Glide.with(this)
                    .load(AppConstants.POSTER_BASE_PATH + moviesEntity.getPosterPath())
                    .apply(new RequestOptions()
                            .placeholder(roundedBitmapDrawable)
                            .error(roundedBitmapDrawable)
                            .dontAnimate()
                            .dontTransform())
                    .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(25, 0)))
                    .into(mImvMoviePoster);
        }

    }

    private void loadCastMember(int movieId) {
        viewModel.getMovieCastAndCrewByMovieId(movieId).observe(this, apiResponse -> {
            if (apiResponse != null) {
                if (apiResponse.getResponse() != null) {
                    if (apiResponse.getResponse().getCast() != null && !apiResponse.getResponse().getCast().isEmpty()) {
                        castMemberAdapter.addMovieCastMember(apiResponse.getResponse().getCast());
                        mLayMovieCast.setVisibility(View.VISIBLE);
                    } else {
                        mLayMovieCast.setVisibility(View.GONE);
                    }
                } else if (apiResponse.getT() != null) {
                    AppUtils.setSnackBar(view, getResources().getString(R.string.txt_some_error_occurred));
                }
            }
            loadVideos(movieId);
        });
    }

    private int dpToPx() {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics()));
    }

    private void loadGenre(List<Integer> genreId) {
        viewModel.getGenreById(genreId).observe(this, genreEntities -> {
            if (genreEntities != null) {
                if (!genreEntities.isEmpty()) {
                    movieGenreAdapter.addGenre(genreEntities);
                    mRvMovieGenre.setVisibility(View.VISIBLE);
                } else {
                    mRvMovieGenre.setVisibility(View.GONE);
                }
            }
        });
    }

    private void loadVideos(int movieId) {
        viewModel.getMovieVideosByMovieId(movieId).observe(this, apiResponse -> {
            if (apiResponse != null) {
                if (apiResponse.getResponse() != null) {
                    if (!apiResponse.getResponse().getResults().isEmpty()) {
                        movieVideoAdapter.addMovieVideo(apiResponse.getResponse().getResults());
                        mLayMovieVideos.setVisibility(View.VISIBLE);
                    } else {
                        mLayMovieVideos.setVisibility(View.GONE);
                    }
                } else if (apiResponse.getT() != null) {
                    AppUtils.setSnackBar(view, getResources().getString(R.string.txt_some_error_occurred));
                }
            }
            loadCastMember(movieId);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//        supportFinishAfterTransition();
    }

    @Override
    public void onVideoItemClickListener(String videoId) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
        startActivity(intent);
    }
}