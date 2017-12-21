package com.reversecoder.quote.geocoding;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;

import java.util.List;
import java.util.Locale;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class ReverseGeocoderTask extends AsyncTask<Location, Void, UserLocationAddress> {

    Context mContext;
    LocationAddressListener mLocationAddressListener;

    public ReverseGeocoderTask(Context context, LocationAddressListener locationAddressListener) {
        mContext = context;
        mLocationAddressListener = locationAddressListener;
    }

    @Override
    protected UserLocationAddress doInBackground(Location... params) {

        try {

            if (params.length > 0) {
                Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
                Location location = params[0];

                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                if (addresses != null && addresses.size() > 0) {

                    Address address = addresses.get(0);

                    double latitude = address.getLatitude();
                    double longitude = address.getLongitude();
                    String streetAddress = (address.getMaxAddressLineIndex() > 0) ? address.getAddressLine(0) : "";
                    String city = address.getLocality();
                    String state = address.getAdminArea();
                    String country = address.getCountryName();
                    String countryCode = address.getCountryCode();
                    String postalCode = address.getPostalCode();
                    String knownName = address.getFeatureName();

                    UserLocationAddress locationAddress = new UserLocationAddress(latitude, longitude, streetAddress, city, state, country, countryCode, postalCode, knownName);
                    mLocationAddressListener.getLocationAddress(locationAddress);

                    return locationAddress;
                }

            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(UserLocationAddress address) {
    }
}
