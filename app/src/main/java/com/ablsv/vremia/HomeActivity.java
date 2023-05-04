package com.ablsv.vremia;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton fab;
    LinearLayout linearLayout;
    DrawerLayout drawer;
    DatabaseHelper Db;
    ArrayList<String> task_id, task_name, task_date, task_time, task_color, task_image;
    CustomAdapter customAdapter;
    private View LinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_drawer);
        setNavigationViewListener();

        Toolbar tbar = findViewById(R.id.toolbar);

        drawer = findViewById(R.id.drawerlayout);
        ActionBarDrawerToggle tog = new ActionBarDrawerToggle(this, drawer, tbar, R.string.navig_drawer_open, R.string.navig_drawer_close);
        tog.syncState();

        fab = findViewById(R.id.addBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addtaskActivity();
            }
        });

        Db = new DatabaseHelper(HomeActivity.this);
        task_id = new ArrayList<>();
        task_name = new ArrayList<>();
        task_date = new ArrayList<>();
        task_time = new ArrayList<>();
        task_color = new ArrayList<>();
        task_image = new ArrayList<>();

        LinearLayout = findViewById(R.id.recycler_view_row);
        storeDataInArrays();

        customAdapter = new CustomAdapter(HomeActivity.this, task_id, task_name, task_date,
                task_time, task_color, task_image);
        for(int i=0;i<customAdapter.getItemCount();i++){
            View itemView = customAdapter.onCreateViewHolder(linearLayout, customAdapter.getItemViewType(i)).itemView;
            customAdapter.onBindViewHolder((CustomAdapter.MyViewHolder) new RecyclerView.ViewHolder(itemView) {}, i);
            linearLayout.addView(itemView);
        }

    }

    void storeDataInArrays() {
        Cursor cursor = Db.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "NO data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                task_id.add(cursor.getString(0));
                task_name.add(cursor.getString(1));
                task_date.add(cursor.getString(3));
                task_time.add(cursor.getString(4));
                task_color.add(cursor.getString(5));
                task_image.add(cursor.getString(6));
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addschedule: {
                addtaskActivity();
                break;
            }
            case R.id.settings:
            {
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.darkmodetoggle:
            {
                Toast.makeText(this, "Still Unsupported.", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.about:
            {
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void setNavigationViewListener()
    {
        NavigationView nv = findViewById(R.id.nav_view);
        nv.setNavigationItemSelectedListener(this);
    }
    public void addtaskActivity()
    {
        Intent intent = new Intent(HomeActivity.this, AddTask.class);
        startActivity(intent);
    }
}