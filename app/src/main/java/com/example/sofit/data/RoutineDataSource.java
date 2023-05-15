package com.example.sofit.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.sofit.model.Routine;

import java.util.ArrayList;

public class RoutineDataSource extends DataSource{

    /**
     * Columnas de la tabla
     */
    private final String[] allColumns = {MyDBHelper.COL_ROUTINE_NAME,MyDBHelper.COL_ROUTINE_USER,
            MyDBHelper.COL_ROUTINE_IMG};

    /**
     * Constructor.
     *
     * @param context
     */
    public RoutineDataSource(Context context) {
        //el último parámetro es la versión
        dbHelper = new MyDBHelper(context, null, null, 1);
    }

    /**
     * Recibe la película y crea el registro en la base de datos.
     *
     * @param routineToInsert
     * @return
     */
    public long createRoutine(Routine routineToInsert) {
        // Establecemos los valores que se insertaran
        ContentValues values = new ContentValues();

        values.put(MyDBHelper.COL_ROUTINE_NAME, routineToInsert.getName());
        values.put(MyDBHelper.COL_ROUTINE_USER, routineToInsert.getUser());
        values.put(MyDBHelper.COL_ROUTINE_IMG, routineToInsert.getImage());

        // Insertamos la valoracion
        long insertId =
                database.insert(MyDBHelper.TABLE_ROUTINES, null, values);

        return insertId;
    }

    public void deleteRoutine(Routine routineToDelete) {

        // Insertamos la valoracion
        database.execSQL("DELETE FROM " + MyDBHelper.TABLE_ROUTINES + " WHERE name = '" + routineToDelete.getName()+"'");
    }

    /**
     * Get the routine from the name
     * @param routineName
     * @return Routine
     */
    public Routine getRoutineFromName(String routineName){

        String whereClause = "name = ?";
        String[] whereArgs = new String[] {
                routineName
        };
        Cursor cursor = database.query(MyDBHelper.TABLE_ROUTINES, allColumns,
                whereClause, whereArgs, null, null, null);
        Routine routine=null;
        cursor.moveToFirst();
        routine= new Routine();
        routine.setName(cursor.getString(0));
        routine.setUser(cursor.getString(1));
        routine.setImage(cursor.getBlob(2));
        cursor.close();
        return routine;
    }

    /**
     * Obtiene todas las valoraciones andadidas por los usuarios.
     *
     * @return Lista de objetos de tipo Rutina
     */
    public ArrayList<Routine> getAllRoutines() {
        ArrayList<Routine> routinesList = new ArrayList<>();
        open();
        Cursor cursor = database.query(MyDBHelper.TABLE_ROUTINES, allColumns,
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final Routine rutina = new Routine();

            rutina.setName(cursor.getString(0));
            rutina.setUser(cursor.getString(1));
            //rutina.setImage(cursor.getBlob(2));


            routinesList.add(rutina);
            cursor.moveToNext();
        }

        cursor.close();
        close();
        return routinesList;
    }

}
