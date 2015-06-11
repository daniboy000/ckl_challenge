package deaguiar.daniel.ckl_challenge;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by daniel on 10/06/15.
 */
public class ArticleList {

    private static ArticleList sArticleList;
    private Context mAppContext;
    private ArrayList<Article> mArticleList;

    private ArticleList(Context appContext) {
        mAppContext = appContext;
        mArticleList = new ArrayList<Article>();
    }

    public static ArticleList getInstance(Context c) {
        if (sArticleList == null) {
            sArticleList = new ArticleList(c.getApplicationContext());
        }
        return sArticleList;
    }

    public ArrayList<Article> getArticleList() {
        return mArticleList;
    }

    public void addArticle(Article article) {
        mArticleList.add(article);
    }

    public Article getArticle(String title) {
        for (Article article : mArticleList) {
            if (article.getTitle().equals(title)) {
                return article;
            }
        }
        return null;
    }
}
