package com.example.sofit.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.sofit.model.ModelExercise;

import java.util.ArrayList;
import java.util.List;

public class ExerciseDataSource extends DataSource{
    private final String[] allColumns = {
                    MyDBHelper.COL_EXERCISES_NAME,
                    MyDBHelper.COL_EXERCISES_IMG,
                    MyDBHelper.COL_EXERCISES_SESSION
            };
    /**
     * Constructor.
     *
     * @param context
     */
    public ExerciseDataSource(Context context) {
        //el último parámetro es la versión
        dbHelper = new MyDBHelper(context, null, null, 1);
    }

    public long addExercise(ModelExercise exerciseToAdd, String idSession){
        ContentValues values =new ContentValues();
        values.put(MyDBHelper.COL_EXERCISES_NAME,exerciseToAdd.getName());
        values.put(MyDBHelper.COL_EXERCISES_IMG,exerciseToAdd.getImage());
        values.put(MyDBHelper.COL_EXERCISES_SESSION,idSession);
        open();
        long insertId = database.insert(MyDBHelper.TABLE_EXERCISES,null,values);
        close();
        return insertId;
    }
    public long createExercise(ModelExercise exerciseToInsert, String session) {
        // Establecemos los valores que se insertaran
        ContentValues values = new ContentValues();

        values.put(MyDBHelper.COL_EXERCISES_NAME, exerciseToInsert.getName());
        values.put(MyDBHelper.COL_EXERCISES_IMG, exerciseToInsert.getImage());
        values.put(MyDBHelper.COL_EXERCISES_SESSION, session);


        // Insertamos la valoracion
        long insertId =
                database.insert(MyDBHelper.TABLE_EXERCISES, null, values);

        return insertId;
    }

    public List<ModelExercise> getExercisesForSession(String sessionId){
        ArrayList<ModelExercise> exercises = new ArrayList<>();
        String whereClause = "SESSION_ID = ?";
        String[] whereArgs = new String[] {
                sessionId
        };
        Cursor cursor = database.query(MyDBHelper.TABLE_EXERCISES, allColumns,
                whereClause, whereArgs, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final ModelExercise exercise = new ModelExercise(cursor.getString(0), cursor.getString(1));
            exercises.add(exercise);
            cursor.moveToNext();
        }
        cursor.close();

        return exercises;
    }
    public List<ModelExercise> getAllExercises(){
        ArrayList<ModelExercise> exercises = new ArrayList<>();
        Cursor cursor = database.query(MyDBHelper.TABLE_EXERCISES, null,
                null, null, null, null, null);

        System.out.println(cursor.getCount());
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ModelExercise exercise = new ModelExercise(cursor.getString(0), cursor.getString(1));
            exercises.add(exercise);
            cursor.moveToNext();
        }
        cursor.close();
        return exercises;
    }
    public void deleteExercise(ModelExercise exerciseToDelete) {

        // Insertamos la valoracion
        database.execSQL("DELETE FROM " + MyDBHelper.TABLE_EXERCISES + " WHERE name = '" + exerciseToDelete.getName()+"'");
    }
}
