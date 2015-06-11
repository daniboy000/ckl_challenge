package deaguiar.daniel.ckl_challenge;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ArticleFragment extends Fragment {

    public static final String EXTRA_TITLE = "title";

    private Article mArticle;
    private TextView mTitleTextView;
    private TextView mAuthorTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String title = getActivity().getIntent().getStringExtra(EXTRA_TITLE);
        mArticle = ArticleList.getInstance(getActivity()).getArticle(title);
    }

    public static ArticleFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString(EXTRA_TITLE, title);

        ArticleFragment fragment = new ArticleFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_article, parent, false);

        mTitleTextView = (TextView)v.findViewById(R.id.fragment_article_title);
        mTitleTextView.setText(mArticle.getTitle());

        mAuthorTextView = (TextView)v.findViewById(R.id.fragment_article_author);
        mAuthorTextView.setText(mArticle.getAuthors());

        return v;
    }

}
