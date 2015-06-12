package deaguiar.daniel.ckl_challenge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by daniel on 10/06/15.
 */
public class ArticleList {

    private static ArticleList sArticleList;
    private Context mAppContext;
    private SQLiteDatabase mDataBase;
    private SQLiteHelper mDBHelper;

    private String[] mAllCollumns = { SQLiteHelper.COLUMN_ID, SQLiteHelper.COLUMN_TITLE,
            SQLiteHelper.COLUMN_DATE, SQLiteHelper.COLUMN_WEBSITE, SQLiteHelper.COLUMN_IMAGE,
            SQLiteHelper.COLUMN_CONTENT, SQLiteHelper.COLUMN_AUTHORS, SQLiteHelper.COLUMN_IMAGE_DATA,
            SQLiteHelper.COLUMN_READED };

    private ArticleList(Context appContext) {
        mAppContext = appContext;
        mDBHelper = new SQLiteHelper(appContext);
    }

    public static ArticleList getInstance(Context c) {
        if (sArticleList == null) {
            sArticleList = new ArticleList(c.getApplicationContext());
        }

        try {
            sArticleList.open();
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }

        return sArticleList;
    }

    public void open() throws SQLException {
        mDataBase = mDBHelper.getWritableDatabase();
    }

    public void close() {
        mDBHelper.close();
    }

    public ArrayList<Article> getArticleList() {
        ArrayList<Article> articleList = new ArrayList<Article>();

        Cursor cursor = mDataBase.query(SQLiteHelper.TABLE_ARTICLE, mAllCollumns,
                null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Article article = cursorToArticle(cursor);
                articleList.add(article);
                cursor.moveToNext();
            }

            cursor.close();
            return articleList;
        }
        return null;
    }

    public void insertArticle(String title, String date, String website,
                              String image, String content, String authors,
                              byte[] imageData, boolean readed) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_TITLE, title);
        values.put(SQLiteHelper.COLUMN_DATE, date);
        values.put(SQLiteHelper.COLUMN_WEBSITE, website);
        values.put(SQLiteHelper.COLUMN_IMAGE, image);
        values.put(SQLiteHelper.COLUMN_CONTENT, content);
        values.put(SQLiteHelper.COLUMN_AUTHORS, authors);
        values.put(SQLiteHelper.COLUMN_IMAGE_DATA, imageData);
        values.put(SQLiteHelper.COLUMN_READED, readed ? 1 : 0);

        String whereClause = SQLiteHelper.COLUMN_TITLE + " = ? AND " +
                SQLiteHelper.COLUMN_DATE + " = ? AND " + SQLiteHelper.COLUMN_WEBSITE + " = ? AND " +
                SQLiteHelper.COLUMN_IMAGE + " = ? AND " + SQLiteHelper.COLUMN_CONTENT + " = ? AND " +
                SQLiteHelper.COLUMN_AUTHORS + " = ?";

        String[] args = { title, date, website, image, content, authors };

        if (mDataBase.update(SQLiteHelper.TABLE_ARTICLE, values, whereClause, args) == 0) {
            long id = mDataBase.insert(SQLiteHelper.TABLE_ARTICLE, null, values);
        }
    }

    public void update(Article article) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_READED, article.isReaded() ? 1 : 0);

        mDataBase.update(SQLiteHelper.TABLE_ARTICLE, values, SQLiteHelper.COLUMN_ID + " = " + article.getId(), null);
    }

    public Article getArticle(long id) {
        Cursor cursor = mDataBase.query(SQLiteHelper.TABLE_ARTICLE, mAllCollumns,
                            SQLiteHelper.COLUMN_ID + " = " + id, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            return cursorToArticle(cursor);
        }

        return null;
    }

    private Article cursorToArticle(Cursor cursor) {
        Article article = new Article();
        article.setId(cursor.getLong(0));
        article.setTitle(cursor.getString(1));
        article.setDate(cursor.getString(2));
        article.setWebsite(cursor.getString(3));
        article.setImage(cursor.getString(4));
        article.setContent(cursor.getString(5));
        article.setAuthors(cursor.getString(6));
        article.setImageData(cursor.getBlob(7));
        article.setReaded(cursor.getInt(8) == 1);

        return article;
    }
}
