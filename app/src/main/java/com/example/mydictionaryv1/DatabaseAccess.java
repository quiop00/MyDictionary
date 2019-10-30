package com.example.mydictionaryv1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;
    public DatabaseAccess(Context context,String DATABASE_NAME){
        this.openHelper=new DatabaseOpenHelper(context,DATABASE_NAME);
    }
    public static DatabaseAccess getInstance(Context context,String DATABASE_NAME){
        if(instance ==null)
            instance=new DatabaseAccess(context,DATABASE_NAME);
        return instance;
    }
    public void open(){

        this.database=openHelper.getWritableDatabase();
    }
    public void close(){
        if(database!=null)
            this.database.close();
    }
    public ArrayList<Data> getWords(String query){

        ArrayList<Data> list=new ArrayList<Data>();
        Cursor cursor=database.rawQuery(query,null);
        cursor.moveToFirst();
        int dem=0;
        while(!cursor.isAfterLast()){
            dem++;
            Data data=new Data();
            data.setWord(cursor.getString(0));
            data.setContent(cursor.getString(1));
            list.add(data);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public ArrayList<String> getWord(String query){

        ArrayList<String> list=new ArrayList<String>();
        Cursor cursor=database.rawQuery(query,null);
        cursor.moveToFirst();
        int dem=0;
        while(!cursor.isAfterLast()){
            dem++;
            String word;
            word=cursor.getString(0);
            list.add(word);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}
