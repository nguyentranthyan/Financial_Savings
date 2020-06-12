package com.example.financial_savings.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.financial_savings.entities.TaiKhoan;
import com.example.financial_savings.interfaces.ICRUD;

import java.util.ArrayList;

public class TaiKhoanDAO implements ICRUD<TaiKhoan> {

    private static final String TABLE_NAME = "TaiKhoan";

    private static final String ID = "email";
    private static final String PASSWORD = "matKhau";
    private static final String CODE = "code";

    public TaiKhoan getByCode(SQLiteDatabase db, int code) {
        String query = "select * from " + TABLE_NAME + " where " + CODE + " = " + code;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        TaiKhoan taiKhoan = new TaiKhoan(cursor.getString(0), cursor.getString(1), cursor.getInt(2));
        cursor.close();
        return taiKhoan;
    }

    @Override
    public boolean insert(SQLiteDatabase db, TaiKhoan taiKhoan) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, taiKhoan.getEmail());
        contentValues.put(PASSWORD, taiKhoan.getMatKhau());
        contentValues.put(CODE, taiKhoan.getStatus());
        if (db.insert(TABLE_NAME, null, contentValues) > 0) {
            db.close();
            return true;
        }
        return false;
    }

    @Override
    public TaiKhoan getByID(SQLiteDatabase db, String id) {
        String query = "select * from " + TABLE_NAME + " where " + ID + " like '%" + id + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        TaiKhoan taiKhoan = new TaiKhoan(cursor.getString(0), cursor.getString(1), cursor.getInt(2));
        cursor.close();
        return taiKhoan;
    }

    @Override
    public ArrayList<TaiKhoan> getAll(SQLiteDatabase db) {
        ArrayList<TaiKhoan> list = new ArrayList<>();
        String query = "select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(new TaiKhoan(cursor.getString(0), cursor.getString(1), cursor.getInt(2)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public boolean delete(SQLiteDatabase db, TaiKhoan taiKhoan) {
        String query = "delete from " + TABLE_NAME + " where " + ID + " = " + taiKhoan.getEmail();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor == null)
            return false;
        else {
            cursor.moveToFirst();
            cursor.close();
        }
        return true;
    }

    @Override
    public boolean update(SQLiteDatabase db, TaiKhoan taiKhoan) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PASSWORD, taiKhoan.getMatKhau());
        contentValues.put(CODE, taiKhoan.getStatus());
        if (db.update(TABLE_NAME, contentValues, ID + " = ?", new String[]{taiKhoan.getEmail()}) > 0) {
            db.close();
            return true;
        }
        return false;
    }
}
