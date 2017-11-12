package com.kannan.glazy.fragments;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kannan.glazy.GlazyCard;
import com.kannan.glazy.R;
import com.kannan.glazy.utils.Utils;
import com.kannan.glazy.interfaces.FragmentItemClickListener;
import com.kannan.glazy.views.GlazyImageView;

import static com.kannan.glazy.utils.Utils.flashingView;

public class GlazyCardFragment extends Fragment {

    private Context mContext;
    private GlazyCard card;
    private FragmentItemClickListener fragmentItemClickListener;

    public static GlazyCardFragment newInstance(GlazyCard card, int position) {

        Bundle args = new Bundle();

        //For updating view pager position is needed
        args.putInt("position", position);

        //For glazy view pager
        GlazyCardFragment glazyCardFragment = new GlazyCardFragment();
        args.putSerializable("glazy_card", card);
        glazyCardFragment.setArguments(args);

        return glazyCardFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        card = (GlazyCard) getArguments().getSerializable("glazy_card");
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.layout_page, container, false);
        v.setBackgroundColor(card.getBackgroundColor());

        TextView country = (TextView) v.findViewById(R.id.tv_nationality);
        country.setText(card.getCountry());

        TextView lifeSpan = (TextView) v.findViewById(R.id.tv_life_span);
        lifeSpan.setText(card.getBirthDate()+" - "+card.getDeathDate());

        TextView description = (TextView) v.findViewById(R.id.tv_quote);
        description.setText(card.getDescription());
        description.setAlpha(0f);

        GlazyImageView imgView = (GlazyImageView) v.findViewById(R.id.glazy_image_view);
        imgView.setImageRes(card.getImageRes());
        imgView.setTitleText(card.getTitle());
        imgView.setTitleTextColor(card.getTitleColor());
        imgView.setTitleTextSize(Utils.dpToPx(mContext, card.getTitleSizeDP()));
        imgView.setSubTitleText(card.getSubTitle());
        imgView.setSubTitleTextColor(card.getSubTitleColor());
        imgView.setSubTitleTextSize(Utils.dpToPx(mContext, card.getSubTitleSizeDP()));
        imgView.setTextMargin(Utils.dpToPx(mContext, card.getTextmatginDP()));
        imgView.setLineSpacing(Utils.dpToPx(mContext, card.getLineSpacingDP()));
        imgView.setAutoTint(card.isAutoTint());
        imgView.setTintColor(card.getTintColor());
        imgView.setTintAlpha(card.getTintAlpha());
        imgView.setCutType(card.getImageCutType());
        imgView.setCutCount(card.getImageCutCount());
        imgView.setCutHeight(Utils.dpToPx(mContext, card.getImageCutHeightDP()));

        final LinearLayout llViewAllQuotes = (LinearLayout) v.findViewById(R.id.ll_more);

        //Animate view all quotes layout
        flashingView(llViewAllQuotes,2000);

        //Listener for view all quotes button
        llViewAllQuotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentItemClickListener.onFragmentItemClick(v);
            }
        });

        return v;
    }

    //sending callback to activity
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof FragmentItemClickListener) {
            fragmentItemClickListener = (FragmentItemClickListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement SampleCallback");
        }
    }

    @Override
    public void onDetach() {
        fragmentItemClickListener = null;
        super.onDetach();
    }

    /***************************
     * For updating view pager *
     ***************************/
    public int getSomeIdentifier() {
        return getArguments().getInt("position");
    }
}
