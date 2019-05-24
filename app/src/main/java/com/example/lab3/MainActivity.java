package com.example.lab3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button mAddButton, mOutputButton, mChangeButton;
    EditText fio;
    SQLiteHelper dbHelper;
    public Cursor table;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fio = findViewById(R.id.name);
        mAddButton = findViewById(R.id.add_btn);

        dbHelper = new SQLiteHelper(this);
        table = dbHelper.getStudentTable();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (table != null)
            if (table.moveToFirst()) {
                db.delete("student", null, null);
                db.execSQL("DELETE FROM sqlite_sequence WHERE name = 'student'");
                dbHelper.addValues(db);
            }

            mAddButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!(fio.getText().toString().equals("") || fio.getText().toString().equals(" ") || fio.getText().toString().equals("ФИО")))
                        dbHelper.add(fio.getText().toString());
                    else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Ошибка при вводе", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });

            mOutputButton = findViewById(R.id.output_btn);
            mOutputButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ShowingActivity.class);
                    table = dbHelper.getStudentTable();
                    ArrayList<String> output = new ArrayList<String>();
                    if (table != null) {
                        table.moveToFirst();
                        while (!table.isLast()) {
                            output.add(String.valueOf(table.getInt(0)) + ' ' + table.getString(1) + ' ' + table.getString(2) + '\n');
                            table.moveToNext();
                        }
                        output.add(String.valueOf(table.getInt(0)) + ' ' + table.getString(1) + ' ' + table.getString(2));
                    }
                    intent.putExtra("mas", output);
                    startActivity(intent);
                }
            });

            mChangeButton = findViewById(R.id.change_btn);
            mChangeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHelper.update();
                }
            });
        }
    }
