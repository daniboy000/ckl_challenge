package deaguiar.daniel.ckl_challenge;

import android.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class ArticleActivity extends SingleFragmentActivity {

    public Fragment createFragment() {
        String title = getIntent().getStringExtra(ArticleFragment.EXTRA_TITLE);

        return ArticleFragment.newInstance(title);
    }
}
