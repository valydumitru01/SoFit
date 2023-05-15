package com.example.sofit.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.sofit.model.Serie;

import java.util.ArrayList;
import java.util.List;

public class SeriesDataSource extends DataSource{
    private final String[] allColumns = {
            MyDBHelper.COL_SERIES_REPS,
            MyDBHelper.COL_SERIES_WEIGHT
    };
    /**
     * Constructor.
     *
     * @param context
     */
    public SeriesDataSource(Context context) {
        //el último parámetro es la versión
        dbHelper = new MyDBHelper(context, null, null, 1);
    }

    public List<Serie> getSeriesForExercise(String exerciseId){
        ArrayList<Serie> series = new ArrayList<>();
        String whereClause = "EXERCISE_ID = ?";
        String[] whereArgs = new String[] {
                exerciseId
        };
        Cursor cursor = database.query(MyDBHelper.TABLA_SERIES, allColumns,
                whereClause, whereArgs, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final Serie serie = new Serie(cursor.getInt(0),cursor.getInt(1));
            series.add(serie);
            cursor.moveToNext();
        }
        cursor.close();
        return series;
    }

    public long addSerieOnExercise(Serie serie,String exercise_id) {
        ContentValues values = new ContentValues();

        values.put(MyDBHelper.COL_SERIES_EXERCISE, exercise_id);
        values.put(MyDBHelper.COL_SERIES_REPS, serie.getReps());
        values.put(MyDBHelper.COL_SERIES_WEIGHT, serie.getWeight());


        // Insertamos la valoracion
        long insertId =
                database.insert(MyDBHelper.TABLA_SERIES, null, values);

        return insertId;
    }
}
