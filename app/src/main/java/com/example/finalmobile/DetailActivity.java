package com.example.finalmobile;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalmobile.adapters.CastAdapter;
import com.example.finalmobile.adapters.onclick.OnItemClickListener;
import com.example.finalmobile.local.RoomHelper;
import com.example.finalmobile.local.table.FavoriteMovie;
import com.example.finalmobile.local.table.FavoriteTv;
import com.example.finalmobile.models.Cast;
import com.example.finalmobile.models.CreditModel;
import com.example.finalmobile.models.DetailModel;
import com.example.finalmobile.models.movie.Movie;
import com.example.finalmobile.models.tvshow.TvShow;
import com.example.finalmobile.networks.Const;
import com.example.finalmobile.repository.MovieRepository;
import com.example.finalmobile.repository.TvShowRepository;
import com.example.finalmobile.repository.callback.OnCastCallback;
import com.example.finalmobile.repository.callback.OnDetailCallback;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener, OnItemClickListener {
    private MovieRepository movieRepository;

    private TvShowRepository tvShowRepository;

    private ImageView ivPoster, ivBackdrop;
    private TextView tvTitle, tvYear, tvDuration, tvSinopsis, tvRating, tvMore;
    private RatingBar ratingBar;
    private RecyclerView rvCast;

    private int EXTRAS_ID;
    private String EXTRAS_TYPE;
    private List<Cast> listCast;
    private List<String> listGenre;

    private RoomHelper roomHelper;
    private String favTitle, favImg;
    private Float favRate;
    private boolean isFavorite;

    private final String EXTRAS_DELETE_SUCCESS = "Data Berhasil Dihapus Dari Favorite";
    private final String EXTRAS_DELETE_FAILED = "Data Gagal Dihapus Dari Favorite";
    private final String EXTRAS_INSERT_SUCCESS = "Data Berhasil Ditambahkan Ke Favorite";
    private final String EXTRAS_INSERT_FAILED = "Data Gagal Ditambahkan Ke Favorite";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        if (getIntent() != null) {
            EXTRAS_ID = getIntent().getIntExtra("ID", 0);
        }

        ivPoster = findViewById(R.id.iv_mv_poster);
        ivBackdrop = findViewById(R.id.iv_mv_backdrop);
        tvTitle = findViewById(R.id.tv_detail_title);
        tvYear = findViewById(R.id.tv_detail_year);
        tvDuration = findViewById(R.id.tv_detail_duration);
        tvSinopsis = findViewById(R.id.tv_detail_synopsis);
        tvMore = findViewById(R.id.tv_detail_more);
        ratingBar = findViewById(R.id.rb_detail);
        tvRating = findViewById(R.id.tv_detail_score);
        rvCast = findViewById(R.id.rv_cast);

        roomHelper = new RoomHelper(this);
        listCast = new ArrayList<>();
        listGenre = new ArrayList<>();

        movieRepository = MovieRepository.getRetrofit();
        tvShowRepository = TvShowRepository.getRetrofit();

        if (getIntent() != null) {
            EXTRAS_ID = getIntent().getIntExtra("ID", 0);
            EXTRAS_TYPE = getIntent().getStringExtra("TYPE");
            loadData();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_detail_activity, menu);
        updateFavoriteItem(menu.getItem(0));
        return super.onCreateOptionsMenu(menu);
    }


    private void updateFavoriteItem(MenuItem item) {
        if(EXTRAS_TYPE.equals("movie")){
            isFavorite = roomHelper.checkFavMovie(EXTRAS_ID);
        }else{
            isFavorite = roomHelper.checkFavTv(EXTRAS_ID);
        }

        if(!isFavorite){
            item.setIcon(ContextCompat.getDrawable(this,R.drawable.ic_baseline_favorite_border_24));
        }else{
            item.setIcon(ContextCompat.getDrawable(this,R.drawable.ic_baseline_favorite_24));
            item.getIcon().setColorFilter(getResources().getColor(R.color.favRed), PorterDuff.Mode.SRC_ATOP);
        }
    }


    private void onClickFavoriteItem(MenuItem item){
        String textStatus = "";

        if(EXTRAS_TYPE.equals("movie")){
            if (!isFavorite) {
                textStatus = roomHelper.insertFavMovie(EXTRAS_ID, favTitle, favImg, favRate) == true? EXTRAS_INSERT_SUCCESS:EXTRAS_INSERT_FAILED;
            } else {
                textStatus = roomHelper.deleteFavMovie(EXTRAS_ID)==true?EXTRAS_DELETE_SUCCESS:EXTRAS_DELETE_FAILED;
            }
        }

        if(EXTRAS_TYPE.equals("tv")){
            if (!isFavorite) {
                textStatus = roomHelper.insertFavTv(EXTRAS_ID, favTitle, favImg, favRate) == true? EXTRAS_INSERT_SUCCESS:EXTRAS_INSERT_FAILED;
            } else {
                textStatus = roomHelper.deleteFavTv(EXTRAS_ID)==true?EXTRAS_DELETE_SUCCESS:EXTRAS_DELETE_FAILED;
            }
        }
        Toast.makeText(this, textStatus, Toast.LENGTH_SHORT).show();

        updateFavoriteItem(item);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_favorite:
                onClickFavoriteItem(item);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void loadData() {
        if(EXTRAS_TYPE.equals("movie")){
            movieRepository.getMovieDetail(EXTRAS_ID, new OnDetailCallback() {
                @Override
                public void onSuccess(DetailModel movie, String message) {
                    //add movie value on success
                    addValue(movie);
                }
                @Override
                public void onFailure(String message) {

                }
            });
        }

        else if(EXTRAS_TYPE.equals("tv")){
            tvShowRepository.getTvShowDetail(EXTRAS_ID, new OnDetailCallback() {
                @Override
                public void onSuccess(DetailModel tv, String message) {
                    addValue(tv);
                }
                @Override
                public void onFailure(String message) {

                }
            });
        }

    }

    private void addValue(DetailModel detailModel) {

        configureActionBar(detailModel.getTitle());

        configureWidgetValue(detailModel);


        configureFavoriteValue(detailModel);

        configureCastValue();

    }

    private void configureFavoriteValue(DetailModel detailModel) {
        favTitle = detailModel.getTitle();
        favImg = detailModel.getPoster();
        favRate = detailModel.getRating();
    }

    private void configureWidgetValue(DetailModel detailModel) {
        tvTitle.setText(detailModel.getTitle());
        tvSinopsis.setText(detailModel.getOverview());
        Glide.with(DetailActivity.this).load(Const.IMG_URL_300+ detailModel.getPoster()).into(ivPoster);
        Glide.with(DetailActivity.this).load(Const.IMG_URL_300+ detailModel.getBackdrop()).into(ivBackdrop);
        ratingBar.setRating(detailModel.getRating());
        tvRating.setText(detailModel.getVoteAverage());
        tvSinopsis.setOnClickListener(this);
        tvMore.setOnClickListener(this);
        if(EXTRAS_TYPE.equals("movie")){
            tvYear.setText(detailModel.getYear());
            tvDuration.setText(detailModel.getDuration());
        }
        if(EXTRAS_TYPE.equals("tv")){
            tvYear.setText(detailModel.getEps());
            tvDuration.setText(detailModel.getStatus());
        }
    }

    private void configureActionBar(String title) {
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle(title);
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#1A212F"));
            getSupportActionBar().setBackgroundDrawable(colorDrawable);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void configureCastValue() {
        if(EXTRAS_TYPE.equals("movie")){
            movieRepository.getMovieCast(EXTRAS_ID, new OnCastCallback() {
                @Override
                public void onSuccess(CreditModel creditModel, String message) {
                    listCast = creditModel.getCast();
                    rvCast.setLayoutManager(new LinearLayoutManager(DetailActivity.this, RecyclerView.HORIZONTAL,false));
                    rvCast.setAdapter(new CastAdapter(listCast));
                }
                @Override
                public void onFailure(String message) {

                }
            });
        }
        else if(EXTRAS_TYPE.equals("tv")){
            tvShowRepository.getTvShowCast(EXTRAS_ID, new OnCastCallback() {
                @Override
                public void onSuccess(CreditModel creditModel, String message) {
                    listCast = creditModel.getCast();
                    rvCast.setLayoutManager(new LinearLayoutManager(DetailActivity.this, RecyclerView.HORIZONTAL,false));
                    rvCast.setAdapter(new CastAdapter(listCast));
                }
                @Override
                public void onFailure(String message) {

                }
            });
        }

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_detail_synopsis:
                actionExpand();
                break;
            case R.id.tv_detail_more:
                actionExpand();
                break;
        }
    }

//    private void actionOpenYoutube() {
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+trailer));
//        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        startActivity(intent);
//    }

    private void actionExpand() {
        if (tvMore.getText().toString().equalsIgnoreCase("less")){
            tvSinopsis.setMaxLines(2);
            tvMore.setText("more");
        } else {
            tvSinopsis.setMaxLines(Integer.MAX_VALUE);
            tvMore.setText("less");
        }
    }

    @Override
    public void onItemClick(FavoriteMovie favoriteMovie) {}

    @Override
    public void onItemClick(FavoriteTv favoriteTv) {}

    @Override
    public void onItemClick(Movie movie) {
        Intent detailActivity = new Intent(this, DetailActivity.class);
        detailActivity.putExtra("ID", movie.getId());
        detailActivity.putExtra("TYPE", EXTRAS_TYPE);
        startActivity(detailActivity);
        finish();
    }

    @Override
    public void onItemClick(TvShow tvShow) {
        //intent to tvshow
        Intent detailActivity = new Intent(this, DetailActivity.class);
        detailActivity.putExtra("ID", tvShow.getId());
        detailActivity.putExtra("TYPE", EXTRAS_TYPE);
        startActivity(detailActivity);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}