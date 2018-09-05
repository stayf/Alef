package com.stayfprod.alef.data.entity;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

public final class Listing<T> {
    private final LiveData<PagedList<T>> pagedList;
    private final LiveData<DataState> networkState;
    private final LiveData<DataState> refreshState;
    private final Runnable retry;
    private final Runnable refresh;

    public LiveData<PagedList<T>> getPagedList() {
        return this.pagedList;
    }

    public LiveData<DataState> getNetworkState() {
        return this.networkState;
    }

    public LiveData<DataState> getRefreshState() {
        return this.refreshState;
    }

    public Runnable getRetry() {
        return this.retry;
    }

    public Runnable getRefresh() {
        return this.refresh;
    }

    public Listing(LiveData<PagedList<T>> pagedList, LiveData<DataState> networkState, LiveData<DataState> refreshState, Runnable retry, Runnable refresh) {
        this.pagedList = pagedList;
        this.networkState = networkState;
        this.refreshState = refreshState;
        this.retry = retry;
        this.refresh = refresh;
    }
}
