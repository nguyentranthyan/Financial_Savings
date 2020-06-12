package com.example.financial_savings.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.financial_savings.entities.ViCaNhan;
import com.example.financial_savings.interfaces.ICRUD;

import java.util.ArrayList;

public class ViCaNhanDAO implements ICRUD<ViCaNhan> {

    private static final String TABLE_NAME = "ViCaNhan";

    private static final String ID = "maVi";
    private static final String MONEY = "soTien";
    private static final String NAME = "tenVi";

    @Override
    public boolean insert(SQLiteDatabase db, ViCaNhan viCaNhan) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, viCaNhan.getMaVi());
        contentValues.put(MONEY, viCaNhan.getSoTien());
        contentValues.put(NAME, viCaNhan.getTenVi());
        if (db.insert(TABLE_NAME, null, contentValues) > 0) {
            db.close();
            return true;
        }
        return false;
    }

    @Override
    public ViCaNhan getByID(SQLiteDatabase db, String id) {
        String query = "select * from " + TABLE_NAME + " where " + ID + " like '%" + id + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        ViCaNhan viCaNhan = new ViCaNhan(cursor.getString(0), cursor.getDouble(1),
                cursor.getString(2));
        cursor.close();
        return viCaNhan;
    }

    public ViCaNhan getByName(SQLiteDatabase db, String name) {
        String query = "select * from " + TABLE_NAME + " where " + NAME + " like '%" + name + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        ViCaNhan viCaNhan = new ViCaNhan(cursor.getString(0), cursor.getDouble(1),
                cursor.getString(2));
        cursor.close();
        return viCaNhan;
    }

    @Override
    public ArrayList<ViCaNhan> getAll(SQLiteDatabase db) {
        ArrayList<ViCaNhan> list = new ArrayList<>();
        String query = "select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(new ViCaNhan(cursor.getString(0), cursor.getDouble(1),
                    cursor.getString(2)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    public ArrayList<ViCaNhan> getAllAnother(SQLiteDatabase db, String id) {
        ArrayList<ViCaNhan> list = new ArrayList<>();
        String query = "select * from " + TABLE_NAME + " where " + ID + " not in (" + id + ")";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(new ViCaNhan(cursor.getString(0), cursor.getDouble(1),
                    cursor.getString(2)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public boolean delete(SQLiteDatabase db, ViCaNhan viCaNhan) {
        String query = "delete from " + TABLE_NAME + " where " + ID + " = " + viCaNhan.getMaVi();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor == null)
            return false;
        else {
            cursor.moveToFirst();
            cursor.close();
        }
        return false;
    }

    @Override
    public boolean update(SQLiteDatabase db, ViCaNhan viCaNhan) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MONEY, viCaNhan.getSoTien());
        contentValues.put(NAME, viCaNhan.getTenVi());
        if (db.update(TABLE_NAME, contentValues, ID + " = ?", new String[]{viCaNhan.getMaVi()}) > 0) {
            db.close();
            return true;
        }
        return false;
    }
}
