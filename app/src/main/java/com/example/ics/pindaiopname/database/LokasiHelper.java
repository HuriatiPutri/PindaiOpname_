package com.example.ics.pindaiopname.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import com.example.ics.pindaiopname.model.LokasiDefault;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.ics.pindaiopname.database.DatabaseContract.LokasiColumns.ID_LOKASI;
import static com.example.ics.pindaiopname.database.DatabaseContract.LokasiColumns.ID_UNIT;
import static com.example.ics.pindaiopname.database.DatabaseContract.LokasiColumns.NAMA_LOKASI;
import static com.example.ics.pindaiopname.database.DatabaseContract.LokasiColumns.NAMA_UNIT;
import static com.example.ics.pindaiopname.database.DatabaseContract.LokasiColumns.TABLE_LOKASI;

public class LokasiHelper {

    private static String DATABASE_TABLE = TABLE_LOKASI;
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public LokasiHelper(Context context){
        this.context = context;
    }

    public LokasiHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public ArrayList<LokasiDefault> query() {
        ArrayList<LokasiDefault> arrayList = new ArrayList<LokasiDefault>();
        Cursor cursor = database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null, _ID + " DESC"
                , null);
        cursor.moveToFirst();
        LokasiDefault favorite;
        if (cursor.getCount() > 0) {
            do {

                favorite = new LokasiDefault();
                favorite.setID(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                favorite.setID_UNIT(cursor.getString(cursor.getColumnIndexOrThrow(ID_UNIT)));
                favorite.setNAMA_UNIT(cursor.getString(cursor.getColumnIndexOrThrow(NAMA_UNIT)));
                favorite.setID_LOKASI(cursor.getString(cursor.getColumnIndexOrThrow(ID_LOKASI)));
                favorite.setNAMA_LOKASI(cursor.getString(cursor.getColumnIndexOrThrow(NAMA_LOKASI)));

                arrayList.add(favorite);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null
                ,_ID + " = ?"
                , new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }

    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null
                ,_ID + " DESC");
    }

    public long insertProvider(ContentValues values){
//        Log.e("CONTENT VALUES :", ""+values.toString());
        return database.insert(DATABASE_TABLE,null, values);
    }

    public int updateProvider(String id,ContentValues values){
        return database.update(DATABASE_TABLE,values,_ID +" = ?",new String[]{id} );
    }

    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,_ID + " = ?", new String[]{id});
    }
}
