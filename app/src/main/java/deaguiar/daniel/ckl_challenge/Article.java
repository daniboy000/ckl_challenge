package deaguiar.daniel.ckl_challenge;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by daniel on 09/06/15.
 */
public class Article {

    private int mId;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    private String mTitle;
    private Date mDate;
    private String website;
    private String mImage;
    private String mContent;
    private String mAuthors;
    private byte[] mImageData;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public String getDateAsString() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(mDate);
    }

    public void setDate(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            mDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getAuthors() {
        return mAuthors;
    }

    public void setAuthors(String authors) {
        mAuthors = authors;
    }

    public String toString() {
        return mTitle;
    }

    public void setImageData(byte[] imageData) {
        mImageData = imageData;
    }

    public Bitmap getImageAsBitmap() {
        if (mImageData != null) {
            final Bitmap bitmap = BitmapFactory.decodeByteArray(mImageData, 0, mImageData.length);
            return bitmap;
        }
        return null;
    }
}
