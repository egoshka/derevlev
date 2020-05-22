package com.example.db3;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "students.db"; // название бд
    private static final int SCHEMA = 2; // версия базы данных
    static final String TABLE = "students"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_F = "f";
    public static final String COLUMN_I = "i";
    public static final String COLUMN_O = "o";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE students (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_F
                + " TEXT, " + COLUMN_I
                + " TEXT, " + COLUMN_O
                + " TEXT, " + COLUMN_TIMESTAMP + " TEXT);");
    }

    public String randomF() {
        Random rand = new Random();
        List<String> listF = new ArrayList<>(Arrays.asList("Иванов", "Петров", "Борисов", "Сидоров", "Васильев"));
        String str = listF.get(rand.nextInt(listF.size())) + " ";
        return str;
    }

    public String randomI() {
        Random rand = new Random();
        List<String> listI = new ArrayList<>(Arrays.asList("Иван", "Петр", "Борис", "Сидр", "Василий"));
        String str = listI.get(rand.nextInt(listI.size())) + " ";
        return str;
    }

    public String randomO() {
        Random rand = new Random();
        List<String> listO = new ArrayList<>(Arrays.asList("Иванович", "Петрович", "Борисович", "Сидорович", "Васильевич"));
        String str = listO.get(rand.nextInt(listO.size()));
        return str;
    }

    public void writeNote(SQLiteDatabase db) {
        Date dateN = new Date();
        DateFormat DFormat = DateFormat.getTimeInstance();
        String formatted = DFormat.format(dateN.getTime());
        String F = randomF();
        String I = randomI();
        String O = randomO();
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_F
                + ", " + COLUMN_I
                + ", " + COLUMN_O
                + ", " + COLUMN_TIMESTAMP  + ") VALUES ('" + F + "', '" + I + "', '" + O + "',  '" + formatted + "');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}