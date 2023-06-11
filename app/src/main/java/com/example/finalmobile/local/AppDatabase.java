package com.example.finalmobile.local;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Context;

import com.example.finalmobile.local.dao.FavoriteDao;
import com.example.finalmobile.local.table.FavoriteMovie;
import com.example.finalmobile.local.table.FavoriteTv;


@Database(entities = {FavoriteMovie.class, FavoriteTv.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FavoriteDao favoriteDao();

    private static AppDatabase instance;

    public synchronized static AppDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "Source Database").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
