package tech.codegarage.quotes.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.security.InvalidParameterException;

import tech.codegarage.quotes.model.database.QuoteOfTheDay;
import tech.codegarage.quotes.viewholder.AmazingTodayViewHolder;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class CycleMenuAdapter extends RecyclerArrayAdapter<QuoteOfTheDay> {

    public static final int TYPE_MENU_ITEM = 2;

    public CycleMenuAdapter(Context context) {
        super(context);
    }

    @Override
    public int getViewType(int position) {
        return TYPE_MENU_ITEM;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_MENU_ITEM:
                return new AmazingTodayViewHolder(parent);
            default:
                throw new InvalidParameterException();
        }
    }
}