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
            data.setId(cursor.getInt(0));
            data.setWord(cursor.getString(1));
            data.setContent(cursor.getString(2));
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
        while(!cursor.isAfterLast()){
            String word;
            word=cursor.getString(0);
            list.add(word);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
//    public int getIdByWord(String query){
//        int id=0;
//        Cursor cursor=database.rawQuery(query,null);
//        cursor.moveToFirst();
//        while(!cursor.isAfterLast()){
//            id=cursor.getInt(0);
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return id;
//    }
    public ArrayList<String> getWordsAroundId(String query,int id){
        ArrayList<String> list=new ArrayList<String>();
        Cursor cursor=database.rawQuery(query+" LIMIT "+(id-1)+",300" ,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            String word;
            word=cursor.getString(0);
            list.add(word);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}
