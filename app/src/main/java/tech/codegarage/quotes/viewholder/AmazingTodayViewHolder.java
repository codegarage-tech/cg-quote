package tech.codegarage.quotes.viewholder;

import android.view.ViewGroup;

import com.cleveroad.cyclemenuwidget.CycleMenuWidget;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import tech.codegarage.quotes.R;
import tech.codegarage.quotes.model.database.QuoteOfTheDay;
import tech.codegarage.quotes.view.CanaroTextView;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class AmazingTodayViewHolder extends BaseViewHolder<QuoteOfTheDay> {

    CycleMenuWidget cycleMenuWidget;
    CanaroTextView tvQuote, tvAuthor;

    public AmazingTodayViewHolder(ViewGroup parent) {
        super(parent, R.layout.recyclerview_item_amazing_today);

        tvQuote = (CanaroTextView) $(R.id.tv_quote);
        tvAuthor = (CanaroTextView) $(R.id.tv_author);
        cycleMenuWidget = (CycleMenuWidget) $(R.id.itemCycleMenuWidget);
        cycleMenuWidget.setMenuRes(R.menu.menu_cycle);
    }

    @Override
    public void setData(final QuoteOfTheDay data) {

        tvQuote.setText("\"" + data.getLitePalQuoteBuilder().getLitePalQuote().getQuoteDescription() + "\"");
        tvAuthor.setText("--- " + data.getLitePalAuthor().getAuthorName());
    }
}