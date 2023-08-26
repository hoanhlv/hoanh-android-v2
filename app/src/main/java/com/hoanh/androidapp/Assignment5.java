package com.hoanh.androidapp;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Assignment5 extends AppCompatActivity {

    Button btnContextMenu, btnCheckPopupMenu, btnAnchorPopupMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment5);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(this.getIntent().getStringExtra("practiceName") + "");

        // context menu
        btnContextMenu = findViewById(R.id.btnContextMenu);
        registerForContextMenu(btnContextMenu);

        // popup menu
        btnCheckPopupMenu = findViewById(R.id.btnCheckPopupMenu);
        btnAnchorPopupMenu = findViewById(R.id.btnAnchorPopupMenu);
        btnCheckPopupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                Toast.makeText(getApplicationContext(), "You pressed: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("This is Context Menu");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
    }

    private void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(this, btnAnchorPopupMenu);
        popupMenu.inflate(R.menu.option_menu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getApplicationContext(), "Popup pressed: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        popupMenu.show();
    }
}