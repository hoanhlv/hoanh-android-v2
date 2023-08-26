package com.hoanh.androidapp.assignment8;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.huy.androidcourseapp.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Assignment8_InternalStorage extends AppCompatActivity {

    EditText textNote;
    Button btnWriteFile, btnReadFile;
    final String FILE_NAME = "Practice8_InternalStorage.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice8_internal_storage);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(this.getIntent().getStringExtra("practiceName") + "");

        textNote = findViewById(R.id.textNote);
        btnWriteFile = findViewById(R.id.btnWriteFile);
        btnReadFile = findViewById(R.id.btnReadFile);

        btnReadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readFile();
            }
        });

        btnWriteFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeFile();
            }
        });
    }

    private void writeFile() {
        String text = textNote.getText().toString();
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            System.out.println("File found");

            OutputStreamWriter outputWriter=new OutputStreamWriter(fos);
            outputWriter.write(text);
            outputWriter.close();

            Toast.makeText(getBaseContext(), "File saved successfully!",
                    Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Save file failed!", Toast.LENGTH_SHORT).show();
        }
    }

    private void readFile() {
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            System.out.println("File found");

            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line + "\n");
            }
            fis.close();

            Toast.makeText(getBaseContext(), "Read file successfully!",
                    Toast.LENGTH_SHORT).show();
            textNote.setText(sb.toString());
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Read file failed!", Toast.LENGTH_SHORT).show();
        }
    }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            finish();
            return true;
        }
}