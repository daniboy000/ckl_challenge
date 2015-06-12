package deaguiar.daniel.ckl_challenge;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ArticleFragment extends Fragment {

    public static final String EXTRA_ID = "_id";

    private Article mArticle;
    private TextView mTitleTextView;
    private TextView mAuthorTextView;
    private ImageView mImageView;
    private CheckBox mReadedCheckBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.main_article_list_label);

        long id = getArguments().getLong(EXTRA_ID);
        mArticle = ArticleList.getInstance(getActivity()).getArticle(id);
    }

    public static ArticleFragment newInstance(long id) {
        Bundle args = new Bundle();
        args.putLong(EXTRA_ID, id);

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

        mReadedCheckBox = (CheckBox) v.findViewById(R.id.fragment_article_readed);
        mReadedCheckBox.setChecked(mArticle.isReaded());
        mReadedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // set the crime's solved property
                mArticle.setReaded(isChecked);
                ArticleList.getInstance(getActivity()).update(mArticle);
            }
        });

        return v;
    }

}
