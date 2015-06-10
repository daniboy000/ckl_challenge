package deaguiar.daniel.ckl_challenge;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends ActionBarActivity {

    public static final String TAG = "MainActivity";
    private Button articleListButton;
    private Button fetchArticleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        articleListButton = (Button) findViewById(R.id.main_article_list);
        articleListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ArticleListActivity.class);
                startActivity(i);
            }
        });

        fetchArticleButton = (Button) findViewById(R.id.main_fetch_articles);
        fetchArticleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchArticlesClass().execute();
            }
        });
    }

    private class FetchArticlesClass extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            new ArticleFetcher(getApplicationContext()).fetchItems();
            return null;
        }
    }

}
