package com.dheia.finalyear;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Dheia on 08/01/2019.
 */

public class QRDatabaseHelper extends SQLiteOpenHelper {


    private static final String TAG = "QRDatabaseHelper";

    private static final String TABLE_NAME = "QR_Table";
    private static final String COL1 = "ID";
    private static final String COL2 = "name";
    private static final String COL3 = "publicKey";
    private static final String COL4 = "privateKey";
    private static final String COL5 = "symbol";
    private static final String COL6 = "url";


    public QRDatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT, " + COL3 + " TEXT, " + COL4 + " TEXT, " + COL5 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addQRData(String name, String publicK, String symbol, String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, name);
        contentValues.put(COL3, publicK);
        contentValues.put(COL4, symbol);
        contentValues.put(COL5, url);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getQRData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getPublicKeyQR(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT "+COL3+" FROM " + TABLE_NAME+" WHERE " + COL5+" = '"+name+"'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getQRID(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateQRName(String newName, int id, String oldName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
                " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + oldName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    public void updatePublicKey(String newPublicKey, int id, String oldPublicKey) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL3 +
                " = '" + newPublicKey + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL3 + " = '" + oldPublicKey + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting publickey to " + newPublicKey);
        db.execSQL(query);
    }

    //update the price of the coin stored on the db

    public void updatePrivateKey(String newPrivateKey, int id, String oldPrivateKey) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL4 +
                " = '" + newPrivateKey + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL4 + " = '" + oldPrivateKey + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName1: Setting newPrivateKey to " + newPrivateKey);
        db.execSQL(query);
    }

    public void deleteQR(int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }

}
