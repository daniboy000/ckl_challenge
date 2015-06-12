package deaguiar.daniel.ckl_challenge;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.Display;
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
    private ImageView mImageView;
    private TextView mTitleTextView;

    private TextView mDateTextView;
    private TextView mAuthorTextView;
    private TextView mWebsiteTextView;

    private TextView mContentTextView;

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

        if (mArticle.getImageAsBitmap() != null) {
            mImageView = (ImageView) v.findViewById(R.id.fragment_article_image);
            Bitmap bitmap = mArticle.getImageAsBitmap();

            int dim = getImageDimension(getActivity());
            Log.i("Article", "DIMENSION: " + dim);

            mImageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, dim, dim, false));
        }

        mTitleTextView = (TextView) v.findViewById(R.id.fragment_article_title);
        mTitleTextView.setText(mArticle.getTitle());

        mAuthorTextView = (TextView) v.findViewById(R.id.fragment_article_author);
        mAuthorTextView.setText("author: " + mArticle.getAuthors());

        mWebsiteTextView = (TextView) v.findViewById(R.id.fragment_article_website);
        mWebsiteTextView.setText("site: " + mArticle.getWebsite());

        mDateTextView = (TextView) v.findViewById(R.id.fragment_article_date);
        mDateTextView.setText(mArticle.getDateAsString());

        mContentTextView = (TextView) v.findViewById(R.id.fragment_article_content);
        mContentTextView.setText(mArticle.getContent().toString());

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

    private int getImageDimension(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        int destWidth = display.getWidth();
        int destHeight = display.getHeight();

        int newSize = 1;
        if (destWidth < destHeight) {
            newSize = Math.round(destWidth / 3);
        }
        else {
            newSize = Math.round(destHeight / 3);
        }

        return newSize;
    }
}
