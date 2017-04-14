package com.pchmn.utilsforandroid;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;


public class CountryCallingCodeUtil {

    /**
     * Get a map of country names with their calling code
     *
     * @param context the context of the app
     * @return map of country names with their region code
     */
    public static Map<String, String> getCountryNamesWithCallingCodes(Context context) {
        Map<String, String> countryNameToRegionCodeMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        String[] countryCodes = context.getResources().getStringArray(R.array.CountryCodes);

        for(String countryCode : countryCodes) {
            String[] country = countryCode.split(",");
            Locale locale = new Locale("", country[1]);
            countryNameToRegionCodeMap.put(locale.getDisplayCountry(), country[0]);
        }

        return countryNameToRegionCodeMap;
    }

    /**
     * Get the country name given a country calling code
     *
     * @param context the context of the app
     * @param code the country calling code
     * @return country name, or null if not found
     */
    public static String getCountryNameCallingCode(Context context, String code) {
        String countryFound = null;
        String[] countryCodes = context.getResources().getStringArray(R.array.CountryCodes);

        for(String countryCode : countryCodes) {
            String[] country = countryCode.split(",");
            if (country[0].equals(code)) {
                Locale locale = new Locale("", country[1]);
                countryFound = locale.getDisplayCountry();
                break;
            }
        }

        return countryFound;
    }

    /**
     * Get the current country calling code by checking the sim card
     *
     * @param context the context of the app
     * @return region code, or null if not found
     */
    public static String getCurrentCountryCallingCode(Context context){
        String CountryID;
        String regionCode = null;

        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        //getNetworkCountryIso
        CountryID = manager.getSimCountryIso().toUpperCase();
        String[] rl = context.getResources().getStringArray(R.array.CountryCodes);
        for (String aRl : rl) {
            String[] g = aRl.split(",");
            if (g[1].trim().equals(CountryID.trim())) {
                regionCode = g[0];
                break;
            }
        }
        return regionCode;
    }

    /**
     * Get the country ISO give a country calling code
     *
     * @param context the context of the app
     * @param code the country calling code
     * @return country ISO, or null if not found
     */
    public static String getCountryIsoByCallingCode(Context context, String code) {
        String countryIso = null;
        String[] countryCodes = context.getResources().getStringArray(R.array.CountryCodes);

        for(String countryCode : countryCodes) {
            String[] country = countryCode.split(",");
            if (country[0].equals(code)) {
                countryIso = country[1];
                break;
            }
        }

        return countryIso;
    }
}
