package com.hoanh.androidapp.assignment8;

import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.huy.androidcourseapp.R;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Assignment8_ExternalStorage extends AppCompatActivity {

    String extState;
    Button btnCheckExtState, btnReadExt, btnWriteExt;
    TextView textExtState, textNoteResponse;
    EditText textExtNote;
    File extFile;
    final String FILE_PATH = "../../../../Download/";
    final String FILE_NAME = "Practice8_ExternalStorage.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice8_external_storage);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(this.getIntent().getStringExtra("practiceName") + "");

        extState = Environment.getExternalStorageState();

        // check state
        textExtState = findViewById(R.id.textExtState);
        btnCheckExtState = findViewById(R.id.btnCheckExtState);
        btnCheckExtState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkState();
            }
        });

        // write file
        textExtNote = findViewById(R.id.textExtNote);
        textNoteResponse = findViewById(R.id.textNoteResponse);
        btnReadExt = findViewById(R.id.btnReadExt);
        btnWriteExt = findViewById(R.id.btnWriteExt);

        if (!isExtStorageWritable()) {
            btnWriteExt.setEnabled(false);
        }
        if (!isExtStorageReadable()) {
            btnReadExt.setEnabled(false);
        }
        btnWriteExt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeFile();
            }
        });
        btnReadExt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readFile();
            }
        });
        // absolut dir
//        extFile = new File(getExternalFilesDir(FILE_PATH), FILE_NAME);
        // public dir
        extFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), FILE_NAME);
    }

    private boolean isExtStorageWritable() {
        return Environment.MEDIA_MOUNTED.equals(extState);
    }

    private boolean isExtStorageReadable() {
        return Environment.MEDIA_MOUNTED.equals(extState) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(extState);
    }

    private void checkState() {
        if (isExtStorageWritable()) {
            textExtState.setText("WRITE");
        } else if (isExtStorageReadable()) {
            textExtState.setText("READ");
        } else {
            textExtState.setText("NOT FOUND");
        }
    }

    private void writeFile() {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(extFile);
            fos.write(textExtNote.getText().toString().getBytes());
            fos.close();

            Toast.makeText(getBaseContext(), "Save file successful!", Toast.LENGTH_SHORT).show();
            textExtNote.setText("");
            textNoteResponse.setText("Saved note to external storage: " + extFile.getPath());
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Save file failed!", Toast.LENGTH_SHORT).show();
            textNoteResponse.setText("Save failed: " + extFile.getPath());
        }
    }

    private void readFile() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(extFile);
            System.out.println("read external file: " + extFile.getPath());

            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            StringBuilder sb = new StringBuilder();
            String strLine;
            while ((strLine = br.readLine()) != null) {
                sb.append(strLine);
            }
            in.close();

            Toast.makeText(getBaseContext(), "Read file successful!", Toast.LENGTH_SHORT).show();
            textExtNote.setText(sb.toString());
            textNoteResponse.setText("Read success: " + extFile.getPath());
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Read file failed!", Toast.LENGTH_SHORT).show();
            textNoteResponse.setText("Read failed: " + extFile.getPath());
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }
}