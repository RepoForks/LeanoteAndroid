package com.apkdv.leanote.network;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import com.apkdv.leanote.model.BaseResponse;
import com.apkdv.leanote.model.Note;
import com.apkdv.leanote.utils.CollectionUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class LeaResponseConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    LeaResponseConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override public T convert(ResponseBody value) throws IOException {
        String jsonString = value.string();
        try {
            T val = adapter.fromJson(jsonString);
            if (val instanceof Note) {
                ((Note)val).updateTags();
                ((Note)val).updateTime();
            } if (val instanceof List
                    && CollectionUtils.isNotEmpty((Collection) val)
                    && ((List)val).get(0) instanceof Note) {
                List<Note> notes = (List<Note>) val;
                for (Note note : notes) {
                    note.updateTags();
                    note.updateTime();
                }
            }
            return val;
        } catch (Exception ex) {
            Log.i("LeaResponseConverter", ex.getMessage());
            BaseResponse response = gson.fromJson(jsonString, BaseResponse.class);
            throw new LeaFailure(response);
        }finally {
            value.close();
        }
    }
}