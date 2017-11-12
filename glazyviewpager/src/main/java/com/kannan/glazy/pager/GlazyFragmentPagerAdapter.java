package com.kannan.glazy.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.kannan.glazy.GlazyCard;
import com.kannan.glazy.fragments.GlazyCardFragment;

import java.util.ArrayList;

public class GlazyFragmentPagerAdapter extends AdvancedFragmentPagerAdapter {

    private int CARDS_COUNT = 0;
    private String TAG = GlazyFragmentPagerAdapter.class.getSimpleName();

    private ArrayList<GlazyCard> cards;
    Context context;

    //For updating view pager
    private final ArrayList<Integer> mItems;

    public GlazyFragmentPagerAdapter(FragmentManager fm, Context ctx) {
        super(fm);
        context = ctx;
        cards = new ArrayList<>();

        //For updating view pager
        mItems = new ArrayList<>();
    }

    public void setData(ArrayList<GlazyCard> data) {
        cards.clear();
        cards.addAll(data);

        //For updating view pager
        mItems.clear();
        for (int i = 0; i < cards.size(); i++) {
            mItems.add(i);
        }

        updateCount();
    }

    public ArrayList<GlazyCard> getData() {
        return cards;
    }

    public GlazyCard getCardItem(String title) {
        if (cards.size() > 0) {
            for (int i = 0; i < cards.size(); i++) {
                if (cards.get(i).getTitle().equalsIgnoreCase(title)) {
                    return cards.get(i);
                }
            }
        }
        return null;
    }

    public GlazyCard getCardItem(int position) {
        if (cards.size() > 0) {
            return cards.get(position);
        }
        return null;
    }

    public int getCardItemPosition(String title) {
        if (cards.size() > 0) {
            for (int i = 0; i < cards.size(); i++) {
                if (cards.get(i).getTitle().equalsIgnoreCase(title)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void addCardItem(GlazyCard card) {
        cards.add(card);
        updateCount();
    }

    public void removeCardItem(int position) {
        try {
            cards.remove(position);
            updateCount();
        } catch (Exception e) {
        }
    }

    public void removeCardItem(GlazyCard card) {
        try {
            cards.remove(card);
            updateCount();
        } catch (Exception e) {
        }
    }

    public void updateCount() {
        CARDS_COUNT = cards.size();
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        GlazyCard card = cards.get(position);
        return GlazyCardFragment.newInstance(card, position);
    }

    @Override
    public int getCount() {
        return CARDS_COUNT;
    }

    /***************************
     * For updating view pager *
     ***************************/
    @Override
    public long getItemId(int position) {
        return mItems.get(position);
    }

    /**
     * This method is only gets called when we invoke {@link #notifyDataSetChanged()} on this adapter.
     * Returns the index of the currently active fragments.
     * There could be minimum two and maximum three active fragments(suppose we have 3 or more  fragments to show).
     * If there is only one fragment to show that will be only active fragment.
     * If there are only two fragments to show, both will be in active state.
     * PagerAdapter keeps left and right fragments of the currently visible fragment in ready/active state so that it could be shown immediate on swiping.
     * Currently Active Fragments means one which is currently visible one is before it and one is after it.
     *
     * @param object Active Fragment reference
     * @return Returns the index of the currently active fragments.
     */
    @Override
    public int getItemPosition(Object object) {
        GlazyCardFragment item = (GlazyCardFragment) object;
        int itemValue = item.getSomeIdentifier();
        for (int i = 0; i < mItems.size(); i++) {
            if (mItems.get(i).equals(itemValue)) {
                return i;
            }
        }
        return POSITION_NONE;
    }
}
