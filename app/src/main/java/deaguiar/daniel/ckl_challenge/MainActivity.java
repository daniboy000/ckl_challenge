package deaguiar.daniel.ckl_challenge;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

public class MainActivity extends Activity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new FetchArticlesClass().execute();
    }

    private class FetchArticlesClass extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
//            try {
//                String result = new ArticleFetcher().getUrl("http://www.google.com");
//                Log.i(TAG, "Fetched contents of URL: " + result);
//            } catch (IOException ioe) {
//                Log.e(TAG, "Failed to fethc URL: ", ioe);
//            }

            new ArticleFetcher().fetchItems();
            return null;
        }
    }

}
