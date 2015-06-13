package deaguiar.daniel.ckl_challenge;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * ImageFragment
 * Class that represents an instance of type ImageFragment
 *
 * @author Daniel Besen de Aguiar
 */
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
