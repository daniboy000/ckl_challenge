package deaguiar.daniel.ckl_challenge;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;

/**
 * ArticleListActivity
 * Class that represents an instance of type ArticleListActivity
 *
 * @author Daniel Besen de Aguiar
 */
public class ArticleListActivity extends SingleFragmentActivity
    implements ArticleListFragment.Callbacks, ArticleFragment.Callbacks {

    @Override
    protected Fragment createFragment() {
        return new ArticleListFragment();
    }

    /**
     * Returns the layout id depending on the screen resolution
     * @return
     */
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    /**
     * Shows the details of Article
     * @param id id of Article in the Database
     */
    public void onArticleSelected(long id) {
        // Start activity if not tablet
        if (findViewById(R.id.detailFragmentContainer) == null) {
            Intent i = new Intent(this, ArticleActivity.class);
            i.putExtra(ArticleFragment.EXTRA_ID, id);
            startActivity(i);
        }
        else { // If tablet, update ArticleFragment
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

    /**
     * Update the data in ArticleListFragment
     */
    public void onArticleUpdated() {
        FragmentManager fm = getFragmentManager();
        ArticleListFragment articleListFragment = (ArticleListFragment)fm.findFragmentById(R.id.fragmentContainer);
        articleListFragment.updateUI();
    }
}
