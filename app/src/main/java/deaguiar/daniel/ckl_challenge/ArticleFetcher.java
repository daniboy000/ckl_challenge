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

            //String jsonString = "[{\"image\": \"http://lorempixel.com/400/400/technics/1/\", \"authors\": \"Graham Spencer\", \"date\": \"05/26/2014\", \"website\": \"MacStories\", \"content\": \"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean suscipit laoreet dolor tempor posuere. Sed fermentum felis ipsum. Cras eu felis non ante tristique consectetur. Fusce volutpat at ante ac lacinia. Cras pulvinar at justo sit amet fringilla. Mauris sit amet feugiat justo, non pretium justo. Nam fermentum erat odio, eget tempor magna rhoncus vitae. Nam pharetra, massa id commodo posuere, velit ante ultricies quam, non consectetur libero lectus a urna. Nunc ut euismod nulla. Maecenas mattis accumsan ornare. Suspendisse potenti. Cras a interdum turpis, at pharetra nulla.\", \"title\": \"Apple Debuts Two New 'Your Verse' iPad Adverts\"}, {\"image\": \"http://lorempixel.com/400/400/technics/2/\", \"authors\": \"Fran Bellamy\", \"date\": \"05/27/2014\", \"website\": \"Masslive\", \"content\": \"Interdum et malesuada fames ac ante ipsum primis in faucibus. Nam ut eleifend ligula. Aliquam mattis dui nec est semper, vel dignissim enim pulvinar. In pharetra vel neque sodales finibus. Cras euismod lorem mi, ac consectetur ipsum auctor quis. Vivamus condimentum placerat augue rhoncus volutpat. Phasellus porta dolor eu tellus efficitur vehicula ornare vitae diam. Sed est est, luctus sit amet lobortis vitae, sagittis et sapien. Sed et tellus libero.\", \"title\": \"Restaurant review: Gypsy Apple Bistro in Shelburne Falls one of the region's best\"}, {\"image\": \"http://lorempixel.com/400/400/technics/3/\", \"authors\": \"Unknown\", \"date\": \"05/28/2014\", \"website\": \"International Business Times - Australia\", \"content\": \"Donec pulvinar magna in fringilla mollis. Sed dui purus, posuere non tristique quis, ullamcorper sollicitudin diam. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Pellentesque id congue urna. Maecenas nec dui hendrerit, vehicula est ut, congue dolor. Quisque nisi massa, tincidunt a arcu at, vulputate efficitur libero. Mauris rhoncus orci ac tempus dapibus.\", \"title\": \"A Tiny Gadget That Charges iPhone And Android Twice as Fast\"}, {\"authors\": \"Cody Lee\", \"content\": \"Donec nisi dolor, laoreet quis odio in, gravida rhoncus lacus. Suspendisse rutrum lorem sed ante rhoncus, et fermentum nunc consequat. Sed porttitor massa sollicitudin vehicula tempor. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque euismod mattis est vel vehicula. Cras varius, odio ut tempor lacinia, nibh tortor placerat purus, eget lobortis justo nulla a lorem. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum imperdiet finibus orci sit amet suscipit. Morbi vulputate id erat mollis rhoncus. Duis non metus metus. Curabitur justo est, posuere ut efficitur vitae, gravida et nulla. Nam aliquam, libero et vulputate facilisis, metus tortor tristique urna, eu eleifend tortor eros sed libero. Etiam libero est, pellentesque sit amet ante ac, luctus viverra lorem. Vestibulum at varius leo, eget elementum augue.\", \"website\": \"iDownload Blog\", \"title\": \"ZAGG taking up to 50% off everything site-wide for Memorial Day\", \"date\": \"06/30/2014\"}, {\"image\": \"http://lorempixel.com/400/400/technics/4/\", \"authors\": \"Dusan Belic\", \"date\": \"05/26/2014\", \"website\": \"Into Mobile\", \"content\": \"Vestibulum non gravida orci. Sed dapibus, leo rhoncus condimentum placerat, dolor enim accumsan ipsum, in faucibus odio risus eu metus. Aliquam sagittis ut neque vel sagittis. Nullam commodo lacus sit amet ex maximus convallis. Donec eget tempus ex, non dignissim nunc. Curabitur finibus dui risus. Morbi tempus tristique laoreet. Curabitur ac risus elit. Curabitur eu quam sit amet dolor mattis aliquet nec eu lectus. Suspendisse molestie dapibus mollis. Cras vel turpis eget enim varius porta. Maecenas justo massa, tincidunt at dignissim vel, consequat sed nulla. Aenean vel suscipit odio. Suspendisse libero felis, pharetra et risus nec, tincidunt efficitur magna. Proin a consectetur sapien. Suspendisse at vulputate ipsum, commodo varius tellus.\", \"title\": \"Sony Xperia Z2 Tablet heading to Verizon Wireless\"}, {\"image\": \"http://lorempixel.com/400/400/technics/5/\", \"authors\": \"Harmeet Shah Singh\", \"date\": \"04/02/2014\", \"website\": \"CNN\", \"content\": \"Proin faucibus ornare libero vitae auctor. Quisque luctus velit eget elit iaculis posuere. Donec tempus molestie ullamcorper. Nam dui nisl, porttitor quis gravida at, congue et nisl. Sed semper turpis in viverra mattis. Curabitur rhoncus sem eu eros ultrices, non euismod augue sollicitudin. Sed at molestie elit, vel malesuada nibh. Nam molestie et libero eu feugiat. Nunc euismod molestie finibus. Duis odio nibh, faucibus eget egestas eu, pharetra in ligula. Etiam ac semper mauris. Fusce aliquet quam non mauris interdum ornare. Sed mattis non ligula nec lobortis.\", \"title\": \"India inauguration: South Asian leaders unite around Narendra Modi\"}, {\"image\": null, \"authors\": \"Rene Ritchie\", \"date\": \"05/26/2014\", \"website\": \"iMore\", \"content\": \"Duis fermentum pretium quam nec sodales. Praesent efficitur rutrum sem, ultrices tincidunt arcu blandit sed. Pellentesque iaculis tellus lectus, non malesuada purus lobortis in. Praesent nibh ligula, elementum id nisi at, vestibulum ultricies odio. Aenean hendrerit fringilla molestie. Ut molestie odio quis blandit dictum. Donec congue erat vel ante ultricies malesuada. In luctus elit ac sapien eleifend, sit amet commodo metus auctor. Nam ac ipsum augue.\", \"title\": \"Regarding ARM-based Macs\"}, {\"image\": \"http://lorempixel.com/400/400/technics/6/\", \"authors\": \"Dave Schumaker\", \"date\": \"01/01/2014\", \"website\": \"Engadget\", \"content\": \"ras aliquam mauris nec sodales tincidunt. Ut luctus vulputate odio feugiat aliquam. Suspendisse non lectus nulla. Fusce rhoncus rutrum sagittis. Nulla urna metus, sodales eget lacus vitae, tristique sagittis turpis. Suspendisse pretium feugiat nulla in porta. Morbi lobortis a dolor vitae facilisis. Morbi fermentum a magna at euismod. Morbi nisi turpis, laoreet sed condimentum non, sagittis id arcu. Morbi malesuada augue eu ex pulvinar ultrices. Proin at felis eget lectus euismod finibus pretium eget velit. Morbi eget quam mi.\", \"title\": \"Weekends with Engadget: Surface Pro 3 review, Samsung VR headsets and more!\"}]";

            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Article article = new Article();

                if (!jsonObject.isNull(JSON_TITLE))
                    article.setTitle(jsonObject.getString(JSON_TITLE));
                else
                    article.setTitle("");

                if (!jsonObject.isNull(JSON_DATE))
                    article.setDate(jsonObject.getString(JSON_DATE));
                else {
                    article.setDate("");
                }

                if (!jsonObject.isNull(JSON_WEBSITE))
                    article.setWebsite(jsonObject.getString(JSON_WEBSITE));
                else
                    article.setWebsite("");

                if (!jsonObject.isNull(JSON_IMAGE))
                    article.setImage(jsonObject.getString(JSON_IMAGE));
                else
                    article.setImage("");

                if (!jsonObject.isNull(JSON_CONTENT))
                    article.setContent(jsonObject.getString(JSON_CONTENT));
                else
                    article.setContent("");

                if (!jsonObject.isNull(JSON_AUTHORS))
                    article.setAuthors(jsonObject.getString(JSON_AUTHORS));
                else
                    article.setAuthors("");

                articleList.addArticle(article);
            }
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException jse) {
            Log.e(TAG, "Failed to parse JSON", jse);
        }

    }

}
