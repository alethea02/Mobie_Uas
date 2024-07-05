package com.azhar.hitungpengeluaran.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.azhar.hitungpengeluaran.database.dao.DatabaseDao;
import com.azhar.hitungpengeluaran.model.ModelDatabase;

@Database(entities = {ModelDatabase.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract DatabaseDao databaseDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "keuangan_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

