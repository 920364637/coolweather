package com.coolweather.android.util;

import android.text.TextUtils;

import com.coolweather.android.db.City;
import com.coolweather.android.db.County;
import com.coolweather.android.db.Province;
import com.coolweather.android.gson.Weather;
import com.google.gson.Gson;

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

    /**
     * 处理市级响应
     *
     * @param response   响应
     * @param provinceId 省id
     * @return 是否处理成功
     */
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

    /**
     * 处理县级相应
     *
     * @param response 响应
     * @param cityId   市id
     * @return 是否处理成功
     */
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

    /**
     * 将返回的JSON解析成Weather类
     */
    public static Weather handleWeatherResponse(String response) {
        try {
            JSONObject jo = new JSONObject(response);
            JSONArray ja = jo.getJSONArray("HeWeather");
            String weatherContent = ja.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
