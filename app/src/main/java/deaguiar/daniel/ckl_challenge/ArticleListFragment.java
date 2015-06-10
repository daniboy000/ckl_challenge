package deaguiar.daniel.ckl_challenge;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Comparator;

public class ArticleListFragment extends ListFragment {

    private ArrayList<Article> mArticleList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        getActivity().setTitle("TITLE");

        mArticleList = ArticleList.getInstance(getActivity()).getArticleList();

        ArrayAdapter<Article> adapter = new ArrayAdapter<Article>(getActivity(),
                                                          android.R.layout.simple_list_item_1,
                                                          mArticleList);

        adapter.sort(new CompareTitle());

        setListAdapter(adapter);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_article_list, container, false);
//    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_list_article, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_title:
                ((ArrayAdapter<Article>)getListAdapter()).sort(new CompareTitle());
                ((ArrayAdapter<Article>)getListAdapter()).notifyDataSetChanged();
                return true;
            case R.id.sort_authors:
                ((ArrayAdapter<Article>)getListAdapter()).sort(new CompareAuthors());
                ((ArrayAdapter<Article>)getListAdapter()).notifyDataSetChanged();
                return true;
            case R.id.sort_websites:
                ((ArrayAdapter<Article>)getListAdapter()).sort(new CompareWebsite());
                ((ArrayAdapter<Article>)getListAdapter()).notifyDataSetChanged();
                return true;
            case R.id.sort_dates:
                ((ArrayAdapter<Article>)getListAdapter()).sort(new CompareDates());
                ((ArrayAdapter<Article>)getListAdapter()).notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class CompareTitle implements Comparator<Article> {

        @Override
        public int compare(Article article1, Article article2) {
            return article1.getTitle().compareTo(article2.getTitle());
        }
    }

    private class CompareAuthors implements Comparator<Article> {

        @Override
        public int compare(Article article1, Article article2) {
            return article1.getAuthors().compareTo(article2.getAuthors());
        }
    }

    private class CompareWebsite implements Comparator<Article> {

        @Override
        public int compare(Article article1, Article article2) {
            return article1.getWebsite().compareTo(article2.getWebsite());
        }
    }

    private class CompareDates implements Comparator<Article> {

        @Override
        public int compare(Article article1, Article article2) {
            return article1.getDate().compareTo(article2.getDate());
        }
    }

}
