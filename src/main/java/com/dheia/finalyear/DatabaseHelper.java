package com.dheia.finalyear;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Dheia on 13/11/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "people_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "name";
    private static final String COL3 = "quantity";
    private static final String COL4 = "coinprice";
    private static final String COL5 = "coinid";
    private static final String COL6 = "coinurl";
    private static final String COL7 = "coinsymbol";





    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT, " + COL3 + " TEXT, "+COL4+" TEXT, "+COL5+" TEXT, " + COL6+" TEXT, " + COL7+" TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String item, String quantity, String price, String id, String url, String symbol) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item);
        contentValues.put(COL3, quantity);
        contentValues.put(COL4, price);
        contentValues.put(COL5,id);
        contentValues.put(COL6,url);
        contentValues.put(COL7,symbol);



        Log.i("222222222222222","1: "+item+" 2: "+quantity+" 3: "+price);
        Log.d(TAG, "addData: Adding " + item + " and " + quantity + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
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

    /**
     * Returns only the ID that matches the name passed in
     *
     * @param name
     * @return
     */
    public Cursor getItemID(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Updates the qty field
     *
     * @param newQty
     * @param id
     * @param oldQty
     */
    public void updateQuantity(String newQty, int id, String oldQty) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL3 +
                " = '" + newQty + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL3 + " = '" + oldQty + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting Qty to " + newQty);
        db.execSQL(query);
    }

    //update the price of the coin stored on the db

    public void updatePrice(String newQty, int id, String oldQty) {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL4 +
                " = '" + newQty + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL4 + " = '" + oldQty + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName1: Setting Qty to " + newQty);
        Log.i("SSSSSSS ",newQty);
        db.execSQL(query);
    }




    /**
     * Delete from database
     *
     * @param id
     * @param name
     */
    public void deleteName(int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }

}
