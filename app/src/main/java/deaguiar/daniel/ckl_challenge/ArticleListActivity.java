package deaguiar.daniel.ckl_challenge;

import android.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class ArticleListActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return new ArticleListFragment();
    }
}
