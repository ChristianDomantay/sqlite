package com.example.christian.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    final static String DBNAME ="contestants.db";
    final static int VER = 1;
    final static String TABLE ="score";
    public DBHelper(Context context) {
        super(context, DBNAME, null, VER);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      String createTable =   "CREATE TABLE score (ID INTEGER PRIMARY KEY AUTOINCREMENT, FName Text, LName Text, Score INTEGER)";
      db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       String deleteTable ="DROP TABLE IF EXISTS score";
       db.execSQL(deleteTable);
       onCreate(db);
    }

    //@Override
    public boolean insert(String fname,String lname, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Fname",fname);
        cv.put("LName",lname);
        cv.put("Score",score);

       long isInserted = db.insert(TABLE,null, cv);

       //validation
       if(isInserted == -1){
           return false;

       }else {
           return true;
       }


    }

    public Cursor populateTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query ="SELECT * FROM score";
        return db.rawQuery(query, null);

    }

    public boolean update(String id, String fname, String lname, int score){
        SQLiteDatabase db = this.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("FName",fname);
                cv.put("LName",lname);
                cv.put("Score",score);
                db.update(TABLE, cv, "ID=?", new String[]{id});
                return true;
    }
    public boolean delete(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        db.delete(TABLE,"ID=?", new String[]{id});
        return true;
    }

}
