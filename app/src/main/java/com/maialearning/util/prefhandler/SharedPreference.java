package com.maialearning.util.prefhandler;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPreference {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public Context context;

    SharedPreference(Context context) {
        this.context = context;
    }

    void putKey(String Key, String Value) {
        sharedPreferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(Key, Value);
        editor.apply();

    }
    String getKey(String Key) {
        sharedPreferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        return sharedPreferences.getString(Key, "");
    }

    void clearAll() {
        sharedPreferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
    List<String> getInsuranceTypeList(String Key) {
        sharedPreferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        List<String> formatedList = new ArrayList<>();
        String jsonPreferences = sharedPreferences.getString(Key, "");
        Type type = new TypeToken<List<String>>() {
        }.getType();
        formatedList = gson.fromJson(jsonPreferences, type);
        return formatedList;
    }

    void putSymbolList(String Key, List<String> locationList) {
        Gson gson = new Gson();
        String locationListString = gson.toJson(locationList);
        sharedPreferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(Key, locationListString);
        editor.apply();
    }
}
