package deaguiar.daniel.ckl_challenge;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class ArticleListActivity extends SingleFragmentActivity
    implements ArticleListFragment.Callbacks, ArticleFragment.Callbacks {

    @Override
    protected Fragment createFragment() {
        return new ArticleListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    public void onArticleSelected(long id) {
        if (findViewById(R.id.detailFragmentContainer) == null) {
            Intent i = new Intent(this, ArticleActivity.class);
            i.putExtra(ArticleFragment.EXTRA_ID, id);
            startActivity(i);
        }
        else {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            Fragment oldDetail = fm.findFragmentById(R.id.detailFragmentContainer);
            Fragment newDetail = ArticleFragment.newInstance(id);

            if (oldDetail != null) {
                ft.remove(oldDetail);
            }

            ft.add(R.id.detailFragmentContainer, newDetail);
            ft.commit();
        }
    }

    public void onArticleUpdated() {
        FragmentManager fm = getFragmentManager();
        ArticleListFragment articleListFragment = (ArticleListFragment)fm.findFragmentById(R.id.fragmentContainer);
        articleListFragment.updateUI();
    }
}
