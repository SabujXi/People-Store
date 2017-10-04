package me.sabuj.sqlitepart1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyDbHelper extends SQLiteOpenHelper {
    public MyDbHelper(Context ctx) {
        super(ctx, "db1.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table
        db.execSQL("CREATE TABLE persons(id integer primary key autoincrement, name text NOT NULL, email text NOT NULL UNIQUE, profession text NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // We do not want to do anything on upgrade in this application
    }

    public String save(String name, String email, String profession){
        String msg = "";
        email = email.toLowerCase(); // Usually emails are case insensitive. So, I am converting it to lowercase to avoid duplication.
        ContentValues cvals = new ContentValues();
        cvals.put("name", name);
        cvals.put("email", email);
        cvals.put("profession", profession);
        long res = this.getWritableDatabase().insert("persons", null, cvals);
        if (res < 0){
            msg = "An error occurred";
        }else{
            msg = "Data inserted Successfully";
        }
        return msg;
    }

    public String search(String email){
        String msg = "";
        email = email.toLowerCase();
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT name, email, profession FROM persons WHERE email='" + email + "'", null);
        if (cursor.getCount() < 1){
            msg = "Person with email " + email + "NOT FOUND";
        }else{
            cursor.moveToNext();
            msg = "Name: " + cursor.getString(0) + "\n" +
                  "Email: " + cursor.getString(1) + "\n" +
                  "Position: " + cursor.getString(2) + "\n";
        }
        return msg;
    }
}
