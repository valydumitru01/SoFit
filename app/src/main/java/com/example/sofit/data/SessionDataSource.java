package com.example.sofit.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.sofit.model.ModelSession;

import java.util.ArrayList;
import java.util.List;

public class SessionDataSource extends DataSource{
    private final String[] allColumns =
            {MyDBHelper.COL_SESSIONS_NAME,
                    MyDBHelper.COL_EXERCISES_NAME, MyDBHelper.COL_SESSIONS_IMG
            };
    /**
     * Constructor.
     *
     * @param context
     */
    public SessionDataSource(Context context) {
        //el último parámetro es la versión
        dbHelper = new MyDBHelper(context, null, null, 1);
    }

    public long createSession(ModelSession session){
        ContentValues values =new ContentValues();
        values.put(MyDBHelper.COL_SESSIONS_NAME,session.getName());
        values.put(MyDBHelper.COL_SESSIONS_ROUTINE,session.getRoutine());
        values.put(MyDBHelper.COL_SESSIONS_IMG, session.getImage());
        open();
        long insertId = database.insert(MyDBHelper.TABLE_SESSIONS,null,values);
        close();
        return insertId;
    }

    public void deleteSession(ModelSession sessionToDelete) {
        database.execSQL("DELETE FROM " + MyDBHelper.TABLE_SESSIONS + " WHERE name = '" + sessionToDelete.getName()+"'");
    }

    public List<ModelSession> getAllSessions(){
        List<ModelSession> sessionsList = new ArrayList<>();
        open();
        Cursor cursor = database.query(MyDBHelper.TABLE_SESSIONS, allColumns,
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final ModelSession session = new ModelSession();

            session.setName(cursor.getString(0));
            session.setRoutine(cursor.getString(1));
            session.setImage(cursor.getBlob(2));

            sessionsList.add(session);
            cursor.moveToNext();
        }

        cursor.close();
        close();
        return sessionsList;
    }
    public List<ModelSession> getSessionsForRoutine(String routineId){
        List<ModelSession> sessionsList = new ArrayList<>();
        String whereClause = "ROUTINE_ID = ?";
        String[] whereArgs = new String[] {
                routineId
        };
        Cursor cursor = database.query(MyDBHelper.TABLE_SESSIONS, allColumns,
                whereClause, whereArgs, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final ModelSession session = new ModelSession();

            session.setName(cursor.getString(0));
            session.setRoutine(cursor.getString(1));
            session.setImage(cursor.getBlob(2));

            sessionsList.add(session);
            cursor.moveToNext();
        }
        cursor.close();
        return sessionsList;
    }

    public ModelSession getSessionByName(String sessionName) {
        String whereClause = "name = ?";
        String[] whereArgs = new String[] {
                sessionName
        };
        Cursor cursor = database.query(MyDBHelper.TABLE_SESSIONS, allColumns,
                whereClause, whereArgs, null, null, null);
        cursor.moveToFirst();
        ModelSession session = new ModelSession();
        session.setName(cursor.getString(0));
        session.setRoutine(cursor.getString(1));
        session.setImage(cursor.getBlob(2));
        cursor.close();
        return session;
    }
}
