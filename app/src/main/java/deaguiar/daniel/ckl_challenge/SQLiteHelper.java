package deaguiar.daniel.ckl_challenge;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by daniel on 11/06/15.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_ARTICLE  = "article";
    public static final String COLUMN_ID      = "_id";
    public static final String COLUMN_TITLE   = "title";
    public static final String COLUMN_DATE    = "date";
    public static final String COLUMN_WEBSITE = "website";
    public static final String COLUMN_IMAGE   = "image";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_AUTHORS = "authors";
    public static final String COLUMN_IMAGE_DATA = "image_data";

    private static final String CREATE_TABLE_ARTICLE = "create table " +
            TABLE_ARTICLE + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TITLE + " TEXT NOT NULL, " +
            COLUMN_DATE + " TEXT NOT NULL, " +
            COLUMN_WEBSITE + " TEXT, " +
            COLUMN_IMAGE + " TEXT, " +
            COLUMN_CONTENT + " TEXT, " +
            COLUMN_AUTHORS + " TEXT, " +
            COLUMN_IMAGE_DATA + " BLOB);";

    private static final String DATABASE_NAME = "cheesecake.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_ARTICLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLE);
        onCreate(db);
    }
}
