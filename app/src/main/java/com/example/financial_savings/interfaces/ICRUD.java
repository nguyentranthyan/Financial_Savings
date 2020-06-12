package com.example.financial_savings.interfaces;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public interface ICRUD<T> {
    boolean insert(SQLiteDatabase db, T t);
    T getByID(SQLiteDatabase db, String id);
    ArrayList<T> getAll(SQLiteDatabase db);
    boolean delete(SQLiteDatabase db, T t);
    boolean update(SQLiteDatabase db, T t);
}
