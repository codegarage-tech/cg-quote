package br.com.stickyindex;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import br.com.stickyindex.adapter.IndexAdapter;
import br.com.stickyindex.layout.IndexLayoutManager;
import br.com.stickyindex.listener.IndexScrollListener;
import br.com.stickyindex.model.Subscriber;


/**
 * Created by edgar on 6/4/15.
 */
public class StickyIndex extends RelativeLayout {
    private RecyclerView indexList;
    private IndexScrollListener scrollListener;

    private IndexAdapter adapter;
    private IndexLayoutManager stickyIndex;

    // Constructors ________________________________________________________________________________
    public StickyIndex(Context context) {
        super(context);
        initialize(context, null);
    }

    public StickyIndex(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public StickyIndex(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    public StickyIndex(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.sticky_index, this, true);

        // Creates RecyclerView and its layout
        this.indexList = (RecyclerView) this.findViewById(R.id.index_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);

        this.indexList.setLayoutManager(linearLayoutManager);
        this.indexList.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

        char[] dataSet = {};

        IndexAdapter.RowStyle styles = getRowStyle(context, attrs);

        // Change sticky width
        View stickyWrapper = (LinearLayout) this.findViewById(R.id.sticky_index_wrapper);
        android.view.ViewGroup.LayoutParams params = stickyWrapper.getLayoutParams();
        params.width = styles.getStickyWidth().intValue();
        stickyWrapper.setLayoutParams(params);
        this.invalidate();

        this.adapter = new IndexAdapter(dataSet, styles);
        this.indexList.setAdapter(adapter);

        this.scrollListener = new IndexScrollListener();
        this.scrollListener.setOnScrollListener(indexList);

        this.stickyIndex = new IndexLayoutManager(this);
        this.stickyIndex.setIndexList(indexList);
        scrollListener.register(stickyIndex);

        // Call after stickyindex has been initialized
        setStickyIndexStyle(styles);
    }

    // UTIL ________________________________________________________________________________________
    private IndexAdapter.RowStyle getRowStyle(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StickyIndex);

            float textSize = typedArray.getDimension(R.styleable.StickyIndex_android_textSize, -1);
            int textColor = typedArray.getColor(R.styleable.StickyIndex_android_textColor, -1);

            textSize = (textSize != -1) ? textSize : 26;
            textColor = (textColor != -1) ? textColor : getResources().getColor(R.color.index_text_color);

            return new IndexAdapter.RowStyle(
                    typedArray.getDimension(R.styleable.StickyIndex_rowHeight, -1f),
                    typedArray.getDimension(R.styleable.StickyIndex_stickyWidth, -1f),
                    textColor,
                    textSize,
                    typedArray.getInt(R.styleable.StickyIndex_android_textStyle, -1)
            );

        } else {
            return null;
        }
    }

    private void setStickyIndexStyle(IndexAdapter.RowStyle styles) {
        if (styles.getRowHeigh() != -1) {
            LinearLayout stickyIndexWrapper = (LinearLayout) this.findViewById(R.id.sticky_index_wrapper);
            android.view.ViewGroup.LayoutParams params = stickyIndexWrapper.getLayoutParams();
            params.height = styles.getRowHeigh().intValue();
            stickyIndexWrapper.setLayoutParams(params);
        }

        if (styles.getTextSize() != -1) {
            stickyIndex.getStickyIndex().setTextSize(TypedValue.COMPLEX_UNIT_PX, styles.getTextSize());
        }

        if (styles.getTextColor() != null) {
            stickyIndex.getStickyIndex().setTextColor(styles.getTextColor());
        }

        if (styles.getTextStyle() != -1) {
            stickyIndex.getStickyIndex().setTypeface(null, styles.getTextStyle());
        }
    }

    // Interface that provides a way for registering/unregister new subscribers to the onScrollEvent
    public void subscribeForScrollListener(Subscriber nexSubscriber) {
        scrollListener.register(nexSubscriber);
    }

    public void removeScrollListenerSubscription(Subscriber oldSubscriber) {
        scrollListener.unregister(oldSubscriber);
    }

    // GETTERs AND SETTERs__________________________________________________________________________
    public void setDataSet(char[] dataSet) {
        this.adapter.setDataSet(dataSet);
        this.adapter.notifyDataSetChanged();
    }

    public char[] getDataSet() {
        return adapter.getDataSet();
    }

    public IndexLayoutManager getStickyIndex() {
        return stickyIndex;
    }

    public void setOnScrollListener(RecyclerView rl) {
        scrollListener.setOnScrollListener(rl);
    }
}
