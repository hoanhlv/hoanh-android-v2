package com.hoanh.androidapp.assignment8;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.huy.androidcourseapp.R;
import com.huy.androidcourseapp.database.BookContract;
import com.huy.androidcourseapp.database.UserContract;
import com.huy.androidcourseapp.database.manager.DbManager;

public class Assignment8_SQLite extends AppCompatActivity {

    EditText edTextUser, edTextPhone, editTextTitle, edTextAuthor;
    TextView textDatabase, textUserDetail, textBookDetail;
    Button btnGetTable, btnCreateTable, btnDropTable;
    Button btnUser, btnBook;
    int userMode = 1, bookMode = 1;
    RadioGroup radioUser, radioBook;
    DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice8_sqlite);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(this.getIntent().getStringExtra("practiceName") + "");

        // user
        edTextUser = findViewById(R.id.edTextUser);
        edTextPhone = findViewById(R.id.edTextPhone);
        textUserDetail = findViewById(R.id.textUserDetail);
        btnUser = findViewById(R.id.btnUser);
        radioUser = findViewById(R.id.radioUser);

        textUserDetail.setVisibility(View.GONE);

        radioUser.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id == R.id.radioUser1) {
                    userMode = 1;
                    btnUser.setText("SEARCH");
                } else if (id == R.id.radioUser2) {
                    userMode = 2;
                    btnUser.setText("INSERT");
                } else if (id == R.id.radioUser3) {
                    userMode = 3;
                    btnUser.setText("DELETE");
                }
            }
        });
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (userMode) {
                    case 1:
                        searchUser();
                        break;
                    case 2:
                        insertUser();
                        break;
                    case 3:
                        deleteUser();
                        break;
                }
            }
        });

        // book
        editTextTitle = findViewById(R.id.edTextTitle);
        edTextAuthor = findViewById(R.id.edTextAuthor);
        textBookDetail = findViewById(R.id.textBookDetail);
        btnBook = findViewById(R.id.btnBook);
        radioBook = findViewById(R.id.radioBook);

        textBookDetail.setVisibility(View.GONE);

        radioBook.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id == R.id.radioBook1) {
                    bookMode = 1;
                    btnBook.setText("SEARCH");
                } else if (id == R.id.radioBook2) {
                    bookMode = 2;
                    btnBook.setText("INSERT");
                } else if (id == R.id.radioBook3) {
                    bookMode = 3;
                    btnBook.setText("DELETE");
                }
            }
        });
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (bookMode) {
                    case 1:
                        searchBook();
                        break;
                    case 2:
                        insertBook();
                        break;
                    case 3:
                        deleteBook();
                        break;
                }
            }
        });

        // db
        dbManager = DbManager.getInstance();
        textDatabase = findViewById(R.id.textDatabase);
        btnGetTable = findViewById(R.id.btnGetTable);
        btnCreateTable = findViewById(R.id.btnCreateTable);
        btnDropTable = findViewById(R.id.btnDropTable);
        btnGetTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tables = dbManager.getAllTable();
                textDatabase.setText("Connected! Tables: " + tables);
                Toast.makeText(getBaseContext(), "Get tables success!", Toast.LENGTH_SHORT).show();
            }
        });
        btnCreateTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbManager.createAllTable();
                String tables = dbManager.getAllTable();
                textDatabase.setText("Connected! Tables: " + tables);
                Toast.makeText(getBaseContext(), "Create tables success!", Toast.LENGTH_SHORT).show();
            }
        });
        btnDropTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbManager.dropAllTable();
                String tables = dbManager.getAllTable();
                textDatabase.setText("Connected! Tables: " + tables);
                Toast.makeText(getBaseContext(), "Drop tables success!", Toast.LENGTH_SHORT).show();
            }
        });
        connectDatabase();
    }

    private void connectDatabase() {
        dbManager.open(this);
        if (dbManager.isConnected()) {
            System.out.println("Connected database");
            textDatabase.setText("Connected!");
        } else {
            System.out.println("Cannot connect database");
            textDatabase.setText("Failed connect!");
        }
    }

    private void searchUser() {
        String username = edTextUser.getText().toString();
        if (username.isEmpty()) {
            Toast.makeText(getBaseContext(), "Please enter username", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] columns = new String[] { UserContract.UserEntry.COLUMN_ID,
                UserContract.UserEntry.COLUMN_USERNAME,
                UserContract.UserEntry.COLUMN_PHONE_NUMBER,
                UserContract.UserEntry.COLUMN_ROLE
        };
        String selections = UserContract.UserEntry.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = { username };

        try {
            Cursor cursor = dbManager.getDb().query(UserContract.UserEntry.TABLE_NAME,
                    columns,
                    selections, selectionArgs,
                    null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
            }
            Toast.makeText(this, "Query success!", Toast.LENGTH_SHORT).show();
            StringBuilder sb = new StringBuilder();
            sb.append("Username: ").append(cursor.getString(1)).append("\n");
            sb.append("Phone: ").append(cursor.getString(2));

            textUserDetail.setText(sb.toString());
        } catch (Exception e) {
            Toast.makeText(this, "Not found!", Toast.LENGTH_SHORT).show();
            textUserDetail.setText("Not found!");
        }
        textUserDetail.setVisibility(View.VISIBLE);
    }

    private void insertUser() {
        if (edTextUser.getText().toString().isEmpty() || edTextPhone.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter user info!", Toast.LENGTH_SHORT).show();
            return;
        }
        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.COLUMN_USERNAME, edTextUser.getText().toString());
        values.put(UserContract.UserEntry.COLUMN_PHONE_NUMBER, edTextPhone.getText().toString());

        long id = dbManager.getDb().insert(UserContract.UserEntry.TABLE_NAME, null, values);
        System.out.println("Inserted user id " + id);

        if (id > 0) {
            edTextUser.setText("");
            edTextPhone.setText("");
            textUserDetail.setText("Insert success id " + id + "!");
            Toast.makeText(this, "Insert success!", Toast.LENGTH_SHORT).show();
        } else {
            textUserDetail.setText("Insert failed!");
            Toast.makeText(this, "Insert failed!", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteUser() {
        String username = edTextUser.getText().toString();
        if (username.isEmpty()) {
            Toast.makeText(getBaseContext(), "Please enter username", Toast.LENGTH_SHORT).show();
            return;
        }
        String selection = UserContract.UserEntry.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = { username };
        try {
            dbManager.getDb().delete(UserContract.UserEntry.TABLE_NAME, selection, selectionArgs);
            Toast.makeText(this, "Delete success!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Not found!", Toast.LENGTH_SHORT).show();
            textUserDetail.setText("Not found!");
        }
    }

    private void insertBook() {
        if (editTextTitle.getText().toString().isEmpty() || edTextAuthor.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter book info!", Toast.LENGTH_SHORT).show();
            return;
        }
        ContentValues values = new ContentValues();
        values.put(BookContract.BookEntry.COLUMN_TITLE, editTextTitle.getText().toString());
        values.put(BookContract.BookEntry.COLUMN_AUTHOR, edTextAuthor.getText().toString());

        long id = dbManager.getDb().insert(BookContract.BookEntry.TABLE_NAME, null, values);
        System.out.println("Inserted book id " + id);

        if (id > 0) {
            editTextTitle.setText("");
            edTextAuthor.setText("");
            textBookDetail.setText("Insert success id " + id + "!");
            Toast.makeText(this, "Insert success!", Toast.LENGTH_SHORT).show();
        } else {
            textBookDetail.setText("Insert failed!");
            Toast.makeText(this, "Insert failed!", Toast.LENGTH_SHORT).show();
        }
    }

    private void searchBook() {
        String bookTitle = editTextTitle.getText().toString();
        if (bookTitle.isEmpty()) {
            Toast.makeText(getBaseContext(), "Please enter title", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] columns = new String[] { BookContract.BookEntry.COLUMN_ID,
                BookContract.BookEntry.COLUMN_TITLE,
                BookContract.BookEntry.COLUMN_AUTHOR
        };
        String selections = BookContract.BookEntry.COLUMN_TITLE + " = ?";
        String[] selectionArgs = { bookTitle };

        try {
            Cursor cursor = dbManager.getDb().query(BookContract.BookEntry.TABLE_NAME,
                    columns,
                    selections, selectionArgs,
                    null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
            }

            Toast.makeText(this, "Query success!", Toast.LENGTH_SHORT).show();
            StringBuilder sb = new StringBuilder();
            sb.append("Title: ").append(cursor.getString(1)).append("\n");
            sb.append("Author: ").append(cursor.getString(2));

            textBookDetail.setText(sb.toString());
        } catch (Exception e) {
            Toast.makeText(this, "Not found!", Toast.LENGTH_SHORT).show();
            textBookDetail.setText("Not found!");
        }
        textBookDetail.setVisibility(View.VISIBLE);
    }

    private void deleteBook() {
        String bookTitle = editTextTitle.getText().toString();
        if (bookTitle.isEmpty()) {
            Toast.makeText(getBaseContext(), "Please enter title", Toast.LENGTH_SHORT).show();
            return;
        }
        String selection = BookContract.BookEntry.COLUMN_TITLE + " = ?";
        String[] selectionArgs = { bookTitle };
        try {
            dbManager.getDb().delete(BookContract.BookEntry.TABLE_NAME, selection, selectionArgs);
            Toast.makeText(this, "Delete success!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Not found!", Toast.LENGTH_SHORT).show();
            textBookDetail.setText("Not found!");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }
}