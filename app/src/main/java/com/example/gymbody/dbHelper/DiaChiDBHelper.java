package com.example.gymbody.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.gymbody.model.DiaChiModel;

import java.util.ArrayList;
import java.util.List;

public class DiaChiDBHelper extends SQLiteOpenHelper {
    // Database information
    private static final String DATABASE_NAME = "DiaChi.db";
    private static final int DATABASE_VERSION = 2;

    // Table and column names
    private static final String TABLE_NAME = "DiaChi";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_HO_TEN = "hoTen";
    private static final String COLUMN_SDT = "sdt";
    private static final String COLUMN_TINH_THANH = "tinhThanh";
    private static final String COLUMN_QUAN_HUYEN = "quanHuyen";
    private static final String COLUMN_PHUONG_XA = "phuongXa";
    private static final String COLUMN_SO_NHA = "soNha";

    public DiaChiDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // SQL command to create table
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_HO_TEN + " TEXT, "
                + COLUMN_SDT + " TEXT, "
                + COLUMN_TINH_THANH + " TEXT, "
                + COLUMN_QUAN_HUYEN + " TEXT, "
                + COLUMN_PHUONG_XA + " TEXT, "
                + COLUMN_SO_NHA + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Drop the older table if exists
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create a new table
        onCreate(sqLiteDatabase);
    }

    // Add new DiaChi
    public long addDiaChi(DiaChiModel diaChi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HO_TEN, diaChi.getHoTen());
        values.put(COLUMN_SDT, diaChi.getSdt());
        values.put(COLUMN_TINH_THANH, diaChi.getTinhThanh());
        values.put(COLUMN_QUAN_HUYEN, diaChi.getQuanHuyen());
        values.put(COLUMN_PHUONG_XA, diaChi.getPhuongXa());
        values.put(COLUMN_SO_NHA, diaChi.getSoNha());

        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }

    // Update DiaChi
    public int updateDiaChi(DiaChiModel diaChi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HO_TEN, diaChi.getHoTen());
        values.put(COLUMN_SDT, diaChi.getSdt());
        values.put(COLUMN_TINH_THANH, diaChi.getTinhThanh());
        values.put(COLUMN_QUAN_HUYEN, diaChi.getQuanHuyen());
        values.put(COLUMN_PHUONG_XA, diaChi.getPhuongXa());
        values.put(COLUMN_SO_NHA, diaChi.getSoNha());

        // Update row
        int rowsAffected = db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(diaChi.getId())});
        db.close();
        return rowsAffected;
    }

    // Delete DiaChi
    public void deleteDiaChi(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Get a single DiaChi
    public DiaChiModel getDiaChi(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            DiaChiModel diaChi = new DiaChiModel(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HO_TEN)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SDT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TINH_THANH)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUAN_HUYEN)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHUONG_XA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SO_NHA))
            );
            cursor.close();
            return diaChi;
        } else {
            return null;
        }
    }

    // Get all DiaChi
    public List<DiaChiModel> getAllDiaChi() {
        List<DiaChiModel> diaChiList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                DiaChiModel diaChi = new DiaChiModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HO_TEN)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SDT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TINH_THANH)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUAN_HUYEN)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHUONG_XA)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SO_NHA))
                );
                diaChiList.add(diaChi);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return diaChiList;
    }
}
