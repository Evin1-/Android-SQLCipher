package com.example.simplesqlcipher;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivityTAG_";

    private static final String EXAMPLE_NAME = "Edwin";
    private static final String EXAMPLE_AGE = "31";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase.loadLibs(this);
    }

    public void addMagic(View view) {
        UsersDatabaseHelper usersDatabaseHelper = new UsersDatabaseHelper(getApplicationContext());
        SQLiteDatabase db = usersDatabaseHelper.getWritableDatabase("password");

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(UsersDatabaseHelper.KEY_USER_NAME, EXAMPLE_NAME);
            values.put(UsersDatabaseHelper.KEY_AGE, EXAMPLE_AGE);

            db.insertOrThrow(UsersDatabaseHelper.TABLE_USERS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    public void loadMagic(View view) {
        UsersDatabaseHelper usersDatabaseHelper = new UsersDatabaseHelper(getApplicationContext());
        SQLiteDatabase db = usersDatabaseHelper.getWritableDatabase("password");

        Cursor cursor = db.query(UsersDatabaseHelper.TABLE_USERS, null, null, null, null, null, null);

        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            do {
                Log.d(TAG, "loadMagic: " + cursor.getString(cursor.getColumnIndex(UsersDatabaseHelper.KEY_USER_ID)));
                Log.d(TAG, "loadMagic: " + cursor.getString(cursor.getColumnIndex(UsersDatabaseHelper.KEY_USER_NAME)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }
}
