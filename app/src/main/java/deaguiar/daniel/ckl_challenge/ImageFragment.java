package deaguiar.daniel.ckl_challenge;

import android.app.Activity;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageFragment extends DialogFragment {

    private ImageView mImageView;

    public static ImageFragment newInstance(long articleId) {
        Bundle args = new Bundle();

        args.putLong(ArticleFragment.EXTRA_ID, articleId);

        ImageFragment fragment = new ImageFragment();
        fragment.setArguments(args);
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

        return fragment;
    }

    public ImageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mImageView = new ImageView(getActivity());

        long id = getArguments().getLong(ArticleFragment.EXTRA_ID);
        Article article = ArticleList.getInstance(getActivity()).getArticle(id);

        mImageView.setImageBitmap(article.getImageAsBitmap());

        return mImageView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
