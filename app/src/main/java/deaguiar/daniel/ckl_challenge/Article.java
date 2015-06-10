package deaguiar.daniel.ckl_challenge;

import java.net.URL;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by daniel on 09/06/15.
 */
public class Article {

    private String mTitle;
    private String mDate;
    private String website;
    private String mImage;
    private String mContent;
    private String mAuthors;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
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
}
