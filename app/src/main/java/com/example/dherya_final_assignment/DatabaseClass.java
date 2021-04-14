package com.example.dherya_final_assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseClass extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Final.db";
    public DatabaseClass(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    public static int user_id;


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user(_id integer primary key autoincrement, username text, password text)");
        db.execSQL("CREATE TABLE TODO(_id integer primary key autoincrement, todoName text, user_id integer, foreign key(user_id) references user (_id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
        db.execSQL("drop table if exists TODO");
        onCreate(db);
    }
    public boolean Insert(String username, String password){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = sqLiteDatabase.insert("user", null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public Boolean CheckUsername(String username){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM user WHERE username=?", new String[]{username});
        if(cursor.getCount() > 0){
            return false;
        }else{
            return true;
        }
    }

    public Boolean CheckLogin(String username, String password){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM user WHERE username=? AND password=?", new String[]{username, password});
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                user_id = cursor.getInt(0);
            }
            return true;
        }else{
            return false;
        }
    }

    public Cursor getTodoDetails(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return sqLiteDatabase.rawQuery("select * from TODO where user_id = ?",new String[]{String.valueOf(user_id)});
    }
    public boolean addTodo(String value)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("todoName", value);
        contentValues.put("user_id",user_id);
        long result = sqLiteDatabase.insert("TODO", null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public void updateTodo(String oldValue,String newValue)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("todoName",newValue);
        sqLiteDatabase.update("TODO",contentValues,"todoName=? and user_id=?",new String[]{oldValue, String.valueOf(user_id)});

    }
    public int deleteTodo(String Value)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete("TODO","todoName=? and user_id=?",new String[]{Value, String.valueOf(user_id)});
    }

    public boolean checkDublicate(String value)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM TODO WHERE todoName=? AND user_id=?", new String[]{value, String.valueOf(user_id)});
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                user_id = cursor.getInt(0);
            }
            return true;
        }else{
            return false;
        }
    }

}