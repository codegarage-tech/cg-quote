package com.reversecoder.quote.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.reversecoder.quote.R;
import com.reversecoder.quote.model.database.LitePalDataBuilder;
import com.reversecoder.quote.util.AppUtils;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class AuthorViewHolder extends BaseViewHolder<LitePalDataBuilder> {

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
    public void setData(final LitePalDataBuilder data) {
        tvAuthorName.setText(data.getLitePalAuthor().getAuthorName());
        tvAuthorSubtitle.setText(data.getLitePalAuthor().getOccupation());
        viewColorBar.setBackgroundColor(AppUtils.getRandomPastelColor());

        Glide
                .with(getContext())
                .load((data.getLitePalAuthor().getProfileImage() != -1) ? data.getLitePalAuthor().getProfileImage() : R.drawable.avatar_male)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                .apply(new RequestOptions().circleCropTransform())
                .into(ivPersonThumbnail);
    }
}
