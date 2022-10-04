package com.example.a6week;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    myDBHelper myDBHelper;
    EditText edtName, edtNum, edtID, edtPW, edtAd,edtNameResult, edtNumResult, edtIDResult, edtPWResult, edtAdResult;
    Button btnInit, btnInsert, btnSelect;
    SQLiteDatabase sqlDB;
    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context) {
            super(context, "groupDB", null, 1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE groupTBL ( gName CHAR(20) PRIMARY KEY,gNumber INTEGER);");
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS groupTBL");
            onCreate(db);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName = findViewById(R.id.edtName);
        edtNum = findViewById(R.id.edtNum);
        edtAd = findViewById(R.id.edtAd);
        edtPW = findViewById(R.id.edtPW);
        edtID = findViewById(R.id.edtID);

        edtNameResult = findViewById(R.id.edtNameResult);
        edtNumResult = findViewById(R.id.edtNumResult);
        edtIDResult = findViewById(R.id.edtIDResult);
        edtPWResult = findViewById(R.id.edtPWResult);
        edtAdResult = findViewById(R.id.edtAdResult);

        btnInit = findViewById(R.id.btnInit);
        btnSelect = findViewById(R.id.btnSelect);
        btnInsert = findViewById(R.id.btnInsert);

        myDBHelper = new myDBHelper(this);
        btnInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myDBHelper.getWritableDatabase();
                myDBHelper.onUpgrade(sqlDB,1,2);
                sqlDB.close();
            }
        });
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                sqlDB = myDBHelper.getWritableDatabase();
                sqlDB.execSQL("INSERT INTO user VALUES  ('"+edtName.getText().toString()+"', '"+edtNum.getText().toString()+"', '"+edtID.getText().toString()+"', '"+edtPW.getText().toString()+"','"+edtAd.getText().toString()+"');");
                sqlDB.close();
                Toast.makeText(getApplicationContext(),"입력됨",0).show();
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myDBHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM user;",null);

                String strNames = "목록 리스트"+"\r\n"+"\r\n";
                String strNumbers = "수량"+"\r\n"+"\r\n";

                while (cursor.moveToNext()){
                    strNames += cursor.getString(0) + "\r\n";
                    strNumbers += cursor.getString(1) + "\r\n";
                }
                edtNameResult.setText(strNames);
                edtNumResult.setText(strNumbers);
                cursor.close();
                sqlDB.close();
            }
        });


    }
}