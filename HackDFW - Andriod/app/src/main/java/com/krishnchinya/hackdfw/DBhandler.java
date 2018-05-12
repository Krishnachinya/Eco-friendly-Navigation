package com.krishnchinya.hackdfw;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by KrishnChinya on 4/21/17.
 */

public class DBhandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 7;
    // Database Name
    private static final String DATABASE_NAME = "hackdfw";
    // Contacts table name
    private static final String TABLE_REGISTRATION = "REGISTRATION";
    private static final String TABLE_LOGIN = "LOGIN";


    public DBhandler(Context context) {
        super(context, DATABASE_NAME, null ,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_REGISTRATION = "create table "+ TABLE_REGISTRATION + " (fname TEXT , lname TEXT,"+
                "DOB TEXT, CELL TEXT, mailId TEXT PRIMARY KEY, carNumber TEXT,hpincode TEXT, opincode TEXT)";

        String CREATE_TABLE_LOGIN = "create table "+ TABLE_LOGIN + " (mailId TEXT PRIMARY KEY, password TEXT)";
        db.execSQL(CREATE_TABLE_LOGIN);
        try {
            db.execSQL(CREATE_TABLE_REGISTRATION);
        }catch (Exception ex)
        {
            Log.i("exception", ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_REGISTRATION);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_LOGIN);
        onCreate(db);
    }


    public void addRegistration(DB_Setter_Getter dbSetterGetter){
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put("fname",dbSetterGetter.getFname());
        values.put("lname",dbSetterGetter.getLname());
        values.put("DOB",dbSetterGetter.getDOB());
        values.put("CELL",dbSetterGetter.getCELL());
        values.put("mailId",dbSetterGetter.getMailId().toLowerCase());
        values.put("carNumber",dbSetterGetter.getCarNumber());
        values.put("hpincode",dbSetterGetter.getHpincode());
        values.put("opincode",dbSetterGetter.getOpincode());

        try {
            db.insert(TABLE_REGISTRATION,null,values);
            db.close();
        }catch (Exception ex)
        {
            Log.i("exception",ex.getMessage());
        }


    }


    public void addLogin(DB_Setter_Getter dbSetterGetter)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("mailId",dbSetterGetter.getMailId().toLowerCase());
        values.put("password",dbSetterGetter.getPassword());

        db.insert(TABLE_LOGIN,null,values);
        db.close();

    }

    public boolean checkMail(DB_Setter_Getter dbSetterGetter)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_REGISTRATION,new String[]{"mailId"},"mailId = ?",
                new String[]{dbSetterGetter.getMailId().toLowerCase()},null,null,null);

        if(cursor.getCount()>0) {
            return false;
        }

        return true;
    }

    public String[] getcredentials(DB_Setter_Getter dbSetterGetter)
    {
        String[] details = new String[3];
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_LOGIN,new String[]{"mailId","password"},"mailId = ?",
                new String[]{dbSetterGetter.getMailId().toLowerCase()},null,null,null);

        if(cursor!=null)
        {
            cursor.moveToFirst();
            details[0] = cursor.getString(0);
            details[1] = cursor.getString(1);
        }

        cursor = db.query(TABLE_REGISTRATION,new String[]{"fname"},"mailId = ?",
                new String[]{dbSetterGetter.getMailId().toLowerCase()},null,null,null);

        if(cursor!=null)
        {
            cursor.moveToFirst();
            details[2] = cursor.getString(0);
        }
        return details;
    }
}
