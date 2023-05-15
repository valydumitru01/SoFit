package com.example.sofit.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * MyDHelper
 */
public class MyDBHelper extends SQLiteOpenHelper {

    /**
     * Nombre y version de la base de datos
     */
    private static final String DATABASE_NAME = "sofit.db";
    private static final int DATABASE_VERSION = 1;

    /**
     * Nombre de la tabla usuario y sus columnas
     * nombre_usuario;correo_usuario;contraseña_usuario;altura_usuario;
     * peso_usuario;edad_usuario;sexo_usuario;nombre_rutina(FOREIGN_KEY)
     */
    public static final String TABLE_USER = "user";

    public static final String COL_USER_NAME = "name";
    public static final String COL_USER_HEIGHT = "height";
    public static final String COL_USER_WEIGHT = "weight";
    public static final String COL_USER_AGE = "age";
    public static final String COL_USER_SEX = "sex";
    public static final String COL_USER_IMG = "user_image";

    //--------------------------------------------------------------------
    /**
     * Nombre de la tabla rutinas y sus columnas
     */
    public static final String TABLE_ROUTINES = "routines";

    public static final String COL_ROUTINE_NAME = "name";
    public static final String COL_ROUTINE_IMG = "image";
    public static final String COL_ROUTINE_USER = "user_id";
    public static final String COL_USER_CURRENT_ROUTINE = "selected_routine";

    //--------------------------------------------------------------------
    /**
     * Nombre de la tabla sesiones y sus columnas
     */
    public static final String TABLE_SESSIONS = "sessions";

    public static final String COL_SESSIONS_NAME = "name";
    public static final String COL_SESSIONS_ROUTINE = "routine_id";
    public static final String COL_SESSIONS_IMG = "session_image";

    //--------------------------------------------------------------------
    /**
     * Nombre de la tabla ejercicios y sus columnas
     */
    public static final String TABLE_EXERCISES = "exercises";

    public static final String COL_EXERCISES_NAME = "name";
    public static final String COL_EXERCISES_IMG = "exercise_image";
    public static final String COL_EXERCISES_SESSION = "session_id";
    //--------------------------------------------------------------------

    /**
     * Nombre de la tabla series y sus columnas
     */
    public static final String TABLA_SERIES = "series";

    public static final String COL_SERIES_REPS = "reps";
    public static final String COL_SERIES_WEIGHT = "weight";
    public static final String COL_SERIES_EXERCISE = "exercise_id";
    //--------------------------------------------------------------------
    /**
     * Nombre de la tabla progreso y sus columnas
     */
    public static final String TABLE_PROGRESS = "progress";

    public static final String COL_PROGRESS_WEIGHT = "weight";
    public static final String COL_PROGRESS_FAT = "fat";
    public static final String COL_PROGRESS_MUSCLE = "muscle";
    public static final String COL_PROGRESS_WATER = "water";
    public static final String COL_PROGRESS_USER = "user_id";

    //--------------------------------------------------------------------


    /**
     * Script para crear la base datos en SQL
     */
    private static final String CREATE_TABLE_USER = "create table "
            + TABLE_USER
            + "( " +
            COL_USER_NAME + " text, " +
            COL_USER_HEIGHT + " integer, " +
            COL_USER_WEIGHT + " integer, " +
            COL_USER_AGE + " integer, " +
            COL_USER_SEX + " text, " +
            COL_USER_IMG + " BLOB, " +
            COL_USER_CURRENT_ROUTINE + " text " +
            ");";

    private static final String CREATE_TABLE_ROUTINE = " create table "
            + TABLE_ROUTINES
            + "( " +
            COL_ROUTINE_NAME + " text primary key not null, " +
            COL_ROUTINE_IMG + " BLOB not null, " +
            COL_ROUTINE_USER + " text not null" +
            ");";

    private static final String CREATE_TABLE_SESSIONS = "create table "
            + TABLE_SESSIONS
            + "( " +
            COL_SESSIONS_NAME + " text primary key not null, " +
            COL_SESSIONS_ROUTINE + " text not null, " +
            COL_SESSIONS_IMG + " BLOB not null" +
            ");";

