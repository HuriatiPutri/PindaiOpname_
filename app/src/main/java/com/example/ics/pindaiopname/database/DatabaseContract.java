package com.example.ics.pindaiopname.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public final class DatabaseContract {

    public static final String AUTHORITY = "com.example.ics.pindaiopname";
    public static final String SCHEME = "content";

    private DatabaseContract(){}

    public static final class LokasiColumns implements BaseColumns {

        public static String TABLE_LOKASI = "lokasi";

        public static String ID_UNIT = "idUnit";
        public static String NAMA_UNIT = "namaUnit";
        public static String ID_LOKASI = "idLokasi";
        public static String NAMA_LOKASI = "namaLokasi";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_LOKASI)
                .build();

    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }

}

