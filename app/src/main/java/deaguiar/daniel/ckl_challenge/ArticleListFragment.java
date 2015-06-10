package deaguiar.daniel.ckl_challenge;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Comparator;

public class ArticleListFragment extends ListFragment {

    private ArrayList<Article> mArticleList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    private class CompareTitle implements Comparator<Article> {

        @Override
        public int compare(Article article1, Article article2) {
            return article1.getTitle().compareTo(article2.getTitle());
        }

    }



}
