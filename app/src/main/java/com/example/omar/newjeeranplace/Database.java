package com.example.omar.newjeeranplace;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omar on 1/18/2017.
 */
public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_PLACE = "place";
    private static final String TABLE_IMAGE = "image";


    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";

    private static final String IMAGE_ID = "id";

    private static final String IMAGE = "image";
    private static final String PLACE_NAME = "place_name";



    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




  /*  public void queryData(String sql)
    {
        SQLiteDatabase Database = getWritableDatabase();
        Database.execSQL(sql);
    }
    public void insertData(String name,String price , byte[] image)
    {
        SQLiteDatabase Database = getWritableDatabase();
        String sql = "INSERT INTO FOOD VALUES (NULL,?,?,?)";
        SQLiteStatement statement = Database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1,name);
        statement.bindString(2,price);
        statement.bindBlob(3,image);
        statement.executeInsert();
    }
    */

    public Cursor getData(String sql)
    {
        SQLiteDatabase Database = getReadableDatabase();
        return  Database.rawQuery(sql,null);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PLACE_TABLE = "CREATE TABLE " + TABLE_PLACE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + IMAGE + "TEXT"
                + ")";

       /* String CREATE_IMAGE_TABLE = "CREATE TABLE " + TABLE_IMAGE + "("
                + IMAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + IMAGE + " TEXT,"
                + PLACE_NAME + " TEXT" + ")";*/
        db.execSQL(CREATE_PLACE_TABLE);
        //db.execSQL(CREATE_IMAGE_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACE);
       // db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGE);


        // Create tables again
        onCreate(db);
    }

    public void addPlace (String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Contact Name

        // Inserting Row
        db.insert(TABLE_PLACE, null, values);

        db.close();
    }


    public void addImage (String imageText , String placeName)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(IMAGE, imageText); // Contact Name
        values.put(PLACE_NAME, placeName); // Contact Name


        // Inserting Row
        db.insert(TABLE_PLACE, null, values);
        db.close();
    }

    public List <String> getImageByPlacename (String placeName)
    {
        List<String> list = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_IMAGE + " where place_name = '"+placeName+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(2));

            } while (cursor.moveToNext());
        }

        // return contact list
        return list;
    }



}
