package com.example.gymbody.dbHelperVideo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class AnhVideoDBhelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "anhVideo.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "anhVideo";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TIEUDE = "tieude";
    public static final String COLUMN_NGAY = "ngay";
    public static final String COLUMN_ANH_URL = "anh";
    public static final String COLUMN_VIDEO_URL = "video";
    private static final String COLUMN_URL = "url";

    // Câu lệnh tạo bảng
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + "," + COLUMN_TIEUDE + " TEXT," + COLUMN_NGAY + " TEXT," + COLUMN_ANH_URL + " TEXT," + COLUMN_VIDEO_URL + " TEXT)";
    // Câu lệnh xóa bảng
    public final static String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public AnhVideoDBhelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Khi tạo cơ sở dữ liệu, thực thi câu lệnh tạo bảng
        try {
            sqLiteDatabase.execSQL(CREATE_TABLE);
            Log.e("Database", "Table created successfully");

            // Thêm dữ liệu mẫu
            insertSampleData(sqLiteDatabase);
        } catch (Exception e) {
            Log.e("Database Error", "Error creating table: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Khi tạo cơ sở dữ liệu, thực thi câu lệnh xóa bảng
        try {
            sqLiteDatabase.execSQL(DROP_TABLE);
            Log.e("Database", "Table created successfully");
        } catch (Exception e) {
            Log.e("Database Error", "Error creating table: " + e.getMessage());
        }
    }

    public void insertSampleData(SQLiteDatabase db) {
        try {
            String insert1 = "INSERT INTO " + TABLE_NAME + " (" +
                    COLUMN_TIEUDE + ", " +
                    COLUMN_NGAY + ", " +
                    COLUMN_ANH_URL + ", " +
                    COLUMN_VIDEO_URL + ") VALUES ('Bài tập ngực', '2024-11-15', 'https://baohanam.com.vn/DATA/IMAGES/2022/09/09/20220909085625-56niem-vui-dc-mua.jpg', 'https://v3.tiktokcdn.com/63bab192b62f693367571026f1204d05/6737c868/video/tos/alisg/tos-alisg-pve-0037c001/ognwIkfEADE0TgLOEzgBfFFsuBURMENKRMJwVQ/?a=1180&bti=OjR2KXMxaGRzZ3dAajo6NkAvOm1mXm92aSsxcWAjMTQzYA%3D%3D&ch=0&cr=0&dr=0&er=0&lr=all&net=0&cd=0%7C0%7C0%7C0&cv=1&br=4372&bt=2186&cs=0&ds=6&ft=~jVQCzEKhWH6BYe~glLo0PD1&mime_type=video_mp4&qs=0&rc=ODk3ZjQ8ZTNnaWg1PDNnZEBpM3kza3A5cjZmdjMzODczNEA0MS8vMDM1NWMxLzVfYV40YSMvZ2svMmQ0a2ZgLS1kMTFzcw%3D%3D&vvpl=1&l=202411151616187898949A2547C508EEB0&btag=e00088000&cc=6')";

            String insert2 = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_TIEUDE + ", " + COLUMN_NGAY + ", " + COLUMN_ANH_URL + ", " + COLUMN_VIDEO_URL + ") " +
                    "VALUES ('Bài tập chân', '2024-12-16', 'https://media-cdn-v2.laodong.vn/Storage/NewsPortal/2022/8/23/1084256/295497942_2922294937.jpg', 'https://youtu.be/iRM2YsHioB4')";

            String insert3 = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_TIEUDE + ", " + COLUMN_NGAY + ", " + COLUMN_ANH_URL + ", " + COLUMN_VIDEO_URL + ") " +
                    "VALUES ('Bài tập tay', '2024-10-15', 'https://khoinguonsangtao.vn/wp-content/uploads/2022/10/hinh-nen-trai-dat.jpg', 'https://v3.tiktokcdn.com/63bab192b62f693367571026f1204d05/6737c868/video/tos/alisg/tos-alisg-pve-0037c001/ognwIkfEADE0TgLOEzgBfFFsuBURMENKRMJwVQ/?a=1180&bti=OjR2KXMxaGRzZ3dAajo6NkAvOm1mXm92aSsxcWAjMTQzYA%3D%3D&ch=0&cr=0&dr=0&er=0&lr=all&net=0&cd=0%7C0%7C0%7C0&cv=1&br=4372&bt=2186&cs=0&ds=6&ft=~jVQCzEKhWH6BYe~glLo0PD1&mime_type=video_mp4&qs=0&rc=ODk3ZjQ8ZTNnaWg1PDNnZEBpM3kza3A5cjZmdjMzODczNEA0MS8vMDM1NWMxLzVfYV40YSMvZ2svMmQ0a2ZgLS1kMTFzcw%3D%3D&vvpl=1&l=202411151616187898949A2547C508EEB0&btag=e00088000&cc=6')";

            db.execSQL(insert1);
            db.execSQL(insert2);
            db.execSQL(insert3);
            Log.e("Database", "Sample data inserted successfully");
        } catch (Exception e) {
            Log.e("Database Error", "Error inserting sample data: " + e.getMessage());
        }
    }

    public String getVideoUrlById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String videoUrl = null;
        Cursor cursor = null;

        try {
            String query = "SELECT " + COLUMN_VIDEO_URL + " FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
            if (cursor != null && cursor.moveToFirst()) {
                videoUrl = cursor.getString(0);
            }
        } catch (Exception e) {
            Log.e("Database Error", "Error fetching video URL: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return videoUrl;
    }


}
