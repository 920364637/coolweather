package com.coolweather.android.util;

import android.text.TextUtils;

import com.coolweather.android.db.City;
import com.coolweather.android.db.County;
import com.coolweather.android.db.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {
    /**
     * 处理省级响应
     *
     * @param response 响应
     * @return 是否处理成功
     */
    public static boolean handleProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allJsonObject = new JSONArray(response);
                for (int i = 0; i < allJsonObject.length(); i++) {
                    JSONObject jo = allJsonObject.getJSONObject(i);
                    Province p = new Province();
                    p.setProvinceName(jo.getString("name"));
                    p.setProvinceCode(jo.getInt("id"));
                    p.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleCityResponse(String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allJsonObject = new JSONArray(response);
                for (int i = 0; i < allJsonObject.length(); i++) {
                    JSONObject jo = allJsonObject.getJSONObject(i);
                    City c = new City();
                    c.setCityName(jo.getString("name"));
                    c.setCityCode(jo.getInt("id"));
                    c.setProvinceId(provinceId);
                    c.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleCountyResponse(String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allJsonObject = new JSONArray(response);
                for (int i = 0; i < allJsonObject.length(); i++) {
                    JSONObject jo = allJsonObject.getJSONObject(i);
                    County c = new County();
                    c.setCountyName(jo.getString("name"));
                    c.setWeatherId(jo.getString("weather_id"));
                    c.setCityId(cityId);
                    c.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
