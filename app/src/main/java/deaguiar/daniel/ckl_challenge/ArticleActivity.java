package deaguiar.daniel.ckl_challenge;

import android.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class ArticleActivity extends SingleFragmentActivity {

    public Fragment createFragment() {
        int id = getIntent().getIntExtra(ArticleFragment.EXTRA_ID, -1);

        return ArticleFragment.newInstance(id);
    }
}
