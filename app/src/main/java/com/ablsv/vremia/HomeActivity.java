package com.ablsv.vremia;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    FloatingActionButton fab;
    RecyclerView recyclerView;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_drawer);
        setNavigationViewListener();

       Toolbar tbar = findViewById(R.id.toolbar);

       drawer = findViewById(R.id.drawerlayout);
       ActionBarDrawerToggle tog = new ActionBarDrawerToggle(this, drawer, tbar, R.string.navig_drawer_open, R.string.navig_drawer_close);
       tog.syncState();



        fab = (FloatingActionButton) findViewById(R.id.addBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addtaskActivity();
            }
        });

        recyclerView = findViewById(R.id.recyc);
        fab = findViewById(R.id.addBtn);

    }

    @Override
    public void onBackPressed()
    {
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.addschedule:
            {
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