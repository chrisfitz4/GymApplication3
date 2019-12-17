package com.example.gymapplication3.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class GymMatDatabase extends SQLiteOpenHelper {

    private static final String TAG = "TAG_F";
    private static final String DATABASE_NAME = "gym.db";
    private static final String TABLE_NAME = "gym_materials";

    private static int DATABASE_VERSION = 1;

    public static final String COLUMN_MATERIAL_ID = "material_id";
    public static final String COLUMN_MATERIAL_NAME = "material_name";
    public static final String COLUMN_MATERIAL_QUANTITY = "material_quantity";

    public GymMatDatabase(@Nullable Context context,
                          //@Nullable String name,
                          @Nullable SQLiteDatabase.CursorFactory factory//,
                          //int version
                          ) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: ");
        String createStatement = "CREATE TABLE "+ TABLE_NAME+
                " ("+
                COLUMN_MATERIAL_ID+" INTEGER PRIMARY KEY, "+
                COLUMN_MATERIAL_NAME+" TEXT, "+
                COLUMN_MATERIAL_QUANTITY+" INTEGER"+
                ")";
        db.execSQL(createStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: ");
        DATABASE_VERSION = newVersion;
        String updateQuery = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(updateQuery);
        onCreate(db);
    }

    //add howMany to the current amount in the db
    public void onPurchase(String material, int howMany){
        int num = howMany+getAmount(material);
        Log.d(TAG, "onPurchase: "+num);
        SQLiteDatabase db = getWritableDatabase();
        String setStatement = "UPDATE "+TABLE_NAME+
                " SET "+COLUMN_MATERIAL_QUANTITY
                +" = " +num+
                " WHERE "+COLUMN_MATERIAL_NAME+" = '"+material+"';";
        db.execSQL(setStatement);
    }

    public int getAmount(String material){
        Log.d(TAG, "getAmount: ");
        SQLiteDatabase db = getReadableDatabase();
        String getStatement = "SELECT "+COLUMN_MATERIAL_QUANTITY+
                " FROM "+TABLE_NAME+
                " WHERE "+COLUMN_MATERIAL_NAME+
                " = "+"'"+material+"';";
        Cursor value = db.rawQuery(getStatement,null);
        int n = 0;
        if(value.getCount()==1){
            value.moveToFirst();
            n = value.getInt(value.getColumnIndex(COLUMN_MATERIAL_QUANTITY));
        }
        Log.d("TAG_X", "getAmount: "+n);
        return n;
    }

    public Cursor getMaterials(){
        Log.d(TAG, "getMaterials: ");
        String selectStatement = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor materials = db.rawQuery(selectStatement,null);
        return materials;
    }

    public void insertMaterial(String material){
        Log.d(TAG, "insertMaterial: ");
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MATERIAL_NAME,material);
        contentValues.put(COLUMN_MATERIAL_QUANTITY,0);

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME,null,contentValues);
    }

}
