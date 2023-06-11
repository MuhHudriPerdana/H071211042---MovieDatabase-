package com.example.finalmobile;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private BottomNavigationView bottomNavigationItemView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationItemView = findViewById(R.id.bn_main);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(this);
        bottomNavigationItemView.setSelectedItemId(R.id.menu_item_tv);

        actionBarSetColor();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.menu_item_movie:
                actionBarSetTitle(getString(R.string.item_movie));
                fragment = new MovieFragment();
                break;
            case R.id.menu_item_tv:
                actionBarSetTitle(getString(R.string.item_tv_show));
                fragment = new TvShowFragment();
                break;
            case R.id.menu_item_favorite:
                actionBarSetTitle(getString(R.string.item_favorite));
                fragment = new FavoriteFragment();
                break;
        }

        if(fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_main, fragment)
                    .commit();
            return true;
        }
        return true;
    }

    private void actionBarSetColor(){
        if(getSupportActionBar()!=null){
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#1A212F"));
            getSupportActionBar().setBackgroundDrawable(colorDrawable);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }
    }
    private void actionBarSetTitle(String title) {
        getSupportActionBar().setTitle(title);

    }

    public void nothing_happen(View view) {}
}