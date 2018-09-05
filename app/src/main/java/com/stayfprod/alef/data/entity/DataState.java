package com.stayfprod.alef.data.entity;

import androidx.annotation.Nullable;

public class DataState {
    public enum Status {
        RUNNING,
        SUCCESS,
        FAILED
    }

    private static final DataState LOADED;
    private static final DataState LOADING;

    static {
        LOADED = new DataState(Status.SUCCESS, null);
        LOADING = new DataState(Status.RUNNING, null);
    }

    private final Status status;
    private final String msg;

    public Status getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    private DataState(Status status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static DataState loaded() {
        return LOADED;
    }

    public static DataState loading() {
        return LOADING;
    }

    public static DataState error(@Nullable String msg) {
        return new DataState(Status.FAILED, msg);
    }
}
