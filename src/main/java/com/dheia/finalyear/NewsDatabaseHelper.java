package com.dheia.finalyear;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Dheia on 13/11/2018.
 */

public class NewsDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "NewsDatabaseHelper";

    private static final String TABLE_NAME = "news_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "site";
    private static final String COL3 = "title";
    private static final String COL4 = "body";
    private static final String COL5 = "url";
    private static final String COL6 = "date";
    private static final String COL7 = "imgurl";



    public NewsDatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                COL2 + " TEXT, " + COL3 + " TEXT, " + COL4 + " TEXT, " + COL5 + " TEXT, " + COL6 + " INTEGER)";
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT, " + COL3 + " TEXT, " + COL4 + " TEXT, " + COL5 + " TEXT, " + COL6 + " INTEGER, "+COL7+ " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String sitename, String title, String body, String url, int date,String imgurl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, sitename);
        contentValues.put(COL3, title);
        contentValues.put(COL4, body);
        contentValues.put(COL5, url);
        contentValues.put(COL6, date);
        contentValues.put(COL7, imgurl);





        Log.d(TAG, "addData: Adding " + sitename + " and " + title + " to " + TABLE_NAME);
        Log.d(TAG, "addData: "+Integer.toString(date));

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            Log.d(TAG, "addData: false");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns all the data from database
     *
     * @return
     */
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
//
//    /**
//     * Returns only the ID that matches the name passed in
//     *
//     * @param name
//     * @return
//     */
//    public Cursor getItemID(String name) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
//                " WHERE " + COL2 + " = '" + name + "'";
//        Cursor data = db.rawQuery(query, null);
//        return data;
//    }
//
//    /**
//     * Updates the name field
//     *
//     * @param newName
//     * @param id
//     * @param oldName
//     */
//    public void updateName(String newName, int id, String oldName) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
//                " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" +
//                " AND " + COL2 + " = '" + oldName + "'";
//        Log.d(TAG, "updateName: query: " + query);
//        Log.d(TAG, "updateName: Setting name to " + newName);
//        db.execSQL(query);
//    }
//
//    /**
//     * Updates the qty field
//     *
//     * @param newQty
//     * @param id
//     * @param oldQty
//     */
//    public void updateQuantity(String newQty, int id, String oldQty) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "UPDATE " + TABLE_NAME + " SET " + COL3 +
//                " = '" + newQty + "' WHERE " + COL1 + " = '" + id + "'" +
//                " AND " + COL3 + " = '" + oldQty + "'";
//        Log.d(TAG, "updateName: query: " + query);
//        Log.d(TAG, "updateName: Setting Qty to " + newQty);
//        db.execSQL(query);
//    }
//
//
//    /**
//     * Delete from database
//     *
//     * @param id
//     * @param name
//     */
//    public void deleteName(int id, String name) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
//                + COL1 + " = '" + id + "'" +
//                " AND " + COL2 + " = '" + name + "'";
//        Log.d(TAG, "deleteName: query: " + query);
//        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
//        db.execSQL(query);
//    }

}
