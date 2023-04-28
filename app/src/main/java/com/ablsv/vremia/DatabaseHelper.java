 package com.ablsv.vremia;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;


 class DatabaseHelper extends SQLiteOpenHelper {

    private final Context context;
     private static final String DATABASE_NAME = "Schedules.db";
     private static final int DATABASE_VERSION = 1;

     private static final String TABLE_NAME = "schedules";
     private static final String COLUMN_ID = "_id";
     private static final String COLUMN_NAME = "task_name";
     private static final String COLUMN_DESCRIPTION = "task_desc";
     private static final DateFormat COLUMN_DATE = new SimpleDateFormat("MM-dd-yyyy");
     private static final DateFormat COLUMN_TIME = new SimpleDateFormat("HH:mm:ss");
     private static final String COLUMN_COLOR = "task_color";
     private static final String COLUMN_IMAGE = "task_image";





    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_COLOR + " INTEGER, " +
                COLUMN_IMAGE + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    void addSched(String Name, String Description, String Date, Integer Time, Integer Color, String Image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, Name);
        cv.put(COLUMN_DESCRIPTION, Description);
        cv.put(String.valueOf(COLUMN_DATE), Date);
        cv.put(String.valueOf(COLUMN_TIME), Time);
        cv.put(COLUMN_COLOR, Color);
        cv.put(COLUMN_IMAGE, Image);

        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
}
