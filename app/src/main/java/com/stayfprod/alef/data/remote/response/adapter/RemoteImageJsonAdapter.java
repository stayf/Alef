package com.stayfprod.alef.data.remote.response.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.stayfprod.alef.data.entity.RemoteImage;

import java.io.IOException;

public class RemoteImageJsonAdapter extends TypeAdapter<RemoteImage> {
    @Override
    public void write(JsonWriter out, RemoteImage value) throws IOException {
        // Java → JSON
    }

    @Override
    public RemoteImage read(JsonReader in) throws IOException {
        // JSON → Java
        RemoteImage remoteImage = new RemoteImage();
        remoteImage.setSrc(in.nextString());
        return remoteImage;
    }
}