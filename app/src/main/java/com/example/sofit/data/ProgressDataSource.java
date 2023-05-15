package com.example.sofit.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.sofit.model.ModelProgress;

import java.util.ArrayList;
import java.util.List;

public class ProgressDataSource extends DataSource {

    /**
     * Constructor.
     *
     * @param context
     */
    public ProgressDataSource(Context context) {
        //el último parámetro es la versión
        dbHelper = new MyDBHelper(context, null, null, 1);
    }
    /**
     * Columnas de la tabla
     */
    private final String[] allColumns =
            {
                MyDBHelper.COL_PROGRESS_WEIGHT,
                MyDBHelper.COL_PROGRESS_FAT,
                MyDBHelper.COL_PROGRESS_MUSCLE,
                MyDBHelper.COL_PROGRESS_WATER,
                MyDBHelper.COL_PROGRESS_USER,
                MyDBHelper.COL_PROGRESS_USER
            };

    public List<ModelProgress> getProgressData() {
        // Lista que almacenara el resultado
        ArrayList<ModelProgress> progressList = new ArrayList<>();

        Cursor cursor = database.query(MyDBHelper.TABLE_PROGRESS, allColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final ModelProgress modelProgress = new ModelProgress();
            modelProgress.setWeight(cursor.getFloat(0));
            modelProgress.setFat(cursor.getFloat(1));
            modelProgress.setMuscle(cursor.getFloat(2));
            modelProgress.setWater(cursor.getFloat(3));
            modelProgress.setUser(cursor.getString(4));
            progressList.add(modelProgress);
            cursor.moveToNext();
        }

        cursor.close();

        return progressList;
    }
    public void addProgressData(ModelProgress modelProgress){
        ContentValues contentValues=new ContentValues();

        contentValues.put(MyDBHelper.COL_PROGRESS_FAT,modelProgress.getFat());
        contentValues.put(MyDBHelper.COL_PROGRESS_WEIGHT,modelProgress.getWeight());
        contentValues.put(MyDBHelper.COL_PROGRESS_MUSCLE,modelProgress.getMuscle());
        contentValues.put(MyDBHelper.COL_PROGRESS_USER,modelProgress.getUser());
        contentValues.put(MyDBHelper.COL_PROGRESS_WATER,modelProgress.getWater());

        database.insert(MyDBHelper.TABLE_PROGRESS,null,contentValues);
    }
}
