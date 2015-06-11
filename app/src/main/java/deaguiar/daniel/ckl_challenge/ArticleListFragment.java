package deaguiar.daniel.ckl_challenge;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

        if (mArticleList.size() > 0) {
            ArticleAdapter adapter = new ArticleAdapter(mArticleList);
            adapter.sort(new CompareTitle());

            setListAdapter(adapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mArticleList.size() > 0) {
            ((ArticleAdapter) getListAdapter()).notifyDataSetChanged();
        }
    }

    public void onListItemClick(ListView l, View v, int position, long id) {

        Article article = ((ArticleAdapter)getListAdapter()).getItem(position);

        // Start ArticleActivity
        Intent i = new Intent(getActivity(), ArticleActivity.class);
        i.putExtra(ArticleFragment.EXTRA_ID, article.getId());
        startActivity(i);
    }

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

    private class ArticleAdapter extends ArrayAdapter<Article> {

        public ArticleAdapter(ArrayList<Article> articles) {
            super(getActivity(), 0, articles);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // If we weren't given a view, inflate one
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_article, null);
            }

            Article article = getItem(position);

            TextView titleTextView = (TextView)convertView.findViewById(R.id.article_list_item_title);
            titleTextView.setText(article.getTitle());

            TextView authorTextView = (TextView)convertView.findViewById(R.id.article_list_item_author);
            authorTextView.setText(article.getAuthors());

            return convertView;
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
