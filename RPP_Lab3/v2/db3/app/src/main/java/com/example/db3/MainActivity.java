package com.example.db3;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    static final String TABLE_NAME = "students";
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        db = databaseHelper.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
        for(int i = 0; i < 5; i++) {
            databaseHelper.writeNote(db);
        }
    }

    public void openBD(View view){
        Intent intent = new Intent(this, OutputActivity.class);
        startActivity(intent);
    }

    public void save(View view){
        databaseHelper.writeNote(db);
    }

    public void ivan(View view) {
        db = databaseHelper.getWritableDatabase();
        String strF = "Иванов ";
        String strI = "Иван ";
        String strO = "Иванович";

        Cursor userCursor;

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_F, strF);
        cv.put(DatabaseHelper.COLUMN_I, strI);
        cv.put(DatabaseHelper.COLUMN_O, strO);
        //int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        userCursor = db.query(TABLE_NAME, new String[] {DatabaseHelper.COLUMN_ID}, null, null, null, null, DatabaseHelper.COLUMN_ID+" DESC");
        userCursor.moveToFirst();
        int ordID = userCursor.getInt(0);

        db.update(DatabaseHelper.TABLE, cv, DatabaseHelper.COLUMN_ID + "=" + ordID, null);
        userCursor.close();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
    }
}