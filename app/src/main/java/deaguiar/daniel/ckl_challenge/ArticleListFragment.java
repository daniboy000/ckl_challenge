package deaguiar.daniel.ckl_challenge;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.opengl.EGLExt;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;

public class ArticleListFragment extends ListFragment {

    private ListView mListView;
    ArrayList<Article> mArticleList;
    private ArticleAdapter mAdapter;

    public static final String TAG = "ArticleListActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.main_article_list_label);

        mArticleList = ArticleList.getInstance(getActivity()).getArticleList();
        mAdapter = new ArticleAdapter(mArticleList);

        setListAdapter(mAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");

        View v = inflater.inflate(R.layout.fragment_article_list, parent, false);

        mListView = (ListView) v.findViewById(android.R.id.list);
        mListView.setEmptyView(v.findViewById(android.R.id.empty));

        return v;
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume ArticleListFragment");
        super.onResume();

        mArticleList = ArticleList.getInstance(getActivity()).getArticleList();

        mAdapter.clear();
        mAdapter.changeData(mArticleList);

        mAdapter.notifyDataSetChanged();
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
            case R.id.fetch_articles:
                new FetchArticlesClass().execute();
                return true;
            case R.id.sort_title:
                mAdapter.sort(new CompareTitle());
                mAdapter.notifyDataSetChanged();
                return true;
            case R.id.sort_authors:
                mAdapter.sort(new CompareAuthors());
                mAdapter.notifyDataSetChanged();
                return true;
            case R.id.sort_websites:
                mAdapter.sort(new CompareWebsite());
                mAdapter.notifyDataSetChanged();
                return true;
            case R.id.sort_dates:
                mAdapter.sort(new CompareDates());
                mAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class ArticleAdapter extends ArrayAdapter<Article> {

        public ArticleAdapter(ArrayList<Article> articles) {
            super(getActivity(), 0, articles);

            sort(new CompareTitle());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // If we weren't given a view, inflate one
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_article, null);
            }

            final Article article = getItem(position);

            ImageView imageView = (ImageView) convertView.findViewById(R.id.thumbnail);
            imageView.setImageBitmap(article.getImageAsBitmap());

            TextView titleTextView = (TextView)convertView.findViewById(R.id.article_list_item_title);
            titleTextView.setText(article.getTitle());

            TextView authorTextView = (TextView)convertView.findViewById(R.id.article_list_item_author);
            authorTextView.setText(article.getAuthors());

            TextView dateTextView = (TextView)convertView.findViewById(R.id.article_list_item_date);
            dateTextView.setText(article.getDateAsString());

            CheckBox checkBox = (CheckBox)convertView.findViewById(R.id.article_list_item_readed);
            checkBox.setChecked(article.isReaded());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // set the crime's solved property
                    article.setReaded(isChecked);
                    ArticleList.getInstance(getActivity()).update(article);
                }
            });

            return convertView;
        }

        public void changeData(ArrayList<Article> articles) {
            for (Article article : articles) {
                add(article);
            }
            sort(new CompareTitle());
            notifyDataSetChanged();
        }
    }

    private class FetchArticlesClass extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Fetching Articles. Please Wait.");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            new ArticleFetcher(getActivity()).fetchItems();
            return null;
        }
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            ArrayList<Article> articles = ArticleList.getInstance(getActivity()).getArticleList();
            ArticleListFragment.this.mAdapter.clear();
            ArticleListFragment.this.mAdapter.changeData(articles);
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
