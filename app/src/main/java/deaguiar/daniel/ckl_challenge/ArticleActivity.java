package deaguiar.daniel.ckl_challenge;

import android.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class ArticleActivity extends SingleFragmentActivity
    implements ArticleFragment.Callbacks {

    public Fragment createFragment() {
        long id = getIntent().getLongExtra(ArticleFragment.EXTRA_ID, -1);

        return ArticleFragment.newInstance(id);
    }

    public void onArticleUpdated() {

    }
}
