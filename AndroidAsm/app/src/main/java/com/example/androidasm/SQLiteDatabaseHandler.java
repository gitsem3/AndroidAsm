package com.example.androidasm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "studentData";
    // Country table name
    private static final String TABLE_STUDENT = "Student";
    // Country Table Columns names
    private static final String KEY_ID = "id";
    private static final String NAME = "Name";
    private static final String EMAIL = "Email";
    private static final String TEL = "Tel";

    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_STUDENT_TABLE = "CREATE TABLE " + TABLE_STUDENT
                + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + NAME + " TEXT UNIQUE,"
                + EMAIL + " TEXT,"
                + TEL + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_STUDENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        // Create tables again
        onCreate(sqLiteDatabase);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new country
    void addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, student.getName());
        values.put(EMAIL, student.getEmail());
        values.put(TEL, student.getTel());

        // Inserting Row
        db.insert(TABLE_STUDENT, null, values);
        db.close(); // Closing database connection
    }

    // Getting single country
    Student getStudent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_STUDENT, new String[]{KEY_ID,

                        NAME, EMAIL, TEL}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Student student = new Student(Integer.parseInt(cursor.getString(0)),
//                cursor.getString(1), cursor.getLong(2));
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        // return country
        return student;
    }

    // Getting All Countries
    public List getAllStudent() {
        List studnetList = new ArrayList();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_STUDENT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setId(Integer.parseInt(cursor.getString(0)));
                student.setName(cursor.getString(1));
                student.setEmail(cursor.getString(2));
                student.setTel(cursor.getString(3));
                studnetList.add(student);
            } while (cursor.moveToNext());
        }

        return studnetList;
    }

    public int update(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, student.getName());
        values.put(EMAIL, student.getEmail());
        values.put(TEL, student.getTel());

        return db.update(TABLE_STUDENT, values, KEY_ID + " = ?",
                new String[]{String.valueOf(student.getId())});
    }

    public void delete(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENT, KEY_ID + " = ?",
                new String[]{String.valueOf(student.getId())});
        db.close();
    }

    public void deleteAllStudent() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENT, null, null);
        db.close();
    }

    public int getStudentCount() {
        String countQuery = "SELECT  * FROM " + TABLE_STUDENT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}

