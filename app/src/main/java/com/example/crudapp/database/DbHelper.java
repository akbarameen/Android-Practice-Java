package com.example.crudapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.crudapp.models.StudentModel;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DbStudent";
    public static final int DATABASE_VERSION = 1;
    public static final String STUDENT_TABLE = "tbl_student";
    public static final String COLUMN_ID  = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_GENDER = "gender";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" Create table " + STUDENT_TABLE + " ( "
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_ADDRESS + " TEXT, "
                + COLUMN_GENDER + " TEXT );"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS " + STUDENT_TABLE);
    }

    // Adding new student
    public boolean addStudent(String name, String address, String gender) {
        // OPENING THE DATABASE
        SQLiteDatabase db = this.getWritableDatabase();

        // VALUES
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_GENDER, gender);

        // Putting 3 parameters in insert method
        long result = db.insert(STUDENT_TABLE, null, values);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

//    @SuppressLint("Range")
//    public ArrayList<HashMap<String, String>> getStudentData(){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//
//        ArrayList<HashMap<String, String>> studentList = new ArrayList<>();
//
//        String query = "SELECT " + COLUMN_NAME + ","+ COLUMN_ADDRESS + "," + COLUMN_GENDER + " FROM "+ STUDENT_TABLE;
//        Cursor cursor = db.rawQuery(query,null);
//
//        while (cursor.moveToNext()){
//            HashMap<String,String> user = new HashMap<>();
//            user.put("name",cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
//            user.put("address",cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
//            user.put("gender",cursor.getString(cursor.getColumnIndex(COLUMN_GENDER)));
//            studentList.add(user);
//        }
//
//        return studentList;
//    }


    // For fetching the data from the database
    public ArrayList<StudentModel> selectStudent(){
        SQLiteDatabase db = this.getWritableDatabase();

//        Cursor cursor =  db.rawQuery(" SELECT " + COLUMN_NAME + ","+ COLUMN_ADDRESS + "," + COLUMN_GENDER + " FROM " + STUDENT_TABLE , null  );
        Cursor cursor =  db.rawQuery(" SELECT * FROM " + STUDENT_TABLE , null  );

        ArrayList<StudentModel> arrStudent = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                arrStudent.add(new StudentModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return arrStudent;


    }


    // for updating

    public void updateStudent( String orgStudentName , String studentName, String studentAddress, String studentGender){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, studentName);
        values.put(COLUMN_ADDRESS, studentAddress);
        values.put(COLUMN_ADDRESS, studentGender);

        db.update(STUDENT_TABLE, values, COLUMN_NAME+ "=?", new String[]{orgStudentName});
        db.close();

    }

    public void updateStudent(int id,  String studentName, String studentAddress, String studentGender){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, studentName);
        values.put(COLUMN_ADDRESS, studentAddress);
        values.put(COLUMN_GENDER, studentGender);


        db.update(STUDENT_TABLE, values, COLUMN_ID + "=" + id , null);
        db.close();


    }

    // Deleting Single book.
    public void deleteStudent(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(STUDENT_TABLE, COLUMN_ID + " =? ", new String[]{String.valueOf(id)});

        db.close();
    }

}
