package com.example.registrationapp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseManager {
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public UserDatabaseManager(Context context) {
        dbHelper = new MyDatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Create (Insert) a new user
    public long insertUser(User user) {
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        values.put("gender", user.getGender());
        return db.insert("users", null, values);
    }

    // Read (Query) user by email
    public User getUserByEmail(String email) {
        String[] columns = {"username", "email", "password", "gender"};
        String selection = "email = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow("gender"));
            cursor.close();
            return new User(username, email, password, gender);
        } else {
            cursor.close();
            return null;
        }
    }

    // Read (Query) user by username
    public User getUserByUsername(String username) {
        String[] columns = {"username", "email", "password", "gender"};
        String selection = "username = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow("gender"));
            cursor.close();
            return new User(username, email, password, gender);
        } else {
            cursor.close();
            return null;
        }
    }

    // Update user password by username
    public boolean updatePassword(String username, String newPassword) {
        ContentValues values = new ContentValues();
        values.put("password", newPassword);

        int rowsAffected = db.update("users", values, "username = ?", new String[]{username});
        return rowsAffected > 0;
    }

    // Delete user by username
    public boolean deleteUserByUsername(String username) {
        int result = db.delete("users", "username = ?", new String[]{username});
        return result > 0;
    }

    private static class MyDatabaseHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "userdatabase.db";
        private static final int DATABASE_VERSION = 1;

        private static final String TABLE_USERS = "users";
        private static final String COLUMN_USERNAME = "username";
        private static final String COLUMN_EMAIL = "email";
        private static final String COLUMN_PASSWORD = "password";
        private static final String COLUMN_GENDER = "gender";

        private static final String TABLE_CREATE =
                "CREATE TABLE " + TABLE_USERS + " (" +
                        COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
                        COLUMN_EMAIL + " TEXT, " +
                        COLUMN_PASSWORD + " TEXT, " +
                        COLUMN_GENDER + " TEXT);";

        MyDatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            onCreate(db);
        }
    }
}
