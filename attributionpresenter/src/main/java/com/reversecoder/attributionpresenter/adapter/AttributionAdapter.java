package com.reversecoder.attributionpresenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.reversecoder.attributionpresenter.R;
import com.reversecoder.attributionpresenter.activity.LicenseDetailActivity;
import com.reversecoder.attributionpresenter.model.Attribution;
import com.reversecoder.attributionpresenter.model.LicenseInfo;
import com.reversecoder.attributionpresenter.util.BrowserOpener;

import java.util.ArrayList;
import java.util.List;

import static com.reversecoder.attributionpresenter.util.Constants.INTENT_KEY_ATTRIBUTION;

public class AttributionAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Attribution> mData;
    private Context mContext;

    public AttributionAdapter(Context context) {
        mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mData = new ArrayList<Attribution>();
    }

    public void setData(List<Attribution> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public List<Attribution> getData() {
        return mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Attribution getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View vi = convertView;
        if (convertView == null) {
            vi = inflater.inflate(R.layout.list_item_attribution, null);
        }

        final Attribution mItem = getItem(position);

        TextView attributionName = (TextView) vi.findViewById(R.id.tv_attribution_name);
        attributionName.setText(mItem.getAttributionName());

        TextView copyrightNotice = (TextView) vi.findViewById(R.id.tv_copyright_notice);
        copyrightNotice.setText((mItem.getLicensesInfo().size() > 0) ? mItem.getLicensesInfo().get(0).getCopyrightNotice() : "");

        ViewGroup licensesLayout = (ViewGroup) vi.findViewById(R.id.fb_licenses_layout);
        licensesLayout.removeAllViews();
        for (LicenseInfo licenseInfo : mItem.getLicensesInfo()) {
            addLicenseView(parent.getContext(), licensesLayout, licenseInfo);
        }

        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLicenseDetail = new Intent(mContext, LicenseDetailActivity.class);
                intentLicenseDetail.putExtra(INTENT_KEY_ATTRIBUTION, mItem);
                mContext.startActivity(intentLicenseDetail);
            }
        });

        return vi;
    }

    private void addLicenseView(final Context context, ViewGroup licensesLayout, final LicenseInfo licenseInfo) {
        View inflatedView = LayoutInflater.from(context).inflate(R.layout.layout_license_name, licensesLayout, false);
        TextView licenseTextView = (TextView) inflatedView.findViewById(R.id.tv_license);

        if (licenseTextView == null) {
            throw new IllegalStateException("LicenseInfo layout does not contain a required TextView with android:id=\"@+id/licenseInfo\"");
        }

        licenseTextView.setText(licenseInfo.getLicense().getLicenseName());
        licenseTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowserOpener.open(context, licenseInfo.getLicense().getLicenseUrl());
            }
        });
        licensesLayout.addView(inflatedView);
    }
}