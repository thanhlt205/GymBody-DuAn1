package com.example.gymbody.dbHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.gymbody.model.VideoFavoriteModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class AnhVideoDBhelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "anhVideo.db";
    public static final int DATABASE_VERSION = 2;
    public static final String TABLE_NAME = "anhVideo";
    public static final String TABLE_NAME_FAVORITES = "favorite_videos";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TIEUDE = "tieude";
    public static final String COLUMN_NGAY = "ngay";
    public static final String COLUMN_ANH_URL = "anh";
    public static final String COLUMN_VIDEO_URL = "video";

    //tạo bảng
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_TIEUDE + " TEXT," +
            COLUMN_NGAY + " TEXT," +
            COLUMN_ANH_URL + " TEXT," +
            COLUMN_VIDEO_URL + " TEXT," +
            "isFavorite INTEGER DEFAULT 0)";
    //xóa bảng
    public final static String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public final static String DROP_TABLE_FAVORITES = "DROP TABLE IF EXISTS " + TABLE_NAME_FAVORITES;

    //thêm video yêu thích
    public static final String CREATE_FAVORITES_TABLE = "CREATE TABLE favorite_videos (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "video_id INTEGER," +
            "FOREIGN KEY (video_id) REFERENCES anhVideo(id))";

    public AnhVideoDBhelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Khi tạo cơ sở dữ liệu, thực thi câu lệnh tạo bảng
        try {
            sqLiteDatabase.execSQL(CREATE_TABLE);
            sqLiteDatabase.execSQL(CREATE_FAVORITES_TABLE);
            Log.e("Database", "Table created successfully");

            // Thêm dữ liệu
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
            sqLiteDatabase.execSQL(DROP_TABLE_FAVORITES);
            onCreate(sqLiteDatabase);
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
                    COLUMN_VIDEO_URL + ") VALUES ('Bài tập full tay', '2024-12-07', 'content://media/external/images/media/4004', 'content://media/external/video/media/4002')";

            String insert2 = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_TIEUDE + ", " + COLUMN_NGAY + ", " + COLUMN_ANH_URL + ", " + COLUMN_VIDEO_URL + ") " +
                    "VALUES ('Kỹ thuật tập lưng', '2024-12-08', 'content://media/external/images/media/4006', 'content://media/external/video/media/4005')";

            String insert3 = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_TIEUDE + ", " + COLUMN_NGAY + ", " + COLUMN_ANH_URL + ", " + COLUMN_VIDEO_URL + ") " +
                    "VALUES ('Các bài tập chỉ với tạ đơn', '2024-11-28', 'content://media/external/images/media/4008', 'content://media/external/video/media/4007')";
            String insert4 = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_TIEUDE + ", " + COLUMN_NGAY + ", " + COLUMN_ANH_URL + ", " + COLUMN_VIDEO_URL + ") " +
                    "VALUES ('Bài tập ngực & tay', '2024-11-18', 'content://media/external/images/media/4010', 'content://media/external/video/media/4009')";

            db.execSQL(insert1);
            db.execSQL(insert2);
            db.execSQL(insert3);
            db.execSQL(insert4);
            Log.e("Database", "Sample data inserted successfully");
        } catch (Exception e) {
            Log.e("Database Error", "Error inserting sample data: " + e.getMessage());
        }
    }
    public void insert(String tieude, String ngay, String anhUrl, String videoUrl) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            // Câu lệnh INSERT vào bảng anhVideo
            String insertQuery = "INSERT INTO " + TABLE_NAME + " (" +
                    COLUMN_TIEUDE + ", " +
                    COLUMN_NGAY + ", " +
                    COLUMN_ANH_URL + ", " +
                    COLUMN_VIDEO_URL + ") VALUES (?, ?, ?, ?)";

            // Thực hiện câu lệnh INSERT
            db.execSQL(insertQuery, new Object[]{tieude, ngay, anhUrl, videoUrl});
            Log.e("Database", "Data inserted successfully");
        } catch (Exception e) {
            Log.e("Database Error", "Error inserting data: " + e.getMessage());
        } finally {
            db.close();
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

    public void addVideoToFavorites(int videoId) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            String insertQuery = "INSERT INTO " + TABLE_NAME_FAVORITES + " (video_id) VALUES (?)";
            db.execSQL(insertQuery, new Object[]{videoId});
            Log.e("Database", "Video added to favorites");
        } catch (Exception e) {
            Log.e("Database Error", "Error adding video to favorites: " + e.getMessage());
        } finally {
            db.close();
        }
    }

    public List<VideoFavoriteModel> getFavoriteVideoDetails() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<VideoFavoriteModel> favoriteVideos = new ArrayList<>();
        Cursor cursor = null;

        try {
            String query = "SELECT " + COLUMN_ID + ", " + COLUMN_ANH_URL + ", " + COLUMN_TIEUDE + ", " + COLUMN_NGAY +
                    " FROM " + TABLE_NAME +
                    " WHERE " + COLUMN_ID + " IN (SELECT video_id FROM " + TABLE_NAME_FAVORITES + ")";
            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0);          // Lấy ID
                    String anhUrl = cursor.getString(1); // Lấy URL ảnh
                    String tieude = cursor.getString(2); // Lấy tiêu đề
                    String ngay = cursor.getString(3);   // Lấy ngày tạo

                    favoriteVideos.add(new VideoFavoriteModel(id, anhUrl, tieude, ngay));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Database Error", "Error fetching favorite videos: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return favoriteVideos;
    }

//    public void uploadVideoToGoogleDrive(final Context context, Uri videoUri, final String title, final String date, final String thumbnailUrl) {
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
//        if (account != null) {
//            Drive driveService = Drive.getDriveClient(context, account);
//            DriveContents driveContents = Drive.getDriveContentsClient(context, account);
//
//            // Mở video từ URI
//            driveContents.openFile(videoUri, DriveFile.MODE_READ_WRITE).addOnSuccessListener(new OnSuccessListener<DriveContents>() {
//                @Override
//                public void onSuccess(DriveContents result) {
//                    // Tạo Metadata cho tệp video
//                    MetadataChangeSet metadataChangeSet = new MetadataChangeSet.Builder()
//                            .setTitle(title)  // Tiêu đề của video
//                            .setMimeType("video/mp4") // Định dạng video (bạn có thể thay đổi nếu cần)
//                            .setDescription("Uploaded by GymBody app")  // Mô tả tệp
//                            .build();
//
//                    // Tạo tệp trên Google Drive
//                    driveService.createFile(metadataChangeSet, result)
//                            .addOnSuccessListener(new OnSuccessListener<DriveFile>() {
//                                @Override
//                                public void onSuccess(DriveFile driveFile) {
//                                    // Nhận link của video từ Google Drive
//                                    String videoDriveUrl = driveFile.getDriveId().asString();
//                                    Log.d("Drive", "Video uploaded successfully: " + videoDriveUrl);
//
//                                    // Sau khi upload, lưu link video vào SQLite
//                                    insertVideo(title, date, thumbnailUrl, videoDriveUrl);
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.e("Drive", "Error uploading video: " + e.getMessage());
//                                }
//                            });
//                }
//            });
//        }
//    }

}
