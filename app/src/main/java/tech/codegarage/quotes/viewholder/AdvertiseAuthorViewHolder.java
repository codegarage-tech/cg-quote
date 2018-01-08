package tech.codegarage.quotes.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import tech.codegarage.quotes.R;
import tech.codegarage.quotes.model.database.LitePalDataBuilder;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class AdvertiseAuthorViewHolder extends BaseViewHolder<LitePalDataBuilder> {

    public TextView txtAdvertiseName;

    public AdvertiseAuthorViewHolder(ViewGroup parent) {
        super(parent, R.layout.recyclerview_item_advertise_author);

        txtAdvertiseName = $(R.id.tv_author_name);
    }

    @Override
    public void setData(final LitePalDataBuilder data) {
//        txtAdvertiseName.setText(data.getAuthorName());
    }
}
