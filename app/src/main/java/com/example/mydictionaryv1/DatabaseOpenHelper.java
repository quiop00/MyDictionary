package com.example.mydictionaryv1;

import android.content.Context;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenHelper extends SQLiteAssetHelper {
    private Context mContext;
    //public static final String DATABASE_NAME="eng_vn.db";

    public DatabaseOpenHelper(Context context,String DATABASE_NAME){
        super(context, DATABASE_NAME, null, 1);
        this.mContext=context;
    }

}
