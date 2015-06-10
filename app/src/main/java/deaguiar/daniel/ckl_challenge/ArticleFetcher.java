package deaguiar.daniel.ckl_challenge;

import android.content.Context;
import android.net.Uri;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by daniel on 09/06/15.
 */
public class ArticleFetcher {

    private Context mAppContext;

    public static final String TAG = "ArticleFetcher";

    private static final String ENDPOINT = "http://www.ckl.io/challenge/";
    private static final String JSON_TITLE = "title";
    private static final String JSON_DATE = "date";
    private static final String JSON_WEBSITE = "website";
    private static final String JSON_IMAGE = "image";
    private static final String JSON_CONTENT = "content";
    private static final String JSON_AUTHORS = "authors";

    public ArticleFetcher(Context context) {
        mAppContext = context;
    }

    byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrl(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public void fetchItems() {
        ArticleList articleList = ArticleList.getInstance(mAppContext);

        try {
            String url = Uri.parse(ENDPOINT).buildUpon().build().toString();
            String jsonString = getUrl(url);

            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String title   = "";
                String date    = "";
                String website = "";
                String image   = "";
                String content = "";
                String authors = "";

                if (!jsonObject.isNull(JSON_TITLE))
                    title = jsonObject.getString(JSON_TITLE);

                if (!jsonObject.isNull(JSON_DATE))
                    date = jsonObject.getString(JSON_DATE);

                if (!jsonObject.isNull(JSON_WEBSITE))
                    website = jsonObject.getString(JSON_WEBSITE);

                if (!jsonObject.isNull(JSON_IMAGE))
                    image = jsonObject.getString(JSON_IMAGE);

                if (!jsonObject.isNull(JSON_CONTENT))
                    content = jsonObject.getString(JSON_CONTENT);

                if (!jsonObject.isNull(JSON_AUTHORS))
                    authors = jsonObject.getString(JSON_AUTHORS);

                Log.i(TAG, "JSON OBJECT title: " + title);
                Log.i(TAG, "JSON OBJECT Date: " + date.toString());
                Log.i(TAG, "JSON OBJECT website: " + website);
                Log.i(TAG, "JSON OBJECT image: " + image.toString());
                Log.i(TAG, "JSON OBJECT content: " + content);
                Log.i(TAG, "JSON OBJECT authors: " + authors);

                Article article = new Article();
                article.setTitle(title);
                article.setDate(date);
                article.setWebsite(website);
                article.setImage(image);
                article.setContent(content);
                article.setAuthors(authors);

                articleList.addArticle(article);
            }
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException jse) {
            Log.e(TAG, "Failed to parse JSON", jse);
        }

    }

}
