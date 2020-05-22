package com.example.db3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class OutputActivity extends AppCompatActivity {

    ListView userList;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);

        userList = (ListView)findViewById(R.id.list);
        databaseHelper = new DatabaseHelper(getApplicationContext());
    }
    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = databaseHelper.getReadableDatabase();

        //получаем данные из бд в виде курсора
        userCursor =  db.rawQuery("select * from "+ DatabaseHelper.TABLE, null);

        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        HashMap<String, String> map;

        while (userCursor.moveToNext()) {
            map = new HashMap<>();
            map.put("Name", userCursor.getString(userCursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)));
            map.put("Properties", userCursor.getString(userCursor.getColumnIndex(DatabaseHelper.COLUMN_ID)) + ", "
                    +  userCursor.getString(userCursor.getColumnIndex(DatabaseHelper.COLUMN_TIMESTAMP)));
            arrayList.add(map);
        }
        userCursor.close();

        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[] {"Name", "Properties"};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleAdapter(this, arrayList, android.R.layout.simple_list_item_2,
                 headers, new int[]{android.R.id.text1, android.R.id.text2});
        userList.setAdapter(userAdapter);
    }
}
