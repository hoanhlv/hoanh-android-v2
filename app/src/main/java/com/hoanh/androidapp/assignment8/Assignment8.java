package com.hoanh.androidapp.assignment8;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.hoanh.androidapp.R;

import java.util.Arrays;
import java.util.List;

public class Assignment8 extends AppCompatActivity {

    List<String> storageList = Arrays.asList(
            "Shared Preferences",
            "Android File System",
            "Internal Storage",
            "External Storage",
            "SQLite"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment8);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(this.getIntent().getStringExtra("practiceName") + "");

        ListAdapter adapter = new ArrayAdapter<>(this, R.layout.activity_list, storageList);
        ListView listView = findViewById(R.id.storageList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = null;
                Context appContext = getApplicationContext();
                switch (position + 1) {
                    case 1:
                    default:
                        intent = new Intent(appContext, Assignment8_SharedPreferences.class);
                        break;
                    case 2:
                        intent = new Intent(appContext, Assignment8_FileSystem.class);
                        break;
                    case 3:
                        intent = new Intent(appContext, Assignment8_InternalStorage.class);
                        break;
                    case 4:
                        intent = new Intent(appContext, Assignment8_ExternalStorage.class);
                        break;
                    case 5:
                        intent = new Intent(appContext, Assignment8_SQLite.class);
                        break;
                }
                intent.putExtra("practiceName", "8 - " + storageList.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }
}