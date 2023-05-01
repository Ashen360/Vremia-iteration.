package com.ablsv.vremia;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;


class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Schedules.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "schedules";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "task_name";
    private static final String COLUMN_DESCRIPTION = "task_desc";
    private static final String COLUMN_DATE = "task_date";
    private static final String COLUMN_TIME = "task_time";
    private static final String COLUMN_COLOR = "task_color";
    private static final String COLUMN_IMAGE = "task_image";
    private final Context context;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public void addSched(EditText name, EditText description, TextView date,
                         TextView time, TextView color, ImageView image) {
        SQLiteDatabase db = getWritableDatabase();

        onCreate(db);

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name.getText().toString());
        cv.put(COLUMN_DESCRIPTION, description.getText().toString());
        cv.put(COLUMN_DATE, date.getText().toString());
        cv.put(COLUMN_TIME, time.getText().toString());
        cv.put(COLUMN_COLOR, color.getText().toString());

        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        cv.put(COLUMN_IMAGE, imageBytes);

        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    public Bitmap getImageById(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_IMAGE};
        String selection = COLUMN_ID + "=?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null, null);
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") byte[] imageBytes = cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE));
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        }
        return null;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_NAME + " TEXT,"
                        + COLUMN_DESCRIPTION + " TEXT,"
                        + COLUMN_DATE + " DATE,"
                        + COLUMN_TIME + " TIME,"
                        + COLUMN_COLOR + " COLOR,"
                        + COLUMN_IMAGE + " BLOB" + ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

