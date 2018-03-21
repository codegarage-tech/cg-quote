package tech.codegarage.quotes.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import tech.codegarage.quotes.model.AppDataBuilder;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import tech.codegarage.quotes.R;
import tech.codegarage.quotes.util.AppUtils;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class AuthorViewHolder extends BaseViewHolder<AppDataBuilder> {

    private TextView tvAuthorName;
    private TextView tvAuthorSubtitle;
    private ImageView ivPersonThumbnail;
    private View viewColorBar;

    public AuthorViewHolder(ViewGroup parent) {
        super(parent, R.layout.recyclerview_item_author);

        tvAuthorName = $(R.id.tv_author_name);
        tvAuthorSubtitle = $(R.id.tv_author_subtitle);
        ivPersonThumbnail = $(R.id.iv_author_thumbnail);
        viewColorBar = $(R.id.color_bar);
    }

    @Override
    public void setData(final AppDataBuilder data) {
        tvAuthorName.setText(data.getAuthor().getAuthorName());
        tvAuthorSubtitle.setText(data.getAuthor().getOccupation());
        viewColorBar.setBackgroundColor(AppUtils.getRandomPastelColor());

        Glide
                .with(getContext())
                .load((data.getAuthor().getProfileImage() != -1) ? data.getAuthor().getProfileImage() : R.drawable.avatar_male)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                .apply(new RequestOptions().circleCropTransform())
                .into(ivPersonThumbnail);
    }
}
