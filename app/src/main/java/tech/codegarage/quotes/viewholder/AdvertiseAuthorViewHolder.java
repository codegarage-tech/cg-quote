package tech.codegarage.quotes.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import tech.codegarage.quotes.R;
import tech.codegarage.quotes.model.AppDataBuilder;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class AdvertiseAuthorViewHolder extends BaseViewHolder<AppDataBuilder> {

    public TextView txtAdvertiseName;

    public AdvertiseAuthorViewHolder(ViewGroup parent) {
        super(parent, R.layout.recyclerview_item_advertise_author);

        txtAdvertiseName = $(R.id.tv_author_name);
    }

    @Override
    public void setData(final AppDataBuilder data) {
//        txtAdvertiseName.setText(data.getAuthorName());
    }
}
