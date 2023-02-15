package com.bawp.testmall.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.bawp.testmall.Util.Constants;
import com.bawp.testmall.model.login_system;

import java.util.ArrayList;
import java.util.List;

public class Databasehandler extends SQLiteOpenHelper {
    public Databasehandler(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Create_table = " CREATE TABLE " + Constants.TABLE_NAME + " ( " + Constants.ID
                + " INTEGER PRIMARY KEY, " + Constants.full_name + " TEXT, "
                + Constants.Email + " TEXT, "
                + Constants.password + " TEXT, " + Constants.Conform_Password + " TEXT )";

        db.execSQL(Create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + Constants.TABLE_NAME);
        onCreate(db);

    }
    public void addData(login_system login_System){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.full_name,login_System.getFullName());
        values.put(Constants.Email,login_System.getEmail());
        values.put(Constants.password,login_System.getPassword());
        values.put(Constants.Conform_Password,login_System.getConformPassword());

        db.insert(Constants.TABLE_NAME,null,values);
        db.close();
    }
    public List<login_system> getAllData()
    {
        SQLiteDatabase db =this.getReadableDatabase();
        List<login_system> login_systems = new ArrayList<>();
        Cursor cursor =db.rawQuery("SELECT * FROM " + Constants.TABLE_NAME,null);
        if(cursor.moveToFirst())
        {
            do {
                login_system loginSystem = new login_system();
                loginSystem.setId(cursor.getInt(0));
                loginSystem.setFullName(cursor.getString(1));
                loginSystem.setEmail(cursor.getString(2));
                loginSystem.setPassword(cursor.getString(3));
                loginSystem.setConformPassword(cursor.getString(4));
                login_systems.add(loginSystem);
            }while (cursor.moveToNext());
        }
        return login_systems;
    }
}
