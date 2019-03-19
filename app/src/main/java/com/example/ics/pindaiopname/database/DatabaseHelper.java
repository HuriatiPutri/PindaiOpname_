package com.example.ics.pindaiopname.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import static com.example.ics.pindaiopname.database.DatabaseContract.LokasiColumns.ID_UNIT;
import static com.example.ics.pindaiopname.database.DatabaseContract.LokasiColumns.NAMA_UNIT;
import static com.example.ics.pindaiopname.database.DatabaseContract.LokasiColumns.ID_LOKASI;
import static com.example.ics.pindaiopname.database.DatabaseContract.LokasiColumns.NAMA_LOKASI;
import static com.example.ics.pindaiopname.database.DatabaseContract.LokasiColumns.TABLE_LOKASI;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "db_lokasi";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_LOKASI = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_LOKASI,
            BaseColumns._ID,
            ID_UNIT,
            NAMA_UNIT,
            ID_LOKASI,
            NAMA_LOKASI
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_LOKASI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_LOKASI);
        onCreate(db);
    }
}
