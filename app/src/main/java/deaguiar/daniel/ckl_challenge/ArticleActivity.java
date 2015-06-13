package deaguiar.daniel.ckl_challenge;

import android.app.Fragment;

/**
 * ArticleActivity
 * Class that represents an instance of type ArticleActivity
 *
 * @author Daniel Besen de Aguiar
 */
public class ArticleActivity extends SingleFragmentActivity
    implements ArticleFragment.Callbacks {

    /**
     * Creates an instance of Article fragment and add an Article id
     * as an extra.
     * @return Fragment instance of ArticleFragment
     */
    public Fragment createFragment() {
        long id = getIntent().getLongExtra(ArticleFragment.EXTRA_ID, -1);

        return ArticleFragment.newInstance(id);
    }

    /**
     * Interface method of ArticleFragment
     */
    public void onArticleUpdated() {

    }
}