    private static final String CREATE_TABLE_EXERCISES =
            " create table " + TABLE_EXERCISES +
                    "( "
                    + COL_EXERCISES_NAME + " text primary key not null, "
                    + COL_EXERCISES_IMG + " text not null, "
                    + COL_EXERCISES_SESSION + " text not null"
                    + ");";
    private static final String CREATE_TABLE_SERIES = " create table "
            + TABLA_SERIES
            + "( " +
            " ID integer primary key autoincrement not null, " +
            COL_SERIES_WEIGHT + " real not null, " +
            COL_SERIES_EXERCISE + " text not null, " +
            COL_SERIES_REPS + " integer not null " +
            ");";
    private static final String CREATE_TABLE_PROGRESS =
            " create table " + TABLE_PROGRESS +
                    "( "
                    + " ID integer primary key autoincrement not null, "
                    + COL_PROGRESS_WEIGHT + " text not null, "
                    + COL_PROGRESS_FAT + " real not null, "
                    + COL_PROGRESS_MUSCLE + " real not null, "
                    + COL_PROGRESS_WATER + " real not null,"
                    + COL_PROGRESS_USER + " text not null"
                    + ");";


    /**
     * Script para borrar la base de datos (SQL)
     */
    private static final String DATABASE_DROP_USER = "DROP TABLE IF EXISTS " + TABLE_USER;
    private static final String DATABASE_DROP_ROUTINES = "DROP TABLE IF EXISTS " + TABLE_ROUTINES;
    private static final String DATABASE_DROP_SESSIONS = "DROP TABLE IF EXISTS " + TABLE_SESSIONS;
    private static final String DATABASE_DROP_EXERCISES = "DROP TABLE IF EXISTS " + TABLE_EXERCISES;
    private static final String DATABASE_DROP_PROGRESS = "DROP TABLE IF EXISTS " + TABLE_PROGRESS;
    private static final String DATABASE_DROP_SERIES = "DROP TABLE IF EXISTS " + TABLA_SERIES;


    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                      int version) {
        super(context, DATABASE_NAME, null, 5);
    }
    private void insertData(SQLiteDatabase db){
        ContentValues values;
        byte[] no_image_bytes={};

        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name NOT IN ('android_metadata', 'sqlite_sequence', 'room_master_table') ",null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            System.out.println(cursor.getString(0));
            System.out.println(getTableAsString(db,cursor.getString(0)));
            cursor.moveToNext();
        }

        /**
         * ---Usuario---
         */
        values = new ContentValues();
        values.put(COL_USER_NAME, "Pepe");
        values.put(COL_USER_AGE, 21);
        values.put(COL_USER_HEIGHT, 187);
        values.put(COL_USER_SEX, "Pepe");
        values.put(COL_USER_WEIGHT, 80);
        values.put(COL_USER_IMG,no_image_bytes);
        values.put(COL_USER_CURRENT_ROUTINE,"Strength");
        db.insert(TABLE_USER, null, values);
        /**
         * ---Progress---
         */
        values=new ContentValues();
        values.put(COL_PROGRESS_MUSCLE,30);
        values.put(COL_PROGRESS_WATER,20);
        values.put(COL_PROGRESS_FAT,12);
        values.put(COL_PROGRESS_WEIGHT,80);
        values.put(COL_PROGRESS_USER,"Pepe");
        db.insert(TABLE_PROGRESS, null, values);

        values.put(COL_PROGRESS_MUSCLE,31);
        values.put(COL_PROGRESS_WATER,21);
        values.put(COL_PROGRESS_FAT,13);
        values.put(COL_PROGRESS_WEIGHT,81);
        values.put(COL_PROGRESS_USER,"Pepe");
        db.insert(TABLE_PROGRESS, null, values);

        values.put(COL_PROGRESS_MUSCLE,32);
        values.put(COL_PROGRESS_WATER,23);
        values.put(COL_PROGRESS_FAT,13);
        values.put(COL_PROGRESS_WEIGHT,84);
        values.put(COL_PROGRESS_USER,"Pepe");
        db.insert(TABLE_PROGRESS, null, values);
        /**
         * ----Rutinas----
         */
        values = new ContentValues();
        values.put(COL_ROUTINE_NAME, "Strength");
        values.put(COL_ROUTINE_IMG, no_image_bytes);
        values.put(COL_ROUTINE_USER,"Pepe");
        db.insert(TABLE_ROUTINES, null, values);


        /**
         * ----SESIONES----
         * Debe incluir la clave foranea de nombre de rutina
         */
        values = new ContentValues();
        values.put(COL_SESSIONS_NAME, "Chest");
        values.put(COL_SESSIONS_IMG, no_image_bytes);
        values.put(COL_SESSIONS_ROUTINE, "Strength");
        db.insert(TABLE_SESSIONS, null, values);
        values.put(COL_SESSIONS_NAME, "Arms");
        values.put(COL_SESSIONS_IMG, no_image_bytes);
        values.put(COL_SESSIONS_ROUTINE, "Strength");
        db.insert(TABLE_SESSIONS, null, values);
        values.put(COL_SESSIONS_NAME, "Legs");
        values.put(COL_SESSIONS_IMG, no_image_bytes);
        values.put(COL_SESSIONS_ROUTINE, "Strength");
        db.insert(TABLE_SESSIONS, null, values);

        /**
         * ----Ejercicios----
         * Debe incluir la clave foranea de nombre de sesion
         */
        values = new ContentValues();
        values.put(COL_EXERCISES_IMG, "");
        values.put(COL_EXERCISES_NAME, "Bench Press");
        values.put(COL_EXERCISES_SESSION, "Chest");
        db.insert(TABLE_EXERCISES, null, values);
        values.put(COL_EXERCISES_IMG, "");
        values.put(COL_EXERCISES_NAME, "Biceps Curl");
        values.put(COL_EXERCISES_SESSION, "Arms");
        db.insert(TABLE_EXERCISES, null, values);
        values.put(COL_EXERCISES_IMG, "");
        values.put(COL_EXERCISES_NAME, "Squat");
        values.put(COL_EXERCISES_SESSION, "Legs");
        db.insert(TABLE_EXERCISES, null, values);

        /**
         * ----SERIES----
         * Debe incluir la clave foranea de nombre de ejercicio
         */
        values = new ContentValues();
        values.put(COL_SERIES_WEIGHT, 30);
        values.put(COL_SERIES_REPS, 10);
        values.put(COL_SERIES_EXERCISE, "Bench Press");
        db.insert(TABLA_SERIES, null, values);
        values.put(COL_SERIES_WEIGHT, 30);
        values.put(COL_SERIES_REPS, 9);
        values.put(COL_SERIES_EXERCISE, "Bench Press");
        db.insert(TABLA_SERIES, null, values);
        values.put(COL_SERIES_WEIGHT, 30);
        values.put(COL_SERIES_REPS, 8);
        values.put(COL_SERIES_EXERCISE, "Bench Press");
        db.insert(TABLA_SERIES, null, values);
        values.put(COL_SERIES_WEIGHT, 30);
        values.put(COL_SERIES_REPS, 8);
        values.put(COL_SERIES_EXERCISE, "Bench Press");
        db.insert(TABLA_SERIES, null, values);
        values.put(COL_SERIES_WEIGHT, 100);
        values.put(COL_SERIES_REPS, 2);
        values.put(COL_SERIES_EXERCISE, "Squat");
        db.insert(TABLA_SERIES, null, values);
        values.put(COL_SERIES_WEIGHT, 120);
        values.put(COL_SERIES_REPS, 1);
        values.put(COL_SERIES_EXERCISE, "Squat");
        db.insert(TABLA_SERIES, null, values);
        values.put(COL_SERIES_WEIGHT, 25);
        values.put(COL_SERIES_REPS, 3);
        values.put(COL_SERIES_EXERCISE, "Biceps Curl");
        db.insert(TABLA_SERIES, null, values);
        values.put(COL_SERIES_WEIGHT, 25);
        values.put(COL_SERIES_REPS, 3);
        values.put(COL_SERIES_EXERCISE, "Biceps Curl");
        db.insert(TABLA_SERIES, null, values);

        Log.i("ONCREATE", "EJECUTO CREACION");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //invocamos execSQL pq no devuelve ningún tipo de dataset
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_ROUTINE);
        db.execSQL(CREATE_TABLE_SESSIONS);
        db.execSQL(CREATE_TABLE_EXERCISES);
        db.execSQL(CREATE_TABLE_PROGRESS);
        db.execSQL(CREATE_TABLE_SERIES);

        //insertData(db);
    }
    @SuppressLint("Range")
    public String getTableAsString(SQLiteDatabase db, String tableName) {
        String tableString = String.format("Table %s columns:\n", tableName);
        Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
        if (allRows.moveToFirst() ){
            String[] columnNames = allRows.getColumnNames();
            do {
                for (String name: columnNames) {
                    tableString += String.format("%s: %s\n", name,
                            allRows.getString(allRows.getColumnIndex(name)));
                }
                tableString += "\n";

            } while (allRows.moveToNext());
        }

        return tableString;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DATABASE_DROP_USER);
        db.execSQL(DATABASE_DROP_ROUTINES);
        db.execSQL(DATABASE_DROP_SESSIONS);
        db.execSQL(DATABASE_DROP_EXERCISES);
        db.execSQL(DATABASE_DROP_PROGRESS);
        db.execSQL(DATABASE_DROP_SERIES);
        this.onCreate(db);
    }
}
