package deaguiar.daniel.ckl_challenge;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ArticleFragment extends Fragment {

    public static final String EXTRA_ID = "_id";

    private Article mArticle;
    private TextView mTitleTextView;
    private TextView mAuthorTextView;
    private ImageView mImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int id = getArguments().getInt(EXTRA_ID);
        mArticle = ArticleList.getInstance(getActivity()).getArticle(id);
    }

    public static ArticleFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_ID, id);

        ArticleFragment fragment = new ArticleFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_article, parent, false);

        mTitleTextView = (TextView) v.findViewById(R.id.fragment_article_title);
        mTitleTextView.setText(mArticle.getTitle());

        mAuthorTextView = (TextView) v.findViewById(R.id.fragment_article_author);
        mAuthorTextView.setText(mArticle.getAuthors());

        if (mArticle.getImageAsBitmap() != null) {
            mImageView = (ImageView) v.findViewById(R.id.fragment_article_image);
            mImageView.setImageBitmap(mArticle.getImageAsBitmap());
        }

        return v;
    }

}
