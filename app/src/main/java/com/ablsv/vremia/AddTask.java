package com.ablsv.vremia;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.util.Calendar;

import yuku.ambilwarna.AmbilWarnaDialog;

public class AddTask extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, View.OnClickListener {


    private EditText taskName, taskDesc, taskIcon;
    private DatabaseHelper databaseHelper;
    private TextView datepick, timepick, colorpick, colorprev;
    int DefColor;
    ImageView imageToUpload;
    Button bSaveTask;
    private static final int RESULT_LOAD_IMAGE = 1;


    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskName = findViewById(R.id.taskNameInput);
        taskDesc = findViewById(R.id.descInput);
        colorpick = findViewById(R.id.colorpick);
        datepick = findViewById(R.id.datepick);
        timepick = findViewById(R.id.timepick);
        taskIcon = findViewById(R.id.imageToUpload);
        DefColor = ContextCompat.getColor(AddTask.this, R.color.white);
        ImageView cancelbtn = findViewById(R.id.cancel);
        Button taskBtn = findViewById(R.id.bSaveTask);

        colorpick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker();
            }
        });
        timepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment tpicker = new TimePickerFragment();
                tpicker.show(getSupportFragmentManager(), "Time picker");
            }
        });

        datepick.setOnClickListener(view -> {
            DatePickerFragment dpick = new DatePickerFragment();
            dpick.show(getSupportFragmentManager(), "date picker");
        });

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imageToUpload = findViewById(R.id.imageToUpload);

        bSaveTask = findViewById(R.id.bSaveTask);

        imageToUpload.setOnClickListener(this);
        bSaveTask.setOnClickListener(this);

        databaseHelper = new DatabaseHelper(AddTask.this);

        bSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = taskName.getText().toString();
                String description = taskDesc.getText().toString();
                String date = datepick.getText().toString();
                String time = timepick.getText().toString();
                int image = imageToUpload.getImageAlpha();

            if (name.isEmpty() && description.isEmpty() && date.isEmpty() && time.isEmpty())
                Toast.makeText(AddTask.this, "Please enter all the data", Toast.LENGTH_SHORT).show();
            return;
            }


        });
        databaseHelper.addSched();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayofmonth);
        String cdatestr = DateFormat.getDateInstance().format(c.getTime());
        datepick.setText(cdatestr);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
       timepick.setText(hour +":"+ minute);

    }
    public void openColorPicker()
    {
        AmbilWarnaDialog colorpicker = new AmbilWarnaDialog(this, DefColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                String colorhexed = toHex(color);
                colorpick.setText(colorhexed);
                colorprev = findViewById(R.id.colorpreview);
                int coldr = Color.parseColor("#"+colorhexed);
                colorprev.setBackgroundColor(coldr);
                colorpick.setText(colorhexed);

            }
        });
        colorpicker.show();
    }

    public static String toHex(int i)
    {
        long unsignedDecimal = i & 0xFFFFFFFFL;
        String hexa = Long.toHexString(unsignedDecimal);
        hexa.length();
        return hexa.toUpperCase();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageToUpload:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                break;
        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            imageToUpload.setImageURI(selectedImage);
        }
    }
//end of AddTask class
}