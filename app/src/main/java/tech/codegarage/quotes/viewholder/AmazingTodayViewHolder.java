package tech.codegarage.quotes.viewholder;

import android.view.View;
import android.view.ViewGroup;

import com.cleveroad.cyclemenuwidget.CycleMenuWidget;
import com.cleveroad.cyclemenuwidget.OnMenuItemClickListener;
import com.cleveroad.cyclemenuwidget.OnStateChangedListener;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import tech.codegarage.quotes.R;
import tech.codegarage.quotes.model.database.QuoteOfTheDay;
import tech.codegarage.quotes.util.AppUtils;
import tech.codegarage.quotes.util.ClipboardHandler;
import tech.codegarage.quotes.util.IntentManager;
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
    }

    @Override
    public void setData(final QuoteOfTheDay data) {

        tvQuote.setText("\"" + data.getLitePalQuoteBuilder().getLitePalQuote().getQuoteDescription() + "\"");
        tvAuthor.setText("--- " + data.getLitePalAuthor().getAuthorName());

        cycleMenuWidget.setMenuRes(R.menu.menu_cycle);
        cycleMenuWidget.setCurrentPosition(-1);
        cycleMenuWidget.setCurrentItemsAngleOffset(CycleMenuWidget.UNDEFINED_ANGLE_VALUE);
        cycleMenuWidget.close(false);
        cycleMenuWidget.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(View view, int itemPosition) {
                switch (itemPosition) {
                    case 0:
                        IntentManager.shareToAllAvailableApps(getContext(), "", AppUtils.getSharedQuote(getContext(), data));
                        break;
                    case 1:
                        ClipboardHandler.copyToClipboard(getContext(), data.getLitePalQuoteBuilder().getLitePalQuote().getQuoteDescription());
                        break;
                    case 2:
                        break;
                }
            }

            @Override
            public void onMenuItemLongClick(View view, int itemPosition) {
            }
        });
        cycleMenuWidget.setStateChangeListener(new OnStateChangedListener() {
            @Override
            public void onStateChanged(CycleMenuWidget.STATE state) {
            }

            @Override
            public void onOpenComplete() {
            }

            @Override
            public void onCloseComplete() {
            }
        });
    }
}