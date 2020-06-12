package com.example.financial_savings.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.financial_savings.entities.DanhMuc;
import com.example.financial_savings.interfaces.ICRUD;

import java.util.ArrayList;

public class DanhMucDAO implements ICRUD<DanhMuc> {

    private static final String TABLE_NAME = "DanhMuc";

    private static final String ID = "maDanhMuc";
    private static final String NAME = "tenDanhMuc";
    private static final String ICON = "bieuTuong";
    private static final String CATEGORY = "loaiDanhMuc";

    private static final String WALLET = "maVi";

    @Override
    public boolean insert(SQLiteDatabase db, DanhMuc danhMuc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, danhMuc.getMaDanhMuc());
        contentValues.put(NAME, danhMuc.getTenDanhMuc());
        contentValues.put(ICON, danhMuc.getBieuTuong());
        contentValues.put(CATEGORY, danhMuc.getLoaiDanhMuc());
        contentValues.put(WALLET, danhMuc.getMaVi());
        if (db.insert(TABLE_NAME, null, contentValues) > 0) {
            db.close();
            return true;
        }
        return false;
    }

    @Override
    public DanhMuc getByID(SQLiteDatabase db, String id) {
        String query = "select * from " + TABLE_NAME + " where " + ID + " like '%" + id + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        DanhMuc danhMuc = new DanhMuc(cursor.getString(0), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4));
        cursor.close();
        return danhMuc;
    }

    public DanhMuc getByName(SQLiteDatabase db, String name) {
        String query = "select * from " + TABLE_NAME + " where " + NAME + " like '%" + name + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        DanhMuc danhMuc = new DanhMuc(cursor.getString(0), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4));
        cursor.close();
        return danhMuc;
    }

    public ArrayList<DanhMuc> getByCategory(SQLiteDatabase db, String cate) {
        ArrayList<DanhMuc> list = new ArrayList<>();
        String query = "select * from " + TABLE_NAME + " where " + CATEGORY + " like '%" + cate + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(new DanhMuc(cursor.getString(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3), cursor.getString(4)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    public ArrayList<DanhMuc> getByWallet(SQLiteDatabase db, String wallet) {
        ArrayList<DanhMuc> list = new ArrayList<>();
        String query = "select * from " + TABLE_NAME + " where " + WALLET + " like '%" + wallet + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(new DanhMuc(cursor.getString(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3), cursor.getString(4)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public ArrayList<DanhMuc> getAll(SQLiteDatabase db) {
        ArrayList<DanhMuc> list = new ArrayList<>();
        String query = "select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(new DanhMuc(cursor.getString(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3), cursor.getString(4)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public boolean delete(SQLiteDatabase db, DanhMuc danhMuc) {
        String query = "delete from " + TABLE_NAME + " where " + ID + " = " + danhMuc.getMaDanhMuc();
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
    public boolean update(SQLiteDatabase db, DanhMuc danhMuc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, danhMuc.getTenDanhMuc());
        contentValues.put(ICON, danhMuc.getBieuTuong());
        contentValues.put(CATEGORY, danhMuc.getLoaiDanhMuc());
        contentValues.put(WALLET, danhMuc.getMaVi());
        if (db.update(TABLE_NAME, contentValues, ID + " = ?", new String[]{danhMuc.getMaDanhMuc()}) > 0) {
            db.close();
            return true;
        }
        return false;
    }
}
