package deaguiar.daniel.ckl_challenge;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ArticleFetcher
 * Class that represents an instance of type ArticleFetcher
 * This class is responsible to connect with the server and fetch the articles.
 *
 * @author Daniel Besen de Aguiar
 */
public class ArticleFetcher {

    private Context mAppContext;

    public static final String TAG = "ArticleFetcher";

    private static final String ENDPOINT     = "http://www.ckl.io/challenge/";
    private static final String JSON_TITLE   = "title";
    private static final String JSON_DATE    = "date";
    private static final String JSON_WEBSITE = "website";
    private static final String JSON_IMAGE   = "image";
    private static final String JSON_CONTENT = "content";
    private static final String JSON_AUTHORS = "authors";

    public ArticleFetcher(Context context) {
        mAppContext = context;
    }

    /**
     * This method tries to connect with the urlSpec and download the data.
     * @param urlSpec an absolute URL
     * @return byte[] the data at urlSpec
     * @throws IOException
     */
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

    /**
     * Converts the response of getUrlBytes(String urlSpec) into String
     * @param urlSpec an absolute URL
     * @return String the data at urlSpec
     * @throws IOException
     */
    public String getUrl(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    /**
     * This method parse all the content in ENDPOINT, create an instance of Article for
     * for each JSON object founded and save it on the Database
     */
    public void fetchItems() {
        try {
            ArticleList articleList = ArticleList.getInstance(mAppContext);

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

                byte[] imageData = null;
                if (!jsonObject.isNull(JSON_IMAGE)) {
                    image = jsonObject.getString(JSON_IMAGE);

                    // Get image from url
                    imageData = getUrlBytes(image);
                }

                if (!jsonObject.isNull(JSON_CONTENT))
                    content = jsonObject.getString(JSON_CONTENT);

                if (!jsonObject.isNull(JSON_AUTHORS))
                    authors = jsonObject.getString(JSON_AUTHORS);

                articleList.insertArticle(title, date, website, image, content, authors, imageData, false);
            }
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException jse) {
            Log.e(TAG, "Failed to parse JSON", jse);
        }
    }

}
