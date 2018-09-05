package com.stayfprod.alef.data.entity;

import com.google.gson.annotations.JsonAdapter;
import com.stayfprod.alef.data.remote.response.adapter.RemoteImageJsonAdapter;
import com.stayfprod.alef.util.Objects;

@JsonAdapter(RemoteImageJsonAdapter.class)
public class RemoteImage {
    private String src;

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RemoteImage that = (RemoteImage) o;
        return Objects.equals(src, that.src);
    }

    @Override
    public int hashCode() {
        return Objects.hash(src);
    }

    public String getSrc() {
        return src;
    }
}
